package com.ruoyi.sales.controller;

import java.util.List;

import com.ruoyi.sales.domain.FeeItemSummary;
import com.ruoyi.sales.domain.OverseasHostingDimensionSummary;
import com.ruoyi.sales.domain.TEMUOrderDetails;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.sales.domain.OverseasHostingData;
import com.ruoyi.sales.service.IOverseasHostingDataService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 海外托管业务数据管理Controller
 *
 * @author ZMJ
 * @date 2025-09-30
 */
@RestController
@RequestMapping("/overseas/data")
public class OverseasHostingDataController extends BaseController
{
    @Autowired
    private IOverseasHostingDataService overseasHostingDataService;

    /**
     * 查询海外托管业务数据管理列表
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:list')")
    @GetMapping("/list")
    public TableDataInfo list(OverseasHostingData overseasHostingData)
    {
        startPage();
        List<OverseasHostingData> list = overseasHostingDataService.selectOverseasHostingDataList(overseasHostingData);
        return getDataTable(list);
    }

    /**
     * 导出海外托管业务数据管理列表
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:export')")
    @Log(title = "海外托管业务数据管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OverseasHostingData overseasHostingData)
    {
        List<OverseasHostingData> list = overseasHostingDataService.selectOverseasHostingDataList(overseasHostingData);
        ExcelUtil<OverseasHostingData> util = new ExcelUtil<OverseasHostingData>(OverseasHostingData.class);
        util.exportExcel(response, list, "海外托管业务数据管理数据");
    }

    /**
     * 获取海外托管业务数据管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(overseasHostingDataService.selectOverseasHostingDataById(id));
    }

    /**
     * 新增海外托管业务数据管理
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:add')")
    @Log(title = "海外托管业务数据管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OverseasHostingData overseasHostingData)
    {
        return toAjax(overseasHostingDataService.insertOverseasHostingData(overseasHostingData));
    }

    /**
     * 修改海外托管业务数据管理
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:edit')")
    @Log(title = "海外托管业务数据管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OverseasHostingData overseasHostingData)
    {
        return toAjax(overseasHostingDataService.updateOverseasHostingData(overseasHostingData));
    }

    /**
     * 删除海外托管业务数据管理
     */
    @PreAuthorize("@ss.hasPermi('overseas:data:remove')")
    @Log(title = "海外托管业务数据管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(overseasHostingDataService.deleteOverseasHostingDataByIds(ids));
    }

    /**
     * 导入数据
     */
    @Log(title = "海外托管数据管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('overseas:data:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<OverseasHostingData> util = new ExcelUtil<>(OverseasHostingData.class);
        List<OverseasHostingData> overseasDataList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = overseasHostingDataService.importOverseasData(overseasDataList, operName);
        return success(message);
    }

    /**
     * 导入模板下载
     * @param response
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<OverseasHostingData> util = new ExcelUtil<>(OverseasHostingData.class);
        util.importTemplateExcel(response, "海外托管数据模板");
    }

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
