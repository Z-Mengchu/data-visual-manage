package com.ruoyi.dataAnalysis.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.dataAnalysis.mapper.ChannelSalesDataMapper;
import com.ruoyi.dataAnalysis.domain.ChannelSalesData;
import com.ruoyi.dataAnalysis.service.IChannelSalesDataService;

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
    public List<ChannelSalesData> selectChannelSalesDataList(ChannelSalesData channelSalesData)
    {
        return channelSalesDataMapper.selectChannelSalesDataList(channelSalesData);
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
     * @param updateSupport 是否更新
     * @param operName 操作人员
     * @return 结果
     */
    @Override
    public String importSalesData(List<ChannelSalesData> salesDataList, boolean updateSupport, String operName) {
        if (StringUtils.isNull(salesDataList) || salesDataList.size() == 0)
        {
            throw new ServiceException("导入用户数据不能为空！");
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
                ChannelSalesData data = null;
                if (!StringUtils.isEmpty(salesData.getOrderNumber())){
                    data = channelSalesDataMapper.selectChannelSalesDataByOrderNumber(salesData.getOrderNumber());
                }
                if (StringUtils.isNull(data))
                {
                    BeanValidators.validateWithException(validator, salesData);
                    salesData.setUpdateBy(operName);
                    channelSalesDataMapper.insertChannelSalesData(salesData);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、订单号 " + salesData.getOrderNumber() + " 导入成功");
                }
                else if (updateSupport)
                {
                    BeanValidators.validateWithException(validator, salesData);
                    salesData.setUpdateBy(operName);
                    salesData.setId(data.getId());
                    channelSalesDataMapper.updateChannelSalesData(salesData);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、订单号 " + salesData.getOrderNumber() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、订单号 " + salesData.getOrderNumber() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、订单号 " + salesData.getOrderNumber() + " 导入失败：";
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
