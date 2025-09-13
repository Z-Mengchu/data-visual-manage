package com.ruoyi.sales.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.sales.domain.ChannelSalesData;
import com.ruoyi.sales.service.IChannelSalesDataVisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 全渠道销售数据可视化Controller
 */
@RestController
@RequestMapping("/sale/visual")
public class ChannelSalesDataVisualController extends BaseController {

    @Autowired
    private IChannelSalesDataVisualService channelSalesDataVisualService;

    /**
     * 获取核心KPI数据
     */
    @GetMapping("/kpi")
    public AjaxResult getKpiData(ChannelSalesData channelSalesData) {
        Map<String, Object> kpiData = channelSalesDataVisualService.getKpiData(channelSalesData);
        return AjaxResult.success(kpiData);
    }

    /**
     * 获取品类退款率数据
     */
    @GetMapping("/stats/categoryRefund")
    public AjaxResult getCategoryRefundData(ChannelSalesData channelSalesData) {
        List<Map<String, Object>> categoryRefundData = channelSalesDataVisualService.getCategoryRefundData(channelSalesData);
        return AjaxResult.success(categoryRefundData);
    }

    /**
     * 获取SKU ROI数据
     */
    @GetMapping("/stats/skuRoi")
    public AjaxResult getSkuRoiData(ChannelSalesData channelSalesData) {
        List<Map<String, Object>> skuRoiData = channelSalesDataVisualService.getSkuRoiData(channelSalesData);
        return AjaxResult.success(skuRoiData);
    }

    /**
     * 获取一级品类数据
     */
    @GetMapping("/stats/firstLevelCategory")
    public AjaxResult getFirstLevelCategoryData(ChannelSalesData channelSalesData) {
        List<Map<String, Object>> firstLevelCategoryData = channelSalesDataVisualService.getFirstLevelCategoryData(channelSalesData);
        return AjaxResult.success(firstLevelCategoryData);
    }

    /**
     * 获取渠道数据
     */
    @GetMapping("/stats/channel")
    public AjaxResult getChannelData(ChannelSalesData channelSalesData) {
        List<Map<String, Object>> channelData = channelSalesDataVisualService.getChannelData(channelSalesData);
        return AjaxResult.success(channelData);
    }

    /**
     * 获取品牌数据
     */
    @GetMapping("/stats/brand")
    public AjaxResult getBrandData(ChannelSalesData channelSalesData) {
        List<Map<String, Object>> brandData = channelSalesDataVisualService.getBrandData(channelSalesData);
        return AjaxResult.success(brandData);
    }

    /**
     * 获取国家数据
     */
    @GetMapping("/stats/country")
    public AjaxResult getCountryData(ChannelSalesData channelSalesData) {
        List<Map<String, Object>> countryData = channelSalesDataVisualService.getCountryData(channelSalesData);
        return AjaxResult.success(countryData);
    }

    /**
     * 获取店铺数据
     */
    @GetMapping("/stats/store")
    public AjaxResult getStoreData(ChannelSalesData channelSalesData) {
        List<Map<String, Object>> storeData = channelSalesDataVisualService.getStoreData(channelSalesData);
        return AjaxResult.success(storeData);
    }

    /**
     * 获取地图数据
     */
    @GetMapping("/map")
    public AjaxResult getMapData(ChannelSalesData channelSalesData) {
        List<Map<String, Object>> mapData = channelSalesDataVisualService.getMapData(channelSalesData);
        return AjaxResult.success(mapData);
    }

    /**
     * 获取渠道销售额占比数据
     */
    @GetMapping("/channelDistribution")
    public AjaxResult getChannelDistributionData(ChannelSalesData channelSalesData) {
        List<Map<String, Object>> channelDistributionData = channelSalesDataVisualService.getChannelDistributionData(channelSalesData);
        return AjaxResult.success(channelDistributionData);
    }

    /**
     * 获取预警数据
     */
    @GetMapping("/alerts")
    public AjaxResult getAlertData(ChannelSalesData channelSalesData) {
        List<Map<String, Object>> alertData = channelSalesDataVisualService.getAlertData(channelSalesData);
        return AjaxResult.success(alertData);
    }

    /**
     * 获取趋势数据
     */
    @GetMapping("/trend")
    public AjaxResult getTrendData(ChannelSalesData channelSalesData) {
        List<Map<String, Object>> trendData = channelSalesDataVisualService.getTrendData(channelSalesData);
        return AjaxResult.success(trendData);
    }

    /**
     * 获取详情表格数据
     */
    @GetMapping("/details")
    public AjaxResult getDetailData(ChannelSalesData channelSalesData) {
        List<Map<String, Object>> detailData = channelSalesDataVisualService.getDetailData(channelSalesData);
        return AjaxResult.success(detailData);
    }

    /**
     * 获取筛选条件选项
     */
    @GetMapping("/filterOptions")
    public AjaxResult getFilterOptions() {
        Map<String, List<String>> filterOptions = channelSalesDataVisualService.getFilterOptions();
        return AjaxResult.success(filterOptions);
    }
}
