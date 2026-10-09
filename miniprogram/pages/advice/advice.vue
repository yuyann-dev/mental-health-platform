<template>
	<view class="advice-container">
		<view v-for="(item, index) in adviceList" :key="index" class="advice-item">
			<view class="advice-content">
				<text class="content-label">建议内容</text>
				<text class="content-text">{{ item.adviceContent }}</text>
			</view>
			<view class="advice-time">
				<text class="time-icon">⏰</text>
				<text class="time-text">{{ formatTime(item.adviceTime) }}</text>
			</view>
			<view class="decoration-dot"></view>
		</view>
	</view>
</template>

<script>
import { selectAllByMy } from '@/api/system/login'
import request from '@/utils/request'
export default {
		data() {
			return {
				adviceList: []
			}
		},
		onLoad() {
			this.checkLogin()
		},
		methods: {
		checkLogin() {
			const userStore = this.$store.state.user
			if (!userStore || !userStore.hasLogin) {
				uni.showToast({
					title: '请先登录',
					icon: 'none'
				})
				setTimeout(() => {
					uni.redirectTo({
						url: '/pages/login/login'
					})
				}, 1500)
				return
			}
			this.selectAllByMy()
		},
		selectAllByMy() {
			// 直接使用request调用API，确保路径正确
			request({
				url: '/advice/selectAllByMy',
				method: 'get'
			}).then(res => {
				console.log(res)
				this.adviceList = res.data || []
			}).catch(err => {
				console.error('获取建议失败:', err)
			})
		},
			formatTime(timeStr) {
				if (!timeStr) return ''
				return timeStr.split('.')[0].replace('T', ' ')
			}
		}
	}
</script>

<style>
.advice-container {
	padding: 30rpx;
	background: linear-gradient(135deg, #f5f6fa 0%, #ffffff 100%);
	min-height: 100vh;
	position: relative;
	overflow: hidden;
}

.advice-container::before {
	content: '';
	position: fixed;
	top: -100rpx;
	right: -100rpx;
	width: 400rpx;
	height: 400rpx;
	border-radius: 50%;
	background: linear-gradient(45deg, rgba(64, 169, 255, 0.1), rgba(64, 169, 255, 0.05));
	z-index: 1;
}

.advice-item {
	background-color: #ffffff;
	border-radius: 16rpx;
	padding: 30rpx;
	margin-bottom: 30rpx;
	box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
	transition: all 0.3s ease;
	border: 2rpx solid #f0f0f0;
	position: relative;
	overflow: hidden;
	z-index: 2;
}

.advice-item:hover {
	transform: translateY(-6rpx);
	box-shadow: 0 8rpx 24rpx rgba(0, 0, 0, 0.08);
}

.advice-item::before {
	content: '';
	position: absolute;
	left: 0;
	top: 0;
	width: 6rpx;
	height: 100%;
	background: linear-gradient(to bottom, #40a9ff, #1890ff);
	border-radius: 6rpx;
}

.advice-item::after {
	content: '';
	position: absolute;
	right: 30rpx;
	bottom: 30rpx;
	width: 120rpx;
	height: 120rpx;
	background: linear-gradient(45deg, rgba(24, 144, 255, 0.05), rgba(24, 144, 255, 0.02));
	border-radius: 50%;
	z-index: -1;
}

.decoration-dot {
	position: absolute;
	right: 20rpx;
	top: 20rpx;
	width: 16rpx;
	height: 16rpx;
	border-radius: 50%;
	background: linear-gradient(45deg, #40a9ff, #1890ff);
	opacity: 0.6;
}

.content-label {
	font-size: 24rpx;
	color: #999;
	margin-bottom: 10rpx;
	display: block;
	font-weight: 500;
	position: relative;
	padding-left: 20rpx;
}

.content-label::before {
	content: '';
	position: absolute;
	left: 0;
	top: 50%;
	transform: translateY(-50%);
	width: 8rpx;
	height: 8rpx;
	background: #1890ff;
	border-radius: 50%;
}

.content-text {
	font-size: 30rpx;
	color: #333;
	line-height: 1.6;
	display: block;
	word-break: break-all;
	padding: 20rpx;
	background: rgba(24, 144, 255, 0.02);
	border-radius: 8rpx;
}

.advice-time {
	display: flex;
	align-items: center;
	border-top: 2rpx solid #f5f5f5;
	padding-top: 20rpx;
	margin-top: 20rpx;
	position: relative;
}

.time-icon {
	font-size: 24rpx;
	color: #1890ff;
	margin-right: 10rpx;
}

.time-text {
	font-size: 24rpx;
	color: #999;
	background: linear-gradient(to right, #666, #999);
	-webkit-background-clip: text;
	color: transparent;
}

@keyframes float {
	0% { transform: translateY(0); }
	50% { transform: translateY(-6rpx); }
	100% { transform: translateY(0); }
}

.decoration-dot {
	animation: float 3s ease-in-out infinite;
}

@font-face {
	font-family: "iconfont";
	src: url('//at.alicdn.com/t/font_2385839_1234567.ttf') format('truetype');
}
</style>
