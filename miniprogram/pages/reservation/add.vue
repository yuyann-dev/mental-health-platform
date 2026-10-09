<template>
  <view class="container">
    <view class="form-item">
      <text class="label">预约医生</text>
      <text class="value">{{ doctorName }}</text>
    </view>
    <view class="form-item">
      <text class="label">预约日期</text>
      <picker mode="date" :value="date" start="2020-01-01" end="2030-12-31" @change="bindDateChange">
        <view class="picker">
          {{ date || '请选择日期' }}
        </view>
      </picker>
    </view>
    <view class="form-item">
      <text class="label">开始时间</text>
      <picker mode="time" :value="startTime" start="09:00" end="18:00" @change="bindStartTimeChange">
        <view class="picker">
          {{ startTime || '请选择时间' }}
        </view>
      </picker>
    </view>
    <view class="form-item">
      <text class="label">结束时间</text>
      <picker mode="time" :value="endTime" start="09:00" end="18:00" @change="bindEndTimeChange">
        <view class="picker">
          {{ endTime || '请选择时间' }}
        </view>
      </picker>
    </view>
    <view class="form-item block">
      <text class="label">问题描述</text>
      <textarea v-model="question" placeholder="请输入您的问题描述" class="textarea" />
    </view>
    
    <button type="primary" class="submit-btn" @click="submit">提交预约</button>
  </view>
</template>

<script>
import request from '@/utils/request'
import { toast } from '@/utils/common'

export default {
  data() {
    return {
      doctorId: '',
      doctorName: '',
      date: '',
      startTime: '',
      endTime: '',
      question: ''
    }
  },
  onLoad(options) {
    this.doctorId = options.doctorId
    this.doctorName = options.doctorName
  },
  methods: {
    bindDateChange(e) {
      this.date = e.detail.value
    },
    bindStartTimeChange(e) {
      this.startTime = e.detail.value
    },
    bindEndTimeChange(e) {
      this.endTime = e.detail.value
    },
    submit() {
      if (!this.date || !this.startTime || !this.endTime || !this.question) {
        toast('请填写完整信息')
        return
      }
      
      const start = `${this.date} ${this.startTime}:00`
      const end = `${this.date} ${this.endTime}:00`
      
      request({
        url: '/reservation/add',
        method: 'POST',
        data: {
          doctorId: this.doctorId,
          timeRange: [start, end],
          question: this.question
        }
      }).then(res => {
        if (res.code === '200') {
          toast('预约成功')
          setTimeout(() => {
            uni.navigateBack()
          }, 1500)
        } else {
          toast(res.msg || '预约失败')
        }
      })
    }
  }
}
</script>

<style>
.container {
  padding: 30rpx;
  background-color: #fff;
  min-height: 100vh;
}
.form-item {
  display: flex;
  align-items: center;
  padding: 30rpx 0;
  border-bottom: 1rpx solid #eee;
}
.form-item.block {
  display: block;
}
.label {
  width: 180rpx;
  color: #333;
  font-size: 30rpx;
}
.value {
  flex: 1;
  color: #666;
  font-size: 30rpx;
}
.picker {
  flex: 1;
  color: #666;
  font-size: 30rpx;
}
.textarea {
  width: 100%;
  height: 200rpx;
  background: #f8f8f8;
  padding: 20rpx;
  margin-top: 20rpx;
  box-sizing: border-box;
  border-radius: 8rpx;
  font-size: 28rpx;
}
.submit-btn {
  margin-top: 60rpx;
  background-color: #007AFF;
}
</style>
