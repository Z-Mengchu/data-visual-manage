package com.ruoyi.sales.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.converter.CustomBigDecimalConverter;
import com.ruoyi.converter.CustomStringConverter;
import com.ruoyi.converter.ExcelDateConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;

/**
 * 海外托管业务数据管理对象 overseas_hosting_data
 *
 * @author ZMJ
 * @date 2025-09-30
 */
@ExcelIgnoreUnannotated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverseasHostingData extends BaseEntity
{
    @Serial
    private static final long serialVersionUID = 1L;

    /** 自增主键，唯一标识每条记录 */
    private Integer id;

    /** 店铺名称，例如 AE56S */
    @Excel(name = "店铺")
    @ExcelProperty(value = "店铺", converter = CustomStringConverter.class)
    private String store;

    /** 结算时间，格式为 YYYY-MM-DD HH:MM:SS */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结算时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "结算时间", converter = ExcelDateConverter.class)
    private Date settlementTime;

    /** 订单号，可能包含长数字字符串 */
    @Excel(name = "订单号")
    @ExcelProperty(value = "订单号", converter = CustomStringConverter.class)
    private String orderNumber;

    /** 商品ID，允许NULL（处理#N/A等无效值） */
    @Excel(name = "商品ID")
    @ExcelProperty(value = "商品ID", converter = CustomStringConverter.class)
    private String productId;

    /** 品牌名称 */
    @Excel(name = "品牌")
    @ExcelProperty(value = "品牌", converter = CustomStringConverter.class)
    private String brand;

    /** SKU编码 */
    @Excel(name = "SKU")
    @ExcelProperty(value = "SKU", converter = CustomStringConverter.class)
    private String sku;

    /** 商品类目 */
    @Excel(name = "类目")
    @ExcelProperty(value = "类目", converter = CustomStringConverter.class)
    private String category;

    /** 费用项，例如供货款、处罚费等 */
    @Excel(name = "费用项")
    @ExcelProperty(value = "费用项", converter = CustomStringConverter.class)
    private String feeItem;

    /** 金额（人民币），支持负值（如退款或处罚） */
    @Excel(name = "金额(CNY)")
    @ExcelProperty(value = "金额(CNY)", converter = CustomBigDecimalConverter.class)
    private BigDecimal amountCny;

    /** 平台流水单号 */
    @Excel(name = "平台流水单号")
    @ExcelProperty(value = "平台流水单号", converter = CustomStringConverter.class)
    private String platformTransactionNumber;

    /** 限时供货价任务ID，允许NULL（处理#DIV/0!等无效值） */
    @Excel(name = "限时供货价任务ID")
    @ExcelProperty(value = "限时供货价任务ID", converter = CustomStringConverter.class)
    private String limitedSupplyPriceTaskId;

    /** 收入金额，允许NULL */
    @Excel(name = "收入")
    @ExcelProperty(value = "收入", converter = CustomBigDecimalConverter.class)
    private BigDecimal revenue;

    /** 退款金额，允许NULL */
    @Excel(name = "退款")
    @ExcelProperty(value = "退款", converter = CustomBigDecimalConverter.class)
    private BigDecimal refund;

    /** 分摊比例，小数形式（如0.0495），允许NULL */
    @Excel(name = "分摊比例")
    @ExcelProperty(value = "分摊比例", converter = CustomBigDecimalConverter.class)
    private BigDecimal allocationRatio;

    /** 采购成本 */
    @Excel(name = "采购成本")
    @ExcelProperty(value = "采购成本", converter = CustomBigDecimalConverter.class)
    private BigDecimal purchaseCost;

    /** 头程成本 */
    @Excel(name = "头程成本")
    @ExcelProperty(value = "头程成本", converter = CustomBigDecimalConverter.class)
    private BigDecimal firstMileCost;

    /** 物流费 */
    @Excel(name = "物流费")
    @ExcelProperty(value = "物流费", converter = CustomBigDecimalConverter.class)
    private BigDecimal logisticsFee;

    /** 包装费用 */
    @Excel(name = "包装费用")
    @ExcelProperty(value = "包装费用", converter = CustomBigDecimalConverter.class)
    private BigDecimal packagingCost;

    /** 其他费用 */
    @Excel(name = "其他费用")
    @ExcelProperty(value = "其他费用", converter = CustomBigDecimalConverter.class)
    private BigDecimal otherCosts;

    /** 补发费用 */
    @Excel(name = "补发费用")
    @ExcelProperty(value = "补发费用", converter = CustomBigDecimalConverter.class)
    private BigDecimal reshipmentCost;

    /** 毛利 */
    @Excel(name = "毛利")
    @ExcelProperty(value = "毛利", converter = CustomBigDecimalConverter.class)
    private BigDecimal grossProfit;

    /** 毛利率，小数形式（如0.1172），支持负值 */
    @Excel(name = "毛利率")
    @ExcelProperty(value = "毛利率", converter = CustomBigDecimalConverter.class)
    private BigDecimal grossProfitMargin;

    /** 开发员姓名 */
    @Excel(name = "开发员")
    @ExcelProperty(value = "开发员", converter = CustomStringConverter.class)
    private String developer;

    /** 运营人员姓名 */
    @Excel(name = "运营")
    @ExcelProperty(value = "运营", converter = CustomStringConverter.class)
    private String operator;

    /** 发货仓库 */
    @Excel(name = "发货仓库")
    @ExcelProperty(value = "发货仓库", converter = CustomStringConverter.class)
    private String shippingWarehouse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OverseasHostingData that = (OverseasHostingData) o;
        return Objects.equals(StringUtils.isEmpty(store) ? null : store, StringUtils.isEmpty(that.store) ? null : that.store) &&
                ((OverseasHostingData) o).getSettlementTime() == (that.settlementTime) &&
                Objects.equals(StringUtils.isEmpty(orderNumber) ? null : orderNumber, StringUtils.isEmpty(that.orderNumber) ? null : that.orderNumber) &&
                Objects.equals(StringUtils.isEmpty(productId) ? null : productId, StringUtils.isEmpty(that.productId) ? null : that.productId) &&
                Objects.equals(StringUtils.isEmpty(brand) ? null : brand, StringUtils.isEmpty(that.brand) ? null : that.brand) &&
                Objects.equals(StringUtils.isEmpty(sku) ? null : sku, StringUtils.isEmpty(that.sku) ? null : that.sku) &&
                Objects.equals(StringUtils.isEmpty(category) ? null : category, StringUtils.isEmpty(that.category) ? null : that.category) &&
                Objects.equals(StringUtils.isEmpty(feeItem) ? null : feeItem, StringUtils.isEmpty(that.feeItem) ? null : that.feeItem) &&
                compareBigDecimal(amountCny, that.amountCny) &&
                Objects.equals(StringUtils.isEmpty(platformTransactionNumber) ? null : platformTransactionNumber, StringUtils.isEmpty(that.platformTransactionNumber) ? null : that.platformTransactionNumber) &&
                Objects.equals(StringUtils.isEmpty(limitedSupplyPriceTaskId) ? null : limitedSupplyPriceTaskId, StringUtils.isEmpty(that.limitedSupplyPriceTaskId) ? null : that.limitedSupplyPriceTaskId) &&
                compareBigDecimal(revenue, that.revenue) &&
                compareBigDecimal(refund, that.refund) &&
                compareBigDecimal(allocationRatio, that.allocationRatio) &&
                compareBigDecimal(purchaseCost, that.purchaseCost) &&
                compareBigDecimal(firstMileCost, that.firstMileCost) &&
                compareBigDecimal(logisticsFee, that.logisticsFee) &&
                compareBigDecimal(packagingCost, that.packagingCost) &&
                compareBigDecimal(otherCosts, that.otherCosts) &&
                compareBigDecimal(reshipmentCost, that.reshipmentCost) &&
                compareBigDecimal(grossProfit, that.grossProfit) &&
                compareBigDecimal(grossProfitMargin, that.grossProfitMargin) &&
                Objects.equals(StringUtils.isEmpty(developer) ? null : developer, StringUtils.isEmpty(that.developer) ? null : that.developer) &&
                Objects.equals(StringUtils.isEmpty(operator) ? null : operator, StringUtils.isEmpty(that.operator) ? null : that.operator) &&
                Objects.equals(StringUtils.isEmpty(shippingWarehouse) ? null : shippingWarehouse, StringUtils.isEmpty(that.shippingWarehouse) ? null : that.shippingWarehouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, store, settlementTime, orderNumber, productId, brand, sku, category, feeItem, amountCny, platformTransactionNumber, limitedSupplyPriceTaskId, revenue, refund, allocationRatio, purchaseCost, firstMileCost, logisticsFee, packagingCost, otherCosts, reshipmentCost, grossProfit, grossProfitMargin, developer, operator, shippingWarehouse);
    }

    private boolean compareBigDecimal(BigDecimal bd1, BigDecimal bd2) {
        if (bd1 == null && bd2 == null) return true;
        if (bd1 == null || bd2 == null) return false;
        return bd1.compareTo(bd2.setScale(bd1.scale(), RoundingMode.HALF_UP)) == 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("store", getStore())
            .append("settlementTime", getSettlementTime())
            .append("orderNumber", getOrderNumber())
            .append("productId", getProductId())
            .append("brand", getBrand())
            .append("sku", getSku())
            .append("category", getCategory())
            .append("feeItem", getFeeItem())
            .append("amountCny", getAmountCny())
            .append("platformTransactionNumber", getPlatformTransactionNumber())
            .append("limitedSupplyPriceTaskId", getLimitedSupplyPriceTaskId())
            .append("revenue", getRevenue())
            .append("refund", getRefund())
            .append("allocationRatio", getAllocationRatio())
            .append("purchaseCost", getPurchaseCost())
            .append("firstMileCost", getFirstMileCost())
            .append("logisticsFee", getLogisticsFee())
            .append("packagingCost", getPackagingCost())
            .append("otherCosts", getOtherCosts())
            .append("reshipmentCost", getReshipmentCost())
            .append("grossProfit", getGrossProfit())
            .append("grossProfitMargin", getGrossProfitMargin())
            .append("developer", getDeveloper())
            .append("operator", getOperator())
            .append("shippingWarehouse", getShippingWarehouse())
            .toString();
    }
}
