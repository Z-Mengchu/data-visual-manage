<template>
  <div class="app-container">
    <!-- 权限提示信息 -->
    <el-alert
      v-if="showPermissionTip"
      :title="permissionTip"
      type="info"
      show-icon
      :closable="false"
      style="margin-bottom: 20px;"
    />
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="一级品类" prop="firstLevelCategory">
        <el-input
          v-model="queryParams.firstLevelCategory"
          placeholder="请输入一级品类"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="品类" prop="category">
        <el-input
          v-model="queryParams.category"
          placeholder="请输入品类"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="通途SKU" prop="tongtuSku">
        <el-input
          v-model="queryParams.tongtuSku"
          placeholder="请输入通途SKU"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="运营组名" prop="operationGroupName">
        <el-input
          v-model="queryParams.operationGroupName"
          placeholder="请输入运营组名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="运营" prop="operator">
        <el-input
          v-model="queryParams.operator"
          placeholder="请输入运营"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开发组名" prop="developmentGroupName">
        <el-input
          v-model="queryParams.developmentGroupName"
          placeholder="请输入开发组名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品开发员" prop="productDeveloper">
        <el-input
          v-model="queryParams.productDeveloper"
          placeholder="请输入产品开发员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="渠道" prop="channel">
        <el-input
          v-model="queryParams.channel"
          placeholder="请输入渠道"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="渠道账号" prop="channelAccount">
        <el-input
          v-model="queryParams.channelAccount"
          placeholder="请输入渠道账号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="运营部门" prop="operationDepartment">
        <el-input
          v-model="queryParams.operationDepartment"
          placeholder="请输入运营部门"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="店铺代码" prop="storeCode">
        <el-input
          v-model="queryParams.storeCode"
          placeholder="请输入店铺代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地区" prop="region">
        <el-input
          v-model="queryParams.region"
          placeholder="请输入地区"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="国家" prop="country">
        <el-input
          v-model="queryParams.country"
          placeholder="请输入国家"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="国家/地区" prop="countryRegion">
        <el-input
          v-model="queryParams.countryRegion"
          placeholder="请输入国家/地区"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="品牌" prop="brand">
        <el-input
          v-model="queryParams.brand"
          placeholder="请输入品牌"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="销售模式" prop="salesMode">
        <el-input
          v-model="queryParams.salesMode"
          placeholder="请输入销售模式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单号" prop="orderNumber">
        <el-input
          v-model="queryParams.orderNumber"
          placeholder="请输入订单号"
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
          v-hasPermi="['sale:data:add']"
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
          v-hasPermi="['sale:data:edit']"
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
          v-hasPermi="['sale:data:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['sale:data:export']"
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

    <el-table v-loading="loading" :data="salesDataList" height="850" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" fixed="left"/>
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="一级品类" align="center" prop="firstLevelCategory" />
      <el-table-column label="品类" align="center" prop="category" />
      <el-table-column label="通途SKU" align="center" prop="tongtuSku" />
      <el-table-column label="产品名称" align="center" prop="productName" :show-overflow-tooltip="true"/>
      <el-table-column label="运营组名" align="center" prop="operationGroupName" />
      <el-table-column label="运营" align="center" prop="operator" />
      <el-table-column label="开发组名" align="center" prop="developmentGroupName" />
      <el-table-column label="产品开发员" align="center" prop="productDeveloper" />
      <el-table-column label="渠道" align="center" prop="channel" />
      <el-table-column label="渠道账号" align="center" prop="channelAccount" />
      <el-table-column label="运营部门" align="center" prop="operationDepartment" />
      <el-table-column label="店铺代码" align="center" prop="storeCode" />
      <el-table-column label="地区" align="center" prop="region" />
      <el-table-column label="国家" align="center" prop="country" />
      <el-table-column label="国家/地区" align="center" prop="countryRegion" />
      <el-table-column label="品牌" align="center" prop="brand" />
      <el-table-column label="销售模式" align="center" prop="salesMode" />
      <el-table-column label="订单号" align="center" prop="orderNumber" />
      <el-table-column label="付款日期/结算时间" align="center" prop="paymentSettlementDate" />
      <el-table-column label="发货仓库" align="center" prop="shippingWarehouse" />
      <el-table-column label="发货数量" align="center" prop="shippingQuantity" />
      <el-table-column label="收入(人民币)" align="center" prop="revenueRmb" />
      <el-table-column label="商品成本" align="center" prop="productCost" />
      <el-table-column label="头程运费" align="center" prop="firstMileShipping" />
      <el-table-column label="包装费用" align="center" prop="packagingCost" />
      <el-table-column label="物流商运费" align="center" prop="logisticsShipping" />
      <el-table-column label="渠道成交费(人民币)" align="center" prop="channelTransactionFeeRmb" />
      <el-table-column label="税费" align="center" prop="taxes" />
      <el-table-column label="退款" align="center" prop="refund" />
      <el-table-column label="补发金额" align="center" prop="reshipmentAmount" />
      <el-table-column label="送样成本" align="center" prop="sampleCost" />
      <el-table-column label="滞销补贴" align="center" prop="unsoldSubsidy" />
      <el-table-column label="订单毛利" align="center" prop="orderGrossProfit" />
      <el-table-column label="订单毛利率" align="center" prop="orderGrossProfitRate" >
        <template slot-scope="scope">
          <span>{{ scope.row.orderGrossProfitRate !== null ? (scope.row.orderGrossProfitRate * 100).toFixed(2) + '%' : '' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="广告/促销" align="center" prop="advertisingPromotion" />
      <el-table-column label="仓储费" align="center" prop="storageFee" />
      <el-table-column label="移除费" align="center" prop="removalFee" />
      <el-table-column label="退货处理费" align="center" prop="returnProcessingFee" />
      <el-table-column label="其他费用" align="center" prop="otherFees" />
      <el-table-column label="滞销品预算" align="center" prop="unsoldBudget" />
      <el-table-column label="手续费" align="center" prop="handlingFee" />
      <el-table-column label="订单净利" align="center" prop="orderNetProfit" />
      <el-table-column label="订单净利率" align="center" prop="orderNetProfitRate" >
        <template slot-scope="scope">
          <span>{{ scope.row.orderNetProfitRate !== null ? (scope.row.orderNetProfitRate * 100).toFixed(2) + '%' : '' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="ROI" align="center" prop="roi" />
      <el-table-column label="产品现金流为正所需的天数" align="center" prop="daysToPositiveCashFlow" />
      <el-table-column label="验算公式" align="center" prop="verificationFormula" />
      <el-table-column label="毛利是否大于0" align="center" prop="isGrossProfitPositive">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.logic_yes_no" :value="scope.row.isGrossProfitPositive"/>
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
            v-hasPermi="['sale:data:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['sale:data:remove']"
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

    <!-- 添加或修改销售数据管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="一级品类" prop="firstLevelCategory">
          <el-input v-model="form.firstLevelCategory" placeholder="请输入一级品类" />
        </el-form-item>
        <el-form-item label="品类" prop="category">
          <el-input v-model="form.category" placeholder="请输入品类" />
        </el-form-item>
        <el-form-item label="通途SKU" prop="tongtuSku">
          <el-input v-model="form.tongtuSku" placeholder="请输入通途SKU" />
        </el-form-item>
        <el-form-item label="产品名称" prop="productName">
          <el-input v-model="form.productName" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="运营组名" prop="operationGroupName">
          <el-input v-model="form.operationGroupName" placeholder="请输入运营组名" />
        </el-form-item>
        <el-form-item label="运营" prop="operator">
          <el-input v-model="form.operator" placeholder="请输入运营" />
        </el-form-item>
        <el-form-item label="开发组名" prop="developmentGroupName">
          <el-input v-model="form.developmentGroupName" placeholder="请输入开发组名" />
        </el-form-item>
        <el-form-item label="产品开发员" prop="productDeveloper">
          <el-input v-model="form.productDeveloper" placeholder="请输入产品开发员" />
        </el-form-item>
        <el-form-item label="渠道" prop="channel">
          <el-input v-model="form.channel" placeholder="请输入渠道" />
        </el-form-item>
        <el-form-item label="渠道账号" prop="channelAccount">
          <el-input v-model="form.channelAccount" placeholder="请输入渠道账号" />
        </el-form-item>
        <el-form-item label="运营部门" prop="operationDepartment">
          <el-input v-model="form.operationDepartment" placeholder="请输入运营部门" />
        </el-form-item>
        <el-form-item label="店铺代码" prop="storeCode">
          <el-input v-model="form.storeCode" placeholder="请输入店铺代码" />
        </el-form-item>
        <el-form-item label="地区" prop="region">
          <el-input v-model="form.region" placeholder="请输入地区" />
        </el-form-item>
        <el-form-item label="国家" prop="country">
          <el-input v-model="form.country" placeholder="请输入国家" />
        </el-form-item>
        <el-form-item label="国家/地区" prop="countryRegion">
          <el-input v-model="form.countryRegion" placeholder="请输入国家/地区" />
        </el-form-item>
        <el-form-item label="品牌" prop="brand">
          <el-input v-model="form.brand" placeholder="请输入品牌" />
        </el-form-item>
        <el-form-item label="销售模式" prop="salesMode">
          <el-input v-model="form.salesMode" placeholder="请输入销售模式" />
        </el-form-item>
        <el-form-item label="订单号" prop="orderNumber">
          <el-input v-model="form.orderNumber" placeholder="请输入订单号" />
        </el-form-item>
        <el-form-item label="付款日期/结算时间" prop="paymentSettlementDate">
          <el-input v-model="form.paymentSettlementDate" placeholder="请输入付款日期/结算时间" />
        </el-form-item>
        <el-form-item label="发货仓库" prop="shippingWarehouse">
          <el-input v-model="form.shippingWarehouse" placeholder="请输入发货仓库" />
        </el-form-item>
        <el-form-item label="发货数量" prop="shippingQuantity">
          <el-input v-model="form.shippingQuantity" placeholder="请输入发货数量" />
        </el-form-item>
        <el-form-item label="收入(人民币)" prop="revenueRmb">
          <el-input v-model="form.revenueRmb" placeholder="请输入收入(人民币)" />
        </el-form-item>
        <el-form-item label="商品成本" prop="productCost">
          <el-input v-model="form.productCost" placeholder="请输入商品成本" />
        </el-form-item>
        <el-form-item label="头程运费" prop="firstMileShipping">
          <el-input v-model="form.firstMileShipping" placeholder="请输入头程运费" />
        </el-form-item>
        <el-form-item label="包装费用" prop="packagingCost">
          <el-input v-model="form.packagingCost" placeholder="请输入包装费用" />
        </el-form-item>
        <el-form-item label="物流商运费" prop="logisticsShipping">
          <el-input v-model="form.logisticsShipping" placeholder="请输入物流商运费" />
        </el-form-item>
        <el-form-item label="渠道成交费(人民币)" prop="channelTransactionFeeRmb">
          <el-input v-model="form.channelTransactionFeeRmb" placeholder="请输入渠道成交费(人民币)" />
        </el-form-item>
        <el-form-item label="税费" prop="taxes">
          <el-input v-model="form.taxes" placeholder="请输入税费" />
        </el-form-item>
        <el-form-item label="退款" prop="refund">
          <el-input v-model="form.refund" placeholder="请输入退款" />
        </el-form-item>
        <el-form-item label="补发金额" prop="reshipmentAmount">
          <el-input v-model="form.reshipmentAmount" placeholder="请输入补发金额" />
        </el-form-item>
        <el-form-item label="送样成本" prop="sampleCost">
          <el-input v-model="form.sampleCost" placeholder="请输入送样成本" />
        </el-form-item>
        <el-form-item label="滞销补贴" prop="unsoldSubsidy">
          <el-input v-model="form.unsoldSubsidy" placeholder="请输入滞销补贴" />
        </el-form-item>
        <el-form-item label="订单毛利" prop="orderGrossProfit">
          <el-input v-model="form.orderGrossProfit" placeholder="请输入订单毛利" />
        </el-form-item>
        <el-form-item label="订单毛利率" prop="orderGrossProfitRate">
          <el-input v-model="form.orderGrossProfitRate" placeholder="请输入订单毛利率" />
        </el-form-item>
        <el-form-item label="广告/促销" prop="advertisingPromotion">
          <el-input v-model="form.advertisingPromotion" placeholder="请输入广告/促销" />
        </el-form-item>
        <el-form-item label="仓储费" prop="storageFee">
          <el-input v-model="form.storageFee" placeholder="请输入仓储费" />
        </el-form-item>
        <el-form-item label="移除费" prop="removalFee">
          <el-input v-model="form.removalFee" placeholder="请输入移除费" />
        </el-form-item>
        <el-form-item label="退货处理费" prop="returnProcessingFee">
          <el-input v-model="form.returnProcessingFee" placeholder="请输入退货处理费" />
        </el-form-item>
        <el-form-item label="其他费用" prop="otherFees">
          <el-input v-model="form.otherFees" placeholder="请输入其他费用" />
        </el-form-item>
        <el-form-item label="滞销品预算" prop="unsoldBudget">
          <el-input v-model="form.unsoldBudget" placeholder="请输入滞销品预算" />
        </el-form-item>
        <el-form-item label="手续费" prop="handlingFee">
          <el-input v-model="form.handlingFee" placeholder="请输入手续费" />
        </el-form-item>
        <el-form-item label="订单净利" prop="orderNetProfit">
          <el-input v-model="form.orderNetProfit" placeholder="请输入订单净利" />
        </el-form-item>
        <el-form-item label="订单净利率" prop="orderNetProfitRate">
          <el-input v-model="form.orderNetProfitRate" placeholder="请输入订单净利" />
        </el-form-item>
        <el-form-item label="ROI" prop="roi">
          <el-input v-model="form.roi" placeholder="请输入ROI" />
        </el-form-item>
        <el-form-item label="产品现金流为正所需的天数" prop="daysToPositiveCashFlow">
          <el-input v-model="form.daysToPositiveCashFlow" placeholder="请输入产品现金流为正所需的天数" />
        </el-form-item>
        <el-form-item label="验算公式" prop="verificationFormula">
          <el-input v-model="form.verificationFormula" placeholder="请输入验算公式" />
        </el-form-item>
        <el-form-item label="毛利是否大于0" prop="isGrossProfitPositive">
          <el-select v-model="form.isGrossProfitPositive" placeholder="请选择毛利是否大于0">
            <el-option
              v-for="dict in dict.type.logic_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
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
import { listSalesData, getSalesData, delSalesData, addSalesData, updateSalesData } from "@/api/saleManage/salesData"
import { getToken } from "@/utils/auth"

export default {
  name: "SalesData",
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
      // 销售数据管理表格数据
      salesDataList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        firstLevelCategory: null,
        category: null,
        tongtuSku: null,
        productName: null,
        operationGroupName: null,
        operator: null,
        developmentGroupName: null,
        productDeveloper: null,
        channel: null,
        channelAccount: null,
        operationDepartment: null,
        storeCode: null,
        region: null,
        country: null,
        countryRegion: null,
        brand: null,
        salesMode: null,
        orderNumber: null,
        paymentSettlementDate: null,
        shippingWarehouse: null,
        shippingQuantity: null,
        revenueRmb: null,
        productCost: null,
        firstMileShipping: null,
        packagingCost: null,
        logisticsShipping: null,
        channelTransactionFeeRmb: null,
        taxes: null,
        refund: null,
        reshipmentAmount: null,
        sampleCost: null,
        unsoldSubsidy: null,
        orderGrossProfit: null,
        orderGrossProfitRate: null,
        advertisingPromotion: null,
        storageFee: null,
        removalFee: null,
        returnProcessingFee: null,
        otherFees: null,
        unsoldBudget: null,
        handlingFee: null,
        orderNetProfit: null,
        orderNetProfitRate: null,
        roi: null,
        daysToPositiveCashFlow: null,
        verificationFormula: null,
        isGrossProfitPositive: null,
        createdAt: null,
        updatedAt: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
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
        url: process.env.VUE_APP_BASE_API + "/sale/data/importData"
      },
      // 权限提示相关数据
      showPermissionTip: false,
      permissionTip: '',
      importLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询销售数据管理列表 */
    getList() {
      this.loading = true
      listSalesData(this.queryParams).then(response => {
        this.salesDataList = response.rows
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
        firstLevelCategory: null,
        category: null,
        tongtuSku: null,
        productName: null,
        operationGroupName: null,
        operator: null,
        developmentGroupName: null,
        productDeveloper: null,
        channel: null,
        channelAccount: null,
        operationDepartment: null,
        storeCode: null,
        region: null,
        country: null,
        countryRegion: null,
        brand: null,
        salesMode: null,
        orderNumber: null,
        paymentSettlementDate: null,
        shippingWarehouse: null,
        shippingQuantity: null,
        revenueRmb: null,
        productCost: null,
        firstMileShipping: null,
        packagingCost: null,
        logisticsShipping: null,
        channelTransactionFeeRmb: null,
        taxes: null,
        refund: null,
        reshipmentAmount: null,
        sampleCost: null,
        unsoldSubsidy: null,
        orderGrossProfit: null,
        orderGrossProfitRate: null,
        advertisingPromotion: null,
        storageFee: null,
        removalFee: null,
        returnProcessingFee: null,
        otherFees: null,
        unsoldBudget: null,
        handlingFee: null,
        orderNetProfit: null,
        orderNetProfitRate: null,
        roi: null,
        daysToPositiveCashFlow: null,
        verificationFormula: null,
        isGrossProfitPositive: null,
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
      this.title = "添加销售数据"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getSalesData(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改销售数据"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSalesData(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addSalesData(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除销售数据编号为"' + ids + '"的数据项？').then(function() {
        return delSalesData(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('sale/data/export', {
        ...this.queryParams
      }, `salesData_${new Date().getTime()}.xlsx`)
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
      this.download('sale/data/importTemplate', {}, `全渠道销售数据导入模板_${new Date().getTime()}.xlsx`);
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

      // 提交上传
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
