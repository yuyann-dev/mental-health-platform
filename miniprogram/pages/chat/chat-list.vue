<template>
  <view class="container">
    <!-- 添加按钮 -->
    <view class="add-btn" @click="addNewChat" v-if="userType === 1">
      <image src="/static/plus.png" mode="aspectFit"></image>
    </view>

    <!-- 聊天列表 -->
    <scroll-view 
      scroll-y 
      class="chat-list" 
      refresher-enabled 
      :refresher-triggered="isRefreshing"
      @refresherrefresh="onRefresh"
      @refresherrestore="onRestore"
    >
      <view 
        class="chat-item" 
        v-for="chat in chatList" 
        :key="chat.id"
        @click="gotoChat(chat)"
      >
        <view class="avatar-wrapper">
          <image 
            class="avatar"
            :src="userType === 1 ? 
              (chat.receiverType === 2 ? getAvatarUrl(chat.receiverAvatar) || '/static/doctor-avatar.svg' : getAvatarUrl(chat.senderAvatar) || '/static/patient-avatar.svg') :
              (chat.senderType === 1 ? getAvatarUrl(chat.senderAvatar) || '/static/patient-avatar.svg' : getAvatarUrl(chat.receiverAvatar) || '/static/doctor-avatar.svg')"
            mode="aspectFill"
          />
          <view class="unread" v-if="chat.unreadCount">{{ chat.unreadCount }}</view>
        </view>
        <view class="chat-info">
          <view class="top-line">
            <text class="name">{{ userType === 1 ? 
              (chat.receiverType === 2 ? chat.receiverName : chat.senderName) :
              (chat.senderType === 1 ? chat.senderName : chat.receiverName) }}</text>
            <text class="time">{{ formatTime(chat.createTime) }}</text>
          </view>
          <view class="bottom-line">
            <text class="last-message" :class="{'unread-message': chat.unreadCount}">{{ getMessagePreview(chat) }}</text>
          </view>
        </view>
      </view>
    </scroll-view>

    <!-- 空状态 -->
    <view class="empty-state" v-if="!loading && (!chatList || chatList.length === 0)">
      <image src="/static/empty-chat.svg" mode="aspectFit"></image>
      <text>暂无聊天记录</text>
      <text class="empty-tips">开始和医生进行咨询吧</text>
    </view>

    <!-- 加载状态 -->
    <view class="loading-state" v-if="loading">
      <text>加载中...</text>
    </view>
  </view>
</template>

<script>
import { getRecentChats, createWebSocket } from '@/api/chat'
import { formatTimeAgo } from '@/utils/time'

import config from '@/config'

