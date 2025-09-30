package com.ruoyi.sales.service.impl;

import java.util.List;
import java.util.Map;
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

    @Override
    public Map<String, Object> getKpiData(TEMUVisualQuery query) {
        return visualizationMapper.selectKpiData(query);
    }

    @Override
    public Map<String, Object> getAverageMetrics(TEMUVisualQuery query) {
        return visualizationMapper.selectAverageMetrics(query);
    }
    @Override
    public List<Map<String, Object>> getTrendData(TEMUVisualQuery query) {
        return visualizationMapper.selectTrendData(query);
    }

    @Override
    public List<Map<String, Object>> getDeveloperSankeyData(TEMUVisualQuery query) {
        return visualizationMapper.selectDeveloperSankeyData(query);
    }

    @Override
    public List<Map<String, Object>> getDeveloperRankingData(TEMUVisualQuery query) {
        return visualizationMapper.selectDeveloperRankingData(query);
    }

    @Override
    public List<Map<String, Object>> getCategoryTreemapData(TEMUVisualQuery query) {
        return visualizationMapper.selectCategoryTreemapData(query);
    }

    @Override
    public List<Map<String, Object>> getCategoryRankingData(TEMUVisualQuery query) {
        return visualizationMapper.selectCategoryRankingData(query);
    }

    @Override
    public List<Map<String, Object>> getDetailTableData(TEMUVisualQuery query) {
        return visualizationMapper.selectDetailTableData(query);
    }

    @Override
    public List<Map<String, Object>> getAlertData(TEMUVisualQuery query) {
        return visualizationMapper.selectAlertData(query);
    }

    @Override
    public Map<String, Object> getFilterOptions() {
        return visualizationMapper.selectFilterOptions();
    }

    @Override
    public List<Map<String, Object>> getOperatorPerspectiveData(TEMUVisualQuery query) {
        return visualizationMapper.selectOperatorPerspectiveData(query);
    }

    @Override
    public List<Map<String, Object>> getDeveloperPerspectiveData(TEMUVisualQuery query) {
        return visualizationMapper.selectDeveloperPerspectiveData(query);
    }

    @Override
    public List<Map<String, Object>> getCategoryPerspectiveData(TEMUVisualQuery query) {
        return visualizationMapper.selectCategoryPerspectiveData(query);
    }

    @Override
    public List<Map<String, Object>> getOperatorWarehouseData(TEMUVisualQuery query) {
        return visualizationMapper.selectOperatorWarehouseData(query);
    }

    @Override
    public List<Map<String, Object>> getDeveloperWarehouseData(TEMUVisualQuery query) {
        return visualizationMapper.selectDeveloperWarehouseData(query);
    }
}
