package com.ruoyi.sales.controller;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.sales.domain.ChannelSalesData;
import com.ruoyi.sales.service.IChannelSalesDataService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 全渠道销售数据分析Controller
 *
 * @author ZMJ
 * @date 2025-09-03
 */
@RestController
@RequestMapping("/sale/data")
public class ChannelSalesDataController extends BaseController
{
    @Autowired
    private IChannelSalesDataService channelSalesDataService;

    @Autowired
    private ISysPostService postService;

    /**
     * 查询全渠道销售数据分析列表
     */
    @PreAuthorize("@ss.hasPermi('sale:data:list')")
    @GetMapping("/list")
    public TableDataInfo list(ChannelSalesData channelSalesData)
    {
        // 获取当前登录用户
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();

        // 获取用户岗位列表
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        startPage();
        List<ChannelSalesData> list = channelSalesDataService.selectChannelSalesDataList(channelSalesData, currentUser, userPosts);
        return getDataTable(list);
    }

    /**
     * 导出全渠道销售数据分析列表
     */
    @PreAuthorize("@ss.hasPermi('sale:data:export')")
    @Log(title = "全渠道销售数据分析", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ChannelSalesData channelSalesData)
    {
        // 获取当前登录用户
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        // 获取用户岗位列表
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<ChannelSalesData> list = channelSalesDataService.selectChannelSalesDataList(channelSalesData, currentUser, userPosts);
        ExcelUtil<ChannelSalesData> util = new ExcelUtil<ChannelSalesData>(ChannelSalesData.class);
        util.exportExcel(response, list, "全渠道销售数据分析数据");
    }

    /**
     * 获取全渠道销售数据分析详细信息
     */
    @PreAuthorize("@ss.hasPermi('sale:data:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(channelSalesDataService.selectChannelSalesDataById(id));
    }

    /**
     * 新增全渠道销售数据分析
     */
    @PreAuthorize("@ss.hasPermi('sale:data:add')")
    @Log(title = "全渠道销售数据分析", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ChannelSalesData channelSalesData)
    {
        channelSalesData.setUpdateBy(getUsername());
        return toAjax(channelSalesDataService.insertChannelSalesData(channelSalesData));
    }

    /**
     * 修改全渠道销售数据分析
     */
    @PreAuthorize("@ss.hasPermi('sale:data:edit')")
    @Log(title = "全渠道销售数据分析", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ChannelSalesData channelSalesData)
    {
        channelSalesData.setUpdateBy(getUsername());
        return toAjax(channelSalesDataService.updateChannelSalesData(channelSalesData));
    }

    /**
     * 删除全渠道销售数据分析
     */
    @PreAuthorize("@ss.hasPermi('sale:data:remove')")
    @Log(title = "全渠道销售数据分析", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(channelSalesDataService.deleteChannelSalesDataByIds(ids));
    }

    /**
     * 导入数据
     */
    @Log(title = "销售数据管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('sale:data:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<ChannelSalesData> util = new ExcelUtil<>(ChannelSalesData.class);
        List<ChannelSalesData> salesDataList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = channelSalesDataService.importSalesData(salesDataList, updateSupport, operName);
        return success(message);
    }

    /**
     * 导入模板下载
     * @param response
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<ChannelSalesData> util = new ExcelUtil<>(ChannelSalesData.class);
        util.importTemplateExcel(response, "全渠道销售数据模板");
    }
}
