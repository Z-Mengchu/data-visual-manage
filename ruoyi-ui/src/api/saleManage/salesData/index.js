import request from '@/utils/request'

// 查询销售数据管理列表
export function listSalesData(query) {
  return request({
    url: '/sale/data/list',
    method: 'get',
    params: query
  })
}

// 查询销售数据管理详细
export function getSalesData(id) {
  return request({
    url: '/sale/data/' + id,
    method: 'get'
  })
}

// 新增销售数据管理
export function addSalesData(data) {
  return request({
    url: '/sale/data',
    method: 'post',
    data: data
  })
}

// 修改销售数据管理
export function updateSalesData(data) {
  return request({
    url: '/sale/data',
    method: 'put',
    data: data
  })
}

// 删除销售数据管理
export function delSalesData(id) {
  return request({
    url: '/sale/data/' + id,
    method: 'delete'
  })
}
