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
          v-hasPermi="['purchase:paymentPeriod:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="multiple"
          @click="handleBatchUpdate"
          v-hasPermi="['purchase:paymentPeriod:edit']"
        >批量修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="handleBatchUpdateIsPaid"
          v-hasPermi="['purchase:paymentPeriod:edit']"
        >批量修改付款状态</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 付款统计 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="4">
        <el-card class="box-card" shadow="hover">
          <div class="card-content">
            <div class="card-info">
              <div class="card-title">已付款</div>
              <div class="card-count">{{ paymentStats.paidCount || 0 }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="box-card" shadow="hover">
          <div class="card-content">
            <div class="card-info">
              <div class="card-title">未付款</div>
              <div class="card-count">{{ paymentStats.unpaidCount || 0 }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="box-card" shadow="hover">
          <div class="card-content">
            <div class="card-info">
              <div class="card-title">筛选条目未付款金额</div>
              <div class="card-count">{{ selectedUnpaidAmount || 0 }}元</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="box-card" shadow="hover">
          <div class="card-content">
            <div class="card-info">
              <div class="card-title">未知</div>
              <div class="card-count">{{ paymentStats.unknownCount || 0 }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="box-card" shadow="hover">
          <div class="card-content">
            <div class="card-info">
              <div class="card-title">总计</div>
              <div class="card-count">{{ totalCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="paymentPeriodList" height="850" @selection-change="handleSelectionChange">
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
      <el-table-column label="运费" align="center" prop="shippingCost" />
      <el-table-column label="采购单总金额" align="center" prop="purchaseTotalAmount" />
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
<!--          <dict-tag :options="dict.type.logic_yes_no" :value="scope.row.isPaid"/>-->
          <el-button
            v-if="scope.row.isPaid !== null"
            :type="scope.row.isPaid === '1' ? 'primary' : 'danger'"
            plain
            size="mini"
            @click="handleMarkAsPaid(scope.row)"
            v-hasPermi="['purchase:paymentPeriod:edit']"
          >{{ scope.row.isPaid === '1' ? '是' : '否' }}</el-button>
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
        <el-form-item label="运费" prop="shippingCost">
          <el-input v-model="form.shippingCost" placeholder="请输入运费" />
        </el-form-item>
        <el-form-item label="采购单总金额" prop="purchaseTotalAmount">
          <el-input v-model="form.purchaseTotalAmount" placeholder="请输入采购单总金额" />
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
                      <p>确保日期、数字等格式与模板一致</p>
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

    <!-- 批量修改对话框 -->
    <el-dialog :title="batchUpdate.title" :visible.sync="batchUpdate.open" width="600px" append-to-body @close="handleBatchUpdateClose">
      <div class="batch-update-container">
        <!-- 步骤1：导出模板 -->
        <div class="batch-step" v-if="!batchUpdate.hasExported">
          <div class="step-header">
            <el-steps :active="1" simple>
              <el-step title="选择数据" icon="el-icon-check"></el-step>
              <el-step title="导出模板" icon="el-icon-download"></el-step>
              <el-step title="修改数据" icon="el-icon-edit"></el-step>
              <el-step title="上传文件" icon="el-icon-upload2"></el-step>
            </el-steps>
          </div>
          <div class="step-content">
            <div class="selection-info">
              <p>当前选中 <strong>{{ batchUpdateIds.length }}</strong> 条数据</p>
              <p class="tip-text">请确认选择的数据是否正确，然后导出批量修改模板</p>
            </div>
            <div class="action-buttons">
              <el-button
                type="primary"
                icon="el-icon-download"
                @click="handleExportBatchTemplate"
                :loading="batchUpdate.isUploading">
                导出批量修改模板
              </el-button>
            </div>
          </div>
        </div>
        <!-- 步骤2：上传修改后的文件 -->
        <div class="batch-step" v-else>
          <div class="step-header">
            <el-steps :active="4" simple>
              <el-step title="选择数据" icon="el-icon-check"></el-step>
              <el-step title="导出模板" icon="el-icon-download"></el-step>
              <el-step title="修改数据" icon="el-icon-edit"></el-step>
              <el-step title="上传文件" icon="el-icon-upload2"></el-step>
            </el-steps>
          </div>
          <div class="step-content">
            <!-- 倒计时显示 -->
            <div class="countdown-display" v-if="batchUpdate.countdown > 0">
              <el-alert
                title="请及时上传修改后的文件"
                type="warning"
                :description="`剩余时间: ${formatCountdown()}`"
                show-icon
                :closable="false">
              </el-alert>
            </div>
            <div class="countdown-display" v-else>
              <el-alert
                title="导出模板已过期"
                type="error"
                description="请重新导出模板"
                show-icon
                :closable="false">
              </el-alert>
            </div>
            <el-upload
              ref="batchUpdateUpload"
              class="batch-upload"
              :limit="1"
              accept=".xlsx, .xls"
              :headers="batchUpdate.headers"
              :action="batchUpdate.url"
              :disabled="batchUpdate.isUploading || batchUpdate.countdown <= 0"
              :on-progress="handleBatchUpdateFileUploadProgress"
              :on-success="handleBatchUpdateFileSuccess"
              :auto-upload="false"
              drag>
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将修改后的文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip">
                <div>请上传已修改的Excel文件</div>
                <div>系统将根据原始ID自动更新对应数据</div>
                <div>请勿修改文件结构，仅修改需要变更的字段</div>
                <div v-if="batchUpdate.countdown > 0" class="upload-tip">
                  剩余时间: <span class="time-remaining">{{ formatCountdown() }}</span>
                </div>
              </div>
            </el-upload>
            <div class="current-selection">
              <el-button
                type="text"
                icon="el-icon-refresh"
                @click="handleReselectData"
                class="reselect-btn">
                重新选择数据
              </el-button>
            </div>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button
          v-if="batchUpdate.hasExported && batchUpdate.countdown > 0"
          type="primary"
          @click="submitBatchUpdateFileForm">
          确认上传
        </el-button>
        <el-button @click="handleBatchUpdateClose">关闭</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="batchUpdateIsPaid.title" :visible.sync="batchUpdateIsPaid.open" width="500px" append-to-body>
      <el-form ref="batchUpdateIsPaidForm" :model="batchUpdateIsPaid" label-width="120px">
        <el-form-item label="修改方式">
          <el-radio-group v-model="batchUpdateIsPaid.mode">
            <el-radio label="selected">修改勾选条目</el-radio>
            <el-radio label="manual">手动输入编号</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item
          label="条目编号"
          v-if="batchUpdateIsPaid.mode === 'manual'"
          prop="manualIds"
          :rules="[{ required: true, message: '请输入条目编号', trigger: 'blur' }]">
          <el-input
            v-model="batchUpdateIsPaid.manualIds"
            @input="handleManualIdsInput"
            type="textarea"
            :rows="3"
            placeholder="请输入条目编号，可用英文逗号、中文逗号或空格分隔">
          </el-input>
        </el-form-item>

        <el-form-item
          label="付款状态"
          prop="isPaid"
          :rules="[{ required: true, message: '请选择付款状态', trigger: 'change' }]">
          <el-select v-model="batchUpdateIsPaid.isPaid" placeholder="请选择付款状态">
            <el-option value="1" label="已付款"></el-option>
            <el-option value="0" label="未付款"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item v-if="batchUpdateIsPaid.mode === 'selected'" label="待修改条目">
          <div class="preview-ids">
            <p>共 {{ batchUpdateIsPaid.selectedIds.length }} 条记录：</p>
            <div class="ids-list">已选择编号：{{ batchUpdateIsPaid.selectedIds.join(', ') }}</div>
          </div>
        </el-form-item>

        <el-form-item v-if="batchUpdateIsPaid.mode === 'manual'" label="待修改条目">
          <div class="preview-ids">
            <p>共 {{ batchUpdateIsPaid.parseManualIds.length }} 条记录：</p>
            <div class="ids-list">已选择编号：{{ batchUpdateIsPaid.parseManualIds.join(', ') }}</div>
          </div>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="batchUpdateIsPaid.open = false">取 消</el-button>
        <el-button
          type="primary"
          @click="confirmBatchUpdateIsPaid">
          确 认
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addPaymentPeriod, countPaymentStatus, delPaymentPeriod, getPaymentPeriod, listPaymentPeriod, updatePaymentPeriod, batchUpdateIsPaid} from "@/api/purchase/paymentPeriod"
import { getToken } from "@/utils/auth";

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
        shippingCost: null,
        purchaseTotalAmount: null,
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
        url: process.env.VUE_APP_BASE_API + "/purchase/paymentPeriod/importData"
      },
      importLoading: false,
      // 付款统计
       paymentStats: {
        paidCount: 0,
        unpaidCount: 0,
        unknownCount: 0
      },
      selectedUnpaidAmount: 0, // 存储选中条目的未付款金额
      // 批量修改参数
      batchUpdate: {
        // 是否显示弹出层（批量修改）
        open: false,
        // 弹出层标题（批量修改）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否已导出模板
        hasExported: false,
        // 倒计时剩余时间（秒）
        countdown: 0,
        // 倒计时定时器
        countdownTimer: null,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/purchase/paymentPeriod/batchUpdate"
      },
      // 批量修改选中的ID列表
      batchUpdateIds: [],
      // 批量修改付款状态参数
      batchUpdateIsPaid: {
        open: false,
        title: "批量修改付款状态",
        mode: "selected", // selected 或 manual
        manualIds: "",
        isPaid: "1",
        selectedIds: [],
        parseManualIds: [],
        previewIds: []
      }
    }
  },
  created() {
    this.getList()
    this.getPaymentStats()
  },
  computed: {
    // 计算总数量
    totalCount() {
      return (this.paymentStats.paidCount || 0) +
        (this.paymentStats.unpaidCount || 0) +
        (this.paymentStats.unknownCount || 0);
    }
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
      this.getPaymentStats();
    },
    /** 获取付款统计 */
    getPaymentStats() {
      const query = { ...this.queryParams }
      delete query.pageNum
      delete query.pageSize

      countPaymentStatus(query).then(response => {
        this.paymentStats = response.data
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
        shippingCost: null,
        purchaseTotalAmount: null,
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

      // 计算选中条目的未付款金额
      this.calculateSelectedUnpaidAmount(this.ids);
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
      this.download('purchase/paymentPeriod/importTemplate', {}, `采购账期导入模板_${new Date().getTime()}.xlsx`);
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
    },
    // 修改已付款状态
    handleMarkAsPaid(row) {
      const content = `是否确认将编号为"${row.id}"的记录标记为${row.isPaid === '1' ? '未付款' : '已付款'}？`;
      this.$modal.confirm(content).then(() => {
        // 创建更新数据对象
        const updateData = {
          id: row.id,
          isPaid: row.isPaid === '1' ? '0' : '1'
        };
        return updatePaymentPeriod(updateData);
      }).then(() => {
        this.$modal.msgSuccess("标记成功");
        this.getList();  // 刷新列表
      }).catch(() => {});
    },
    /**
     * 计算选中条目的未付款金额
     */
    calculateSelectedUnpaidAmount(selectedIds) {
      if (!selectedIds || selectedIds.length === 0) {
        this.selectedUnpaidAmount = 0;
        return;
      }
      // 过滤出选中的条目并计算未付款金额
      const selectedItems = this.paymentPeriodList.filter(item =>
        selectedIds.includes(item.id) && item.isPaid !== '1'
      );
      // 计算未付款金额总和
      this.selectedUnpaidAmount = selectedItems.reduce((sum, item) => {
        return sum + (parseFloat(item.purchaseTotalAmount) || 0);
      }, 0);
    },
    /** 批量修改按钮操作 */
    handleBatchUpdate() {
      if (this.ids.length === 0) {
        this.$modal.msgError("请先选择要修改的条目");
        return;
      }
      // 保存选中的ID列表
      this.batchUpdateIds = [...this.ids];
      // 打开批量修改对话框
      this.batchUpdate.title = "批量修改数据"
      this.batchUpdate.open = true
      this.batchUpdate.isUploading = false
      // 如果之前已经导出过且倒计时未结束，保持状态
      // 否则重置导出状态
      if (!this.batchUpdate.hasExported || this.batchUpdate.countdown <= 0) {
        this.batchUpdate.hasExported = false;
        this.batchUpdate.countdown = 0;
      }
    },
    /** 重新选择数据 */
    handleReselectData() {
      // 重置批量修改状态
      this.resetBatchUpdateState();

      // 关闭对话框
      this.batchUpdate.open = false;

      // 清空当前表格的选中状态
      this.ids = [];
      this.single = true;
      this.multiple = true;

      // 提示用户重新选择
      this.$modal.msgWarning("请重新在表格中选择要修改的数据，然后再次点击批量修改按钮");
    },
    /** 重置批量修改状态 */
    resetBatchUpdateState() {
      // 清除倒计时
      if (this.batchUpdate.countdownTimer) {
        clearInterval(this.batchUpdate.countdownTimer);
        this.batchUpdate.countdownTimer = null;
      }

      // 重置状态
      this.batchUpdate.hasExported = false;
      this.batchUpdate.countdown = 0;
      this.batchUpdateIds = [];
      this.batchUpdate.isUploading = false;

      // 清空上传的文件
      if (this.$refs.batchUpdateUpload) {
        this.$refs.batchUpdateUpload.clearFiles();
      }
    },
    /** 导出批量修改模板 */
    handleExportBatchTemplate() {
      if (this.batchUpdateIds.length === 0) {
        this.$modal.msgError("没有选中的数据");
        return;
      }

      // 将数组转换为逗号分隔的字符串
      const idsStr = this.batchUpdateIds.join(',');

      this.download('/purchase/paymentPeriod/exportSelected', {
        ids: idsStr
      }, `采购账期批量修改模板_${new Date().getTime()}.xlsx`)

      // 设置已导出状态
      this.batchUpdate.hasExported = true;

      // 开始60分钟倒计时
      this.startCountdown();

      this.$modal.msgSuccess("模板导出成功！请修改Excel文件后上传");
    },
    /** 开始倒计时 */
    startCountdown() {
      // 设置60分钟倒计时（3600秒）
      this.batchUpdate.countdown = 3600;

      // 清除之前的定时器
      if (this.batchUpdate.countdownTimer) {
        clearInterval(this.batchUpdate.countdownTimer);
      }

      // 启动新定时器
      this.batchUpdate.countdownTimer = setInterval(() => {
        this.batchUpdate.countdown--;

        if (this.batchUpdate.countdown <= 0) {
          clearInterval(this.batchUpdate.countdownTimer);
          this.batchUpdate.countdownTimer = null;
          this.$modal.msgWarning("导出模板已过期，请重新导出");
          this.batchUpdate.hasExported = false;
        }
      }, 1000);
    },
    /** 格式化倒计时显示 */
    formatCountdown() {
      const minutes = Math.floor(this.batchUpdate.countdown / 60);
      const seconds = this.batchUpdate.countdown % 60;
      return `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
    },
    /** 批量修改文件上传中处理 */
    handleBatchUpdateFileUploadProgress(event, file, fileList) {
      this.batchUpdate.isUploading = true;
    },
    /** 批量修改文件上传成功处理 */
    handleBatchUpdateFileSuccess(response, file, fileList) {
      this.batchUpdate.isUploading = false;
      this.$refs.batchUpdateUpload.clearFiles();

      // 关闭对话框
      this.batchUpdate.open = false;

      // 清除倒计时
      if (this.batchUpdate.countdownTimer) {
        clearInterval(this.batchUpdate.countdownTimer);
        this.batchUpdate.countdownTimer = null;
      }

      // 重置状态
      this.batchUpdate.hasExported = false;
      this.batchUpdate.countdown = 0;
      this.batchUpdateIds = [];

      // 显示结果
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "批量修改结果", { dangerouslyUseHTMLString: true });

      // 刷新列表
      this.getList();
    },
    /** 提交批量修改文件 */
    submitBatchUpdateFileForm() {
      const file = this.$refs.batchUpdateUpload.uploadFiles;
      if (!file || file.length === 0) {
        this.$modal.msgError("请选择要上传的文件");
        return;
      }

      if (!file[0].name.toLowerCase().endsWith('.xls') && !file[0].name.toLowerCase().endsWith('.xlsx')) {
        this.$modal.msgError("请选择后缀为 'xls' 或 'xlsx' 的文件");
        return;
      }

      // 检查是否已过期
      if (this.batchUpdate.countdown <= 0) {
        this.$modal.msgError("导出模板已过期，请重新导出");
        return;
      }

      this.batchUpdate.isUploading = true;
      this.$refs.batchUpdateUpload.submit();
    },
    /** 关闭批量修改对话框 */
    handleBatchUpdateClose() {
      // 只关闭对话框，不重置状态，保持倒计时继续运行
      this.batchUpdate.open = false;
    },
    /** 批量修改付款状态按钮操作 */
    handleBatchUpdateIsPaid() {
      // 保存当前勾选的ID
      this.batchUpdateIsPaid.selectedIds = [...this.ids];
      // 重置状态
      this.batchUpdateIsPaid.manualIds = "";
      this.batchUpdateIsPaid.previewIds = [];
      this.batchUpdateIsPaid.mode = "selected";
      this.batchUpdateIsPaid.parseManualIds = [];
      this.batchUpdateIsPaid.isPaid = "1";

      // 打开对话框
      this.batchUpdateIsPaid.title = "批量修改付款状态";
      this.batchUpdateIsPaid.open = true;
    },

    /** 处理手动输入编号 */
    handleManualIdsInput() {
      // 使用正则表达式分割：支持英文逗号、中文逗号、空格
      let ids = this.batchUpdateIsPaid.manualIds
        .split(/[,，\s]+/)
        .map(id => id.trim())
        .filter(id => id !== "");
      ids = [...new Set(ids)];
      this.batchUpdateIsPaid.parseManualIds = ids;
    },

    /** 确认批量修改付款状态 */
    confirmBatchUpdateIsPaid() {
      if (this.batchUpdateIsPaid.mode === "selected") {
        this.batchUpdateIsPaid.previewIds = [...this.batchUpdateIsPaid.selectedIds];
      }else if (this.batchUpdateIsPaid.mode === "manual") {
        this.batchUpdateIsPaid.previewIds = [...this.batchUpdateIsPaid.parseManualIds];
      }

      if (this.batchUpdateIsPaid.previewIds.length === 0) {
        this.$modal.msgError("请选择要修改的编号");
        return;
      }
      const ids = this.batchUpdateIsPaid.previewIds;
      const isPaid = this.batchUpdateIsPaid.isPaid;

      const context = `确定将编号为 ${ids} 共 ${ids.length}条记录 修改为"${isPaid === '1' ? '已付款' : '未付款'}"状态吗？`;
      this.$modal.confirm(context).then(() => {
        // 关闭对话框
        this.batchUpdateIsPaid.open = false;
        const data = {
          ids: ids,
          isPaid: isPaid === "" ? null : isPaid
        };
        // 调用批量更新接口
        return batchUpdateIsPaid(data);
      }).then(() => {
        this.$modal.msgSuccess("修改成功");
        this.getList(); //刷新列表
      }).catch(() => {});
    }
  },
  // 在组件销毁前清理定时器
  beforeDestroy() {
    if (this.batchUpdate.countdownTimer) {
      clearInterval(this.batchUpdate.countdownTimer);
      this.batchUpdate.countdownTimer = null;
    }
  },
}
</script>

<style scoped>
.card-content {
  display: flex;
  align-items: center;
  padding: 10px;
}
.card-info {
  flex: 1;
}
.card-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 5px;
}
.card-count {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}
.box-card {
  border-radius: 8px;
}
/* 批量修改相关样式 */
.batch-update-container {
  padding: 10px 0;
}
.batch-step {
  margin-bottom: 20px;
}
.step-header {
  margin-bottom: 20px;
}
.step-content {
  text-align: center;
}
.selection-info {
  margin-bottom: 20px;
}
.selection-info p {
  margin: 10px 0;
  font-size: 14px;
}
.action-buttons {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 20px;
}
.current-selection {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 15px;
}
.reselect-btn {
  margin-left: 10px;
}
.tip-text {
  color: #909399;
  font-size: 13px;
}
.countdown-display {
  margin-bottom: 20px;
}
.batch-upload {
  margin-top: 20px;
}
.upload-tip {
  margin-top: 10px;
  font-size: 12px;
  color: #e6a23c;
}
.time-remaining {
  font-weight: bold;
  color: #e6a23c;
}

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

.preview-ids {
  font-size: 13px;
  color: black;
}
.ids-list {
  overflow-y: auto;
  background-color: #f5f7fa;
  padding: 8px;
  border-radius: 4px;
  margin-top: 5px;
}

</style>
