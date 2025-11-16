<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="发货日期">
        <el-date-picker
          v-model="daterangeShipmentDate"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="是否补发货" prop="isReplenishment">
        <el-select v-model="queryParams.isReplenishment" placeholder="请选择是否补发货" clearable>
          <el-option
            v-for="dict in dict.type.logic_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="通途SKU" prop="tongtuSku">
        <el-input
          v-model="queryParams.tongtuSku"
          placeholder="请输入通途SKU"
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
      <el-form-item label="发货数量" prop="shipmentQuantity">
        <el-input
          v-model="queryParams.shipmentQuantity"
          placeholder="请输入发货数量"
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
      <el-form-item label="销售站点" prop="salesSite">
        <el-input
          v-model="queryParams.salesSite"
          placeholder="请输入销售站点"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发货仓库" prop="shipmentWarehouse">
        <el-input
          v-model="queryParams.shipmentWarehouse"
          placeholder="请输入发货仓库"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="包裹号" prop="packageNumber">
        <el-input
          v-model="queryParams.packageNumber"
          placeholder="请输入包裹号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="邮寄方式" prop="shippingMethod">
        <el-input
          v-model="queryParams.shippingMethod"
          placeholder="请输入邮寄方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物流商单号" prop="logisticsNumber">
        <el-input
          v-model="queryParams.logisticsNumber"
          placeholder="请输入物流商单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="买家姓名" prop="buyerName">
        <el-input
          v-model="queryParams.buyerName"
          placeholder="请输入买家姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="国家 / 地区" prop="countryRegion">
        <el-input
          v-model="queryParams.countryRegion"
          placeholder="请输入国家 / 地区"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="付款日期">
        <el-date-picker
          v-model="daterangePaymentDate"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="产品开发员" prop="productDeveloper">
        <el-input
          v-model="queryParams.productDeveloper"
          placeholder="请输入产品开发员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收款币种" prop="receiptCurrency">
        <el-input
          v-model="queryParams.receiptCurrency"
          placeholder="请输入收款币种"
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
          v-hasPermi="['temu:details:add']"
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
          v-hasPermi="['temu:details:edit']"
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
          v-hasPermi="['temu:details:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['temu:details:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['temu:details:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="temuList" :row-key="row => row.id" height="850" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" fixed="left"/>
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="发货日期" align="center" prop="shipmentDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.shipmentDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否补发货" align="center" prop="isReplenishment">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.logic_yes_no" :value="scope.row.isReplenishment"/>
        </template>
      </el-table-column>
      <el-table-column label="通途SKU" align="center" prop="tongtuSku" />
      <el-table-column label="产品名称" align="center" prop="productName" :show-overflow-tooltip="true"/>
      <el-table-column label="品类" align="center" prop="category" />
      <el-table-column label="发货数量" align="center" prop="shipmentQuantity" />
      <el-table-column label="渠道" align="center" prop="channel" />
      <el-table-column label="渠道账号" align="center" prop="channelAccount" />
      <el-table-column label="销售站点" align="center" prop="salesSite" />
      <el-table-column label="发货仓库" align="center" prop="shipmentWarehouse" />
      <el-table-column label="包裹号" align="center" prop="packageNumber" />
      <el-table-column label="邮寄方式" align="center" prop="shippingMethod" :show-overflow-tooltip="true"/>
      <el-table-column label="物流商单号" align="center" prop="logisticsNumber" />
      <el-table-column label="订单号" align="center" prop="orderNumber" />
      <el-table-column label="买家姓名" align="center" prop="buyerName" />
      <el-table-column label="国家 / 地区" align="center" prop="countryRegion" />
      <el-table-column label="付款日期" align="center" prop="paymentDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.paymentDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="产品开发员" align="center" prop="productDeveloper" />
      <el-table-column label="收款币种" align="center" prop="receiptCurrency" />
      <el-table-column label="订单总售价" align="center" prop="totalOrderPrice" />
      <el-table-column label="订单总售价 (人民币)" align="center" prop="totalOrderPriceCny" />
      <el-table-column label="售价" align="center" prop="sellingPrice" />
      <el-table-column label="售价 (人民币)" align="center" prop="sellingPriceCny" />
      <el-table-column label="商品成本" align="center" prop="productCost" />
      <el-table-column label="渠道成交费 (人民币)" align="center" prop="channelFeeCny" />
      <el-table-column label="收入 (人民币)" align="center" prop="incomeCny" />
      <el-table-column label="头程运费" align="center" prop="firstLegShippingCost" />
      <el-table-column label="头程报关费" align="center" prop="firstLegCustomsFee" />
      <el-table-column label="头程其它费" align="center" prop="firstLegOtherFee" />
      <el-table-column label="包装材料" align="center" prop="packagingMaterial" />
      <el-table-column label="包装费用" align="center" prop="packagingCost" />
      <el-table-column label="通途运费" align="center" prop="tongtuShippingCost" />
      <el-table-column label="物流商运费" align="center" prop="logisticsShippingCost" />
      <el-table-column label="订单备注" align="center" prop="orderNotes" />
      <el-table-column label="订单号-整理" align="center" prop="sortedOrderNumber" />
      <el-table-column label="头程费用" align="center" prop="firstLegCost" />
      <el-table-column label="退款金额" align="center" prop="refundAmount" />
      <el-table-column label="补发费用" align="center" prop="replenishmentCost" />
      <el-table-column label="其他费用" align="center" prop="otherCosts" />
      <el-table-column label="后台退款运费补贴/费用" align="center" prop="backendRefundSubsidy" />
      <el-table-column label="广告费" align="center" prop="advertisingFee" />
      <el-table-column label="清货补贴" align="center" prop="clearanceSubsidy" />
      <el-table-column label="美国平台面单费用" align="center" prop="usPlatformLabelFee" />
      <el-table-column label="比例" align="center" prop="ratio" />
      <el-table-column label="毛利" align="center" prop="grossProfit" />
      <el-table-column label="毛利率" align="center" prop="grossProfitRate" />
      <el-table-column label="分销佣金" align="center" prop="distributionCommission" />
      <el-table-column label="TD实际毛利" align="center" prop="tdActualGrossProfit" />
      <el-table-column label="TD实际毛利率" align="center" prop="tdActualGrossProfitRate" />
      <el-table-column label="运营" align="center" prop="operator" />
      <el-table-column label="开发员" align="center" prop="developer" />
      <el-table-column label="订单数量" align="center" prop="orderQuantity" />
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['temu:details:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['temu:details:remove']"
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

    <!-- 添加或修改Temu订单明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="发货日期" prop="shipmentDate">
          <el-date-picker clearable
                          v-model="form.shipmentDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择发货日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否补发货" prop="isReplenishment">
          <el-select v-model="form.isReplenishment" placeholder="请选择是否补发货">
            <el-option
              v-for="dict in dict.type.logic_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="通途SKU" prop="tongtuSku">
          <el-input v-model="form.tongtuSku" placeholder="请输入通途SKU" />
        </el-form-item>
        <el-form-item label="产品名称" prop="productName">
          <el-input v-model="form.productName" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="品类" prop="category">
          <el-input v-model="form.category" placeholder="请输入品类" />
        </el-form-item>
        <el-form-item label="发货数量" prop="shipmentQuantity">
          <el-input v-model="form.shipmentQuantity" placeholder="请输入发货数量" />
        </el-form-item>
        <el-form-item label="渠道" prop="channel">
          <el-input v-model="form.channel" placeholder="请输入渠道" />
        </el-form-item>
        <el-form-item label="渠道账号" prop="channelAccount">
          <el-input v-model="form.channelAccount" placeholder="请输入渠道账号" />
        </el-form-item>
        <el-form-item label="销售站点" prop="salesSite">
          <el-input v-model="form.salesSite" placeholder="请输入销售站点" />
        </el-form-item>
        <el-form-item label="发货仓库" prop="shipmentWarehouse">
          <el-input v-model="form.shipmentWarehouse" placeholder="请输入发货仓库" />
        </el-form-item>
        <el-form-item label="包裹号" prop="packageNumber">
          <el-input v-model="form.packageNumber" placeholder="请输入包裹号" />
        </el-form-item>
        <el-form-item label="邮寄方式" prop="shippingMethod">
          <el-input v-model="form.shippingMethod" placeholder="请输入邮寄方式" />
        </el-form-item>
        <el-form-item label="物流商单号" prop="logisticsNumber">
          <el-input v-model="form.logisticsNumber" placeholder="请输入物流商单号" />
        </el-form-item>
        <el-form-item label="订单号" prop="orderNumber">
          <el-input v-model="form.orderNumber" placeholder="请输入订单号" />
        </el-form-item>
        <el-form-item label="买家姓名" prop="buyerName">
          <el-input v-model="form.buyerName" placeholder="请输入买家姓名" />
        </el-form-item>
        <el-form-item label="国家 / 地区" prop="countryRegion">
          <el-input v-model="form.countryRegion" placeholder="请输入国家 / 地区" />
        </el-form-item>
        <el-form-item label="付款日期" prop="paymentDate">
          <el-date-picker clearable
                          v-model="form.paymentDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择付款日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="产品开发员" prop="productDeveloper">
          <el-input v-model="form.productDeveloper" placeholder="请输入产品开发员" />
        </el-form-item>
        <el-form-item label="收款币种" prop="receiptCurrency">
          <el-input v-model="form.receiptCurrency" placeholder="请输入收款币种" />
        </el-form-item>
        <el-form-item label="订单总售价" prop="totalOrderPrice">
          <el-input v-model="form.totalOrderPrice" placeholder="请输入订单总售价" />
        </el-form-item>
        <el-form-item label="订单总售价 (人民币)" prop="totalOrderPriceCny">
          <el-input v-model="form.totalOrderPriceCny" placeholder="请输入订单总售价 (人民币)" />
        </el-form-item>
        <el-form-item label="售价" prop="sellingPrice">
          <el-input v-model="form.sellingPrice" placeholder="请输入售价" />
        </el-form-item>
        <el-form-item label="售价 (人民币)" prop="sellingPriceCny">
          <el-input v-model="form.sellingPriceCny" placeholder="请输入售价 (人民币)" />
        </el-form-item>
        <el-form-item label="商品成本" prop="productCost">
          <el-input v-model="form.productCost" placeholder="请输入商品成本" />
        </el-form-item>
        <el-form-item label="渠道成交费 (人民币)" prop="channelFeeCny">
          <el-input v-model="form.channelFeeCny" placeholder="请输入渠道成交费 (人民币)" />
        </el-form-item>
        <el-form-item label="收入 (人民币)" prop="incomeCny">
          <el-input v-model="form.incomeCny" placeholder="请输入收入 (人民币)" />
        </el-form-item>
        <el-form-item label="头程运费" prop="firstLegShippingCost">
          <el-input v-model="form.firstLegShippingCost" placeholder="请输入头程运费" />
        </el-form-item>
        <el-form-item label="头程报关费" prop="firstLegCustomsFee">
          <el-input v-model="form.firstLegCustomsFee" placeholder="请输入头程报关费" />
        </el-form-item>
        <el-form-item label="头程其它费" prop="firstLegOtherFee">
          <el-input v-model="form.firstLegOtherFee" placeholder="请输入头程其它费" />
        </el-form-item>
        <el-form-item label="包装材料" prop="packagingMaterial">
          <el-input v-model="form.packagingMaterial" placeholder="请输入包装材料" />
        </el-form-item>
        <el-form-item label="包装费用" prop="packagingCost">
          <el-input v-model="form.packagingCost" placeholder="请输入包装费用" />
        </el-form-item>
        <el-form-item label="通途运费" prop="tongtuShippingCost">
          <el-input v-model="form.tongtuShippingCost" placeholder="请输入通途运费" />
        </el-form-item>
        <el-form-item label="物流商运费" prop="logisticsShippingCost">
          <el-input v-model="form.logisticsShippingCost" placeholder="请输入物流商运费" />
        </el-form-item>
        <el-form-item label="订单备注" prop="orderNotes">
          <el-input v-model="form.orderNotes" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="订单号-整理" prop="sortedOrderNumber">
          <el-input v-model="form.sortedOrderNumber" placeholder="请输入订单号-整理" />
        </el-form-item>
        <el-form-item label="头程费用" prop="firstLegCost">
          <el-input v-model="form.firstLegCost" placeholder="请输入头程费用" />
        </el-form-item>
        <el-form-item label="退款金额" prop="refundAmount">
          <el-input v-model="form.refundAmount" placeholder="请输入退款金额" />
        </el-form-item>
        <el-form-item label="补发费用" prop="replenishmentCost">
          <el-input v-model="form.replenishmentCost" placeholder="请输入补发费用" />
        </el-form-item>
        <el-form-item label="其他费用" prop="otherCosts">
          <el-input v-model="form.otherCosts" placeholder="请输入其他费用" />
        </el-form-item>
        <el-form-item label="后台退款运费补贴/费用" prop="backendRefundSubsidy">
          <el-input v-model="form.backendRefundSubsidy" placeholder="请输入后台退款运费补贴/费用" />
        </el-form-item>
        <el-form-item label="广告费" prop="advertisingFee">
          <el-input v-model="form.advertisingFee" placeholder="请输入广告费" />
        </el-form-item>
        <el-form-item label="清货补贴" prop="clearanceSubsidy">
          <el-input v-model="form.clearanceSubsidy" placeholder="请输入清货补贴" />
        </el-form-item>
        <el-form-item label="美国平台面单费用" prop="usPlatformLabelFee">
          <el-input v-model="form.usPlatformLabelFee" placeholder="请输入美国平台面单费用" />
        </el-form-item>
        <el-form-item label="比例" prop="ratio">
          <el-input v-model="form.ratio" placeholder="请输入比例" />
        </el-form-item>
        <el-form-item label="毛利" prop="grossProfit">
          <el-input v-model="form.grossProfit" placeholder="请输入毛利" />
        </el-form-item>
        <el-form-item label="毛利率" prop="grossProfitRate">
          <el-input v-model="form.grossProfitRate" placeholder="请输入毛利率" />
        </el-form-item>
        <el-form-item label="分销佣金" prop="distributionCommission">
          <el-input v-model="form.distributionCommission" placeholder="请输入分销佣金" />
        </el-form-item>
        <el-form-item label="TD实际毛利" prop="tdActualGrossProfit">
          <el-input v-model="form.tdActualGrossProfit" placeholder="请输入TD实际毛利" />
        </el-form-item>
        <el-form-item label="TD实际毛利率" prop="tdActualGrossProfitRate">
          <el-input v-model="form.tdActualGrossProfitRate" placeholder="请输入TD实际毛利率" />
        </el-form-item>
        <el-form-item label="运营" prop="operator">
          <el-input v-model="form.operator" placeholder="请输入运营" />
        </el-form-item>
        <el-form-item label="开发员" prop="developer">
          <el-input v-model="form.developer" placeholder="请输入开发员" />
        </el-form-item>
        <el-form-item label="订单数量" prop="orderQuantity">
          <el-input v-model="form.orderQuantity" placeholder="请输入订单数量" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
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
          <span>仅允许导入xls、xlsx格式文件。</span><br/>
          <span>系统将自动去除完全雷同的数据，请勿重复导入。</span>
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
import { listTemu, getTemu, delTemu, addTemu, updateTemu } from "@/api/saleManage/temu"
import { getToken } from "@/utils/auth";

export default {
  name: "Temu",
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
      // Temu订单明细表格数据
      temuList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 发货日期时间范围
      daterangeShipmentDate: [],
      // 付款时间范围
      daterangePaymentDate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        shipmentDate: null,
        isReplenishment: null,
        tongtuSku: null,
        productName: null,
        category: null,
        shipmentQuantity: null,
        channel: null,
        channelAccount: null,
        salesSite: null,
        shipmentWarehouse: null,
        packageNumber: null,
        shippingMethod: null,
        logisticsNumber: null,
        buyerName: null,
        countryRegion: null,
        paymentDate: null,
        productDeveloper: null,
        receiptCurrency: null,
        totalOrderPrice: null,
        totalOrderPriceCny: null,
        sellingPrice: null,
        sellingPriceCny: null,
        productCost: null,
        channelFeeCny: null,
        incomeCny: null,
        firstLegShippingCost: null,
        firstLegCustomsFee: null,
        firstLegOtherFee: null,
        packagingMaterial: null,
        packagingCost: null,
        tongtuShippingCost: null,
        logisticsShippingCost: null,
        orderNotes: null,
        sortedOrderNumber: null,
        firstLegCost: null,
        refundAmount: null,
        replenishmentCost: null,
        otherCosts: null,
        backendRefundSubsidy: null,
        advertisingFee: null,
        clearanceSubsidy: null,
        usPlatformLabelFee: null,
        ratio: null,
        grossProfit: null,
        grossProfitRate: null,
        distributionCommission: null,
        tdActualGrossProfit: null,
        tdActualGrossProfitRate: null,
        operator: null,
        developer: null,
        orderQuantity: null,
        remarks: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
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
        url: process.env.VUE_APP_BASE_API + "/temu/details/importData"
      },
      importLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询Temu订单明细列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeShipmentDate && '' !== this.daterangeShipmentDate) {
        this.queryParams.params["beginShipmentDate"] = this.daterangeShipmentDate[0]
        this.queryParams.params["endShipmentDate"] = this.daterangeShipmentDate[1]
      }
      if (null != this.daterangePaymentDate && '' !== this.daterangePaymentDate) {
        this.queryParams.params["beginPaymentDate"] = this.daterangePaymentDate[0]
        this.queryParams.params["endPaymentDate"] = this.daterangePaymentDate[1]
      }
      listTemu(this.queryParams).then(response => {
        this.temuList = response.rows
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
        shipmentDate: null,
        isReplenishment: null,
        tongtuSku: null,
        productName: null,
        category: null,
        shipmentQuantity: null,
        channel: null,
        channelAccount: null,
        salesSite: null,
        shipmentWarehouse: null,
        packageNumber: null,
        shippingMethod: null,
        logisticsNumber: null,
        orderNumber: null,
        buyerName: null,
        countryRegion: null,
        paymentDate: null,
        productDeveloper: null,
        receiptCurrency: null,
        totalOrderPrice: null,
        totalOrderPriceCny: null,
        sellingPrice: null,
        sellingPriceCny: null,
        productCost: null,
        channelFeeCny: null,
        incomeCny: null,
        firstLegShippingCost: null,
        firstLegCustomsFee: null,
        firstLegOtherFee: null,
        packagingMaterial: null,
        packagingCost: null,
        tongtuShippingCost: null,
        logisticsShippingCost: null,
        orderNotes: null,
        sortedOrderNumber: null,
        firstLegCost: null,
        refundAmount: null,
        replenishmentCost: null,
        otherCosts: null,
        backendRefundSubsidy: null,
        advertisingFee: null,
        clearanceSubsidy: null,
        usPlatformLabelFee: null,
        ratio: null,
        grossProfit: null,
        grossProfitRate: null,
        distributionCommission: null,
        tdActualGrossProfit: null,
        tdActualGrossProfitRate: null,
        operator: null,
        developer: null,
        orderQuantity: null,
        remarks: null
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
      this.daterangeShipmentDate = []
      this.daterangePaymentDate = []
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
      this.title = "添加Temu订单明细"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const orderNumber = row.id || this.ids
      getTemu(orderNumber).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改Temu订单明细"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTemu(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addTemu(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除Temu订单明细编号为"' + ids + '"的数据项？').then(function() {
        return delTemu(id)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('temu/details/export', {
        ...this.queryParams
      }, `temu_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      if (this.importLoading) {
        this.$modal.msgError("正在导入数据，请不要重复点击")
        return
      }
      this.upload.title = "数据导入"
      this.upload.open = true
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('temu/details/importTemplate', {
      }, `temu_details_data_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
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
        type: 'success',
        duration: 0, // 0表示不会自动关闭
        showClose: true
      })

      this.$refs.upload.submit()
    }
  }
}
</script>
