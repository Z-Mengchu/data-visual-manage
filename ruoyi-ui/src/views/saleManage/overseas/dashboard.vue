<template>
  <div id="index" ref="appRef">
    <div class="bg">
      <dv-loading v-show="loading">Loading...</dv-loading>
      <div class="host-body">
        <div>
          <!-- 顶部title部分 -->
          <el-row>
            <el-col :span="6">
              <div class="country-filter">
                <span class="filter-label">选择国家:</span>
                <el-select
                  v-model="selectedCountry"
                  placeholder="全部国家"
                  size="mini"
                  @change="handleCountryChange"
                >
                  <el-option label="全部国家" value="" />
                  <el-option
                    v-for="country in countryList"
                    :key="country"
                    :label="country"
                    :value="country"
                  />
                </el-select>
              </div>
              <dv-decoration-8
              class="title_right"
              :color="['#008CFF', '#00ADDD']"
              />
            </el-col>
            <el-col :span="12">
              <div class="title_text">海 外 托 管 数 据 大 屏</div>
              <dv-decoration-5
                class="title_center"
                :color="['#008CFF', '#00ADDD']"
              />
            </el-col>
            <el-col :span="6">
              <div class="title_time">{{ dateYear + dateWeek + dateDay }}</div>
              <dv-decoration-8
                :reverse="true"
                class="title_left"
                :color="['#008CFF', '#00ADDD']"
              />
            </el-col>
          </el-row>
          <!-- 主体部分 -->
          <el-row>
            <!-- 第一列 -->
            <el-col :span="6">
              <!-- 饼图部分 -->
              <div class="left_box1">
                <dv-border-box-12 style="padding-top: 10px">
                  <!-- 标题和选择器行 -->
                  <div class="chart-header">
                    <p class="chart-title">运营员数据汇总图</p>
                    <el-select
                      v-model="operatorDimension"
                      placeholder="选择数据维度"
                      size="mini"
                      @change="handleOperatorDimensionChange"
                      class="chart-dimension-selector"
                    >
                      <el-option
                        v-for="item in dimensionOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      />
                    </el-select>
                  </div>
                  <div id="Rose_diagram"></div>
                  <dv-active-ring-chart
                    :config="ringChartConfig"
                    class="left_box1_rose_right"
                  />
                </dv-border-box-12>
              </div>
              <!-- 柱状图部分 -->
              <div class="left_box2">
                <dv-border-box-12 style="padding-top: 10px">
                  <!-- 标题和选择器行 -->
                  <div class="chart-header">
                    <p class="chart-title">开发员数据汇总图</p>
                    <el-select
                      v-model="developerDimension"
                      placeholder="选择数据维度"
                      size="mini"
                      @change="handleDeveloperDimensionChange"
                      class="chart-dimension-selector"
                    >
                      <el-option
                        v-for="item in dimensionOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      />
                    </el-select>
                  </div>
                  <div id="columnar"></div>
                </dv-border-box-12>
              </div>
              <!-- 轮播表格部分 -->
              <div class="left_box3">
                <dv-border-box-12 style="padding-top: 10px">
                  <dv-scroll-board
                    :config="board_info"
                    class="carousel_list"
                    oddRowBGC="#fff"
                  />
                </dv-border-box-12>
              </div>
            </el-col>
            <!-- 第二列 -->
            <el-col :span="12">
              <!-- 指标模块 -->
              <div class="kpi_box">
                <div class="kpi_content">
                  <el-row :gutter="20" style="height: 100%;">
                    <el-col :span="6" v-for="(item, index) in kpiData" :key="index">
                      <dv-border-box-12 style="height: 150px;">
                        <div class="kpi_item">
                          <dv-digital-flop
                            :config="item.config"
                            style="width: 100%; height: 60px;"
                          />
                          <div class="kpi_label">{{ item.label }}</div>
                        </div>
                      </dv-border-box-12>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <!-- 中间模块 -->
              <div class="center_box">
                <el-row :gutter="10">
                  <!-- 左边模块 - 40%宽度 -->
                  <el-col :span="10">
                    <div class="center_box_left">
                      <dv-border-box-12>
                        <!-- 左侧内容 -->
                        <div class="center_left_content">
                          <dv-decoration-3 style="width:200px;height:15px;"/>
                          <div id="center_left_chart"></div>
                        </div>
                      </dv-border-box-12>
                    </div>
                  </el-col>
                  <!-- 右边模块 - 60%宽度 -->
                  <el-col :span="14">
                    <div class="center_box_right">
                      <dv-border-box-12>
                        <!-- 右侧内容 -->
                        <div class="center_right_content">
                          <dv-decoration-1 style="width:200px;height:15px;"/>
                          <div id="center_right_chart"></div>
                        </div>
                      </dv-border-box-12>
                    </div>
                  </el-col>
                </el-row>
              </div>
              <!-- 折线图 -->
              <div class="line_center">
                <dv-border-box-8>
                  <div id="line_center_diagram"></div>
                </dv-border-box-8>
              </div>
            </el-col>
            <!-- 第三列 -->
            <el-col :span="6">
              <!-- 轮播排行榜部分 -->
              <div class="right_box1">
                <dv-border-box-12 style="padding-top: 10px">
                  <!-- 标题和选择器行 -->
                  <div class="chart-header">
                    <dv-decoration-7 style="width: 100%; height: 20px"
                    >SKU 排 行 榜</dv-decoration-7
                    >
                    <el-select
                      v-model="skuDimension"
                      placeholder="选择数据维度"
                      size="mini"
                      @change="handleSkuDimensionChange"
                      class="chart-dimension-selector"
                    >
                      <el-option
                        v-for="item in dimensionOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      />
                    </el-select>
                  </div>
                  <dv-scroll-ranking-board
                    :config="config"
                    style="width: 95%; height: 87%; margin-left: 2%"
                  />
                </dv-border-box-12>
              </div>
              <!-- 虚线柱状图部分 -->
              <div class="right_box2">
                <dv-border-box-12 :reverse="true" style="padding-top: 10px">
                  <!-- 标题和选择器行 -->
                  <div class="chart-header">
                    <p class="chart-title">品牌类目数据汇总图</p>
                    <el-select
                      v-model="brandCategoryDimension"
                      placeholder="选择数据维度"
                      size="mini"
                      @change="handleBrandCategoryDimensionChange"
                      class="chart-dimension-selector"
                    >
                      <el-option
                        v-for="item in dimensionOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      />
                    </el-select>
                  </div>
                  <div id="dotter_bar"></div>
                </dv-border-box-12>
              </div>
              <!-- 饼状图部分 -->
              <div class="right_box3">
                <dv-border-box-12 :reverse="true"  style="padding-top: 10px">
                  <!-- 标题和选择器行 -->
                  <div class="chart-header">
                    <dv-decoration-7 style="width: 100%; height: 20px"
                    >仓 库 数 据 汇 总 图</dv-decoration-7
                    >
                    <el-select
                      v-model="warehouseDimension"
                      placeholder="选择数据维度"
                      size="mini"
                      @change="handleWarehouseDimensionChange"
                      class="chart-dimension-selector"
                    >
                      <el-option
                        v-for="item in dimensionOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      />
                    </el-select>
                  </div>
                  <div id="pie_chart" class="pie_chart"/>
                </dv-border-box-12>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import drawMixin from "@/utils/drawMixin"; //自适应缩放
