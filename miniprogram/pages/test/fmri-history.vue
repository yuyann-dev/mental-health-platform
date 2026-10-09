<template>
	<view class="container">
		<view class="history-list">
			<view class="history-item" v-for="(item, index) in list" :key="index">
				<view class="img-wrapper">
					<image v-if="!isTif(item.imageUrl)" :src="getImageUrl(item.imageUrl)" mode="aspectFill" class="item-img" @error="handleImageError(index)"></image>
					<view v-else class="item-img tif-placeholder">
						<text>TIF</text>
						<text class="sub-text">不支持预览</text>
					</view>
				</view>
				<view class="item-info">
					<view class="item-row">
						<text class="label">预测结果：</text>
						<text class="value highlight">{{ item.prediction }}</text>
					</view>
					<view class="item-row">
						<text class="label">置信度：</text>
						<text class="value">{{ (item.confidence * 100).toFixed(2) }}%</text>
					</view>
					<view class="item-row">
						<text class="label">详细概率：</text>
						<text class="value">{{ formatProbabilities(item.probabilities) }}</text>
					</view>
					<view class="item-row">
						<text class="label">时间：</text>
						<text class="value">{{ item.createTime }}</text>
					</view>
				</view>
			</view>
			<view v-if="list.length === 0" class="empty-tip">暂无记录</view>
		</view>
	</view>
</template>

<script>
	import request from '@/utils/request'
	import { toast, formatProbabilities } from '@/utils/common'
	import config from '@/config'
	
	export default {
		data() {
			return {
				list: []
			}
		},
		onShow() {
			this.getList()
		},
		methods: {
            formatProbabilities,
			async getList() {
				try {
					const userId = this.$store.getters.userId
					if (!userId) return
					
					const res = await request({
						url: '/fmri/history',
						method: 'get',
						params: {
							userId: userId
						}
					})
					
					if (res.code === 200 || res.code === '200') {
						this.list = res.data
					}
				} catch (e) {
					console.error(e)
					toast('获取历史记录失败')
				}
			},
			getImageUrl(url) {
				if (!url) return '/static/errorImage.jpg'
				if (url.startsWith('http') || url.startsWith('wxfile') || url.startsWith('file')) {
					return url
				}
				return config.url + url
			},
			isTif(url) {
				if (!url) return false
				return url.toLowerCase().endsWith('.tif') || url.toLowerCase().endsWith('.tiff')
			},
			handleImageError(index) {
				this.list[index].imageUrl = '' // Will trigger default error image if we had one, or just blank
			}
		}
	}
</script>

<style lang="scss">
	.container {
		padding: 20rpx;
		background-color: #f5f5f5;
		min-height: 100vh;
	}
	
	.history-item {
		background-color: #fff;
		border-radius: 12rpx;
		padding: 20rpx;
		margin-bottom: 20rpx;
		display: flex;
		align-items: center;
		
		.img-wrapper {
			width: 160rpx;
			height: 160rpx;
			margin-right: 20rpx;
			flex-shrink: 0;
		}
		
		.item-img {
			width: 100%;
			height: 100%;
			border-radius: 8rpx;
			background-color: #eee;
			
			&.tif-placeholder {
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: center;
				background-color: #e0e0e0;
				color: #666;
				font-size: 32rpx;
				font-weight: bold;
				
				.sub-text {
					font-size: 20rpx;
					font-weight: normal;
					margin-top: 5rpx;
				}
			}
		}
		
		.item-info {
			flex: 1;
			display: flex;
			flex-direction: column;
			justify-content: space-between;
			height: 160rpx;
			padding: 5rpx 0;
			
			.item-row {
				display: flex;
				align-items: center;
				font-size: 26rpx;
				
				.label {
					color: #999;
					margin-right: 10rpx;
					width: 140rpx;
					flex-shrink: 0;
				}
				
				.value {
					color: #333;
					flex: 1;
					word-break: break-all;
					
					&.highlight {
						color: #007AFF;
						font-weight: bold;
						font-size: 30rpx;
					}
				}
			}
		}
	}
	
	.empty-tip {
		text-align: center;
		color: #999;
		margin-top: 100rpx;
		font-size: 28rpx;
	}
</style>
