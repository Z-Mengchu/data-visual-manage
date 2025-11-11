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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public String importOverseasData(List<OverseasHostingData> overseasHostingData, String operName) {
        if (StringUtils.isNull(overseasHostingData) || overseasHostingData.isEmpty())
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        List<OverseasHostingData> dataToInsert = new ArrayList<>(); // 用于批量插入的非重复数据
        Map<String, List<OverseasHostingData>> existingDataMap = new HashMap<>(); // 存储现有数据，键为订单号
        Set<OverseasHostingData> processedSet = new HashSet<>();    // 用于检查待导入数据内部的重复
        // 步骤1: 预处理数据，并收集所有订单号用于批量查询
        Set<String> nonNullOrderNumbers = overseasHostingData.stream()
                .map(OverseasHostingData::getOrderNumber)
                .filter(StringUtils::isNotEmpty)    // 过滤非空订单号
                .collect(Collectors.toSet());
        boolean hasNullOrderNumber = overseasHostingData.stream()
                .anyMatch(data -> StringUtils.isEmpty(data.getOrderNumber())); // 检查是否存在null订单号
        // 批量查询现有数据，分别处理非空订单号和null订单号
        List<OverseasHostingData> existingDataList = new ArrayList<>();

        // 查询非空订单号的数据
        if (!nonNullOrderNumbers.isEmpty()) {
            existingDataList.addAll(overseasHostingDataMapper.selectOverseasHostingDataByOrderNumbers(nonNullOrderNumbers));
        }
        // 查询null订单号的数据
        if (hasNullOrderNumber) {
            existingDataList.addAll(overseasHostingDataMapper.selectOverseasHostingDataByOrderNumberIsNull());
        }
        // 构建map，键为订单号，值为对应数据列表（支持一个订单号多条数据的情况）
        for (OverseasHostingData data : existingDataList) {
            String key = StringUtils.isEmpty(data.getOrderNumber()) ? "__NULL_ORDER_NUMBER__" : data.getOrderNumber();
            existingDataMap.computeIfAbsent(key, k -> new ArrayList<>()).add(data);
        }
        // 步骤2：遍历数据，进行验证和重复检查（先检查内部重复，再检查数据库重复）
        for (int i = 0; i < overseasHostingData.size(); i++) {
            OverseasHostingData overseasData = overseasHostingData.get(i);
            int currentIndex = i + 1; // 记录当前数据位置
            try {
                BeanValidators.validateWithException(validator, overseasData);
                // 忘记42是什么了，反正是读取错误数据（应该是#N/A）自动转换成了42，所以改成空字符串
                if (overseasData.getCategory().equals("42")) overseasData.setCategory("");
                // 订单号如果为空，则设置为null，在查询数据库时，不忽略订单号为null的数据
                if (StringUtils.isEmpty(overseasData.getOrderNumber())) overseasData.setOrderNumber(null);
                // 检查导入数据内部的重复，使用Set基于equals方法判断
                if (processedSet.contains(overseasData)) {
                    failureNum++;
                    String msg = "<br/>第 " + currentIndex + " 条数据在导入列表中重复，请勿重复导入";
                    failureMsg.append(msg);
                    continue; //跳过后续处理
                }
                processedSet.add(overseasData); // 添加到已处理的数据中
                // 检查与数据库的重复，基于内存中的existingDataMap
                boolean isExistInDb = false;
                // 从Map中获取对应订单号的数据列表（包括null键）
                String mapKey = overseasData.getOrderNumber();
                if (mapKey == null) {
                    mapKey = "__NULL_ORDER_NUMBER__";
                }
                List<OverseasHostingData> existingList = existingDataMap.get(mapKey);
                if (existingList != null) {
                    for (OverseasHostingData existingData : existingList) {
                        if(existingData.equals(overseasData)){
                            isExistInDb = true;
                            break;
                        }
                    }
                }
                if (isExistInDb) {
                    failureNum++;
                    String msg = "<br/>第 " + currentIndex + " 条数据在数据库中已存在，请勿重复导入";
                    failureMsg.append(msg);
                } else {
                    // 添加到批量插入列表
                    dataToInsert.add(overseasData);
                    successNum++; // 假设批量插入成功，暂时记录数量
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>第 " + currentIndex + " 条数据导入失败：" + e.getMessage();
                failureMsg.append(msg);
                log.error(msg, e);
            }
        }
        // 步骤3：批量插入非重复数据
        if (!dataToInsert.isEmpty()) {
            try {
                // 执行批量插入
                int batchSize = 1000;
                for (int i = 0; i < dataToInsert.size(); i += batchSize) {
                    int end = Math.min(dataToInsert.size(), i + batchSize);
                    List<OverseasHostingData> batchList = dataToInsert.subList(i, end);
                    overseasHostingDataMapper.batchInsertOverseasHostingData(batchList);
                }
                successMsg.append("恭喜您，数据已全部导入成功！共 ").append(successNum).append(" 条，数据如下：");
                for (int i = 0; i < dataToInsert.size(); i++) {
                    OverseasHostingData data = dataToInsert.get(i);
                    successMsg.append("<br/>").append(i + 1).append("、订单号 ").append(data.getOrderNumber() == null ? "空" : data.getOrderNumber()).append(" 导入成功");
                }
            } catch (Exception e) {
                // 如果批量插入失败，回退到逐条处理记录具体错误（确保错误跟踪）
                // 逐条处理以记录具体错误
                failureNum += dataToInsert.size(); // 假设全部失败
                successNum = 0;
                String msg = "<br/>批量插入失败：" + e.getMessage();
                failureMsg.append(msg);
                log.error("批量插入异常", e);
            }
        }
        // 步骤4: 生成最终报告
        if (failureNum > 0) {
            if (successNum == 0) {
                successMsg.delete(0, successMsg.length());
            }
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
        }
        return successMsg + failureMsg.toString();
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
