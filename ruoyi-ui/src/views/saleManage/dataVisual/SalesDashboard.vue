<!-- 顶部筛选区-->
<!--<div class="filter-area">
<el-row :gutter="20">
  <el-col :span="4">
    <el-select v-model="filterParams.paymentSettlementDate" placeholder="选择时间" clearable @change="handleFilterChange">
      <el-option
        v-for="item in filterOptions.dates"
        :key="item"
        :label="item"
        :value="item"
      />
    </el-select>
  </el-col>
  <el-col :span="4">
    <el-select v-model="filterParams.firstLevelCategory" placeholder="选择一级品类" clearable @change="handleFilterChange">
      <el-option
        v-for="item in filterOptions.firstLevelCategories"
        :key="item"
        :label="item"
        :value="item"
      />
    </el-select>
  </el-col>
  <el-col :span="4">
    <el-select v-model="filterParams.category" placeholder="选择品类" clearable @change="handleFilterChange">
      <el-option
        v-for="item in filterOptions.categories"
        :key="item"
        :label="item"
        :value="item"
      />
    </el-select>
  </el-col>
  <el-col :span="4">
    <el-select v-model="filterParams.channel" placeholder="选择渠道" clearable @change="handleFilterChange">
      <el-option
        v-for="item in filterOptions.channels"
        :key="item"
        :label="item"
        :value="item"
      />
    </el-select>
  </el-col>
  <el-col :span="4">
    <el-select v-model="filterParams.country" placeholder="选择国家" clearable @change="handleFilterChange">
      <el-option
        v-for="item in filterOptions.countries"
        :key="item"
        :label="item"
        :value="item"
      />
    </el-select>
  </el-col>
  <el-col :span="4">
    <el-button type="primary" @click="resetFilters">重置</el-button>
  </el-col>
</el-row>
</div>-->

<template>
  <div class="sales-dashboard">
    <dv-full-screen-container class="dashboard-container">
      <div class="d-flex jc-center">
        <dv-decoration-10 class="dv-dec-10" />
        <div class="d-flex jc-center">
          <dv-decoration-8 class="dv-dec-8" :color="decorationColor" />
          <div class="title">
            <span class="title-text">TEMU数据可视化大屏</span>
            <dv-decoration-6
              class="dv-dec-6"
              :reverse="true"
              :color="['#50e3c2', '#67a1e5']"
            />
          </div>
          <dv-decoration-8
            class="dv-dec-8"
            :reverse="true"
            :color="decorationColor"
          />
        </div>
        <dv-decoration-10 class="dv-dec-10-s" />
      </div>

      <!-- 第二行 -->
      <div class="d-flex jc-between px-2">
        <div class="d-flex aside-width">
          <div class="react-left ml-4 react-l-s">
            <span class="react-left ml-3"></span>
            <span class="text">收入统计</span>
          </div>
          <div class="react-left">
            <span class="text">毛利统计</span>
          </div>
        </div>
        <div class="d-flex aside-width">
          <div class="react-right bg-color-blue mr-3">
            <span class="text fw-b">净利统计</span>
          </div>
          <div class="react-right mr-4 react-l-s">
            <span class="react-after"></span>
            <span class="text">{{ dateYear }} {{ dateWeek }} {{ dateDay }}</span>
          </div>
        </div>
      </div>

      <!-- 核心KPI区 -->
      <div class="kpi-area">
        <el-row :gutter="20">
          <el-col :span="6">
            <dv-border-box-12>
              <div class="kpi-card">
                <div class="kpi-title">总销售收入</div>
                <div class="kpi-value">¥{{ formatNumber(kpiData.totalRevenue) }}</div>
              </div>
            </dv-border-box-12>
          </el-col>
          <el-col :span="6">
            <dv-border-box-12>
              <div class="kpi-card">
                <div class="kpi-title">总订单毛利</div>
                <div class="kpi-value">¥{{ formatNumber(kpiData.totalGrossProfit) }}</div>
                <div class="kpi-rate">毛利率: {{ formatPercentage(kpiData.grossProfitRate) }}%</div>
              </div>
            </dv-border-box-12>
          </el-col>
          <el-col :span="6">
            <dv-border-box-12>
              <div class="kpi-card">
                <div class="kpi-title">总订单净利</div>
                <div class="kpi-value">¥{{ formatNumber(kpiData.totalNetProfit) }}</div>
                <div class="kpi-rate">净利率: {{ formatPercentage(kpiData.netProfitRate) }}%</div>
              </div>
            </dv-border-box-12>
          </el-col>
          <el-col :span="6">
            <dv-border-box-12>
              <div class="kpi-card">
                <div class="kpi-title">平均ROI</div>
                <div class="kpi-value">{{ formatPercentage(kpiData.avgRoi) }}%</div>
              </div>
            </dv-border-box-12>
          </el-col>
        </el-row>
      </div>

      <!-- 主图表区 -->
      <div class="main-chart-area">
        <el-row :gutter="20">
          <!-- 左侧动态图表 -->
          <el-col :span="8">
            <dv-border-box-8>
              <div class="chart-container">
                <div class="chart-header">
                  <span>分析维度</span>
                  <div>
                    <el-select v-model="selectedDimension" size="small" @change="changeDimension">
                      <el-option label="品类退款率" value="categoryRefund" />
                      <el-option label="SKU ROI" value="skuRoi" />
                      <el-option label="一级品类" value="firstLevelCategory" />
                      <el-option label="渠道分析" value="channel" />
                      <el-option label="品牌分析" value="brand" />
                      <el-option label="国家分析" value="country" />
                      <el-option label="店铺分析" value="store" />
                    </el-select>
                  </div>
                </div>
                <div class="chart-content">
                  <!-- 品类退款率图表 -->
                  <div v-if="selectedDimension === 'categoryRefund'" id="categoryRefundChart" style="width: 100%; height: 300px;"></div>
                  <!-- SKU ROI图表 -->
                  <div v-if="selectedDimension === 'skuRoi'" id="skuRoiChart" style="width: 100%; height: 300px;"></div>
                  <!-- 一级品类图表 -->
                  <div v-if="selectedDimension === 'firstLevelCategory'" id="firstLevelCategoryChart" style="width: 100%; height: 300px;"></div>
                  <!-- 渠道分析图表 -->
                  <div v-if="selectedDimension === 'channel'" id="channelChart" style="width: 100%; height: 300px;"></div>
                  <!-- 品牌分析图表 -->
                  <div v-if="selectedDimension === 'brand'" id="brandChart" style="width: 100%; height: 300px;"></div>
                  <!-- 国家分析图表 -->
                  <div v-if="selectedDimension === 'country'" id="countryChart" style="width: 100%; height: 300px;"></div>
                  <!-- 店铺分析图表 -->
                  <div v-if="selectedDimension === 'store'" id="storeChart" style="width: 100%; height: 300px;"></div>
                </div>
              </div>
            </dv-border-box-8>
          </el-col>

          <!-- 中间预警面板 -->
          <el-col :span="8">
            <dv-border-box-8>
              <div class="chart-container" style="height: 340px;">
                <div class="chart-header">
                  <span>预警面板</span>
                </div>
                <div class="alert-content">
                  <div class="alert-scroll-container" id="alertScrollContainer">
                    <div
                      v-for="(alert, index) in sortedAlertData"
                      :key="index"
                      class="alert-item"
                    >
                      <span class="alert-type">{{ alert.alertType }}:</span>
                      <span class="alert-item-name">{{ alert.item }}</span>
                      <span class="alert-value" :class="{'negative': alert.value < 0}">
                        {{ formatAlertValue(alert.value, alert.alertType) }}
                      </span>
                    </div>
                    <!-- 复制一份数据以实现无缝滚动 -->
                    <div
                      v-for="(alert, index) in sortedAlertData"
                      :key="'copy-' + index"
                      class="alert-item"
                    >
                      <span class="alert-type">{{ alert.alertType }}:</span>
                      <span class="alert-item-name">{{ alert.item }}</span>
                      <span class="alert-value" :class="{'negative': alert.value < 0}">
                        {{ formatAlertValue(alert.value, alert.alertType) }}
                      </span>
                    </div>
                    <div v-if="sortedAlertData.length === 0" class="no-alerts">
                      暂无预警信息
                    </div>
                  </div>
                </div>
              </div>
            </dv-border-box-8>
          </el-col>

          <!-- 右侧全球销售分布图 -->
          <el-col :span="8">
            <dv-border-box-8>
              <div class="chart-container">
                <div class="chart-header">
                  <span>全球销售分布</span>
                </div>
                <div id="mapChart" style="width: 100%; height: 300px;"></div>
              </div>
            </dv-border-box-8>
          </el-col>
        </el-row>
      </div>

      <!-- 趋势图区和渠道分布图区 -->
      <div class="combined-chart-area">
        <el-row :gutter="20">
          <!-- 渠道销售分布图 -->
          <el-col :span="12">
            <dv-border-box-8>
              <div class="chart-container">
                <div class="chart-header">
                  <span>渠道销售分布</span>
                </div>
                <div id="channelDistributionChart" style="width: 100%; height: 200px;"></div>
              </div>
            </dv-border-box-8>
          </el-col>

          <!-- 销售趋势图 -->
          <el-col :span="12">
            <dv-border-box-8>
              <div class="chart-container">
                <div class="chart-header">
                  <span>销售趋势</span>
                </div>
                <div id="trendChart" style="width: 100%; height: 200px;"></div>
              </div>
            </dv-border-box-8>
          </el-col>
        </el-row>
      </div>
    </dv-full-screen-container>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import '@/common/echart/world_fix'
