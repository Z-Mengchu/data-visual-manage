import request from '@/utils/request'

// 获取核心KPI数据
export function getKpiData(params) {
  return request({
    url: '/temu/visual/kpi',
    method: 'get',
    params
  })
}

// 获取平均指标数据
export function getAverageMetrics(params) {
  return request({
    url: '/temu/visual/averageMetrics',
    method: 'get',
    params
  })
}

// 获取预警数据
export function getAlertData(params) {
  return request({
    url: '/temu/visual/alert',
    method: 'get',
    params
  })
}

// 获取筛选选项
export function getFilterOptions() {
  return request({
    url: '/temu/visual/filterOptions',
    method: 'get'
  })
}

// 获取运营员透视数据
export function getOperatorPerspectiveData(params) {
  return request({
    url: '/temu/visual/operatorPerspective',
    method: 'get',
    params
  })
}

// 获取开发员透视数据
export function getDeveloperPerspectiveData(params) {
  return request({
    url: '/temu/visual/developerPerspective',
    method: 'get',
    params
  })
}

// 获取品类透视数据
export function getCategoryPerspectiveData(params) {
  return request({
    url: '/temu/visual/categoryPerspective',
    method: 'get',
    params
  })
}

// 获取运营员发货仓库数据
export function getOperatorWarehouseData(params) {
  return request({
    url: '/temu/visual/operatorWarehouse',
    method: 'get',
    params
  })
}

// 获取开发员发货仓库数据
export function getDeveloperWarehouseData(params) {
  return request({
    url: '/temu/visual/developerWarehouse',
    method: 'get',
    params
  })
}
