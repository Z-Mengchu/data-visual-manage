package com.ruoyi.sales.service;

import java.util.List;
import java.util.Map;
import com.ruoyi.sales.dto.TEMUVisualQuery;

/**
 * TEMU可视化数据服务接口
 */
public interface ITEMUVisualizationService {

    /**
     * 获取核心KPI数据
     */
    Map<String, Object> getKpiData(TEMUVisualQuery query);

    /**
     * 获取平均指标数据
     */
    Map<String, Object> getAverageMetrics(TEMUVisualQuery query);

    /**
     * 获取趋势数据
     */
    List<Map<String, Object>> getTrendData(TEMUVisualQuery query);

    /**
     * 获取开发员桑基图数据
     */
    List<Map<String, Object>> getDeveloperSankeyData(TEMUVisualQuery query);

    /**
     * 获取开发员排行榜数据
     */
    List<Map<String, Object>> getDeveloperRankingData(TEMUVisualQuery query);

    /**
     * 获取品类矩形树图数据
     */
    List<Map<String, Object>> getCategoryTreemapData(TEMUVisualQuery query);

    /**
     * 获取品类排行榜数据
     */
    List<Map<String, Object>> getCategoryRankingData(TEMUVisualQuery query);

    /**
     * 获取明细表格数据
     */
    List<Map<String, Object>> getDetailTableData(TEMUVisualQuery query);

    /**
     * 获取预警数据
     */
    List<Map<String, Object>> getAlertData(TEMUVisualQuery query);

    /**
     * 获取筛选选项
     */
    Map<String, Object> getFilterOptions();

    /**
     * 获取运营员透视数据
     */
    List<Map<String, Object>> getOperatorPerspectiveData(TEMUVisualQuery query);

    /**
     * 获取开发员透视数据
     */
    List<Map<String, Object>> getDeveloperPerspectiveData(TEMUVisualQuery query);

    /**
     * 获取品类透视数据
     */
    List<Map<String, Object>> getCategoryPerspectiveData(TEMUVisualQuery query);

    /**
     * 获取运营员发货仓库数据
     */
    List<Map<String, Object>> getOperatorWarehouseData(TEMUVisualQuery query);

    /**
     * 获取开发员发货仓库数据
     */
    List<Map<String, Object>> getDeveloperWarehouseData(TEMUVisualQuery query);
}
