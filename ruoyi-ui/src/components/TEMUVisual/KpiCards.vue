<template>
  <div class="kpi-cards">
    <!-- 核心KPI指标卡片 -->
    <div class="kpi-section">
      <div class="kpi-grid">
        <el-row :gutter="20">
          <el-col :span="12">
            <dv-border-box-12>
              <div class="kpi-card">
                <div class="kpi-title">总收入</div>
                <div class="kpi-value">¥{{ formatNumber(kpiData.totalIncome || 0) }}</div>
              </div>
            </dv-border-box-12>
          </el-col>

          <el-col :span="12">
            <dv-border-box-12>
              <div class="kpi-card">
                <div class="kpi-title">总利润</div>
                <div class="kpi-value">¥{{ formatNumber(kpiData.totalTdActualGrossProfit || 0) }}</div>
              </div>
            </dv-border-box-12>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <dv-border-box-12>
              <div class="kpi-card">
                <div class="kpi-title">平均退款率</div>
                <div class="kpi-value">{{ ((kpiData.refundRatio || 0) * 100).toFixed(1) }}%</div>
              </div>
            </dv-border-box-12>
          </el-col>

          <el-col :span="12">
            <dv-border-box-12>
              <div class="kpi-card">
                <div class="kpi-title">平均毛利率</div>
                <div class="kpi-value">{{ ((kpiData.avgGrossProfitRate || 0) * 100).toFixed(1) }}%</div>
              </div>
            </dv-border-box-12>
          </el-col>
        </el-row>
        <el-row :gutter="40">
          <el-col :span="12" style="display: flex;justify-content: center;align-items: center;">
            <dv-water-level-pond :config="refundRateConfig" style="width:150px;height:200px;margin-top: 10px"/>
          </el-col>
          <el-col :span="12" style="display: flex;justify-content: center;align-items: center;">
            <dv-water-level-pond :config="grossProfitRateConfig"  style="width:150px;height:200px;margin-top: 10px"/>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import { getKpiData, getAverageMetrics } from '@/api/saleManage/temu/temuVisualization'

export default {
  name: 'KpiCards',
  props: {
    filterParams: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      kpiData: {},
      avgMetrics: {},
    }
  },
  mounted() {
    this.loadData()
    this.loadAvgMetrics()
  },
  computed: {
    grossProfitRateConfig(){
      return {
        data: [this.avgMetrics.averageGrossProfitRate]
      }
    },
    refundRateConfig(){
      return {
        data: [this.avgMetrics.averageRefundRate]
      }
    }
  },
  methods: {
    async loadData() {
      try {
        const response = await getKpiData(this.filterParams)
        this.kpiData = response.data || {}
      } catch (error) {
        console.error('加载KPI数据失败:', error)
      }
    },
    async loadAvgMetrics() {
      try {
        const response = await getAverageMetrics(this.filterParams)
        this.avgMetrics = response.data || {}
        this.avgMetrics.averageGrossProfitRate = ((this.avgMetrics.averageGrossProfitRate || 0) * 100).toFixed(1)
        this.avgMetrics.averageRefundRate = ((this.avgMetrics.averageRefundRate || 0) * 100).toFixed(1)
        console.log(this.avgMetrics);
      } catch (error) {
        console.error('加载平均指标数据失败:', error)
      }
    },
    formatPercentage(num) {
      const number = Number(num) || 0;
      return (number * 100).toFixed(1)
    },
    formatNumber(num) {
      const number = Number(num) || 0;
      if (number >= 10000) {
        return (number / 10000).toFixed(1) + '万';
      }
      return number.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    },
    handleFilterChange(params) {
      this.filterParams = {...params}
      this.loadData()
    }
  }
}
</script>

<style scoped>
.kpi-cards {
  height: 100%;
  width: 100%;
}

.kpi-section {
  height: 100%;
}

.kpi-grid {
  margin-bottom: 20px;
}

.kpi-card {
  padding: 15px;
  text-align: center;
}
.kpi-title {
  font-size: 14px;
  margin-bottom: 10px;
  color: #ffffff;
}
.kpi-value {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 5px;
  color: #ffffff;
}

.kpi-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(64, 158, 255, 0.2);
  border-color: rgba(64, 158, 255, 0.5);
}

.kpi-content {
  position: relative;
  z-index: 2;
  display: flex;
  align-items: center;
  height: 100%;
}

.kpi-label {
  font-size: 12px;
  color: #a0c8ff;
}

.water-level {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(180deg, rgba(64, 158, 255, 0.3), rgba(64, 158, 255, 0.1));
  transition: height 1s ease;
  overflow: hidden;
}

.water-wave {
  position: absolute;
  top: -10px;
  left: 0;
  right: 0;
  height: 20px;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
  animation: wave 3s infinite linear;
}

@keyframes wave {
  0% {
    transform: translateX(-100%);
  }
  100% {
    transform: translateX(100%);
  }
}

@keyframes glow {
  0%, 100% {
    opacity: 0.5;
  }
  50% {
    opacity: 1;
  }
}
</style>
