package com.ruoyi.sales.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.sales.domain.ChannelSalesData;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.ISysPostService;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.sales.mapper.TEMUOrderDetailsMapper;
import com.ruoyi.sales.domain.TEMUOrderDetails;
import com.ruoyi.sales.service.ITEMUOrderDetailsService;

/**
 * Temu订单明细Service业务层处理
 *
 * @author ZMJ
 * @date 2025-09-22
 */
@Service
public class TEMUOrderDetailsServiceImpl implements ITEMUOrderDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(TEMUOrderDetailsServiceImpl.class);

    @Autowired
    private TEMUOrderDetailsMapper TEMUOrderDetailsMapper;

    @Autowired
    protected Validator validator;

    /**
     * 查询Temu订单明细
     *
     * @param orderNumber Temu订单明细主键
     * @return Temu订单明细
     */
    @Override
    public TEMUOrderDetails selectTEMUOrderDetailsByOrderNumber(String orderNumber)
    {
        return TEMUOrderDetailsMapper.selectTEMUOrderDetailsByOrderNumber(orderNumber);
    }

    /**
     * 查询Temu订单明细列表
     *
     * @param tEMUOrderDetails Temu订单明细
     * @return Temu订单明细
     */
    @Override
    public List<TEMUOrderDetails> selectTEMUOrderDetailsList(TEMUOrderDetails tEMUOrderDetails, SysUser currentUser, List<SysPost> userPosts)
    {
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).toList();
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();

        return TEMUOrderDetailsMapper.selectTEMUOrderDetailsListWithPermission(tEMUOrderDetails, currentUser, postCodes, roleKeys);
    }

    /**
     * 查询Temu订单明细列表（带数据权限）
     *
     * @param tEMUOrderDetails Temu订单明细
     * @param user 当前用户
     * @param postCodes 用户岗位编码列表
     * @return Temu订单明细集合
     */
    @Override
    public List<TEMUOrderDetails> selectTEMUOrderDetailsListWithPermission(TEMUOrderDetails tEMUOrderDetails, SysUser user, List<String> postCodes, List<String> roleKeys) {
        return TEMUOrderDetailsMapper.selectTEMUOrderDetailsListWithPermission(tEMUOrderDetails, user, postCodes, roleKeys);
    }

    /**
     * 新增Temu订单明细
     *
     * @param tEMUOrderDetails Temu订单明细
     * @return 结果
     */
    @Override
    public int insertTEMUOrderDetails(TEMUOrderDetails tEMUOrderDetails)
    {
        return TEMUOrderDetailsMapper.insertTEMUOrderDetails(tEMUOrderDetails);
    }

    /**
     * 修改Temu订单明细
     *
     * @param tEMUOrderDetails Temu订单明细
     * @return 结果
     */
    @Override
    public int updateTEMUOrderDetails(TEMUOrderDetails tEMUOrderDetails)
    {
        return TEMUOrderDetailsMapper.updateTEMUOrderDetails(tEMUOrderDetails);
    }

    /**
     * 批量删除Temu订单明细
     *
     * @param ids 需要删除的Temu订单明细主键
     * @return 结果
     */
    @Override
    public int deleteTEMUOrderDetailsByIds(Integer[] ids)
    {
        return TEMUOrderDetailsMapper.deleteTEMUOrderDetailsByIds(ids);
    }

    /**
     * 删除Temu订单明细信息
     *
     * @param id Temu订单明细主键
     * @return 结果
     */
    @Override
    public int deleteTEMUOrderDetailsById(Integer id)
    {
        return TEMUOrderDetailsMapper.deleteTEMUOrderDetailsById(id);
    }

    /**
     * 导入数据
     *
     * @param temuDataList 数据列表
     * @param operName 操作人员
     * @return 结果
     */
    @Override
    public String importTEMUData(List<TEMUOrderDetails> temuDataList, String operName) {
        if (StringUtils.isNull(temuDataList) || temuDataList.isEmpty())
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TEMUOrderDetails temuData : temuDataList)
        {
            try
            {
                BeanValidators.validateWithException(validator, temuData);
                temuData.setUpdateBy(operName);
                TEMUOrderDetailsMapper.insertTEMUOrderDetails(temuData);
                successNum++;
                successMsg.append("<br/>" + successNum + "、订单号 " + temuData.getOrderNumber() + " 导入成功");
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>第 " + failureNum + "条数据导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

}
