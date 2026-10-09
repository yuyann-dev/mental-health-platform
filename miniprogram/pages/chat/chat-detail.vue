<template>
  <view class="container">
    <!-- 消息列表 -->
    <scroll-view 
      scroll-y 
      class="message-list"
      :scroll-top="scrollTop"
      :scroll-with-animation="true"
    >
      <!-- 加载更多 -->
      <view class="load-more" v-if="hasMore && messageList.length > 0">
        <text>{{ loading ? '加载中...' : '加载更多' }}</text>
      </view>
      
      <!-- 消息内容 -->
      <view class="message-wrapper">
        <block v-for="(message, index) in messageList" :key="message.id">
          <!-- 日期分割线 -->
          <view class="date-divider" v-if="showDateDivider(message, index)">
            <text>{{ formatDate(message.createTime) }}</text>
          </view>
          
          <!-- 消息气泡 -->
          <view :class="['message-item', message.senderId === userId && message.senderType === userType ? 'self' : 'other']">
            <image 
              class="avatar"
              :src="getAvatarUrl(
                message.senderId === userId && message.senderType === userType ? message.senderAvatar : message.senderAvatar,
                message.senderId === userId && message.senderType === userType ? userType : message.senderType
              )"
              mode="aspectFill"
            ></image>
            <view class="message-content">
              <view class="text-message" :class="{'send-failed': message.sendFailed}">
                {{ message.content }}
              </view>
            </view>
          </view>
        </block>
      </view>

      <!-- 空状态 -->
      <view class="empty-state" v-if="!loading && (!messageList || messageList.length === 0)">
        <text>暂无聊天记录</text>
      </view>
    </scroll-view>

    <!-- 输入区域 -->
    <view class="input-area">
      <view class="input-box">
        <input
          type="text"
          v-model="inputContent"
          class="text-input"
          placeholder="请输入消息"
          :confirm-type="'send'"
          @confirm="sendTextMessage"
        />
        <view class="send-btn" @tap="sendTextMessage">发送</view>
      </view>
    </view>
  </view>
</template>

<script>
import { getChatHistory, markMessageRead, sendWebSocketMessage, createWebSocket } from '@/api/chat'
import { formatTimeAgo, formatDate } from '@/utils/time'
import { getFileUrl } from '@/utils/common'

