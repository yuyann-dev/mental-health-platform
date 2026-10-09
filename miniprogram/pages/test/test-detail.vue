<template>
	<view class="test-detail">
		<view class="progress-bar">
			<view class="progress" :style="{ width: `${(currentIndex + 1) / questions.length * 100}%` }"></view>
			<text class="progress-text">{{ currentIndex + 1 }}/{{ questions.length }}</text>
		</view>

		<view class="question-container" v-if="currentQuestion">
			<view class="question-title">
				<text class="number">Q{{ currentIndex + 1 }}.</text>
				<text class="title">{{ currentQuestion.title }}</text>
			</view>

			<view class="options">
				<view class="option-item" v-for="(option, key) in options" :key="key"
					:class="{ active: currentAnswer === key }" @click="selectOption(key, option.score)">
					<text class="option-key">{{ key }}</text>
					<text class="option-text">{{ option.text }}</text>
				</view>
			</view>
		</view>

		<view class="bottom-buttons">
			<button class="prev-btn" @click="prevQuestion" v-if="currentIndex > 0">上一题</button>
			<button class="next-btn" @click="nextQuestion" v-if="currentIndex < questions.length - 1">下一题</button>
			<button class="submit-btn" @click="submitTest" v-if="currentIndex === questions.length - 1">提交</button>
		</view>
	</view>
</template>

<script>
import { getTopicsByType, submitTestResult } from '@/api/system/test.js'
import { toast } from '@/utils/common'
import storage from '@/utils/storage'
import constant from '@/utils/constant'

export default {
	data() {
		const storageData = uni.getStorageSync('storage_data') || {}
		return {
			typeId: '',
			typeName: '',
			paperId: '',
			userId: storageData[constant.id], // 从storage_data中获取用户ID
			questions: [],
			currentIndex: 0,
			currentAnswer: '',
			answers: []
		}
	},
	computed: {
		currentQuestion() {
			if (this.questions.length === 0) return null
			return this.questions[this.currentIndex]
		},
		options() {
			if (!this.currentQuestion) return {}
			return {
				'A': { text: this.currentQuestion.aName, score: this.currentQuestion.aScore },
				'B': { text: this.currentQuestion.bName, score: this.currentQuestion.bScore },
				'C': { text: this.currentQuestion.cName, score: this.currentQuestion.cScore },
				'D': { text: this.currentQuestion.dName, score: this.currentQuestion.dScore }
			}
		}
	},
	onLoad(options) {
		this.typeId = options.typeId
		this.typeName = decodeURIComponent(options.typeName)
		this.paperId = options.paperId

		// 检查是否已登录
		if (!this.userId) {
			toast('请先登录')
			uni.navigateTo({
				url: '/pages/login/login'
			})
			return
		}

		this.getQuestions()
	},
	onShow() {
		// 每次页面显示时检查登录状态
		const storageData = uni.getStorageSync('storage_data') || {}
		this.userId = storageData[constant.id]

		if (!this.userId) {
			toast('请先登录')
			uni.navigateTo({
				url: '/pages/login/login'
			})
			return
		}
	},
	methods: {
		async getQuestions() {
			try {
				console.log('开始获取题目列表...')
				const res = await getTopicsByType(this.typeId)
				console.log('题目列表接口返回数据：', res)

				// 检查 res 是否直接就是接口返回的数据
				const response = res.data ? res : { data: res }
				console.log('response:', response.code)
				if (response.code === '200' && response.data) {
					// 反转数组顺序，使题目按ID从小到大排序
					this.questions = response.data.sort((a, b) => a.id - b.id)
					this.answers = new Array(this.questions.length).fill('')
					console.log('处理后的题目列表：', this.questions)
				} else {
					throw new Error(response.msg || '获取题目失败')
				}
			} catch (e) {
				console.error('获取题目失败：', e)
				// 检查是否是登录过期错误
				if (e.message && (e.message.includes('INVALID_LOGIN') || e.message.includes('access_token expired'))) {
					toast('登录已过期，请重新登录')
					// 清除本地存储的登录信息
					uni.removeStorageSync('storage_data')
					// 跳转到登录页面
					setTimeout(() => {
						uni.redirectTo({
							url: '/pages/login/login'
						})
					}, 1500)
					return
				}
				toast(`获取题目失败：${e.message || '请检查网络连接'}`)
			}
		},
		selectOption(key, score) {  
			this.currentAnswer = key
			this.answers[this.currentIndex] = key // 只保存选项字母
			console.log(6666, this.answers);
			
		},
		prevQuestion() {
			if (this.currentIndex > 0) {
				this.currentIndex--
				this.currentAnswer = this.answers[this.currentIndex]?.option || ''
			}
		},
		nextQuestion() {
			if (this.currentIndex < this.questions.length - 1) {
				this.currentIndex++
				this.currentAnswer = this.answers[this.currentIndex]?.option || ''
			}
		},
		async submitTest() {
			if (this.answers.includes('')) {
				toast('请完成所有题目')
				return
			}

			try {
				// 调用后端API保存测试结果
				const submitData = {
					paperId: Number(this.paperId),
					userId: Number(this.userId),
					answers: this.answers // 直接提交答案字母数组
				}

				console.log('提交数据：', submitData)
				const res = await submitTestResult(submitData)
				console.log('提交返回数据：', res)

				// 检查 res 是否直接就是接口返回的数据
				const response = res.data ? res : { data: res }

				if (response.code === '200') {
					// 从后端返回数据中获取结果
					const resultData = response.data || {}
					const score = resultData.score || 0
					const level = resultData.level || ''
					const analysis = resultData.analysis || ''
					
					console.log('测试结果：', { score, level, analysis })

					// 跳转到结果页面，传递后端返回的真实结果
					uni.redirectTo({
						url: `/pages/test/test-result?score=${score}&level=${encodeURIComponent(level)}&analysis=${encodeURIComponent(analysis)}&typeId=${this.typeId}&typeName=${encodeURIComponent(this.typeName)}`
					})
				} else {
					throw new Error(response.msg || '提交失败')
				}
			} catch (e) {
				console.error('提交测评失败：', e)
				// 检查是否是登录过期错误
				if (e.message && (e.message.includes('INVALID_LOGIN') || e.message.includes('access_token expired'))) {
					toast('登录已过期，请重新登录')
					// 清除本地存储的登录信息
					uni.removeStorageSync('storage_data')
					// 跳转到登录页面
					setTimeout(() => {
						uni.redirectTo({
							url: '/pages/login/login'
						})
					}, 1500)
					return
				}
				toast(`提交失败：${e.message || '请检查网络连接'}`)
			}
		}
	}
}
</script>

