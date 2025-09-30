<template>
  <dv-border-box-8 class="alert-panel">
    <div class="panel-header">
      <span class="title">预警信息</span>
    </div>
    <div class="panel-content">
      <div class="alert-section">
        <dv-scroll-ranking-board
          v-if="processedAlertData.length > 0"
          :config="rankingConfig"
          class="ranking-board"
          :style="rankingBoardStyle"
        />
        <div v-else class="no-alerts">
          <div class="no-alert-text">暂无预警信息</div>
        </div>
      </div>
    </div>
  </dv-border-box-8>
</template>

<script>
export default {
  name: 'AlertPanel',
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
      rankingBoardStyle: {
        height: '100%'
      }
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
            value: alertValue,
            displayValue: alertValue
          };
        });
      }
      return [];
    },

    rankingConfig() {
      return {
        data: this.processedAlertData.map((alert, index) => {
          // 根据预警类型设置不同的颜色
          let color = '#4ecdc4'; // 默认颜色
          if (alert.type.includes('高退款率')) {
            color = '#ff6b6b';
          } else if (alert.type.includes('负毛利')) {
            color = '#ffa500';
          }

          return {
            name: `${alert.type} - ${alert.item}`,
            value: alert.displayValue,
            itemStyle: {
              color: color
            }
          };
        }),
        rowNum: 4,
        waitTime: 2000,
        unit: '元',
        textStyle: {
          color: '#fff',
          fontSize: 14
        },
        titleStyle: {
          color: '#a0c8ff',
          fontSize: 12
        },
        indexStyle: {
          color: '#fff',
          fontSize: 14
        },
        indexBackgroundStyle: {
          color: 'rgba(64, 158, 255, 0.3)'
        }
      };
    }
  },
  methods: {
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
    }
  }
}
</script>

<style scoped>
.alert-panel {
  height: 100%;
  padding: 15px;
  border: 1px solid rgba(64, 158, 255, 0.3);
  border-radius: 8px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
}

.panel-header {
  height: 40px;
  margin-bottom: 20px;
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
}

.alert-section {
  height: 100%;
}

.ranking-board {
  width: 100%;
  height: 100%;
}

.no-alerts {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  color: #a0c8ff;
  text-align: center;
  padding: 40px 20px;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-10px);
  }
}

.no-alert-text {
  font-size: 16px;
  opacity: 0.8;
}

</style>
