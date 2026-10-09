<template>
	<view class="result-container">
		<view class="result-card">
			<view class="result-header">
				<text class="complete-text">测评完成</text>
			</view>
			
			<view class="score-section">
				<view class="score-circle">
					<text class="score-number">{{score}}</text>
					<text class="score-label">总分</text>
				</view>
				
				<view class="score-analysis">
					<text class="analysis-title">测评结果</text>
					<text class="analysis-text">{{getAnalysis()}}</text>
				</view>
			</view>
		</view>
		
		<view class="action-buttons">
			<button class="secondary-btn" @click="backToList">返回测评列表</button>
		</view>
	</view>
</template>

<script>
	import { getDoctorAdvice } from '@/api/system/test.js'
	import { toast } from '@/utils/common'
	
	export default {
		data() {
			return {
				typeId: '',
				typeName: '',
				score: 0,
				level: '',  // 后端返回的结果等级
				analysis: '',  // 后端返回的AI分析
				doctorAdvice: null
			}
		},
		onLoad(options) {
			this.typeId = options.typeId
			this.typeName = options.typeName
			this.score = parseInt(options.score) || 0
			this.level = decodeURIComponent(options.level || '')
			this.analysis = decodeURIComponent(options.analysis || '')
			
			console.log('测试结果页面接收参数：', {
				typeName: this.typeName,
				score: this.score,
				level: this.level,
				analysis: this.analysis
			})
		},
		onShow() {
			this.getDoctorAdvice()
		},
		methods: {
			getAnalysis() {
				// 优先返回后端的level，如果没有则返回analysis，都没有才用默认值
				if (this.level) {
					return this.level
				} else if (this.analysis) {
					return this.analysis
				} else {
					return '测评完成'
				}
			},
			async getDoctorAdvice() {
				// 不再获取医生建议
				this.doctorAdvice = null
			},
			goToAdvice() {
				uni.switchTab({
					url: '/pages/advice/advice'
				})
			},
			backToList() {
				uni.switchTab({
					url: '/pages/test/test'
				})
			}
		}
	}
</script>

<style lang="scss">
.result-container {
	min-height: 100vh;
	background: #f8f8f8;
	padding: 30rpx;
	
	.result-card {
		background: #fff;
		border-radius: 20rpx;
		padding: 40rpx;
		margin-bottom: 40rpx;
		box-shadow: 0 2rpx 12rpx rgba(0,0,0,0.05);
		
		.result-header {
			margin-bottom: 40rpx;
			
			.complete-text {
				font-size: 28rpx;
				color: #007AFF;
			}
		}
		
		.score-section {
			display: flex;
			align-items: center;
			
			.score-circle {
				width: 160rpx;
				height: 160rpx;
				border-radius: 80rpx;
				background: #007AFF;
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;
				margin-right: 40rpx;
				
				.score-number {
					font-size: 48rpx;
					font-weight: bold;
					color: #fff;
				}
				
				.score-label {
					font-size: 24rpx;
					color: rgba(255,255,255,0.8);
					margin-top: 4rpx;
				}
			}
			
			.score-analysis {
				flex: 1;
				
				.analysis-title {
					font-size: 32rpx;
					font-weight: bold;
					color: #333;
					margin-bottom: 20rpx;
					display: block;
				}
				
				.analysis-text {
					font-size: 28rpx;
					color: #666;
					line-height: 1.6;
				}
			}
		}
	}
	
	.action-buttons {
		.secondary-btn {
			width: 100%;
			height: 88rpx;
			line-height: 88rpx;
			background: #fff;
			color: #007AFF;
			border: 2rpx solid #007AFF;
			border-radius: 44rpx;
			font-size: 32rpx;
			
			&:active {
				opacity: 0.8;
			}
		}
	}
}
</style> 