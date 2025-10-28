package com.ruoyi.sales.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.sales.domain.OverseasHostingData;
import com.ruoyi.sales.domain.OverseasHostingDimensionSummary;
import com.ruoyi.sales.domain.FeeItemSummary;
import com.ruoyi.sales.domain.TEMUOrderDetails;
import org.apache.ibatis.annotations.Param;

/**
 * 海外托管业务数据管理Mapper接口
 *
 * @author ZMJ
 * @date 2025-09-30
 */
public interface OverseasHostingDataMapper
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
     * 查询海外托管业务数据管理列表权限数据过滤
     *
     * @param overseasHostingData 海外托管业务数据管理
     * @param user 当前用户
     * @param postCodes 用户岗位编码列表
     * @return 海外托管业务数据管理集合
     */
    public List<OverseasHostingData> selectOverseasHostingDataListWithPermission(@Param("data") OverseasHostingData overseasHostingData,
                                                                                 @Param("user") SysUser user,
                                                                                 @Param("postCodes") List<String> postCodes,
                                                                                 @Param("roleKeys") List<String> roleKeys);

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
     * 删除海外托管业务数据管理
     *
     * @param id 海外托管业务数据管理主键
     * @return 结果
     */
    public int deleteOverseasHostingDataById(Integer id);

    /**
     * 批量删除海外托管业务数据管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOverseasHostingDataByIds(Integer[] ids);

    //----------- 数据大屏 ---------------
    /**
     * 按运营分组汇总数据
     *
     * @return 运营维度汇总列表
     */
    public List<OverseasHostingDimensionSummary> selectSummaryByOperator(@Param("warehouseList") List<String> warehouseList,
                                                                         @Param("user") SysUser user,
                                                                         @Param("postCodes") List<String> postCodes,
                                                                         @Param("roleKeys") List<String> roleKeys);

    /**
     * 按开发员分组汇总数据
     *
     * @return 开发员维度汇总列表
     */
    public List<OverseasHostingDimensionSummary> selectSummaryByDeveloper(@Param("warehouseList") List<String> warehouseList,
                                                                          @Param("user") SysUser user,
                                                                          @Param("postCodes") List<String> postCodes,
                                                                          @Param("roleKeys") List<String> roleKeys);

    /**
     * 按品牌分组汇总数据
     *
     * @return 品牌维度汇总列表
     */
    public List<OverseasHostingDimensionSummary> selectSummaryByBrand(@Param("warehouseList") List<String> warehouseList,
                                                                      @Param("user") SysUser user,
                                                                      @Param("postCodes") List<String> postCodes,
                                                                      @Param("roleKeys") List<String> roleKeys);

    /**
     * 按仓库分组汇总数据
     *
     * @return 仓库维度汇总列表
     */
    public List<OverseasHostingDimensionSummary> selectSummaryByWarehouse(@Param("warehouseList") List<String> warehouseList,
                                                                          @Param("user") SysUser user,
                                                                          @Param("postCodes") List<String> postCodes,
                                                                          @Param("roleKeys") List<String> roleKeys);

    /**
     * 按类目分组汇总数据
     *
     * @return 类目维度汇总列表
     */
    public List<OverseasHostingDimensionSummary> selectSummaryByCategory(@Param("warehouseList") List<String> warehouseList,
                                                                         @Param("user") SysUser user,
                                                                         @Param("postCodes") List<String> postCodes,
                                                                         @Param("roleKeys") List<String> roleKeys);

    /**
     * 按SKU分组汇总数据
     *
     * @return SKU维度汇总列表
     */
    public List<OverseasHostingDimensionSummary> selectSummaryBySku(@Param("warehouseList") List<String> warehouseList,
                                                                    @Param("user") SysUser user,
                                                                    @Param("postCodes") List<String> postCodes,
                                                                    @Param("roleKeys") List<String> roleKeys);

    /**
     * 按费用项分组汇总金额
     *
     * @return 费用项汇总列表
     */
    public List<FeeItemSummary> selectSummaryByFeeItem(@Param("warehouseList") List<String> warehouseList,
                                                       @Param("user") SysUser user,
                                                       @Param("postCodes") List<String> postCodes,
                                                       @Param("roleKeys") List<String> roleKeys);

    /**
     * 按品牌和类目分组汇总数据
     *
     * @return 品牌类目维度汇总列表
     */
    public List<Map<String, Object>> selectSummaryByBrandAndCategory(@Param("warehouseList") List<String> warehouseList,
                                                                     @Param("user") SysUser user,
                                                                     @Param("postCodes") List<String> postCodes,
                                                                     @Param("roleKeys") List<String> roleKeys);

    /**
     * 按月份分组汇总当前年份数据
     *
     * @return 月度汇总列表
     */
    public List<Map<String, Object>> selectSummaryByMonthly(@Param("warehouseList") List<String> warehouseList,
                                                            @Param("user") SysUser user,
                                                            @Param("postCodes") List<String> postCodes,
                                                            @Param("roleKeys") List<String> roleKeys);

    /**
     * 获取总体统计数据
     *
     * @return 总体统计数据
     */
    Map<String, Object> selectSummaryByTotal(@Param("warehouseList") List<String> warehouseList,
                                             @Param("user") SysUser user,
                                             @Param("postCodes") List<String> postCodes,
                                             @Param("roleKeys") List<String> roleKeys);

    /**
     * 获取核心费用项汇总数据
     *
     * @return 核心费用项汇总数据
     */
    Map<String, Object> selectSummaryByCoreExpenses(@Param("warehouseList") List<String> warehouseList,
                                                    @Param("user") SysUser user,
                                                    @Param("postCodes") List<String> postCodes,
                                                    @Param("roleKeys") List<String> roleKeys);

    /**
     * 获取仓库信息
     *
     * @return 仓库信息
     */
    List<String> selectWarehouse();
}
