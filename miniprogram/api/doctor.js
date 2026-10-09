import request from '@/utils/request'

// 获取所有医生列表
export function getDoctorList(params) {
  return request({
    url: '/doctor/selectAll',
    method: 'get',
    params
  })
} 