package com.ruoyi.sales.service;

import com.ruoyi.sales.domain.ChannelSalesData;

import java.util.List;
import java.util.Map;

/**
 * 全渠道销售数据可视化Service接口
 */
public interface IChannelSalesDataVisualService {

    /**
     * 获取核心KPI数据
     */
    Map<String, Object> getKpiData(ChannelSalesData channelSalesData);

    /**
     * 获取品类退款率数据
     */
    List<Map<String, Object>> getCategoryRefundData(ChannelSalesData channelSalesData);

    /**
     * 获取SKU ROI数据
     */
    List<Map<String, Object>> getSkuRoiData(ChannelSalesData channelSalesData);

    /**
     * 获取一级品类数据
     */
    List<Map<String, Object>> getFirstLevelCategoryData(ChannelSalesData channelSalesData);

    /**
     * 获取渠道数据
     */
    List<Map<String, Object>> getChannelData(ChannelSalesData channelSalesData);

    /**
     * 获取品牌数据
     */
    List<Map<String, Object>> getBrandData(ChannelSalesData channelSalesData);

    /**
     * 获取国家数据
     */
    List<Map<String, Object>> getCountryData(ChannelSalesData channelSalesData);

    /**
     * 获取店铺数据
     */
    List<Map<String, Object>> getStoreData(ChannelSalesData channelSalesData);

    /**
     * 获取地图数据
     */
    List<Map<String, Object>> getMapData(ChannelSalesData channelSalesData);

    /**
     * 获取渠道销售额占比数据
     */
    List<Map<String, Object>> getChannelDistributionData(ChannelSalesData channelSalesData);

    /**
     * 获取预警数据
     */
    List<Map<String, Object>> getAlertData(ChannelSalesData channelSalesData);

    /**
     * 获取趋势数据
     */
    List<Map<String, Object>> getTrendData(ChannelSalesData channelSalesData);

    /**
     * 获取详情表格数据
     */
    List<Map<String, Object>> getDetailData(ChannelSalesData channelSalesData);

    /**
     * 获取筛选条件选项
     */
    Map<String, List<String>> getFilterOptions();
}
