<template>
	<view class="container">
		<view class="upload-area" @click="chooseImage" v-if="!imageUrl">
			<view class="icon-plus">+</view>
			<text>点击上传fMRI图像</text>
		</view>
		<view class="preview-area" v-else>
			<image v-if="!isTif(imageUrl)" :src="imageUrl" mode="aspectFit" class="preview-image"></image>
			<view v-else class="preview-image tif-placeholder">
				<text>TIF 格式</text>
				<text class="sub-text">暂不支持预览</text>
			</view>
			<view class="re-upload" @click="chooseImage">重新上传</view>
		</view>

		<view class="btn-area">
			<button type="primary" :loading="loading" @click="uploadImage" :disabled="!imageUrl || loading">开始识别</button>
		</view>

		<view class="result-area" v-if="result">
			<view class="result-title">识别结果</view>
			<view class="result-item">
				<text class="label">预测类别：</text>
				<text class="value highlight">{{ result.prediction }}</text>
			</view>
			<view class="result-item">
				<text class="label">置信度：</text>
				<text class="value">{{ (result.confidence * 100).toFixed(2) }}%</text>
			</view>
			<view class="result-item">
				<text class="label">详细概率：</text>
				<text class="value">{{ formatProbabilities(result.probabilities) }}</text>
			</view>
		</view>
		
		<view class="history-link" @click="goToHistory">
			查看历史记录 >
		</view>
	</view>
</template>

<script>
	import upload from '@/utils/upload'
	import { toast, formatProbabilities, getFileUrl } from '@/utils/common'
	
	export default {
		data() {
			return {
				imageUrl: '',
				tempFilePath: '',
				loading: false,
				result: null
			}
		},
		methods: {
            formatProbabilities,
			chooseImage() {
				uni.chooseImage({
					count: 1,
					sizeType: ['original', 'compressed'],
					sourceType: ['album', 'camera'],
					success: (res) => {
						this.tempFilePath = res.tempFilePaths[0]
						this.result = null
                        
                        if (this.isTif(this.tempFilePath)) {
                            this.uploadForPreview(this.tempFilePath)
                        } else {
                            this.imageUrl = this.tempFilePath
                        }
					}
				})
			},
            uploadForPreview(filePath) {
                uni.showLoading({ title: '生成预览...' })
                upload({
                    url: '/files/upload',
                    filePath: filePath,
                    name: 'file'
                }).then(res => {
                    uni.hideLoading()
                    // Check code loosely
                    if (res.code == 200) {
                        this.imageUrl = getFileUrl(res.data)
                    } else {
                        toast('预览生成失败')
                        this.imageUrl = filePath
                    }
                }).catch(e => {
                    uni.hideLoading()
                    console.error(e)
                    toast('预览生成失败')
                    this.imageUrl = filePath
                })
            },
			isTif(url) {
				if (!url) return false
				return url.toLowerCase().endsWith('.tif') || url.toLowerCase().endsWith('.tiff')
			},
			async uploadImage() {
				if (!this.tempFilePath) return
				
				this.loading = true
				try {
					const userId = this.$store.getters.userId
					if (!userId) {
						toast('请先登录')
						return
					}
					
					const res = await upload({
						url: '/fmri/predict',
						filePath: this.tempFilePath,
						name: 'file',
						formData: {
							userId: userId
						}
					})
					
					if (res.code === 200 || res.code === '200') {
						this.result = res.data
						toast('识别成功')
					} else {
						toast(res.msg || '识别失败')
					}
				} catch (e) {
					console.error(e)
					toast('上传失败')
				} finally {
					this.loading = false
				}
			},
			goToHistory() {
				uni.navigateTo({
					url: '/pages/test/fmri-history'
				})
			}
		}
	}
</script>

<style lang="scss">
	.container {
		padding: 30rpx;
        background-color: #F8F8F8;
        min-height: 100vh;
	}
	
	.upload-area {
		width: 100%;
		height: 400rpx;
		background-color: #f8f8f8;
		border: 2rpx dashed #ddd;
		border-radius: 12rpx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		margin-bottom: 30rpx;
		
		.icon-plus {
			font-size: 80rpx;
			color: #999;
			margin-bottom: 20rpx;
		}
		
		text {
			color: #666;
			font-size: 28rpx;
		}
	}
	
	.preview-area {
		width: 100%;
		margin-bottom: 30rpx;
		text-align: center;
		
		.preview-image {
			width: 100%;
			height: 400rpx;
			border-radius: 12rpx;
			
			&.tif-placeholder {
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: center;
				background-color: #f0f0f0;
				border: 1px solid #eee;
				color: #666;
				font-size: 32rpx;
				font-weight: bold;
				
				.sub-text {
					font-size: 24rpx;
					font-weight: normal;
					margin-top: 10rpx;
					color: #999;
				}
			}
		}
		
		.re-upload {
			margin-top: 20rpx;
			color: #007AFF;
			font-size: 28rpx;
		}
	}
	
	.btn-area {
		margin-bottom: 50rpx;
	}
	
	.result-area {
		background-color: #fff;
		padding: 30rpx;
		border-radius: 12rpx;
		box-shadow: 0 2rpx 12rpx rgba(0,0,0,0.05);
		margin-bottom: 30rpx;
		
		.result-title {
			font-size: 32rpx;
			font-weight: bold;
			margin-bottom: 20rpx;
			text-align: center;
		}
		
		.result-item {
			display: flex;
			margin-bottom: 15rpx;
			font-size: 28rpx;
			
			.label {
				color: #666;
				width: 160rpx;
				flex-shrink: 0;
			}
			
			.value {
				flex: 1;
				color: #333;
				word-break: break-all;
				
				&.highlight {
					color: #007AFF;
					font-weight: bold;
				}
			}
		}
	}
	
	.history-link {
		text-align: center;
		color: #666;
		font-size: 28rpx;
		margin-top: 40rpx;
	}
</style>
