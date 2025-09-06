package com.ruoyi.purchase.domain;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 采购账期对象 purchase_payment_period
 *
 * @author ZMJ
 * @date 2025-09-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchasePaymentPeriod extends BaseEntity
{
    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Integer id;

    /** 采购单号 */
    @Excel(name = "采购单号", needMerge = true)
    private String purchaseOrderNumber;

    /** 采购计划编号 */
    @Excel(name = "采购计划编号", needMerge = true)
    private String purchasePlanNumber;

    /** 1688账号 */
    @Excel(name = "1688账号", needMerge = true)
    private String account1688;

    /** 采购仓库 */
    @Excel(name = "采购仓库")
    private String purchaseWarehouse;

    /** 采购员 */
    @Excel(name = "采购员")
    private String purchaser;

    /** 采购日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "采购日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date purchaseDate;

    /** 图片存放url */
    @Excel(name = "图片", cellType = Excel.ColumnType.IMAGE)
    private String imageUrl;

    /** 货位 */
    @Excel(name = "货位")
    private String location;

    /** SKU */
    @Excel(name = "SKU")
    private String sku;

    /** 货品名称 */
    @Excel(name = "货品名称")
    private String productName;

    /** 规格 */
    @Excel(name = "规格")
    private String specifications;

    /** 采购单价 */
    @Excel(name = "采购单价")
    private BigDecimal purchaseUnitPrice;

    /** 采购数量 */
    @Excel(name = "采购数量")
    private Integer purchaseQuantity;

    /** 货款 */
    @Excel(name = "货款", needMerge = true)
    private BigDecimal goodsPayment;

    /** 供应商名称 */
    @Excel(name = "供应商名称", needMerge = true)
    private String supplierName;

    /** 到货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date arrivalDate;

    /** 采购备注 */
    @Excel(name = "采购备注")
    private String purchaseNotes;

    /** 账期天数 */
    @Excel(name = "账期天数")
    private Integer paymentPeriodDays;

    /** 到期付款时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期付款时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date duePaymentDate;

    /** 采购部门 */
    @Excel(name = "采购部门")
    private String purchaseDepartment;

    /** 是否已付款(0否 1是) */
    @Excel(name = "是否已付款", readConverterExp = "0=否,1=是")
    private String isPaid;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

}
