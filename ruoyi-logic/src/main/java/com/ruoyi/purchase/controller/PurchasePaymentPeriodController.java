package com.ruoyi.purchase.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.ReadListener;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.listener.ImportImageListener;
import com.ruoyi.purchase.domain.PurchasePaymentPeriod;
import com.ruoyi.purchase.dto.PurchasePaymentPeriodUpdateDTO;
import com.ruoyi.purchase.service.IPurchasePaymentPeriodService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    @Autowired
    private RedisCache redisCache;

    private final String PURCHASE_UPDATE_CACHE_KEY_PREFIX = "purchase:update:";

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
        ReadListener<PurchasePaymentPeriod> listener = new ImportImageListener(file.getInputStream());
        List<PurchasePaymentPeriod> purchasePaymentPeriodList = EasyExcel.read(file.getInputStream(), PurchasePaymentPeriod.class, listener).sheet().doReadSync();
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

    /**
     * 统计已付款和未付款数量
     */
    @PreAuthorize("@ss.hasPermi('purchase:paymentPeriod:list')")
    @GetMapping("/countPaymentStatus")
    public AjaxResult countPaymentStatus(PurchasePaymentPeriod purchasePaymentPeriod)
    {
        Map<String, Integer> result = purchasePaymentPeriodService.countPaymentStatus(purchasePaymentPeriod);
        return success(result);
    }

    /**
     * 导出选中条目的数据用于批量修改
     */
    @PreAuthorize("@ss.hasPermi('purchase:paymentPeriod:export')")
    @Log(title = "采购账期", businessType = BusinessType.EXPORT)
    @PostMapping("/exportSelected")
    public void exportSelected(HttpServletResponse response, @RequestParam("ids") String idsStr)
    {
        // 将逗号分隔的字符串转换为数组
        String[] idArray = idsStr.split(",");
        Integer[] ids = Arrays.stream(idArray)
                .map(Integer::valueOf)
                .toArray(Integer[]::new);

        List<PurchasePaymentPeriod> list = purchasePaymentPeriodService.selectPurchasePaymentPeriodByIds(ids);
        List<PurchasePaymentPeriodUpdateDTO> updateDTOList = list.stream().map(purchasePaymentPeriod -> {
            PurchasePaymentPeriodUpdateDTO updateDTO = new PurchasePaymentPeriodUpdateDTO();
            BeanUtils.copyProperties(purchasePaymentPeriod, updateDTO);
            return updateDTO;
        }).collect(Collectors.toList());
        // 将选中的ID缓存到Redis中，用于批量修改，60分钟后过期
        // 在前端中同时进行限制，超过60分钟则需要重新导出，或者在时间结束之前续时
        redisCache.setCacheObject(PURCHASE_UPDATE_CACHE_KEY_PREFIX + getUsername(), ids, 60 * 60, TimeUnit.SECONDS);
        ExcelUtil<PurchasePaymentPeriodUpdateDTO> util = new ExcelUtil<>(PurchasePaymentPeriodUpdateDTO.class);
        util.exportExcel(response, updateDTOList, "采购账期批量修改模板");
    }

    /**
     * 批量更新采购账期数据
     */
    @PreAuthorize("@ss.hasPermi('purchase:paymentPeriod:edit')")
    @Log(title = "采购账期", businessType = BusinessType.UPDATE)
    @PostMapping("/batchUpdate")
    public AjaxResult batchUpdate(MultipartFile file) throws Exception {
        // 获取缓存中的ID
        List<Integer> ids = redisCache.getCacheObject(PURCHASE_UPDATE_CACHE_KEY_PREFIX + getUsername());
        if (ids == null || ids.isEmpty()) {
            return error("请先导出数据");
        }
        // 将list转为数组
        Integer[] idsArray = ids.toArray(new Integer[0]);
        ExcelUtil<PurchasePaymentPeriodUpdateDTO> util = new ExcelUtil<>(PurchasePaymentPeriodUpdateDTO.class);
        List<PurchasePaymentPeriodUpdateDTO> updateDTOList = util.importEasyExcel(file.getInputStream());

        String operName = getUsername();
        String message = purchasePaymentPeriodService.batchUpdateData(updateDTOList, idsArray, operName);
        redisCache.deleteObject(PURCHASE_UPDATE_CACHE_KEY_PREFIX + operName);
        return success(message);
    }

}
