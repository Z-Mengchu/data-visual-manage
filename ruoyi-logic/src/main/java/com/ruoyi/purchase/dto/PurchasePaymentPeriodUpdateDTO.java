package com.ruoyi.purchase.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.converter.BooleanStringExportConverter;
import com.ruoyi.converter.CustomBigDecimalConverter;
import com.ruoyi.converter.CustomStringConverter;
import com.ruoyi.converter.ExcelDateConverter;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购账期更新DTO
 */
@Data
public class PurchasePaymentPeriodUpdateDTO {

    /** 主键ID */
    @Excel(name = "主键ID")
    @ExcelProperty(value = "主键ID")
    private Integer id;

    /** 采购单号 */
    @Excel(name = "采购单号", needMerge = true)
    @ExcelProperty(value = "采购单号", converter = CustomStringConverter.class)
    private String purchaseOrderNumber;

    /** 采购计划编号 */
    @Excel(name = "采购计划编号", needMerge = true)
    @ExcelProperty(value = "采购计划编号", converter = CustomStringConverter.class)
    private String purchasePlanNumber;

    /** 1688账号 */
    @Excel(name = "1688账号", needMerge = true)
    @ExcelProperty(value = "1688账号", converter = CustomStringConverter.class)
    private String account1688;

    /** 采购仓库 */
    @Excel(name = "采购仓库")
    @ExcelProperty(value = "采购仓库", converter = CustomStringConverter.class)
    private String purchaseWarehouse;

    /** 采购员 */
    @Excel(name = "采购员")
    @ExcelProperty(value = "采购员", converter = CustomStringConverter.class)
    private String purchaser;

    /** 采购日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "采购日期", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(value = "采购日期", converter = ExcelDateConverter.class)
    private Date purchaseDate;

    /** 货位 */
    @Excel(name = "货位")
    @ExcelProperty(value = "货位", converter = CustomStringConverter.class)
    private String location;

    /** SKU */
    @Excel(name = "SKU")
    @ExcelProperty(value = "SKU", converter = CustomStringConverter.class)
    private String sku;

    /** 货品名称 */
    @Excel(name = "货品名称")
    @ExcelProperty(value = "货品名称", converter = CustomStringConverter.class)
    private String productName;

    /** 规格 */
    @Excel(name = "规格")
    @ExcelProperty(value = "规格", converter = CustomStringConverter.class)
    private String specifications;

    /** 采购单价 */
    @Excel(name = "采购单价")
    @ExcelProperty(value = "采购单价", converter = CustomBigDecimalConverter.class)
    private BigDecimal purchaseUnitPrice;

    /** 采购数量 */
    @Excel(name = "采购数量")
    @ExcelProperty(value = "采购数量")
    private Integer purchaseQuantity;

    /** 货款 */
    @Excel(name = "货款", needMerge = true)
    @ExcelProperty(value = "货款", converter = CustomBigDecimalConverter.class)
    private BigDecimal goodsPayment;

    /** 运费 */
    @Excel(name = "运费")
    @ExcelProperty(value = "运费", converter = CustomBigDecimalConverter.class)
    private BigDecimal shippingCost;

    /** 采购单总金额 */
    @Excel(name = "采购单总金额")
    @ExcelProperty(value = "采购单总金额", converter = CustomBigDecimalConverter.class)
    private BigDecimal purchaseTotalAmount;

    /** 供应商名称 */
    @Excel(name = "供应商名称", needMerge = true)
    @ExcelProperty(value = "供应商名称", converter = CustomStringConverter.class)
    private String supplierName;

    /** 到货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到货日期", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(value = "到货日期", converter = ExcelDateConverter.class)
    private Date arrivalDate;

    /** 采购备注 */
    @Excel(name = "采购备注")
    @ExcelProperty(value = "采购备注", converter = CustomStringConverter.class)
    private String purchaseNotes;

    /** 账期天数 */
    @Excel(name = "账期天数")
    @ExcelProperty(value = "账期天数")
    private Integer paymentPeriodDays;

    /** 到期付款时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期付款时间", width = 30, dateFormat = "yyyy-MM-dd")
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(value = "到期付款时间", converter = ExcelDateConverter.class)
    private Date duePaymentDate;

    /** 采购部门 */
    @Excel(name = "采购部门")
    @ExcelProperty(value = "采购部门", converter = CustomStringConverter.class)
    private String purchaseDepartment;

    /** 是否已付款(0否 1是) */
    @Excel(name = "是否已付款", readConverterExp = "0=否,1=是")
    @ExcelProperty(value = "是否已付款", converter = BooleanStringExportConverter.class)
    private String isPaid;

}
