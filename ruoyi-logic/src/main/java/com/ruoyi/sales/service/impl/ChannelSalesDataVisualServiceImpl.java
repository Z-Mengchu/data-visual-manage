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
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return channelSalesDataVisualMapper.selectKpiDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys);
    }
    @Override
    public List<Map<String, Object>> getCategoryRefundData(ChannelSalesData channelSalesData) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();

        return channelSalesDataVisualMapper.selectCategoryRefundDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys);
    }
    @Override
    public List<Map<String, Object>> getSkuRoiData(ChannelSalesData channelSalesData) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return channelSalesDataVisualMapper.selectSkuRoiDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys);
    }
    @Override
    public List<Map<String, Object>> getFirstLevelCategoryData(ChannelSalesData channelSalesData) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return channelSalesDataVisualMapper.selectFirstLevelCategoryDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys);
    }
    @Override
    public List<Map<String, Object>> getChannelData(ChannelSalesData channelSalesData) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return channelSalesDataVisualMapper.selectChannelDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys);
    }
    @Override
    public List<Map<String, Object>> getBrandData(ChannelSalesData channelSalesData) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return channelSalesDataVisualMapper.selectBrandDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys);
    }
    @Override
    public List<Map<String, Object>> getCountryData(ChannelSalesData channelSalesData) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return channelSalesDataVisualMapper.selectCountryDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys);
    }
    @Override
    public List<Map<String, Object>> getStoreData(ChannelSalesData channelSalesData) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return channelSalesDataVisualMapper.selectStoreDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys);
    }
    @Override
    public List<Map<String, Object>> getMapData(ChannelSalesData channelSalesData) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return channelSalesDataVisualMapper.selectMapDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys);
    }
    @Override
    public List<Map<String, Object>> getChannelDistributionData(ChannelSalesData channelSalesData) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return channelSalesDataVisualMapper.selectChannelDistributionDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys);
    }
    @Override
    public List<Map<String, Object>> getAlertData(ChannelSalesData channelSalesData) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return channelSalesDataVisualMapper.selectAlertDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys);
    }
    @Override
    public List<Map<String, Object>> getTrendData(ChannelSalesData channelSalesData) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return channelSalesDataVisualMapper.selectTrendDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys);
    }
    @Override
    public List<Map<String, Object>> getDetailData(ChannelSalesData channelSalesData) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return channelSalesDataVisualMapper.selectDetailDataWithPermission(channelSalesData, currentUser, postCodes, roleKeys);
    }

    @Override
    public Map<String, List<String>> getFilterOptions() {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return channelSalesDataVisualMapper.selectFilterOptions(currentUser, postCodes, roleKeys);
    }
}