export default {
  data() {
    return {
      userId: '',
      userType: 1,
      userName: '',
      userAvatar: '',
      targetId: '',
      targetType: '',
      targetName: '',
      targetAvatar: '',
      messageList: [],
      pageNum: 1,
      pageSize: 20,
      total: 0,
      hasMore: false,
      loading: false,
      scrollTop: 0,
      inputContent: '',
      socketTask: null
    }
  },

  onLoad(options) {
    console.log('聊天页面参数:', options)
    
    // 解码URL参数，确保ID是数字
    this.targetId = parseInt(options.targetId)
    this.targetType = parseInt(options.targetType)
    this.targetName = decodeURIComponent(options.targetName || '')
    this.targetAvatar = decodeURIComponent(options.targetAvatar || '')
    
    console.log('解析后的参数:', {
      targetId: this.targetId,
      targetType: this.targetType,
      targetName: this.targetName
    })
    
    // 设置导航栏标题
    uni.setNavigationBarTitle({
      title: this.targetName || '医生咨询'
    })
    
    this.checkLoginAndInit()
  },
  
  onShow() {
    // 每次页面显示时重新检查登录状态
    if (this.targetId) {  // 只在已初始化后才检查
      this.checkLoginAndInit()
    }
  },

  onUnload() {
    // 页面卸载时关闭WebSocket
    if (this.socketTask) {
      this.socketTask.close()
      this.socketTask = null
    }
  },

  methods: {
    // 检查登录状态并初始化
    checkLoginAndInit() {
      const userInfo = uni.getStorageSync('info')
      console.log("检查用户信息:", userInfo);
      
      if (userInfo && userInfo.id) {
        this.userId = userInfo.id || userInfo.userId
        this.userType = userInfo.type || userInfo.userType || 1
        this.userName = userInfo.name || userInfo.userName || userInfo.username || ''
        this.userAvatar = userInfo.avatar || ''
        
        console.log('设置用户信息:', { 
          userId: this.userId, 
          userType: this.userType, 
          userName: this.userName,
          userAvatar: this.userAvatar
        })
        
        if (!this.userId || !this.userType) {
          console.error('用户信息不完整:', userInfo)
          uni.showToast({
            title: '用户信息获取失败',
            icon: 'none'
          })
          setTimeout(() => {
            uni.navigateBack()
          }, 1500)
          return
        }
        
        // 只在第一次加载时初始化（避免重复）
        if (!this.socketTask) {
          this.loadMessages()
          this.initWebSocket()
        }
      } else {
        console.error('未找到用户信息，跳转登录')
        uni.showToast({
          title: '请先登录',
          icon: 'none'
        })
        setTimeout(() => {
          uni.redirectTo({
            url: '/pages/login/login'
          })
        }, 1500)
      }
    },
    
    // 获取完整的头像URL（仅用于前端展示，不在消息体内传默认 /static/*）
    getAvatarUrl(avatar, userType) {
      if (!avatar) {
        // 如果没有头像，返回默认头像
        return userType === 1 ? '/static/patient-avatar.svg' : '/static/doctor-avatar.svg'
      }
      return getFileUrl(avatar)
    },
    
    async loadMessages(isLoadMore = false) {
      this.loading = true
      try {
        const params = {
          userId: this.userId,
          userType: this.userType,
          targetId: this.targetId,
          targetType: this.targetType,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
        
        console.log('加载历史消息，参数:', params)
        const res = await getChatHistory(params)
        console.log('历史消息返回结果:', res)
        
        if (res.code === '200') {
          const { list, total } = res.data
          console.log('历史消息列表:', list)
          
          // 处理消息数据
          const processedList = list.map(msg => ({
            id: msg.id,
            senderId: msg.senderId,
            senderType: msg.senderType,
            senderName: msg.senderName,
            senderAvatar: msg.senderAvatar,  // 直接使用后端返回的头像
            receiverId: msg.receiverId,
            receiverType: msg.receiverType,
            receiverName: msg.receiverName,
            receiverAvatar: msg.receiverAvatar,  // 直接使用后端返回的头像
            content: msg.content,
            messageType: msg.messageType || 1,
            createTime: msg.createTime
          }))

          // 按时间排序消息
          processedList.sort((a, b) => {
            return new Date(a.createTime) - new Date(b.createTime)
          })
          
          if (isLoadMore) {
            // 加载更多时，新消息在前面
            this.messageList = [...processedList, ...this.messageList]
          } else {
            this.messageList = processedList
          }
          
          console.log('处理后的消息列表:', this.messageList)
          this.total = total
          this.hasMore = this.messageList.length < total
          
          if (!isLoadMore) {
            this.scrollToBottom()
          }
        } else {
          console.error('获取历史消息失败:', res.msg)
          uni.showToast({
            title: res.msg || '获取历史消息失败',
            icon: 'none'
          })
        }
      } catch (error) {
        console.error('加载历史消息失败:', error)
        uni.showToast({
          title: '加载失败',
          icon: 'none'
        })
      } finally {
        this.loading = false
      }
    },

    initWebSocket() {
      try {
        this.socketTask = createWebSocket(this.userId, this.userType)
        
        if (this.socketTask) {
          console.log('使用createWebSocket初始化成功')
          
          this.socketTask.onMessage((res) => {
            try {
              const message = JSON.parse(res.data)
              console.log('收到WebSocket消息:', message)
              
              // 处理错误消息
              if (message.type === 'error') {
                uni.showToast({
                  title: message.message || '消息发送失败',
                  icon: 'none'
                })
                return
              }
              
              // 处理确认消息（自己发送的消息的确认）
              if (message.type === 'confirm') {
                // 更新本地消息状态
                const index = this.messageList.findIndex(m => m.sending)
                if (index !== -1) {
                  this.messageList[index].sending = false
                  this.messageList[index].id = message.messageId
                }
                return
              }
              
              // 处理接收到的消息（type === 'message'）
              if (message.type === 'message') {
                // 验证消息是否属于当前会话
                const isFromTarget = (message.senderId == this.targetId && message.senderType == this.targetType)
                const isToMe = (message.receiverId == this.userId && message.receiverType == this.userType)

                // 统一唯一ID（数据库id或临时tempId）
                const incomingUniqueId = message.id || message.tempId

                console.log('消息验证:', {
                  msgSenderId: message.senderId,
                  targetId: this.targetId,
                  msgSenderType: message.senderType,
                  targetType: this.targetType,
                  msgReceiverId: message.receiverId,
                  userId: this.userId,
                  msgReceiverType: message.receiverType,
                  userType: this.userType,
                  isFromTarget: isFromTarget,
                  isToMe: isToMe,
                  uniqueId: incomingUniqueId
                })

                if (isFromTarget && isToMe) {
                  // 检查是否已存在（避免重复）
                  const exists = this.messageList.find(m => (m.id || m.tempId) === incomingUniqueId)
                  if (!exists) {
                    console.log('添加新消息到列表(唯一ID=' + incomingUniqueId + '):', message)
                    const normalized = {
                      ...message,
                      id: incomingUniqueId
                    }
                    this.messageList.push(normalized)
                    this.scrollToBottom()
                  } else {
                    console.log('消息已存在，忽略唯一ID:', incomingUniqueId)
                  }
                } else {
                  console.log('过滤消息 - 不属于当前会话')
                }
              }
            } catch (error) {
              console.error('解析消息失败:', error)
              uni.showToast({
                title: '消息处理失败',
                icon: 'none'
              })
            }
          })
        } else {
          console.error('createWebSocket初始化失败')
          uni.showToast({
            title: '连接失败，请重试',
            icon: 'none'
          })
        }
      } catch (error) {
        console.error('初始化WebSocket失败:', error)
        uni.showToast({
          title: '连接错误，请检查网络',
          icon: 'none'
        })
      }
    },

    async markAsRead() {
      try {
        await markMessageRead({
          userId: this.userId,
          userType: this.userType,
          senderId: this.targetId,
          senderType: this.targetType
        })
      } catch (error) {
        console.error('标记已读失败:', error)
      }
    },

    async sendTextMessage() {
      if (!this.inputContent.trim()) {
        return
      }

      // 验证发送者和接收者不能是同一个人
      if (this.userId === this.targetId && this.userType === this.targetType) {
        uni.showToast({
          title: '不能给自己发送消息',
          icon: 'none'
        })
        return
      }

      // 重新获取最新用户信息，确保name字段存在
      const userInfo = uni.getStorageSync('info')
      if (!userInfo || !userInfo.id) {
        uni.showToast({
          title: '登录已过期，请重新登录',
          icon: 'none'
        })
        setTimeout(() => {
          uni.redirectTo({
            url: '/pages/login/login'
          })
        }, 1500)
        return
      }
      
      // 使用已保存的用户信息，避免每次都读取storage
      const realName = this.userName || userInfo.name || userInfo.username || '用户'
      const currentAvatar = this.userAvatar || userInfo.avatar || ''
      
      console.log('发送消息用户信息:', { 
        realName, 
        currentAvatar,
        userId: this.userId,
        userType: this.userType
      })
      
      // 规范化头像：如果是纯文件名补前缀 /mental/files/download/，空值不发送
      let normalizedAvatar = currentAvatar
      // 跳过小程序本地 /static/ 路径，留空让对端使用默认头像
      if (normalizedAvatar && normalizedAvatar.startsWith('/static/')) {
        normalizedAvatar = ''
      } else if (normalizedAvatar && !normalizedAvatar.startsWith('/mental/files') && !normalizedAvatar.startsWith('http')) {
        normalizedAvatar = '/mental/files/download/' + normalizedAvatar
      }
      
      // 同样规范化接收者头像
      let normalizedReceiverAvatar = this.targetAvatar || ''
      if (normalizedReceiverAvatar.startsWith('/static/')) {
        normalizedReceiverAvatar = ''
      } else if (normalizedReceiverAvatar && !normalizedReceiverAvatar.startsWith('/mental/files') && !normalizedReceiverAvatar.startsWith('http')) {
        normalizedReceiverAvatar = '/mental/files/download/' + normalizedReceiverAvatar
      }
      
      const message = {
         senderId: this.userId,
         senderType: this.userType,
         senderName: realName || '用户',  // 使用真实姓名，最后才用默认值
        senderAvatar: normalizedAvatar,
        receiverId: parseInt(this.targetId),  // 确保是数字
        receiverType: parseInt(this.targetType),  // 确保是数字
        receiverName: this.targetName || '',
        receiverAvatar: normalizedReceiverAvatar,
        content: this.inputContent.trim(),
        messageType: 1,
        isRead: 0,
        createTime: new Date().toISOString()
      }
      
      console.log('小程序发送消息:', message, 'userInfo:', userInfo)

      // 先添加到本地消息列表
      const localMessage = {
        ...message,
        id: Date.now(),
        sending: true
      }
      this.messageList.push(localMessage)
      this.scrollToBottom()

      try {
        // 使用chat.js中的sendWebSocketMessage方法发送消息
        const success = await sendWebSocketMessage(message)
        
        if (success) {
          this.inputContent = ''
        } else {
          // 标记消息发送失败
          const index = this.messageList.findIndex(m => m.id === localMessage.id)
          if (index !== -1) {
            this.messageList[index].sendFailed = true
            this.messageList[index].sending = false
          }
          uni.showToast({
            title: '发送失败，请重试',
            icon: 'none'
          })
        }
      } catch (error) {
        console.error('发送消息失败:', error)
        // 标记消息发送失败
        const index = this.messageList.findIndex(m => m.id === localMessage.id)
        if (index !== -1) {
          this.messageList[index].sendFailed = true
          this.messageList[index].sending = false
        }
        uni.showToast({
          title: '发送失败，请重试',
          icon: 'none'
        })
      }
    },

    scrollToBottom() {
      setTimeout(() => {
        const query = uni.createSelectorQuery().in(this)
        query.select('.message-list').boundingClientRect()
        query.select('.message-wrapper').boundingClientRect()
        query.exec(([listRect, wrapperRect]) => {
          if (listRect && wrapperRect) {
            // 计算滚动位置时加上输入框和底部安全区域的高度
            const inputBoxHeight = 140 // 输入框高度（包含padding）
            const safeAreaBottom = 34 // 底部安全区域高度（iPhone X 及以上机型）
            this.scrollTop = wrapperRect.height - listRect.height + inputBoxHeight + safeAreaBottom
            console.log('滚动到底部:', this.scrollTop)
          }
        })
      }, 100)
    },

    showDateDivider(message, index) {
      if (index === 0) return true
      const prevMessage = this.messageList[index - 1]
      return !this.isSameDay(message.createTime, prevMessage.createTime)
    },

    isSameDay(time1, time2) {
      const date1 = new Date(time1)
      const date2 = new Date(time2)
      return (
        date1.getFullYear() === date2.getFullYear() &&
        date1.getMonth() === date2.getMonth() &&
        date1.getDate() === date2.getDate()
      )
    },

    formatDate,
    formatTimeAgo
  }
}
</script>

<style>
.container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f5f5f5;
  box-sizing: border-box;
  padding-bottom: env(safe-area-inset-bottom);
}

