package com.ruoyi.sales.domain;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 海外托管业务数据管理对象 overseas_hosting_data
 *
 * @author ZMJ
 * @date 2025-09-30
 */
@EqualsAndHashCode(callSuper = true)
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
    private String store;

    /** 结算时间，格式为 YYYY-MM-DD HH:MM:SS */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结算时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date settlementTime;

    /** 订单号，可能包含长数字字符串 */
    @Excel(name = "订单号")
    private String orderNumber;

    /** 商品ID，允许NULL（处理#N/A等无效值） */
    @Excel(name = "商品ID")
    private String productId;

    /** 品牌名称 */
    @Excel(name = "品牌")
    private String brand;

    /** SKU编码 */
    @Excel(name = "SKU")
    private String sku;

    /** 商品类目 */
    @Excel(name = "类目")
    private String category;

    /** 费用项，例如供货款、处罚费等 */
    @Excel(name = "费用项")
    private String feeItem;

    /** 金额（人民币），支持负值（如退款或处罚） */
    @Excel(name = "金额(CNY)")
    private BigDecimal amountCny;

    /** 平台流水单号 */
    @Excel(name = "平台流水单号")
    private String platformTransactionNumber;

    /** 限时供货价任务ID，允许NULL（处理#DIV/0!等无效值） */
    @Excel(name = "限时供货价任务ID")
    private String limitedSupplyPriceTaskId;

    /** 收入金额，允许NULL */
    @Excel(name = "收入")
    private BigDecimal revenue;

    /** 退款金额，允许NULL */
    @Excel(name = "退款")
    private BigDecimal refund;

    /** 分摊比例，小数形式（如0.0495），允许NULL */
    @Excel(name = "分摊比例")
    private BigDecimal allocationRatio;

    /** 采购成本 */
    @Excel(name = "采购成本")
    private BigDecimal purchaseCost;

    /** 头程成本 */
    @Excel(name = "头程成本")
    private BigDecimal firstMileCost;

    /** 物流费 */
    @Excel(name = "物流费")
    private BigDecimal logisticsFee;

    /** 包装费用 */
    @Excel(name = "包装费用")
    private BigDecimal packagingCost;

    /** 其他费用 */
    @Excel(name = "其他费用")
    private BigDecimal otherCosts;

    /** 补发费用 */
    @Excel(name = "补发费用")
    private BigDecimal reshipmentCost;

    /** 毛利 */
    @Excel(name = "毛利")
    private BigDecimal grossProfit;

    /** 毛利率，小数形式（如0.1172），支持负值 */
    @Excel(name = "毛利率")
    private BigDecimal grossProfitMargin;

    /** 开发员姓名 */
    @Excel(name = "开发员")
    private String developer;

    /** 运营人员姓名 */
    @Excel(name = "运营")
    private String operator;

    /** 发货仓库 */
    @Excel(name = "发货仓库")
    private String shippingWarehouse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OverseasHostingData that = (OverseasHostingData) o;
        return Objects.equals(store.isEmpty() ? null : store, that.store.isEmpty() ? null : that.store) &&
                ((OverseasHostingData) o).getSettlementTime() == (that.settlementTime) &&
                Objects.equals(orderNumber.isEmpty() ? null : orderNumber, that.orderNumber.isEmpty() ? null : that.orderNumber) &&
                Objects.equals(productId.isEmpty() ? null : productId, that.productId.isEmpty() ? null : that.productId) &&
                Objects.equals(brand.isEmpty() ? null : brand, that.brand.isEmpty() ? null : that.brand) &&
                Objects.equals(sku.isEmpty() ? null : sku, that.sku.isEmpty() ? null : that.sku) &&
                Objects.equals(category.isEmpty() ? null : category, that.category.isEmpty() ? null : that.category) &&
                Objects.equals(feeItem.isEmpty() ? null : feeItem, that.feeItem.isEmpty() ? null : that.feeItem) &&
                compareBigDecimal(amountCny, that.amountCny) &&
                Objects.equals(platformTransactionNumber.isEmpty() ? null : platformTransactionNumber, that.platformTransactionNumber.isEmpty() ? null : that.platformTransactionNumber) &&
                Objects.equals(limitedSupplyPriceTaskId.isEmpty() ? null : limitedSupplyPriceTaskId, that.limitedSupplyPriceTaskId.isEmpty() ? null : that.limitedSupplyPriceTaskId) &&
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
                Objects.equals(developer.isEmpty() ? null : developer, that.developer.isEmpty() ? null : that.developer) &&
                Objects.equals(operator.isEmpty() ? null : operator, that.operator.isEmpty() ? null : that.operator) &&
                Objects.equals(shippingWarehouse.isEmpty() ? null : shippingWarehouse, that.shippingWarehouse.isEmpty() ? null : that.shippingWarehouse);
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
