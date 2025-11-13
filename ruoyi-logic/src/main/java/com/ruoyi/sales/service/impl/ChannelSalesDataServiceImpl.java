package com.ruoyi.sales.service.impl;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.sales.domain.ChannelSalesData;
import com.ruoyi.sales.domain.OverseasHostingData;
import com.ruoyi.sales.domain.TEMUOrderDetails;
import com.ruoyi.sales.mapper.ChannelSalesDataMapper;
import com.ruoyi.sales.service.IChannelSalesDataService;
import com.ruoyi.system.domain.SysPost;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 全渠道销售数据分析Service业务层处理
 *
 * @author ZMJ
 * @date 2025-09-03
 */
@Service
public class ChannelSalesDataServiceImpl implements IChannelSalesDataService
{
    private static final Logger log = LoggerFactory.getLogger(ChannelSalesDataServiceImpl.class);

    @Autowired
    private ChannelSalesDataMapper channelSalesDataMapper;

    @Autowired
    protected Validator validator;

    /**
     * 查询全渠道销售数据分析
     *
     * @param id 全渠道销售数据分析主键
     * @return 全渠道销售数据分析
     */
    @Override
    public ChannelSalesData selectChannelSalesDataById(Integer id)
    {
        return channelSalesDataMapper.selectChannelSalesDataById(id);
    }

    /**
     * 查询全渠道销售数据分析列表
     *
     * @param channelSalesData 全渠道销售数据分析
     * @return 全渠道销售数据分析
     */
    @Override
    public List<ChannelSalesData> selectChannelSalesDataList(ChannelSalesData channelSalesData, SysUser currentUser, List<SysPost> userPosts)
    {
        List<String> postCodes = userPosts.stream().map(SysPost::getPostCode).toList();
        List<String> roleKeys = currentUser.getRoles().stream().map(SysRole::getRoleKey).toList();
        return channelSalesDataMapper.selectChannelSalesDataListWithFilter(channelSalesData, currentUser, postCodes, roleKeys);
       /* if (roleKeys.contains(RoleConstants.ROLE_FINANCE)){
            return channelSalesDataMapper.selectChannelSalesDataListWithFilter(channelSalesData, currentUser, postCodes);
        }
        return channelSalesDataMapper.selectChannelSalesDataList(channelSalesData);*/
    }

    /**
     * 新增全渠道销售数据分析
     *
     * @param channelSalesData 全渠道销售数据分析
     * @return 结果
     */
    @Override
    public int insertChannelSalesData(ChannelSalesData channelSalesData)
    {
        return channelSalesDataMapper.insertChannelSalesData(channelSalesData);
    }

    /**
     * 修改全渠道销售数据分析
     *
     * @param channelSalesData 全渠道销售数据分析
     * @return 结果
     */
    @Override
    public int updateChannelSalesData(ChannelSalesData channelSalesData)
    {
        return channelSalesDataMapper.updateChannelSalesData(channelSalesData);
    }

    /**
     * 批量删除全渠道销售数据分析
     *
     * @param ids 需要删除的全渠道销售数据分析主键
     * @return 结果
     */
    @Override
    public int deleteChannelSalesDataByIds(Integer[] ids)
    {
        return channelSalesDataMapper.deleteChannelSalesDataByIds(ids);
    }

    /**
     * 删除全渠道销售数据分析信息
     *
     * @param id 全渠道销售数据分析主键
     * @return 结果
     */
    @Override
    public int deleteChannelSalesDataById(Integer id)
    {
        return channelSalesDataMapper.deleteChannelSalesDataById(id);
    }

