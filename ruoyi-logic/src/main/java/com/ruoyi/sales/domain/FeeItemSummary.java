package com.ruoyi.sales.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 费用项汇总结果DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeeItemSummary {

    /** 费用项 */
    private String feeItem;

    /** 金额(CNY)求和 */
    private BigDecimal amountCnySum;

}
