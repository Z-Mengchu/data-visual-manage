package com.ruoyi.sales.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.sales.domain.FeeItemSummary;
import com.ruoyi.sales.domain.OverseasHostingDimensionSummary;
import com.ruoyi.sales.service.IOverseasHostingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public AjaxResult getSummaryByOperator()
    {
        List<OverseasHostingDimensionSummary> summaryList = overseasHostingDataService.getSummaryByOperator();
        return success(summaryList);
    }

    /**
     * 按开发员分组汇总数据
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/summary/developer")
    public AjaxResult getSummaryByDeveloper()
    {
        List<OverseasHostingDimensionSummary> summaryList = overseasHostingDataService.getSummaryByDeveloper();
        return success(summaryList);
    }

    /**
     * 按品牌分组汇总数据
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/summary/brand")
    public AjaxResult getSummaryByBrand()
    {
        List<OverseasHostingDimensionSummary> summaryList = overseasHostingDataService.getSummaryByBrand();
        return success(summaryList);
    }

    /**
     * 按仓库分组汇总数据
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/summary/warehouse")
    public AjaxResult getSummaryByWarehouse()
    {
        List<OverseasHostingDimensionSummary> summaryList = overseasHostingDataService.getSummaryByWarehouse();
        return success(summaryList);
    }

    /**
     * 按类目分组汇总数据
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/summary/category")
    public AjaxResult getSummaryByCategory()
    {
        List<OverseasHostingDimensionSummary> summaryList = overseasHostingDataService.getSummaryByCategory();
        return success(summaryList);
    }

    /**
     * 按SKU分组汇总数据
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/summary/sku")
    public AjaxResult getSummaryBySku()
    {
        List<OverseasHostingDimensionSummary> summaryList = overseasHostingDataService.getSummaryBySku();
        return success(summaryList);
    }

    /**
     * 按费用项分组汇总金额
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/summary/fee-item")
    public AjaxResult getSummaryByFeeItem()
    {
        List<FeeItemSummary> summaryList = overseasHostingDataService.getSummaryByFeeItem();
        return success(summaryList);
    }
}
