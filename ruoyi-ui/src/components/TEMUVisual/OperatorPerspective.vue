<template>
  <dv-border-box-8 class="perspective-container">
    <div class="perspective-header">
      <span class="title">运营员数据</span>
      <div class="chart-controls">
        <div class="field-selector">
          <span class="selector-label">数据维度:</span>
          <el-select
            v-model="selectedField"
            placeholder="选择数据维度"
            @change="updatePieChart"
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
      <div class="chart-container">
        <div id="pieChart" class="pie-chart"></div>
      </div>
      <div class="legend-container">
        <div class="legend-title">运营员数据明细</div>
        <div class="legend-list">
          <div
            v-for="(item, index) in pieChartData"
            :key="index"
            class="legend-item"
            @mouseenter="highlightPieChart(index)"
            @mouseleave="resetPieChartHighlight"
          >
            <div class="legend-color" :style="{ backgroundColor: getColor(index) }"></div>
            <div class="legend-name">{{ item.name }}</div>
            <div class="legend-value">{{ formatLegendValue(item.value) }}</div>
            <div class="legend-percentage">{{ item.percentage }}%</div>
          </div>
        </div>
      </div>
    </div>
  </dv-border-box-8>
</template>

<script>
import * as echarts from 'echarts'
import { getOperatorPerspectiveData, getOperatorWarehouseData  } from '@/api/saleManage/temu/temuVisualization'

export default {
  name: 'OperatorPerspective',
  props: {
    filterParams: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      tableData: [],
      sortOrder: 'desc',
      chartRadius: ['40%', '70%'], // 默认半径
      selectedField: 'income',
      pieChart: null,
      pieChartData: [],
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
  watch: {
    filterParams: {
      handler() {
        this.loadData()
        this.loadWarehouseData()
      },
      deep: true
    }
  },
  mounted() {
    this.initChart()
    this.loadData()
    this.loadWarehouseData()
    this.calculateRadius()
  },
  beforeDestroy() {
    if (this.pieChart) {
      this.pieChart.dispose()
    }
  },
  methods: {
    async loadData() {
      try {
        const params = {
          ...this.filterParams,
          sortOrder: this.sortOrder
        }
        const response = await getOperatorPerspectiveData(params)
        this.tableData = response.data || []
        this.updatePieChart()
      } catch (error) {
        console.error('加载运营员透视数据失败:', error)
      }
    },
    async loadWarehouseData() {
      try {
        const params = {
          ...this.filterParams
        }
        const response = await getOperatorWarehouseData(params)
        const warehouseData = response.data || []

        // 按运营员分组仓库数据
        this.warehouseData = {}
        warehouseData.forEach(item => {
          const operator = item.groupName
          if (!this.warehouseData[operator]) {
            this.warehouseData[operator] = []
          }
          this.warehouseData[operator].push(item)
        })
      } catch (error) {
        console.error('加载运营员仓库数据失败:', error)
      }
    },
    initChart() {
      const chartDom = document.getElementById('pieChart')
      if (!chartDom) return

      this.pieChart = echarts.init(chartDom)
    },
    calculateRadius() {
      const chartDom = document.getElementById('pieChart')
      if (!chartDom) return
      const containerWidth = chartDom.clientWidth
      const containerHeight = chartDom.clientHeight

      // 根据容器大小动态计算半径
      const minDimension = Math.min(containerWidth, containerHeight)

      if (minDimension < 300) {
        // 小屏幕
        this.chartRadius = ['10%', '30%']
      } else if (minDimension < 500) {
        // 中等屏幕
        this.chartRadius = ['20%', '40%']
      } else {
        // 大屏幕
        this.chartRadius = ['40%', '70%']
      }

      // 根据数据项数量调整半径
      const dataCount = this.pieChartData.length
      if (dataCount > 15) {
        // 数据项多时减小半径以避免重叠
        this.chartRadius = this.chartRadius.map(radius => {
          const value = parseInt(radius)
          return Math.max(value - 10, 20) + '%'
        })
      } else if (dataCount < 5) {
        // 数据项少时增大半径以填充空间
        this.chartRadius = this.chartRadius.map(radius => {
          const value = parseInt(radius)
          return Math.min(value + 10, 80) + '%'
        })
      }
    },

    updatePieChart() {
      if (!this.pieChart || !this.tableData.length) return

      // 准备饼图数据
      const totalValue = this.tableData.reduce((sum, item) => {
        const value = item[this.selectedField] || 0
        return sum + Math.abs(value) // 使用绝对值确保值为正
      }, 0)

      this.pieChartData = this.tableData.map((item, index) => {
        const value = item[this.selectedField] || 0
        const absoluteValue = Math.abs(value)
        const percentage = totalValue > 0 ? (absoluteValue / totalValue * 100).toFixed(2) : 0

        return {
          name: item.groupName || `运营员${index + 1}`,
          value: absoluteValue,
          percentage: percentage,
          originalValue: value
        }
      }).filter(item => item.value > 0) // 过滤掉值为0的数据

      // 设置饼图选项
      const option = {
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'item',
          formatter: (params) => {
            const operatorName = params.name
            const data = params.data

            // 获取该运营员的仓库数据
            const warehouseData = this.warehouseData[operatorName] || []

            let tooltipContent = `<div style="font-size: 14px; font-weight: bold; margin-bottom: 8px;">${operatorName}</div>`

            // 主数据
            const isPercentageField = this.selectedField.includes('Rate') || this.selectedField.includes('Ratio')
            const valueDisplay = isPercentageField ?
              `${(data.value * 100).toFixed(2)}%` :
              `¥${this.formatNumber(data.value)}`
            tooltipContent += `<div style="margin-bottom: 8px;">${this.getFieldLabel(this.selectedField)}: ${valueDisplay}<br/>占比: ${params.percent}%</div>`

            // 如果有仓库数据，显示仓库细分
            if (warehouseData.length > 0) {
              tooltipContent += `<div style="margin-top: 8px; border-top: 1px solid #666; padding-top: 8px;">`
              tooltipContent += `<div style="font-size: 12px; font-weight: bold; margin-bottom: 4px;">发货仓库明细:</div>`

              warehouseData.forEach(warehouse => {
                const warehouseValue = warehouse[this.selectedField] || 0
                const warehouseValueDisplay = isPercentageField ?
                  `${(warehouseValue * 100).toFixed(2)}%` :
                  `¥${this.formatNumber(Math.abs(warehouseValue))}`

                tooltipContent += `<div style="font-size: 11px; display: flex; justify-content: space-between; margin-bottom: 2px;">
                  <span>${warehouse.warehouseName || '未知仓库'}:</span>
                  <span style="color: #fac858; margin-left: 8px;">${warehouseValueDisplay}</span>
                </div>`
              })

              tooltipContent += `</div>`
            }

            return tooltipContent
          },
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff',
            fontSize: 14
          }
        },
        legend: {
          show: false
        },
        series: [
          {
            name: this.getFieldLabel(this.selectedField),
            type: 'pie',
            radius: this.chartRadius,
            center: ['50%', '40%'],
            avoidLabelOverlap: true,
            itemStyle: {
              borderColor: '#0a1d39',
              borderWidth: 2
            },
            label: {
              show: true,
              formatter: '{b}: {d}%',
              color: '#fff',
              fontSize: 12,
              fontWeight: 'bold'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 14,
                fontWeight: 'bold'
              },
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            },
            labelLine: {
              show: true,
              length: 20,
              length2: 30
            },
            data: this.pieChartData.map((item, index) => ({
              name: item.name,
              value: item.value,
              itemStyle: {
                color: this.getColor(index)
              }
            }))
          }
        ]
      }

      this.pieChart.setOption(option)

      // 添加点击事件
      this.pieChart.off('click')
      this.pieChart.on('click', (params) => {
        // this.$emit('pieChartClick', params)
      })
    },
    highlightPieChart(index) {
      if (this.pieChart) {
        this.pieChart.dispatchAction({
          type: 'highlight',
          seriesIndex: 0,
          dataIndex: index
        })
      }
    },
    resetPieChartHighlight() {
      if (this.pieChart) {
        this.pieChart.dispatchAction({
          type: 'downplay',
          seriesIndex: 0
        })
      }
    },
    getColor(index) {
      return this.colorPalette[index % this.colorPalette.length]
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
    formatLegendValue(value) {
      const isPercentageField = this.selectedField.includes('Rate') || this.selectedField.includes('Ratio')
      if (isPercentageField) {
        return (value * 100).toFixed(2) + '%'
      }
      return '¥' + this.formatNumber(value)
    },
    handleFilterChange(params) {
      this.filterParams = { ...params }
      this.loadData()
    },
    handleResize() {
      if (this.pieChart) {
        setTimeout(() => {
          this.pieChart.resize()
        }, 100)
      }
    },
    formatNumber(num) {
      const number = Number(num) || 0;
      return number.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }
  }
}
</script>

