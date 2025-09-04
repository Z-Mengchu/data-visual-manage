package com.ruoyi.dataAnalysis.service;

import com.ruoyi.dataAnalysis.domain.ChannelSalesData;
import java.util.List;


/**
 * 全渠道销售数据分析Service接口
 *
 * @author ZMJ
 * @date 2025-09-02
 */
public interface IChannelSalesDataService
{
    /**
     * 查询全渠道销售数据分析
     *
     * @param id 全渠道销售数据分析主键
     * @return 全渠道销售数据分析
     */
    public ChannelSalesData selectChannelSalesDataById(Integer id);

    /**
     * 查询全渠道销售数据分析列表
     *
     * @param channelSalesData 全渠道销售数据分析
     * @return 全渠道销售数据分析集合
     */
    public List<ChannelSalesData> selectChannelSalesDataList(ChannelSalesData channelSalesData);

    /**
     * 新增全渠道销售数据分析
     *
     * @param channelSalesData 全渠道销售数据分析
     * @return 结果
     */
    public int insertChannelSalesData(ChannelSalesData channelSalesData);

    /**
     * 修改全渠道销售数据分析
     *
     * @param channelSalesData 全渠道销售数据分析
     * @return 结果
     */
    public int updateChannelSalesData(ChannelSalesData channelSalesData);

    /**
     * 批量删除全渠道销售数据分析
     *
     * @param ids 需要删除的全渠道销售数据分析主键集合
     * @return 结果
     */
    public int deleteChannelSalesDataByIds(Integer[] ids);

    /**
     * 删除全渠道销售数据分析信息
     *
     * @param id 全渠道销售数据分析主键
     * @return 结果
     */
    public int deleteChannelSalesDataById(Integer id);

    /**
     * 导入销售数据
     *
     * @param salesDataList 销售数据列表
     * @param updateSupport 是否更新已经存在的销售数据
     * @param operName 操作用户
     * @return 结果
     */
    String importSalesData(List<ChannelSalesData> salesDataList, boolean updateSupport, String operName);
}
