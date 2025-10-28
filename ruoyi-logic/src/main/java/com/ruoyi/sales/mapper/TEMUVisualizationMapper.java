package com.ruoyi.sales.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.sales.dto.TEMUVisualQuery;
import org.apache.ibatis.annotations.Param;

/**
 * TEMU可视化数据Mapper接口
 */
public interface TEMUVisualizationMapper {

    /**
     * 获取核心KPI数据
     */
    Map<String, Object> selectKpiData(@Param("query") TEMUVisualQuery query,
                                      @Param("user") SysUser user,
                                      @Param("postCodes") List<String> postCodes,
                                      @Param("roleKeys") List<String> roleKeys);

    /**
     * 获取平均指标数据
     */
    Map<String, Object> selectAverageMetrics(@Param("query") TEMUVisualQuery query,
                                             @Param("user") SysUser user,
                                             @Param("postCodes") List<String> postCodes,
                                             @Param("roleKeys") List<String> roleKeys);

    /**
     * 获取预警数据
     */
    List<Map<String, Object>> selectAlertData(@Param("query") TEMUVisualQuery query,
                                              @Param("user") SysUser user,
                                              @Param("postCodes") List<String> postCodes,
                                              @Param("roleKeys") List<String> roleKeys);

    /**
     * 获取筛选选项
     */
    Map<String, Object> selectFilterOptions();

    /**
     * 获取运营员透视数据
     */
    List<Map<String, Object>> selectOperatorPerspectiveData(@Param("query") TEMUVisualQuery query,
                                                            @Param("user") SysUser user,
                                                            @Param("postCodes") List<String> postCodes,
                                                            @Param("roleKeys") List<String> roleKeys);

    /**
     * 获取开发员透视数据
     */
    List<Map<String, Object>> selectDeveloperPerspectiveData(@Param("query") TEMUVisualQuery query,
                                                             @Param("user") SysUser user,
                                                             @Param("postCodes") List<String> postCodes,
                                                             @Param("roleKeys") List<String> roleKeys);

    /**
     * 获取品类透视数据
     */
    List<Map<String, Object>> selectCategoryPerspectiveData(@Param("query") TEMUVisualQuery query,
                                                            @Param("user") SysUser user,
                                                            @Param("postCodes") List<String> postCodes,
                                                            @Param("roleKeys") List<String> roleKeys);

    /**
     * 获取运营员发货仓库数据
     */
    List<Map<String, Object>> selectOperatorWarehouseData(@Param("query") TEMUVisualQuery query,
                                                          @Param("user") SysUser user,
                                                          @Param("postCodes") List<String> postCodes,
                                                          @Param("roleKeys") List<String> roleKeys);

    /**
     * 获取开发员发货仓库数据
     */
    List<Map<String, Object>> selectDeveloperWarehouseData(@Param("query") TEMUVisualQuery query,
                                                           @Param("user") SysUser user,
                                                           @Param("postCodes") List<String> postCodes,
                                                           @Param("roleKeys") List<String> roleKeys);
}
