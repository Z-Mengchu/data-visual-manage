package com.ruoyi.sales.controller;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.sales.domain.ChannelSalesData;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.ISysPostService;
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
import com.ruoyi.sales.domain.TEMUOrderDetails;
import com.ruoyi.sales.service.ITEMUOrderDetailsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * Temu订单明细Controller
 *
 * @author ZMJ
 * @date 2025-09-22
 */
@RestController
@RequestMapping("/temu/details")
public class TEMUOrderDetailsController extends BaseController
{
    @Autowired
    private ITEMUOrderDetailsService tEMUOrderDetailsService;

    @Autowired
    private ISysPostService postService;

    /**
     * 查询Temu订单明细列表
     */
    @PreAuthorize("@ss.hasPermi('temu:details:list')")
    @GetMapping("/list")
    public TableDataInfo list(TEMUOrderDetails tEMUOrderDetails)
    {
        // 获取当前登录用户
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        // 获取用户岗位列表
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        startPage();
        List<TEMUOrderDetails> list = tEMUOrderDetailsService.selectTEMUOrderDetailsList(tEMUOrderDetails, currentUser, userPosts);
        return getDataTable(list);
    }

    /**
     * 导出Temu订单明细列表
     */
    @PreAuthorize("@ss.hasPermi('temu:details:export')")
    @Log(title = "Temu订单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TEMUOrderDetails tEMUOrderDetails)
    {
        // 获取当前登录用户
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        // 获取用户岗位列表
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<TEMUOrderDetails> list = tEMUOrderDetailsService.selectTEMUOrderDetailsList(tEMUOrderDetails, currentUser, userPosts);
        ExcelUtil<TEMUOrderDetails> util = new ExcelUtil<>(TEMUOrderDetails.class);
        util.exportExcel(response, list, "Temu订单明细数据");
    }

    /**
     * 获取Temu订单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('temu:details:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(tEMUOrderDetailsService.selectTEMUOrderDetailsById(id));
    }

    /**
     * 新增Temu订单明细
     */
    @PreAuthorize("@ss.hasPermi('temu:details:add')")
    @Log(title = "Temu订单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TEMUOrderDetails tEMUOrderDetails)
    {
        return toAjax(tEMUOrderDetailsService.insertTEMUOrderDetails(tEMUOrderDetails));
    }

    /**
     * 修改Temu订单明细
     */
    @PreAuthorize("@ss.hasPermi('temu:details:edit')")
    @Log(title = "Temu订单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TEMUOrderDetails tEMUOrderDetails)
    {
        return toAjax(tEMUOrderDetailsService.updateTEMUOrderDetails(tEMUOrderDetails));
    }

    /**
     * 删除Temu订单明细
     */
    @PreAuthorize("@ss.hasPermi('temu:details:remove')")
    @Log(title = "Temu订单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(tEMUOrderDetailsService.deleteTEMUOrderDetailsByIds(ids));
    }


    /**
     * 导入数据
     */
    @Log(title = "TEMU数据管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('temu:data:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<TEMUOrderDetails> util = new ExcelUtil<>(TEMUOrderDetails.class);
        List<TEMUOrderDetails> temuDataList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = tEMUOrderDetailsService.importTEMUData(temuDataList, operName);
        return success(message);
    }

    /**
     * 导入模板下载
     * @param response
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<TEMUOrderDetails> util = new ExcelUtil<>(TEMUOrderDetails.class);
        util.importTemplateExcel(response, "TEMU数据模板");
    }
}
