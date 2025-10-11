package com.ruoyi.sales.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.sales.domain.FeeItemSummary;
import com.ruoyi.sales.domain.OverseasHostingDimensionSummary;
import com.ruoyi.sales.service.IOverseasHostingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.ruoyi.common.core.domain.AjaxResult.success;

@RestController
@RequestMapping("/overseas/visual")
public class OverseasDashboardController {
    @Autowired
    private IOverseasHostingDataService overseasHostingDataService;

    /**
     * 按运营分组汇总数据
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/summary/operator")
    public AjaxResult getSummaryByOperator(@RequestParam(value = "country", required = false) String country)
    {
        List<OverseasHostingDimensionSummary> summaryList = overseasHostingDataService.getSummaryByOperator(country);
        return success(summaryList);
    }
    /**
     * 按开发员分组汇总数据
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/summary/developer")
    public AjaxResult getSummaryByDeveloper(@RequestParam(value = "country", required = false) String country)
    {
        List<OverseasHostingDimensionSummary> summaryList = overseasHostingDataService.getSummaryByDeveloper(country);
        return success(summaryList);
    }
    /**
     * 按品牌分组汇总数据
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/summary/brand")
    public AjaxResult getSummaryByBrand(@RequestParam(value = "country", required = false) String country)
    {
        List<OverseasHostingDimensionSummary> summaryList = overseasHostingDataService.getSummaryByBrand(country);
        return success(summaryList);
    }
    /**
     * 按仓库分组汇总数据
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/summary/warehouse")
    public AjaxResult getSummaryByWarehouse(@RequestParam(value = "country", required = false) String country)
    {
        List<OverseasHostingDimensionSummary> summaryList = overseasHostingDataService.getSummaryByWarehouse(country);
        return success(summaryList);
    }
    /**
     * 按类目分组汇总数据
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/summary/category")
    public AjaxResult getSummaryByCategory(@RequestParam(value = "country", required = false) String country)
    {
        List<OverseasHostingDimensionSummary> summaryList = overseasHostingDataService.getSummaryByCategory(country);
        return success(summaryList);
    }
    /**
     * 按SKU分组汇总数据
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/summary/sku")
    public AjaxResult getSummaryBySku(@RequestParam(value = "country", required = false) String country)
    {
        List<OverseasHostingDimensionSummary> summaryList = overseasHostingDataService.getSummaryBySku(country);
        return success(summaryList);
    }
    /**
     * 按费用项分组汇总金额
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/summary/fee-item")
    public AjaxResult getSummaryByFeeItem(@RequestParam(value = "country", required = false) String country)
    {
        List<FeeItemSummary> summaryList = overseasHostingDataService.getSummaryByFeeItem(country);
        return success(summaryList);
    }
    /**
     * 按品牌和类目分组汇总数据
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/summary/brand-category")
    public AjaxResult getSummaryByBrandAndCategory(@RequestParam(value = "country", required = false) String country)
    {
        List<Map<String, Object>> summaryList = overseasHostingDataService.getSummaryByBrandAndCategory(country);
        return success(summaryList);
    }
    /**
     * 按月份分组汇总当前年份数据
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/summary/monthly")
    public AjaxResult getSummaryByMonthly(@RequestParam(value = "country", required = false) String country)
    {
        List<Map<String, Object>> summaryList = overseasHostingDataService.getSummaryByMonthly(country);
        return success(summaryList);
    }
    /**
     * 获取总体统计数据
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/summary/total")
    public AjaxResult getSummaryByTotal(@RequestParam(value = "country", required = false) String country)
    {
        Map<String, Object> summaryData = overseasHostingDataService.getSummaryByTotal(country);
        return success(summaryData);
    }
    /**
     * 获取核心费用项汇总数据
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/summary/core-expenses")
    public AjaxResult getSummaryByCoreExpenses(@RequestParam(value = "country", required = false) String country)
    {
        Map<String, Object> summaryData = overseasHostingDataService.getSummaryByCoreExpenses(country);
        return success(summaryData);
    }

    /**
     * 获取国家信息
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/allCountry")
    public AjaxResult getAllCountry(){
        return success(overseasHostingDataService.getAllCountry());
    }
}
