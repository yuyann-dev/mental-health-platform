import request from '@/utils/request'

// 获取未读消息数
export function getUnreadCount(userId, userType) {
  return request({
    url: '/chat/unread',
    method: 'get',
    params: { userId, userType }
  })
}

// 标记消息为已读
export function markMessageRead(data) {
  return request({
    url: '/chat/read',
    method: 'post',
    data
  })
}

// 获取聊天历史记录
export function getChatHistory(params) {
  return request({
    url: '/chat/history',
    method: 'get',
    params
  })
}

// 获取最近聊天列表
export function getRecentChats(userId, userType) {
  return request({
    url: '/chat/recent',
    method: 'get',
    params: { userId, userType }
  })
}

// WebSocket实例和状态
let socketTask = null
let isConnecting = false
let reconnectTimer = null
const HEARTBEAT_INTERVAL = 30000 // 30秒发送一次心跳
const MAX_RECONNECT_TIMES = 3 // 最大重连次数
let reconnectCount = 0

// 保存用户信息
function saveUserInfo(userId, userType) {
  try {
    // 使用 ws_reconnect_info 而不是 info，避免覆盖登录用户信息
    uni.setStorageSync('ws_reconnect_info', {
      userId,
      userType,
      timestamp: Date.now()
    })
  } catch (error) {
    console.error('保存WebSocket用户信息失败:', error)
  }
}

// 获取保存的用户信息
function getSavedUserInfo() {
  try {
    return uni.getStorageSync('ws_reconnect_info')
  } catch (error) {
    console.error('获取WebSocket用户信息失败:', error)
    return null
  }
}

// 创建WebSocket连接
export function createWebSocket(userId, userType) {
  if (!userId || !userType) {
    console.error('缺少必要的用户信息')
    return null
  }

  if (socketTask) {
    console.log('WebSocket已存在')
    return socketTask
  }

  if (isConnecting) {
    console.log('WebSocket正在连接中')
    return null
  }

  // 保存用户信息用于重连
  saveUserInfo(userId, userType)
  isConnecting = true

  // 使用实际的服务器地址
  const baseUrl = 'ws://202.115.17.253:52531/mental'  // 保留端口52531
  const wsUrl = `${baseUrl}/api/chat/${userId}/${userType}`

  console.log('正在连接WebSocket:', wsUrl)

  try {
    socketTask = uni.connectSocket({
      url: wsUrl,
      complete: () => {
        isConnecting = false
      }
    })

    // 监听连接成功
    socketTask.onOpen(() => {
      console.log('WebSocket连接成功')
      reconnectCount = 0 // 重置重连次数
      startHeartbeat()
    })

    // 监听连接关闭
    socketTask.onClose(() => {
      console.log('WebSocket连接关闭')
      handleConnectionClose()
    })

    // 监听连接错误
    socketTask.onError((error) => {
      console.error('WebSocket错误:', error)
      handleConnectionError()
    })

    return socketTask
  } catch (error) {
    console.error('创建WebSocket失败:', error)
    isConnecting = false
    return null
  }
}

// 处理连接关闭
function handleConnectionClose() {
  clearHeartbeat()
  socketTask = null
  isConnecting = false

  // 判断是否需要重连
  if (reconnectCount < MAX_RECONNECT_TIMES) {
    reconnectCount++
    console.log(`准备第${reconnectCount}次重连`)
    if (reconnectTimer) clearTimeout(reconnectTimer)
    reconnectTimer = setTimeout(() => {
      console.log('尝试重新连接...')
      const savedInfo = getSavedUserInfo()
      if (savedInfo && savedInfo.userId && savedInfo.userType) {
        createWebSocket(savedInfo.userId, savedInfo.userType)
      }
    }, 3000 * reconnectCount) // 重连间隔随次数增加
  } else {
    console.log('达到最大重连次数，停止重连')
    // 通知UI层连接已断开
    uni.showToast({
      title: '连接已断开，请重新进入',
      icon: 'none',
      duration: 2000
    })
  }
}

// 处理连接错误
function handleConnectionError() {
  socketTask = null
  isConnecting = false
  clearHeartbeat()
}

// 发送消息
export function sendWebSocketMessage(message) {
  if (!socketTask) {
    console.error('WebSocket未连接')
    return false
  }

  return new Promise((resolve, reject) => {
    try {
      socketTask.send({
        data: JSON.stringify(message),
        success() {
          console.log('消息发送成功')
          resolve(true)
        },
        fail(error) {
          console.error('发送消息失败:', error)
          resolve(false)
        }
      })
    } catch (error) {
      console.error('发送消息失败:', error)
      resolve(false)
    }
  })
}

// 关闭WebSocket连接
export function closeWebSocket() {
  if (socketTask) {
    socketTask.close({
      success() {
        console.log('WebSocket关闭成功')
      },
      fail(error) {
        console.error('WebSocket关闭失败:', error)
      }
    })
    socketTask = null
  }
  clearHeartbeat()
  if (reconnectTimer) {
    clearTimeout(reconnectTimer)
    reconnectTimer = null
  }
}

// 心跳定时器
let heartbeatTimer = null

// 开始心跳
function startHeartbeat() {
  clearHeartbeat()
  heartbeatTimer = setInterval(() => {
    if (socketTask) {
      sendWebSocketMessage({ type: 'heartbeat' })
    }
  }, HEARTBEAT_INTERVAL)
}

// 清除心跳
function clearHeartbeat() {
  if (heartbeatTimer) {
    clearInterval(heartbeatTimer)
    heartbeatTimer = null
  }
}