<style scoped>
.perspective-container {
  min-height: 0;;
  padding: 10px;
  display: flex;
  flex-direction: column;
}

.perspective-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
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

.sort-controls {
  display: flex;
  gap: 10px;
}

.sort-controls .el-select,
.field-selector .el-select {
  width: 180px;
}

.perspective-content {
  height: calc(100% - 50px);
  display: flex;
}

.chart-container {
  flex: 2;
  height: 100%;
  padding-right: 10px;
}

.legend-container {
  flex: 1;
  height: 100%;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 4px;
  padding: 10px;
  overflow-y: auto;
}

.legend-title {
  font-size: 14px;
  font-weight: bold;
  color: #fff;
  margin-bottom: 10px;
  text-align: center;
}

.legend-list {
  max-height: calc(100% - 30px);
  overflow-y: auto;
}

.legend-item {
  display: flex;
  align-items: center;
  padding: 8px 5px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  cursor: pointer;
  transition: background-color 0.3s;
}

.legend-item:hover {
  background: rgba(255, 255, 255, 0.1);
}

.legend-color {
  width: 12px;
  height: 12px;
  border-radius: 2px;
  margin-right: 8px;
}

.legend-name {
  flex: 1;
  font-size: 12px;
  color: #fff;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.legend-value {
  font-size: 11px;
  color: #fac858;
  font-weight: bold;
  margin: 0 5px;
  min-width: 70px;
  text-align: right;
}

.legend-percentage {
  font-size: 11px;
  color: #ffffff;
  font-weight: bold;
  min-width: 50px;
  text-align: right;
}

.pie-chart {
  width: 100%;
  height: 100%;
}

/* 滚动条样式 */
.legend-list::-webkit-scrollbar {
  width: 6px;
}

.legend-list::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.1);
  border-radius: 3px;
}

.legend-list::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.3);
  border-radius: 3px;
}

.legend-list::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.5);
}

/* 响应式调整 */
@media screen and (max-width: 1600px) {
  .chart-controls {
    flex-direction: column;
    gap: 8px;
  }

  .field-selector,
  .sort-controls {
    width: 100%;
  }

  .sort-controls {
    justify-content: space-between;
  }

  .sort-controls .el-select {
    width: 48%;
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
/* 美化el-select样式 */
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
