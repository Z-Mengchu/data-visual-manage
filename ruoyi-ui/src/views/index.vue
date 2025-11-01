<template>
  <div class="dashboard-editor-container">

    <!-- 管理员身份 -->
    <el-divider content-position="left">常用功能管理</el-divider>
    <CommonManageQuickMenu v-if="checkRole(['admin', 'finance'])" />

  </div>
</template>

<script>
import CommonManageQuickMenu from '@/views/dashboard/CommonManageQuickMenu.vue'

import { checkRole } from "@/utils/permission";

export default {
  name: 'Index',
  components: {
    CommonManageQuickMenu,
  },
  data() {
    return {

    }
  },
  methods: {
    checkRole,
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 0 20px;
}
</style>