import { getKpiData, getCategoryRefundData, getSkuRoiData, getFirstLevelCategoryData,
  getChannelData, getBrandData, getCountryData, getStoreData, getMapData,
  getChannelDistributionData, getAlertData, getTrendData, getDetailData,
  getFilterOptions } from '@/api/saleManage/salesData/visual'
import { formatTime2 } from "@/utils";

export default {
  name: 'SalesDashboard',
  data() {
    return {
      decorationColor: ['#568aea', '#000000'],
      timing: null,
      dateDay: null,
      dateYear: null,
      dateWeek: null,
      weekday: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
      filterParams: {
        paymentSettlementDate: '',
        firstLevelCategory: '',
        category: '',
        channel: '',
        country: ''
      },
      filterOptions: {
        dates: [],
        firstLevelCategories: [],
        categories: [],
        channels: [],
        countries: []
      },
      selectedDimension: 'categoryRefund',
      dimensionList: ['categoryRefund', 'skuRoi', 'firstLevelCategory', 'channel', 'brand', 'country', 'store'],
      kpiData: {
        totalRevenue: 0,
        totalGrossProfit: 0,
        totalNetProfit: 0,
        grossProfitRate: 0,
        netProfitRate: 0,
        avgRoi: 0
      },
      categoryRefundData: [],
      skuRoiData: [],
      firstLevelCategoryData: [],
      channelData: [],
      brandData: [],
      countryData: [],
      storeData: [],
      mapData: [],
      channelDistributionData: [],
      alertData: [],
      trendData: [],
      detailData: [],
      analysisTableData: [],
      analysisTableColumns: [],
      charts: {},
      resizeTimer: null,
      isLoading: true,
      currentChartInstance: null,
      zoomLevel: 1, // 放大级别
      isZoomed: false, // 是否处于放大状态
    }
  },
  computed: {
    sortedAlertData() {
      // 按照退款率从大到小排序
      return [...this.alertData].sort((a, b) => {
        if (a.alertType === '高退款率' && b.alertType === '高退款率') {
          return b.value - a.value;
        }
        // 保持其他类型的顺序不变
        return 0;
      });
    }
  },
  mounted() {
    this.timeFn();
    this.initData()
    window.addEventListener('resize', this.handleResize)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.handleResize)
    Object.values(this.charts).forEach(chart => {
      if (chart) {
        chart.dispose()
      }
    })
    if (this.currentChartInstance) {
      this.currentChartInstance.dispose()
    }
    clearInterval(this.timing)
    // 清理表格滚动定时器
    if (this.tableScrollInterval) {
      clearInterval(this.tableScrollInterval);
      this.tableScrollInterval = null;
    }
  },
  methods: {
    timeFn() {
      this.timing = setInterval(() => {
        this.dateDay = formatTime2(new Date(), 'HH: mm: ss')
        this.dateYear = formatTime2(new Date(), 'yyyy-MM-dd')
        this.dateWeek = this.weekday[new Date().getDay()]
      }, 1000)
    },
    async initData() {
      this.isLoading = true;
      try {
        await this.loadFilterOptions()
        await this.loadAllData()
        this.$nextTick(() => {
          this.initCharts()
        })
      } catch (error) {
        console.error('初始化数据失败:', error)
      } finally {
        this.isLoading = false;
      }
    },
    async loadFilterOptions() {
      try {
        const response = await getFilterOptions()
        this.filterOptions = {
          dates: response.data.dates ? response.data.dates.split(',') : [],
          firstLevelCategories: response.data.firstLevelCategories ? response.data.firstLevelCategories.split(',') : [],
          categories: response.data.categories ? response.data.categories.split(',') : [],
          channels: response.data.channels ? response.data.channels.split(',') : [],
          countries: response.data.countries ? response.data.countries.split(',') : []
        }
      } catch (error) {
        console.error('加载筛选选项失败:', error)
      }
    },
    async loadAllData() {
      try {
        await Promise.all([
          this.loadKpiData(),
          this.loadCategoryRefundData(),
          this.loadSkuRoiData(),
          this.loadFirstLevelCategoryData(),
          this.loadChannelData(),
          this.loadBrandData(),
          this.loadCountryData(),
          this.loadStoreData(),
          this.loadMapData(),
          this.loadChannelDistributionData(),
          this.loadAlertData(),
          this.loadTrendData(),
          this.loadDetailData()
        ])
      } catch (error) {
        console.error('加载数据失败:', error)
      }
    },
    async loadKpiData() {
      try {
        const response = await getKpiData(this.filterParams)
        this.kpiData = response.data || {
          totalRevenue: 0,
          totalGrossProfit: 0,
          totalNetProfit: 0,
          grossProfitRate: 0,
          netProfitRate: 0,
          avgRoi: 0
        }
      } catch (error) {
        console.error('加载KPI数据失败:', error)
        this.kpiData = {
          totalRevenue: 0,
          totalGrossProfit: 0,
          totalNetProfit: 0,
          grossProfitRate: 0,
          netProfitRate: 0,
          avgRoi: 0
        }
      }
    },
    async loadCategoryRefundData() {
      try {
        const response = await getCategoryRefundData(this.filterParams)
        this.categoryRefundData = response.data || []
      } catch (error) {
        console.error('加载品类退款率数据失败:', error)
        this.categoryRefundData = []
      }
    },
    async loadSkuRoiData() {
      try {
        const response = await getSkuRoiData(this.filterParams)
        this.skuRoiData = response.data || []
      } catch (error) {
        console.error('加载SKU ROI数据失败:', error)
        this.skuRoiData = []
      }
    },
    async loadFirstLevelCategoryData() {
      try {
        const response = await getFirstLevelCategoryData(this.filterParams)
        this.firstLevelCategoryData = response.data || []
      } catch (error) {
        console.error('加载一级品类数据失败:', error)
        this.firstLevelCategoryData = []
      }
    },
    async loadChannelData() {
      try {
        const response = await getChannelData(this.filterParams)
        this.channelData = response.data || []
      } catch (error) {
        console.error('加载渠道数据失败:', error)
        this.channelData = []
      }
    },
    async loadBrandData() {
      try {
        const response = await getBrandData(this.filterParams)
        this.brandData = response.data || []
      } catch (error) {
        console.error('加载品牌数据失败:', error)
        this.brandData = []
      }
    },
    async loadCountryData() {
      try {
        const response = await getCountryData(this.filterParams)
        this.countryData = response.data || []
      } catch (error) {
        console.error('加载国家数据失败:', error)
        this.countryData = []
      }
    },
    async loadStoreData() {
      try {
        const response = await getStoreData(this.filterParams)
        this.storeData = response.data || []
      } catch (error) {
        console.error('加载店铺数据失败:', error)
        this.storeData = []
      }
    },
    async loadMapData() {
      try {
        const response = await getMapData(this.filterParams)
        this.mapData = response.data || []
      } catch (error) {
        console.error('加载地图数据失败:', error)
        this.mapData = []
      }
    },
    async loadChannelDistributionData() {
      try {
        const response = await getChannelDistributionData(this.filterParams)
        this.channelDistributionData = response.data || []
      } catch (error) {
        console.error('加载渠道分布数据失败:', error)
        this.channelDistributionData = []
      }
    },
    async loadAlertData() {
      try {
        // 使用品类退款率数据作为预警数据
        const response = await getCategoryRefundData(this.filterParams);
        this.alertData = response.data.map(item => ({
          alertType: '高退款率',
          item: item.category || '未知',
          value: item.refundRate
        })).sort((a, b) => b.value - a.value) // 按退款率从高到低排序
      } catch (error) {
        console.error('加载预警数据失败:', error);
        this.alertData = [];
      }
    },
    async loadTrendData() {
      try {
        const response = await getTrendData(this.filterParams)
        this.trendData = response.data || []
      } catch (error) {
        console.error('加载趋势数据失败:', error)
        this.trendData = []
      }
    },
    async loadDetailData() {
      try {
        const response = await getDetailData(this.filterParams)
        this.detailData = response.data || []
      } catch (error) {
        console.error('加载详情数据失败:', error)
        this.detailData = []
      }
    },
    initCharts() {
      switch(this.selectedDimension) {
        case 'categoryRefund':
          this.initCategoryRefundChart()
          break
        case 'skuRoi':
          this.initSkuRoiParallelChart()
          break
        case 'firstLevelCategory':
          this.initFirstLevelCategoryChart()
          break
        case 'channel':
          this.initChannelChart()
          break
        case 'brand':
          this.initBrandChart()
          break
        case 'country':
          this.initCountryChart()
          break
        case 'store':
          this.initStoreChart()
          break
      }
      this.initMapChart()
      this.initChannelDistributionChart()
      this.initTrendChart()
    },
    initCategoryRefundChart() {
      if (this.currentChartInstance) {
        this.currentChartInstance.dispose()
      }
      const chartDom = document.getElementById('categoryRefundChart')
      if (!chartDom) {
        setTimeout(() => this.initCategoryRefundChart(), 100)
        return
      }
      if (!this.categoryRefundData || this.categoryRefundData.length === 0) {
        return
      }
      this.currentChartInstance = echarts.init(chartDom)
      const option = {
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'axis',
          axisPointer: { type: 'cross' },
          formatter: (params) => {
            const category = params[0].name
            const revenue = params[0].value
            const refund = params[1].value
            const refundRate = params[2].value
            return `品类: ${category}<br/>收入: ¥${revenue}<br/>退款金额: ¥${refund}<br/>退款率: ${(refundRate * 100).toFixed(2)}%`
          },
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff',
            fontSize: 14,
            fontWeight: 'bold'
          }
        },
        // 添加dataZoom配置
        dataZoom: [
          {
            type: 'inside',
            start: 0,
            end: 100,
            zoomLock: this.isZoomed,
            zoomOnMouseWheel: true,
            moveOnMouseMove: true,
            moveOnMouseWheel: false
          },
          {
            type: 'slider',
            show: this.isZoomed,
            start: 0,
            end: 100,
            bottom: 10,
            height: 20,
            backgroundColor: 'rgba(47,69,84,0.3)',
            fillerColor: 'rgba(82,196,26,0.6)',
            borderColor: 'rgba(82,196,26,0.6)',
            textStyle: {
              color: '#fff'
            }
          }
        ],
        legend: {
          data: ['收入', '退款金额', '退款率'],
          textStyle: {
            color: '#fff',
            fontSize: 12
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '10%',
          top: '15%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: this.categoryRefundData.map(item => item.category || '未知'),
          axisLabel: {
            rotate: 45,
            interval: 0,
            textStyle: {
              color: '#fff',
              fontSize: 12
            }
          }
        },
        yAxis: [
          {
            type: 'value',
            name: '金额',
            nameTextStyle: {
              color: '#fff',
              fontSize: 12
            },
            axisLabel: {
              formatter: '¥{value}',
              textStyle: {
                color: '#fff',
                fontSize: 12
              }
            }
          },
          {
            type: 'value',
            name: '退款率',
            nameTextStyle: {
              color: '#fff',
              fontSize: 12
            },
            min: 0,
            max: 1,
            axisLabel: {
              formatter: '{value}%',
              textStyle: {
                color: '#fff',
                fontSize: 12
              }
            }
          }
        ],
        series: [
          {
            name: '收入',
            type: 'bar',
            data: this.categoryRefundData.map(item => item.revenue),
            itemStyle: {
              color: '#5470c6'
            }
          },
          {
            name: '退款金额',
            type: 'bar',
            data: this.categoryRefundData.map(item => item.refundAmount),
            itemStyle: {
              color: '#ee6666'
            }
          },
          {
            name: '退款率',
            type: 'line',
            yAxisIndex: 1,
            data: this.categoryRefundData.map(item => item.refundRate),
            itemStyle: {
              color: '#fac858'
            },
            lineStyle: {
              width: 3
            }
          }
        ]
      }
      this.currentChartInstance.setOption(option)
    },
    initSkuRoiParallelChart() {
      if (this.currentChartInstance) {
        this.currentChartInstance.dispose()
      }
      const chartDom = document.getElementById('skuRoiChart')
      if (!chartDom) return
      if (!this.skuRoiData || this.skuRoiData.length === 0) {
        return
      }
      this.currentChartInstance = echarts.init(chartDom)

      // 准备平行坐标系数据
      const dimensions = [
        { name: '发货数量', type: 'number' },
        { name: '商品成本', type: 'number' },
        { name: '头程运费', type: 'number' },
        { name: '订单净利', type: 'number' },
        { name: 'ROI', type: 'number' },
        { name: '回正天数', type: 'number' }
      ]

      const data = this.skuRoiData.map(item => [
        item.shippingQuantity || 0,
        item.productCost || 0,
        item.firstMileShipping || 0,
        item.orderNetProfit || 0,
        item.roi || 0,
        item.daysToPositiveCashFlow || 0
      ])

      const option = {
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'item',
          formatter: (params) => {
            const sku = this.skuRoiData[params.dataIndex]?.tongtuSku || '未知SKU'
            const values = params.value
            return `SKU: ${sku}<br/>
                    发货数量: ${values[0]}<br/>
                    商品成本: ¥${values[1].toFixed(2)}<br/>
                    头程运费: ¥${values[2].toFixed(2)}<br/>
                    订单净利: ¥${values[3].toFixed(2)}<br/>
                    ROI: ${(values[4] * 100).toFixed(2)}%<br/>
                    回正天数: ${values[5]}天`
          },
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff',
            fontSize: 14,
            fontWeight: 'bold'
          }
        },
        dataZoom: [
          {
            type: 'inside',
            orient: 'horizontal',
            start: 0,
            end: 100,
            zoomLock: this.isZoomed,
            zoomOnMouseWheel: true
          },
          {
            type: 'inside',
            orient: 'vertical',
            start: 0,
            end: 100,
            zoomLock: this.isZoomed,
            zoomOnMouseWheel: true
          },
          {
            type: 'slider',
            show: this.isZoomed,
            orient: 'horizontal',
            start: 0,
            end: 100,
            bottom: 10,
            height: 20
          },
          {
            type: 'slider',
            show: this.isZoomed,
            orient: 'vertical',
            start: 0,
            end: 100,
            right: 10,
            width: 20
          }
        ],
        parallelAxis: [
          { dim: 0, name: '发货数量', nameTextStyle: { color: '#fff' }, axisLabel: { textStyle: { color: '#fff' } } },
          { dim: 1, name: '商品成本', nameTextStyle: { color: '#fff' }, axisLabel: {
              formatter: '¥{value}',
              textStyle: { color: '#fff' }
            }},
          { dim: 2, name: '头程运费', nameTextStyle: { color: '#fff' }, axisLabel: {
              formatter: '¥{value}',
              textStyle: { color: '#fff' }
            }},
          { dim: 3, name: '订单净利', nameTextStyle: { color: '#fff' }, axisLabel: {
              formatter: '¥{value}',
              textStyle: { color: '#fff' }
            }},
          { dim: 4, name: 'ROI', nameTextStyle: { color: '#fff' }, axisLabel: { textStyle: { color: '#fff' } } },
          { dim: 5, name: '回正天数', nameTextStyle: { color: '#fff' }, axisLabel: { textStyle: { color: '#fff' } } }
        ],
        series: [
          {
            name: 'SKU ROI',
            type: 'parallel',
            lineStyle: {
              width: 1,
              color: (params) => {
                return params.data[3] > 0 ? '#91cc75' : '#ee6666'
              }
            },
            data: data
          }
        ]
      }
      this.currentChartInstance.setOption(option)
    },
    initFirstLevelCategoryChart() {
      if (this.currentChartInstance) {
        this.currentChartInstance.dispose()
      }
      const chartDom = document.getElementById('firstLevelCategoryChart')
      if (!chartDom) return
      if (!this.firstLevelCategoryData || this.firstLevelCategoryData.length === 0) {
        return
      }
      this.currentChartInstance = echarts.init(chartDom)
      const option = {
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'axis',
          axisPointer: { type: 'cross' },
          formatter: (params) => {
            const category = params[0].name
            const revenue = params[0].value
            const grossProfit = params[1].value
            const netProfit = params[2].value
            const roi = params[3].value
            const days = params[4].value
            return `一级品类: ${category}<br/>收入: ¥${revenue}<br/>订单毛利: ¥${grossProfit}<br/>订单净利: ¥${netProfit}<br/>ROI: ${(roi * 100).toFixed(2)}%<br/>回正天数: ${days}天`
          },
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff',
            fontSize: 14,
            fontWeight: 'bold'
          }
        },
        legend: {
          data: ['收入', '订单毛利', '订单净利', 'ROI', '回正天数'],
          textStyle: {
            color: '#fff',
            fontSize: 12
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '10%',
          top: '15%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: this.firstLevelCategoryData.map(item => item.firstLevelCategory || '未知'),
          axisLabel: {
            rotate: 45,
            interval: 0,
            textStyle: {
              color: '#fff',
              fontSize: 12
            }
          },
          axisLine: {
            lineStyle: {
              color: '#fff'
            }
          }
        },
        yAxis: [
          {
            type: 'value',
            name: '金额',
            nameTextStyle: {
              color: '#fff',
              fontSize: 12
            },
            axisLabel: {
              formatter: '¥{value}',
              textStyle: {
                color: '#fff',
                fontSize: 12
              }
            },
            axisLine: {
              lineStyle: {
                color: '#fff'
              }
            },
            splitLine: {
              lineStyle: {
                color: 'rgba(255,255,255,0.2)'
              }
            }
          },
          {
            type: 'value',
            name: '比率/天数',
            nameTextStyle: {
              color: '#fff',
              fontSize: 12
            },
            axisLabel: {
              textStyle: {
                color: '#fff',
                fontSize: 12
              }
            },
            axisLine: {
              lineStyle: {
                color: '#fff'
              }
            },
            splitLine: {
              show: false
            }
          }
        ],
        series: [
          {
            name: '收入',
            type: 'bar',
            data: this.firstLevelCategoryData.map(item => item.revenue),
            itemStyle: {
              color: '#5470c6'
            }
          },
          {
            name: '订单毛利',
            type: 'bar',
            data: this.firstLevelCategoryData.map(item => item.orderGrossProfit),
            itemStyle: {
              color: '#91cc75'
            }
          },
          {
            name: '订单净利',
            type: 'bar',
            data: this.firstLevelCategoryData.map(item => item.orderNetProfit),
            itemStyle: {
              color: '#fac858'
            }
          },
          {
            name: 'ROI',
            type: 'line',
            yAxisIndex: 1,
            data: this.firstLevelCategoryData.map(item => item.roi),
            itemStyle: {
              color: '#ee6666'
            },
            lineStyle: {
              width: 3
            }
          },
          {
            name: '回正天数',
            type: 'line',
            yAxisIndex: 1,
            data: this.firstLevelCategoryData.map(item => item.daysToPositiveCashFlow),
            itemStyle: {
              color: '#73c0de'
            },
            lineStyle: {
              width: 3,
              type: 'dashed'
            }
          }
        ]
      }
      this.currentChartInstance.setOption(option)
    },
    initChannelChart() {
      if (this.currentChartInstance) {
        this.currentChartInstance.dispose()
      }
      const chartDom = document.getElementById('channelChart')
      if (!chartDom) return
      if (!this.channelData || this.channelData.length === 0) {
        return
      }
      this.currentChartInstance = echarts.init(chartDom)

      const option = {
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'item',
          formatter: (params) => {
            return `渠道: ${params.name}<br/>收入: ￥${params.value}<br/>订单毛利: ￥${params.data.orderGrossProfit}<br/>毛利率: ${(params.data.grossProfitRate * 100).toFixed(2)}%<br/>订单净利: ￥${params.data.orderNetProfit}<br/>订单净毛利: ${(params.data.netProfitRate * 100).toFixed(2)}%<br/>ROI: ${(params.data.roi * 100).toFixed(2)}%<br/>回正天数: ${params.data.daysToPositiveCashFlow}`
          },
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff',
            fontSize: 14,
            fontWeight: 'bold'
          }
        },
        legend: {
          orient: 'vertical',
          right: 10,
          top: 'center',
          textStyle: {
            color: '#fff'
          }
        },
        series: [
          {
            name: '渠道收入',
            type: 'pie',
            radius: ['40%', '70%'],
            center: ['25%', '50%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#0a1d39',
              borderWidth: 2
            },
            label: {
              show: true,
              color: '#fff',
              fontWeight: 'bold'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '14',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: true
            },
            data: this.channelData.map(item => {
              return {
                value: item.revenue,
                orderGrossProfit: item.orderGrossProfit,
                grossProfitRate: item.grossProfitRate,
                orderNetProfit: item.orderNetProfit,
                netProfitRate: item.netProfitRate,
                roi: item.roi,
                daysToPositiveCashFlow: item.daysToPositiveCashFlow,
                name: item.channel || '未知'
              }
            })
          }
        ]
      }
      this.currentChartInstance.setOption(option)
    },
    initBrandChart() {
      if (this.currentChartInstance) {
        this.currentChartInstance.dispose()
      }
      const chartDom = document.getElementById('brandChart')
      if (!chartDom) return
      if (!this.brandData || this.brandData.length === 0) {
        return
      }
      this.currentChartInstance = echarts.init(chartDom)

      const option = {
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'item',
          formatter: (params) => {
            return `品牌: ${params.name}<br/>收入: ￥${params.value}<br/>订单毛利: ￥${params.data.orderGrossProfit}<br/>毛利率: ${(params.data.grossProfitRate * 100).toFixed(2)}%<br/>订单净利: ￥${params.data.orderNetProfit}<br/>订单净毛利: ${(params.data.netProfitRate * 100).toFixed(2)}%<br/>ROI: ${(params.data.roi * 100).toFixed(2)}%<br/>回正天数: ${params.data.daysToPositiveCashFlow}`
          },
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff',
            fontSize: 14,
            fontWeight: 'bold'
          }
        },
        legend: {
          orient: 'vertical',
          right: 10,
          top: 'center',
          textStyle: {
            color: '#fff'
          }
        },
        series: [
          {
            name: '品牌收入',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#0a1d39',
              borderWidth: 2
            },
            label: {
              show: true,
              formatter: '{b}: {d}%',
              color: '#fff',
              fontWeight: 'bold'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '14',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: true
            },
            data: this.brandData.map(item => {
              return {
                value: item.revenue,
                orderGrossProfit: item.orderGrossProfit,
                grossProfitRate: item.grossProfitRate,
                orderNetProfit: item.orderNetProfit,
                netProfitRate: item.netProfitRate,
                roi: item.roi,
                daysToPositiveCashFlow: item.daysToPositiveCashFlow,
                name: item.brand || '未知'
              }
            })
          }
        ]
      }
      this.currentChartInstance.setOption(option)
    },
    initCountryChart() {
      if (this.currentChartInstance) {
        this.currentChartInstance.dispose()
      }
      const chartDom = document.getElementById('countryChart')
      if (!chartDom) return
      if (!this.countryData || this.countryData.length === 0) {
        return
      }
      this.currentChartInstance = echarts.init(chartDom)

      const option = {
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'item',
          formatter: (params) => {
            return `国家: ${params.name}<br/>收入: ￥${params.value}<br/>订单毛利: ￥${params.data.orderGrossProfit}<br/>毛利率: ${(params.data.grossProfitRate * 100).toFixed(2)}%<br/>订单净利: ￥${params.data.orderNetProfit}<br/>订单净毛利: ${(params.data.netProfitRate * 100).toFixed(2)}%<br/>ROI: ${(params.data.roi * 100).toFixed(2)}%<br/>回正天数: ${params.data.daysToPositiveCashFlow}`
          },
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff',
            fontSize: 14,
            fontWeight: 'bold'
          }
        },
        legend: {
          orient: 'vertical',
          right: 10,
          top: 'center',
          textStyle: {
            color: '#fff'
          }
        },
        series: [
          {
            name: '国家收入',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#0a1d39',
              borderWidth: 2
            },
            label: {
              show: true,
              formatter: '{b}: {d}%',
              color: '#fff',
              fontWeight: 'bold'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '14',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: true
            },
            data: this.countryData.map(item => {
              return {
                value: item.revenue,
                orderGrossProfit: item.orderGrossProfit,
                grossProfitRate: item.grossProfitRate,
                orderNetProfit: item.orderNetProfit,
                netProfitRate: item.netProfitRate,
                roi: item.roi,
                daysToPositiveCashFlow: item.daysToPositiveCashFlow,
                name: item.country || '未知'
              }
            })
          }
        ]
      }
      this.currentChartInstance.setOption(option)
    },
    initStoreChart() {
      if (this.currentChartInstance) {
        this.currentChartInstance.dispose()
      }
      const chartDom = document.getElementById('storeChart')
      if (!chartDom) return
      if (!this.storeData || this.storeData.length === 0) {
        return
      }
      this.currentChartInstance = echarts.init(chartDom)

      const option = {
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'axis',
          axisPointer: { type: 'cross' },
          formatter: (params) => {
            params = params[0]
            return `店铺: ${params.name}<br/>收入: ￥${params.value}<br/>订单毛利: ￥${params.data.orderGrossProfit}<br/>毛利率: ${(params.data.grossProfitRate * 100).toFixed(2)}%<br/>订单净利: ￥${params.data.orderNetProfit}<br/>订单净毛利: ${(params.data.netProfitRate * 100).toFixed(2)}%<br/>ROI: ${(params.data.roi * 100).toFixed(2)}%<br/>回正天数: ${params.data.daysToPositiveCashFlow}`
          },
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff',
            fontSize: 14,
            fontWeight: 'bold'
          }
        },
        // 添加dataZoom配置
        dataZoom: [
          {
            type: 'inside',
            start: 0,
            end: 100,
            zoomLock: this.isZoomed,
            zoomOnMouseWheel: true,
            moveOnMouseMove: true,
            moveOnMouseWheel: false
          },
          {
            type: 'slider',
            show: this.isZoomed,
            start: 0,
            end: 100,
            bottom: 10,
            height: 20,
            backgroundColor: 'rgba(47,69,84,0.3)',
            fillerColor: 'rgba(82,196,26,0.6)',
            borderColor: 'rgba(82,196,26,0.6)',
            textStyle: {
              color: '#fff'
            }
          }
        ],
        grid: {
          left: '3%',
          right: '4%',
          bottom: '10%',
          top: '15%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: this.storeData.map(item => item.storeCode || '未知'),
          axisLabel: {
            rotate: 45,
            textStyle: {
              color: '#fff',
              fontSize: 12
            }
          }
        },
        yAxis: {
          type: 'value',
          name: '收入',
          nameTextStyle: {
            color: '#fff',
            fontSize: 12
          }
        },
        axisLabel: {
          formatter: '¥{value}',
          textStyle: {
            color: '#fff',
            fontSize: 12
          }
        },
        splitLine: {
          lineStyle: {
            color: 'rgba(255,255,255,0.2)'
          }
        },
        series: [
          {
            name: '收入',
            type: 'bar',
            data: this.storeData.map(item => {
              return {
                name: item.storeCode || '未知',
                value: item.revenue,
                orderGrossProfit: item.orderGrossProfit,
                grossProfitRate: item.grossProfitRate,
                orderNetProfit: item.orderNetProfit,
                netProfitRate: item.netProfitRate,
                roi: item.roi,
                daysToPositiveCashFlow: item.daysToPositiveCashFlow,
                itemStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: '#fac858' },
                  { offset: 1, color: '#fde2a0' }
                ])
                }
              }
      }),
      emphasis: {
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#fde2a0' },
            { offset: 1, color: '#fac858' }
          ])
        }
      }
    }
    ]
    }
    this.currentChartInstance.setOption(option)
  },
    initMapChart() {
      const chartDom = document.getElementById('mapChart')
      if (!chartDom) return
      if (!this.mapData || this.mapData.length === 0) {
        return
      }

      if (!this.charts.map) {
        this.charts.map = echarts.init(chartDom)
      }

      const mapData = this.mapData.map(item => {
        return {
          name: item.country || '未知',
          value: item.orderNetProfit,
          revenue: item.revenue,
          roi: item.roi,
          daysToPositiveCashFlow: item.daysToPositiveCashFlow
        }
      })

      const minValue = Math.min(...mapData.map(item => item.value))
      const maxValue = Math.max(...mapData.map(item => item.value))

      const option = {
        tooltip: {
          trigger: 'item',
          formatter: (params) => {
            // 安全访问数据
            const data = params.data || {}
            return `国家: ${params.name}<br/>销售额: ¥${data.revenue ?? 'N/A'}<br/>利润: ¥${data.value ?? 'N/A'}<br/>ROI: ${data.roi ?? 'N/A'}<br/>回正天数: ${data.daysToPositiveCashFlow ?? 'N/A'}天`
          },
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff',
            fontSize: 14,
            fontWeight: 'bold'
          }
        },
        visualMap: {
          min: minValue,
          max: maxValue,
          text: ['高', '低'],
          textStyle: {
            color: '#fff',
            fontSize: 12,
            fontWeight: 'bold'
          },
          calculable: true,
          inRange: {
            color: ['#ee6666', '#fac858', '#91cc75']
          }
        },
        series: [{
          name: '销售利润',
          type: 'map',
          map: 'world',
          data: mapData,
          // 启用缩放和漫游
          roam: true,
          // 缩放控制按钮
          scaleLimit: {
            min: 1,
            max: 10
          },
          // 标签样式优化
          label: {
            show: false,
            position: 'right',
            distance: 5,
            fontSize: 10,
            fontWeight: 'bold',
            color: '#fff',
            backgroundColor: 'rgba(0, 0, 0, 0.5)',
            padding: [2, 4],
            borderRadius: 3
          },
          // 高亮状态样式
          emphasis: {
            label: {
              show: true,
              fontSize: 14,
              fontWeight: 'bold',
              color: '#fff',
              backgroundColor: 'rgba(0, 0, 0, 0.7)',
              padding: [4, 6],
              borderRadius: 4
            },
            itemStyle: {
              areaColor: '#5470c6',
              borderColor: '#fff',
              borderWidth: 2
            }
          },
          // 选中状态样式
          select: {
            label: {
              show: true,
              fontSize: 14,
              fontWeight: 'bold',
              color: '#fff',
              backgroundColor: 'rgba(84, 112, 198, 0.8)',
              padding: [4, 6],
              borderRadius: 4
            },
            itemStyle: {
              areaColor: '#5470c6'
            }
          },
          // 允许选择区域
          selectedMode: 'single',
          itemStyle: {
            areaColor: '#eee',
            borderColor: '#ccc',
            borderWidth: 0.5
          }
        }],
      }

      // 设置选项并确保渲染
      this.charts.map.setOption(option)

      // 添加缩放控制事件监听
      this.charts.map.on('georoam', (params) => {
        if (params.zoom != null) {
          // 根据缩放级别调整标签显示
          const zoom = params.zoom
          let labelShow = zoom > 2
          let fontSize = Math.max(8, Math.min(14, zoom * 2))

          this.charts.map.setOption({
            series: [{
              label: {
                show: labelShow,
                fontSize: fontSize
              }
            }]
          })
        }
      })
      setTimeout(() => {
        this.charts.map.resize()
      }, 100)
    },
    initChannelDistributionChart() {
      const chartDom = document.getElementById('channelDistributionChart')
      if (!chartDom) return
      if (!this.channelDistributionData || this.channelDistributionData.length === 0) {
        return
      }
      this.charts.channelDistribution = echarts.init(chartDom)
      const option = {
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'item',
          formatter: '{b}: {c}%',
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff',
            fontSize: 14,
            fontWeight: 'bold'
          }
        },
        legend: {
          orient: 'vertical',
          right: 10,
          top: 'center',
          textStyle: {
            color: '#fff'
          }
        },
        series: [{
          type: 'pie',
          radius: ['50%', '70%'],
          center: ['25%', '50%'],
          data: this.channelDistributionData.map(item => {
            return {
              value: item.percentage,
              name: item.channel || '未知'
            }
          }),
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          },
          itemStyle: {
            borderRadius: 10,
            borderColor: '#0a1d39',
            borderWidth: 2
          },
          label: {
            formatter: '{b}: {d}%',
            color: '#fff',
            fontWeight: 'bold'
          }
        }]
      }
      this.charts.channelDistribution.setOption(option)
    },
    initTrendChart() {
      const chartDom = document.getElementById('trendChart')
      if (!chartDom) return
      if (!this.trendData || this.trendData.length === 0) {
        return
      }
      this.charts.trend = echarts.init(chartDom)
      const option = {
        backgroundColor: 'transparent',
        tooltip: {
          trigger: 'axis',
          backgroundColor: 'rgba(0,0,0,0.8)',
          textStyle: {
            color: '#fff',
            fontSize: 12
          }
        },
        legend: {
          data: ['销售额', '利润'],
          textStyle: {
            color: '#fff',
            fontSize: 12
          },
          right: 10,
          top: 0
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          top: '15%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: this.trendData.map(item => item.date || '未知'),
          axisLabel: {
            textStyle: {
              color: '#fff',
              fontSize: 12
            }
          },
          axisLine: {
            lineStyle: {
              color: '#fff'
            }
          }
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: '¥{value}',
            textStyle: {
              color: '#fff',
              fontSize: 12
            }
          },
          axisLine: {
            lineStyle: {
              color: '#fff'
            }
          },
          splitLine: {
            lineStyle: {
              color: 'rgba(255,255,255,0.2)'
            }
          }
        },
        series: [
          {
            name: '销售额',
            type: 'line',
            smooth: true,
            symbol: 'circle',
            symbolSize: 8,
            itemStyle: {
              color: '#5470c6'
            },
            lineStyle: {
              width: 3,
              color: '#5470c6'
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: 'rgba(84, 112, 198, 0.6)' },
                { offset: 1, color: 'rgba(84, 112, 198, 0.1)' }
              ])
            },
            emphasis: {
              focus: 'series'
            },
            data: this.trendData.map(item => item.revenue)
          },
          {
            name: '利润',
            type: 'line',
            smooth: true,
            symbol: 'circle',
            symbolSize: 8,
            itemStyle: {
              color: '#91cc75'
            },
            lineStyle: {
              width: 3,
              color: '#91cc75'
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: 'rgba(145, 204, 117, 0.6)' },
                { offset: 1, color: 'rgba(145, 204, 117, 0.1)' }
              ])
            },
            emphasis: {
              focus: 'series'
            },
            data: this.trendData.map(item => item.profit)
          }
        ]
      };
      this.charts.trend.setOption(option)
    },
    changeDimension() {
      this.zoomLevel = 1;
      this.isZoomed = false;
      if (this.currentChartInstance) {
        this.currentChartInstance.dispose()
        this.currentChartInstance = null
      }
      this.$nextTick(() => {
        this.initCharts()
      })
    },
    async handleFilterChange() {
      await this.loadAllData()
      this.updateCharts()
    },
    updateCharts() {
      if (this.currentChartInstance) {
        this.currentChartInstance.dispose()
        this.currentChartInstance = null
      }
      this.initCharts()
      this.initMapChart()
      this.initChannelDistributionChart()
      this.initTrendChart()
    },
    resetFilters() {
      this.filterParams = {
        paymentSettlementDate: '',
        firstLevelCategory: '',
        category: '',
        channel: '',
        country: ''
      }
      this.handleFilterChange()
    },
    handleResize() {
      if (this.resizeTimer) {
        clearTimeout(this.resizeTimer)
      }
      this.resizeTimer = setTimeout(() => {
        Object.values(this.charts).forEach(chart => {
          if (chart) {
            chart.resize()
          }
        })
        if (this.currentChartInstance) {
          this.currentChartInstance.resize()
        }
      }, 300)
    },
    formatNumber(num) {
      return num.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    },
    formatPercentage(num) {
      return (num * 100).toFixed(2)
    },
    formatAlertValue(value, type) {
      if (type === '低ROI') {
        return value.toFixed(2)
      } else if (type === '高退款率') {
        return (value * 100).toFixed(2) + '%'
      }
      return value
    },
  }
}
</script>

