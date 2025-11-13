package com.ruoyi.purchase.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.purchase.domain.PurchasePaymentPeriod;
import com.ruoyi.purchase.mapper.PurchasePaymentPeriodMapper;
import com.ruoyi.purchase.service.IPurchasePaymentPeriodService;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 采购账期Service业务层处理
 *
 * @author ZMJ
 * @date 2025-09-04
 */
@Service
public class PurchasePaymentPeriodServiceImpl implements IPurchasePaymentPeriodService
{
    private static final Logger log = LoggerFactory.getLogger(PurchasePaymentPeriodServiceImpl.class);

    @Autowired
    private PurchasePaymentPeriodMapper purchasePaymentPeriodMapper;

    @Autowired
    protected Validator validator;

    /**
     * 查询采购账期
     *
     * @param id 采购账期主键
     * @return 采购账期
     */
    @Override
    public PurchasePaymentPeriod selectPurchasePaymentPeriodById(Integer id)
    {
        PurchasePaymentPeriod purchasePaymentPeriod = purchasePaymentPeriodMapper.selectPurchasePaymentPeriodById(id);
        purchasePaymentPeriod.calculateOverdueStatus();
        return purchasePaymentPeriod;
    }

    /**
     * 查询采购账期列表
     *
     * @param purchasePaymentPeriod 采购账期
     * @return 采购账期
     */
    @Override
    public List<PurchasePaymentPeriod> selectPurchasePaymentPeriodList(PurchasePaymentPeriod purchasePaymentPeriod)
    {
        List<PurchasePaymentPeriod> purchasePaymentPeriodList = purchasePaymentPeriodMapper.selectPurchasePaymentPeriodList(purchasePaymentPeriod);
        for (PurchasePaymentPeriod paymentPeriod : purchasePaymentPeriodList) {
            paymentPeriod.calculateOverdueStatus();
        }
        return purchasePaymentPeriodList;
    }

    /**
     * 新增采购账期
     *
     * @param purchasePaymentPeriod 采购账期
     * @return 结果
     */
    @Override
    public int insertPurchasePaymentPeriod(PurchasePaymentPeriod purchasePaymentPeriod)
    {
        return purchasePaymentPeriodMapper.insertPurchasePaymentPeriod(purchasePaymentPeriod);
    }

    /**
     * 修改采购账期
     *
     * @param purchasePaymentPeriod 采购账期
     * @return 结果
     */
    @Override
    public int updatePurchasePaymentPeriod(PurchasePaymentPeriod purchasePaymentPeriod)
    {
        return purchasePaymentPeriodMapper.updatePurchasePaymentPeriod(purchasePaymentPeriod);
    }

    /**
     * 批量删除采购账期
     *
     * @param ids 需要删除的采购账期主键
     * @return 结果
     */
    @Override
    public int deletePurchasePaymentPeriodByIds(Integer[] ids)
    {
        return purchasePaymentPeriodMapper.deletePurchasePaymentPeriodByIds(ids);
    }

    /**
     * 删除采购账期信息
     *
     * @param id 采购账期主键
     * @return 结果
     */
    @Override
    public int deletePurchasePaymentPeriodById(Integer id)
    {
        return purchasePaymentPeriodMapper.deletePurchasePaymentPeriodById(id);
    }

