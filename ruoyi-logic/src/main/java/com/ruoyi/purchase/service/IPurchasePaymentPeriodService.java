package com.ruoyi.purchase.service;

import com.ruoyi.purchase.domain.PurchasePaymentPeriod;
import com.ruoyi.purchase.dto.PurchasePaymentPeriodUpdateDTO;

import java.util.List;
import java.util.Map;

/**
 * 采购账期Service接口
 *
 * @author ZMJ
 * @date 2025-09-04
 */
public interface IPurchasePaymentPeriodService
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
     * 批量删除采购账期
     *
     * @param ids 需要删除的采购账期主键集合
     * @return 结果
     */
    public int deletePurchasePaymentPeriodByIds(Integer[] ids);

    /**
     * 删除采购账期信息
     *
     * @param id 采购账期主键
     * @return 结果
     */
    public int deletePurchasePaymentPeriodById(Integer id);

    /**
     * 导入数据
     * @param purchasePaymentPeriodList
     * @param operName
     * @return
     */
    String importData(List<PurchasePaymentPeriod> purchasePaymentPeriodList, String operName);

    /**
     * 统计已付款和未付款数量
     * @param purchasePaymentPeriod 查询条件
     * @return 统计结果
     */
    Map<String, Integer> countPaymentStatus(PurchasePaymentPeriod purchasePaymentPeriod);

    /**
     * 批量更新数据
     * @param updateDTOList 更新数据DTO列表
     * @param originalIds 原始ID列表
     * @param operName 操作人
     * @return 操作结果消息
     */
    String batchUpdateData(List<PurchasePaymentPeriodUpdateDTO> updateDTOList, Integer[] originalIds, String operName);

    /**
     * 根据ID列表查询数据
     * @param ids ID列表
     * @return 查询结果列表
     */
    List<PurchasePaymentPeriod> selectPurchasePaymentPeriodByIds(Integer[] ids);
}
