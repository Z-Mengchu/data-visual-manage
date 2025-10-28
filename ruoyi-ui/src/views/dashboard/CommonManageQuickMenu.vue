<template>
  <el-row :gutter="40" class="panel-group">

    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col" v-if="checkRole(['admin']) || checkPermi(['system:user:list'])">
      <div class="card-panel" @click="handleViewData('userManage')">
        <div class="card-panel-icon-wrapper icon-common">
          <svg-icon icon-class="peoples" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description" style="margin: 30px 0px;">
          <div class="card-panel-text quick-menu-name">
            用户管理
          </div>
        </div>
      </div>
    </el-col>

    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col" v-if="checkRole(['admin']) || checkPermi(['system:dept:list'])">
      <div class="card-panel" @click="handleViewData('deptManage')">
        <div class="card-panel-icon-wrapper icon-common">
          <svg-icon icon-class="user" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description" style="margin: 30px 0px;">
          <div class="card-panel-text quick-menu-name">
            部门管理
          </div>
        </div>
      </div>
    </el-col>

    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col" v-if="checkRole(['admin']) || checkPermi(['purchase:paymentPeriod:list'])">
      <div class="card-panel" @click="handleViewData('purchase')">
        <div class="card-panel-icon-wrapper icon-common">
          <svg-icon icon-class="shopping" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description" style="margin: 30px 0px;">
          <div class="card-panel-text quick-menu-name">
            采购账期管理
          </div>
        </div>
      </div>
    </el-col>

    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col" v-if="checkRole(['admin']) || checkPermi(['sale:data:list'])">
      <div class="card-panel" @click="handleViewData('sales')">
        <div class="card-panel-icon-wrapper icon-common">
          <svg-icon icon-class="post" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description" style="margin: 30px 0px;">
          <div class="card-panel-text quick-menu-name">
            全渠道销售数据管理
          </div>
        </div>
      </div>
    </el-col>
  </el-row>

</template>

<script>
import CountTo from 'vue-count-to'

import { checkPermi, checkRole } from "@/utils/permission";

export default {
  components: {
    CountTo
  },
  data() {
    return {
    }
  },
  created() {

  },
  methods: {
    checkRole,
    checkPermi,

    handleViewData(type) {
      if (type === 'userManage')
        this.$router.push('system/user');
      if (type === 'deptManage')
        this.$router.push('system/dept');
      if (type === 'purchase')
        this.$router.push('finance/purchase/payment-period');
      if (type === 'sales')
        this.$router.push('finance/sales/data');
    },
  }
}
</script>

<style lang="scss" scoped>
.panel-group {

  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-common {
        background: #409EFF
      }
    }

    .icon-common {
      color: #409EFF
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .quick-menu-name {
        font-size: 18pt;
        font-weight: bold;
        margin-top: 20px;
        margin-right: 30px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width:550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
