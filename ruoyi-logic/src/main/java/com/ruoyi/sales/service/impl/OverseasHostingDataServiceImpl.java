package com.ruoyi.sales.service.impl;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.sales.domain.FeeItemSummary;
import com.ruoyi.sales.domain.OverseasHostingData;
import com.ruoyi.sales.domain.OverseasHostingDimensionSummary;
import com.ruoyi.sales.dto.PostDataFilterQueryParams;
import com.ruoyi.sales.mapper.OverseasHostingDataMapper;
import com.ruoyi.sales.service.IOverseasHostingDataService;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.ISysPostService;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 海外托管业务数据管理Service业务层处理
 *
 * @author ZMJ
 * @date 2025-09-30
 */
@Service
public class OverseasHostingDataServiceImpl implements IOverseasHostingDataService
{
    private static final Logger log = LoggerFactory.getLogger(OverseasHostingDataServiceImpl.class);

    private static final Map<String, List<String>> countryWarehouseMap = new HashMap<>();

    // 仓库映射
    // TODO 暂时使用静态代码块，后续需要提供一个功能进行国家和仓库的映射
    static {
        countryWarehouseMap.put("波兰", List.of("递四方波兰1仓（实际用）"));
        countryWarehouseMap.put("德国", List.of("递四方德国仓"));
        countryWarehouseMap.put("法国", List.of("递四方法国巴黎1仓"));
        countryWarehouseMap.put("美国", List.of("递四方美国仓"));
        countryWarehouseMap.put("西班牙", List.of("递四方西班牙马德里仓"));
        countryWarehouseMap.put("英国", List.of("递四方英国路腾仓(新)"));
        countryWarehouseMap.put("未分类国家", List.of(""));
    }

    @Autowired
    private OverseasHostingDataMapper overseasHostingDataMapper;

    @Autowired
    protected Validator validator;

    @Autowired
    private ISysPostService postService;

    /**
     * 查询海外托管业务数据管理
     *
     * @param id 海外托管业务数据管理主键
     * @return 海外托管业务数据管理
     */
    @Override
    public OverseasHostingData selectOverseasHostingDataById(Integer id)
    {
        return overseasHostingDataMapper.selectOverseasHostingDataById(id);
    }

    /**
     * 查询海外托管业务数据管理列表
     *
     * @param overseasHostingData 海外托管业务数据管理
     * @return 海外托管业务数据管理
     */
    @Override
    public List<OverseasHostingData> selectOverseasHostingDataList(OverseasHostingData overseasHostingData, SysUser currentUser, List<SysPost> userPosts)
    {
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).toList();
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();

