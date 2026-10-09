import request from '@/utils/request'

// Get rehabilitation advice list with pagination
export function getRehabilitationList(params) {
  return request({
    url: '/rehabilitation/page',
    method: 'get',
    params
  })
}

// Get rehabilitation advice detail
export function getRehabilitationDetail(id) {
  return request({
    url: `/rehabilitation/${id}`,
    method: 'get'
  })
}

// Get rehabilitation advice by condition
export function getRehabilitationByCondition(condition) {
  return request({
    url: `/rehabilitation/condition/${condition}`,
    method: 'get'
  })
} 