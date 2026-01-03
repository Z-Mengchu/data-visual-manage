import request from '@/utils/request'

// 查询采购账期列表
export function listPaymentPeriod(query) {
  return request({
    url: '/purchase/paymentPeriod/list',
    method: 'get',
    params: query
  })
}

// 查询采购账期详细
export function getPaymentPeriod(id) {
  return request({
    url: '/purchase/paymentPeriod/' + id,
    method: 'get'
  })
}

// 新增采购账期
export function addPaymentPeriod(data) {
  return request({
    url: '/purchase/paymentPeriod',
    method: 'post',
    data: data
  })
}

// 修改采购账期
export function updatePaymentPeriod(data) {
  return request({
    url: '/purchase/paymentPeriod',
    method: 'put',
    data: data
  })
}

// 删除采购账期
export function delPaymentPeriod(id) {
  return request({
    url: '/purchase/paymentPeriod/' + id,
    method: 'delete'
  })
}

// 统计已付款和未付款数量
export function countPaymentStatus(query) {
  return request({
    url: '/purchase/paymentPeriod/countPaymentStatus',
    method: 'get',
    params: query
  })
}

// 批量修改采购条目付款状态
export function batchUpdateIsPaid(data) {
  return request({
    url: '/purchase/paymentPeriod/batchUpdateIsPaid',
    method: 'post',
    data: data
  })
}
