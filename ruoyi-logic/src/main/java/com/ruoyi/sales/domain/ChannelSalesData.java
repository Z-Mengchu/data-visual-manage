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
import java.util.Date;

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
    @Excel(name = "付款日期/结算时间")
    private String paymentSettlementDate;

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

}