<style lang="scss">
.test-detail {
	min-height: 100vh;
	background: #f8f8f8;
	padding-bottom: 120rpx;

	.progress-bar {
		height: 8rpx;
		background: #eee;
		position: relative;

		.progress {
			height: 100%;
			background: #007AFF;
			transition: width 0.3s;
		}

		.progress-text {
			position: absolute;
			right: 30rpx;
			top: 20rpx;
			font-size: 24rpx;
			color: #666;
		}
	}

	.question-container {
		padding: 40rpx 30rpx;

		.question-title {
			margin-bottom: 40rpx;

			.number {
				font-size: 36rpx;
				font-weight: bold;
				color: #007AFF;
				margin-right: 20rpx;
			}

			.title {
				font-size: 32rpx;
				color: #333;
			}
		}

		.options {
			.option-item {
				background: #fff;
				border-radius: 12rpx;
				padding: 30rpx;
				margin-bottom: 20rpx;
				display: flex;
				align-items: center;

				&.active {
					background: #007AFF;

					.option-key {
						color: #fff;
						background: transparent;
					}

					.option-text {
						color: #fff;
					}
				}

				.option-key {
					width: 60rpx;
					height: 60rpx;
					background: #f5f5f5;
					border-radius: 30rpx;
					display: flex;
					align-items: center;
					justify-content: center;
					font-size: 28rpx;
					color: #666;
					margin-right: 20rpx;
				}

				.option-text {
					font-size: 28rpx;
					color: #333;
					flex: 1;
				}
			}
		}
	}

	.bottom-buttons {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		padding: 20rpx 30rpx;
		background: #fff;
		display: flex;
		justify-content: space-between;
		box-shadow: 0 -2rpx 10rpx rgba(0, 0, 0, 0.05);

		button {
			flex: 1;
			margin: 0 10rpx;
			height: 80rpx;
			line-height: 80rpx;
			font-size: 28rpx;
			border-radius: 40rpx;

			&.prev-btn {
				background: #f5f5f5;
				color: #666;
			}

			&.next-btn,
			&.submit-btn {
				background: #007AFF;
				color: #fff;
			}
		}
	}
}
</style>