package com.ruoyi.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.ruoyi.sales.domain.ChannelSalesData;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 销售数据导入监听器
 * 过滤paymentSettlementDate为null或空字符串的数据
 */
@Slf4j
public class SalesDataImportListener extends AnalysisEventListener<ChannelSalesData> {
    private final List<ChannelSalesData> validDataList;
    private final List<Integer> invalidRowNumbers;
    private int totalCount = 0;
    private int validCount = 0;
    private int invalidCount = 0;

    public SalesDataImportListener(List<ChannelSalesData> validDataList, List<Integer> invalidRowNumbers) {
        this.validDataList = validDataList;
        this.invalidRowNumbers = invalidRowNumbers;
    }

    /**
     * 每读取一行数据时调用
     */
    @Override
    public void invoke(ChannelSalesData data, AnalysisContext context) {
        totalCount++;

        // 检查paymentSettlementDate是否为空
        if (isPaymentSettlementDateValid(data)) {
            if (data.getOrderNetProfit() != null && data.getRevenueRmb() != null && data.getRevenueRmb().compareTo(BigDecimal.ZERO) != 0) {
                data.setOrderNetProfitRate(data.getOrderNetProfit().divide(data.getRevenueRmb(), 6, RoundingMode.HALF_UP));
            }else {
                data.setOrderNetProfitRate(BigDecimal.ZERO);
            }
            validDataList.add(data);
            validCount++;
            log.debug("有效数据: {}", data);
        } else {
            invalidCount++;
            int rowIndex = context.readRowHolder().getRowIndex() + 1; // Excel行号从1开始
            invalidRowNumbers.add(rowIndex);
            log.warn("第{}行数据被过滤，paymentSettlementDate为空", rowIndex);
        }
    }

    /**
     * 所有数据解析完成后调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("数据导入完成: 总计{}条，有效{}条，无效{}条",
                totalCount, validCount, invalidCount);
    }

    /**
     * 检查paymentSettlementDate是否有效
     */
    private boolean isPaymentSettlementDateValid(ChannelSalesData data) {
        if (data == null) {
            return false;
        }
        return data.getPaymentSettlementDate() != null;
    }
}
