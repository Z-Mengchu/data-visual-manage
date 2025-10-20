<template>
  <div class="temu-dashboard">
    <dv-full-screen-container class="temu-container">
      <dv-loading v-show="loading">Loading...</dv-loading>
      <dv-border-box-11 title="TEMU数据大屏">
        <!-- 整合日期和时间到标题区域 -->
        <div class="header-info">
          <span class="date-display">{{ dateYear }} {{ dateWeek }}</span>
          <span class="time-display">当前时间：{{ dateDay }}</span>
        </div>

        <!-- 筛选区域和主内容区域容器 -->
        <div class="content-wrapper">
          <!-- 筛选区域 -->
          <div class="filter-area">
            <div class="filter-row">
              <div class="filter-item">
                <el-date-picker
                  v-model="filterParams.startDate"
                  type="date"
                  placeholder="付款日期开始"
                  value-format="yyyy-MM-dd"
                  @change="handleFilterChange"
                />
              </div>
              <div class="filter-item">
                <el-date-picker
                  v-model="filterParams.endDate"
                  type="date"
                  placeholder="付款日期结束"
                  value-format="yyyy-MM-dd"
                  @change="handleFilterChange"
                />
              </div>
              <div class="filter-item">
                <el-button type="primary" @click="resetFilters">重置</el-button>
              </div>
            </div>
          </div>

          <!-- 主内容区域 -->
          <div class="dashboard-content">
            <!-- 第一行-->
            <div class="content-row first-row">
              <div class="content-cell">
                <DevelopmentPerspective
                  :filterParams="filterParams"
                  @sortChange="handleSortChange"
                />
              </div>
              <div class="content-cell">
                <KpiCards
                  :filterParams="filterParams" />
              </div>
              <div class="content-cell">
                <CategoryPerspective
                  :filterParams="filterParams"
                  @sortChange="handleSortChange"
                />
              </div>
            </div>

            <!-- 第二行：运营员透视图和预警信息 -->
            <div class="content-row second-row">
              <div class="content-cell">
                <OperatorPerspective
                  :filterParams="filterParams"
                  @sortChange="handleSortChange"
                />
              </div>
              <div class="content-cell">
                <AlertPanel
                  :filterParams="filterParams"
                  :alertData="alertData"
                />
              </div>
            </div>
          </div>
        </div>
      </dv-border-box-11>
    </dv-full-screen-container>
  </div>
</template>

<script>
import OperatorPerspective from '@/components/TEMUVisual/OperatorPerspective.vue'
import DevelopmentPerspective from '@/components/TEMUVisual/DevelopmentPerspective.vue'
import CategoryPerspective from '@/components/TEMUVisual/CategoryPerspective.vue'
import KpiCards from '@/components/TEMUVisual/KpiCards.vue'
import AlertPanel from '@/components/TEMUVisual/AlertPanel.vue'
import { getFilterOptions, getAlertData } from '@/api/saleManage/temu/temuVisualization'
import { formatTime2 } from "@/utils";

export default {
  name: 'TEMUDashboard',
  components: {
    OperatorPerspective,
    DevelopmentPerspective,
    CategoryPerspective,
    KpiCards,
    AlertPanel
  },
  data() {
    return {
      //loading图
      loading: true,
      dateDay: null,
      dateYear: null,
      dateWeek: null,
      weekday: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
      filterParams: {
        startDate: null,
        endDate: null,
        category: null,
        developer: null,
        operator: null,
        countryRegion: null
      },
      filterOptions: {
        categories: [],
        developers: [],
        operators: [],
        countries: []
      },
      alertData: [],
      timing: null
    }
  },
  mounted() {
    this.timeFn();
    this.loadFilterOptions()
    this.loadAlertData()
    this.handleResize()
    window.addEventListener('resize', this.handleResize)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.handleResize)
    if (this.timing) {
      clearInterval(this.timing)
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
    async loadFilterOptions() {
      try {
        const response = await getFilterOptions()
        const data = response.data || {}
        this.filterOptions.categories = data.categories ? data.categories.split(',') : []
        this.filterOptions.developers = data.developers ? data.developers.split(',') : []
        this.filterOptions.operators = data.operators ? data.operators.split(',') : []
        this.filterOptions.countries = data.countries ? data.countries.split(',') : []
      } catch (error) {
        console.error('加载筛选选项失败:', error)
      }
    },
    async loadAlertData() {
      try {
        const response = await getAlertData(this.filterParams)
        this.alertData = response.data || []
        this.loading = false;
      } catch (error) {
        console.error('加载预警数据失败:', error)
      }
    },
    handleFilterChange() {
      this.loading = true;
      this.loadAlertData()
    },
    resetFilters() {
      this.filterParams = {
        startDate: null,
        endDate: null,
        category: null,
        developer: null,
        operator: null,
        countryRegion: null
      }
      this.handleFilterChange()
    },
    handleSortChange(params) {
      console.log('排序变更:', params)
    },
    handleResize() {
      this.$nextTick(() => {
        // this.$bus.emit('resizeCharts')
      })
    }
  }
}
</script>

<style scoped>
.temu-dashboard {
  background-color: rgb(9, 18, 32);
  background-image: url("../../../assets/images/bg.jpg");
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.temu-container {
  width: 100%;
  height: 100vh;
}

.content-wrapper {
  width: 100%;
  height: calc(100vh - 80px);
  padding: 10px;
  box-sizing: border-box;
  overflow: hidden;
}

.header-info {
  display: flex;
  justify-content: space-between;
  padding: 10px 20px;
  background: rgba(0, 0, 0, 0.2);
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  color: #fff;
  font-size: 18px;
  min-height: 50px;
  box-sizing: border-box;
}

.date-display {
  font-weight: bold;
}

.time-display {
  margin-left: 20px;
}

.filter-area {
  height: 60px;
  margin-bottom: 10px;
  background: rgba(255, 255, 255, 0.1);
  padding: 10px;
  border-radius: 4px;
  box-sizing: border-box;
}

.filter-row {
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 100%;
}

.filter-item {
  width: 16%;
  height: 100%;
}

.dashboard-content {
  height: calc(100% - 70px);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  min-height: 0;
}

.content-row {
  display: flex;
  width: 100%;
  min-height: 0;
  flex: 1;
}

.first-row {
  flex: 1;
  margin-bottom: 10px;
  min-height: 400px;
}

.second-row {
  flex: 1;
  min-height: 400px;
}

.content-cell {
  flex: 1;
  margin: 0 5px;
  min-height: 0;
  display: flex;
  flex-direction: column;
}

.left-panel {
  flex: 1;
}

.right-panel {
  flex: 1;
}

/* 调整筛选器样式 */
::v-deep .el-date-editor,
::v-deep .el-select {
  width: 100%;
  height: 100%;
}

::v-deep .el-date-editor .el-input__inner,
::v-deep .el-select .el-input__inner {
  background-color: #1e3a68 !important;
  border-color: #2a3f6c;
  color: #fff;
  height: 100%;
  line-height: normal;
}

::v-deep .el-date-editor .el-input__icon,
::v-deep .el-select .el-input__icon {
  color: #fff;
  line-height: 36px;
}

::v-deep .el-button {
  width: 100%;
  height: 100%;
  background-color: #1e3a68;
  border-color: #2a3f6c;
  color: #fff;
}

::v-deep .el-button:hover {
  background-color: #2a3f6c;
  border-color: #3a4f7c;
}

/* 确保DataV组件正确渲染 */
::v-deep .dv-border-box-11 {
  height: 100vh !important;
}

::v-deep .dv-border-box-12 {
  height: 100% !important;
}
</style>
