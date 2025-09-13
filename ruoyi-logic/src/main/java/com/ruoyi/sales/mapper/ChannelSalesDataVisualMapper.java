package com.ruoyi.sales.mapper;

import com.ruoyi.sales.domain.ChannelSalesData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 全渠道销售数据可视化Mapper接口
 */
public interface ChannelSalesDataVisualMapper {

    /**
     * 获取核心KPI数据
     */
    Map<String, Object> selectKpiData(ChannelSalesData channelSalesData);

    /**
     * 获取品类退款率数据
     */
    List<Map<String, Object>> selectCategoryRefundData(ChannelSalesData channelSalesData);

    /**
     * 获取SKU ROI数据
     */
    List<Map<String, Object>> selectSkuRoiData(ChannelSalesData channelSalesData);

    /**
     * 获取一级品类数据
     */
    List<Map<String, Object>> selectFirstLevelCategoryData(ChannelSalesData channelSalesData);

    /**
     * 获取渠道数据
     */
    List<Map<String, Object>> selectChannelData(ChannelSalesData channelSalesData);

    /**
     * 获取品牌数据
     */
    List<Map<String, Object>> selectBrandData(ChannelSalesData channelSalesData);

    /**
     * 获取国家数据
     */
    List<Map<String, Object>> selectCountryData(ChannelSalesData channelSalesData);

    /**
     * 获取店铺数据
     */
    List<Map<String, Object>> selectStoreData(ChannelSalesData channelSalesData);

    /**
     * 获取地图数据
     */
    List<Map<String, Object>> selectMapData(ChannelSalesData channelSalesData);

    /**
     * 获取渠道销售额占比数据
     */
    List<Map<String, Object>> selectChannelDistributionData(ChannelSalesData channelSalesData);

    /**
     * 获取预警数据
     */
    List<Map<String, Object>> selectAlertData(ChannelSalesData channelSalesData);

    /**
     * 获取趋势数据
     */
    List<Map<String, Object>> selectTrendData(ChannelSalesData channelSalesData);

    /**
     * 获取详情表格数据
     */
    List<Map<String, Object>> selectDetailData(ChannelSalesData channelSalesData);

    /**
     * 获取筛选条件选项
     */
    Map<String, List<String>> selectFilterOptions();
}
