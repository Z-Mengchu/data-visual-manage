import request from '@/utils/request'

// 查询海外托管业务数据管理列表
export function listData(query) {
  return request({
    url: '/overseas/data/list',
    method: 'get',
    params: query
  })
}

// 查询海外托管业务数据管理详细
export function getData(id) {
  return request({
    url: '/overseas/data/' + id,
    method: 'get'
  })
}

// 新增海外托管业务数据管理
export function addData(data) {
  return request({
    url: '/overseas/data',
    method: 'post',
    data: data
  })
}

// 修改海外托管业务数据管理
export function updateData(data) {
  return request({
    url: '/overseas/data',
    method: 'put',
    data: data
  })
}

// 删除海外托管业务数据管理
export function delData(id) {
  return request({
    url: '/overseas/data/' + id,
    method: 'delete'
  })
}
