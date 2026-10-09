<template>
	<view class="container">
		<view class="bg bg-color-base margin-b20"></view>
		<view class="tab vs-row vs-align-center">
			<image class="tab-bg"
				src="https://6e69-niew6-1302638010.tcb.qcloud.la/denglu/%E7%99%BB%E5%BD%955/banner-icon.png?sign=d63f6b91aed3733b261cc0127d2cf702&t=1604049324"
				mode=""></image>
			<view class="vs-row vs-align-center">
				<view class="vs-column vs-align-center margin-r40" @click="cur = 0"> <text class="font-50 margin-b20"
						:class="[cur ? 'color-black-3': 'color-base']">登录</text>
					<view class="line bg-color-base" v-if="!cur"></view>
				</view>
				<view class="vs-column vs-align-center" @click="cur = 1"> <text class="font-46 margin-b20"
						:class="[cur ? 'color-base': 'color-black-3']">注册</text>
					<view class="line bg-color-base" v-if="cur"></view>
				</view>
			</view>
		</view>
		<view class="login margin-b80" v-if="!cur">
			<view class="input vs-row vs-align-center margin-b40">
				<image class="input-icon margin-r20"
					src="https://6e69-niew6-1302638010.tcb.qcloud.la/denglu/%E7%99%BB%E5%BD%955/account.png?sign=599d8600e2d55f39ebd1cbc159a04729&t=1604049340"
					mode=""></image> <input v-model="loForm.username" class="vs-flex-item color-base font-30"
					type="text" value="" :maxlength="11" placeholder="请输入您的账号" placeholder-class="input-placeholder" />
			</view>
			<view class="input vs-row vs-align-center margin-b40">
				<image class="input-icon margin-r20"
					src="https://6e69-niew6-1302638010.tcb.qcloud.la/denglu/%E7%99%BB%E5%BD%955/password.png?sign=9f8fdd0ae0a1ae530a9226de8917ebbd&t=1604049354"
					mode=""></image> <input v-model="loForm.password" class="vs-flex-item color-base font-30"
					type="text" password value="" placeholder="请输入您的登录密码" placeholder-class="input-placeholder" />
			</view>
		</view>

		<view class="register margin-b80" v-if="cur">
			<view class="input vs-row vs-align-center margin-b40">
				<image class="input-icon margin-r20"
					src="https://6e69-niew6-1302638010.tcb.qcloud.la/denglu/%E7%99%BB%E5%BD%955/account.png?sign=599d8600e2d55f39ebd1cbc159a04729&t=1604049340"
					mode=""></image> <input v-model="reForm.username" class="vs-flex-item color-base font-30"
					type="text" value="" :maxlength="11" placeholder="请输入您的账号" placeholder-class="input-placeholder" />
			</view>
			<view class="input vs-row vs-align-center margin-b40">
				<image class="input-icon margin-r20"
					src="https://6e69-niew6-1302638010.tcb.qcloud.la/denglu/%E7%99%BB%E5%BD%955/password.png?sign=9f8fdd0ae0a1ae530a9226de8917ebbd&t=1604049354"
					mode=""></image> <input v-model="reForm.password" class="vs-flex-item color-base font-30"
					type="text" password value="" placeholder="请输入您的登录密码" placeholder-class="input-placeholder" />
			</view>
			<view class="input vs-row vs-align-center margin-b40">
				<image class="input-icon margin-r20"
					src="https://6e69-niew6-1302638010.tcb.qcloud.la/denglu/%E7%99%BB%E5%BD%955/password.png?sign=9f8fdd0ae0a1ae530a9226de8917ebbd&t=1604049354"
					mode=""></image> <input v-model="reForm.name" class="vs-flex-item color-base font-30" type="text"
					value="" placeholder="请输入您的姓名" placeholder-class="input-placeholder" />
			</view>
			<view class="input vs-row vs-align-center margin-b40">
				<image class="input-icon margin-r20"
					src="https://6e69-niew6-1302638010.tcb.qcloud.la/denglu/%E7%99%BB%E5%BD%955/password.png?sign=9f8fdd0ae0a1ae530a9226de8917ebbd&t=1604049354"
					mode=""></image> <input v-model="reForm.phone" class="vs-flex-item color-base font-30" type="text"
					value="" placeholder="请输入您的电话" placeholder-class="input-placeholder" />
			</view>
			<view class="input vs-row vs-align-center margin-b40">
				<image class="input-icon margin-r20"
					src="https://6e69-niew6-1302638010.tcb.qcloud.la/denglu/%E7%99%BB%E5%BD%955/password.png?sign=9f8fdd0ae0a1ae530a9226de8917ebbd&t=1604049354"
					mode=""></image> <input v-model="reForm.email" class="vs-flex-item color-base font-30" type="text"
					value="" placeholder="请输入您的邮箱" placeholder-class="input-placeholder" />
			</view>
			<view class="avatar">
				<image style="width: 100px;height: 100px;" :src="avatar" class="avatar-img"></image>
				<button @click="chooseImage">上传头像</button>
			</view>
		</view>
		<view @click="reAndLogin" style="color: #fff;"
			class="button bg-color-base vs-row vs-align-center vs-space-center margin-b20"> <text
				class="color-white font-34">立即{{ cur ? '注册': '登录' }}</text> </view>
		<!-- <view class="vs-row vs-align-center vs-space-center margin-b100"> <text class="color-base font-28">忘记密码？</text>
		</view> -->
		<view class="other">
		</view>
	</view>
