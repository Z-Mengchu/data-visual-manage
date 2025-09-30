<template>
  <dv-border-box-8 class="perspective-container">
    <div class="perspective-header">
      <span class="title">透视-品类</span>
      <div class="chart-controls">
        <div class="field-selector">
          <span class="selector-label">数据维度:</span>
          <el-select
            v-model="selectedField"
            placeholder="选择数据维度"
            @change="updateChart"
            class="custom-select"
          >
            <el-option
              v-for="item in fieldOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </div>
      </div>
    </div>
    <div class="perspective-content">
      <div id="treemapChart" class="treemap-chart"></div>
    </div>
  </dv-border-box-8>
</template>

<script>
import * as echarts from 'echarts'
import { getCategoryPerspectiveData } from '@/api/saleManage/temu/temuVisualization'
export default {
  name: 'CategoryPerspective',
  props: {
    filterParams: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      tableData: [],
      selectedField: 'income',
      treemapChart: null,
      fieldOptions: [
        { label: '收入', value: 'income' },
        { label: '退款金额', value: 'refundAmount' },
        { label: '商品成本', value: 'productCost' },
        { label: '头程费用', value: 'firstLegCost' },
        { label: '渠道成交费', value: 'channelFee' },
        { label: '物流商运费', value: 'logisticsShippingCost' },
        { label: '美国平台面单费用', value: 'usPlatformLabelFee' },
        { label: '其他费用', value: 'otherCosts' },
        { label: '后台退款运费补贴', value: 'backendRefundSubsidy' },
        { label: '广告费', value: 'advertisingFee' },
        { label: '补发费用', value: 'replenishmentCost' },
        { label: '包装费用', value: 'packagingCost' },
        { label: '清货补贴', value: 'clearanceSubsidy' },
        { label: '毛利', value: 'grossProfit' },
        { label: '毛利率', value: 'grossProfitRate' },
        { label: '分销佣金', value: 'distributionCommission' },
        { label: 'TD实际毛利', value: 'tdActualGrossProfit' },
        { label: 'TD实际毛利率', value: 'tdActualGrossProfitRate' },
        { label: '商品成本占比', value: 'productCostRatio' },
        { label: '头程占比', value: 'firstLegCostRatio' },
        { label: '物流费占比', value: 'logisticsShippingCostRatio' },
        { label: '退款占比', value: 'refundRatio' },
        { label: '补发占比', value: 'replenishmentCostRatio' }
      ],
      colorPalette: [
        '#5470c6', '#91cc75', '#fac858', '#ee6666', '#73c0de',
        '#3ba272', '#fc8452', '#9a60b4', '#ea7ccc', '#5470c6',
        '#91cc75', '#fac858', '#ee6666', '#73c0de', '#3ba272',
        '#fc8452', '#9a60b4', '#ea7ccc', '#5470c6', '#91cc75'
      ]
    }
  },
  mounted() {
    this.initChart()
    this.loadData()
  },
  beforeDestroy() {
    if (this.treemapChart) {
      this.treemapChart.dispose()
    }
    window.removeEventListener('resize', this.handleResize)
  },
  methods: {
    async loadData() {
      try {
        const params = {
          ...this.filterParams
        }
        const response = await getCategoryPerspectiveData(params)
        this.tableData = response.data || []
        this.updateChart()
      } catch (error) {
        console.error('加载品类透视数据失败:', error)
      }
    },
    initChart() {
      const chartDom = document.getElementById('treemapChart')
      if (!chartDom) return
      this.treemapChart = echarts.init(chartDom)
      window.addEventListener('resize', this.handleResize)
    },
    updateChart() {
      if (!this.treemapChart || !this.tableData.length) return
      // 准备矩形树图数据
      const treemapData = this.tableData.map((item, index) => {
        const value = item[this.selectedField] || 0
        const absoluteValue = Math.abs(value)

        return {
          name: item.groupName || `品类${index + 1}`,
          value: absoluteValue,
          originalValue: value,
          itemStyle: {
            color: this.colorPalette[index % this.colorPalette.length]
          },
          // 存储其他字段用于tooltip显示
          income: item.income || 0,
          grossProfit: item.grossProfit || 0,
          tdActualGrossProfit: item.tdActualGrossProfit || 0,
          grossProfitRate: item.grossProfitRate || 0,
          tdActualGrossProfitRate: item.tdActualGrossProfitRate || 0,
          refundAmount: item.refundAmount || 0,
          productCost: item.productCost || 0,
          firstLegCost: item.firstLegCost || 0,
          channelFee: item.channelFee || 0,
          logisticsShippingCost: item.logisticsShippingCost || 0,
          usPlatformLabelFee: item.usPlatformLabelFee || 0,
          otherCosts: item.otherCosts || 0,
          backendRefundSubsidy: item.backendRefundSubsidy || 0,
          advertisingFee: item.advertisingFee || 0,
          replenishmentCost: item.replenishmentCost || 0,
          packagingCost: item.packagingCost || 0,
          clearanceSubsidy: item.clearanceSubsidy || 0,
          distributionCommission: item.distributionCommission || 0,
          productCostRatio: item.productCostRatio || 0,
          firstLegCostRatio: item.firstLegCostRatio || 0,
          logisticsShippingCostRatio: item.logisticsShippingCostRatio || 0,
          refundRatio: item.refundRatio || 0,
          replenishmentCostRatio: item.replenishmentCostRatio || 0
        }
      }).filter(item => item.value > 0) // 过滤掉值为0的数据
      // 设置矩形树图选项
      const option = {
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'item',
          formatter: (params) => {
            // 添加安全检查
            if (!params.data) {
              return ''
            }
            const data = params.data
            const isPercentageField = this.selectedField.includes('Rate') || this.selectedField.includes('Ratio')
            const valueDisplay = isPercentageField ?
              `${(data.originalValue * 100).toFixed(2)}%` :
              `¥${this.formatNumber(data.originalValue)}`

            let tooltipContent = `<div style="font-size: 14px; font-weight: bold; margin-bottom: 8px;">${data.name || '未知品类'}</div>`
            tooltipContent += `<div style="margin-bottom: 8px;">${this.getFieldLabel(this.selectedField)}: ${valueDisplay}</div>`

            // 显示其他关键指标
            tooltipContent += `<div style="margin-top: 8px; border-top: 1px solid #666; padding-top: 8px;">`
            tooltipContent += `<div style="font-size: 12px; font-weight: bold; margin-bottom: 4px;">关键指标:</div>`

            const keyMetrics = [
              { label: '收入', value: data.income, isPercentage: false },
              { label: '毛利', value: data.grossProfit, isPercentage: false },
              { label: '毛利率', value: data.grossProfitRate, isPercentage: true },
              { label: 'TD实际毛利', value: data.tdActualGrossProfit, isPercentage: false },
              { label: '退款金额', value: data.refundAmount, isPercentage: false }
            ]

            keyMetrics.forEach(metric => {
              if (metric.value !== 0) {
                const displayValue = metric.isPercentage ?
                  `${(metric.value * 100).toFixed(2)}%` :
                  `¥${this.formatNumber(metric.value)}`
                tooltipContent += `<div style="font-size: 11px; display: flex; justify-content: space-between; margin-bottom: 2px;">
                  <span>${metric.label}:</span>
                  <span style="color: #fac858; margin-left: 8px;">${displayValue}</span>
                </div>`
              }
            })

            tooltipContent += `</div>`

            return tooltipContent
          },
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff',
            fontSize: 12
          },
          extraCssText: 'box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);'
        },
        series: [{
          type: 'treemap',
          data: treemapData,
          roam: false,
          nodeClick: false,
          breadcrumb: {
            show: false
          },
          // 去掉矩形之间的间隔
          gapWidth: 0, // 设置为0去掉间隔
          label: {
            show: true,
            formatter: (params) => {
              // 修复：添加安全检查
              const name = params.data && params.data.name ? params.data.name : ''
              const maxLength = 8 // 最大显示字符数
              return name.length > maxLength ? name.substring(0, maxLength) + '...' : name
            },
            color: '#fff',
            fontSize: 12,
            fontWeight: 'bold'
          },
          upperLabel: {
            show: true,
            height: 30,
            color: '#fff',
            fontSize: 12,
            fontWeight: 'bold',
            formatter: (params) => {
              // 修复：添加安全检查
              const name = params.data && params.data.name ? params.data.name : ''
              const maxLength = 8
              return name.length > maxLength ? name.substring(0, maxLength) + '...' : name
            }
          },
          itemStyle: {
            borderColor: '#0a1d39',
            borderWidth: 0, // 设置为0去掉边框
            gapWidth: 0 // 设置为0去掉间隔
          },
          levels: [
            {
              itemStyle: {
                borderColor: '#0a1d39',
                borderWidth: 0, // 设置为0去掉边框
                gapWidth: 0 // 设置为0去掉间隔
              },
              upperLabel: {
                show: true
              }
            }
          ],
          emphasis: {
            itemStyle: {
              borderColor: '#fac858',
              borderWidth: 2 // 悬停时显示边框以增强交互感
            },
            label: {
              show: true,
              fontSize: 14
            }
          }
        }]
      }
      this.treemapChart.setOption(option, true)
      // 添加点击事件
      this.treemapChart.off('click')
      this.treemapChart.on('click', (params) => {
        this.$emit('treemapClick', params)
      })
    },
    getFieldLabel(field) {
      const fieldMap = {
        'income': '收入',
        'refundAmount': '退款金额',
        'productCost': '商品成本',
        'firstLegCost': '头程费用',
        'channelFee': '渠道成交费',
        'logisticsShippingCost': '物流商运费',
        'usPlatformLabelFee': '美国平台面单费用',
        'otherCosts': '其他费用',
        'backendRefundSubsidy': '后台退款运费补贴',
        'advertisingFee': '广告费',
        'replenishmentCost': '补发费用',
        'packagingCost': '包装费用',
        'clearanceSubsidy': '清货补贴',
        'grossProfit': '毛利',
        'grossProfitRate': '毛利率',
        'distributionCommission': '分销佣金',
        'tdActualGrossProfit': 'TD实际毛利',
        'tdActualGrossProfitRate': 'TD实际毛利率',
        'productCostRatio': '商品成本占比',
        'firstLegCostRatio': '头程占比',
        'logisticsShippingCostRatio': '物流费占比',
        'refundRatio': '退款占比',
        'replenishmentCostRatio': '补发占比'
      }
      return fieldMap[field] || field
    },
    handleFilterChange(params) {
      this.filterParams = { ...params }
      this.loadData()
    },
    handleResize() {
      if (this.treemapChart) {
        this.treemapChart.resize()
      }
    },
    formatNumber(num) {
      const number = Number(num) || 0
      if (number >= 1000000) {
        return (number / 1000000).toFixed(1) + 'M'
      } else if (number >= 10000) {
        return (number / 10000).toFixed(1) + '万'
      }
      return number.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    }
  }
}
</script>

