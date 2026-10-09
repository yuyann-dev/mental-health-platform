<template>
	<view class="test-container">
		<view class="test-header">
			<text class="header-title">心理评测</text>
			<text class="header-desc">专业的心理评测帮助您更好地了解自己</text>
		</view>
		
		<view class="test-list">
			<!-- fMRI Entry -->
			<view class="test-item" @click="goToFmri">
				<view class="item-left">
					<text class="item-title">fMRI图像识别</text>
					<text class="item-desc">上传脑部fMRI图像，AI辅助诊断</text>
					<view class="item-info">
						<text class="info-text">AI智能分析</text>
					</view>
				</view>
				<view class="item-right">
					<text class="start-btn">开始识别</text>
				</view>
			</view>

			<view class="test-item" v-for="(item, index) in testList" :key="index" @click="startTest(item)">
				<view class="item-left">
					<text class="item-title">{{item.typeName}}</text>
					<text class="item-desc">{{item.description}}</text>
					<view class="item-info">
						<text class="info-text">题目数量: {{item.questionCount}}</text>
						<text class="info-text">预计用时: {{item.estimatedTime}}分钟</text>
					</view>
				</view>
				<view class="item-right">
					<text class="start-btn">开始测试</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import { getAllTopics } from '@/api/system/test.js'
	import { toast } from '@/utils/common'
	
	export default {
		data() {
			return {
				testList: []
			}
		},
		onShow() {
			if (this.checkLogin()) {
				this.getTestList()
			}
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
					return false
				}
				return true
			},
			// 获取测试列表
			async getTestList() {
				try {
					console.log('开始获取测试列表...')
					const res = await getAllTopics()
					console.log('测试列表接口返回数据：', res)
					
					if(res.code === '200' && res.data) {
						// 按类型分组题目并添加描述信息
						const groupedTests = {}
						res.data.forEach(item => {
							if(!groupedTests[item.typeId]) {
								let description = ''
								switch(item.typeName) {
									case '抑郁自评量表SDS':
										description = '用于评估抑郁症状的严重程度，帮助及早发现抑郁倾向'
										break
									case 'CES-D抑郁自评量表':
										description = '评估抑郁症状和情绪状态的专业量表'
										break
									case 'PHQ-9抑郁症筛查量表':
										description = '快速筛查抑郁症状的简短量表'
										break
									case '焦虑自评量表(SAS)':
										description = '评估焦虑症状的严重程度，了解自身焦虑状态'
										break
									case '社交焦虑量表(LSAS)':
										description = '评估社交焦虑程度，帮助改善社交状况'
										break
									case '强迫症状自评量表(MOCI)':
										description = '评估强迫症状的类型和严重程度'
										break
									case '创伤后应激障碍量表(PCL-5)':
										description = '评估创伤后应激反应的专业量表'
										break
									default:
										description = '专业的心理评测量表，帮助评估心理健康状况'
								}
								
								groupedTests[item.typeId] = {
									id: item.typeId,
									typeId: item.typeId,
									typeName: item.typeName,
									description: description,
									questionCount: 0,
									estimatedTime: 0
								}
							}
							groupedTests[item.typeId].questionCount++
							// 每题预计用时1分钟
							groupedTests[item.typeId].estimatedTime = Math.ceil(groupedTests[item.typeId].questionCount)
						})
						this.testList = Object.values(groupedTests)
						console.log('处理后的测试列表：', this.testList)
					} else {
						throw new Error(res.msg || '获取数据失败')
					}
				} catch(e) {
					console.error('获取测试列表失败：', e)
					toast(`获取测试列表失败：${e.message || '请检查网络连接'}`)
				}
			},
			// 开始测试
			startTest(test) {
				console.log(test.typeName);
				
				uni.navigateTo({
					url: `/pages/test/test-detail?typeId=${test.typeId}&typeName=${encodeURIComponent(test.typeName)}&paperId=${test.id}`
				})
			},
			goToFmri() {
				uni.navigateTo({
					url: '/pages/test/fmri-upload'
				})
			}
		}
	}
</script>

<style lang="scss">
.test-container {
	padding: 30rpx;
	
	.test-header {
		margin-bottom: 40rpx;
		
		.header-title {
			font-size: 40rpx;
			font-weight: bold;
			color: #333;
			margin-bottom: 10rpx;
		}
		
		.header-desc {
			font-size: 28rpx;
			color: #666;
		}
	}
	
	.test-list {
		.test-item {
			background: #fff;
			border-radius: 12rpx;
			padding: 30rpx;
			margin-bottom: 30rpx;
			display: flex;
			justify-content: space-between;
			align-items: center;
			box-shadow: 0 2rpx 12rpx rgba(0,0,0,0.05);
			
			.item-left {
				flex: 1;
				margin-right: 20rpx;
				
				.item-title {
					font-size: 32rpx;
					font-weight: bold;
					color: #333;
					margin-bottom: 10rpx;
				}
				
				.item-desc {
					font-size: 26rpx;
					color: #666;
					margin-bottom: 20rpx;
				}
				
				.item-info {
					display: flex;
					
					.info-text {
						font-size: 24rpx;
						color: #999;
						margin-right: 20rpx;
					}
				}
			}
			
			.item-right {
				.start-btn {
					background: #007AFF;
					color: #fff;
					padding: 12rpx 30rpx;
					border-radius: 30rpx;
					font-size: 28rpx;
				}
			}
		}
	}
}
</style>
