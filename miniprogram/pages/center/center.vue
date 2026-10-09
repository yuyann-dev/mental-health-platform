<template>
	<view class="mypage-container">
		<!-- 用户信息区域 -->
		<view class="user-info-section">
			<view class="avatar-box">
				<image class="avatar" :src="avatarUrl"></image>
				<view @click="goLogin" class="name">
					<text style="font-size: 40px;" v-if="userInfo.name">{{ userInfo.name }}</text>
					<text v-else>登录/注册</text>
				</view>
			</view>
		</view>

		<!-- 列表菜单 -->
		<view class="list-menu">
			<view class="list-item" @click="logout">
				<view class="item-left">
					<uni-icons type="closeempty" size="20" color="#e74c3c"></uni-icons>
					<text>退出登录</text>
				</view>
				<uni-icons type="right" size="16" color="#999"></uni-icons>
			</view>
		</view>
	</view>
</template>

<script>
import { getFileUrl } from '@/utils/common'

export default {
	components: {
		uniIcons: () => import('@/uni_modules/uni-icons/components/uni-icons/uni-icons.vue')
	},
	data() {
		return {
			title: 'Hello'
		}
	},
	computed: {
		avatarUrl() {
			return getFileUrl(this.userInfo.avatar) || '../../static/patient-avatar.svg'
		},
		userInfo() {
			// 从store获取用户信息,确保与其他页面一致
			return this.$store.state.user || {}
		}
	},
	onShow() {
		console.log('个人中心onShow, 用户信息:', this.userInfo)
		// 强制更新视图
		this.$forceUpdate()
	},
	methods: {
		goLogin() {
			uni.navigateTo({
				url: "/pages/login/login"
			})
		},
		logout() {
			try {
				// 清除store
				this.$store.dispatch('LogOut')
				// 清除所有存储
				uni.clearStorageSync()
				uni.showToast({
					title: '退出成功',
					icon: 'success',
					duration: 1500
				})
				// 延迟跳转
				setTimeout(() => {
					uni.reLaunch({
						url: '/pages/login/login'
					})
				}, 1500)
			} catch(e) {
				console.error('退出登录失败：', e)
				uni.showToast({
					title: '退出失败，请重试',
					icon: 'none',
					duration: 2000
				})
			}
		}
	}
}
</script>

<style>
.mypage-container {
	min-height: 100vh;
	background-color: #f5f6fa;
}

.user-info-section {
	background: #2b81e2;
	height: 300rpx;
	padding: 40rpx;
	position: relative;
}

.avatar-box {
	display: flex;
	flex-direction: column;
	align-items: center;
	padding-top: 40rpx;
}

.avatar {
	width: 140rpx;
	height: 140rpx;
	border-radius: 50%;
	border: 4rpx solid rgba(255, 255, 255, 0.3);
}

.name {
	color: #fff;
	font-size: 32rpx;
	margin-top: 20rpx;
}

.list-menu {
	margin: 20rpx;
	background: #fff;
	border-radius: 16rpx;
	padding: 0 30rpx;
}

.list-item {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 30rpx 0;
	border-bottom: 1rpx solid #eee;
}

.list-item:last-child {
	border-bottom: none;
}

.item-left {
	display: flex;
	align-items: center;
	gap: 20rpx;
}

.item-left text {
	font-size: 28rpx;
	color: #333;
}
</style>