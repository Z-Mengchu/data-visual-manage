package com.ruoyi.sales.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.sales.domain.FeeItemSummary;
import com.ruoyi.sales.domain.OverseasHostingData;
import com.ruoyi.sales.domain.OverseasHostingDimensionSummary;
import com.ruoyi.sales.domain.TEMUOrderDetails;
import com.ruoyi.system.domain.SysPost;

/**
 * 海外托管业务数据管理Service接口
 *
 * @author ZMJ
 * @date 2025-09-30
 */
public interface IOverseasHostingDataService
{
    /**
     * 查询海外托管业务数据管理
     *
     * @param id 海外托管业务数据管理主键
     * @return 海外托管业务数据管理
     */
    public OverseasHostingData selectOverseasHostingDataById(Integer id);

    /**
     * 查询海外托管业务数据管理列表
     *
     * @param overseasHostingData 海外托管业务数据管理
     * @return 海外托管业务数据管理集合
     */
    public List<OverseasHostingData> selectOverseasHostingDataList(OverseasHostingData overseasHostingData, SysUser currentUser, List<SysPost> userPosts);

    /**
     * 新增海外托管业务数据管理
     *
     * @param overseasHostingData 海外托管业务数据管理
     * @return 结果
     */
    public int insertOverseasHostingData(OverseasHostingData overseasHostingData);

    /**
     * 修改海外托管业务数据管理
     *
     * @param overseasHostingData 海外托管业务数据管理
     * @return 结果
     */
    public int updateOverseasHostingData(OverseasHostingData overseasHostingData);

    /**
     * 批量删除海外托管业务数据管理
     *
     * @param ids 需要删除的海外托管业务数据管理主键集合
     * @return 结果
     */
    public int deleteOverseasHostingDataByIds(Integer[] ids);

    /**
     * 删除海外托管业务数据管理信息
     *
     * @param id 海外托管业务数据管理主键
     * @return 结果
     */
    public int deleteOverseasHostingDataById(Integer id);

    /**
     * 导入海外托管数据
     *
     * @param overseasHostingData 待导入的数据列表
     * @param operName 操作人员
     * @return 结果
     */
    public String importOverseasData(List<OverseasHostingData> overseasHostingData, String operName);

    //----------- 数据大屏 ---------------

    /**
     * 按运营分组汇总数据
     */
    public List<OverseasHostingDimensionSummary> getSummaryByOperator(String country);
    /**
     * 按开发员分组汇总数据
     */
    public List<OverseasHostingDimensionSummary> getSummaryByDeveloper(String country);
    /**
     * 按品牌分组汇总数据
     */
    public List<OverseasHostingDimensionSummary> getSummaryByBrand(String country);
    /**
     * 按仓库分组汇总数据
     */
    public List<OverseasHostingDimensionSummary> getSummaryByWarehouse(String country);
    /**
     * 按类目分组汇总数据
     */
    public List<OverseasHostingDimensionSummary> getSummaryByCategory(String country);
    /**
     * 按SKU分组汇总数据
     */
    public List<OverseasHostingDimensionSummary> getSummaryBySku(String country);
    /**
     * 按费用项分组汇总金额
     */
    public List<FeeItemSummary> getSummaryByFeeItem(String country);
    /**
     * 按品牌和类目分组汇总数据
     */
    public List<Map<String, Object>> getSummaryByBrandAndCategory(String country);
    /**
     * 按月份分组汇总当前年份数据
     */
    public List<Map<String, Object>> getSummaryByMonthly(String country);
    /**
     * 获取总体统计数据
     */
    Map<String, Object> getSummaryByTotal(String country);
    /**
     * 获取核心费用项汇总数据
     */
    Map<String, Object> getSummaryByCoreExpenses(String country);

    /**
     * 获取所有仓库名称
     *
     * @return 所有仓库名称列表
     */
    public List<String> getAllCountry();

    /**
     * 获取指定国家的仓库列表
     * @param country 国家名称
     * @return 仓库列表
     */
    public List<String> getWarehouseList(String country);
}
