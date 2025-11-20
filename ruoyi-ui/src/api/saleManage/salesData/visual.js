import request from '@/utils/request'

// 获取核心KPI数据
export function getKpiData(params) {
  return request({
    url: '/sale/visual/kpi',
    method: 'get',
    params
  })
}

// 获取品类退款率数据
export function getCategoryRefundData(params) {
  return request({
    url: '/sale/visual/stats/categoryRefund',
    method: 'get',
    params
  })
}

// 获取SKU ROI数据
export function getSkuRoiData(params) {
  return request({
    url: '/sale/visual/stats/skuRoi',
    method: 'get',
    params
  })
}

// 获取一级品类数据
export function getFirstLevelCategoryData(params) {
  return request({
    url: '/sale/visual/stats/firstLevelCategory',
    method: 'get',
    params
  })
}

// 获取渠道数据
export function getChannelData(params) {
  return request({
    url: '/sale/visual/stats/channel',
    method: 'get',
    params
  })
}

// 获取品牌数据
export function getBrandData(params) {
  return request({
    url: '/sale/visual/stats/brand',
    method: 'get',
    params
  })
}

// 获取国家数据
export function getCountryData(params) {
  return request({
    url: '/sale/visual/stats/country',
    method: 'get',
    params
  })
}

// 获取店铺数据
export function getStoreData(params) {
  return request({
    url: '/sale/visual/stats/store',
    method: 'get',
    params
  })
}

// 获取地图数据
export function getMapData(params) {
  return request({
    url: '/sale/visual/map',
    method: 'get',
    params
  })
}

// 获取渠道销售额占比数据
export function getChannelDistributionData(params) {
  return request({
    url: '/sale/visual/channelDistribution',
    method: 'get',
    params
  })
}

// 获取趋势数据
export function getTrendData(params) {
  return request({
    url: '/sale/visual/trend',
    method: 'get',
    params
  })
}

// 获取详情表格数据
export function getDetailData(params) {
  return request({
    url: '/sale/visual/details',
    method: 'get',
    params
  })
}

// 获取筛选条件选项
export function getFilterOptions() {
  return request({
    url: '/sale/visual/filterOptions',
    method: 'get'
  })
}

// 获取预警数据
export function getCategoryAlerts(params) {
  return request({
    url: '/sale/visual/alerts',
    method: 'get',
    params
  })
}
