package com.ruoyi.sales.service.impl;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.sales.domain.ChannelSalesData;
import com.ruoyi.sales.mapper.ChannelSalesDataVisualMapper;
import com.ruoyi.sales.service.IChannelSalesDataVisualService;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全渠道销售数据可视化Service业务层处理
 */
@Service
public class ChannelSalesDataVisualServiceImpl implements IChannelSalesDataVisualService {

    @Autowired
    private ChannelSalesDataVisualMapper channelSalesDataVisualMapper;
    @Autowired
    private ISysPostService postService;

    @Override
    public Map<String, Object> getKpiData(ChannelSalesData channelSalesData) {
        return executeWithPermission((currentUser, postCodes, roleKeys) ->
                channelSalesDataVisualMapper.selectKpiDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys));
    }
    @Override
    public List<Map<String, Object>> getCategoryRefundData(ChannelSalesData channelSalesData) {
        return executeWithPermission((currentUser, postCodes, roleKeys) ->
                channelSalesDataVisualMapper.selectCategoryRefundDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys));
    }
    @Override
    public List<Map<String, Object>> getSkuRoiData(ChannelSalesData channelSalesData) {
        return executeWithPermission((currentUser, postCodes, roleKeys) ->
                channelSalesDataVisualMapper.selectSkuRoiDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys));
    }
    @Override
    public List<Map<String, Object>> getFirstLevelCategoryData(ChannelSalesData channelSalesData) {
        return executeWithPermission((currentUser, postCodes, roleKeys) ->
                channelSalesDataVisualMapper.selectFirstLevelCategoryDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys));
    }
    @Override
    public List<Map<String, Object>> getChannelData(ChannelSalesData channelSalesData) {
        return executeWithPermission((currentUser, postCodes, roleKeys) ->
                channelSalesDataVisualMapper.selectChannelDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys));
    }
    @Override
    public List<Map<String, Object>> getBrandData(ChannelSalesData channelSalesData) {
        return executeWithPermission((currentUser, postCodes, roleKeys) ->
                channelSalesDataVisualMapper.selectBrandDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys));
    }
    @Override
    public List<Map<String, Object>> getCountryData(ChannelSalesData channelSalesData) {
        return executeWithPermission((currentUser, postCodes, roleKeys) ->
                channelSalesDataVisualMapper.selectCountryDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys));
    }
    @Override
    public List<Map<String, Object>> getStoreData(ChannelSalesData channelSalesData) {
        return executeWithPermission((currentUser, postCodes, roleKeys) ->
                channelSalesDataVisualMapper.selectStoreDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys));
    }
    @Override
    public List<Map<String, Object>> getMapData(ChannelSalesData channelSalesData) {
        return executeWithPermission((currentUser, postCodes, roleKeys) ->
                channelSalesDataVisualMapper.selectMapDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys));
    }
    @Override
    public List<Map<String, Object>> getChannelDistributionData(ChannelSalesData channelSalesData) {
        return executeWithPermission((currentUser, postCodes, roleKeys) ->
                channelSalesDataVisualMapper.selectChannelDistributionDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys));
    }
    @Override
    public List<Map<String, Object>> getAlertData(ChannelSalesData channelSalesData) {
        return executeWithPermission((currentUser, postCodes, roleKeys) ->
                channelSalesDataVisualMapper.selectAlertDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys));
    }
    @Override
    public List<Map<String, Object>> getTrendData(ChannelSalesData channelSalesData) {
        return executeWithPermission((currentUser, postCodes, roleKeys) ->
                channelSalesDataVisualMapper.selectTrendDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys));
    }
    @Override
    public List<Map<String, Object>> getDetailData(ChannelSalesData channelSalesData) {
        return executeWithPermission((currentUser, postCodes, roleKeys) ->
                channelSalesDataVisualMapper.selectDetailDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys));
    }

    @Override
    public Map<String, List<String>> getFilterOptions() {
        return executeWithPermission((currentUser, postCodes, roleKeys) ->
                channelSalesDataVisualMapper.selectFilterOptions(currentUser, postCodes, roleKeys));
    }

    @FunctionalInterface
    private interface DataPermissionHandler<T> {
        T handle(SysUser currentUser, List<String> postCodes, List<String> roleKeys);
    }

    private <T> T executeWithPermission(DataPermissionHandler<T> handler) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return handler.handle(currentUser, postCodes, roleKeys);
    }
}
