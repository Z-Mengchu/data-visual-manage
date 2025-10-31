import request from '@/utils/request'

// 查询Temu订单明细列表
export function listTemu(query) {
  return request({
    url: '/temu/details/list',
    method: 'get',
    params: query
  })
}

// 查询Temu订单明细详细
export function getTemu(id) {
  return request({
    url: '/temu/details/' + id,
    method: 'get'
  })
}

// 新增Temu订单明细
export function addTemu(data) {
  return request({
    url: '/temu/details',
    method: 'post',
    data: data
  })
}

// 修改Temu订单明细
export function updateTemu(data) {
  return request({
    url: '/temu/details',
    method: 'put',
    data: data
  })
}

// 删除Temu订单明细
export function delTemu(ids) {
  return request({
    url: '/temu/details/' + ids,
    method: 'delete'
  })
}