</template>
<script>
	import {
		register,
		loginByUsernameAndPassword
	} from "@/api/system/login.js"
	import {
		toast,
	} from '@/utils/common'
	import config from '@/config'

	export default {
		data() {
			return {
				url: 'https://ext.dcloud.net.cn/plugin?id=1931',
				cur: 0,
				reForm: {},
				loForm: {

				},
				avatar: '', // 头像图片路径  
			}
		},
		methods: {
			reAndLogin() {
				if (this.cur) {
					this.reForm.avatar=this.avatar
					register(this.reForm).then(res => {
						console.log(res);
						if (res.code == '200') {
							toast("注册成功")
							this.cur=0
							
						}
					})
				} else {
					this.$store.dispatch("LoginByUsernameAndPassword", this.loForm).then(res => {
						if (res.code == 200 || res.code == '200') {
							toast("登录成功")
							uni.switchTab({
								url: "/pages/center/center"
							})
						} else {
							toast(res.msg || "登录失败")
							uni.clearStorageSync("info")
						}
					}).catch(error => {
						console.error('登录请求失败:', error)
						toast("登录失败，请检查账号密码")
					})
				}
			},
			chooseImage() {
				uni.chooseImage({
					count: 1, // 默认9  
					sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有  
					sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有  
					success: (res) => {
						// 返回选定照片的本地文件路径列表  
						const tempFilePaths = res.tempFilePaths;
						this.uploadImage(tempFilePaths[0]);
					}
				});
			},
			uploadImage(filePath) {
				// 获取token（如果已登录）
				const token = uni.getStorageSync('token')
				const headers = {}
				if (token) {
					headers['Authorization'] = 'Bearer ' + token
				}
				
				const uploadTask = uni.uploadFile({
					url: config.baseUrl + '/files/upload',
					filePath: filePath,
					name: 'file',
					header: headers,
					formData: {
						'user': 'test'
					},
					success: (uploadFileRes) => {
						console.log('uploadFile success:', uploadFileRes);
						let data = JSON.parse(uploadFileRes.data);
						console.log(data);
						// 后端返回的是完整的访问路径 /mental/files/download/xxx.png
						this.avatar = data.data;
					},
					fail: (err) => {
						console.error('uploadFile fail:', err);
						uni.showToast({
							title: '头像上传失败',
							icon: 'none'
						});
					}
				});
			}
		},
	}
