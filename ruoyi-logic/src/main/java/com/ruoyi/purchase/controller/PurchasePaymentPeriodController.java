package com.ruoyi.purchase.controller;

import java.util.List;

import com.ruoyi.sales.domain.ChannelSalesData;
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
import com.ruoyi.purchase.domain.PurchasePaymentPeriod;
import com.ruoyi.purchase.service.IPurchasePaymentPeriodService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 采购账期Controller
 *
 * @author ZMJ
 * @date 2025-09-04
 */
@RestController
@RequestMapping("/purchase/paymentPeriod")
public class PurchasePaymentPeriodController extends BaseController
{
    @Autowired
    private IPurchasePaymentPeriodService purchasePaymentPeriodService;

    /**
     * 查询采购账期列表
     */
    @PreAuthorize("@ss.hasPermi('purchase:paymentPeriod:list')")
    @GetMapping("/list")
    public TableDataInfo list(PurchasePaymentPeriod purchasePaymentPeriod)
    {
        startPage();
        List<PurchasePaymentPeriod> list = purchasePaymentPeriodService.selectPurchasePaymentPeriodList(purchasePaymentPeriod);
        return getDataTable(list);
    }

    /**
     * 导出采购账期列表
     */
    @PreAuthorize("@ss.hasPermi('purchase:paymentPeriod:export')")
    @Log(title = "采购账期", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PurchasePaymentPeriod purchasePaymentPeriod)
    {
        List<PurchasePaymentPeriod> list = purchasePaymentPeriodService.selectPurchasePaymentPeriodList(purchasePaymentPeriod);
        ExcelUtil<PurchasePaymentPeriod> util = new ExcelUtil<PurchasePaymentPeriod>(PurchasePaymentPeriod.class);
        util.exportExcel(response, list, "采购账期数据");
    }

    /**
     * 获取采购账期详细信息
     */
    @PreAuthorize("@ss.hasPermi('purchase:paymentPeriod:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(purchasePaymentPeriodService.selectPurchasePaymentPeriodById(id));
    }

    /**
     * 新增采购账期
     */
    @PreAuthorize("@ss.hasPermi('purchase:paymentPeriod:add')")
    @Log(title = "采购账期", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PurchasePaymentPeriod purchasePaymentPeriod)
    {
        purchasePaymentPeriod.setUpdateBy(getUsername());
        return toAjax(purchasePaymentPeriodService.insertPurchasePaymentPeriod(purchasePaymentPeriod));
    }

    /**
     * 修改采购账期
     */
    @PreAuthorize("@ss.hasPermi('purchase:paymentPeriod:edit')")
    @Log(title = "采购账期", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PurchasePaymentPeriod purchasePaymentPeriod)
    {
        purchasePaymentPeriod.setUpdateBy(getUsername());
        return toAjax(purchasePaymentPeriodService.updatePurchasePaymentPeriod(purchasePaymentPeriod));
    }

    /**
     * 删除采购账期
     */
    @PreAuthorize("@ss.hasPermi('purchase:paymentPeriod:remove')")
    @Log(title = "采购账期", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(purchasePaymentPeriodService.deletePurchasePaymentPeriodByIds(ids));
    }

    /**
     * 导入数据
     */
    @Log(title = "销售数据管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('purchase:paymentPeriod:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<PurchasePaymentPeriod> util = new ExcelUtil<>(PurchasePaymentPeriod.class);
        List<PurchasePaymentPeriod> purchasePaymentPeriodList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = purchasePaymentPeriodService.importData(purchasePaymentPeriodList, operName);
        return success(message);
    }

    /**
     * 导入模板下载
     * @param response
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<PurchasePaymentPeriod> util = new ExcelUtil<>(PurchasePaymentPeriod.class);
        util.importTemplateExcel(response, "采购账期表模板");
    }
}
