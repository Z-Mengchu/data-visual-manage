package com.ruoyi.purchase.domain;

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

import java.io.Serial;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 采购账期对象 purchase_payment_period
 *
 * @author ZMJ
 * @date 2025-09-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class PurchasePaymentPeriod extends BaseEntity
{
    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
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

    /** 图片存放url */
    @Excel(name = "图片", cellType = Excel.ColumnType.IMAGE)
    @ExcelProperty(value = "图片")
    private String imageUrl;

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

    // 计算字段
    @Excel(name = "是否逾期", readConverterExp = "0=否,1=是", type = Excel.Type.EXPORT)
    @ExcelProperty(value = "是否逾期", converter = BooleanStringExportConverter.class)
    private String isOverdue; // 是否已逾期

    @Excel(name = "距到期天数", type = Excel.Type.EXPORT)
    @ExcelProperty(value = "距到期天数")
    private Integer daysUntilDue; // 距到期天数

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchasePaymentPeriod that = (PurchasePaymentPeriod) o;
        return Objects.equals(StringUtils.isEmpty(purchaseOrderNumber) ? null : purchaseOrderNumber, StringUtils.isEmpty(that.purchaseOrderNumber) ? null : that.purchaseOrderNumber) &&
                Objects.equals(StringUtils.isEmpty(purchasePlanNumber) ? null : purchasePlanNumber, StringUtils.isEmpty(that.purchasePlanNumber) ? null : that.purchasePlanNumber) &&
                Objects.equals(StringUtils.isEmpty(account1688) ? null : account1688, StringUtils.isEmpty(that.account1688) ? null : that.account1688) &&
                Objects.equals(StringUtils.isEmpty(purchaseWarehouse) ? null : purchaseWarehouse, StringUtils.isEmpty(that.purchaseWarehouse) ? null : that.purchaseWarehouse) &&
                Objects.equals(StringUtils.isEmpty(purchaser) ? null : purchaser, StringUtils.isEmpty(that.purchaser) ? null : that.purchaser) &&
                ((PurchasePaymentPeriod) o).getPurchaseDate() == (that.purchaseDate) &&
                Objects.equals(StringUtils.isEmpty(location) ? null : location, StringUtils.isEmpty(that.location) ? null : that.location) &&
                Objects.equals(StringUtils.isEmpty(sku) ? null : sku, StringUtils.isEmpty(that.sku) ? null : that.sku) &&
                Objects.equals(StringUtils.isEmpty(productName) ? null : productName, StringUtils.isEmpty(that.productName) ? null : that.productName) &&
                Objects.equals(StringUtils.isEmpty(specifications) ? null : specifications, StringUtils.isEmpty(that.specifications) ? null : that.specifications) &&
                compareBigDecimal(purchaseUnitPrice, that.purchaseUnitPrice) &&
                Objects.equals(purchaseQuantity, that.purchaseQuantity) &&
                compareBigDecimal(goodsPayment, that.goodsPayment) &&
                Objects.equals(StringUtils.isEmpty(supplierName) ? null : supplierName, StringUtils.isEmpty(that.supplierName) ? null : that.supplierName) &&
                ((PurchasePaymentPeriod) o).getArrivalDate() == (that.arrivalDate) &&
                Objects.equals(StringUtils.isEmpty(purchaseNotes) ? null : purchaseNotes, StringUtils.isEmpty(that.purchaseNotes) ? null : that.purchaseNotes) &&
                Objects.equals(paymentPeriodDays, that.paymentPeriodDays) &&
                ((PurchasePaymentPeriod) o).getDuePaymentDate() == (that.duePaymentDate) &&
                Objects.equals(StringUtils.isEmpty(purchaseDepartment) ? null : purchaseDepartment, StringUtils.isEmpty(that.purchaseDepartment) ? null : that.purchaseDepartment) &&
                Objects.equals(StringUtils.isEmpty(isPaid) ? null : isPaid, StringUtils.isEmpty(that.isPaid) ? null : that.isPaid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, purchaseOrderNumber, purchasePlanNumber, account1688, purchaseWarehouse, purchaser, purchaseDate, imageUrl, location, sku, productName, specifications, purchaseUnitPrice, purchaseQuantity, goodsPayment, supplierName, arrivalDate, purchaseNotes, paymentPeriodDays, duePaymentDate, purchaseDepartment, isPaid, isOverdue, daysUntilDue, createdAt, updatedAt);
    }

    private boolean compareBigDecimal(BigDecimal bd1, BigDecimal bd2) {
        if (bd1 == null && bd2 == null) return true;
        if (bd1 == null || bd2 == null) return false;
        return bd1.compareTo(bd2.setScale(bd1.scale(), RoundingMode.HALF_UP)) == 0;
    }

    /**
     * 计算是否逾期
     */
    public void calculateOverdueStatus() {
        if (duePaymentDate == null || "1".equals(isPaid)) {
            this.isOverdue = "0";
            this.daysUntilDue = null;
            return;
        }

        Date now = new Date();
        long diff = duePaymentDate.getTime() - now.getTime();
        long daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        this.daysUntilDue = Integer.valueOf(String.valueOf(daysDiff));
        this.isOverdue = daysDiff < 0 ? "1" : "0";
    }

}
