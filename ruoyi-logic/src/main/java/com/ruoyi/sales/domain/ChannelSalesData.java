package com.ruoyi.sales.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;

/**
 * 全渠道销售数据分析对象 channel_sales_data
 *
 * @author ZMJ
 * @date 2025-09-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelSalesData extends BaseEntity
{
    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Integer id;

    /** 一级品类 */
    @Excel(name = "一级品类")
    private String firstLevelCategory;

    /** 品类 */
    @Excel(name = "品类")
    private String category;

    /** 通途SKU */
    @Excel(name = "通途SKU")
    private String tongtuSku;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 运营组名 */
    @Excel(name = "运营组名")
    private String operationGroupName;

    /** 运营 */
    @Excel(name = "运营")
    private String operator;

    /** 开发组名 */
    @Excel(name = "开发组名")
    private String developmentGroupName;

    /** 产品开发员 */
    @Excel(name = "产品开发员")
    private String productDeveloper;

    /** 渠道 */
    @Excel(name = "渠道")
    private String channel;

    /** 渠道账号 */
    @Excel(name = "渠道账号")
    private String channelAccount;

    /** 运营部门 */
    @Excel(name = "运营部门")
    private String operationDepartment;

    /** 店铺代码 */
    @Excel(name = "店铺代码")
    private String storeCode;

    /** 地区 */
    @Excel(name = "地区")
    private String region;

    /** 国家 */
    @Excel(name = "国家")
    private String country;

    /** 国家/地区 */
    @Excel(name = "国家/地区")
    private String countryRegion;

    /** 品牌 */
    @Excel(name = "品牌")
    private String brand;

    /** 销售模式 */
    @Excel(name = "销售模式")
    private String salesMode;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNumber;

    /** 付款日期/结算时间 */
    @Excel(name = "付款日期/结算时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date paymentSettlementDate;

    /** 发货仓库 */
    @Excel(name = "发货仓库")
    private String shippingWarehouse;

    /** 发货数量 */
    @Excel(name = "发货数量")
    private Integer shippingQuantity;

    /** 收入(人民币) */
    @Excel(name = "收入(人民币)")
    private BigDecimal revenueRmb;

    /** 商品成本 */
    @Excel(name = "商品成本")
    private BigDecimal productCost;

    /** 头程运费 */
    @Excel(name = "头程运费")
    private BigDecimal firstMileShipping;

    /** 包装费用 */
    @Excel(name = "包装费用")
    private BigDecimal packagingCost;

    /** 物流商运费 */
    @Excel(name = "物流商运费")
    private BigDecimal logisticsShipping;

    /** 渠道成交费(人民币) */
    @Excel(name = "渠道成交费(人民币)")
    private BigDecimal channelTransactionFeeRmb;

    /** 税费 */
    @Excel(name = "税费")
    private BigDecimal taxes;

    /** 退款 */
    @Excel(name = "退款")
    private BigDecimal refund;

    /** 补发金额 */
    @Excel(name = "补发金额")
    private BigDecimal reshipmentAmount;

    /** 送样成本 */
    @Excel(name = "送样成本")
    private BigDecimal sampleCost;

    /** 滞销补贴 */
    @Excel(name = "滞销补贴")
    private BigDecimal unsoldSubsidy;

    /** 订单毛利 */
    @Excel(name = "订单毛利")
    private BigDecimal orderGrossProfit;

    /** 订单毛利率 */
    @Excel(name = "订单毛利率")
    private BigDecimal orderGrossProfitRate;

    /** 广告/促销 */
    @Excel(name = "广告/促销")
    private BigDecimal advertisingPromotion;

    /** 仓储费 */
    @Excel(name = "仓储费")
    private BigDecimal storageFee;

    /** 移除费 */
    @Excel(name = "移除费")
    private BigDecimal removalFee;

    /** 退货处理费 */
    @Excel(name = "退货处理费")
    private BigDecimal returnProcessingFee;

    /** 其他费用 */
    @Excel(name = "其他费用")
    private BigDecimal otherFees;

    /** 滞销品预算 */
    @Excel(name = "滞销品预算")
    private BigDecimal unsoldBudget;

    /** 手续费 */
    @Excel(name = "手续费")
    private BigDecimal handlingFee;

    /** 订单净利 */
    @Excel(name = "订单净利")
    private BigDecimal orderNetProfit;

    /** ROI */
    @Excel(name = "ROI")
    private BigDecimal roi;

    /** 产品现金流为正所需的天数 */
    @Excel(name = "产品现金流为正所需的天数")
    private Integer daysToPositiveCashFlow;

    /** 验算公式 */
    @Excel(name = "验算公式")
    private BigDecimal verificationFormula;

    /** 毛利是否大于0（0否 1是） */
    @Excel(name = "毛利是否大于0", dictType = "logic_yes_no")
    private String isGrossProfitPositive;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelSalesData that = (ChannelSalesData) o;
        return Objects.equals(firstLevelCategory != null && firstLevelCategory.isEmpty() ? null : firstLevelCategory, that.firstLevelCategory != null && that.firstLevelCategory.isEmpty() ? null : that.firstLevelCategory) &&
                Objects.equals(category != null && category.isEmpty() ? null : category, that.category != null && that.category.isEmpty() ? null : that.category) &&
                Objects.equals(tongtuSku != null && tongtuSku.isEmpty() ? null : tongtuSku, that.tongtuSku != null && that.tongtuSku.isEmpty() ? null : that.tongtuSku) &&
                Objects.equals(productName != null && productName.isEmpty() ? null : productName, that.productName != null && that.productName.isEmpty() ? null : that.productName) &&
                Objects.equals(operationGroupName != null && operationGroupName.isEmpty() ? null : operationGroupName, that.operationGroupName != null && that.operationGroupName.isEmpty() ? null : that.operationGroupName) &&
                Objects.equals(operator != null && operator.isEmpty() ? null : operator, that.operator != null && that.operator.isEmpty() ? null : that.operator) &&
                Objects.equals(developmentGroupName != null && developmentGroupName.isEmpty() ? null : developmentGroupName, that.developmentGroupName != null && that.developmentGroupName.isEmpty() ? null : that.developmentGroupName) &&
                Objects.equals(productDeveloper != null && productDeveloper.isEmpty() ? null : productDeveloper, that.productDeveloper != null && that.productDeveloper.isEmpty() ? null : that.productDeveloper) &&
                Objects.equals(channel != null && channel.isEmpty() ? null : channel, that.channel != null && that.channel.isEmpty() ? null : that.channel) &&
                Objects.equals(channelAccount != null && channelAccount.isEmpty() ? null : channelAccount, that.channelAccount != null && that.channelAccount.isEmpty() ? null : that.channelAccount) &&
                Objects.equals(operationDepartment != null && operationDepartment.isEmpty() ? null : operationDepartment, that.operationDepartment != null && that.operationDepartment.isEmpty() ? null : that.operationDepartment) &&
                Objects.equals(storeCode != null && storeCode.isEmpty() ? null : storeCode, that.storeCode != null && that.storeCode.isEmpty() ? null : that.storeCode) &&
                Objects.equals(region != null && region.isEmpty() ? null : region, that.region != null && that.region.isEmpty() ? null : that.region) &&
                Objects.equals(country != null && country.isEmpty() ? null : country, that.country != null && that.country.isEmpty() ? null : that.country) &&
                Objects.equals(countryRegion != null && countryRegion.isEmpty() ? null : countryRegion, that.countryRegion != null && that.countryRegion.isEmpty() ? null : that.countryRegion) &&
                Objects.equals(brand != null && brand.isEmpty() ? null : brand, that.brand != null && that.brand.isEmpty() ? null : that.brand) &&
                Objects.equals(salesMode != null && salesMode.isEmpty() ? null : salesMode, that.salesMode != null && that.salesMode.isEmpty() ? null : that.salesMode) &&
                Objects.equals(orderNumber != null && orderNumber.isEmpty() ? null : orderNumber, that.orderNumber != null && that.orderNumber.isEmpty() ? null : that.orderNumber) &&
                ((ChannelSalesData) o).getPaymentSettlementDate() == (that.paymentSettlementDate) &&
                Objects.equals(shippingWarehouse != null && shippingWarehouse.isEmpty() ? null : shippingWarehouse, that.shippingWarehouse != null && that.shippingWarehouse.isEmpty() ? null : that.shippingWarehouse) &&
                Objects.equals(shippingQuantity, that.shippingQuantity) &&
                compareBigDecimal(revenueRmb, that.revenueRmb) &&
                compareBigDecimal(productCost, that.productCost) &&
                compareBigDecimal(firstMileShipping, that.firstMileShipping) &&
                compareBigDecimal(packagingCost, that.packagingCost) &&
                compareBigDecimal(logisticsShipping, that.logisticsShipping) &&
                compareBigDecimal(channelTransactionFeeRmb, that.channelTransactionFeeRmb) &&
                compareBigDecimal(taxes, that.taxes) &&
                compareBigDecimal(refund, that.refund) &&
                compareBigDecimal(reshipmentAmount, that.reshipmentAmount) &&
                compareBigDecimal(sampleCost, that.sampleCost) &&
                compareBigDecimal(unsoldSubsidy, that.unsoldSubsidy) &&
                compareBigDecimal(orderGrossProfit, that.orderGrossProfit) &&
                compareBigDecimal(orderGrossProfitRate, that.orderGrossProfitRate) &&
                compareBigDecimal(advertisingPromotion, that.advertisingPromotion) &&
                compareBigDecimal(storageFee, that.storageFee) &&
                compareBigDecimal(removalFee, that.removalFee) &&
                compareBigDecimal(returnProcessingFee, that.returnProcessingFee) &&
                compareBigDecimal(otherFees, that.otherFees) &&
                compareBigDecimal(unsoldBudget, that.unsoldBudget) &&
                compareBigDecimal(handlingFee, that.handlingFee) &&
                compareBigDecimal(orderNetProfit, that.orderNetProfit) &&
                compareBigDecimal(roi, that.roi) &&
                Objects.equals(daysToPositiveCashFlow, that.daysToPositiveCashFlow) &&
                compareBigDecimal(verificationFormula, that.verificationFormula) &&
                Objects.equals(isGrossProfitPositive != null && isGrossProfitPositive.isEmpty() ? null : isGrossProfitPositive, that.isGrossProfitPositive != null && that.isGrossProfitPositive.isEmpty() ? null : that.isGrossProfitPositive);
    }


    private boolean compareBigDecimal(BigDecimal bd1, BigDecimal bd2) {
        if (bd1 == null && bd2 == null) return true;
        if (bd1 == null || bd2 == null) return false;
        return bd1.compareTo(bd2.setScale(bd1.scale(), RoundingMode.HALF_UP)) == 0;
    }

}
