<template>
  <dv-border-box-12 class="indicator-panel">
    <div class="panel-header">
      <span class="title">指标面板</span>
    </div>
    <div class="panel-content">
      <!-- 核心KPI指标卡片 -->
      <div class="kpi-section">
        <div class="kpi-grid">
          <div class="kpi-card tech-card">
            <div class="card-glow"></div>
            <div class="kpi-content">
              <div class="kpi-info">
                <div class="kpi-value">¥{{ formatNumber(kpiData.totalIncome || 0) }}</div>
                <div class="kpi-label">总收入</div>
              </div>
              <div class="water-level" :style="{ height: calculateWaterLevel(kpiData.totalIncome) }">
                <div class="water-wave"></div>
              </div>
            </div>
          </div>

          <div class="kpi-card tech-card">
            <div class="card-glow"></div>
            <div class="kpi-content">
              <div class="kpi-info">
                <div class="kpi-value">¥{{ formatNumber(kpiData.totalTdActualGrossProfit || 0) }}</div>
                <div class="kpi-label">总利润</div>
              </div>
              <div class="water-level" :style="{ height: calculateWaterLevel(kpiData.totalTdActualGrossProfit, 500000) }">
                <div class="water-wave"></div>
              </div>
            </div>
          </div>

          <div class="kpi-card tech-card">
            <div class="card-glow"></div>
            <div class="kpi-content">
              <div class="kpi-info">
                <div class="kpi-value">{{ kpiData.totalOrders || 0 }}</div>
                <div class="kpi-label">总订单数</div>
              </div>
              <div class="water-level" :style="{ height: calculateWaterLevel(kpiData.totalOrders, 10000) }">
                <div class="water-wave"></div>
              </div>
            </div>
          </div>

          <div class="kpi-card tech-card">
            <div class="card-glow"></div>
            <div class="kpi-content">
              <div class="kpi-info">
                <div class="kpi-value">{{ ((kpiData.avgGrossProfitRate || 0) * 100).toFixed(1) }}%</div>
                <div class="kpi-label">平均毛利率</div>
              </div>
              <div class="water-level" :style="{ height: (kpiData.avgGrossProfitRate || 0) * 100 + '%' }">
                <div class="water-wave"></div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 预警信息 -->
      <div class="alert-section">
        <div class="section-title">预警信息</div>
        <div class="alert-list">
          <div
            v-for="(alert, index) in processedAlertData"
            :key="index"
            class="alert-item tech-alert"
            :class="getAlertClass(alert)"
          >
            <div class="alert-glow"></div>
            <div class="alert-content">
              <div class="alert-type-icon">
                <span v-if="alert.type.includes('高退款率')">💸</span>
                <span v-else-if="alert.type.includes('负毛利')">📉</span>
              </div>
              <div class="alert-info">
                <div class="alert-type">{{ alert.type }}</div>
                <div class="alert-item-name">{{ alert.item }}</div>
              </div>
              <div class="alert-value">{{ alert.value }}</div>
            </div>
          </div>
          <div v-if="!processedAlertData.length" class="no-alerts">
            <div class="no-alert-icon">✅</div>
            <div class="no-alert-text">暂无预警信息</div>
          </div>
        </div>
      </div>
    </div>
  </dv-border-box-12>
</template>

<script>
import { getKpiData } from '@/api/saleManage/temu/temuVisualization'

