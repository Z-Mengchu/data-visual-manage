<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="采购单号" prop="purchaseOrderNumber">
        <el-input
          v-model="queryParams.purchaseOrderNumber"
          placeholder="请输入采购单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购计划编号" prop="purchasePlanNumber">
        <el-input
          v-model="queryParams.purchasePlanNumber"
          placeholder="请输入采购计划编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="1688账号" prop="account1688">
        <el-input
          v-model="queryParams.account1688"
          placeholder="请输入1688账号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购仓库" prop="purchaseWarehouse">
        <el-input
          v-model="queryParams.purchaseWarehouse"
          placeholder="请输入采购仓库"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购员" prop="purchaser">
        <el-input
          v-model="queryParams.purchaser"
          placeholder="请输入采购员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购日期">
        <el-date-picker
          v-model="daterangePurchaseDate"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="货位" prop="location">
        <el-input
          v-model="queryParams.location"
          placeholder="请输入货位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="SKU" prop="sku">
        <el-input
          v-model="queryParams.sku"
          placeholder="请输入SKU"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购单价" prop="purchaseUnitPrice">
        <el-input
          v-model="queryParams.purchaseUnitPrice"
          placeholder="请输入采购单价"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购数量" prop="purchaseQuantity">
        <el-input
          v-model="queryParams.purchaseQuantity"
          placeholder="请输入采购数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="货款" prop="goodsPayment">
        <el-input
          v-model="queryParams.goodsPayment"
          placeholder="请输入货款"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商名称" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="到货日期" prop="arrivalDate">
        <el-date-picker clearable
                        v-model="queryParams.arrivalDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择到货日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="账期天数" prop="paymentPeriodDays">
        <el-input
          v-model="queryParams.paymentPeriodDays"
          placeholder="请输入账期天数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="到期付款时间">
        <el-date-picker
          v-model="daterangeDuePaymentDate"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="采购部门" prop="purchaseDepartment">
        <el-input
          v-model="queryParams.purchaseDepartment"
          placeholder="请输入采购部门"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否已付款" prop="isPaid">
        <el-select v-model="queryParams.isPaid" placeholder="请选择是否已付款" clearable>
          <el-option
            v-for="dict in dict.type.logic_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreatedAt"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="更新时间">
        <el-date-picker
          v-model="daterangeUpdatedAt"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
          v-hasPermi="['purchase:paymentPeriod:add']"
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
          v-hasPermi="['purchase:paymentPeriod:edit']"
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
          v-hasPermi="['purchase:paymentPeriod:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['purchase:paymentPeriod:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['sale:data:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="paymentPeriodList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" fixed="left"/>
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="采购单号" align="center" prop="purchaseOrderNumber" />
      <el-table-column label="采购计划编号" align="center" prop="purchasePlanNumber" />
      <el-table-column label="1688账号" align="center" prop="account1688" />
      <el-table-column label="采购仓库" align="center" prop="purchaseWarehouse" />
      <el-table-column label="采购员" align="center" prop="purchaser" />
      <el-table-column label="采购日期" align="center" prop="purchaseDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.purchaseDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="图片" align="center" prop="imageUrl">
        <template slot-scope="scope">
          <image-preview
            v-show="scope.row.imageUrl"
            :src="scope.row.imageUrl"
            :width="50"
            :height="50"
          >
          </image-preview>
        </template>
      </el-table-column>
      <el-table-column label="货位" align="center" prop="location" />
      <el-table-column label="SKU" align="center" prop="sku" />
      <el-table-column label="货品名称" align="center" prop="productName" :show-overflow-tooltip="true"/>
      <el-table-column label="规格" align="center" prop="specifications" />
      <el-table-column label="采购单价" align="center" prop="purchaseUnitPrice" />
      <el-table-column label="采购数量" align="center" prop="purchaseQuantity" />
      <el-table-column label="货款" align="center" prop="goodsPayment" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="到货日期" align="center" prop="arrivalDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.arrivalDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="采购备注" align="center" prop="purchaseNotes" :show-overflow-tooltip="true"/>
      <el-table-column label="账期天数" align="center" prop="paymentPeriodDays" />
      <el-table-column label="到期付款时间" align="center" prop="duePaymentDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.duePaymentDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="采购部门" align="center" prop="purchaseDepartment" />
      <el-table-column label="是否已付款" align="center" prop="isPaid">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.logic_yes_no" :value="scope.row.isPaid"/>
        </template>
      </el-table-column>
      <el-table-column label="是否已逾期" align="center" prop="isOverdue">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.logic_yes_no" :value="scope.row.isOverdue"/>
        </template>
      </el-table-column>
      <el-table-column label="距到期天数" align="center" prop="daysUntilDue">
        <template slot-scope="scope" v-if="scope.row.daysUntilDue">
          <el-tag type="success"
                  v-if="scope.row.daysUntilDue > 10">
            {{ scope.row.daysUntilDue }}
          </el-tag>
          <el-tag type="warning"
                  v-if="scope.row.daysUntilDue > 5 && scope.row.daysUntilDue <= 10">
            {{ scope.row.daysUntilDue }}
          </el-tag>
          <el-tag type="danger"
                  v-if="scope.row.daysUntilDue <= 5">
            {{ scope.row.daysUntilDue }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createdAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updatedAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updatedAt) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="120" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['purchase:paymentPeriod:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['purchase:paymentPeriod:remove']"
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

    <!-- 添加或修改采购账期对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="采购单号" prop="purchaseOrderNumber">
          <el-input v-model="form.purchaseOrderNumber" placeholder="请输入采购单号" />
        </el-form-item>
        <el-form-item label="采购计划编号" prop="purchasePlanNumber">
          <el-input v-model="form.purchasePlanNumber" placeholder="请输入采购计划编号" />
        </el-form-item>
        <el-form-item label="1688账号" prop="account1688">
          <el-input v-model="form.account1688" placeholder="请输入1688账号" />
        </el-form-item>
        <el-form-item label="采购仓库" prop="purchaseWarehouse">
          <el-input v-model="form.purchaseWarehouse" placeholder="请输入采购仓库" />
        </el-form-item>
        <el-form-item label="采购员" prop="purchaser">
          <el-input v-model="form.purchaser" placeholder="请输入采购员" />
        </el-form-item>
        <el-form-item label="采购日期" prop="purchaseDate">
          <el-date-picker clearable
                          v-model="form.purchaseDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择采购日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="图片" prop="imageUrl">
          <image-upload v-model="form.imageUrl"/>
        </el-form-item>
        <el-form-item label="货位" prop="location">
          <el-input v-model="form.location" placeholder="请输入货位" />
        </el-form-item>
        <el-form-item label="SKU" prop="sku">
          <el-input v-model="form.sku" placeholder="请输入SKU" />
        </el-form-item>
        <el-form-item label="货品名称" prop="productName">
          <el-input v-model="form.productName" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="规格" prop="specifications">
          <el-input v-model="form.specifications" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="采购单价" prop="purchaseUnitPrice">
          <el-input v-model="form.purchaseUnitPrice" placeholder="请输入采购单价" />
        </el-form-item>
        <el-form-item label="采购数量" prop="purchaseQuantity">
          <el-input v-model="form.purchaseQuantity" placeholder="请输入采购数量" />
        </el-form-item>
        <el-form-item label="货款" prop="goodsPayment">
          <el-input v-model="form.goodsPayment" placeholder="请输入货款" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="到货日期" prop="arrivalDate">
          <el-date-picker clearable
                          v-model="form.arrivalDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择到货日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="采购备注" prop="purchaseNotes">
          <el-input v-model="form.purchaseNotes" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="账期天数" prop="paymentPeriodDays">
          <el-input v-model="form.paymentPeriodDays" placeholder="请输入账期天数" />
        </el-form-item>
        <el-form-item label="到期付款时间" prop="duePaymentDate">
          <el-date-picker clearable
                          v-model="form.duePaymentDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择到期付款时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="采购部门" prop="purchaseDepartment">
          <el-input v-model="form.purchaseDepartment" placeholder="请输入采购部门" />
        </el-form-item>
        <el-form-item label="是否已付款" prop="isPaid">
          <el-select v-model="form.isPaid" placeholder="请选择是否已付款">
            <el-option
              v-for="dict in dict.type.logic_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 数据导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers" :action="upload.url" :disabled="upload.isUploading" :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            若单元格内为图片，请确保图片整体在单元格
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size: 12px; vertical-align: baseline" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPaymentPeriod, getPaymentPeriod, delPaymentPeriod, addPaymentPeriod, updatePaymentPeriod } from "@/api/purchase/paymentPeriod"
import {getToken} from "@/utils/auth";

export default {
  name: "PaymentPeriod",
  dicts: ['logic_yes_no'],
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
      // 采购账期表格数据
      paymentPeriodList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 采购时间时间范围
      daterangePurchaseDate: [],
      //到期付款时间时间范围
      daterangeDuePaymentDate: [],
      // 创建时间时间范围
      daterangeCreatedAt: [],
      // 更新时间时间范围
      daterangeUpdatedAt: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        purchaseOrderNumber: null,
        purchasePlanNumber: null,
        account1688: null,
        purchaseWarehouse: null,
        purchaser: null,
        purchaseDate: null,
        imageUrl: null,
        location: null,
        sku: null,
        productName: null,
        specifications: null,
        purchaseUnitPrice: null,
        purchaseQuantity: null,
        goodsPayment: null,
        supplierName: null,
        arrivalDate: null,
        purchaseNotes: null,
        paymentPeriodDays: null,
        duePaymentDate: null,
        purchaseDepartment: null,
        isPaid: null,
        isOverDue: null,
        daysUntilDue: null,
        createdAt: null,
        updatedAt: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        sku: [
          { required: true, message: "SKU不能为空", trigger: "blur" }
        ],
      },
      // 数据导入参数
      upload: {
        // 是否显示弹出层（数据导入）
        open: false,
        // 弹出层标题（数据导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/purchase/paymentPeriod/importData"
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询采购账期列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      this.queryParams.params = {}
      if (null != this.daterangePurchaseDate && '' != this.daterangePurchaseDate) {
        this.queryParams.params["beginPurchaseDate"] = this.daterangePurchaseDate[0]
        this.queryParams.params["endPurchaseDate"] = this.daterangePurchaseDate[1]
      }
      if (null != this.daterangeDuePaymentDate && '' != this.daterangeDuePaymentDate) {
        this.queryParams.params["beginDuePaymentDate"] = this.daterangeDuePaymentDate[0]
        this.queryParams.params["endDuePaymentDate"] = this.daterangeDuePaymentDate[1]
      }
      if (null != this.daterangeCreatedAt && '' != this.daterangeCreatedAt) {
        this.queryParams.params["beginCreatedAt"] = this.daterangeCreatedAt[0]
        this.queryParams.params["endCreatedAt"] = this.daterangeCreatedAt[1]
      }
      if (null != this.daterangeUpdatedAt && '' != this.daterangeUpdatedAt) {
        this.queryParams.params["beginUpdatedAt"] = this.daterangeUpdatedAt[0]
        this.queryParams.params["endUpdatedAt"] = this.daterangeUpdatedAt[1]
      }
      listPaymentPeriod(this.queryParams).then(response => {
        this.paymentPeriodList = response.rows
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
        purchaseOrderNumber: null,
        purchasePlanNumber: null,
        account1688: null,
        purchaseWarehouse: null,
        purchaser: null,
        purchaseDate: null,
        imageUrl: null,
        location: null,
        sku: null,
        productName: null,
        specifications: null,
        purchaseUnitPrice: null,
        purchaseQuantity: null,
        goodsPayment: null,
        supplierName: null,
        arrivalDate: null,
        purchaseNotes: null,
        paymentPeriodDays: null,
        duePaymentDate: null,
        purchaseDepartment: null,
        isPaid: null,
        createdAt: null,
        updatedAt: null,
        updateBy: null
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
      this.daterangePurchaseDate = []
      this.daterangeDuePaymentDate = []
      this.daterangeCreatedAt = []
      this.daterangeUpdatedAt = []
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
      this.title = "添加采购账期"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getPaymentPeriod(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改采购账期"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePaymentPeriod(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addPaymentPeriod(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除采购账期编号为"' + ids + '"的数据项？').then(function() {
        return delPaymentPeriod(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('purchase/paymentPeriod/export', {
        ...this.queryParams
      }, `paymentPeriod_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "数据导入"
      this.upload.open = true
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('purchase/paymentPeriod/importTemplate', {
      }, `purchase_payment_period_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
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
      this.$refs.upload.submit()
    }
  }
}
</script>