export default {
  data() {
    return {
      userId: '',
      userType: 1,
      userName: '',
      chatList: [],
      loading: false,
      socketTask: null,
      isRefreshing: false
    }
  },

  onLoad() {
    this.initUserInfo()
  },

  onShow() {
    // 每次显示页面时重新加载聊天列表
    if (this.userId) {
      this.loadChatList()
    } else {
      this.initUserInfo()
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
    getAvatarUrl(avatar) {
      if (!avatar) return ''
      if (avatar.startsWith('http')) return avatar
      return config.url + avatar
    },

    initUserInfo() {
      // 从Vuex store获取用户信息
      const userStore = this.$store.state.user
      console.log('Vuex用户信息:', userStore)
      
      if (userStore && userStore.hasLogin && userStore.userId) {
        this.userId = userStore.userId
        this.userType = userStore.userType || 1 // 默认为患者(1)
        this.userName = userStore.name || ''
        
        // 初始化WebSocket
        this.initWebSocket()
      } else {
        console.error('未登录或用户信息不完整')
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

    initWebSocket() {
      this.socketTask = createWebSocket(this.userId, this.userType)
      
      if (this.socketTask) {
        this.socketTask.onMessage((event) => {
          try {
            const message = JSON.parse(event.data)
            // 收到新消息时更新聊天列表
            this.loadChatList()
          } catch (error) {
            console.error('处理WebSocket消息失败:', error)
          }
        })
      }
    },

    // 处理下拉刷新
    async onRefresh() {
      this.isRefreshing = true
      await this.loadChatList()
      this.isRefreshing = false
    },

    // 处理刷新复位
    onRestore() {
      this.isRefreshing = false
    },

    async loadChatList() {
      if (this.loading) return
      
      this.loading = true
      try {
        const res = await getRecentChats(this.userId, this.userType)
        console.log('获取聊天列表结果:', res)
        
        if (res.code === '200') {
          this.chatList = res.data
        } else {
          console.error('获取聊天列表失败:', res.msg)
          uni.showToast({
            title: res.msg || '获取聊天列表失败',
            icon: 'none'
          })
        }
      } catch (error) {
        console.error('加载聊天列表失败:', error)
        uni.showToast({
          title: '加载失败',
          icon: 'none'
        })
      } finally {
        this.loading = false
      }
    },

    // 跳转到聊天详情页
    gotoChat(chat) {
      // 根据当前用户类型和聊天对象类型来确定目标用户信息
      let targetId, targetType, targetName, targetAvatar
      
      if (this.userType === 1) { // 当前用户是患者
        if (chat.receiverType === 2) { // 对方是医生
          targetId = chat.receiverId
          targetType = chat.receiverType
          targetName = chat.receiverName
          targetAvatar = chat.receiverAvatar
        } else { // 对方是患者
          targetId = chat.senderId
          targetType = chat.senderType
          targetName = chat.senderName
          targetAvatar = chat.senderAvatar
        }
      } else { // 当前用户是医生
        if (chat.senderType === 1) { // 对方是患者
          targetId = chat.senderId
          targetType = chat.senderType
          targetName = chat.senderName
          targetAvatar = chat.senderAvatar
        } else { // 对方是医生
          targetId = chat.receiverId
          targetType = chat.receiverType
          targetName = chat.receiverName
          targetAvatar = chat.receiverAvatar
        }
      }

      const params = {
        targetId,
        targetType,
        targetName,
        targetAvatar: this.getAvatarUrl(targetAvatar) || ''
      }
      
      // 编码URL参数
      const query = Object.keys(params)
        .map(key => `${key}=${encodeURIComponent(params[key] || '')}`)
        .join('&')
      
      console.log('跳转参数:', params)
      
      uni.navigateTo({
        url: `/pages/chat/chat-detail?${query}`,
        fail: (error) => {
          console.error('导航失败:', error)
          uni.showToast({
            title: '进入聊天失败，请重试',
            icon: 'none'
          })
        }
      })
    },

    // 添加新聊天（仅患者可用）
    addNewChat() {
      if (this.userType !== 1) {
        uni.showToast({
          title: '仅患者可以发起咨询',
          icon: 'none'
        })
        return
      }
      
      uni.navigateTo({
        url: '/pages/chat/select-doctor',
        fail: (error) => {
          console.error('导航失败:', error)
          uni.showToast({
            title: '进入选择页面失败，请重试',
            icon: 'none'
          })
        }
      })
    },

    getMessagePreview(chat) {
      if (chat.messageType === 1) {
        return chat.content
      } else if (chat.messageType === 2) {
        return '[图片]'
      } else if (chat.messageType === 3) {
        return '[语音]'
      }
      return ''
    },

    formatTime(time) {
      return formatTimeAgo(time)
    }
  }
}
</script>

<style>
.container {
  height: 100vh;
  background-color: #f5f5f5;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  padding-bottom: env(safe-area-inset-bottom);
}

.add-btn {
  position: fixed;
  right: 30rpx;
  bottom: calc(120rpx + env(safe-area-inset-bottom));  /* 适配底部安全区域 */
  width: 100rpx;
  height: 100rpx;
  background-color: #007AFF;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4rpx 16rpx rgba(0, 122, 255, 0.3);
  z-index: 100;
}

.add-btn image {
  width: 48rpx;
  height: 48rpx;
  filter: brightness(0) invert(1);
}

.chat-list {
  flex: 1;
  height: 100%;
  box-sizing: border-box;
}

.chat-item {
  display: flex;
  align-items: center;
  padding: 30rpx;
  background-color: #fff;
  border-bottom: 1rpx solid #eee;
  box-sizing: border-box;
}

.avatar-wrapper {
  position: relative;
  margin-right: 24rpx;
  flex-shrink: 0;
}

.avatar {
  width: 96rpx;
  height: 96rpx;
  border-radius: 48rpx;
  background-color: #f0f0f0;
}

.unread {
  position: absolute;
  top: -6rpx;
  right: -6rpx;
  min-width: 32rpx;
  height: 32rpx;
  padding: 0 8rpx;
  background-color: #ff4d4f;
  border-radius: 16rpx;
  color: #fff;
  font-size: 24rpx;
  display: flex;
  align-items: center;
  justify-content: center;
}

.chat-info {
  flex: 1;
  min-width: 0;  /* 防止flex子元素溢出 */
}

.top-line {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12rpx;
}

.name {
  font-size: 32rpx;
  color: #333;
  font-weight: 500;
  flex-shrink: 1;  /* 允许名字压缩 */
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-right: 20rpx;  /* 与时间保持间距 */
}

.time {
  font-size: 24rpx;
  color: #999;
  flex-shrink: 0;  /* 防止时间被压缩 */
}

.bottom-line {
  display: flex;
  align-items: center;
  width: 100%;
}

.last-message {
  font-size: 28rpx;
  color: #666;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 100%;  /* 使用完整宽度 */
}

.unread-message {
  color: #333;
  font-weight: 500;
}

.empty-state {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 60rpx;
}

.empty-state image {
  width: 240rpx;
  height: 240rpx;
  margin-bottom: 20rpx;
}

.empty-state text {
  font-size: 32rpx;
  color: #333;
  margin-bottom: 12rpx;
}

.empty-tips {
  font-size: 28rpx;
  color: #999;
}

.loading-state {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
  font-size: 28rpx;
}
</style> 