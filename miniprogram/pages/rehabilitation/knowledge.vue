<template>
  <view class="container">
    <!-- Search Bar -->
    <view class="search-box">
      <input 
        type="text" 
        v-model="keyword"
        placeholder="搜索康复知识" 
        class="search-input"
        @input="handleSearch"
      />
    </view>

    <!-- Loading State -->
    <view class="loading-state" v-if="loading">
      <text>加载中...</text>
    </view>

    <!-- Knowledge List -->
    <view class="knowledge-list" v-else>
      <block v-if="knowledgeList && knowledgeList.length > 0">
        <view 
          class="knowledge-card" 
          v-for="item in knowledgeList" 
          :key="item.id"
          @click="viewDetail(item.id)"
        >
          <view class="card-header">
            <text class="title">{{ item.title }}</text>
            <text class="difficulty" v-if="item.difficultyLevel">
              难度: {{ getDifficultyText(item.difficultyLevel) }}
            </text>
          </view>
          
          <view class="card-content">
            <text class="content-preview">{{ getContentPreview(item.content) }}</text>
          </view>
          
          <view class="card-footer">
            <text class="conditions" v-if="item.applicableConditions">
              适用: {{ item.applicableConditions }}
            </text>
            <text class="duration" v-if="item.estimatedDuration">
              预计时长: {{ item.estimatedDuration }}分钟
            </text>
          </view>
        </view>
      </block>

      <!-- Empty State -->
      <view class="empty-state" v-else>
        <image src="/static/empty.png" class="empty-image" mode="aspectFit"></image>
        <text class="empty-text">{{ keyword ? '未找到相关康复知识' : '暂无康复知识' }}</text>
      </view>
    </view>

    <!-- Load More -->
    <view class="load-more" v-if="hasMore" @click="loadMore">
      <text>加载更多</text>
    </view>
  </view>
</template>

<script>
import { getRehabilitationList } from '@/api/rehabilitation'

export default {
  data() {
    return {
      keyword: '',
      pageNum: 1,
      pageSize: 10,
      total: 0,
      knowledgeList: [],
      hasMore: false,
      loading: false
    }
  },
  
  onLoad() {
    this.loadData()
  },
  
  methods: {
    async loadData(reset = false) {
      if (reset) {
        this.pageNum = 1
        this.knowledgeList = []
      }
      
      this.loading = true
      try {
        const params = {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          type: 2,
          keyword: this.keyword
        }
        
        const res = await getRehabilitationList(params)
        if (res.code === '200') {
          const { list, total } = res.data
          if (reset) {
            this.knowledgeList = list
          } else {
            this.knowledgeList = [...this.knowledgeList, ...list]
          }
          this.total = total
          this.hasMore = this.knowledgeList.length < total
        } else {
          uni.showToast({
            title: res.msg || '加载失败',
            icon: 'none'
          })
        }
      } catch (error) {
        uni.showToast({
          title: '加载失败',
          icon: 'none'
        })
      } finally {
        this.loading = false
      }
    },
    
    handleSearch() {
      this.loadData(true)
    },
    
    loadMore() {
      if (this.hasMore) {
        this.pageNum++
        this.loadData()
      }
    },
    
    viewDetail(id) {
      uni.navigateTo({
        url: '/pages/rehabilitation/detail?id=' + id
      })
    },
    
    getDifficultyText(level) {
      const levels = {
        1: '简单',
        2: '中等',
        3: '困难'
      }
      return levels[level] || '未知'
    },
    
    getContentPreview(content) {
      return content ? content.substring(0, 100) + (content.length > 100 ? '...' : '') : ''
    }
  }
}
</script>

<style>
.container {
  padding: 20rpx;
}

.search-box {
  padding: 20rpx;
  background-color: #fff;
  margin-bottom: 20rpx;
}

.search-input {
  background-color: #f5f5f5;
  padding: 20rpx;
  border-radius: 10rpx;
}

.knowledge-list {
  margin-top: 20rpx;
}

.knowledge-card {
  background-color: #fff;
  border-radius: 12rpx;
  padding: 20rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16rpx;
}

.title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
}

.difficulty {
  font-size: 24rpx;
  color: #666;
  background-color: #f5f5f5;
  padding: 4rpx 12rpx;
  border-radius: 6rpx;
}

.card-content {
  margin-bottom: 16rpx;
}

.content-preview {
  font-size: 28rpx;
  color: #666;
  line-height: 1.5;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  font-size: 24rpx;
  color: #999;
}

.load-more {
  text-align: center;
  padding: 20rpx;
  color: #666;
}

.loading-state {
  text-align: center;
  padding: 40rpx;
  color: #999;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60rpx 0;
}

.empty-image {
  width: 200rpx;
  height: 200rpx;
  margin-bottom: 20rpx;
}

.empty-text {
  font-size: 28rpx;
  color: #999;
}
</style> 