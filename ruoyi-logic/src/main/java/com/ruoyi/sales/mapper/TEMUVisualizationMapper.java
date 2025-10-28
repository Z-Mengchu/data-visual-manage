package com.ruoyi.sales.mapper;

import java.util.List;
import java.util.Map;
import com.ruoyi.sales.dto.TEMUVisualQuery;

/**
 * TEMU可视化数据Mapper接口
 */
public interface TEMUVisualizationMapper {

    /**
     * 获取核心KPI数据
     */
    Map<String, Object> selectKpiData(TEMUVisualQuery query);

    /**
     * 获取平均指标数据
     */
    Map<String, Object> selectAverageMetrics(TEMUVisualQuery query);

    /**
     * 获取预警数据
     */
    List<Map<String, Object>> selectAlertData(TEMUVisualQuery query);

    /**
     * 获取筛选选项
     */
    Map<String, Object> selectFilterOptions();

    /**
     * 获取运营员透视数据
     */
    List<Map<String, Object>> selectOperatorPerspectiveData(TEMUVisualQuery query);

    /**
     * 获取开发员透视数据
     */
    List<Map<String, Object>> selectDeveloperPerspectiveData(TEMUVisualQuery query);

    /**
     * 获取品类透视数据
     */
    List<Map<String, Object>> selectCategoryPerspectiveData(TEMUVisualQuery query);

    /**
     * 获取运营员发货仓库数据
     */
    List<Map<String, Object>> selectOperatorWarehouseData(TEMUVisualQuery query);

    /**
     * 获取开发员发货仓库数据
     */
    List<Map<String, Object>> selectDeveloperWarehouseData(TEMUVisualQuery query);
}