<style scoped lang="scss">
@import "@/assets/scss/index.scss";
.sales-dashboard {
  width: 100%;
  height: 100%;
  background-color: #0a1d39;
  background-image: url("../../../assets/images/pageBg.png");
  color: #fff;
  padding: 20px;
  box-sizing: border-box;
}
.dashboard-container {
  width: 100%;
  height: 100%;
}
.filter-area {
  margin-bottom: 20px;
}
.kpi-area {
  margin-bottom: 20px;
}
.kpi-card {
  padding: 15px;
  text-align: center;
}
.kpi-title {
  font-size: 14px;
  margin-bottom: 10px;
  color: #aaa;
}
.kpi-value {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 5px;
}
.kpi-rate {
  font-size: 12px;
  color: #91cc75;
}
.main-chart-area {
  margin-bottom: 20px;
}
.chart-container {
  padding: 15px;
  height: 100%;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
}
.chart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  font-size: 16px;
  font-weight: bold;
}
.analysis-table {
  flex: 1;
  overflow: hidden;
  min-height: 0;
}
.alert-content {
  height: 280px;
  overflow: hidden;
  position: relative;
}
.alert-scroll-container {
  position: relative;
  height: 100%;
  overflow: hidden;
}
.alert-item {
  padding: 10px 0;
  border-bottom: 1px solid #2a3f6c;
  display: flex;
  justify-content: space-between;
  align-items: center;
  animation: scrollItem 15s linear infinite;
  position: relative;
}
@keyframes scrollItem {
  0% {
    transform: translateY(100%);
    opacity: 0;
  }
  5% {
    transform: translateY(0);
    opacity: 1;
  }
  95% {
    transform: translateY(0);
    opacity: 1;
  }
  100% {
    transform: translateY(-100%);
    opacity: 0;
  }
}
.alert-type {
  font-weight: bold;
  color: #ee6666;
  min-width: 80px;
  white-space: nowrap;
}
.alert-item-name {
  flex: 1;
  margin: 0 10px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.alert-value.negative {
  color: #ee6666;
}
.no-alerts {
  text-align: center;
  padding: 20px;
  color: #aaa;
}
.trend-area, .channel-distribution-area {
  margin-bottom: 20px;
}
.channel-distribution-area {
  margin-top: 20px;
}
::v-deep .el-table .warning-row {
  background-color: #fef0f0;
}
::v-deep .el-table {
  background-color: transparent;
  color: #fff;
}
::v-deep .el-table th {
  background-color: #1e3a68;
  color: #fff;
}
::v-deep .el-table tr {
  background-color: transparent;
}
::v-deep .el-table--enable-row-hover .el-table__body tr:hover>td {
  background-color: #2a3f6c;
}
::v-deep .el-select .el-input__inner {
  background-color: #1e3a68 !important;
  border-color: #2a3f6c;
  color: #fff;
}
::v-deep .el-select .el-input__suffix {
  color: #fff;
}
::v-deep .el-button {
  background-color: #1e3a68;
  border-color: #2a3f6c;
  color: #fff;
}
::v-deep .el-button:hover {
  background-color: #2a3f6c;
  border-color: #3a4f7c;
}
::v-deep .el-select-dropdown {
  background-color: #000;
  border: 1px solid #2a3f6c;
}
::v-deep .el-select-dropdown__item {
  color: #fff;
}
::v-deep .el-select-dropdown__item.hover,
::v-deep .el-select-dropdown__item:hover {
  background-color: #2a3f6c;
}

/* 基础样式 */
body {
  margin: 0;
  padding: 0;
  background-color: #0f1325;
  color: white;
}

.d-flex {
  display: flex;
}

.jc-center {
  justify-content: center;
}

.jc-between {
  justify-content: space-between;
}

.ml-4, .ml-3, .mr-3, .mr-4 {
  margin-left: 1rem;
}

.mr-3, .mr-4 {
  margin-right: 1rem;
}

.px-2 {
  padding: 0 1rem;
}

.text {
  font-size: 18px;
}

.fw-b {
  font-weight: bold;
}

.bg-color-blue {
  background-color: #0e72df;
}

/* 装饰样式 */
.dv-dec-10,
.dv-dec-10-s {
  width: 33.3%;
  height: 5px;
  background-color: #67a1e5;
  border-top: 5px solid #67a1e5;
  border-bottom: 5px solid #67a1e5;
}

.dv-dec-10-s {
  transform: rotateY(180deg);
}

.dv-dec-8 {
  width: 200px;
  height: 50px;
  background-color: transparent;
  border-top: 5px solid #67a1e5;
  border-bottom: 5px solid #67a1e5;
}

.title {
  position: relative;
  width: 500px;
  text-align: center;
  background-size: cover;
  background-repeat: no-repeat;
}

.title-text {
  font-size: 24px;
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translate(-50%);
}

.dv-dec-6 {
  position: absolute;
  bottom: -30px;
  left: 50%;
  width: 250px;
  height: 8px;
  transform: translate(-50%);
  background-image: linear-gradient(to right, #50e3c2, #3987e3);
}

/* 第二行样式 */
.aside-width {
  width: 40%;
}

.react-r-s,
.react-l-s {
  background-color: #0f1325;
}

/* 平行四边形 */
.react-right {
  &.react-l-s {
    text-align: right;
    width: 500px;
  }
  font-size: 18px;
  width: 300px;
  line-height: 50px;
  text-align: center;
  transform: skewX(-45deg);
}

.react-after {
  position: absolute;
  right: -25px;
  top: 0;
  height: 50px;
  width: 50px;
  background-color: #0f1325;
  transform: skewX(45deg);
}

.react-right .text {
  display: inline-block;
  transform: skewX(45deg);
}

.react-left {
  &.react-l-s {
    width: 500px;
    text-align: left;
  }
  font-size: 18px;
  width: 300px;
  height: 50px;
  line-height: 50px;
  text-align: center;
  transform: skewX(45deg);
  background-color: #0f1325;
}

.react-left .react-left {
  position: absolute;
  left: -25px;
  top: 0;
  height: 50px;
  width: 50px;
  background-color: #0f1325;
  transform: skewX(-45deg);
}

.react-left .text {
  display: inline-block;
  transform: skewX(-45deg);
}

.zoom-btn {
  margin-right: 10px;
  background-color: #1e3a68;
  border-color: #2a3f6c;
  color: #fff;
}
.zoom-btn:hover {
  background-color: #2a3f6c;
  border-color: #3a4f7c;
}
.table-pagination {
  display: flex;
  justify-content: center;
  margin-top: 10px;
}
.page-indicator {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: #2a3f6c;
  margin: 0 4px;
  transition: all 0.3s ease;
}
.page-indicator.active {
  background-color: #67a1e5;
  transform: scale(1.2);
}

.combined-chart-area {
  margin-bottom: 20px;
}
.scrolling-table .el-table__body-wrapper {
  animation: scrollTable 30s linear infinite;
}
.scrolling-table:hover .el-table__body-wrapper {
  animation-play-state: paused;
}

// 添加滚动动画
@keyframes scrollTable {
  0% {
    transform: translateY(0);
  }
  100% {
    transform: translateY(-100%);
  }
}
</style>
