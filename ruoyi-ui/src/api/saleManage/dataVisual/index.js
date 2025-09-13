import request from '@/utils/request'

// 查询品类退款率求和列表
export function listCategoryRefundData() {
  return request({
    url: '/sale/visual/stats/categoryRefund',
    method: 'get'
  })
}

// 查询skuRoi求和列表
export function listSkuRoiData() {
  return request({
    url: '/sale/visual/stats/skuRoi',
    method: 'get'
  })
}

// 查询skuRoi求和列表
export function listFirstLevelCategoryData() {
  return request({
    url: '/sale/visual/stats/firstLevelCategory',
    method: 'get'
  })
}

// 查询skuRoi求和列表
export function listChannelData() {
  return request({
    url: '/sale/visual/stats/channel',
    method: 'get'
  })
}

// 查询skuRoi求和列表
export function listBrandData() {
  return request({
    url: '/sale/visual/stats/brand',
    method: 'get'
  })
}

// 查询skuRoi求和列表
export function listRegionData() {
  return request({
    url: '/sale/visual/stats/region',
    method: 'get'
  })
}

// 查询skuRoi求和列表
export function listCountryData() {
  return request({
    url: '/sale/visual/stats/country',
    method: 'get'
  })
}

// 查询skuRoi求和列表
export function listStoreData() {
  return request({
    url: '/sale/visual/stats/store',
    method: 'get'
  })
}

// 查询skuRoi求和列表
export function listSecondLevelCategoryData() {
  return request({
    url: '/sale/visual/stats/secondLevelCategory',
    method: 'get'
  })
}
