package com.ruoyi.sales.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.converter.BooleanStringExportConverter;
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
 * Temu订单明细对象 temu_order_details
 *
 * @author ZMJ
 * @date 2025-09-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TEMUOrderDetails extends BaseEntity
{
    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Integer id;

    /** 发货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发货日期", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(value = "yyyy-MM-dd")
    @ExcelProperty(value = "发货日期", converter = ExcelDateConverter.class)
    private Date shipmentDate;

    /** 是否补发货（0否 1是） */
    @Excel(name = "是否补发货", readConverterExp = "0=否,1=是")
    @ExcelProperty(value = "是否补发货", converter = BooleanStringExportConverter.class)
    private String isReplenishment;

    /** 通途SKU */
    @Excel(name = "通途SKU")
    @ExcelProperty(value = "通途SKU", converter = CustomStringConverter.class)
    private String tongtuSku;

    /** 产品名称 */
    @Excel(name = "产品名称")
    @ExcelProperty(value = "产品名称", converter = CustomStringConverter.class)
    private String productName;

    /** 品类 */
    @Excel(name = "品类")
    @ExcelProperty(value = "品类", converter = CustomStringConverter.class)
    private String category;

    /** 发货数量 */
    @Excel(name = "发货数量")
    @ExcelProperty(value = "发货数量")
    private Integer shipmentQuantity;

    /** 渠道 */
    @Excel(name = "渠道")
    @ExcelProperty(value = "渠道", converter = CustomStringConverter.class)
    private String channel;

    /** 渠道账号 */
    @Excel(name = "渠道账号")
    @ExcelProperty(value = "渠道账号", converter = CustomStringConverter.class)
    private String channelAccount;

    /** 销售站点 */
    @Excel(name = "销售站点")
    @ExcelProperty(value = "销售站点", converter = CustomStringConverter.class)
    private String salesSite;

    /** 发货仓库 */
    @Excel(name = "发货仓库")
    @ExcelProperty(value = "发货仓库", converter = CustomStringConverter.class)
    private String shipmentWarehouse;

    /** 包裹号 */
    @Excel(name = "包裹号")
    @ExcelProperty(value = "包裹号", converter = CustomStringConverter.class)
    private String packageNumber;

    /** 邮寄方式 */
    @Excel(name = "邮寄方式")
    @ExcelProperty(value = "邮寄方式", converter = CustomStringConverter.class)
    private String shippingMethod;

    /** 物流商单号 */
    @Excel(name = "物流商单号")
    @ExcelProperty(value = "物流商单号", converter = CustomStringConverter.class)
    private String logisticsNumber;

    /** 订单号 */
    @Excel(name = "订单号")
    @ExcelProperty(value = "订单号", converter = CustomStringConverter.class)
    private String orderNumber;

    /** 买家姓名 */
    @Excel(name = "买家姓名")
    @ExcelProperty(value = "买家姓名", converter = CustomStringConverter.class)
    private String buyerName;

    /** 国家 / 地区 */
    @Excel(name = "国家 / 地区")
    @ExcelProperty(value = "国家 / 地区", converter = CustomStringConverter.class)
    private String countryRegion;

    /** 付款日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "付款日期", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat(value = "yyyy-MM-dd")
    @ExcelProperty(value = "付款日期", converter = ExcelDateConverter.class)
    private Date paymentDate;

    /** 产品开发员 */
    @Excel(name = "产品开发员")
    @ExcelProperty(value = "产品开发员", converter = CustomStringConverter.class)
    private String productDeveloper;

    /** 收款币种 */
    @Excel(name = "收款币种")
    @ExcelProperty(value = "收款币种", converter = CustomStringConverter.class)
    private String receiptCurrency;

    /** 订单总售价 */
    @Excel(name = "订单总售价")
    @ExcelProperty(value = "订单总售价", converter = CustomBigDecimalConverter.class)
    private BigDecimal totalOrderPrice;

    /** 订单总售价 (人民币) */
    @Excel(name = "订单总售价 (人民币)")
    @ExcelProperty(value = "订单总售价 (人民币)", converter = CustomBigDecimalConverter.class)
    private BigDecimal totalOrderPriceCny;

    /** 售价 */
    @Excel(name = "售价")
    @ExcelProperty(value = "售价", converter = CustomBigDecimalConverter.class)
    private BigDecimal sellingPrice;

    /** 售价 (人民币) */
    @Excel(name = "售价 (人民币)")
    @ExcelProperty(value = "售价 (人民币)", converter = CustomBigDecimalConverter.class)
    private BigDecimal sellingPriceCny;

    /** 商品成本 */
    @Excel(name = "商品成本")
    @ExcelProperty(value = "商品成本", converter = CustomBigDecimalConverter.class)
    private BigDecimal productCost;

    /** 渠道成交费 (人民币) */
    @Excel(name = "渠道成交费 (人民币)")
    @ExcelProperty(value = "渠道成交费 (人民币)", converter = CustomBigDecimalConverter.class)
    private BigDecimal channelFeeCny;

    /** 收入 (人民币) */
    @Excel(name = "收入 (人民币)")
    @ExcelProperty(value = "收入 (人民币)", converter = CustomBigDecimalConverter.class)
    private BigDecimal incomeCny;

    /** 头程运费 */
    @Excel(name = "头程运费")
    @ExcelProperty(value = "头程运费", converter = CustomBigDecimalConverter.class)
    private BigDecimal firstLegShippingCost;

    /** 头程报关费 */
    @Excel(name = "头程报关费")
    @ExcelProperty(value = "头程报关费", converter = CustomBigDecimalConverter.class)
    private BigDecimal firstLegCustomsFee;

    /** 头程其它费 */
    @Excel(name = "头程其它费")
    @ExcelProperty(value = "头程其它费", converter = CustomBigDecimalConverter.class)
    private BigDecimal firstLegOtherFee;

    /** 包装材料 */
    @Excel(name = "包装材料")
    @ExcelProperty(value = "包装材料", converter = CustomStringConverter.class)
    private String packagingMaterial;

    /** 包装费用 */
    @Excel(name = "包装费用")
    @ExcelProperty(value = "包装费用", converter = CustomBigDecimalConverter.class)
    private BigDecimal packagingCost;

    /** 通途运费 */
    @Excel(name = "通途运费")
    @ExcelProperty(value = "通途运费", converter = CustomBigDecimalConverter.class)
    private BigDecimal tongtuShippingCost;

    /** 物流商运费 */
    @Excel(name = "物流商运费")
    @ExcelProperty(value = "物流商运费", converter = CustomBigDecimalConverter.class)
    private BigDecimal logisticsShippingCost;

    /** 订单备注 */
    @Excel(name = "订单备注")
    @ExcelProperty(value = "订单备注", converter = CustomStringConverter.class)
    private String orderNotes;

    /** 订单号-整理 */
    @Excel(name = "订单号-整理")
    @ExcelProperty(value = "订单号-整理", converter = CustomStringConverter.class)
    private String sortedOrderNumber;

    /** 头程费用 */
    @Excel(name = "头程费用")
    @ExcelProperty(value = "头程费用", converter = CustomBigDecimalConverter.class)
    private BigDecimal firstLegCost;

    /** 退款金额 */
    @Excel(name = "退款金额")
    @ExcelProperty(value = "退款金额", converter = CustomBigDecimalConverter.class)
    private BigDecimal refundAmount;

    /** 补发费用 */
    @Excel(name = "补发费用")
    @ExcelProperty(value = "补发费用", converter = CustomBigDecimalConverter.class)
    private BigDecimal replenishmentCost;

    /** 其他费用 */
    @Excel(name = "其他费用")
    @ExcelProperty(value = "其他费用", converter = CustomBigDecimalConverter.class)
    private BigDecimal otherCosts;

    /** 后台退款运费补贴/费用 */
    @Excel(name = "后台退款运费补贴/费用")
    @ExcelProperty(value = "后台退款运费补贴/费用", converter = CustomBigDecimalConverter.class)
    private BigDecimal backendRefundSubsidy;

    /** 广告费 */
    @Excel(name = "广告费")
    @ExcelProperty(value = "广告费", converter = CustomBigDecimalConverter.class)
    private BigDecimal advertisingFee;

    /** 清货补贴 */
    @Excel(name = "清货补贴")
    @ExcelProperty(value = "清货补贴", converter = CustomBigDecimalConverter.class)
    private BigDecimal clearanceSubsidy;

    /** 美国平台面单费用 */
    @Excel(name = "美国平台面单费用")
    @ExcelProperty(value = "美国平台面单费用", converter = CustomBigDecimalConverter.class)
    private BigDecimal usPlatformLabelFee;

    /** 比例 */
    @Excel(name = "比例")
    @ExcelProperty(value = "比例", converter = CustomBigDecimalConverter.class)
    private BigDecimal ratio;

    /** 毛利 */
    @Excel(name = "毛利")
    @ExcelProperty(value = "毛利", converter = CustomBigDecimalConverter.class)
    private BigDecimal grossProfit;

    /** 毛利率 */
    @Excel(name = "毛利率")
    @ExcelProperty(value = "毛利率", converter = CustomBigDecimalConverter.class)
    private BigDecimal grossProfitRate;

    /** 分销佣金 */
    @Excel(name = "分销佣金")
    @ExcelProperty(value = "分销佣金", converter = CustomBigDecimalConverter.class)
    private BigDecimal distributionCommission;

    /** TD实际毛利 */
    @Excel(name = "TD实际毛利")
    @ExcelProperty(value = "TD实际毛利", converter = CustomBigDecimalConverter.class)
    private BigDecimal tdActualGrossProfit;

    /** TD实际毛利率 */
    @Excel(name = "TD实际毛利率")
    @ExcelProperty(value = "TD实际毛利率", converter = CustomBigDecimalConverter.class)
    private BigDecimal tdActualGrossProfitRate;

    /** 运营 */
    @Excel(name = "运营")
    @ExcelProperty(value = "运营", converter = CustomStringConverter.class)
    private String operator;

    /** 开发员 */
    @Excel(name = "开发员")
    @ExcelProperty(value = "开发员", converter = CustomStringConverter.class)
    private String developer;

    /** 订单数量 */
    @Excel(name = "订单数量")
    @ExcelProperty(value = "订单数量")
    private Integer orderQuantity;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    private Date createdAt;

    /** 更新时间 */
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    private Date updateAt;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TEMUOrderDetails that = (TEMUOrderDetails) o;
        return ((TEMUOrderDetails) o).getShipmentDate() == (that.shipmentDate) &&
                Objects.equals(StringUtils.isEmpty(isReplenishment) ? null : isReplenishment, StringUtils.isEmpty(that.isReplenishment) ? null : that.isReplenishment) &&
                Objects.equals(StringUtils.isEmpty(tongtuSku) ? null : tongtuSku, StringUtils.isEmpty(that.tongtuSku) ? null : that.tongtuSku) &&
                Objects.equals(StringUtils.isEmpty(productName) ? null : productName, StringUtils.isEmpty(that.productName) ? null : that.productName) &&
                Objects.equals(StringUtils.isEmpty(category) ? null : category, StringUtils.isEmpty(that.category) ? null : that.category) &&
                Objects.equals(shipmentQuantity, that.shipmentQuantity) &&
                Objects.equals(StringUtils.isEmpty(channel) ? null : channel, StringUtils.isEmpty(that.channel) ? null : that.channel) &&
                Objects.equals(StringUtils.isEmpty(channelAccount) ? null : channelAccount, StringUtils.isEmpty(that.channelAccount) ? null : that.channelAccount) &&
                Objects.equals(StringUtils.isEmpty(salesSite) ? null : salesSite, StringUtils.isEmpty(that.salesSite) ? null : that.salesSite) &&
                Objects.equals(StringUtils.isEmpty(shipmentWarehouse) ? null : shipmentWarehouse, StringUtils.isEmpty(that.shipmentWarehouse) ? null : that.shipmentWarehouse) &&
                Objects.equals(StringUtils.isEmpty(packageNumber) ? null : packageNumber, StringUtils.isEmpty(that.packageNumber) ? null : that.packageNumber) &&
                Objects.equals(StringUtils.isEmpty(shippingMethod) ? null : shippingMethod, StringUtils.isEmpty(that.shippingMethod) ? null : that.shippingMethod) &&
                Objects.equals(StringUtils.isEmpty(logisticsNumber) ? null : logisticsNumber, StringUtils.isEmpty(that.logisticsNumber) ? null : that.logisticsNumber) &&
                Objects.equals(StringUtils.isEmpty(orderNumber) ? null : orderNumber, StringUtils.isEmpty(that.orderNumber) ? null : that.orderNumber) &&
                Objects.equals(StringUtils.isEmpty(buyerName) ? null : buyerName, StringUtils.isEmpty(that.buyerName) ? null : that.buyerName) &&
                Objects.equals(StringUtils.isEmpty(countryRegion) ? null : countryRegion, StringUtils.isEmpty(that.countryRegion) ? null : that.countryRegion) &&
                ((TEMUOrderDetails) o).getPaymentDate() == (that.paymentDate) &&
                Objects.equals(StringUtils.isEmpty(productDeveloper) ? null : productDeveloper, StringUtils.isEmpty(that.productDeveloper) ? null : that.productDeveloper) &&
                Objects.equals(StringUtils.isEmpty(receiptCurrency) ? null : receiptCurrency, StringUtils.isEmpty(that.receiptCurrency) ? null : that.receiptCurrency) &&
                compareBigDecimal(totalOrderPrice, that.totalOrderPrice) &&
                compareBigDecimal(totalOrderPriceCny, that.totalOrderPriceCny) &&
                compareBigDecimal(sellingPrice, that.sellingPrice) &&
                compareBigDecimal(sellingPriceCny, that.sellingPriceCny) &&
                compareBigDecimal(productCost, that.productCost) &&
                compareBigDecimal(channelFeeCny, that.channelFeeCny) &&
                compareBigDecimal(incomeCny, that.incomeCny) &&
                compareBigDecimal(firstLegShippingCost, that.firstLegShippingCost) &&
                compareBigDecimal(firstLegCustomsFee, that.firstLegCustomsFee) &&
                compareBigDecimal(firstLegOtherFee, that.firstLegOtherFee) &&
                Objects.equals(StringUtils.isEmpty(packagingMaterial) ? null : packagingMaterial, StringUtils.isEmpty(that.packagingMaterial) ? null : that.packagingMaterial) &&
                compareBigDecimal(packagingCost, that.packagingCost) &&
                compareBigDecimal(tongtuShippingCost, that.tongtuShippingCost) &&
                compareBigDecimal(logisticsShippingCost, that.logisticsShippingCost) &&
                Objects.equals(StringUtils.isEmpty(orderNotes) ? null : orderNotes, StringUtils.isEmpty(that.orderNotes) ? null : that.orderNotes) &&
                Objects.equals(StringUtils.isEmpty(sortedOrderNumber) ? null : sortedOrderNumber, StringUtils.isEmpty(that.sortedOrderNumber) ? null : that.sortedOrderNumber) &&
                compareBigDecimal(firstLegCost, that.firstLegCost) &&
                compareBigDecimal(refundAmount, that.refundAmount) &&
                compareBigDecimal(replenishmentCost, that.replenishmentCost) &&
                compareBigDecimal(otherCosts, that.otherCosts) &&
                compareBigDecimal(backendRefundSubsidy, that.backendRefundSubsidy) &&
                compareBigDecimal(advertisingFee, that.advertisingFee) &&
                compareBigDecimal(clearanceSubsidy, that.clearanceSubsidy) &&
                compareBigDecimal(usPlatformLabelFee, that.usPlatformLabelFee) &&
                compareBigDecimal(ratio, that.ratio) &&
                compareBigDecimal(grossProfit, that.grossProfit) &&
                compareBigDecimal(grossProfitRate, that.grossProfitRate) &&
                compareBigDecimal(distributionCommission, that.distributionCommission) &&
                compareBigDecimal(tdActualGrossProfit, that.tdActualGrossProfit) &&
                compareBigDecimal(tdActualGrossProfitRate, that.tdActualGrossProfitRate) &&
                Objects.equals(StringUtils.isEmpty(operator) ? null : operator, StringUtils.isEmpty(that.operator) ? null : that.operator) &&
                Objects.equals(StringUtils.isEmpty(developer) ? null : developer, StringUtils.isEmpty(that.developer) ? null : that.developer) &&
                Objects.equals(orderQuantity, that.orderQuantity) &&
                Objects.equals(StringUtils.isEmpty(remarks) ? null : remarks, StringUtils.isEmpty(that.remarks) ? null : that.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shipmentDate, isReplenishment, tongtuSku, productName, category, shipmentQuantity, channel, channelAccount, salesSite, shipmentWarehouse, packageNumber, shippingMethod, logisticsNumber, orderNumber, buyerName, countryRegion, paymentDate, productDeveloper, receiptCurrency, totalOrderPrice, totalOrderPriceCny, sellingPrice, sellingPriceCny, productCost, channelFeeCny, incomeCny, firstLegShippingCost, firstLegCustomsFee, firstLegOtherFee, packagingMaterial, packagingCost, tongtuShippingCost, logisticsShippingCost, orderNotes, sortedOrderNumber, firstLegCost, refundAmount, replenishmentCost, otherCosts, backendRefundSubsidy, advertisingFee, clearanceSubsidy, usPlatformLabelFee, ratio, grossProfit, grossProfitRate, distributionCommission, tdActualGrossProfit, tdActualGrossProfitRate, operator, developer, orderQuantity, createdAt, updateAt, remarks);
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
                .append("shipmentDate", getShipmentDate())
                .append("isReplenishment", getIsReplenishment())
                .append("tongtuSku", getTongtuSku())
                .append("productName", getProductName())
                .append("category", getCategory())
                .append("shipmentQuantity", getShipmentQuantity())
                .append("channel", getChannel())
                .append("channelAccount", getChannelAccount())
                .append("salesSite", getSalesSite())
                .append("shipmentWarehouse", getShipmentWarehouse())
                .append("packageNumber", getPackageNumber())
                .append("shippingMethod", getShippingMethod())
                .append("logisticsNumber", getLogisticsNumber())
                .append("orderNumber", getOrderNumber())
                .append("buyerName", getBuyerName())
                .append("countryRegion", getCountryRegion())
                .append("paymentDate", getPaymentDate())
                .append("productDeveloper", getProductDeveloper())
                .append("receiptCurrency", getReceiptCurrency())
                .append("totalOrderPrice", getTotalOrderPrice())
                .append("totalOrderPriceCny", getTotalOrderPriceCny())
                .append("sellingPrice", getSellingPrice())
                .append("sellingPriceCny", getSellingPriceCny())
                .append("productCost", getProductCost())
                .append("channelFeeCny", getChannelFeeCny())
                .append("incomeCny", getIncomeCny())
                .append("firstLegShippingCost", getFirstLegShippingCost())
                .append("firstLegCustomsFee", getFirstLegCustomsFee())
                .append("firstLegOtherFee", getFirstLegOtherFee())
                .append("packagingMaterial", getPackagingMaterial())
                .append("packagingCost", getPackagingCost())
                .append("tongtuShippingCost", getTongtuShippingCost())
                .append("logisticsShippingCost", getLogisticsShippingCost())
                .append("orderNotes", getOrderNotes())
                .append("sortedOrderNumber", getSortedOrderNumber())
                .append("firstLegCost", getFirstLegCost())
                .append("refundAmount", getRefundAmount())
                .append("replenishmentCost", getReplenishmentCost())
                .append("otherCosts", getOtherCosts())
                .append("backendRefundSubsidy", getBackendRefundSubsidy())
                .append("advertisingFee", getAdvertisingFee())
                .append("clearanceSubsidy", getClearanceSubsidy())
                .append("usPlatformLabelFee", getUsPlatformLabelFee())
                .append("ratio", getRatio())
                .append("grossProfit", getGrossProfit())
                .append("grossProfitRate", getGrossProfitRate())
                .append("distributionCommission", getDistributionCommission())
                .append("tdActualGrossProfit", getTdActualGrossProfit())
                .append("tdActualGrossProfitRate", getTdActualGrossProfitRate())
                .append("operator", getOperator())
                .append("developer", getDeveloper())
                .append("orderQuantity", getOrderQuantity())
                .append("remarks", getRemarks())
                .toString();
    }
}
