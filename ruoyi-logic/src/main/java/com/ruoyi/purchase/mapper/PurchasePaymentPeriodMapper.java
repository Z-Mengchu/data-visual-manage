package com.ruoyi.purchase.mapper;

import com.ruoyi.purchase.domain.PurchasePaymentPeriod;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 采购账期Mapper接口
 *
 * @author ZMJ
 * @date 2025-09-04
 */
public interface PurchasePaymentPeriodMapper
{
    /**
     * 查询采购账期
     *
     * @param id 采购账期主键
     * @return 采购账期
     */
    public PurchasePaymentPeriod selectPurchasePaymentPeriodById(Integer id);

    /**
     * 查询采购账期列表
     *
     * @param purchasePaymentPeriod 采购账期
     * @return 采购账期集合
     */
    public List<PurchasePaymentPeriod> selectPurchasePaymentPeriodList(PurchasePaymentPeriod purchasePaymentPeriod);

    /**
     * 根据采购单号哦查询采购账期列表
     *
     * @param purchaseOrderNumber 采购单号
     * @return 采购账期集合
     */
    public List<PurchasePaymentPeriod> selectPurchasePaymentPeriodByPurchaseOrderNumber(String purchaseOrderNumber);

    /**
     * 新增采购账期
     *
     * @param purchasePaymentPeriod 采购账期
     * @return 结果
     */
    public int insertPurchasePaymentPeriod(PurchasePaymentPeriod purchasePaymentPeriod);

    /**
     * 修改采购账期
     *
     * @param purchasePaymentPeriod 采购账期
     * @return 结果
     */
    public int updatePurchasePaymentPeriod(PurchasePaymentPeriod purchasePaymentPeriod);

    /**
     * 删除采购账期
     *
     * @param id 采购账期主键
     * @return 结果
     */
    public int deletePurchasePaymentPeriodById(Integer id);

    /**
     * 批量删除采购账期
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchasePaymentPeriodByIds(Integer[] ids);

    /**
     * 查询采购账期数量
     * @param purchasePaymentPeriod 采购账期
     * @return 数量
     */
    public int selectPurchasePaymentPeriodCount(PurchasePaymentPeriod purchasePaymentPeriod);

    /**
     * 查询未知状态采购账期数量（isPaid为null或空）
     * @param purchasePaymentPeriod 采购账期
     * @return 数量
     */
    public int selectPurchasePaymentPeriodCountUnknown(PurchasePaymentPeriod purchasePaymentPeriod);

    /**
     * 根据采购单号列表查询海外数据
     * @param purchaseOrderNumbers 采购单号集合
     * @return 海外数据列表
     */
    List<PurchasePaymentPeriod> selectPurchasePaymentPeriodByPurchaseOrderNumbers(@Param("purchaseOrderNumbers") Set<String> purchaseOrderNumbers);

    /**
     * 查询采购单号为null的海外数据
     * @return 订单号为null的海外数据列表
     */
    List<PurchasePaymentPeriod> selectPurchasePaymentPeriodByPurchaseOrderNumberIsNull();

    /**
     * 批量插入采购账期数据
     * @param list 采购账期数据列表
     * @return 插入的行数
     */
    int batchInsertPurchasePaymentPeriod(@Param("list") List<PurchasePaymentPeriod> list, @Param("operName") String operName);

    /**
     * 根据采购账期id列表查询采购账期数据
     * @param originalIds 采购账期id列表
     * @return 采购账期数据列表
     */
    List<PurchasePaymentPeriod> selectPurchasePaymentPeriodByIds(@Param("originalIds") Integer[] originalIds);
}