import { formatTime2 } from "@/utils/index.js"; //日期格式转换
import * as echarts from "echarts";
import { getSummaryByOperator, getSummaryByDeveloper, getSummaryByFeeItem,
  getSummaryBySku, getSummaryByWarehouse, getSummaryByBrand,
  getSummaryByBrandAndCategory, getSummaryByMonthly,
  getSummaryByTotal, getSummaryByCoreExpenses, getAllCountry } from "@/api/saleManage/overseas/dashboard"

export default {
  mixins: [drawMixin],
  data() {
    return {
      //定时器
      timing: null,
      //loading图
      loading: true,
      //时分秒
      dateDay: null,
      //年月日
      dateYear: null,
      //周几
      dateWeek: null,
      //周几
      weekday: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
      // 选中国家
      selectedCountry: '',
      // 国家列表
      countryList: [],
      // 运营员当前数据维度
      operatorDimension: 'revenueSum',
      // 开发员当前数据维度
      developerDimension: 'revenueSum',
      // SKU当前数据维度
      skuDimension: 'revenueSum',
      // 仓库当前数据维度
      warehouseDimension: 'revenueSum',
      // 品牌类目当前数据维度
      brandCategoryDimension: 'revenueSum',
      // 数据维度选项
      dimensionOptions: [
        { label: '收入', value: 'revenueSum' },
        { label: '退款', value: 'refundSum' },
        { label: '采购成本', value: 'purchaseCostSum' },
        { label: '头程成本', value: 'firstMileCostSum' },
        { label: '物流费', value: 'logisticsFeeSum' },
        { label: '包装费用', value: 'packagingCostSum' },
        { label: '其他费用', value: 'otherCostsSum' },
        { label: '补发费用', value: 'reshipmentCostSum' },
        { label: '毛利', value: 'grossProfitSum' }
      ],
      // 运营员汇总数据
      operatorSummaryData: [],
      // 开发员汇总数据
      developerSummaryData: [],
      // 费用项汇总数据
      feeItemSummaryData: [],
      // SKU汇总数据
      skuSummaryData: [],
      // 仓库汇总数据
      warehouseSummaryData: [],
      // 品牌汇总数据
      brandSummaryData: [],
      // 品牌类目汇总数据
      brandCategorySummaryData: [],
      // 月度汇总数据
      monthlySummaryData: [],
      // 总体统计数据
      totalSummaryData: {},
      // 核心费用项数据
      coreExpensesData: {},
      // 圆环图配置
      ringChartConfig: {
        data: [],
        digitalFlopStyle: {
          fontSize: 20
        },
        color: ['#37a2da', '#32c5e9', '#9fe6b8', '#ffdb5c', '#ff9f7f', '#fb7293', '#e7bcf3', '#8378ea'],
        animationCurve: 'easeOutBack'
      },
      //轮播排行榜
      config: {
        data: [],
        unit: '元'
      },
      //左侧轮播表格配置项
      board_info: {
        header: ["费用项", "金额（CNY）"],
        data: [],
        evenRowBGC: "#020308",
        oddRowBGC: "#382B47",
        headerBGC: "#020308",
      },
      // KPI指标数据
      kpiData: [
        {
          label: '总收入',
          config: {
            number: [0],
            content: '{nt}',
            style: {
              fontSize: 24,
              fill: '#3de7c9'
            }
          }
        },
        {
          label: '总订单',
          config: {
            number: [0],
            content: '{nt}',
            style: {
              fontSize: 24,
              fill: '#3de7c9'
            }
          }
        },
        {
          label: '总毛利',
          config: {
            number: [0],
            content: '{nt}',
            style: {
              fontSize: 24,
              fill: '#3de7c9'
            }
          }
        },
        {
          label: '毛利率',
          config: {
            number: [0],
            toFixed: 2,
            content: '{nt}%',
            style: {
              fontSize: 24,
              fill: '#3de7c9'
            }
          }
        }
      ],
      // 定义颜色
      colorList: {
        linearYtoG: {
          type: "linear",
          x: 0,
          y: 0,
          x2: 1,
          y2: 1,
          colorStops: [
            {
              offset: 0,
              color: "#f5b44d",
            },
            {
              offset: 1,
              color: "#28f8de",
            },
          ],
        },
        linearGtoB: {
          type: "linear",
          x: 0,
          y: 0,
          x2: 1,
          y2: 0,
          colorStops: [
            {
              offset: 0,
              color: "#43dfa2",
            },
            {
              offset: 1,
              color: "#28f8de",
            },
          ],
        },
        linearBtoG: {
          type: "linear",
          x: 0,
          y: 0,
          x2: 1,
          y2: 0,
          colorStops: [
            {
              offset: 0,
              color: "#1c98e8",
            },
            {
              offset: 1,
              color: "#28f8de",
            },
          ],
        },
        areaBtoG: {
          type: "linear",
          x: 0,
          y: 0,
          x2: 0,
          y2: 1,
          colorStops: [
            {
              offset: 0,
              color: "rgba(35,184,210,.2)",
            },
            {
              offset: 1,
              color: "rgba(35,184,210,0)",
            },
          ],
        },
      },
      // 饼状图数据
      pieChartData: {
        data: [
          { name: "周口", value: 55 },
          { name: "南阳", value: 120 },
          { name: "西峡", value: 71 },
          { name: "驻马店", value: 66 },
          { name: "新乡", value: 80 },
          { name: "信阳", value: 35 },
          { name: "漯河", value: 15 }
        ]
      },
    };
  },

  mounted() {
    //获取实时时间
    this.timeFn();
    //获取国家列表
    this.getCountryList();
    //获取运营员汇总数据
    this.getOperatorSummaryData();
    //获取开发员汇总数据
    this.getDeveloperSummaryData();
    //获取费用项汇总数据
    this.getFeeItemSummaryData();
    //获取SKU汇总数据
    this.getSkuSummaryData();
    //获取仓库汇总数据
    this.getWarehouseSummaryData();
    //获取品牌汇总数据
    this.getBrandSummaryData();
    //获取品牌类目汇总数据
    this.getBrandCategorySummaryData();
    //获取月度汇总数据
    this.getMonthlySummaryData();
    //获取总体统计数据
    this.getTotalSummaryData();
    //获取核心费用项数据
    this.getCoreExpensesData();
    //左侧柱状图
    this.columnar();
    //中间折线图
    this.line_center_diagram();
    //虚线柱状图
    this.dotter_bar();
    //饼状图
    this.pie_chart();
    //中间左侧图表
    this.center_left_chart();
    //中间右侧图表
    this.center_right_chart();
  },
  beforeDestroy() {
    //离开时删除计时器
    clearInterval(this.timing);
  },
  methods: {
    formatNumber(num) {
      const number = Number(num) || 0;
      if (number >= 10000) {
        return (number / 10000).toFixed(1) + '万';
      }
      return number.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    },
    //右上角当前日期时间显示：每一秒更新一次最新时间
    timeFn() {
      this.timing = setInterval(() => {
        //获取当前时分秒
        this.dateDay = formatTime2(new Date(), "HH: mm: ss");
        //获取当前年月日
        this.dateYear = formatTime2(new Date(), "yyyy-MM-dd");
        //获取当前周几
        this.dateWeek = this.weekday[new Date().getDay()];
      }, 1000);
    },
    // 获取国家列表
    async getCountryList() {
      try {
        const response = await getAllCountry();
        this.countryList = response.data || [];
      } catch (error) {
        console.error('获取国家列表失败:', error);
        this.countryList = [];
      }
    },
    // 处理国家变化
    handleCountryChange() {
      this.loading = true;
      // 重新加载所有数据
      this.reloadAllData();
    },
    // 重新加载所有数据
    async reloadAllData() {
      try {
        await Promise.all([
          this.getOperatorSummaryData(),
          this.getDeveloperSummaryData(),
          this.getFeeItemSummaryData(),
          this.getSkuSummaryData(),
          this.getWarehouseSummaryData(),
          this.getBrandSummaryData(),
          this.getBrandCategorySummaryData(),
          this.getMonthlySummaryData(),
          this.getTotalSummaryData(),
          this.getCoreExpensesData()
        ]);

        // 重新渲染所有图表
        this.Rose_diagram();
        this.updateRingChartData();
        this.columnar();
        this.line_center_diagram();
        this.dotter_bar();
        this.pie_chart();
        this.center_left_chart();
        this.center_right_chart();
        this.updateRankingData();
        this.updateBoardData();

      } catch (error) {
        console.error('重新加载数据失败:', error);
      } finally {
        this.loading = false;
      }
    },
    // 获取运营员汇总数据
    async getOperatorSummaryData() {
      try {
        const response = await getSummaryByOperator(this.selectedCountry);
        this.operatorSummaryData = response.data;
        // 初始化饼图和圆环图
        this.Rose_diagram();
        this.updateRingChartData();
      } catch (error) {
        console.error('获取运营员汇总数据失败:', error);
      }
    },
    // 获取开发员汇总数据
    async getDeveloperSummaryData() {
      try {
        const response = await getSummaryByDeveloper(this.selectedCountry);
        this.developerSummaryData = response.data;
        // 初始化柱状图
        this.columnar();
      } catch (error) {
        console.error('获取开发员汇总数据失败:', error);
      }
    },
    // 获取仓库汇总数据
    async getWarehouseSummaryData() {
      try {
        const response = await getSummaryByWarehouse(this.selectedCountry);
        this.warehouseSummaryData = response.data;
        // 更新饼状图数据
        this.updatePieChartData();
      } catch (error) {
        console.error('获取仓库汇总数据失败:', error);
      }
    },
    // 获取品牌汇总数据
    async getBrandSummaryData() {
      try {
        const response = await getSummaryByBrand(this.selectedCountry);
        this.brandSummaryData = response.data;
      } catch (error) {
        console.error('获取品牌汇总数据失败:', error);
      }
    },
    // 获取品牌类目汇总数据
    async getBrandCategorySummaryData() {
      try {
        const response = await getSummaryByBrandAndCategory(this.selectedCountry);
        this.brandCategorySummaryData = response.data;
        // 更新虚线柱状图数据
        this.updateDotterBarData();
      } catch (error) {
        console.error('获取品牌类目汇总数据失败:', error);
      }
    },
    // 获取月度汇总数据
    async getMonthlySummaryData() {
      try {
        const response = await getSummaryByMonthly(this.selectedCountry);
        this.monthlySummaryData = response.data;
        // 更新折线图数据
        this.updateLineChartData();
      } catch (error) {
        console.error('获取月度汇总数据失败:', error);
      }
    },
    // 获取总体统计数据
    async getTotalSummaryData() {
      try {
        const response = await getSummaryByTotal(this.selectedCountry);
        this.totalSummaryData = response.data;
        // 更新KPI指标数据
        this.updateKpiData();
      } catch (error) {
        console.error('获取总体统计数据失败:', error);
      }
    },
    // 获取核心费用项数据
    async getCoreExpensesData() {
      try {
        const response = await getSummaryByCoreExpenses(this.selectedCountry);
        this.coreExpensesData = response.data;
        // 更新中间模块图表数据
        this.updateCenterCharts();
      } catch (error) {
        console.error('获取核心费用项数据失败:', error);
      }
    },
    // 处理运营员数据维度变化
    handleOperatorDimensionChange() {
      this.Rose_diagram();
      this.updateRingChartData();
    },
    // 处理开发人员数据维度变化
    handleDeveloperDimensionChange() {
      this.columnar();
    },
    // 处理SKU数据维度变化
    handleSkuDimensionChange() {
      this.updateRankingData();
    },
    // 处理仓库数据维度变化
    handleWarehouseDimensionChange() {
      this.updatePieChartData();
    },
    // 处理品牌类目数据维度变化
    handleBrandCategoryDimensionChange() {
      this.updateDotterBarData();
    },
    // 获取SKU汇总数据
    async getSkuSummaryData() {
      try {
        const response = await getSummaryBySku(this.selectedCountry);
        this.skuSummaryData = response.data;
        // 更新排行榜数据
        this.updateRankingData();
      } catch (error) {
        console.error('获取SKU汇总数据失败:', error);
      }
    },
    // 更新排行榜数据
    updateRankingData() {
      if (!this.skuSummaryData || this.skuSummaryData.length === 0) return;
      // 处理SKU数据
      const rankingData = this.skuSummaryData.map(item => {
        let value = 0;
        // 根据当前选择的维度获取对应的数值
        switch (this.skuDimension) {
          case 'revenueSum':
            value = item.revenueSum || 0;
            break;
          case 'refundSum':
            value = item.refundSum || 0;
            break;
          case 'purchaseCostSum':
            value = item.purchaseCostSum || 0;
            break;
          case 'firstMileCostSum':
            value = item.firstMileCostSum || 0;
            break;
          case 'logisticsFeeSum':
            value = item.logisticsFeeSum || 0;
            break;
          case 'packagingCostSum':
            value = item.packagingCostSum || 0;
            break;
          case 'otherCostsSum':
            value = item.otherCostsSum || 0;
            break;
          case 'reshipmentCostSum':
            value = item.reshipmentCostSum || 0;
            break;
          case 'grossProfitSum':
            value = item.grossProfitSum || 0;
            break;
          default:
            value = item.revenueSum || 0;
        }
        return {
          name: item.groupValue || '未知SKU',
          value: Number(value)
        };
      }).filter(item => item.value > 0)
        .sort((a, b) => b.value - a.value)
        .slice(0, 100); // 取前100名
      // 更新排行榜配置
      this.config = {
        ...this.config,
        data: rankingData,
        unit: '元'
      };
    },
    // 更新饼状图数据
    updatePieChartData() {
      if (!this.warehouseSummaryData || this.warehouseSummaryData.length === 0) return;

      // 处理仓库数据
      const pieData = this.warehouseSummaryData.map(item => {
        let value = 0;
        // 根据当前选择的维度获取对应的数值
        switch (this.warehouseDimension) {
          case 'revenueSum':
            value = item.revenueSum || 0;
            break;
          case 'refundSum':
            value = item.refundSum || 0;
            break;
          case 'purchaseCostSum':
            value = item.purchaseCostSum || 0;
            break;
          case 'firstMileCostSum':
            value = item.firstMileCostSum || 0;
            break;
          case 'logisticsFeeSum':
            value = item.logisticsFeeSum || 0;
            break;
          case 'packagingCostSum':
            value = item.packagingCostSum || 0;
            break;
          case 'otherCostsSum':
            value = item.otherCostsSum || 0;
            break;
          case 'reshipmentCostSum':
            value = item.reshipmentCostSum || 0;
            break;
          case 'grossProfitSum':
            value = item.grossProfitSum || 0;
            break;
          default:
            value = item.revenueSum || 0;
        }
        return {
          name: item.groupValue || '未知仓库',
          value: Number(value)
        };
      }).filter(item => item.value > 0);
      // 重新渲染饼状图
      this.pie_chart(pieData);
    },
    // 更新虚线柱状图数据
    updateDotterBarData() {
      if (!this.brandSummaryData || this.brandSummaryData.length === 0) return;
      // 处理品牌数据
      const chartData = this.brandSummaryData.map(item => {
        let value = 0;
        // 根据当前选择的维度获取对应的数值
        switch (this.brandCategoryDimension) {
          case 'revenueSum':
            value = item.revenueSum || 0;
            break;
          case 'refundSum':
            value = item.refundSum || 0;
            break;
          case 'purchaseCostSum':
            value = item.purchaseCostSum || 0;
            break;
          case 'firstMileCostSum':
            value = item.firstMileCostSum || 0;
            break;
          case 'logisticsFeeSum':
            value = item.logisticsFeeSum || 0;
            break;
          case 'packagingCostSum':
            value = item.packagingCostSum || 0;
            break;
          case 'otherCostsSum':
            value = item.otherCostsSum || 0;
            break;
          case 'reshipmentCostSum':
            value = item.reshipmentCostSum || 0;
            break;
          case 'grossProfitSum':
            value = item.grossProfitSum || 0;
            break;
          default:
            value = item.revenueSum || 0;
        }
        return {
          name: item.groupValue || '未知品牌',
          value: Number(value)
        };
      }).filter(item => item.value > 0)
      // 重新渲染虚线柱状图
      this.dotter_bar(chartData);
    },
    // 更新折线图数据
    updateLineChartData() {
      if (!this.monthlySummaryData || this.monthlySummaryData.length === 0) return;
      // 重新渲染折线图
      this.line_center_diagram();
    },
    // 更新KPI指标数据
    updateKpiData() {
      if (!this.totalSummaryData) return;
      this.kpiData = [
        {
          label: '总收入',
          config: {
            number: [Number(this.totalSummaryData.total_revenue) || 0],
            content: '{nt}',
            style: {
              fontSize: 24,
              fill: '#3de7c9'
            }
          }
        },
        {
          label: '总订单',
          config: {
            number: [Number(this.totalSummaryData.total_orders) || 0],
            content: '{nt}',
            style: {
              fontSize: 24,
              fill: '#3de7c9'
            }
          }
        },
        {
          label: '总毛利',
          config: {
            number: [Number(this.totalSummaryData.total_gross_profit) || 0],
            content: '{nt}',
            style: {
              fontSize: 24,
              fill: '#3de7c9'
            }
          }
        },
        {
          label: '毛利率',
          config: {
            number: [Number(this.totalSummaryData.gross_profit_margin) || 0],
            toFixed: 2,
            content: '{nt}%',
            style: {
              fontSize: 24,
              fill: '#3de7c9'
            }
          }
        }
      ];
    },
    // 更新中间模块图表数据
    updateCenterCharts() {
      if (!this.coreExpensesData) return;
      // 重新渲染中间模块图表
      this.center_left_chart();
      this.center_right_chart();
    },
    // 获取运营员当前维度显示名称
    getOperatorDimensionLabel() {
      const option = this.dimensionOptions.find(item => item.value === this.operatorDimension);
      return option ? option.label : '收入';
    },
    // 获取开发员维度显示名称
    getDeveloperDimensionLabel() {
      const option = this.dimensionOptions.find(item => item.value === this.developerDimension);
      return option ? option.label : '收入';
    },
    // 获取仓库维度显示名称
    getWarehouseDimensionLabel() {
      const option = this.dimensionOptions.find(item => item.value === this.warehouseDimension);
      return option ? option.label : '收入';
    },
    // 获取品牌类目维度显示名称
    getBrandCategoryDimensionLabel() {
      const option = this.dimensionOptions.find(item => item.value === this.brandCategoryDimension);
      return option ? option.label : '收入';
    },
    // 获取费用项汇总数据
    async getFeeItemSummaryData() {
      try {
        const response = await getSummaryByFeeItem(this.selectedCountry);
        this.feeItemSummaryData = response.data;
        // 更新轮播表格数据
        this.updateBoardData();
      } catch (error) {
        console.error('获取费用项汇总数据失败:', error);
      }
    },
    // 更新轮播表格数据
    updateBoardData() {
      if (!this.feeItemSummaryData || this.feeItemSummaryData.length === 0) return;

      // 将费用项数据转换为轮播表格格式
      const tableData = this.feeItemSummaryData.map(item => {
        return [
          item.feeItem || '未知费用项',
          this.formatNumber(item.amountCnySum || 0)
        ];
      });

      // 更新轮播表格配置
      this.board_info = {
        ...this.board_info,
        data: tableData
      };
    },
    //玫瑰饼图
    Rose_diagram() {
      let mapChart = this.$echarts.init(
        document.getElementById("Rose_diagram")
      );
      //图表初始化
      window.onresize = mapChart.resize; //如果容器变大小，自适应从新构图

      // 处理运营员数据
      const chartData = this.operatorSummaryData.map(item => {
        let value = 0;
        // 根据当前选择的维度获取对应的数值
        switch (this.operatorDimension) {
          case 'revenueSum':
            value = item.revenueSum || 0;
            break;
          case 'refundSum':
            value = item.refundSum || 0;
            break;
          case 'purchaseCostSum':
            value = item.purchaseCostSum || 0;
            break;
          case 'firstMileCostSum':
            value = item.firstMileCostSum || 0;
            break;
          case 'logisticsFeeSum':
            value = item.logisticsFeeSum || 0;
            break;
          case 'packagingCostSum':
            value = item.packagingCostSum || 0;
            break;
          case 'otherCostsSum':
            value = item.otherCostsSum || 0;
            break;
          case 'reshipmentCostSum':
            value = item.reshipmentCostSum || 0;
            break;
          case 'grossProfitSum':
            value = item.grossProfitSum || 0;
            break;
          default:
            value = item.revenueSum || 0;
        }

        return {
          value: Number(value),
          name: item.groupValue || '未知运营员'
        };
      });
      let option = {
        color: [
          "#37a2da",
          "#32c5e9",
          "#9fe6b8",
          "#ffdb5c",
          "#ff9f7f",
          "#fb7293",
          "#e7bcf3",
          "#8378ea",
        ],
        tooltip: {
          trigger: "item",
          formatter:  (params) => {
            const value = this.formatNumber(params.value);
            const percentage = params.percent.toFixed(1);
            return `${params.seriesName} <br/>${params.name} : ${value} (${percentage}%)`;
          },
          axisPointer: {
            type: 'shadow'
          },
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff',
            fontSize: 12
          },
          extraCssText: 'box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);'
        },
        toolbox: {
          show: true,
        },
        calculable: true,
        legend: {
          orient: "horizontal",
          icon: "circle",
          bottom: 0,
          x: "center",
          data: chartData.map(item => item.name),
          textStyle: {
            color: "#fff",
          },
        },
        series: [
          {
            name: `运营员${this.getOperatorDimensionLabel()}统计`,
            type: "pie",
            radius: [10, 50],
            roseType: "area",
            center: ["50%", "40%"],
            data: chartData,
          },
        ],
      };
      mapChart.setOption(option); //生成图表
    },
    // 更新圆环图数据
    updateRingChartData() {
      if (!this.operatorSummaryData || this.operatorSummaryData.length === 0) return;
      // 为每个运营员创建圆环图数据项
      // 更新圆环图数据
      this.ringChartConfig.data = this.operatorSummaryData.map(item => {
        let value = 0;
        // 根据当前选择的维度获取对应的数值
        switch (this.operatorDimension) {
          case 'revenueSum':
            value = item.revenueSum || 0;
            break;
          case 'refundSum':
            value = item.refundSum || 0;
            break;
          case 'purchaseCostSum':
            value = item.purchaseCostSum || 0;
            break;
          case 'firstMileCostSum':
            value = item.firstMileCostSum || 0;
            break;
          case 'logisticsFeeSum':
            value = item.logisticsFeeSum || 0;
            break;
          case 'packagingCostSum':
            value = item.packagingCostSum || 0;
            break;
          case 'otherCostsSum':
            value = item.otherCostsSum || 0;
            break;
          case 'reshipmentCostSum':
            value = item.reshipmentCostSum || 0;
            break;
          case 'grossProfitSum':
            value = item.grossProfitSum || 0;
            break;
          default:
            value = item.revenueSum || 0;
        }

        return {
          name: item.groupValue || '未知运营员',
          value: Number(value)
        };
      }).filter(item => item.value > 0);
      this.ringChartConfig = {...this.ringChartConfig}
    },
    //柱状图
    columnar() {
      let mapChart = this.$echarts.init(document.getElementById("columnar"));
      window.onresize = mapChart.resize; //如果容器变大小，自适应从新构图
      // 处理开发员数据
      const chartData = this.developerSummaryData.map(item => {
        let value = 0;
        // 根据当前选择的维度获取对应的数值
        switch (this.developerDimension) {
          case 'revenueSum':
            value = item.revenueSum || 0;
            break;
          case 'refundSum':
            value = item.refundSum || 0;
            break;
          case 'purchaseCostSum':
            value = item.purchaseCostSum || 0;
            break;
          case 'firstMileCostSum':
            value = item.firstMileCostSum || 0;
            break;
          case 'logisticsFeeSum':
            value = item.logisticsFeeSum || 0;
            break;
          case 'packagingCostSum':
            value = item.packagingCostSum || 0;
            break;
          case 'otherCostsSum':
            value = item.otherCostsSum || 0;
            break;
          case 'reshipmentCostSum':
            value = item.reshipmentCostSum || 0;
            break;
          case 'grossProfitSum':
            value = item.grossProfitSum || 0;
            break;
          default:
            value = item.revenueSum || 0;
        }
        return {
          name: item.groupValue || '未知开发员',
          value: Number(value)
        };
      });
      // 提取名称和数值
      const names = chartData.map(item => item.name);
      const values = chartData.map(item => item.value);
      let option = {
        title: {
          text: "",
        },
        tooltip: {
          trigger: "axis",
          formatter: (params) => {
            const value = this.formatNumber(params[0].value);
            return `${this.getDeveloperDimensionLabel()}<br/>${params[0].name} : ${value}`;
          },
          backgroundColor: "rgba(255,255,255,0.1)",
          axisPointer: {
            type: "shadow",
            label: {
              show: true,
              backgroundColor: "#7B7DDC",
            },
          },
          textStyle: {
            color: '#fff',
            fontSize: 12
          },
          extraCssText: 'box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);'
        },
        legend: {
          data: [this.getDeveloperDimensionLabel()],
          textStyle: {
            color: "#B4B4B4",
          },
          top: "0%",
        },
        grid: {
          x: "11%",
          width: "87%",
          y: "4%",
        },
        xAxis: {
          data: names,
          axisLine: {
            lineStyle: {
              color: "#B4B4B4",
            },
          },
          axisTick: {
            show: false,
          }
        },
        yAxis: [
          {
            splitLine: {
              show: false,
              lineStyle: {
                color: 'rgba(180, 180, 180, 0.3)',
                type: 'dashed'
              }
            },
            axisLine: {
              lineStyle: {
                color: "#B4B4B4",
              },
            },

            axisLabel: {
              formatter: (value) => {
                return this.formatNumber(value);
              },
              textStyle: {
                color: "#B4B4B4"
              }
            },
          },
        ],
        series: [
          {
            name: this.getDeveloperDimensionLabel(),
            type: "line",
            smooth: true,
            showAllSymbol: true,
            symbol: "emptyCircle",
            symbolSize: 15,
            data: values,
          },
          {
            name: this.getDeveloperDimensionLabel(),
            type: "bar",
            barGap: "-100%",
            barWidth: "50%",
            itemStyle: {
              normal: {
                barBorderRadius: 5,
                color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: "#956FD4" },
                  { offset: 1, color: "#3EACE5" },
                ]),
              },
            },
            data: values,
          }
        ],
      };
      mapChart.setOption(option, true); //生成图表
    },
    //折线图
    line_center_diagram() {
      let mapChart = this.$echarts.init(
        document.getElementById("line_center_diagram")
      ); //图表初始化
      window.onresize = mapChart.resize; //如果容器变大小，自适应从新构图
      // 处理月度数据
      const monthlyData = this.monthlySummaryData;
      // 处理月度数据
      const months = ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"];
      const revenueData = new Array(12).fill(0);
      const refundData = new Array(12).fill(0);
      const grossProfitData = new Array(12).fill(0);
      // 填充月度数据
      monthlyData.forEach(item => {
        const monthStr = item.month;
        const monthIndex = parseInt(monthStr.split('-')[1]) - 1; // 从月份字符串中提取月份数字

        if (monthIndex >= 0 && monthIndex < 12) {
          revenueData[monthIndex] = Number(item.revenue_sum) || 0;
          refundData[monthIndex] = Number(item.refund_sum) || 0;
          grossProfitData[monthIndex] = Number(item.gross_profit_sum) || 0;
        }
      });
      let option = {
        xAxis: {
          type: "category",
          data: months,
          position: "bottom",
          axisLine: true,
          axisLabel: {
            color: "rgba(255,255,255,.8)",
            fontSize: 12,
          },
        },
        yAxis: {
          type: "value",
          name: "年度数据图",
          nameLocation: "end",
          nameGap: 24,
          nameTextStyle: {
            color: "#fff",
            fontSize: 14,
          },
          splitNumber: 4,
          axisLine: {
            lineStyle: {
              opacity: 0,
            },
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: "#fff",
              opacity: 0.1,
            },
          },
          axisLabel: {
            color: "rgba(255,255,255,.8)",
            fontSize: 12,
            formatter: (value) => {
              return this.formatNumber(value);
            }
          },
        },
        tooltip: {
          trigger: "axis",
          formatter: (params) => {
            const month = params[0].name;
            const revenue = this.formatNumber(params[0].value);
            const refund = this.formatNumber(refundData[params[0].dataIndex]);
            const grossProfit = this.formatNumber(grossProfitData[params[0].dataIndex]);

            return `
              <div style="font-weight: bold; margin-bottom: 8px;">${month}月度数据</div>
              <div style="font-size: 12px;">收入: ${revenue}</div>
              <div style="font-size: 12px;">退款: ${refund}</div>
              <div style="font-size: 12px;">毛利: ${grossProfit}</div>
            `;
          },
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff',
            fontSize: 12
          },
          extraCssText: 'box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);'
        },
        grid: {
          left: 70,
          right: 10,
          bottom: 25,
          top: "18%",
        },
        series: [
          {
            name: "月度收入",
            data: revenueData,
            type: "line",
            smooth: true,
            symbol: "emptyCircle",
            symbolSize: 8,
            itemStyle: {
              normal: {
                color: "#fff",
              },
            },
            //线的颜色样式
            lineStyle: {
              normal: {
                color: this.colorList.linearBtoG,
                width: 3,
              },
            },
            //填充颜色样式
            areaStyle: {
              normal: {
                color: this.colorList.areaBtoG,
              },
            },
          },
        ],
      };
      mapChart.setOption(option); //生成图表
    },
    //右侧虚线柱状图图
    dotter_bar(chartData) {
      let mapChart = this.$echarts.init(document.getElementById("dotter_bar")); //图表初始化
      window.onresize = mapChart.resize; //如果容器变大小，自适应从新构图
      // 如果没有传入数据，使用品牌数据
      const data = chartData || this.brandSummaryData.map(item => {
        let value = 0;
        // 根据当前选择的维度获取对应的数值
        switch (this.brandCategoryDimension) {
          case 'revenueSum':
            value = item.revenueSum || 0;
            break;
          case 'refundSum':
            value = item.refundSum || 0;
            break;
          case 'purchaseCostSum':
            value = item.purchaseCostSum || 0;
            break;
          case 'firstMileCostSum':
            value = item.firstMileCostSum || 0;
            break;
          case 'logisticsFeeSum':
            value = item.logisticsFeeSum || 0;
            break;
          case 'packagingCostSum':
            value = item.packagingCostSum || 0;
            break;
          case 'otherCostsSum':
            value = item.otherCostsSum || 0;
            break;
          case 'reshipmentCostSum':
            value = item.reshipmentCostSum || 0;
            break;
          case 'grossProfitSum':
            value = item.grossProfitSum || 0;
            break;
          default:
            value = item.revenueSum || 0;
        }
        return {
          name: item.groupValue || '未知品牌',
          value: Number(value)
        };
      }).filter(item => item.value > 0);
      // 提取名称和数值
      const names = data.map(item => item.name);
      const values = data.map(item => item.value);
      // 获取当前品牌的类目数据
      const getCategoryDataForBrand = (brandName) => {
        return this.brandCategorySummaryData
          .filter(item => item.brand === brandName)
          .map(item => {
            let value = 0;
            // 根据当前选择的维度获取对应的数值
            switch (this.brandCategoryDimension) {
              case 'revenueSum':
                value = item.revenue_sum || 0;
                break;
              case 'refundSum':
                value = item.refund_sum || 0;
                break;
              case 'purchaseCostSum':
                value = item.purchase_cost_sum || 0;
                break;
              case 'firstMileCostSum':
                value = item.first_mile_cost_sum || 0;
                break;
              case 'logisticsFeeSum':
                value = item.logistics_fee_sum || 0;
                break;
              case 'packagingCostSum':
                value = item.packaging_cost_sum || 0;
                break;
              case 'otherCostsSum':
                value = item.other_costs_sum || 0;
                break;
              case 'reshipmentCostSum':
                value = item.reshipment_cost_sum || 0;
                break;
              case 'grossProfitSum':
                value = item.gross_profit_sum || 0;
                break;
              default:
                value = item.revenue_sum || 0;
            }
            return {
              category: item.category || '未知类目',
              value: Number(value)
            };
          })
          .filter(item => item.value > 0)
          .sort((a, b) => b.value - a.value);
      };
      // option
      let option = {
        tooltip: {
          trigger: "axis",
          formatter: (params) => {
            const brandName = params[0].name;
            const brandValue = this.formatNumber(params[0].value);
            const categoryData = getCategoryDataForBrand(brandName);

            let tooltipHtml = `<div style="font-weight: bold; margin-bottom: 8px;">${brandName} - ${this.getBrandCategoryDimensionLabel()}: ${brandValue}</div>`;

            if (categoryData.length > 0) {
              tooltipHtml += `<div style="font-size: 12px; margin-bottom: 4px;">类目分布:</div>`;
              categoryData.forEach(item => {
                tooltipHtml += `<div style="font-size: 11px; margin-left: 10px;">${item.category}: ${this.formatNumber(item.value)}</div>`;
              });
            } else {
              tooltipHtml += `<div style="font-size: 12px;">暂无类目数据</div>`;
            }

            return tooltipHtml;
          },
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff',
            fontSize: 12
          },
          extraCssText: 'box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);'
        },
        grid: {
          x: "13%",
          width: "85%",
          y: "4%",
        },
        legend: {
          data: [this.getBrandCategoryDimensionLabel()],
          textStyle: {
            color: "#ccc",
          },
        },
        xAxis: {
          data: names,
          axisLine: {
            lineStyle: {
              color: "#ccc",
            },
          },
        },
        yAxis: {
          splitLine: { show: false },
          axisLine: {
            lineStyle: {
              color: "#ccc",
            },
          },
          axisLabel: {
            formatter: (value) => {
              return this.formatNumber(value);
            },
            textStyle: {
              color: "#ccc"
            }
          },
        },
        series: [
          {
            name: this.getBrandCategoryDimensionLabel(),
            type: "line",
            smooth: true,
            showAllSymbol: true,
            symbol: "emptyCircle",
            symbolSize: 15,
            data: values,
          },
          {
            name: this.getBrandCategoryDimensionLabel(),
            type: "bar",
            barGap: "-100%",
            barWidth: 10,
            itemStyle: {
              // color: "rgba(20,200,212,0.5)",
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: "#14c8d4" },
                { offset: 1, color: "#43eec6" },
              ]),
            },
            z: -12,
            data: values,
          },
        ],
      };
      mapChart.setOption(option); //生成图表
    },
    //饼状图
    pie_chart(pieData) {
      let pieChart = this.$echarts.init(document.getElementById("pie_chart")); //图表初始化
      window.onresize = pieChart.resize; //如果容器变大小，自适应从新构图

      // 如果没有传入数据，使用仓库数据
      const chartData = pieData || this.warehouseSummaryData.map(item => {
        let value = 0;
        // 根据当前选择的维度获取对应的数值
        switch (this.warehouseDimension) {
          case 'revenueSum':
            value = item.revenueSum || 0;
            break;
          case 'refundSum':
            value = item.refundSum || 0;
            break;
          case 'purchaseCostSum':
            value = item.purchaseCostSum || 0;
            break;
          case 'firstMileCostSum':
            value = item.firstMileCostSum || 0;
            break;
          case 'logisticsFeeSum':
            value = item.logisticsFeeSum || 0;
            break;
          case 'packagingCostSum':
            value = item.packagingCostSum || 0;
            break;
          case 'otherCostsSum':
            value = item.otherCostsSum || 0;
            break;
          case 'reshipmentCostSum':
            value = item.reshipmentCostSum || 0;
            break;
          case 'grossProfitSum':
            value = item.grossProfitSum || 0;
            break;
          default:
            value = item.revenueSum || 0;
        }
        return {
          name: item.groupValue || '未知仓库',
          value: Number(value)
        };
      }).filter(item => item.value > 0);

      let option = {
        color: [
          "#37a2da",
          "#32c5e9",
          "#9fe6b8",
          "#ffdb5c",
          "#ff9f7f",
          "#fb7293",
          "#e7bcf3",
          "#8378ea",
        ],
        tooltip: {
          trigger: "item",
          formatter: (params) => {
            const value = this.formatNumber(params.value);
            const percentage = params.percent.toFixed(1);
            return `${this.getWarehouseDimensionLabel()}汇总<br/>${params.name} : ${value} (${percentage}%)`;
          },
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff',
            fontSize: 12
          },
          extraCssText: 'box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);'
        },
        legend: {
          orient: "vertical",
          right: 10,
          top: "20%",
          textStyle: {
            color: "#fff",
          },
        },
        series: [
          {
            name: `仓库${this.getWarehouseDimensionLabel()}统计`,
            type: "pie",
            radius: ["40%", "70%"],
            center: ["30%", "45%"],
            avoidLabelOverlap: false,
            itemStyle: {
              borderColor: "#fff",
              borderWidth: 1
            },
            label: {
              show: false,
              position: "center"
            },
            emphasis: {
              label: {
                show: true,
                fontSize: "16",
                fontWeight: "bold",
                formatter: (params) => {
                  return `${params.name}\n${this.formatNumber(params.value)}\n${params.percent}%`;
                }
              }
            },
            labelLine: {
              show: false
            },
            data: chartData
          }
        ]
      };
      pieChart.setOption(option); //生成图表
    },
    //中间左侧图表 - 圆饼图
    center_left_chart() {
      let mapChart = this.$echarts.init(document.getElementById("center_left_chart"));
      window.onresize = mapChart.resize;

      // 使用从后端获取的核心费用项数据
      const pieData = [
        { name: '收入', value: Number(this.coreExpensesData.revenue) || 0 },
        { name: '退款', value: Number(this.coreExpensesData.refund) || 0 },
        { name: '采购成本', value: Number(this.coreExpensesData.purchase_cost) || 0 },
        { name: '物流费用', value: Number(this.coreExpensesData.logistics_fee) || 0 },
        { name: '包装费用', value: Number(this.coreExpensesData.packaging_cost) || 0 },
        { name: '其他费用', value: Number(this.coreExpensesData.other_costs) || 0 }
      ].filter(item => item.value > 0); // 只显示有数据的费用项
      let option = {
        title: {
          text: '费用项分布',
          left: 'center',
          textStyle: {
            color: '#fff',
            fontSize: 16
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: (params) => {
            const value = this.formatNumber(params.value);
            const percentage = params.percent.toFixed(1);
            return `${params.name}<br/>金额: ${value} (${percentage}%)`;
          },
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff'
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
            name: '费用项',
            type: 'pie',
            radius: ['40%', '65%'],
            center: ['40%', '50%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '18',
                fontWeight: 'bold',
                formatter: (params) => {
                  return `${params.name}\n${this.formatNumber(params.value)}`;
                }
              }
            },
            labelLine: {
              show: false
            },
            data: pieData
          }
        ]
      };
      mapChart.setOption(option);
      this.loading = false;
    },
    //中间右侧图表 - 雷达图
    center_right_chart() {
      let mapChart = this.$echarts.init(document.getElementById("center_right_chart"));
      window.onresize = mapChart.resize;

      // 使用从后端获取的核心费用项数据
      const maxValues = {
        revenue: Math.max(Number(this.coreExpensesData.revenue) || 0, 1000000),
        refund: Math.max(Number(this.coreExpensesData.refund) || 0, 200000),
        purchase_cost: Math.max(Number(this.coreExpensesData.purchase_cost) || 0, 600000),
        first_mile_cost: Math.max(Number(this.coreExpensesData.first_mile_cost) || 0, 300000),
        logistics_fee: Math.max(Number(this.coreExpensesData.logistics_fee) || 0, 150000),
        gross_profit: Math.max(Number(this.coreExpensesData.gross_profit) || 0, 500000)
      };
      // 雷达图数据 - 六个重要指标
      const indicator = [
        { name: '收入', max: maxValues.revenue },
        { name: '退款', max: maxValues.refund },
        { name: '采购成本', max: maxValues.purchase_cost },
        { name: '头程成本', max: maxValues.first_mile_cost },
        { name: '物流费', max: maxValues.logistics_fee },
        { name: '毛利', max: maxValues.gross_profit }
      ];
      const radarData = [{
        value: [
          Number(this.coreExpensesData.revenue) || 0,
          Number(this.coreExpensesData.refund) || 0,
          Number(this.coreExpensesData.purchase_cost) || 0,
          Number(this.coreExpensesData.first_mile_cost) || 0,
          Number(this.coreExpensesData.logistics_fee) || 0,
          Number(this.coreExpensesData.gross_profit) || 0
        ],
        name: ['收入', '退款', '采购成本', '头程成本', '物流费', '毛利'],
        areaStyle: {
          color: 'rgba(255, 255, 255, 0.2)'
        }
      }];
      let option = {
        title: {
          text: '核心数据雷达图',
          left: 'center',
          textStyle: {
            color: '#fff',
            fontSize: 16
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: (params) => {
            const name = params.data.name;
            const value = params.value;
            let tooltipHtml = `<div style="font-size: 14px;">金额分布: </div>`;
            if (name.length > 0) {
              name.forEach(item => {
                tooltipHtml += `<div style="font-size: 12px;">${item}: ${this.formatNumber(value[name.indexOf(item)])}</div>`;
              });
            } else {
              tooltipHtml += `<div style="font-size: 12px;">暂无数据</div>`;
            }
            return `${tooltipHtml}`;
          },
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
          textStyle: {
            color: '#fff'
          }
        },
        radar: {
          indicator: indicator,
          shape: 'circle',
          splitNumber: 5,
          radius: '70%',
          axisName: {
            color: '#fff',
            borderRadius: 3,
            padding: [3, 5]
          },
          splitLine: {
            lineStyle: {
              color: [
                'rgba(34, 116, 213, 0.1)',
                'rgba(34, 116, 213, 0.2)',
                'rgba(34, 116, 213, 0.4)',
                'rgba(34, 116, 213, 0.6)',
                'rgba(34, 116, 213, 0.8)',
                'rgba(34, 116, 213, 1)'
              ].reverse()
            }
          },
          splitArea: {
            show: true,
            areaStyle: {
              color: ['rgba(255, 255, 255, 0.02)', 'rgba(255, 255, 255, 0.05)']
            }
          },
          axisLine: {
            lineStyle: {
              color: '#00c7ff'
            }
          }
        },
        series: [
          {
            name: '核心指标',
            type: 'radar',
            emphasis: {
              lineStyle: {
                width: 4
              }
            },
            data: radarData,
            symbolSize: 0,
            lineStyle: {
              width: 2,
              color: '#00c7ff'
            },
            itemStyle: {
              color: '#00c7ff'
            }
          }
        ]
      };
      mapChart.setOption(option);
    }
  },
};
</script>

