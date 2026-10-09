import store from '@/store'
import config from '../config'
import {
	getToken
} from '@/utils/auth'
import errorCode from '@/utils/errorCode'
import {
	toast,
	showConfirm,
	tansParams
} from '@/utils/common'

let timeout = 10000
const baseUrl = config.baseUrl

const request = config => {
	// 是否需要设置 token
	const isToken = (config.headers || {}).isToken === false
	config.header = config.header || {}
	console.log('当前token:', getToken());
	if (getToken() && !isToken) {
		config.header['token'] = getToken()
	}
	// get请求映射params参数
	if (config.params) {
		let url = config.url + '?' + tansParams(config.params)
		url = url.slice(0, -1)
		config.url = url
	}
	return new Promise((resolve, reject) => {
		// https://uniapp.dcloud.net.cn/api/request/request.html#
		// 请求的 header 中 content-type 默认为 application/json。
		let url = baseUrl + config.url
		console.log('请求URL:', url)
		uni.request({
			method: config.method || 'get',
			timeout: config.timeout || timeout,
			url: url,
			data: config.data,
			header: config.header,
			dataType: 'json'
		}).then(response => {
			const res = response.data
			const code = res.code || 200
			// 获取错误信息
			const msg = errorCode[code] || res.msg || errorCode['default']

			if (code === 200 || code === '200') {
				resolve(res)
			} else if (code === 401) {
				showConfirm('登录状态已过期，请重新登录').then(res => {
					if (res.confirm) {
						store.dispatch('LogOut').then(() => {
							uni.reLaunch({ url: '/pages/login/login' })
						})
					}
				})
				reject('无效的会话，或者会话已过期，请重新登录。')
			} else if (code === 500 || code === '500') {
				toast(msg)
				reject('500')
			} else {
				toast(msg)
				reject(code)
			}
		}).catch(error => {
			console.error('请求失败:', error)
			let { message } = error
			if (message === 'Network Error') {
				message = '后端接口连接异常'
			} else if (message.includes('timeout')) {
				message = '系统接口请求超时'
			} else if (message.includes('Request failed with status code')) {
				message = '系统接口' + message.substr(message.length - 3) + '异常'
			}
			toast(message)
			reject(error)
		})

	})
}

export default request