<style scoped>
.perspective-container {
  height: 100%;
  padding: 10px;
}

.perspective-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  height: 40px;
}

.title {
  font-size: 18px;
  font-weight: bold;
  color: #fff;
  text-shadow: 0 0 10px rgba(64, 158, 255, 0.5);
}

.chart-controls {
  display: flex;
  align-items: center;
  gap: 15px;
}

.field-selector {
  display: flex;
  align-items: center;
}

.selector-label {
  color: #fff;
  margin-right: 8px;
  font-size: 14px;
}

.field-selector .el-select {
  width: 180px;
}

.perspective-content {
  height: calc(100% - 50px);
  width: 100%;
}

.treemap-chart {
  width: 100%;
  height: 100%;
}

/* 响应式调整 */
@media screen and (max-width: 1600px) {
  .chart-controls {
    flex-direction: column;
    gap: 8px;
  }

  .field-selector {
    width: 100%;
  }
}

@media screen and (max-height: 800px) {
  .perspective-header {
    height: 60px;
    flex-direction: column;
    align-items: flex-start;
  }

  .chart-controls {
    width: 100%;
    justify-content: space-between;
  }
}
</style>

<style>
/* 美化el-select样式 - 与OperatorPerspective保持一致 */
.custom-select .el-input__inner {
  background: linear-gradient(135deg, rgba(30, 58, 104, 0.8), rgba(15, 32, 65, 0.9)) !important;
  border: 1px solid rgba(64, 158, 255, 0.4) !important;
  border-radius: 6px !important;
  color: #fff !important;
  height: 36px !important;
  line-height: 36px !important;
  font-size: 14px !important;
  transition: all 0.3s ease !important;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2) !important;
}

