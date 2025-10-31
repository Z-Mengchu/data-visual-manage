package com.ruoyi.sales.service.impl;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.sales.domain.ChannelSalesData;
import com.ruoyi.sales.domain.TEMUOrderDetails;
import com.ruoyi.sales.mapper.ChannelSalesDataMapper;
import com.ruoyi.sales.service.IChannelSalesDataService;
import com.ruoyi.system.domain.SysPost;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 全渠道销售数据分析Service业务层处理
 *
 * @author ZMJ
 * @date 2025-09-03
 */
@Service
public class ChannelSalesDataServiceImpl implements IChannelSalesDataService
{
    private static final Logger log = LoggerFactory.getLogger(ChannelSalesDataServiceImpl.class);

    @Autowired
    private ChannelSalesDataMapper channelSalesDataMapper;

    @Autowired
    protected Validator validator;

    /**
     * 查询全渠道销售数据分析
     *
     * @param id 全渠道销售数据分析主键
     * @return 全渠道销售数据分析
     */
    @Override
    public ChannelSalesData selectChannelSalesDataById(Integer id)
    {
        return channelSalesDataMapper.selectChannelSalesDataById(id);
    }

    /**
     * 查询全渠道销售数据分析列表
     *
     * @param channelSalesData 全渠道销售数据分析
     * @return 全渠道销售数据分析
     */
    @Override
    public List<ChannelSalesData> selectChannelSalesDataList(ChannelSalesData channelSalesData, SysUser currentUser, List<SysPost> userPosts)
    {
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).toList();
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return channelSalesDataMapper.selectChannelSalesDataListWithFilter(channelSalesData, currentUser, postCodes, roleKeys);
       /* if (roleKeys.contains(RoleConstants.ROLE_FINANCE)){
            return channelSalesDataMapper.selectChannelSalesDataListWithFilter(channelSalesData, currentUser, postCodes);
        }
        return channelSalesDataMapper.selectChannelSalesDataList(channelSalesData);*/
    }

    /**
     * 新增全渠道销售数据分析
     *
     * @param channelSalesData 全渠道销售数据分析
     * @return 结果
     */
    @Override
    public int insertChannelSalesData(ChannelSalesData channelSalesData)
    {
        return channelSalesDataMapper.insertChannelSalesData(channelSalesData);
    }

    /**
     * 修改全渠道销售数据分析
     *
     * @param channelSalesData 全渠道销售数据分析
     * @return 结果
     */
    @Override
    public int updateChannelSalesData(ChannelSalesData channelSalesData)
    {
        return channelSalesDataMapper.updateChannelSalesData(channelSalesData);
    }

    /**
     * 批量删除全渠道销售数据分析
     *
     * @param ids 需要删除的全渠道销售数据分析主键
     * @return 结果
     */
    @Override
    public int deleteChannelSalesDataByIds(Integer[] ids)
    {
        return channelSalesDataMapper.deleteChannelSalesDataByIds(ids);
    }

    /**
     * 删除全渠道销售数据分析信息
     *
     * @param id 全渠道销售数据分析主键
     * @return 结果
     */
    @Override
    public int deleteChannelSalesDataById(Integer id)
    {
        return channelSalesDataMapper.deleteChannelSalesDataById(id);
    }

    /**
     * 导入数据
     *
     * @param salesDataList 数据列表
     * @param operName 操作人员
     * @return 结果
     */
    @Override
    public String importSalesData(List<ChannelSalesData> salesDataList, String operName) {
        if (StringUtils.isNull(salesDataList) || salesDataList.isEmpty())
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (ChannelSalesData salesData : salesDataList)
        {
            try
            {
                // 验证是否存在这个数据
                BeanValidators.validateWithException(validator, salesData);
                salesData.setUpdateBy(operName);

                // 判断这条数据在数据库中是否已经存在完全相同的数据
                // 先通过相同订单号查询数据
                if (salesData.getOrderNumber().isEmpty()) salesData.setOrderNumber(null);
                List<ChannelSalesData> willEqualDataList = channelSalesDataMapper.selectChannelSalesDataByOrderNumber(salesData.getOrderNumber());

                boolean isExist = false;
                // 再通过重写equal方法比较两者是否完全相同
                for (ChannelSalesData channelSalesData : willEqualDataList) {
                    if (channelSalesData.equals(salesData)) {
                        isExist = true;
                        break;
                    }
                }
                willEqualDataList.clear();
                if (isExist) {
                    // 数据已存在进行提示
                    failureNum++;
                    String msg = "<br/>第 " + (failureNum + successNum) + "条数据已存在，请勿重复导入";
                    failureMsg.append(msg);
                }else {
                    // 数据不存在进行插入
                    channelSalesDataMapper.insertChannelSalesData(salesData);
                    successNum++;
                    successMsg.append("<br/>第 ").append(failureNum + successNum).append(" 条数据导入成功");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>第 " + (failureNum + successNum) + "条数据导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        if (failureNum > 0)
        {
            if (successNum == 0) successMsg.delete(0, successMsg.length());
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(successMsg + "\n" + failureMsg);
        }
        return successMsg.toString();
    }
}
