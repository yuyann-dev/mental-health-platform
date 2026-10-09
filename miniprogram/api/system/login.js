import request from '@/utils/request'

// login_for_ruoyi_autoee

// 用户名密码密码登录
export function loginByUsernameAndPassword(username, password) {
	const data = {
		username,
		password,
		role:"USER"
	
	}
	return request({
		'url': '/login',
		headers: {
			isToken: false
		},
		'method': 'post',
		'data': data
	})
}



// 获取用户详细信息
export function getInfo() {
	return request({
		'url': '/getInfo',
		'method': 'get'
	})
}

// 退出方法
export function logout() {
	return request({
		'url': '/jwtapi/logoutByJwt',
		'method': 'post'
	})
}


export function register(data) {
	data.role="USER"
	return request({
		url: '/register',
		method: 'post',
		data: data
	})
}


// 获取我的诊疗建议
export function selectAllByMy() {
	return request({
		url: `/advice/selectAllByMy`,
		method: 'get'
	})
}


