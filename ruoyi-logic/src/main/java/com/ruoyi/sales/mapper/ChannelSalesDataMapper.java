package com.ruoyi.sales.mapper;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.sales.domain.ChannelSalesData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

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
     * 根据订单号查询
     *
     * @param orderNumber 订单号
     * @return 全渠道销售数据分析集合
     */
    public List<ChannelSalesData> selectChannelSalesDataByOrderNumber(@Param("orderNumber") String orderNumber);


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
     * 根据订单号列表查询全渠道销售数据
     * @param orderNumbers 订单号集合
     * @return 全渠道销售数据列表
     */
    List<ChannelSalesData> selectChannelSalesDataByOrderNumbers(@Param("orderNumbers") Set<String> orderNumbers);

    /**
     * 查询订单号为null的全渠道销售数据
     * @return 订单号为null的全渠道销售数据列表
     */
    List<ChannelSalesData> selectChannelSalesDataByOrderNumberIsNull();

    /**
     * 批量插入全渠道销售数据
     * @param list 全渠道销售数据列表
     * @return 插入的行数
     */
    int batchInsertChannelSalesData(@Param("list") List<ChannelSalesData> list);
}
