package com.ruoyi.sales.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.sales.dto.TEMUVisualQuery;
import com.ruoyi.sales.mapper.TEMUVisualizationMapper;
import com.ruoyi.sales.service.ITEMUVisualizationService;

/**
 * TEMU可视化数据服务实现
 */
@Service
public class TEMUVisualizationServiceImpl implements ITEMUVisualizationService {

    @Autowired
    private TEMUVisualizationMapper visualizationMapper;

    @Autowired
    private ISysPostService postService;

    @Override
    public Map<String, Object> getKpiData(TEMUVisualQuery query) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return visualizationMapper.selectKpiData(query, currentUser, postCodes, roleKeys);
    }

    @Override
    public Map<String, Object> getAverageMetrics(TEMUVisualQuery query) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return visualizationMapper.selectAverageMetrics(query, currentUser, postCodes, roleKeys);
    }

    @Override
    public List<Map<String, Object>> getAlertData(TEMUVisualQuery query) {SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return visualizationMapper.selectAlertData(query, currentUser, postCodes, roleKeys);
    }

    @Override
    public Map<String, Object> getFilterOptions() {
        return visualizationMapper.selectFilterOptions();
    }

    @Override
    public List<Map<String, Object>> getOperatorPerspectiveData(TEMUVisualQuery query) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return visualizationMapper.selectOperatorPerspectiveData(query, currentUser, postCodes, roleKeys);
    }

    @Override
    public List<Map<String, Object>> getDeveloperPerspectiveData(TEMUVisualQuery query) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return visualizationMapper.selectDeveloperPerspectiveData(query, currentUser, postCodes, roleKeys);
    }

    @Override
    public List<Map<String, Object>> getCategoryPerspectiveData(TEMUVisualQuery query) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return visualizationMapper.selectCategoryPerspectiveData(query, currentUser, postCodes, roleKeys);
    }

    @Override
    public List<Map<String, Object>> getOperatorWarehouseData(TEMUVisualQuery query) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return visualizationMapper.selectOperatorWarehouseData(query, currentUser, postCodes, roleKeys);
    }

    @Override
    public List<Map<String, Object>> getDeveloperWarehouseData(TEMUVisualQuery query) {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return visualizationMapper.selectDeveloperWarehouseData(query, currentUser, postCodes, roleKeys);
    }
}
