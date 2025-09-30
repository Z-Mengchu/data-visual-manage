import request from '@/utils/request'

// 获取核心KPI数据
export function getKpiData(params) {
  return request({
    url: '/temu/visual/kpi',
    method: 'get',
    params
  })
}

// 获取核心KPI数据
export function getAverageMetrics(params) {
  return request({
    url: '/temu/visual/averageMetrics',
    method: 'get',
    params
  })
}

// 获取趋势数据
export function getTrendData(params) {
  return request({
    url: '/temu/visual/trend',
    method: 'get',
    params
  })
}

// 获取开发员桑基图数据
export function getDeveloperSankeyData(params) {
  return request({
    url: '/temu/visual/developerSankey',
    method: 'get',
    params
  })
}

// 获取品类矩形树图数据
export function getCategoryTreemapData(params) {
  return request({
    url: '/temu/visual/categoryTreemap',
    method: 'get',
    params
  })
}

// 获取品类排行榜数据
export function getCategoryRankingData(params) {
  return request({
    url: '/temu/visual/categoryRanking',
    method: 'get',
    params
  })
}

// 获取明细表格数据
export function getDetailTableData(params) {
  return request({
    url: '/temu/visual/detailTable',
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
