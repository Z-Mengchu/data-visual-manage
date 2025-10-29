import request from '@/utils/request'

// 查询运营数据汇总列表
export function getSummaryByOperator(country, daterangeParams) {
  return request({
    url: '/overseas/visual/summary/operator',
    method: 'get',
    params: { country, ...daterangeParams }
  })
}
// 查询开发数据汇总列表
export function getSummaryByDeveloper(country, daterangeParams) {
  return request({
    url: '/overseas/visual/summary/developer',
    method: 'get',
    params: { country, ...daterangeParams }
  })
}
// 查询品类数据汇总列表
export function getSummaryByBrand(country, daterangeParams) {
  return request({
    url: '/overseas/visual/summary/brand',
    method: 'get',
    params: { country, ...daterangeParams }
  })
}
// 查询仓库数据汇总列表
export function getSummaryByWarehouse(country, daterangeParams) {
  return request({
    url: '/overseas/visual/summary/warehouse',
    method: 'get',
    params: { country, ...daterangeParams }
  })
}
// 查询类目数据汇总列表
export function getSummaryByCategory(country, daterangeParams) {
  return request({
    url: '/overseas/visual/summary/category',
    method: 'get',
    params: { country, ...daterangeParams }
  })
}
// 查询SKU数据汇总列表
export function getSummaryBySku(country, daterangeParams) {
  return request({
    url: '/overseas/visual/summary/sku',
    method: 'get',
    params: { country, ...daterangeParams }
  })
}
// 查询费用项分组总和
export function getSummaryByFeeItem(country, daterangeParams) {
  return request({
    url: '/overseas/visual/summary/fee-item',
    method: 'get',
    params: { country, ...daterangeParams }
  })
}
// 查询品牌和类目分组汇总数据
export function getSummaryByBrandAndCategory(country, daterangeParams) {
  return request({
    url: '/overseas/visual/summary/brand-category',
    method: 'get',
    params: { country, ...daterangeParams }
  })
}
// 查询月度汇总数据
export function getSummaryByMonthly(country, daterangeParams) {
  return request({
    url: '/overseas/visual/summary/monthly',
    method: 'get',
    params: { country, ...daterangeParams }
  })
}
// 查询总体统计数据
export function getSummaryByTotal(country, daterangeParams) {
  return request({
    url: '/overseas/visual/summary/total',
    method: 'get',
    params: { country, ...daterangeParams }
  })
}
// 查询核心费用项汇总数据
export function getSummaryByCoreExpenses(country, daterangeParams) {
  return request({
    url: '/overseas/visual/summary/core-expenses',
    method: 'get',
    params: { country, ...daterangeParams }
  })
}

// 获取国家列表
export function getAllCountry() {
  return request({
    url: '/overseas/visual/allCountry',
    method: 'get'
  })
}
