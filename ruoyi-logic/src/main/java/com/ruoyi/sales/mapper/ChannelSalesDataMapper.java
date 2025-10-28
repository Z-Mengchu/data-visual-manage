package com.ruoyi.sales.mapper;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.sales.domain.ChannelSalesData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 全渠道销售数据分析Mapper接口
 *
 * @author ZMJ
 * @date 2025-09-02
 */
public interface ChannelSalesDataMapper
{
    /**
     * 查询全渠道销售数据分析
     *
     * @param id 全渠道销售数据分析主键
     * @return 全渠道销售数据分析
     */
    public ChannelSalesData selectChannelSalesDataById(Integer id);

    /**
     * 查询全渠道销售数据分析列表过滤
     *
     * @param channelSalesData 全渠道销售数据分析
     * @return 全渠道销售数据分析集合
     */
    public List<ChannelSalesData> selectChannelSalesDataList(ChannelSalesData channelSalesData);

    /**
     * 查询全渠道销售数据分析列表过滤
     *
     * @param channelSalesData 全渠道销售数据分析
     * @return 全渠道销售数据分析集合
     */
    public List<ChannelSalesData> selectChannelSalesDataListWithFilter(@Param("data") ChannelSalesData channelSalesData,
                                                             @Param("user") SysUser user,
                                                             @Param("postCodes") List<String> postCodes,
                                                             @Param("roleKeys") List<String> roleKeys);

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
     * 删除全渠道销售数据分析
     *
     * @param id 全渠道销售数据分析主键
     * @return 结果
     */
    public int deleteChannelSalesDataById(Integer id);

    /**
     * 批量删除全渠道销售数据分析
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChannelSalesDataByIds(Integer[] ids);

    /**
     * 根据订单号查询数据
     */
    @Select("select * from channel_sales_data where order_number = #{orderNumber}")
    public ChannelSalesData selectChannelSalesDataByOrderNumber(String orderNumber);
}