        return overseasHostingDataMapper.selectOverseasHostingDataListWithPermission(overseasHostingData, currentUser, postCodes, roleKeys);
    }

    /**
     * 新增海外托管业务数据管理
     *
     * @param overseasHostingData 海外托管业务数据管理
     * @return 结果
     */
    @Override
    public int insertOverseasHostingData(OverseasHostingData overseasHostingData)
    {
        return overseasHostingDataMapper.insertOverseasHostingData(overseasHostingData);
    }

    /**
     * 修改海外托管业务数据管理
     *
     * @param overseasHostingData 海外托管业务数据管理
     * @return 结果
     */
    @Override
    public int updateOverseasHostingData(OverseasHostingData overseasHostingData)
    {
        return overseasHostingDataMapper.updateOverseasHostingData(overseasHostingData);
    }

    /**
     * 批量删除海外托管业务数据管理
     *
     * @param ids 需要删除的海外托管业务数据管理主键
     * @return 结果
     */
    @Override
    public int deleteOverseasHostingDataByIds(Integer[] ids)
    {
        return overseasHostingDataMapper.deleteOverseasHostingDataByIds(ids);
    }

    /**
     * 删除海外托管业务数据管理信息
     *
     * @param id 海外托管业务数据管理主键
     * @return 结果
     */
    @Override
    public int deleteOverseasHostingDataById(Integer id)
    {
        return overseasHostingDataMapper.deleteOverseasHostingDataById(id);
    }

    /**
     * 导入数据
     *
     * @param overseasHostingData 数据列表
     * @param operName 操作人员
     * @return 结果
     */
    @Override
    public String importOverseasData(List<OverseasHostingData> overseasHostingData, String operName) {
        if (StringUtils.isNull(overseasHostingData) || overseasHostingData.isEmpty())
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (OverseasHostingData overseasData : overseasHostingData)
        {
            try
            {
                if (overseasData.getCategory().equals("42")) overseasData.setCategory("");
                // 忘记42是什么了，反正是读取错误数据自动转换成了42，所以改成空字符串
                BeanValidators.validateWithException(validator, overseasData);

                // 判断这条数据在数据库中是否已经存在完全相同的数据
                // 先通过相同订单号查询数据
                if (overseasData.getOrderNumber().isEmpty()) overseasData.setOrderNumber(null);
                List<OverseasHostingData> willEqualDataList = overseasHostingDataMapper.selectOverseasHostingDataByOrderNumber(overseasData.getOrderNumber());

                boolean isExist = false;
                // 再通过重写equal方法比较两者是否完全相同
                for (OverseasHostingData hostingData : willEqualDataList) {
                    if (hostingData.equals(overseasData)) {
                        isExist = true;
                        break;
                    }
                }
                willEqualDataList.clear();
                if (isExist) {
                    // 数据已存在进行提示
                    failureNum++;
                    String msg = "<br/>第 " + (failureNum + successNum) + "条数据已存在，请勿重复导入";
                    failureMsg.append(msg);
                }else {
                    // 数据不存在，可以插入
                    overseasHostingDataMapper.insertOverseasHostingData(overseasData);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、订单号 ").append(overseasData.getOrderNumber()).append(" 导入成功");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>第 " + (failureNum + successNum) + "条数据导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        if (failureNum > 0)
        {
            if (successNum == 0) successMsg.delete(0, successMsg.length());
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(successMsg + "\n" + failureMsg);
        }
        return successMsg.toString();
    }

    //----------- 数据大屏 ---------------

    /**
     * 按运营分组汇总数据
     *
     * @return 运营维度汇总列表
     */
    @Override
    public List<OverseasHostingDimensionSummary> getSummaryByOperator(String country, String beginSettlementDate, String endSettlementDate) {
        List<String> warehouseList = getWarehouseList(country);
        return getSummaryByDimension(params -> overseasHostingDataMapper.selectSummaryByOperator(
                warehouseList, params.getCurrentUser(),
                        params.getPostCodes(), params.getRoleKeys(), beginSettlementDate, endSettlementDate));
    }
    /**
     * 按开发员分组汇总数据
     *
     * @return 开发员维度汇总列表
     */
    @Override
    public List<OverseasHostingDimensionSummary> getSummaryByDeveloper(String country, String beginSettlementDate, String endSettlementDate) {
        List<String> warehouseList = getWarehouseList(country);
        return getSummaryByDimension(params -> overseasHostingDataMapper.selectSummaryByDeveloper(
                warehouseList, params.getCurrentUser(),
                params.getPostCodes(), params.getRoleKeys(), beginSettlementDate, endSettlementDate));
    }
    /**
     * 按品牌分组汇总数据
     *
     * @return 品牌维度汇总列表
     */
    @Override
    public List<OverseasHostingDimensionSummary> getSummaryByBrand(String country, String beginSettlementDate, String endSettlementDate) {
        List<String> warehouseList = getWarehouseList(country);
        return getSummaryByDimension(params -> overseasHostingDataMapper.selectSummaryByBrand(
                warehouseList, params.getCurrentUser(),
                params.getPostCodes(), params.getRoleKeys(), beginSettlementDate, endSettlementDate));
    }
    /**
     * 按仓库分组汇总数据
     *
     * @return 仓库维度汇总列表
     */
    @Override
    public List<OverseasHostingDimensionSummary> getSummaryByWarehouse(String country, String beginSettlementDate, String endSettlementDate) {
        List<String> warehouseList = getWarehouseList(country);
        return getSummaryByDimension(params -> overseasHostingDataMapper.selectSummaryByWarehouse(
                warehouseList, params.getCurrentUser(),
                params.getPostCodes(), params.getRoleKeys(), beginSettlementDate, endSettlementDate));
    }
    /**
     * 按类目分组汇总数据
     *
     * @return 类目维度汇总列表
     */
    @Override
    public List<OverseasHostingDimensionSummary> getSummaryByCategory(String country, String beginSettlementDate, String endSettlementDate) {
        List<String> warehouseList = getWarehouseList(country);
        return getSummaryByDimension(params -> overseasHostingDataMapper.selectSummaryByCategory(
                warehouseList, params.getCurrentUser(),
                params.getPostCodes(), params.getRoleKeys(), beginSettlementDate, endSettlementDate));
    }
    /**
     * 按SKU分组汇总数据
     *
     * @return SKU维度汇总列表
     */
    @Override
    public List<OverseasHostingDimensionSummary> getSummaryBySku(String country, String beginSettlementDate, String endSettlementDate) {
        List<String> warehouseList = getWarehouseList(country);
        return getSummaryByDimension(params -> overseasHostingDataMapper.selectSummaryBySku(
                warehouseList, params.getCurrentUser(),
                params.getPostCodes(), params.getRoleKeys(), beginSettlementDate, endSettlementDate));
    }
    /**
     * 按费用项分组汇总金额
     *
     * @return 费用项汇总列表
     */
    @Override
    public List<FeeItemSummary> getSummaryByFeeItem(String country, String beginSettlementDate, String endSettlementDate) {
        List<String> warehouseList = getWarehouseList(country);
        return getSummaryByDimension(params -> overseasHostingDataMapper.selectSummaryByFeeItem(
                warehouseList, params.getCurrentUser(),
                params.getPostCodes(), params.getRoleKeys(), beginSettlementDate, endSettlementDate));
    }
    /**
     * 按品牌和类目分组汇总数据
     *
     * @return 品牌类目维度汇总列表
     */
    @Override
    public List<Map<String, Object>> getSummaryByBrandAndCategory(String country, String beginSettlementDate, String endSettlementDate) {
        List<String> warehouseList = getWarehouseList(country);
        return getSummaryByDimension(params -> overseasHostingDataMapper.selectSummaryByBrandAndCategory(
                warehouseList, params.getCurrentUser(),
                params.getPostCodes(), params.getRoleKeys(), beginSettlementDate, endSettlementDate));
    }
    /**
     * 按月份分组汇总当前年份数据
     *
     * @return 月度汇总列表
     */
    @Override
    public List<Map<String, Object>> getSummaryByMonthly(String country, String beginSettlementDate, String endSettlementDate) {
        List<String> warehouseList = getWarehouseList(country);
        return getSummaryByDimension(params -> overseasHostingDataMapper.selectSummaryByMonthly(
                warehouseList, params.getCurrentUser(),
                params.getPostCodes(), params.getRoleKeys(), beginSettlementDate, endSettlementDate));
    }
    /**
     * 获取总体统计数据
     *
     * @return 总体统计数据
     */
    @Override
    public Map<String, Object> getSummaryByTotal(String country, String beginSettlementDate, String endSettlementDate) {
        List<String> warehouseList = getWarehouseList(country);
        return getSummaryByDimension(params -> overseasHostingDataMapper.selectSummaryByTotal(
                warehouseList, params.getCurrentUser(),
                params.getPostCodes(), params.getRoleKeys(), beginSettlementDate, endSettlementDate));
    }
    /**
     * 获取核心费用项汇总数据
     *
     * @return 核心费用项汇总数据
     */
    @Override
    public Map<String, Object> getSummaryByCoreExpenses(String country, String beginSettlementDate, String endSettlementDate) {
        List<String> warehouseList = getWarehouseList(country);
        return getSummaryByDimension(params -> overseasHostingDataMapper.selectSummaryByCoreExpenses(
                warehouseList, params.getCurrentUser(),
                params.getPostCodes(), params.getRoleKeys(), beginSettlementDate, endSettlementDate));
    }

    @Override
    public List<String> getAllCountry() {
        List<String> warehouses = overseasHostingDataMapper.selectWarehouse();
        List<String> countries = new ArrayList<>();
        Set<String> countrySet = countryWarehouseMap.keySet();
        for (String warehouse : warehouses) {
            for (String country : countrySet) {
                if (countryWarehouseMap.get(country).contains(warehouse)) {
                    countries.add(country);
                    break;
                }
            }
        }
        return countries;
    }

    @Override
    public List<String> getWarehouseList(String country) {
        if ("未分类国家".equals(country)) {
            List<String> warehouses = overseasHostingDataMapper.selectWarehouse();
            countryWarehouseMap.forEach((key, value) -> warehouses.removeAll(value));
            if (warehouses.isEmpty()) warehouses.add(""); // 没有仓库时不让查询参数为空
            return warehouses;
        }
        if (countryWarehouseMap.containsKey(country)) {
            return countryWarehouseMap.get(country);
        }
        return new ArrayList<>();
    }

    /**
     * 构建数据汇总查询所需的基础参数
     *
     * @return 包含仓库列表、当前用户、岗位编码列表和角色键列表的参数对象
     */
    private PostDataFilterQueryParams buildSummaryQueryParams() {
        SysUser currentUser = SecurityUtils.getLoginUser().getUser();
        List<SysPost> userPosts = postService.selectPostsByUserName(currentUser.getUserName());
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).collect(Collectors.toList());
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();

        return new PostDataFilterQueryParams(currentUser, postCodes, roleKeys);
    }

    /**
     * 按指定维度分组汇总数据的通用方法
     *
     * @param summaryFunction 汇总函数
     * @param <T> 返回值类型
     * @return 汇总结果
     */
    private <T> T getSummaryByDimension(Function<PostDataFilterQueryParams, T> summaryFunction) {
        PostDataFilterQueryParams params = buildSummaryQueryParams();
        return summaryFunction.apply(params);
    }
}