    /**
     * 导入数据
     *
     * @param salesDataList 数据列表
     * @param operName 操作人员
     * @return 结果
     */
    @Override
    public String importSalesData(List<ChannelSalesData> salesDataList, String operName) {
        if (StringUtils.isNull(salesDataList) || salesDataList.isEmpty())
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        List<ChannelSalesData> dataToInsert = new ArrayList<>(); // 用于批量插入的非重复数据
        Map<String, List<ChannelSalesData>> existingDataMap = new HashMap<>(); // 存储现有数据，键为订单号
        Set<ChannelSalesData> processedSet = new HashSet<>();    // 用于检查待导入数据内部的重复
        // 步骤1: 预处理数据，并收集所有订单号用于批量查询
        Set<String> nonNullOrderNumbers = salesDataList.stream()
                .map(ChannelSalesData::getOrderNumber)
                .filter(StringUtils::isNotEmpty)    // 过滤非空订单号
                .collect(Collectors.toSet());
        boolean hasNullOrderNumber = salesDataList.stream()
                .anyMatch(data -> StringUtils.isEmpty(data.getOrderNumber())); // 检查是否存在null订单号
        // 批量查询现有数据，分别处理非空订单号和null订单号
        List<ChannelSalesData> existingDataList = new ArrayList<>();

        // 查询非空订单号的数据
        if (!nonNullOrderNumbers.isEmpty()) {
            existingDataList.addAll(channelSalesDataMapper.selectChannelSalesDataByOrderNumbers(nonNullOrderNumbers));
        }
        // 查询null订单号的数据
        if (hasNullOrderNumber) {
            existingDataList.addAll(channelSalesDataMapper.selectChannelSalesDataByOrderNumberIsNull());
        }
        // 构建map，键为订单号，值为对应数据列表（支持一个订单号多条数据的情况）
        for (ChannelSalesData data : existingDataList) {
            String key = StringUtils.isEmpty(data.getOrderNumber()) ? "__NULL_ORDER_NUMBER__" : data.getOrderNumber();
            existingDataMap.computeIfAbsent(key, k -> new ArrayList<>()).add(data);
        }
        // 步骤2：遍历数据，进行验证和重复检查（先检查内部重复，再检查数据库重复）
        for (int i = 0; i < salesDataList.size(); i++) {
            ChannelSalesData salesData = salesDataList.get(i);
            int currentIndex = i + 1; // 记录当前数据位置
            try {
                // 订单号如果为空，则设置为null，在查询数据库时，不忽略订单号为null的数据
                BeanValidators.validateWithException(validator, salesData);
                salesData.setUpdateBy(operName);
                if (StringUtils.isEmpty(salesData.getOrderNumber())) salesData.setOrderNumber(null);
                // 检查导入数据内部的重复，使用Set基于equals方法判断
                if (processedSet.contains(salesData)) {
                    failureNum++;
                    String msg = "<br/>第 " + currentIndex + " 条数据在导入列表中重复，请勿重复导入";
                    failureMsg.append(msg);
                    continue; //跳过后续处理
                }
                processedSet.add(salesData); // 添加到已处理的数据中
                // 检查与数据库的重复，基于内存中的existingDataMap
                boolean isExistInDb = false;
                // 从Map中获取对应订单号的数据列表（包括null键）
                String mapKey = salesData.getOrderNumber();
                if (mapKey == null) {
                    mapKey = "__NULL_ORDER_NUMBER__";
                }
                List<ChannelSalesData> existingList = existingDataMap.get(mapKey);
                if (existingList != null) {
                    for (ChannelSalesData existingData : existingList) {
                        if(existingData.equals(salesData)){
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
                    dataToInsert.add(salesData);
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
                int batchSize = 3000;
                for (int i = 0; i < dataToInsert.size(); i += batchSize) {
                    int end = Math.min(dataToInsert.size(), i + batchSize);
                    List<ChannelSalesData> batchList = dataToInsert.subList(i, end);
                    channelSalesDataMapper.batchInsertChannelSalesData(batchList);
                }
                successMsg.append("恭喜您，数据已全部导入成功！共 ").append(successNum).append(" 条，数据如下：");
                for (int i = 0; i < dataToInsert.size(); i++) {
                    ChannelSalesData data = dataToInsert.get(i);
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
}
