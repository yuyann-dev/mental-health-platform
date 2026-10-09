<template>
  <view class="container">
    <view class="detail-card">
      <!-- Title Section -->
      <view class="title-section">
        <text class="title">{{ detail.title }}</text>
        <view class="meta-info">
          <text class="difficulty" v-if="detail.difficultyLevel">
            难度: {{ getDifficultyText(detail.difficultyLevel) }}
          </text>
          <text class="duration" v-if="detail.estimatedDuration">
            时长: {{ detail.estimatedDuration }}分钟
          </text>
        </view>
      </view>

      <!-- Content Section -->
      <view class="content-section">
        <text class="content">{{ detail.content }}</text>
      </view>

      <!-- Additional Info -->
      <view class="info-section" v-if="detail.applicableConditions">
        <view class="info-item">
          <text class="info-label">适用症状/疾病:</text>
          <text class="info-value">{{ detail.applicableConditions }}</text>
        </view>
      </view>

      <!-- Precautions -->
      <view class="precautions-section" v-if="detail.precautions">
        <text class="section-title">注意事项</text>
        <text class="precautions">{{ detail.precautions }}</text>
      </view>
    </view>
  </view>
</template>

<script>
import { getRehabilitationDetail } from '@/api/rehabilitation'

export default {
  data() {
    return {
      id: '',
      detail: {}
    }
  },

  onLoad(options) {
    if (options.id) {
      this.id = options.id
      this.loadDetail()
    }
  },

  methods: {
    async loadDetail() {
      try {
        const res = await getRehabilitationDetail(this.id)
        if (res.code === '200') {
          this.detail = res.data
        }
      } catch (error) {
        uni.showToast({
          title: '加载失败',
          icon: 'none'
        })
      }
    },

    getDifficultyText(level) {
      const levels = {
        1: '简单',
        2: '中等',
        3: '困难'
      }
      return levels[level] || '未知'
    }
  }
}
</script>

<style>
.container {
  padding: 20rpx;
}

.detail-card {
  background-color: #fff;
  border-radius: 12rpx;
  padding: 30rpx;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.title-section {
  margin-bottom: 30rpx;
}

.title {
  font-size: 36rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 16rpx;
}

.meta-info {
  display: flex;
  gap: 20rpx;
}

.difficulty,
.duration {
  font-size: 24rpx;
  color: #666;
  background-color: #f5f5f5;
  padding: 4rpx 12rpx;
  border-radius: 6rpx;
}

.content-section {
  margin-bottom: 30rpx;
}

.content {
  font-size: 30rpx;
  color: #333;
  line-height: 1.6;
  white-space: pre-wrap;
}

.info-section {
  margin-bottom: 30rpx;
  padding: 20rpx;
  background-color: #f8f9fa;
  border-radius: 8rpx;
}

.info-item {
  display: flex;
  align-items: flex-start;
  margin-bottom: 10rpx;
}

.info-label {
  font-size: 28rpx;
  color: #666;
  margin-right: 10rpx;
  white-space: nowrap;
}

.info-value {
  font-size: 28rpx;
  color: #333;
  flex: 1;
}

.precautions-section {
  margin-top: 30rpx;
  padding-top: 30rpx;
  border-top: 2rpx solid #eee;
}

.section-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 16rpx;
}

.precautions {
  font-size: 28rpx;
  color: #666;
  line-height: 1.6;
  white-space: pre-wrap;
}
</style> 