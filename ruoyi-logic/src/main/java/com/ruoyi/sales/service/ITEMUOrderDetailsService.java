package com.ruoyi.sales.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.sales.domain.TEMUOrderDetails;
import com.ruoyi.system.domain.SysPost;

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
    public TEMUOrderDetails selectTEMUOrderDetailsById(Integer id);

    /**
     * 查询Temu订单明细列表
     *
     * @param tEMUOrderDetails Temu订单明细
     * @return Temu订单明细集合
     */
    public List<TEMUOrderDetails> selectTEMUOrderDetailsList(TEMUOrderDetails tEMUOrderDetails, SysUser currentUser, List<SysPost> userPosts);

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
     * @param ids 需要删除的Temu订单明细主键集合
     * @return 结果
     */
    public int deleteTEMUOrderDetailsByIds(Integer[] ids);

    /**
     * 删除Temu订单明细信息
     *
     * @param id Temu订单明细主键
     * @return 结果
     */
    public int deleteTEMUOrderDetailsById(Integer id);

    /**
     * 导入Temu数据
     *
     * @param temuDataList 待导入的数据列表
     * @param operName 操作人员
     * @return 结果
     */
    String importTEMUData(List<TEMUOrderDetails> temuDataList, String operName);

    /**
     * 查询Temu订单明细列表（带数据权限）
     *
     * @param tEMUOrderDetails Temu订单明细
     * @param user 当前用户
     * @param postCodes 用户岗位编码列表
     * @return Temu订单明细集合
     */
    List<TEMUOrderDetails> selectTEMUOrderDetailsListWithPermission(TEMUOrderDetails tEMUOrderDetails, SysUser user, List<String> postCodes, List<String> roleKeys);
}
