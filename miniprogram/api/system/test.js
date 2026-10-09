import request from '@/utils/request'

// 获取所有测评题目
export function getAllTopics() {
	return request({
		url: '/topic/selectAll',
		method: 'get'
	})
}

// 根据类型ID获取题目
export function getTopicsByType(typeId) {
	return request({
		url: '/topic/selectAll',
		method: 'get',
		params: { typeId }
	})
}

// 提交测评结果
export function submitTestResult(data) {
	return request({
		url: '/test/submit',
		method: 'post',
		data: data
	})
}

// 获取医生建议
export function getDoctorAdvice(data) {
	return request({
		url: '/advice/get',
		method: 'get',
		params: data
	})
} 