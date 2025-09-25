package com.ruoyi.sales.service;

import java.util.List;
import com.ruoyi.sales.domain.TEMUOrderDetails;

/**
 * Temu订单明细Service接口
 *
 * @author ZMJ
 * @date 2025-09-22
 */
public interface ITEMUOrderDetailsService
{
    /**
     * 查询Temu订单明细
     *
     * @param orderNumber Temu订单明细主键
     * @return Temu订单明细
     */
    public TEMUOrderDetails selectTEMUOrderDetailsByOrderNumber(String orderNumber);

    /**
     * 查询Temu订单明细列表
     *
     * @param tEMUOrderDetails Temu订单明细
     * @return Temu订单明细集合
     */
    public List<TEMUOrderDetails> selectTEMUOrderDetailsList(TEMUOrderDetails tEMUOrderDetails);

    /**
     * 新增Temu订单明细
     *
     * @param tEMUOrderDetails Temu订单明细
     * @return 结果
     */
    public int insertTEMUOrderDetails(TEMUOrderDetails tEMUOrderDetails);

    /**
     * 修改Temu订单明细
     *
     * @param tEMUOrderDetails Temu订单明细
     * @return 结果
     */
    public int updateTEMUOrderDetails(TEMUOrderDetails tEMUOrderDetails);

    /**
     * 批量删除Temu订单明细
     *
     * @param orderNumbers 需要删除的Temu订单明细主键集合
     * @return 结果
     */
    public int deleteTEMUOrderDetailsByOrderNumbers(String[] orderNumbers);

    /**
     * 删除Temu订单明细信息
     *
     * @param orderNumber Temu订单明细主键
     * @return 结果
     */
    public int deleteTEMUOrderDetailsByOrderNumber(String orderNumber);

    /**
     * 导入Temu数据
     *
     * @param temuDataList 待导入的数据列表
     * @param updateSupport 是否更新已经存在的数据
     * @param operName 操作人员
     * @return 结果
     */
    String importTEMUData(List<TEMUOrderDetails> temuDataList, String operName);
}
