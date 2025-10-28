package com.ruoyi.sales.mapper;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.sales.domain.ChannelSalesData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 全渠道销售数据可视化Mapper接口
 */
public interface ChannelSalesDataVisualMapper {

    /**
     * 获取核心KPI数据
     */
    Map<String, Object> selectKpiData(ChannelSalesData channelSalesData);

    /**
     * 获取品类退款率数据
     */
    List<Map<String, Object>> selectCategoryRefundData(ChannelSalesData channelSalesData);

    /**
     * 获取SKU ROI数据
     */
    List<Map<String, Object>> selectSkuRoiData(ChannelSalesData channelSalesData);

    /**
     * 获取一级品类数据
     */
    List<Map<String, Object>> selectFirstLevelCategoryData(ChannelSalesData channelSalesData);

    /**
     * 获取渠道数据
     */
    List<Map<String, Object>> selectChannelData(ChannelSalesData channelSalesData);

    /**
     * 获取品牌数据
     */
    List<Map<String, Object>> selectBrandData(ChannelSalesData channelSalesData);

    /**
     * 获取国家数据
     */
    List<Map<String, Object>> selectCountryData(ChannelSalesData channelSalesData);

    /**
     * 获取店铺数据
     */
    List<Map<String, Object>> selectStoreData(ChannelSalesData channelSalesData);

    /**
     * 获取地图数据
     */
    List<Map<String, Object>> selectMapData(ChannelSalesData channelSalesData);

    /**
     * 获取渠道销售额占比数据
     */
    List<Map<String, Object>> selectChannelDistributionData(ChannelSalesData channelSalesData);

    /**
     * 获取预警数据
     */
    List<Map<String, Object>> selectAlertData(ChannelSalesData channelSalesData);

    /**
     * 获取趋势数据
     */
    List<Map<String, Object>> selectTrendData(ChannelSalesData channelSalesData);

    /**
     * 获取详情表格数据
     */
    List<Map<String, Object>> selectDetailData(ChannelSalesData channelSalesData);

    /**
     * 获取筛选条件选项
     */
    Map<String, List<String>> selectFilterOptions();

    /**
     * 获取核心KPI数据（带权限）
     */
    Map<String, Object> selectKpiDataWithPermission(@Param("data") ChannelSalesData channelSalesData,
                                                    @Param("user") SysUser user,
                                                    @Param("postCodes") List<String> postCodes,
                                                    @Param("roleKeys") List<String> roleKeys);
    /**
     * 获取品类退款率数据（带权限）
     */
    List<Map<String, Object>> selectCategoryRefundDataWithPermission(@Param("data") ChannelSalesData channelSalesData,
                                                                     @Param("user") SysUser user,
                                                                     @Param("postCodes") List<String> postCodes,
                                                                     @Param("roleKeys") List<String> roleKeys);
    /**
     * 获取SKU ROI数据（带权限）
     */
    List<Map<String, Object>> selectSkuRoiDataWithPermission(@Param("data") ChannelSalesData channelSalesData,
                                                             @Param("user") SysUser user,
                                                             @Param("postCodes") List<String> postCodes,
                                                             @Param("roleKeys") List<String> roleKeys);
    /**
     * 获取一级品类数据（带权限）
     */
    List<Map<String, Object>> selectFirstLevelCategoryDataWithPermission(@Param("data") ChannelSalesData channelSalesData,
                                                                         @Param("user") SysUser user,
                                                                         @Param("postCodes") List<String> postCodes,
                                                                         @Param("roleKeys") List<String> roleKeys);
    /**
     * 获取渠道数据（带权限）
     */
    List<Map<String, Object>> selectChannelDataWithPermission(@Param("data") ChannelSalesData channelSalesData,
                                                              @Param("user") SysUser user,
                                                              @Param("postCodes") List<String> postCodes,
                                                              @Param("roleKeys") List<String> roleKeys);
    /**
     * 获取品牌数据（带权限）
     */
    List<Map<String, Object>> selectBrandDataWithPermission(@Param("data") ChannelSalesData channelSalesData,
                                                            @Param("user") SysUser user,
                                                            @Param("postCodes") List<String> postCodes,
                                                            @Param("roleKeys") List<String> roleKeys);
    /**
     * 获取国家数据（带权限）
     */
    List<Map<String, Object>> selectCountryDataWithPermission(@Param("data") ChannelSalesData channelSalesData,
                                                              @Param("user") SysUser user,
                                                              @Param("postCodes") List<String> postCodes,
                                                              @Param("roleKeys") List<String> roleKeys);
    /**
     * 获取店铺数据（带权限）
     */
    List<Map<String, Object>> selectStoreDataWithPermission(@Param("data") ChannelSalesData channelSalesData,
                                                            @Param("user") SysUser user,
                                                            @Param("postCodes") List<String> postCodes,
                                                            @Param("roleKeys") List<String> roleKeys);
    /**
     * 获取地图数据（带权限）
     */
    List<Map<String, Object>> selectMapDataWithPermission(@Param("data") ChannelSalesData channelSalesData,
                                                          @Param("user") SysUser user,
                                                          @Param("postCodes") List<String> postCodes,
                                                          @Param("roleKeys") List<String> roleKeys);
    /**
     * 获取渠道销售额占比数据（带权限）
     */
    List<Map<String, Object>> selectChannelDistributionDataWithPermission(@Param("data") ChannelSalesData channelSalesData,
                                                                          @Param("user") SysUser user,
                                                                          @Param("postCodes") List<String> postCodes,
                                                                          @Param("roleKeys") List<String> roleKeys);
    /**
     * 获取预警数据（带权限）
     */
    List<Map<String, Object>> selectAlertDataWithPermission(@Param("data") ChannelSalesData channelSalesData,
                                                            @Param("user") SysUser user,
                                                            @Param("postCodes") List<String> postCodes,
                                                            @Param("roleKeys") List<String> roleKeys);
    /**
     * 获取趋势数据（带权限）
     */
    List<Map<String, Object>> selectTrendDataWithPermission(@Param("data") ChannelSalesData channelSalesData,
                                                            @Param("user") SysUser user,
                                                            @Param("postCodes") List<String> postCodes,
                                                            @Param("roleKeys") List<String> roleKeys);
    /**
     * 获取详情表格数据（带权限）
     */
    List<Map<String, Object>> selectDetailDataWithPermission(@Param("data") ChannelSalesData channelSalesData,
                                                             @Param("user") SysUser user,
                                                             @Param("postCodes") List<String> postCodes,
                                                             @Param("roleKeys") List<String> roleKeys);
}
