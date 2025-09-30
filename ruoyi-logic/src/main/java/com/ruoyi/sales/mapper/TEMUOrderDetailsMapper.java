package com.ruoyi.sales.mapper;

import java.util.List;
import com.ruoyi.sales.domain.TEMUOrderDetails;

/**
 * Temu订单明细Mapper接口
 *
 * @author ZMJ
 * @date 2025-09-22
 */
public interface TEMUOrderDetailsMapper
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
     * 删除Temu订单明细
     *
     * @param orderNumber Temu订单明细主键
     * @return 结果
     */
    public int deleteTEMUOrderDetailsById(Integer id);

    /**
     * 批量删除Temu订单明细
     *
     * @param orderNumbers 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTEMUOrderDetailsByIds(Integer[] ids);
}
