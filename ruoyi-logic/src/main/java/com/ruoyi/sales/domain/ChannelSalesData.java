package com.ruoyi.sales.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
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
        return Objects.equals(StringUtils.isEmpty(firstLevelCategory) ? null : firstLevelCategory, StringUtils.isEmpty(that.firstLevelCategory) ? null : that.firstLevelCategory) &&
                Objects.equals(StringUtils.isEmpty(category) ? null : category, StringUtils.isEmpty(that.category) ? null : that.category) &&
                Objects.equals(StringUtils.isEmpty(tongtuSku) ? null : tongtuSku, StringUtils.isEmpty(that.tongtuSku) ? null : that.tongtuSku) &&
                Objects.equals(StringUtils.isEmpty(productName) ? null : productName, StringUtils.isEmpty(that.productName) ? null : that.productName) &&
                Objects.equals(StringUtils.isEmpty(operationGroupName) ? null : operationGroupName, StringUtils.isEmpty(that.operationGroupName) ? null : that.operationGroupName) &&
                Objects.equals(StringUtils.isEmpty(operator) ? null : operator, StringUtils.isEmpty(that.operator) ? null : that.operator) &&
                Objects.equals(StringUtils.isEmpty(developmentGroupName) ? null : developmentGroupName, StringUtils.isEmpty(that.developmentGroupName) ? null : that.developmentGroupName) &&
                Objects.equals(StringUtils.isEmpty(productDeveloper) ? null : productDeveloper, StringUtils.isEmpty(that.productDeveloper) ? null : that.productDeveloper) &&
                Objects.equals(StringUtils.isEmpty(channel) ? null : channel, StringUtils.isEmpty(that.channel) ? null : that.channel) &&
                Objects.equals(StringUtils.isEmpty(channelAccount) ? null : channelAccount, StringUtils.isEmpty(that.channelAccount) ? null : that.channelAccount) &&
                Objects.equals(StringUtils.isEmpty(operationDepartment) ? null : operationDepartment, StringUtils.isEmpty(that.operationDepartment) ? null : that.operationDepartment) &&
                Objects.equals(StringUtils.isEmpty(storeCode) ? null : storeCode, StringUtils.isEmpty(that.storeCode) ? null : that.storeCode) &&
                Objects.equals(StringUtils.isEmpty(region) ? null : region, StringUtils.isEmpty(that.region) ? null : that.region) &&
                Objects.equals(StringUtils.isEmpty(country) ? null : country, StringUtils.isEmpty(that.country) ? null : that.country) &&
                Objects.equals(StringUtils.isEmpty(countryRegion) ? null : countryRegion, StringUtils.isEmpty(that.countryRegion) ? null : that.countryRegion) &&
                Objects.equals(StringUtils.isEmpty(brand) ? null : brand, StringUtils.isEmpty(that.brand) ? null : that.brand) &&
                Objects.equals(StringUtils.isEmpty(salesMode) ? null : salesMode, StringUtils.isEmpty(that.salesMode) ? null : that.salesMode) &&
                Objects.equals(StringUtils.isEmpty(orderNumber) ? null : orderNumber, StringUtils.isEmpty(that.orderNumber) ? null : that.orderNumber) &&
                ((ChannelSalesData) o).getPaymentSettlementDate() == (that.paymentSettlementDate) &&
                Objects.equals(StringUtils.isEmpty(shippingWarehouse) ? null : shippingWarehouse, StringUtils.isEmpty(that.shippingWarehouse) ? null : that.shippingWarehouse) &&
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
                Objects.equals(StringUtils.isEmpty(isGrossProfitPositive) ? null : isGrossProfitPositive, StringUtils.isEmpty(that.isGrossProfitPositive) ? null : that.isGrossProfitPositive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstLevelCategory, category, tongtuSku, productName, operationGroupName, operator, developmentGroupName, productDeveloper, channel, channelAccount, operationDepartment, storeCode, region, country, countryRegion, brand, salesMode, orderNumber, paymentSettlementDate, shippingWarehouse, shippingQuantity, revenueRmb, productCost, firstMileShipping, packagingCost, logisticsShipping, channelTransactionFeeRmb, taxes, refund, reshipmentAmount, sampleCost, unsoldSubsidy, orderGrossProfit, orderGrossProfitRate, advertisingPromotion, storageFee, removalFee, returnProcessingFee, otherFees, unsoldBudget, handlingFee, orderNetProfit, roi, daysToPositiveCashFlow, verificationFormula, isGrossProfitPositive, createdAt, updatedAt, startTime, endTime);
    }

    private boolean compareBigDecimal(BigDecimal bd1, BigDecimal bd2) {
        if (bd1 == null && bd2 == null) return true;
        if (bd1 == null || bd2 == null) return false;
        return bd1.compareTo(bd2.setScale(bd1.scale(), RoundingMode.HALF_UP)) == 0;
    }

}