</script>
<style lang="scss">
	.container {
		position: relative;
	}

	.bg {
		position: relative;
		width: 750rpx;
		height: 400rpx;
	}

	.tab {
		position: absolute;
		top: 250rpx;
		left: 20rpx;
		right: 20rpx;
		height: 150rpx;
		padding: 0 50rpx;
		background-color: #fff;
		border-top-left-radius: 20rpx;
		border-top-right-radius: 20rpx;

		&-bg {
			position: absolute;
			top: -200rpx;
			right: -50rpx;
			width: 440rpx;
			height: 285rpx;
		}
	}

	.line {
		width: 25rpx;
		height: 7rpx;
	}

	.login,
	.register {
		padding: 0 60rpx;
	}

	.input {
		width: 580rpx;
		height: 90rpx;
		padding: 0 30rpx;
		background-color: rgba(80, 100, 235, 0.1);
		border-radius: 100rpx;

		&-icon {
			width: 30rpx;
			height: 38rpx;
		}

		&-placeholder {
			color: #5064eb;
		}
	}

	.button {
		width: 630rpx;
		height: 90rpx;
		margin-left: 60rpx;
		border-radius: 100rpx;
	}

	.separator {
		height: 2rpx;
		margin: 0 30rpx;
		background-color: #f5f5f5;
	}

	.other {
		&-items {
			padding: 0 200rpx;
		}

		&-icon {
			width: 50rpx;
			height: 50rpx;
		}
	}

	// 下边距  .margin-b5 {    margin-bottom: 5rpx;  }
	.margin-b10 {
		margin-bottom: 10rpx;
	}

	.margin-b15 {
		margin-bottom: 15rpx;
	}

	.margin-b20 {
		margin-bottom: 20rpx;
	}

	.margin-b25 {
		margin-bottom: 25rpx;
	}

	.margin-b30 {
		margin-bottom: 30rpx;
	}

	.margin-b40 {
		margin-bottom: 40rpx;
	}

	.margin-b60 {
		margin-bottom: 60rpx;
	}

	.margin-b80 {
		margin-bottom: 80rpx;
	}

	.margin-b100 {
		margin-bottom: 100rpx;
	}

	// 右边距  .margin-r5 {    margin-right: 5rpx;  }
	.margin-r10 {
		margin-right: 10rpx;
	}

	.margin-r15 {
		margin-right: 15rpx;
	}

	.margin-r20 {
		margin-right: 20rpx;
	}

	.margin-r25 {
		margin-right: 25rpx;
	}

	.margin-r30 {
		margin-right: 30rpx;
	}

	.margin-r40 {
		margin-right: 40rpx;
	}

	.margin-r60 {
		margin-right: 60rpx;
	}

	// 字体大小  .font-18 {    font-style: normal;    font-size: 18rpx;    font-family: Droid Sans Fallback;  }
	.font-20 {
		font-style: normal;
		font-size: 20rpx;
		font-family: Droid Sans Fallback;
	}

	.font-22 {
		font-style: normal;
		font-size: 22rpx;
		font-family: Droid Sans Fallback;
	}

	.font-24 {
		font-style: normal;
		font-size: 24rpx;
		font-family: Droid Sans Fallback;
	}

	.font-26 {
		font-style: normal;
		font-size: 26rpx;
		font-family: Droid Sans Fallback;
	}

	.font-28 {
		font-style: normal;
		font-size: 28rpx;
		font-family: Droid Sans Fallback;
	}

	.font-30 {
		font-style: normal;
		font-size: 30rpx;
		font-family: Droid Sans Fallback;
	}

	.font-32 {
		font-style: normal;
		font-size: 32rpx;
		font-family: Droid Sans Fallback;
	}

	.font-34 {
		font-style: normal;
		font-size: 34rpx;
		font-family: Droid Sans Fallback;
	}

	.font-36 {
		font-style: normal;
		font-size: 36rpx;
		font-family: Droid Sans Fallback;
	}

	.font-38 {
		font-style: normal;
		font-size: 38rpx;
		font-family: Droid Sans Fallback;
	}

	.font-40 {
		font-style: normal;
		font-size: 40rpx;
		font-family: Droid Sans Fallback;
	}

	.font-46 {
		font-style: normal;
		font-size: 46rpx;
		font-family: Droid Sans Fallback;
	}

	.font-50 {
		font-style: normal;
		font-size: 50rpx;
		font-family: Droid Sans Fallback;
	}

	.font-60 {
		font-style: normal;
		font-size: 60rpx;
		font-family: Droid Sans Fallback;
	}

	.font-80 {
		font-style: normal;
		font-size: 80rpx;
		font-family: Droid Sans Fallback;
	}

	// 字体对齐  .text-left {    text-align: left;  }
	.text-center {
		text-align: center;
	}

	.text-right {
		text-align: right;
	}

	// color相关  .color-white {    color: #FFFFFF;  }
	.color-red {
		color: #dc0000;
	}

	// 黑色色阶向下  .color-black {    color: #000;  }
	.color-black-3 {
		color: #333;
	}

	.color-black-6 {
		color: #666;
	}

	.color-black-9 {
		color: #999;
	}

	// 字体宽度  .font-weight-400 {    font-weight: 400;  }
	.font-weight-500 {
		font-weight: bold;
	}

	// 间隔  .spacing-20 {    width: 750rpx;    height: 20rpx;    background-color: #f8f8f8;  }
	// 圆角  .radius-10 {    border-radius: 10rpx;  }
	.radius-20 {
		border-radius: 20rpx;
	}

	.radius-30 {
		border-radius: 30rpx;
	}

	.radius-circle {
		border-radius: 50%;
	}

	.radius-height {
		border-radius: 10000px;
	}

	// flex相关  .vs-flex-item {    flex: 1;  }
	.vs-space-between {
		justify-content: space-between;
	}

	.vs-space-around {
		justify-content: space-around;
	}

	.vs-space-center {
		justify-content: center;
	}

	.vs-space-end {
		justify-content: flex-end;
	}

	.vs-row {
		flex-direction: row;
	}

	.vs-column {
		flex-direction: column;
	}

	.vs-align-end {
		align-items: flex-end;
	}

	.vs-align-center {
		display: flex;
		align-items: center;
	}

	.vs-align-start {
		align-items: flex-start;
	}

	.vs-item-hover {
		background-color: rgba(0, 0, 0, 0.05);
	}

	.vs-btn-hover {
		opacity: 0.8;
	}

	.color-base {
		color: #5064eb;
	}

	.bg-color-base {
		background-color: #5064eb;
	}
</style>