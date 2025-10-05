import request from '@/utils/request'

// 查询运营数据汇总列表
export function getSummaryByOperator() {
  return request({
    url: '/overseas/visual/summary/operator',
    method: 'get'
  })
}

// 查询开发数据汇总列表
export function getSummaryByDeveloper() {
  return request({
    url: '/overseas/visual/summary/developer',
    method: 'get'
  })
}

// 查询品类数据汇总列表
export function getSummaryByBrand() {
  return request({
    url: '/overseas/visual/summary/brand',
    method: 'get'
  })
}

// 查询仓库数据汇总列表
export function getSummaryByWarehouse() {
  return request({
    url: '/overseas/visual/summary/warehouse',
    method: 'get'
  })
}

// 查询类目数据汇总列表
export function getSummaryByCategory() {
  return request({
    url: '/overseas/visual/summary/category',
    method: 'get'
  })
}

// 查询SKU数据汇总列表
export function getSummaryBySku() {
  return request({
    url: '/overseas/visual/summary/sku',
    method: 'get'
  })
}

// 查询费用项分组总和
export function getSummaryByFeeItem() {
  return request({
    url: '/overseas/visual/summary/fee-item',
    method: 'get'
  })
}

// 查询品牌和类目分组汇总数据
export function getSummaryByBrandAndCategory() {
  return request({
    url: '/overseas/visual/summary/brand-category',
    method: 'get'
  })
}

// 查询月度汇总数据
export function getSummaryByMonthly() {
  return request({
    url: '/overseas/visual/summary/monthly',
    method: 'get'
  })
}

// 查询总体统计数据
export function getSummaryByTotal() {
  return request({
    url: '/overseas/visual/summary/total',
    method: 'get'
  })
}

// 查询核心费用项汇总数据
export function getSummaryByCoreExpenses() {
  return request({
    url: '/overseas/visual/summary/core-expenses',
    method: 'get'
  })
}