<style lang="scss">
//全局样式部分！！！！
* {
  margin: 0;
  padding: 0;
  list-style-type: none;
  outline: none;
  box-sizing: border-box;
}
html {
  margin: 0;
  padding: 0;
}
body {
  font-family: Arial, Helvetica, sans-serif;
  line-height: 1.2em;
  background-color: #fff;
  margin: 0;
  padding: 0;
}
a {
  color: #343440;
  text-decoration: none;
}
//--------------------------------------------

//页面样式部分！！！！
#index {
  color: #fff;
  width: 1920px;
  height: 1080px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  transform-origin: left top;
  overflow: hidden;
  .bg {
    //整体页面背景
    width: 100%;
    height: 100%;
    padding: 16px 16px 0 16px;
    background-image: url("../../../assets/images/datavisual.png"); //背景图
    background-size: cover; //背景尺寸
    background-position: center center; //背景位置
  }
  //顶部右边装饰效果
  .title_left {
    width: 100%;
    height: 50px;
  }
  //顶部左边装饰效果
  .title_right {
    width: 100%;
    height: 50px;
    margin-top: 0;
  }
  //顶部中间装饰效果
  .title_center {
    width: 100%;
    height: 50px;
  }
  .country-filter {
    text-align: center;
  }
  //顶部中间文字数据可视化系统
  .title_text {
    text-align: center;
    font-size: 24px;
    font-weight: bold;
    margin-top: 14px;
    color: #008cff;
  }
  //时间日期
  .title_time {
    text-align: center;
  }
  //指标模块
  .kpi_box {
    height: 200px;
    width: 100%;
    .kpi_content {
      height: 100%;
      padding: 20px;
      .kpi_item {
        text-align: center;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        .kpi_label {
          margin-top: 10px;
          color: #fff;
          font-size: 16px;
        }
      }
    }
  }
  //中间模块
  .center_box {
    height: 460px;
    width: 100%;
    // 中间模块左右分割样式
    .center_box_left {
      height: 460px;
      width: 100%;
      .center_left_content {
        height: 100%;
        width: 100%;
        padding: 10px;
        #center_left_chart {
          height: 90%;
          width: 100%;
        }
      }
    }
    .center_box_right {
      height: 460px;
      width: 100%;
      .center_right_content {
        height: 100%;
        width: 100%;
        padding: 10px;
        #center_right_chart {
          height: 90%;
          width: 100%;
        }
      }
    }
  }
  //中间折线图
  .line_center {
    width: 100%;
    height: 288px;
  }
  //左1模块
  .left_box1 {
    height: 310px;
    width: 100%;
    margin-bottom: 10px;
    position: relative;
  }
  //左2模块
  .left_box2 {
    height: 310px;
    width: 100%;
    margin-bottom: 10px;
  }
  //左3模块
  .left_box3 {
    height: 310px;
    width: 100%;
  }
  //右1模块
  .right_box1 {
    height: 310px;
    width: 100%;
    margin-bottom: 10px;
  }
  //右2模块
  .right_box2 {
    height: 310px;
    width: 100%;
    margin-bottom: 10px;
  }
  //右3模块
  .right_box3 {
    height: 310px;
    width: 100%;
  }
  //左1模块-玫瑰饼图
  #Rose_diagram {
    height: 70%;
    width: 55%;
  }
  //左1模块-圆环图
  .left_box1_rose_right {
    height: 85%;
    width: 55%;
    position: absolute;
    right: 0;
    top: 0;
  }
  //左1模块-文字部分
  .rose_text {
    display: inline-block;
    margin-top: 4%;
    margin-left: 4%;
  }
  // 左1模块-￥符号样式
  .coin {
    font-size: 20px;
    color: #ffc107;
  }
  //左1模块-（件）样式
  .colorYellow {
    color: yellowgreen;
  }
  //左1模块-数字样式
  .rose_text_nmb {
    font-size: 20px;
    color: #00b891;
  }
  //左2模块 柱状图
  #columnar {
    height: 97%;
    width: 95%;
    margin-left: 3%;
    margin-top: 5px;
  }
  //折线图
  #line_center_diagram {
    height: 100%;
    width: 100%;
  }
  //轮播表格
  .carousel_list {
    width: 96%;
    height: 98%;
    margin-left: 10px;
  }
  //虚线柱状图
  #dotter_bar {
    width: 100%;
    height: 100%;
  }
  //饼状图
  .pie_chart {
    width: 95%;
    height: 95%;
    margin-left: 3%;
    margin-bottom: 5%;
  }
  // 柱状图标题样式
  .chart-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 15px;
    margin-bottom: 5px;
  }
  .chart-title {
    margin: 0;
    font-size: 14px;
    color: #d3d6dd;
  }
  // 选择器样式
  .chart-dimension-selector {
    width: 180px;
    // 确保选择器本身有足够的 z-index
    z-index: 1000;
  }
  // el-select 蓝色样式美化
  .el-select {
    .el-input {
      .el-input__inner {
        background-color: rgba(0, 140, 255, 0.1) !important;
        border: 1px solid #008CFF !important;
        color: #008CFF !important;
        border-radius: 4px;

        &:hover {
          border-color: #00ADDD !important;
        }

        &:focus {
          border-color: #008CFF !important;
          box-shadow: 0 0 5px rgba(0, 140, 255, 0.5) !important;
        }
      }

      .el-input__suffix {
        .el-select__caret {
          color: #008CFF !important;
        }
      }
    }
  }

  .el-select-dropdown {
    background-color: rgba(0, 0, 0, 0.9) !important;
    border: 1px solid #008CFF !important;
    border-radius: 4px;

    .el-select-dropdown__item {
      color: #008CFF !important;
      background-color: transparent !important;

      &:hover {
        background-color: rgba(0, 140, 255, 0.2) !important;
        color: #00ADDD !important;
      }

      &.selected {
        background-color: rgba(0, 140, 255, 0.3) !important;
        color: #00ADDD !important;
      }

      &.hover {
        background-color: rgba(0, 140, 255, 0.2) !important;
      }
    }

    .el-select-dropdown__empty {
      color: #008CFF !important;
    }

    .el-select-dropdown__wrap {
      &::-webkit-scrollbar {
        width: 6px;
      }

      &::-webkit-scrollbar-track {
        background: rgba(0, 140, 255, 0.1);
      }

      &::-webkit-scrollbar-thumb {
        background: #008CFF;
        border-radius: 3px;
      }
    }
  }

  .el-popper {
    &[x-placement^=bottom] {
      .popper__arrow {
        border-bottom-color: #008CFF !important;

        &::after {
          border-bottom-color: rgba(0, 0, 0, 0.9) !important;
        }
      }
    }

    &[x-placement^=top] {
      .popper__arrow {
        border-top-color: #008CFF !important;

        &::after {
          border-top-color: rgba(0, 0, 0, 0.9) !important;
        }
      }
    }
  }
}
</style>

