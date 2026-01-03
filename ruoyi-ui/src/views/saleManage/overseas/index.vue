<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="店铺名称" prop="store">
        <el-input
          v-model="queryParams.store"
          placeholder="请输入店铺名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="结算时间">
        <el-date-picker
          v-model="daterangeSettlementTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="订单号" prop="orderNumber">
        <el-input
          v-model="queryParams.orderNumber"
          placeholder="请输入订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品ID" prop="productId">
        <el-input
          v-model="queryParams.productId"
          placeholder="请输入商品ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="品牌名称" prop="brand">
        <el-input
          v-model="queryParams.brand"
          placeholder="请输入品牌名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="SKU编码" prop="sku">
        <el-input
          v-model="queryParams.sku"
          placeholder="请输入SKU编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品类目" prop="category">
        <el-input
          v-model="queryParams.category"
          placeholder="请输入商品类目"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="费用项" prop="feeItem">
        <el-input
          v-model="queryParams.feeItem"
          placeholder="请输入费用项"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="平台流水单号" prop="platformTransactionNumber">
        <el-input
          v-model="queryParams.platformTransactionNumber"
          placeholder="请输入平台流水单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="限时供货价任务ID" prop="limitedSupplyPriceTaskId">
        <el-input
          v-model="queryParams.limitedSupplyPriceTaskId"
          placeholder="请输入限时供货价任务ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开发员姓名" prop="developer">
        <el-input
          v-model="queryParams.developer"
          placeholder="请输入开发员姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="运营人员姓名" prop="operator">
        <el-input
          v-model="queryParams.operator"
          placeholder="请输入运营人员姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发货仓库" prop="shippingWarehouse">
        <el-input
          v-model="queryParams.shippingWarehouse"
          placeholder="请输入发货仓库"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['overseas:data:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['overseas:data:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['overseas:data:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['overseas:data:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['overseas:data:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dataList" height="850" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" fixed="left"/>
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="店铺名称" align="center" prop="store" />
      <el-table-column label="结算时间" align="center" prop="settlementTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.settlementTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="订单号" align="center" prop="orderNumber" />
      <el-table-column label="商品ID" align="center" prop="productId" />
      <el-table-column label="品牌名称" align="center" prop="brand" />
      <el-table-column label="SKU编码" align="center" prop="sku" />
      <el-table-column label="商品类目" align="center" prop="category" />
      <el-table-column label="费用项" align="center" prop="feeItem" />
      <el-table-column label="金额" align="center" prop="amountCny" />
      <el-table-column label="平台流水单号" align="center" prop="platformTransactionNumber" />
      <el-table-column label="限时供货价任务ID" align="center" prop="limitedSupplyPriceTaskId" />
      <el-table-column label="收入金额" align="center" prop="revenue" />
      <el-table-column label="退款金额" align="center" prop="refund" />
      <el-table-column label="分摊比例" align="center" prop="allocationRatio" />
      <el-table-column label="采购成本" align="center" prop="purchaseCost" />
      <el-table-column label="头程成本" align="center" prop="firstMileCost" />
      <el-table-column label="物流费" align="center" prop="logisticsFee" />
      <el-table-column label="包装费用" align="center" prop="packagingCost" />
      <el-table-column label="其他费用" align="center" prop="otherCosts" />
      <el-table-column label="补发费用" align="center" prop="reshipmentCost" />
      <el-table-column label="毛利" align="center" prop="grossProfit" />
      <el-table-column label="毛利率" align="center" prop="grossProfitMargin" />
      <el-table-column label="开发员姓名" align="center" prop="developer" />
      <el-table-column label="运营人员姓名" align="center" prop="operator" />
      <el-table-column label="发货仓库" align="center" prop="shippingWarehouse" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['overseas:data:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['overseas:data:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改海外托管业务数据管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="店铺名称" prop="store">
          <el-input v-model="form.store" placeholder="请输入店铺名称" />
        </el-form-item>
        <el-form-item label="结算时间" prop="settlementTime">
          <el-date-picker clearable
                          v-model="form.settlementTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择结算时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单号" prop="orderNumber">
          <el-input v-model="form.orderNumber" placeholder="请输入订单号" />
        </el-form-item>
        <el-form-item label="商品ID" prop="productId">
          <el-input v-model="form.productId" placeholder="请输入商品ID" />
        </el-form-item>
        <el-form-item label="品牌名称" prop="brand">
          <el-input v-model="form.brand" placeholder="请输入品牌名称" />
        </el-form-item>
        <el-form-item label="SKU编码" prop="sku">
          <el-input v-model="form.sku" placeholder="请输入SKU编码" />
        </el-form-item>
        <el-form-item label="商品类目" prop="category">
          <el-input v-model="form.category" placeholder="请输入商品类目" />
        </el-form-item>
        <el-form-item label="费用项" prop="feeItem">
          <el-input v-model="form.feeItem" placeholder="请输入费用项" />
        </el-form-item>
        <el-form-item label="金额" prop="amountCny">
          <el-input v-model="form.amountCny" placeholder="请输入金额" />
        </el-form-item>
        <el-form-item label="平台流水单号" prop="platformTransactionNumber">
          <el-input v-model="form.platformTransactionNumber" placeholder="请输入平台流水单号" />
        </el-form-item>
        <el-form-item label="限时供货价任务ID" prop="limitedSupplyPriceTaskId">
          <el-input v-model="form.limitedSupplyPriceTaskId" placeholder="请输入限时供货价任务ID" />
        </el-form-item>
        <el-form-item label="收入金额" prop="revenue">
          <el-input v-model="form.revenue" placeholder="请输入收入金额" />
        </el-form-item>
        <el-form-item label="退款金额" prop="refund">
          <el-input v-model="form.refund" placeholder="请输入退款金额" />
        </el-form-item>
        <el-form-item label="分摊比例" prop="allocationRatio">
          <el-input v-model="form.allocationRatio" placeholder="请输入分摊比例" />
        </el-form-item>
        <el-form-item label="采购成本" prop="purchaseCost">
          <el-input v-model="form.purchaseCost" placeholder="请输入采购成本" />
        </el-form-item>
        <el-form-item label="头程成本" prop="firstMileCost">
          <el-input v-model="form.firstMileCost" placeholder="请输入头程成本" />
        </el-form-item>
        <el-form-item label="物流费" prop="logisticsFee">
          <el-input v-model="form.logisticsFee" placeholder="请输入物流费" />
        </el-form-item>
        <el-form-item label="包装费用" prop="packagingCost">
          <el-input v-model="form.packagingCost" placeholder="请输入包装费用" />
        </el-form-item>
        <el-form-item label="其他费用" prop="otherCosts">
          <el-input v-model="form.otherCosts" placeholder="请输入其他费用" />
        </el-form-item>
        <el-form-item label="补发费用" prop="reshipmentCost">
          <el-input v-model="form.reshipmentCost" placeholder="请输入补发费用" />
        </el-form-item>
        <el-form-item label="毛利" prop="grossProfit">
          <el-input v-model="form.grossProfit" placeholder="请输入毛利" />
        </el-form-item>
        <el-form-item label="毛利率" prop="grossProfitMargin">
          <el-input v-model="form.grossProfitMargin" placeholder="请输入毛利率" />
        </el-form-item>
        <el-form-item label="开发员姓名" prop="developer">
          <el-input v-model="form.developer" placeholder="请输入开发员姓名" />
        </el-form-item>
        <el-form-item label="运营人员姓名" prop="operator">
          <el-input v-model="form.operator" placeholder="请输入运营人员姓名" />
        </el-form-item>
        <el-form-item label="发货仓库" prop="shippingWarehouse">
          <el-input v-model="form.shippingWarehouse" placeholder="请输入发货仓库" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 数据导入对话框（流程化） -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="600px" append-to-body @close="handleImportClose">
      <div class="import-container">
        <!-- 步骤1：下载模板 -->
        <div class="import-step" v-if="upload.step === 1">
          <div class="step-header">
            <el-steps :active="1" simple>
              <el-step title="下载模板" icon="el-icon-download"></el-step>
              <el-step title="填写数据" icon="el-icon-edit"></el-step>
              <el-step title="上传文件" icon="el-icon-upload2"></el-step>
            </el-steps>
          </div>
          <div class="step-content">
            <div class="step-info">
              <h4>第一步：下载导入模板</h4>
              <p class="tip-text">请先下载标准模板文件，按照模板格式准备数据</p>

              <div class="template-info">
                <div class="info-item">
                  <i class="el-icon-info"></i>
                  <span>模板包含所有必要字段和格式要求</span>
                </div>
                <div class="info-item">
                  <i class="el-icon-warning"></i>
                  <span>请严格按照模板格式填写数据</span>
                </div>
                <div class="info-item">
                  <i class="el-icon-success"></i>
                  <span>建议使用"粘贴为数值"功能避免格式问题</span>
                </div>
              </div>
              <el-button
                type="primary"
                icon="el-icon-download"
                @click="handleDownloadTemplate"
                class="download-btn">
                下载导入模板
              </el-button>
            </div>
          </div>
        </div>
        <!-- 步骤2：填写数据提示 -->
        <div class="import-step" v-if="upload.step === 2">
          <div class="step-header">
            <el-steps :active="2" simple>
              <el-step title="下载模板" icon="el-icon-download"></el-step>
              <el-step title="填写数据" icon="el-icon-edit"></el-step>
              <el-step title="上传文件" icon="el-icon-upload2"></el-step>
            </el-steps>
          </div>
          <div class="step-content">
            <div class="step-info">
              <h4>第二步：填写导入数据</h4>

              <div class="data-fill-tips">
                <el-alert
                  title="重要提示：请使用'粘贴为数值'功能"
                  type="warning"
                  description="从其他系统复制数据时，请务必使用Excel的'粘贴为数值'功能，避免格式问题导致导入失败"
                  show-icon
                  :closable="false">
                </el-alert>
                <div class="tips-list">
                  <div class="tip-item">
                    <div class="tip-icon">1</div>
                    <div class="tip-content">
                      <strong>复制数据</strong>
                      <p>从其他系统或文档中复制需要导入的数据</p>
                    </div>
                  </div>

                  <div class="tip-item">
                    <div class="tip-icon">2</div>
                    <div class="tip-content">
                      <strong>粘贴为数值</strong>
                      <p>在Excel中右键 → 粘贴选项 → 粘贴为数值 (Ctrl+Shift+V)</p>
                    </div>
                  </div>

                  <div class="tip-item">
                    <div class="tip-icon">3</div>
                    <div class="tip-content">
                      <strong>检查格式</strong>
                      <p>请将日期格式修改为：年-月-日或年/月/日 时/分/秒，如2025-01-01或2025/01/01 09:00:00。否则数据将导入失败</p>
                    </div>
                  </div>

                  <div class="tip-item">
                    <div class="tip-icon">4</div>
                    <div class="tip-content">
                      <strong>保存文件</strong>
                      <p>完成数据填写后保存Excel文件</p>
                    </div>
                  </div>
                </div>
              </div>
              <div class="action-buttons">
                <el-button
                  type="primary"
                  icon="el-icon-upload"
                  @click="upload.step = 3">
                  前往上传
                </el-button>
                <el-button
                  icon="el-icon-download"
                  @click="handleDownloadTemplate">
                  重新下载模板
                </el-button>
              </div>
            </div>
          </div>
        </div>
        <!-- 步骤3：上传文件 -->
        <div class="import-step" v-if="upload.step === 3">
          <div class="step-header">
            <el-steps :active="3" simple>
              <el-step title="下载模板" icon="el-icon-download"></el-step>
              <el-step title="填写数据" icon="el-icon-edit"></el-step>
              <el-step title="上传文件" icon="el-icon-upload2"></el-step>
            </el-steps>
          </div>
          <div class="step-content">
            <div class="step-info">
              <h4>第三步：上传数据文件</h4>

              <el-alert
                title="请上传已填写数据的Excel文件"
                type="info"
                description="系统将自动验证数据格式并导入"
                show-icon
                :closable="false"
                class="upload-alert">
              </el-alert>
              <el-upload
                ref="upload"
                class="import-upload"
                :limit="1"
                accept=".xlsx, .xls"
                :headers="upload.headers"
                :action="upload.url"
                :disabled="upload.isUploading"
                :on-progress="handleFileUploadProgress"
                :on-success="handleFileSuccess"
                :auto-upload="false"
                drag>
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将填写好的Excel文件拖到此处，或<em>点击上传</em></div>
                <div class="el-upload__tip" slot="tip">
                  <div class="upload-requirements">
                    <p><strong>文件要求：</strong></p>
                    <ul>
                      <li>仅支持 .xlsx 或 .xls 格式</li>
                      <li>必须使用下载的模板格式</li>
                      <li>确保数据已使用"粘贴为数值"</li>
                    </ul>
                  </div>
                </div>
              </el-upload>
              <div class="upload-actions">
                <el-button
                  type="text"
                  icon="el-icon-back"
                  @click="upload.step = 2">
                  返回上一步
                </el-button>
                <el-button
                  type="text"
                  icon="el-icon-refresh"
                  @click="handleResetImport">
                  重新开始
                </el-button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button
          v-if="upload.step === 1"
          type="primary"
          @click="upload.step = 2">
          下一步
        </el-button>
        <el-button
          v-if="upload.step === 3"
          type="primary"
          @click="submitFileForm">
          确认上传
        </el-button>
        <el-button @click="handleImportClose">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listData, getData, delData, addData, updateData } from "@/api/saleManage/overseas/data"
import { getToken } from "@/utils/auth";

export default {
  name: "Data",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 海外托管业务数据管理表格数据
      dataList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 发货仓库时间范围
      daterangeSettlementTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        store: null,
        settlementTime: null,
        orderNumber: null,
        productId: null,
        brand: null,
        sku: null,
        category: null,
        feeItem: null,
        amountCny: null,
        platformTransactionNumber: null,
        limitedSupplyPriceTaskId: null,
        revenue: null,
        refund: null,
        allocationRatio: null,
        purchaseCost: null,
        firstMileCost: null,
        logisticsFee: null,
        packagingCost: null,
        otherCosts: null,
        reshipmentCost: null,
        grossProfit: null,
        grossProfitMargin: null,
        developer: null,
        operator: null,
        shippingWarehouse: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        store: [
          { required: true, message: "店铺名称不能为空", trigger: "blur" }
        ],
        settlementTime: [
          { required: true, message: "结算时间不能为空", trigger: "blur" }
        ],
        orderNumber: [
          { required: true, message: "订单号不能为空", trigger: "blur" }
        ],
        feeItem: [
          { required: true, message: "费用项不能为空", trigger: "blur" }
        ],
        amountCny: [
          { required: true, message: "金额不能为空", trigger: "blur" }
        ],
      },
      // 数据导入参数（流程化）
      upload: {
        // 是否显示弹出层（数据导入）
        open: false,
        // 弹出层标题（数据导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 当前步骤 (1:下载模板, 2:填写数据, 3:上传文件)
        step: 1,
        // 是否已下载模板
        hasDownloaded: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/overseas/data/importData"
      },
      importLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询海外托管业务数据管理列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeSettlementTime && '' != this.daterangeSettlementTime) {
        this.queryParams.params["beginSettlementTime"] = this.daterangeSettlementTime[0]
        this.queryParams.params["endSettlementTime"] = this.daterangeSettlementTime[1]
      }
      listData(this.queryParams).then(response => {
        this.dataList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        store: null,
        settlementTime: null,
        orderNumber: null,
        productId: null,
        brand: null,
        sku: null,
        category: null,
        feeItem: null,
        amountCny: null,
        platformTransactionNumber: null,
        limitedSupplyPriceTaskId: null,
        revenue: null,
        refund: null,
        allocationRatio: null,
        purchaseCost: null,
        firstMileCost: null,
        logisticsFee: null,
        packagingCost: null,
        otherCosts: null,
        reshipmentCost: null,
        grossProfit: null,
        grossProfitMargin: null,
        developer: null,
        operator: null,
        shippingWarehouse: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeSettlementTime = []
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加海外托管业务数据管理"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getData(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改海外托管业务数据管理"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateData(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addData(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除海外托管业务数据管理编号为"' + ids + '"的数据项？').then(function() {
        return delData(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('overseas/data/export', {
        ...this.queryParams
      }, `data_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      if (this.importLoading) {
        this.$modal.msgError("正在导入数据，请不要重复点击");
        return;
      }
      this.upload.title = "数据导入";
      this.upload.open = true;
      this.upload.step = 1;
      this.upload.hasDownloaded = false;
      this.upload.isUploading = false;
    },
    /** 下载导入模板 */
    handleDownloadTemplate() {
      this.download('overseas/data/importTemplate', {}, `海外托管数据导入模板_${new Date().getTime()}.xlsx`);
      this.upload.hasDownloaded = true;
      this.$modal.msgSuccess("模板下载成功！请按照模板格式填写数据");
    },
    /** 重置导入流程 */
    handleResetImport() {
      this.upload.step = 1;
      this.upload.hasDownloaded = false;
      this.upload.isUploading = false;
      if (this.$refs.upload) {
        this.$refs.upload.clearFiles();
      }
      this.$modal.msg("已重置导入流程，请重新开始");
    },
    /** 关闭导入对话框 */
    handleImportClose() {
      this.upload.open = false;
      this.upload.step = 1;
      this.upload.hasDownloaded = false;
      this.upload.isUploading = false;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.isUploading = false
      this.importLoading = false
      this.$message.closeAll()
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true })
      this.getList()
    },
    // 提交上传文件
    submitFileForm() {
      const file = this.$refs.upload.uploadFiles
      if (!file || file.length === 0 || !file[0].name.toLowerCase().endsWith('.xls') && !file[0].name.toLowerCase().endsWith('.xlsx')) {
        this.$modal.msgError("请选择后缀为 “xls”或“xlsx”的文件。")
        return
      }
      // 关闭导入对话框
      this.upload.open = false
      // 显示导入等待提示
      this.importLoading = true
      this.$message({
        message: '系统正在导入数据，预计导入时间过长，请耐心等待......',
        type: 'success'
      })

      this.$refs.upload.submit()
    }
  }
}
</script>
<style scoped>
/* 数据导入相关样式 */
.import-container {
  padding: 10px 0;
}
.import-step {
  margin-bottom: 20px;
}
.step-header {
  margin-bottom: 20px;
}
.step-content {
  text-align: left;
}
.step-info h4 {
  margin-bottom: 15px;
  color: #303133;
}
.tip-text {
  color: #909399;
  font-size: 14px;
  margin-bottom: 20px;
}
.template-info {
  background: #f8f9fa;
  border-radius: 6px;
  padding: 15px;
  margin-bottom: 20px;
}
.info-item {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
  font-size: 14px;
}
.info-item:last-child {
  margin-bottom: 0;
}
.info-item i {
  margin-right: 8px;
  font-size: 16px;
}
.info-item .el-icon-info {
  color: #409EFF;
}
.info-item .el-icon-warning {
  color: #E6A23C;
}
.info-item .el-icon-success {
  color: #67C23A;
}
.download-btn {
  margin-top: 10px;
}
.data-fill-tips {
  margin-bottom: 20px;
}
.tips-list {
  margin-top: 15px;
}
.tip-item {
  display: flex;
  align-items: flex-start;
  margin-bottom: 15px;
  padding: 12px;
  background: #f8f9fa;
  border-radius: 6px;
}
.tip-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 24px;
  height: 24px;
  background: #409EFF;
  color: white;
  border-radius: 50%;
  font-size: 12px;
  font-weight: bold;
  margin-right: 12px;
  flex-shrink: 0;
}
.tip-content strong {
  display: block;
  margin-bottom: 4px;
  color: #303133;
}
.tip-content p {
  margin: 0;
  color: #606266;
  font-size: 13px;
}
.action-buttons {
  display: flex;
  gap: 10px;
  justify-content: center;
  margin-top: 20px;
}
.upload-alert {
  margin-bottom: 20px;
}
.import-upload {
  margin-top: 10px;
}
.upload-requirements {
  font-size: 13px;
  color: #606266;
}
.upload-requirements p {
  margin-bottom: 8px;
}
.upload-requirements ul {
  margin: 0;
  padding-left: 20px;
}
.upload-requirements li {
  margin-bottom: 4px;
}
.upload-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid #ebeef5;
}
</style>
