import request from '@/utils/request'

// 查询销售数据管理列表
export function listSalesData(query) {
  return request({
    url: '/dataAnalysis/salesData/list',
    method: 'get',
    params: query
  })
}

// 查询销售数据管理详细
export function getSalesData(id) {
  return request({
    url: '/dataAnalysis/salesData/' + id,
    method: 'get'
  })
}

// 新增销售数据管理
export function addSalesData(data) {
  return request({
    url: '/dataAnalysis/salesData',
    method: 'post',
    data: data
  })
}

// 修改销售数据管理
export function updateSalesData(data) {
  return request({
    url: '/dataAnalysis/salesData',
    method: 'put',
    data: data
  })
}

// 删除销售数据管理
export function delSalesData(id) {
  return request({
    url: '/dataAnalysis/salesData/' + id,
    method: 'delete'
  })
}
