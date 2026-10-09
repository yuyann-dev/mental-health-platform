import upload from '@/utils/upload'
import request from '@/utils/request'

// 用户密码重置
export default {

	updateUserPwd(oldPassword, newPassword) {
		const data = {
			oldPassword,
			newPassword
		}
		return request({
			url: '/user/resetPwd',
			method: 'put',
			params: data
		})
	},

	// 查询用户个人信息
	getUserProfile() {
		return request({
			url: '/getInfo',
			method: 'get'
		})
	},

	// 修改用户个人信息
	updateUserProfile(data) {
		return request({
			url: '/user/updateUserProfile',
			method: 'put',
			data: data
		})
	},

	// 用户头像上传
	uploadAvatar(data) {
		return upload({
			url: '/system/user/profile/avatar',
			name: data.name,
			filePath: data.filePath
		})
	},
}
