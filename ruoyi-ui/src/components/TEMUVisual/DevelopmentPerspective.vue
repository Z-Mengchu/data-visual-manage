<template>
  <dv-border-box-8 class="perspective-container">
    <div class="perspective-header">
      <span class="title">开发员数据</span>
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
        <div class="sort-controls">
          <el-select
            v-model="sortOrder"
            placeholder="排序方式"
            @change="handleSortChange"
            class="custom-select"
          >
            <el-option label="升序" value="asc" />
            <el-option label="降序" value="desc" />
          </el-select>
        </div>
      </div>
    </div>
    <div class="perspective-content">
      <div id="bar3dChart" class="bar3d-chart"></div>
    </div>
  </dv-border-box-8>
</template>

<script>
import * as echarts from 'echarts'
import {
  getDeveloperPerspectiveData,
  getDeveloperWarehouseData
} from '@/api/saleManage/temu/temuVisualization'

export default {
  name: 'DeveloperPerspective',
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
      selectedField: 'income',
      barChart: null,
      warehouseData: {}, // 存储开发员仓库数据
      fieldOptions: [
        { label: '收入（人民币）', value: 'income' },
        { label: '退款金额', value: 'refundAmount' },
        { label: '商品成本', value: 'productCost' },
        { label: '头程费用', value: 'firstLegCost' },
        { label: '渠道成交费（人民币）', value: 'channelFee' },
        { label: '物流商运费', value: 'logisticsShippingCost' },
        { label: '其他费用', value: 'otherCosts' },
        { label: '后台退款运费补贴', value: 'backendRefundSubsidy' },
        { label: '广告费', value: 'advertisingFee' },
        { label: '补发费用', value: 'replenishmentCost' },
        { label: '包装费用', value: 'packagingCost' },
        { label: '清货补贴', value: 'clearanceSubsidy' },
        { label: '毛利', value: 'grossProfit' },
        { label: '分销佣金', value: 'distributionCommission' },
        { label: 'TD实际毛利', value: 'tdActualGrossProfit' }
      ],
      colorPalette: [
        '#5470c6', '#91cc75', '#fac858', '#ee6666', '#73c0de',
        '#3ba272', '#fc8452', '#9a60b4', '#ea7ccc', '#5470c6',
        '#91cc75', '#fac858', '#ee6666', '#73c0de', '#3ba272'
      ]
    }
  },
  mounted() {
    this.initChart()
    this.loadData()
    this.loadWarehouseData()
  },
  beforeDestroy() {
    if (this.barChart) {
      this.barChart.dispose()
    }
    window.removeEventListener('resize', this.handleResize)
  },
  methods: {
    async loadData() {
      try {
        const params = {
          ...this.filterParams,
          sortField: this.selectedField,
          sortOrder: this.sortOrder
        }
        const response = await getDeveloperPerspectiveData(params)
        this.tableData = response.data || []
        this.updateChart()
      } catch (error) {
        console.error('加载开发员透视数据失败:', error)
      }
    },
    async loadWarehouseData() {
      try {
        const params = {
          ...this.filterParams
        }
        const response = await getDeveloperWarehouseData(params)
        const warehouseData = response.data || []

        // 按开发员分组仓库数据
        this.warehouseData = {}
        warehouseData.forEach(item => {
          const developer = item.groupName
          if (!this.warehouseData[developer]) {
            this.warehouseData[developer] = []
          }
          this.warehouseData[developer].push(item)
        })
      } catch (error) {
        console.error('加载开发员仓库数据失败:', error)
      }
    },
    initChart() {
      const chartDom = document.getElementById('bar3dChart')
      if (!chartDom) return

      this.barChart = echarts.init(chartDom)
      window.addEventListener('resize', this.handleResize)
    },
    updateChart() {
      if (!this.barChart || !this.tableData.length) return

      // 准备数据
      const barData = this.tableData.map((item, index) => {
        const value = item[this.selectedField] || 0
        return {
          name: item.groupName || `开发员${index + 1}`,
          value: Math.abs(value),
          originalValue: value
        }
      }).filter(item => item.value > 0)

      // 计算最大值
      const maxValue = Math.max(...barData.map(item => item.value))

      // 使用2D柱状图模拟3D效果
      const option = {
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          },
          formatter: (params) => {
            const data = params[0]
            const developerName = data.name
            const valueDisplay = this.formatNumber(data.data.originalValue)

            // 获取该开发员的仓库数据
            const warehouseData = this.warehouseData[developerName] || []

            let tooltipContent = `<div style="font-size: 14px; font-weight: bold; margin-bottom: 8px;">${developerName}</div>`

            // 主数据
            tooltipContent += `<div style="margin-bottom: 8px;">${this.getFieldLabel(this.selectedField)}: ¥${valueDisplay}</div>`

            // 如果有仓库数据，显示仓库细分
            if (warehouseData.length > 0) {
              tooltipContent += `<div style="margin-top: 8px; border-top: 1px solid #666; padding-top: 8px;">`
              tooltipContent += `<div style="font-size: 12px; font-weight: bold; margin-bottom: 4px;">发货仓库明细:</div>`

              warehouseData.forEach(warehouse => {
                const warehouseValue = warehouse[this.selectedField] || 0
                const warehouseValueDisplay = this.formatNumber(Math.abs(warehouseValue))

                tooltipContent += `<div style="font-size: 11px; display: flex; justify-content: space-between; margin-bottom: 2px;">
                  <span>${warehouse.warehouseName || '未知仓库'}:</span>
                  <span style="color: #fac858; margin-left: 8px;">¥${warehouseValueDisplay}</span>
                </div>`
              })

              tooltipContent += `</div>`
            }

            return tooltipContent
          },
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff',
            fontSize: 12
          },
          extraCssText: 'box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);'
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          top: '15%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          name: "",
          nameTextStyle: {
            color: '#fff',
            fontSize: 14
          },
          axisLine: {
            lineStyle: {
              color: '#fff'
            }
          },
          axisLabel: {
            color: '#fff',
            formatter: (value) => {
              return this.formatNumber(value)
            }
          },
          splitLine: {
            lineStyle: {
              color: 'rgba(255, 255, 255, 0.2)'
            }
          }
        },
        yAxis: {
          type: 'category',
          data: barData.map(item => item.name),
          axisLine: {
            lineStyle: {
              color: '#fff'
            }
          },
          axisLabel: {
            color: '#fff',
            fontSize: 12,
            interval: 0
          },
          axisTick: {
            show: false
          }
        },
        series: [
          // 主条形
          {
            name: this.getFieldLabel(this.selectedField),
            type: 'bar',
            data: barData.map((item, index) => ({
              value: item.value,
              originalValue: item.originalValue,
              itemStyle: {
                color: {
                  type: 'linear',
                  x: 0,
                  y: 0,
                  x2: 1,
                  y2: 0,
                  colorStops: [
                    {
                      offset: 0,
                      color: this.colorPalette[index % this.colorPalette.length]
                    },
                    {
                      offset: 1,
                      color: this.lightenColor(this.colorPalette[index % this.colorPalette.length], 30)
                    }
                  ]
                },
                borderRadius: [0, 4, 4, 0],
                shadowColor: 'rgba(0, 0, 0, 0.5)',
                shadowBlur: 8,
                shadowOffsetX: 2,
                shadowOffsetY: 2
              }
            })),
            barWidth: '60%',
            label: {
              show: true,
              position: 'right',
              formatter: (params) => {
                // 修改这里，显示原始值的正负号
                const isNegative = params.data.originalValue < 0;
                const symbol = isNegative ? '-' : '';
                return "¥" + symbol + this.formatNumber(params.data.value);
              },
              color: '#fff',
              fontSize: 12,
              backgroundColor: 'rgba(0, 0, 0, 0.5)',
              padding: [2, 6],
              borderRadius: 4
            },
            emphasis: {
              itemStyle: {
                shadowColor: 'rgba(255, 215, 0, 0.8)',
                shadowBlur: 12,
                borderWidth: 1,
                borderColor: '#ffd700'
              },
              label: {
                show: true,
                fontWeight: 'bold',
                color: '#ffd700'
              }
            }
          },
          // 3D效果 - 顶部高光
          {
            type: 'bar',
            data: barData.map((item, index) => ({
              value: item.value,
              itemStyle: {
                color: {
                  type: 'linear',
                  x: 0,
                  y: 0,
                  x2: 0,
                  y2: 1,
                  colorStops: [
                    {
                      offset: 0,
                      color: 'rgba(255, 255, 255, 0.3)'
                    },
                    {
                      offset: 1,
                      color: 'rgba(255, 255, 255, 0)'
                    }
                  ]
                }
              }
            })),
            barWidth: '60%',
            barGap: '-100%',
            z: 2
          },
          // 3D效果 - 右侧阴影
          {
            type: 'bar',
            data: barData.map((item, index) => ({
              value: item.value * 1.02,
              itemStyle: {
                color: {
                  type: 'linear',
                  x: 0,
                  y: 0,
                  x2: 1,
                  y2: 0,
                  colorStops: [
                    {
                      offset: 0,
                      color: 'rgba(0, 0, 0, 0.2)'
                    },
                    {
                      offset: 1,
                      color: 'rgba(0, 0, 0, 0.4)'
                    }
                  ]
                },
                borderRadius: [0, 4, 4, 0]
              }
            })),
            barWidth: '60%',
            barGap: '-100%',
            z: 1
          }
        ]
      }

      this.barChart.setOption(option, true)
    },
    lightenColor(color, percent) {
      const num = parseInt(color.replace("#", ""), 16)
      const amt = Math.round(2.55 * percent)
      const R = (num >> 16) + amt
      const G = (num >> 8 & 0x00FF) + amt
      const B = (num & 0x0000FF) + amt
      return "#" + (
        0x1000000 +
        (R < 255 ? R < 1 ? 0 : R : 255) * 0x10000 +
        (G < 255 ? G < 1 ? 0 : G : 255) * 0x100 +
        (B < 255 ? B < 1 ? 0 : B : 255)
      ).toString(16).slice(1)
    },
    getFieldLabel(field) {
      const fieldMap = {
        'income': '收入（人民币）',
        'refundAmount': '退款金额',
        'productCost': '商品成本',
        'firstLegCost': '头程费用',
        'channelFee': '渠道成交费（人民币）',
        'logisticsShippingCost': '物流商运费',
        'otherCosts': '其他费用',
        'backendRefundSubsidy': '后台退款运费补贴',
        'advertisingFee': '广告费',
        'replenishmentCost': '补发费用',
        'packagingCost': '包装费用',
        'clearanceSubsidy': '清货补贴',
        'grossProfit': '毛利',
        'distributionCommission': '分销佣金',
        'tdActualGrossProfit': 'TD实际毛利'
      }
      return fieldMap[field] || field
    },
    handleSortChange() {
      this.loadData()
      this.loadWarehouseData()
    },
    handleFilterChange(params) {
      this.filterParams = {...params}
      this.loadData()
      this.loadWarehouseData()
    },
    handleResize() {
      if (this.barChart) {
        this.barChart.resize()
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
  width: 100%;
}

.bar3d-chart {
  width: 100%;
  height: 100%;
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
