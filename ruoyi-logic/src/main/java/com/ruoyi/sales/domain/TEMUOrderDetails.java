package com.ruoyi.sales.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Temu订单明细对象 temu_order_details
 *
 * @author ZMJ
 * @date 2025-09-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TEMUOrderDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Integer id;

    /** 发货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date shipmentDate;

    /** 是否补发货（0否 1是） */
    @Excel(name = "是否补发货", readConverterExp = "0=否,1=是")
    private String isReplenishment;

    /** 通途SKU */
    @Excel(name = "通途SKU")
    private String tongtuSku;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 品类 */
    @Excel(name = "品类")
    private String category;

    /** 发货数量 */
    @Excel(name = "发货数量")
    private Integer shipmentQuantity;

    /** 渠道 */
    @Excel(name = "渠道")
    private String channel;

    /** 渠道账号 */
    @Excel(name = "渠道账号")
    private String channelAccount;

    /** 销售站点 */
    @Excel(name = "销售站点")
    private String salesSite;

    /** 发货仓库 */
    @Excel(name = "发货仓库")
    private String shipmentWarehouse;

    /** 包裹号 */
    @Excel(name = "包裹号")
    private String packageNumber;

    /** 邮寄方式 */
    @Excel(name = "邮寄方式")
    private String shippingMethod;

    /** 物流商单号 */
    @Excel(name = "物流商单号")
    private String logisticsNumber;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNumber;

    /** 买家姓名 */
    @Excel(name = "买家姓名")
    private String buyerName;

    /** 国家 / 地区 */
    @Excel(name = "国家 / 地区")
    private String countryRegion;

    /** 付款日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "付款日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date paymentDate;

    /** 产品开发员 */
    @Excel(name = "产品开发员")
    private String productDeveloper;

    /** 收款币种 */
    @Excel(name = "收款币种")
    private String receiptCurrency;

    /** 订单总售价 */
    @Excel(name = "订单总售价")
    private BigDecimal totalOrderPrice;

    /** 订单总售价 (人民币) */
    @Excel(name = "订单总售价 (人民币)")
    private BigDecimal totalOrderPriceCny;

    /** 售价 */
    @Excel(name = "售价")
    private BigDecimal sellingPrice;

    /** 售价 (人民币) */
    @Excel(name = "售价 (人民币)")
    private BigDecimal sellingPriceCny;

    /** 商品成本 */
    @Excel(name = "商品成本")
    private BigDecimal productCost;

    /** 渠道成交费 (人民币) */
    @Excel(name = "渠道成交费 (人民币)")
    private BigDecimal channelFeeCny;

    /** 收入 (人民币) */
    @Excel(name = "收入 (人民币)")
    private BigDecimal incomeCny;

    /** 头程运费 */
    @Excel(name = "头程运费")
    private BigDecimal firstLegShippingCost;

    /** 头程报关费 */
    @Excel(name = "头程报关费")
    private BigDecimal firstLegCustomsFee;

    /** 头程其它费 */
    @Excel(name = "头程其它费")
    private BigDecimal firstLegOtherFee;

    /** 包装材料 */
    @Excel(name = "包装材料")
    private String packagingMaterial;

    /** 包装费用 */
    @Excel(name = "包装费用")
    private BigDecimal packagingCost;

    /** 通途运费 */
    @Excel(name = "通途运费")
    private BigDecimal tongtuShippingCost;

    /** 物流商运费 */
    @Excel(name = "物流商运费")
    private BigDecimal logisticsShippingCost;

    /** 订单备注 */
    @Excel(name = "订单备注")
    private String orderNotes;

    /** 订单号-整理 */
    @Excel(name = "订单号-整理")
    private String sortedOrderNumber;

    /** 头程费用 */
    @Excel(name = "头程费用")
    private BigDecimal firstLegCost;

    /** 退款金额 */
    @Excel(name = "退款金额")
    private BigDecimal refundAmount;

    /** 补发费用 */
    @Excel(name = "补发费用")
    private BigDecimal replenishmentCost;

    /** 其他费用 */
    @Excel(name = "其他费用")
    private BigDecimal otherCosts;

    /** 后台退款运费补贴/费用 */
    @Excel(name = "后台退款运费补贴/费用")
    private BigDecimal backendRefundSubsidy;

    /** 广告费 */
    @Excel(name = "广告费")
    private BigDecimal advertisingFee;

    /** 清货补贴 */
    @Excel(name = "清货补贴")
    private BigDecimal clearanceSubsidy;

    /** 美国平台面单费用 */
    @Excel(name = "美国平台面单费用")
    private BigDecimal usPlatformLabelFee;

    /** 比例 */
    @Excel(name = "比例")
    private BigDecimal ratio;

    /** 毛利 */
    @Excel(name = "毛利")
    private BigDecimal grossProfit;

    /** 毛利率 */
    @Excel(name = "毛利率")
    private BigDecimal grossProfitRate;

    /** 分销佣金 */
    @Excel(name = "分销佣金")
    private BigDecimal distributionCommission;

    /** TD实际毛利 */
    @Excel(name = "TD实际毛利")
    private BigDecimal tdActualGrossProfit;

    /** TD实际毛利率 */
    @Excel(name = "TD实际毛利率")
    private BigDecimal tdActualGrossProfitRate;

    /** 运营 */
    @Excel(name = "运营")
    private String operator;

    /** 开发员 */
    @Excel(name = "开发员")
    private String developer;

    /** 订单数量 */
    @Excel(name = "订单数量")
    private Integer orderQuantity;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private Date createdAt;

    /** 更新时间 */
    @Excel(name = "更新时间")
    private Date updateAt;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

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
