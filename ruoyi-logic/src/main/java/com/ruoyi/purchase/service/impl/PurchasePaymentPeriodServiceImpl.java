package com.ruoyi.purchase.service.impl;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
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
                purchasePaymentPeriodMapper.insertPurchasePaymentPeriod(purchasePaymentPeriod);
                successNum++;
                successMsg.append("<br/>" + successNum + "、编号 " + purchasePaymentPeriod.getId() + " 导入成功");
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
        message.append(successMsg).append(failureMsg);
        return message.toString();
    }
}