    /**
     * 导入采购账期数据
     * @param purchasePaymentPeriodList
     * @param operName
     * @return
     */
    @Override
    public String importData(List<PurchasePaymentPeriod> purchasePaymentPeriodList, String operName) {
        if (StringUtils.isNull(purchasePaymentPeriodList) || purchasePaymentPeriodList.isEmpty())
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        List<PurchasePaymentPeriod> dataToInsert = new ArrayList<>(); // 用于批量插入的非重复数据
        Map<String, List<PurchasePaymentPeriod>> existingDataMap = new HashMap<>(); // 存储现有数据，键为采购单号
        Set<PurchasePaymentPeriod> processedSet = new HashSet<>();    // 用于检查待导入数据内部的重复
        // 步骤1: 预处理数据，并收集所有采购单号用于批量查询
        Set<String> nonNullPurchaseOrderNumbers = purchasePaymentPeriodList.stream()
                .map(PurchasePaymentPeriod::getPurchaseOrderNumber)
                .filter(StringUtils::isNotEmpty)    // 过滤非空采购单号
                .collect(Collectors.toSet());
        boolean hasNullPurchaseOrderNumber = purchasePaymentPeriodList.stream()
                .anyMatch(data -> StringUtils.isEmpty(data.getPurchaseOrderNumber())); // 检查是否存在null采购单号
        // 批量查询现有数据，分别处理非空采购单号和null采购单号
        List<PurchasePaymentPeriod> existingDataList = new ArrayList<>();

        // 查询非空采购单号的数据
        if (!nonNullPurchaseOrderNumbers.isEmpty()) {
            existingDataList.addAll(purchasePaymentPeriodMapper.selectPurchasePaymentPeriodByPurchaseOrderNumbers(nonNullPurchaseOrderNumbers));
        }
        // 查询null采购单号的数据
        if (hasNullPurchaseOrderNumber) {
            existingDataList.addAll(purchasePaymentPeriodMapper.selectPurchasePaymentPeriodByPurchaseOrderNumberIsNull());
        }
        // 构建map，键为采购单号，值为对应数据列表（支持一个采购单号多条数据的情况）
        for (PurchasePaymentPeriod data : existingDataList) {
            String key = StringUtils.isEmpty(data.getPurchaseOrderNumber()) ? "__NULL_ORDER_NUMBER__" : data.getPurchaseOrderNumber();
            existingDataMap.computeIfAbsent(key, k -> new ArrayList<>()).add(data);
        }
        // 步骤2：遍历数据，进行验证和重复检查（先检查内部重复，再检查数据库重复）
        for (int i = 0; i < purchasePaymentPeriodList.size(); i++) {
            PurchasePaymentPeriod purchasePaymentPeriod = purchasePaymentPeriodList.get(i);
            int currentIndex = i + 1; // 记录当前数据位置
            try {
                BeanValidators.validateWithException(validator, purchasePaymentPeriod);
                // 处理null采购单号
                String purchaseOrderNumber = purchasePaymentPeriod.getPurchaseOrderNumber();
                purchasePaymentPeriod.setPurchaseOrderNumber(StringUtils.isEmpty(purchaseOrderNumber) ? null : purchaseOrderNumber);
                // 处理null采购计划编号
                String purchasePlanNumber = purchasePaymentPeriod.getPurchasePlanNumber();
                purchasePaymentPeriod.setPurchasePlanNumber(StringUtils.isEmpty(purchasePlanNumber) ? null : purchasePlanNumber);
                // 处理null账户1688
                String account1688 = purchasePaymentPeriod.getAccount1688();
                purchasePaymentPeriod.setAccount1688(StringUtils.isEmpty(account1688) ? null : account1688);
                // 检查导入数据内部的重复，使用Set基于equals方法判断
                if (processedSet.contains(purchasePaymentPeriod)) {
                    failureNum++;
                    String msg = "<br/>第 " + currentIndex + " 条数据在导入列表中重复，请勿重复导入";
                    failureMsg.append(msg);
                    continue; //跳过后续处理
                }
                processedSet.add(purchasePaymentPeriod); // 添加到已处理的数据中
                // 检查与数据库的重复，基于内存中的existingDataMap
                boolean isExistInDb = false;
                // 从Map中获取对应采购单号的数据列表（包括null键）
                String mapKey = purchasePaymentPeriod.getPurchaseOrderNumber();
                if (mapKey == null) {
                    mapKey = "__NULL_ORDER_NUMBER__";
                }
                List<PurchasePaymentPeriod> existingList = existingDataMap.get(mapKey);
                if (existingList != null) {
                    for (PurchasePaymentPeriod existingData : existingList) {
                        if(existingData.equals(purchasePaymentPeriod)){
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
                    dataToInsert.add(purchasePaymentPeriod);
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
                    List<PurchasePaymentPeriod> batchList = dataToInsert.subList(i, end);
                    purchasePaymentPeriodMapper.batchInsertPurchasePaymentPeriod(batchList, operName);
                }
                successMsg.append("恭喜您，数据已全部导入成功！共 ").append(successNum).append(" 条，数据如下：");
                for (int i = 0; i < dataToInsert.size(); i++) {
                    PurchasePaymentPeriod data = dataToInsert.get(i);
                    successMsg.append("<br/>").append(i + 1).append("、采购单号 ").append(data.getPurchaseOrderNumber() == null ? "空" : data.getPurchaseOrderNumber()).append(" 导入成功");
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

    /**
     * 统计已付款和未付款数量
     */
    @Override
    public Map<String, Integer> countPaymentStatus(PurchasePaymentPeriod purchasePaymentPeriod) {
        Map<String, Integer> result = new HashMap<>();

        // 统计已付款数量
        PurchasePaymentPeriod paidQuery = new PurchasePaymentPeriod();
        BeanUtils.copyProperties(purchasePaymentPeriod, paidQuery);
        paidQuery.setIsPaid("1"); // 已付款
        int paidCount = purchasePaymentPeriodMapper.selectPurchasePaymentPeriodCount(paidQuery);

        // 统计未付款数量
        PurchasePaymentPeriod unpaidQuery = new PurchasePaymentPeriod();
        BeanUtils.copyProperties(purchasePaymentPeriod, unpaidQuery);
        unpaidQuery.setIsPaid("0"); // 未付款
        int unpaidCount = purchasePaymentPeriodMapper.selectPurchasePaymentPeriodCount(unpaidQuery);

        // 统计未知状态数量（isPaid为null或空）
        PurchasePaymentPeriod unknownQuery = new PurchasePaymentPeriod();
        BeanUtils.copyProperties(purchasePaymentPeriod, unknownQuery);
        unknownQuery.setIsPaid(""); // 设置为空字符串来查询未知状态
        int unknownCount = purchasePaymentPeriodMapper.selectPurchasePaymentPeriodCountUnknown(unknownQuery);

        result.put("paidCount", paidCount);
        result.put("unpaidCount", unpaidCount);
        result.put("unknownCount", unknownCount);

        return result;
    }
}
