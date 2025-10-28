package com.ruoyi.sales.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.sales.dto.TEMUVisualQuery;
import com.ruoyi.sales.service.ITEMUVisualizationService;

/**
 * TEMU可视化数据Controller
 */
@RestController
@RequestMapping("/temu/visual")
public class TEMUVisualizationController extends BaseController {

    @Autowired
    private ITEMUVisualizationService visualizationService;

    /**
     * 获取核心KPI数据
     */
    @GetMapping("/kpi")
    public AjaxResult getKpiData(TEMUVisualQuery query) {
        Map<String, Object> data = visualizationService.getKpiData(query);
        return AjaxResult.success(data);
    }

    /**
     * 获取平均指标数据
     */
    @GetMapping("/averageMetrics")
    public AjaxResult getAverageMetrics(TEMUVisualQuery query) {
        Map<String, Object> data = visualizationService.getAverageMetrics(query);
        return AjaxResult.success(data);
    }

    /**
     * 获取预警数据
     */
    @GetMapping("/alert")
    public AjaxResult getAlertData(TEMUVisualQuery query) {
        List<Map<String, Object>> data = visualizationService.getAlertData(query);
        return AjaxResult.success(data);
    }

    /**
     * 获取筛选选项
     */
    @GetMapping("/filterOptions")
    public AjaxResult getFilterOptions() {
        Map<String, Object> data = visualizationService.getFilterOptions();
        return AjaxResult.success(data);
    }

    /**
     * 获取运营员透视数据
     */
    @GetMapping("/operatorPerspective")
    public AjaxResult getOperatorPerspectiveData(TEMUVisualQuery query) {
        List<Map<String, Object>> data = visualizationService.getOperatorPerspectiveData(query);
        return AjaxResult.success(data);
    }

    /**
     * 获取开发员透视数据
     */
    @GetMapping("/developerPerspective")
    public AjaxResult getDeveloperPerspectiveData(TEMUVisualQuery query) {
        List<Map<String, Object>> data = visualizationService.getDeveloperPerspectiveData(query);
        return AjaxResult.success(data);
    }

    /**
     * 获取品类透视数据
     */
    @GetMapping("/categoryPerspective")
    public AjaxResult getCategoryPerspectiveData(TEMUVisualQuery query) {
        List<Map<String, Object>> data = visualizationService.getCategoryPerspectiveData(query);
        return AjaxResult.success(data);
    }

    /**
     * 获取运营员发货仓库数据
     */
    @GetMapping("/operatorWarehouse")
    public AjaxResult getOperatorWarehouseData(TEMUVisualQuery query) {
        List<Map<String, Object>> data = visualizationService.getOperatorWarehouseData(query);
        return AjaxResult.success(data);
    }

    /**
     * 获取开发员发货仓库数据
     */
    @GetMapping("/developerWarehouse")
    public AjaxResult getDeveloperWarehouseData(TEMUVisualQuery query) {
        List<Map<String, Object>> data = visualizationService.getDeveloperWarehouseData(query);
        return AjaxResult.success(data);
    }
}