export default {
  name: 'IndicatorPanel',
  props: {
    filterParams: {
      type: Object,
      default: () => ({})
    },
    alertData: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      kpiData: {}
    }
  },
  computed: {
    processedAlertData() {
      if (this.alertData && this.alertData.length > 0) {
        return this.alertData.map(alert => {
          const alertType = (alert && alert.alertType) || '预警';
          const alertItem = (alert && alert.item) || '未知项目';
          const alertValue = this.formatAlertValue(alert);

          return {
            type: alertType,
            item: alertItem,
            value: alertValue
          };
        });
      }
      return [];
    }
  },
  mounted() {
    this.loadData()
    // this.$bus.on('filterChange', this.handleFilterChange)
  },
  beforeDestroy() {
    // this.$bus.off('filterChange', this.handleFilterChange)
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
    calculateWaterLevel(value, maxValue = 10000000) {
      const percentage = Math.min((value || 0) / maxValue * 100, 100);
      return percentage + '%';
    },
    getAlertClass(alert) {
      if (!alert || !alert.type) {
        return 'alert-normal';
      }
      if (alert.type.includes('高退款率')) {
        return 'alert-high-refund';
      } else if (alert.type.includes('负毛利')) {
        return 'alert-negative-profit';
      }
      return 'alert-normal';
    },
    formatAlertValue(alert) {
      if (!alert || !alert.value) {
        return '-';
      }
      if (alert.type && alert.type.includes('退款率')) {
        const value = parseFloat(alert.value);
        if (isNaN(value)) {
          return alert.value;
        }
        return (value * 100).toFixed(2) + '%';
      }
      return alert.value;
    },
    formatNumber(num) {
      const number = Number(num) || 0;
      if (number >= 1000000) {
        return (number / 1000000).toFixed(1) + 'M';
      } else if (number >= 10000) {
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
.indicator-panel {
  height: 100%;
  padding: 15px;
  border: 1px solid rgba(64, 158, 255, 0.3);
  border-radius: 8px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
}

.panel-header {
  height: 40px;
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid rgba(64, 158, 255, 0.3);
}

.panel-header .title {
  font-size: 18px;
  font-weight: bold;
  color: #fff;
  text-shadow: 0 0 10px rgba(64, 158, 255, 0.5);
}

.panel-content {
  height: calc(100% - 60px);
  display: flex;
  flex-direction: column;
}

.kpi-section {
  margin-bottom: 25px;
}

.kpi-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 1fr;
  gap: 15px;
  height: 200px;
}

.kpi-card {
  position: relative;
  border: 1px solid rgba(64, 158, 255, 0.2);
  border-radius: 8px;
  padding: 15px;
  overflow: hidden;
  transition: all 0.3s ease;
}

.kpi-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(64, 158, 255, 0.2);
  border-color: rgba(64, 158, 255, 0.5);
}

.card-glow {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 2px;
  animation: glow 2s infinite;
}

.kpi-content {
  position: relative;
  z-index: 2;
  display: flex;
  align-items: center;
  height: 100%;
}

.kpi-icon {
  font-size: 24px;
  margin-right: 12px;
  opacity: 0.8;
}

.kpi-info {
  flex: 1;
}

.kpi-value {
  font-size: 16px;
  font-weight: bold;
  color: #fff;
  margin-bottom: 4px;
  text-shadow: 0 0 8px rgba(255, 255, 255, 0.3);
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

.alert-section {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.section-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 15px;
  color: #fff;
  display: flex;
  align-items: center;
}

.title-icon {
  margin-right: 8px;
}

.alert-list {
  flex: 1;
  overflow-y: auto;
  max-height: 200px;
}

.alert-item {
  position: relative;
  margin-bottom: 10px;
  border-radius: 6px;
  padding: 12px;
  transition: all 0.3s ease;
  overflow: hidden;
}

.tech-alert {
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.alert-glow {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 1px;
  background: linear-gradient(90deg, transparent, #ff6b6b, transparent);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.alert-item:hover .alert-glow {
  opacity: 1;
}

.alert-content {
  display: flex;
  align-items: center;
  position: relative;
  z-index: 2;
}

.alert-type-icon {
  font-size: 18px;
  margin-right: 12px;
  width: 24px;
  text-align: center;
}

.alert-info {
  flex: 1;
}

.alert-type {
  font-size: 12px;
  font-weight: bold;
  margin-bottom: 2px;
}

.alert-item-name {
  font-size: 11px;
  color: #a0c8ff;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.alert-value {
  font-size: 12px;
  font-weight: bold;
  min-width: 60px;
  text-align: right;
}

.alert-high-refund {
  border-left: 4px solid #ff6b6b;
  background: linear-gradient(90deg, rgba(255, 107, 107, 0.1), transparent);
}

.alert-negative-profit {
  border-left: 4px solid #ffa500;
  background: linear-gradient(90deg, rgba(255, 165, 0, 0.1), transparent);
}

.alert-normal {
  border-left: 4px solid #4ecdc4;
  background: linear-gradient(90deg, rgba(78, 205, 196, 0.1), transparent);
}

.no-alerts {
  text-align: center;
  color: #a0c8ff;
  padding: 30px 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.no-alert-icon {
  font-size: 32px;
  margin-bottom: 10px;
  opacity: 0.7;
}

.no-alert-text {
  font-size: 14px;
}

/* 滚动条样式 */
.alert-list::-webkit-scrollbar {
  width: 6px;
}

.alert-list::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.1);
  border-radius: 3px;
}

.alert-list::-webkit-scrollbar-thumb {
  background: rgba(64, 158, 255, 0.5);
  border-radius: 3px;
}

.alert-list::-webkit-scrollbar-thumb:hover {
  background: rgba(64, 158, 255, 0.7);
}
</style>
