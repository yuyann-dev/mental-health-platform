<template>
  <view class="container">
    <!-- 搜索框 -->
    <view class="search-box">
      <input 
        type="text" 
        v-model="searchKey" 
        placeholder="搜索医生姓名"
        @input="onSearch"
      />
    </view>

    <!-- 医生列表 -->
    <scroll-view scroll-y class="doctor-list" refresher-enabled @refresherrefresh="loadDoctors">
      <view 
        class="doctor-item" 
        v-for="doctor in doctorList" 
        :key="doctor.id"
        @click="selectDoctor(doctor)"
      >
        <image 
          class="avatar"
          :src="doctor.avatar"
          mode="aspectFill"
        />
        <view class="info">
          <view class="name-row">
            <text class="name">{{ doctor.name }}</text>
            <text class="phone">{{ doctor.phone }}</text>
          </view>
          <view class="detail-row">
            <text class="email">{{ doctor.email }}</text>
            <button class="reserve-btn" size="mini" type="primary" @click.stop="goToReservation(doctor)">预约</button>
          </view>
        </view>
      </view>
    </scroll-view>

    <!-- 空状态 -->
    <view class="empty-state" v-if="!loading && (!doctorList || doctorList.length === 0)">
      <image src="/static/empty-chat.png" mode="aspectFit"></image>
      <text>暂无可选医生</text>
    </view>

    <!-- 加载状态 -->
    <view class="loading-state" v-if="loading">
      <text>加载中...</text>
    </view>
  </view>
</template>

<script>
import { getDoctorList } from '@/api/doctor'

export default {
  data() {
    return {
      doctorList: [],
      loading: false,
      searchKey: ''
    }
  },

  onLoad() {
    this.loadDoctors()
  },

  methods: {
    async loadDoctors() {
      this.loading = true
      try {
        const params = {}
        if (this.searchKey) {
          params.name = this.searchKey
        }
        
        const res = await getDoctorList(params)
        console.log('获取医生列表结果:', res)
        
        if (res.code === '200') {
          this.doctorList = res.data
          console.log('医生列表数据:', this.doctorList)
        } else {
          console.error('获取医生列表失败:', res.msg)
          uni.showToast({
            title: res.msg || '获取医生列表失败',
            icon: 'none'
          })
        }
      } catch (error) {
        console.error('加载医生列表失败:', error)
        uni.showToast({
          title: '加载失败',
          icon: 'none'
        })
      } finally {
        this.loading = false
        uni.stopPullDownRefresh()
      }
    },

    onSearch() {
      clearTimeout(this._searchTimer)
      this._searchTimer = setTimeout(() => {
        this.loadDoctors()
      }, 300)
    },

    goToReservation(doctor) {
      uni.navigateTo({
        url: `/pages/reservation/add?doctorId=${doctor.id}&doctorName=${encodeURIComponent(doctor.name)}`
      })
    },

    selectDoctor(doctor) {
      console.log('选择医生:', doctor)
      
      const params = {
        targetId: doctor.id,
        targetType: 2,
        targetName: doctor.name,
        targetAvatar: doctor.avatar || ''
      }
      
      const query = Object.keys(params)
        .map(key => `${key}=${encodeURIComponent(params[key])}`)
        .join('&')
      
      try {
        const url = `/pages/chat/chat-detail?${query}`
        console.log('导航URL:', url)
        
        const pages = getCurrentPages()
        const currentPage = pages[pages.length - 1]
        
        if (currentPage && currentPage.route === 'pages/chat/chat-detail') {
          uni.redirectTo({
            url,
            success: () => { console.log('重定向成功') },
            fail: (error) => {
              console.error('重定向失败:', error)
              uni.navigateBack({ delta: 1 })
            }
          })
        } else {
          uni.navigateTo({
            url,
            success: () => { console.log('导航成功') },
            fail: (error) => {
              console.error('导航失败:', error)
              uni.showToast({ title: '进入聊天失败，请重试', icon: 'none' })
            }
          })
        }
      } catch (error) {
        console.error('导航异常:', error)
        uni.showToast({ title: '系统异常，请重试', icon: 'none' })
      }
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
}

.search-box {
  padding: 20rpx 30rpx;
  background-color: #fff;
}

.search-box input {
  height: 72rpx;
  background-color: #f5f5f5;
  border-radius: 36rpx;
  padding: 0 30rpx;
  font-size: 28rpx;
}

.doctor-list {
  flex: 1;
}

.doctor-item {
  display: flex;
  align-items: center;
  padding: 30rpx;
  background-color: #fff;
  border-bottom: 1rpx solid #eee;
}

.avatar {
  width: 96rpx;
  height: 96rpx;
  border-radius: 48rpx;
  margin-right: 24rpx;
  background-color: #f0f0f0;
}

.info {
  flex: 1;
}

.name-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12rpx;
}

.name {
  font-size: 32rpx;
  color: #333;
  font-weight: 500;
}

.phone {
  font-size: 28rpx;
  color: #666;
}

.detail-row {
  display: flex;
  align-items: center;
}

.email {
  font-size: 26rpx;
  color: #999;
  flex: 1;
}

.reserve-btn {
  margin-left: 20rpx;
  font-size: 24rpx;
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