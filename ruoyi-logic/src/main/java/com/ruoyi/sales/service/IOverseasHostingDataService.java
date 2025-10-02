package com.ruoyi.sales.service;

import java.util.List;

import com.ruoyi.sales.domain.FeeItemSummary;
import com.ruoyi.sales.domain.OverseasHostingData;
import com.ruoyi.sales.domain.OverseasHostingDimensionSummary;
import com.ruoyi.sales.domain.TEMUOrderDetails;

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
    public List<OverseasHostingData> selectOverseasHostingDataList(OverseasHostingData overseasHostingData);

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

    /**
     * 按运营分组汇总数据
     *
     * @return 运营维度汇总列表
     */
    public List<OverseasHostingDimensionSummary> getSummaryByOperator();

    /**
     * 按开发员分组汇总数据
     *
     * @return 开发员维度汇总列表
     */
    public List<OverseasHostingDimensionSummary> getSummaryByDeveloper();

    /**
     * 按品牌分组汇总数据
     *
     * @return 品牌维度汇总列表
     */
    public List<OverseasHostingDimensionSummary> getSummaryByBrand();

    /**
     * 按仓库分组汇总数据
     *
     * @return 仓库维度汇总列表
     */
    public List<OverseasHostingDimensionSummary> getSummaryByWarehouse();

    /**
     * 按类目分组汇总数据
     *
     * @return 类目维度汇总列表
     */
    public List<OverseasHostingDimensionSummary> getSummaryByCategory();

    /**
     * 按SKU分组汇总数据
     *
     * @return SKU维度汇总列表
     */
    public List<OverseasHostingDimensionSummary> getSummaryBySku();

    /**
     * 按费用项分组汇总金额
     *
     * @return 费用项汇总列表
     */
    public List<FeeItemSummary> getSummaryByFeeItem();
}
