<template>
	<view class="container">
		<view class="article-card">
			<view class="article-header">
				<text class="article-title">{{ article.title }}</text>
				<text class="article-date">{{ article.createTime }}</text>
			</view>
			
			<view class="article-content">
				<rich-text :nodes="article.content"></rich-text>
			</view>
		</view>
	</view>
</template>

<script>
	import request from '@/utils/request'
	import { toast } from '@/utils/common'
	
	export default {
		data() {
			return {
				id: '',
				article: {}
			}
		},
		onLoad(options) {
			if (options.id) {
				this.id = options.id
				this.getDetail()
			}
		},
		methods: {
			async getDetail() {
				try {
					const res = await request({
						url: `/health/article/${this.id}`,
						method: 'get'
					})
					if (res.code === 200 || res.code === '200') {
						this.article = res.data
						// Handle potential image paths in rich text if needed
						// For now assuming full paths or relative paths handled by base URL
						if (this.article.content) {
							// Replace relative paths with absolute paths if necessary
							// This depends on how WangEditor saves images. 
							// If it saves as /files/download/..., we might need to prepend base URL if not handled by rich-text
							// But usually rich-text handles relative paths if base URL is set? No, uniapp rich-text needs absolute URLs usually.
							// Let's check request.js config.baseUrl.
							// Actually, let's just assume the backend returns full URLs or we replace them.
							// WangEditor upload returns full URL in our backend implementation: fileBaseUrl + ...
							// So it should be fine.
						}
					} else {
						toast(res.msg || '获取文章详情失败')
					}
				} catch (e) {
					console.error(e)
					toast('获取文章详情失败')
				}
			}
		}
	}
</script>

<style>
	.container {
		padding: 30rpx;
		background-color: #f8f9fa;
		min-height: 100vh;
	}
	
	.article-card {
		background-color: #ffffff;
		border-radius: 20rpx;
		padding: 40rpx;
		box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.05);
	}
	
	.article-header {
		margin-bottom: 40rpx;
		text-align: center;
		border-bottom: 2rpx solid #f0f0f0;
		padding-bottom: 30rpx;
	}
	
	.article-title {
		font-size: 40rpx;
		font-weight: bold;
		color: #333;
		margin-bottom: 16rpx;
		display: block;
	}
	
	.article-date {
		font-size: 24rpx;
		color: #999;
	}
	
	.article-content {
		font-size: 30rpx;
		color: #333;
		line-height: 1.8;
	}
	
	/* Rich text image style */
	.article-content img {
		max-width: 100%;
		height: auto;
		border-radius: 8rpx;
		margin: 20rpx 0;
	}
</style>
