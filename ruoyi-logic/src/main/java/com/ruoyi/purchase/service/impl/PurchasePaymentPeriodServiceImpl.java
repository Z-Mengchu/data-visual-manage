package com.ruoyi.purchase.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.sales.domain.ChannelSalesData;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.purchase.mapper.PurchasePaymentPeriodMapper;
import com.ruoyi.purchase.domain.PurchasePaymentPeriod;
import com.ruoyi.purchase.service.IPurchasePaymentPeriodService;

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
        StringBuilder message = new StringBuilder();
        for (PurchasePaymentPeriod purchasePaymentPeriod : purchasePaymentPeriodList)
        {
            try
            {
                BeanValidators.validateWithException(validator, purchasePaymentPeriod);
                purchasePaymentPeriod.setUpdateBy(operName);

                // 判断这条数据在数据库中是否已经存在完全相同的数据
                // 先通过相同订单号查询数据
                if (purchasePaymentPeriod.getPurchaseOrderNumber().isEmpty()) purchasePaymentPeriod.setPurchaseOrderNumber(null);
                List<PurchasePaymentPeriod> willEqualDataList = purchasePaymentPeriodMapper.selectPurchasePaymentPeriodByPurchaseOrderNumber(purchasePaymentPeriod.getPurchaseOrderNumber());

                boolean isExist = false;
                // 再通过重写equal方法比较两者是否完全相同
                for (PurchasePaymentPeriod paymentPeriod : willEqualDataList) {
                    if (paymentPeriod.equals(purchasePaymentPeriod)) {
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
                    // 数据不存在进行插入
                    purchasePaymentPeriodMapper.insertPurchasePaymentPeriod(purchasePaymentPeriod);
                    successNum++;
                    successMsg.append("<br/>第 ").append(failureNum + successNum).append("条数据导入成功");
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