.message-list {
  flex: 1;
  padding: 20rpx;
  box-sizing: border-box;
  height: calc(100vh - 120rpx - env(safe-area-inset-bottom));  /* 减去输入框高度 */
  padding-bottom: 140rpx; /* 为输入框预留空间 */
}

.input-area {
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #fff;
  border-top: 1rpx solid #eee;
  padding: 20rpx;
  padding-bottom: calc(20rpx + env(safe-area-inset-bottom));
  box-sizing: border-box;
  z-index: 100;
}

.input-box {
  display: flex;
  align-items: center;
  gap: 20rpx;
  height: 80rpx;
}

.text-input {
  flex: 1;
  height: 72rpx;
  background-color: #f5f5f5;
  border-radius: 36rpx;
  padding: 0 24rpx;
  font-size: 28rpx;
  box-sizing: border-box;
}

.send-btn {
  width: 120rpx;
  height: 72rpx;
  background-color: #007AFF;
  color: #fff;
  border-radius: 36rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28rpx;
  flex-shrink: 0;
}

.message-wrapper {
  display: flex;
  flex-direction: column;
}

.message-item {
  display: flex;
  margin-bottom: 20rpx;
  gap: 10rpx;
}

.message-item.self {
  flex-direction: row-reverse;
}

.avatar {
  width: 80rpx;
  height: 80rpx;
  border-radius: 50%;
  flex-shrink: 0;
}

.message-content {
  max-width: 70%;
}

.text-message {
  padding: 16rpx 24rpx;
  border-radius: 8rpx;
  font-size: 28rpx;
  word-break: break-all;
  background-color: #fff;
  position: relative;
}

.message-item.self .text-message {
  background-color: #007AFF;
  color: #fff;
}

.text-message.send-failed::after {
  content: '!';
  position: absolute;
  right: -24rpx;
  top: 50%;
  transform: translateY(-50%);
  color: #ff4d4f;
  font-size: 24rpx;
  width: 24rpx;
  height: 24rpx;
  line-height: 24rpx;
  text-align: center;
  border-radius: 50%;
  background-color: #fff;
  border: 1px solid #ff4d4f;
}

.date-divider {
  text-align: center;
  margin: 20rpx 0;
}

.date-divider text {
  font-size: 24rpx;
  color: #999;
  background-color: #f0f0f0;
  padding: 4rpx 16rpx;
  border-radius: 24rpx;
}

.empty-state {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  color: #999;
  font-size: 28rpx;
}

.load-more {
  text-align: center;
  padding: 20rpx;
  color: #999;
  font-size: 24rpx;
}
</style> 