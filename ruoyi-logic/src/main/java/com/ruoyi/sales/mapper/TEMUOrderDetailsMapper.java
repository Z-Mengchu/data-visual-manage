package com.ruoyi.sales.mapper;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.sales.domain.TEMUOrderDetails;
import org.apache.ibatis.annotations.Param;

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
    public TEMUOrderDetails selectTEMUOrderDetailsById(Integer id);

    /**
     * 查询Temu订单明细列表
     *
     * @param tEMUOrderDetails Temu订单明细
     * @return Temu订单明细集合
     */
    public List<TEMUOrderDetails> selectTEMUOrderDetailsList(TEMUOrderDetails tEMUOrderDetails);

    /**
     * 查询Temu订单明细列表
     *
     * @param orderNumber 订单号
     * @return Temu订单明细集合
     */
    public List<TEMUOrderDetails> selectTEMUOrderDetailsByOrderNumber(@Param("orderNumber") String orderNumber);

    /**
     * 查询Temu订单明细列表（带数据权限）
     *
     * @param tEMUOrderDetails Temu订单明细
     * @param user 当前用户
     * @param postCodes 用户岗位编码列表
     * @return Temu订单明细集合
     */
    List<TEMUOrderDetails> selectTEMUOrderDetailsListWithPermission(@Param("data") TEMUOrderDetails tEMUOrderDetails,
                                                                    @Param("user") SysUser user,
                                                                    @Param("postCodes") List<String> postCodes,
                                                                    @Param("roleKeys") List<String> roleKeys);

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