.custom-select .el-input__inner:hover {
  border-color: rgba(64, 158, 255, 0.7) !important;
  box-shadow: 0 2px 12px rgba(64, 158, 255, 0.3) !important;
}

.custom-select .el-input__inner:focus {
  border-color: #409eff !important;
  box-shadow: 0 0 8px rgba(64, 158, 255, 0.5) !important;
}

.custom-select .el-input__suffix {
  color: #a0c8ff !important;
}

.custom-select .el-input__icon {
  line-height: 36px !important;
}

/* 下拉框样式 */
.custom-select .el-select-dropdown {
  background: linear-gradient(135deg, rgba(30, 58, 104, 0.95), rgba(15, 32, 65, 0.98)) !important;
  border: 1px solid rgba(64, 158, 255, 0.4) !important;
  border-radius: 6px !important;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.3) !important;
  backdrop-filter: blur(10px) !important;
}

.custom-select .el-select-dropdown__item {
  color: #fff !important;
  background: transparent !important;
  font-size: 14px !important;
  height: 36px !important;
  line-height: 36px !important;
  transition: all 0.2s ease !important;
}

.custom-select .el-select-dropdown__item:hover {
  background: rgba(64, 158, 255, 0.2) !important;
  color: #a0c8ff !important;
}

.custom-select .el-select-dropdown__item.selected {
  background: rgba(64, 158, 255, 0.3) !important;
  color: #409eff !important;
  font-weight: bold !important;
}

/* 选中项高亮 */
.custom-select .el-select-dropdown__item.hover,
.custom-select .el-select-dropdown__item:hover {
  background: rgba(64, 158, 255, 0.15) !important;
}

/* 滚动条样式 */
.custom-select .el-select-dropdown .el-scrollbar__bar.is-vertical {
  width: 6px !important;
}

.custom-select .el-select-dropdown .el-scrollbar__thumb {
  background: rgba(64, 158, 255, 0.5) !important;
  border-radius: 3px !important;
}

.custom-select .el-select-dropdown .el-scrollbar__thumb:hover {
  background: rgba(64, 158, 255, 0.7) !important;
}

/* 空数据样式 */
.custom-select .el-select-dropdown__empty {
  color: #a0c8ff !important;
  padding: 20px !important;
  text-align: center !important;
}

/* 分组标题样式 */
.custom-select .el-select-group__title {
  color: #a0c8ff !important;
  font-size: 12px !important;
  padding: 8px 12px !important;
  border-bottom: 1px solid rgba(64, 158, 255, 0.2) !important;
}
</style>
