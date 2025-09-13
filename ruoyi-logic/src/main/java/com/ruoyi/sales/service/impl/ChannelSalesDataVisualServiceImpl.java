package com.ruoyi.sales.service.impl;

import com.ruoyi.sales.domain.ChannelSalesData;
import com.ruoyi.sales.mapper.ChannelSalesDataVisualMapper;
import com.ruoyi.sales.service.IChannelSalesDataVisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 全渠道销售数据可视化Service业务层处理
 */
@Service
public class ChannelSalesDataVisualServiceImpl implements IChannelSalesDataVisualService {

    @Autowired
    private ChannelSalesDataVisualMapper channelSalesDataVisualMapper;

    @Override
    public Map<String, Object> getKpiData(ChannelSalesData channelSalesData) {
        return channelSalesDataVisualMapper.selectKpiData(channelSalesData);
    }

    @Override
    public List<Map<String, Object>> getCategoryRefundData(ChannelSalesData channelSalesData) {
        return channelSalesDataVisualMapper.selectCategoryRefundData(channelSalesData);
    }

    @Override
    public List<Map<String, Object>> getSkuRoiData(ChannelSalesData channelSalesData) {
        return channelSalesDataVisualMapper.selectSkuRoiData(channelSalesData);
    }

    @Override
    public List<Map<String, Object>> getFirstLevelCategoryData(ChannelSalesData channelSalesData) {
        return channelSalesDataVisualMapper.selectFirstLevelCategoryData(channelSalesData);
    }

    @Override
    public List<Map<String, Object>> getChannelData(ChannelSalesData channelSalesData) {
        return channelSalesDataVisualMapper.selectChannelData(channelSalesData);
    }

    @Override
    public List<Map<String, Object>> getBrandData(ChannelSalesData channelSalesData) {
        return channelSalesDataVisualMapper.selectBrandData(channelSalesData);
    }

    @Override
    public List<Map<String, Object>> getCountryData(ChannelSalesData channelSalesData) {
        return channelSalesDataVisualMapper.selectCountryData(channelSalesData);
    }

    @Override
    public List<Map<String, Object>> getStoreData(ChannelSalesData channelSalesData) {
        return channelSalesDataVisualMapper.selectStoreData(channelSalesData);
    }

    @Override
    public List<Map<String, Object>> getMapData(ChannelSalesData channelSalesData) {
        return channelSalesDataVisualMapper.selectMapData(channelSalesData);
    }

    @Override
    public List<Map<String, Object>> getChannelDistributionData(ChannelSalesData channelSalesData) {
        return channelSalesDataVisualMapper.selectChannelDistributionData(channelSalesData);
    }

    @Override
    public List<Map<String, Object>> getAlertData(ChannelSalesData channelSalesData) {
        return channelSalesDataVisualMapper.selectAlertData(channelSalesData);
    }

    @Override
    public List<Map<String, Object>> getTrendData(ChannelSalesData channelSalesData) {
        return channelSalesDataVisualMapper.selectTrendData(channelSalesData);
    }

    @Override
    public List<Map<String, Object>> getDetailData(ChannelSalesData channelSalesData) {
        return channelSalesDataVisualMapper.selectDetailData(channelSalesData);
    }

    @Override
    public Map<String, List<String>> getFilterOptions() {
        return channelSalesDataVisualMapper.selectFilterOptions();
    }
}
