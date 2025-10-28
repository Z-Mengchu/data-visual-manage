package com.ruoyi.purchase.mapper;

import java.util.List;
import com.ruoyi.purchase.domain.PurchasePaymentPeriod;

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
}
