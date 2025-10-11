import request from '@/utils/request'

// 查询运营数据汇总列表
export function getSummaryByOperator(country) {
  return request({
    url: '/overseas/visual/summary/operator',
    method: 'get',
    params: { country }
  })
}
// 查询开发数据汇总列表
export function getSummaryByDeveloper(country) {
  return request({
    url: '/overseas/visual/summary/developer',
    method: 'get',
    params: { country }
  })
}
// 查询品类数据汇总列表
export function getSummaryByBrand(country) {
  return request({
    url: '/overseas/visual/summary/brand',
    method: 'get',
    params: { country }
  })
}
// 查询仓库数据汇总列表
export function getSummaryByWarehouse(country) {
  return request({
    url: '/overseas/visual/summary/warehouse',
    method: 'get',
    params: { country }
  })
}
// 查询类目数据汇总列表
export function getSummaryByCategory(country) {
  return request({
    url: '/overseas/visual/summary/category',
    method: 'get',
    params: { country }
  })
}
// 查询SKU数据汇总列表
export function getSummaryBySku(country) {
  return request({
    url: '/overseas/visual/summary/sku',
    method: 'get',
    params: { country }
  })
}
// 查询费用项分组总和
export function getSummaryByFeeItem(country) {
  return request({
    url: '/overseas/visual/summary/fee-item',
    method: 'get',
    params: { country }
  })
}
// 查询品牌和类目分组汇总数据
export function getSummaryByBrandAndCategory(country) {
  return request({
    url: '/overseas/visual/summary/brand-category',
    method: 'get',
    params: { country }
  })
}
// 查询月度汇总数据
export function getSummaryByMonthly(country) {
  return request({
    url: '/overseas/visual/summary/monthly',
    method: 'get',
    params: { country }
  })
}
// 查询总体统计数据
export function getSummaryByTotal(country) {
  return request({
    url: '/overseas/visual/summary/total',
    method: 'get',
    params: { country }
  })
}
// 查询核心费用项汇总数据
export function getSummaryByCoreExpenses(country) {
  return request({
    url: '/overseas/visual/summary/core-expenses',
    method: 'get',
    params: { country }
  })
}

// 获取国家列表
export function getAllCountry() {
  return request({
    url: '/overseas/visual/allCountry',
    method: 'get'
  })
}
