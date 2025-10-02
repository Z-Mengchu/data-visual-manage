package com.ruoyi.sales.service.impl;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.sales.domain.FeeItemSummary;
import com.ruoyi.sales.domain.OverseasHostingDimensionSummary;
import com.ruoyi.sales.domain.TEMUOrderDetails;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.sales.mapper.OverseasHostingDataMapper;
import com.ruoyi.sales.domain.OverseasHostingData;
import com.ruoyi.sales.service.IOverseasHostingDataService;

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
    public List<OverseasHostingDimensionSummary> getSummaryByOperator() {
        return overseasHostingDataMapper.selectSummaryByOperator();
    }
    /**
     * 按开发员分组汇总数据
     *
     * @return 开发员维度汇总列表
     */
    @Override
    public List<OverseasHostingDimensionSummary> getSummaryByDeveloper() {
        return overseasHostingDataMapper.selectSummaryByDeveloper();
    }
    /**
     * 按品牌分组汇总数据
     *
     * @return 品牌维度汇总列表
     */
    @Override
    public List<OverseasHostingDimensionSummary> getSummaryByBrand() {
        return overseasHostingDataMapper.selectSummaryByBrand();
    }
    /**
     * 按仓库分组汇总数据
     *
     * @return 仓库维度汇总列表
     */
    @Override
    public List<OverseasHostingDimensionSummary> getSummaryByWarehouse() {
        return overseasHostingDataMapper.selectSummaryByWarehouse();
    }
    /**
     * 按类目分组汇总数据
     *
     * @return 类目维度汇总列表
     */
    @Override
    public List<OverseasHostingDimensionSummary> getSummaryByCategory() {
        return overseasHostingDataMapper.selectSummaryByCategory();
    }
    /**
     * 按SKU分组汇总数据
     *
     * @return SKU维度汇总列表
     */
    @Override
    public List<OverseasHostingDimensionSummary> getSummaryBySku() {
        return overseasHostingDataMapper.selectSummaryBySku();
    }
    /**
     * 按费用项分组汇总金额
     *
     * @return 费用项汇总列表
     */
    @Override
    public List<FeeItemSummary> getSummaryByFeeItem() {
        return overseasHostingDataMapper.selectSummaryByFeeItem();
    }
}
