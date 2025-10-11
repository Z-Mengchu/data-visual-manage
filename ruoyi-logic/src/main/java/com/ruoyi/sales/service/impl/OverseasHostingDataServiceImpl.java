package com.ruoyi.sales.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.sales.domain.FeeItemSummary;
import com.ruoyi.sales.domain.OverseasHostingData;
import com.ruoyi.sales.domain.OverseasHostingDimensionSummary;
import com.ruoyi.sales.mapper.OverseasHostingDataMapper;
import com.ruoyi.sales.service.IOverseasHostingDataService;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public List<OverseasHostingData> selectOverseasHostingDataList(OverseasHostingData overseasHostingData)
    {
        return overseasHostingDataMapper.selectOverseasHostingDataList(overseasHostingData);
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
                BeanValidators.validateWithException(validator, overseasData);
                overseasData.setUpdateBy(operName);
                overseasHostingDataMapper.insertOverseasHostingData(overseasData);
                successNum++;
                successMsg.append("<br/>" + successNum + "、订单号 " + overseasData.getOrderNumber() + " 导入成功");
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>第 " + failureNum + "条数据导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 按运营分组汇总数据
     *
     * @return 运营维度汇总列表
     */
    @Override
    public List<OverseasHostingDimensionSummary> getSummaryByOperator(String country) {
        List<String> warehouseList = countryWarehouseMap.get(country);
        return overseasHostingDataMapper.selectSummaryByOperator(warehouseList);
    }
    /**
     * 按开发员分组汇总数据
     *
     * @return 开发员维度汇总列表
     */
    @Override
    public List<OverseasHostingDimensionSummary> getSummaryByDeveloper(String country) {
        List<String> warehouseList = countryWarehouseMap.get(country);
        return overseasHostingDataMapper.selectSummaryByDeveloper(warehouseList);
    }
    /**
     * 按品牌分组汇总数据
     *
     * @return 品牌维度汇总列表
     */
    @Override
    public List<OverseasHostingDimensionSummary> getSummaryByBrand(String country) {
        List<String> warehouseList = countryWarehouseMap.get(country);
        return overseasHostingDataMapper.selectSummaryByBrand(warehouseList);
    }
    /**
     * 按仓库分组汇总数据
     *
     * @return 仓库维度汇总列表
     */
    @Override
    public List<OverseasHostingDimensionSummary> getSummaryByWarehouse(String country) {
        List<String> warehouseList = countryWarehouseMap.get(country);
        return overseasHostingDataMapper.selectSummaryByWarehouse(warehouseList);
    }
    /**
     * 按类目分组汇总数据
     *
     * @return 类目维度汇总列表
     */
    @Override
    public List<OverseasHostingDimensionSummary> getSummaryByCategory(String country) {
        List<String> warehouseList = countryWarehouseMap.get(country);
        return overseasHostingDataMapper.selectSummaryByCategory(warehouseList);
    }
    /**
     * 按SKU分组汇总数据
     *
     * @return SKU维度汇总列表
     */
    @Override
    public List<OverseasHostingDimensionSummary> getSummaryBySku(String country) {
        List<String> warehouseList = countryWarehouseMap.get(country);
        return overseasHostingDataMapper.selectSummaryBySku(warehouseList);
    }
    /**
     * 按费用项分组汇总金额
     *
     * @return 费用项汇总列表
     */
    @Override
    public List<FeeItemSummary> getSummaryByFeeItem(String country) {
        List<String> warehouseList = countryWarehouseMap.get(country);
        return overseasHostingDataMapper.selectSummaryByFeeItem(warehouseList);
    }
    /**
     * 按品牌和类目分组汇总数据
     *
     * @return 品牌类目维度汇总列表
     */
    @Override
    public List<Map<String, Object>> getSummaryByBrandAndCategory(String country) {
        List<String> warehouseList = countryWarehouseMap.get(country);
        return overseasHostingDataMapper.selectSummaryByBrandAndCategory(warehouseList);
    }
    /**
     * 按月份分组汇总当前年份数据
     *
     * @return 月度汇总列表
     */
    @Override
    public List<Map<String, Object>> getSummaryByMonthly(String country) {
        List<String> warehouseList = countryWarehouseMap.get(country);
        return overseasHostingDataMapper.selectSummaryByMonthly(warehouseList);
    }
    /**
     * 获取总体统计数据
     *
     * @return 总体统计数据
     */
    @Override
    public Map<String, Object> getSummaryByTotal(String country) {
        List<String> warehouseList = countryWarehouseMap.get(country);
        return overseasHostingDataMapper.selectSummaryByTotal(warehouseList);
    }
    /**
     * 获取核心费用项汇总数据
     *
     * @return 核心费用项汇总数据
     */
    @Override
    public Map<String, Object> getSummaryByCoreExpenses(String country) {
        List<String> warehouseList = countryWarehouseMap.get(country);
        return overseasHostingDataMapper.selectSummaryByCoreExpenses(warehouseList);
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
        if (countryWarehouseMap.containsKey(country)) {
            return countryWarehouseMap.get(country);
        }
        return countryWarehouseMap.get("未分类国家");
    }
}
