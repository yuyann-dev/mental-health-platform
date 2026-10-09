<template>
	<view class="container">
		<!-- 轮播图 -->
		<view class="banner-section">
			<swiper class="banner-swiper" :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000"
				indicator-color="rgba(255, 255, 255, 0.6)" indicator-active-color="#ffffff">
				<swiper-item v-for="(item, index) in bannerList" :key="index" @click="handleBannerClick(item)">
					<image :src="item.imageUrl" class="swiper-img" mode="aspectFill" />
				</swiper-item>
			</swiper>
		</view>

		<!--文章列表-->
		<view class="article-list">
			<view class="article-item" v-for="(item, index) in articleList" :key="index" @click="goToDetail(item)">
				<image :src="item.cover" mode="aspectFill" style="width: 100%; height: 300rpx; border-radius: 12rpx; margin-bottom: 20rpx;" v-if="item.cover"></image>
				<view class="article-content">
					<text class="article-title">{{ item.title }}</text>
				</view>
				<view class="article-meta">
					<text class="read-time">{{ item.createTime }}</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import request from '@/utils/request'
	import { toast } from '@/utils/common'
	import config from '@/config'

	export default {
		data() {
			return {
				bannerList: [],
				articleList: [],
				baseUrl: config.url // http://202.115.17.253:52531/mental/files
			};
		},
		onShow() {
			console.log('首页onShow触发')
			this.getHomeData()
		},
		methods: {
			async getHomeData() {
				try {
					console.log('开始获取首页数据')
					// Get Carousels
					const bannerRes = await request({
						url: '/health/carousel/all',
						method: 'get',
						headers: { isToken: false }
					})
					console.log('轮播图数据:', bannerRes)
					if (bannerRes.code === 200 || bannerRes.code === '200') {
						// 处理图片URL - 拼接完整路径
						this.bannerList = (bannerRes.data || []).map(item => ({
							...item,
							imageUrl: item.imageUrl.startsWith('http') ? item.imageUrl : this.baseUrl + item.imageUrl
						}))
						console.log('处理后的轮播图:', this.bannerList)
					}
					
					// Get Articles
					const articleRes = await request({
						url: '/health/article/all',
						method: 'get',
						headers: { isToken: false }
					})
					console.log('文章数据:', articleRes)
					if (articleRes.code === 200 || articleRes.code === '200') {
						// 处理图片URL - 拼接完整路径
						this.articleList = (articleRes.data || []).map(item => ({
							...item,
							cover: item.cover && !item.cover.startsWith('http') ? this.baseUrl + item.cover : item.cover
						}))
						console.log('处理后的文章:', this.articleList)
					}
				} catch (e) {
					console.error('获取首页数据失败:', e)
					toast('获取首页数据失败')
				}
			},
			goToDetail(item) {
				uni.navigateTo({
					url: `/pages/index/article-detail?id=${item.id}`
				})
			},
			handleBannerClick(item) {
				if (item.targetUrl) {
					if (item.targetUrl.startsWith('http')) {
						// External link
					} else {
						uni.navigateTo({
							url: item.targetUrl
						})
					}
				}
			}
		}
	};
</script>

<style scoped>
	.container {
		background: linear-gradient(to bottom right, #eef2ff, #ffffff);
		min-height: 100vh;
		padding-bottom: 40rpx;
	}

	.banner-section {
		width: 92%;
		height: 360rpx;
		position: relative;
		margin: 0 auto;
		border-radius: 16rpx;
		overflow: hidden;
		box-shadow: 0 8rpx 24rpx rgba(0, 0, 0, 0.06);
		margin-bottom: 40rpx;
	}

	.banner-swiper {
		width: 100%;
		height: 100%;
	}

	.swiper-img {
		width: 100%;
		height: 100%;
		object-fit: cover;
	}

	.article-list {
		padding: 0 30rpx;
	}

	.article-item {
		background-color: #ffffff;
		border-radius: 16rpx;
		padding: 30rpx;
		margin-bottom: 30rpx;
		box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
		transition: all 0.3s ease;
	}

	.article-item:active {
		transform: scale(0.98);
		box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.05);
	}

	.article-content {
		margin-bottom: 20rpx;
	}

	.article-title {
		font-size: 34rpx;
		font-weight: bold;
		color: #333;
		margin-bottom: 12rpx;
		display: block;
	}

	.article-meta {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-top: 20rpx;
		padding-top: 20rpx;
		border-top: 2rpx solid #f5f5f5;
	}

	.read-time {
		font-size: 24rpx;
		color: #999;
	}
</style>