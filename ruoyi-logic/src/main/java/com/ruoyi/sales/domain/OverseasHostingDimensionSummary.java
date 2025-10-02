package com.ruoyi.sales.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 维度汇总结果DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OverseasHostingDimensionSummary {

    /** 分组字段值（如运营名称、开发员名称等） */
    private String groupValue;

    /** 求和项:收入 */
    private BigDecimal revenueSum;

    /** 求和项:退款 */
    private BigDecimal refundSum;

    /** 求和项:采购成本 */
    private BigDecimal purchaseCostSum;

    /** 求和项:头程成本 */
    private BigDecimal firstMileCostSum;

    /** 求和项:物流费 */
    private BigDecimal logisticsFeeSum;

    /** 求和项:包装费用 */
    private BigDecimal packagingCostSum;

    /** 求和项:其他费用 */
    private BigDecimal otherCostsSum;

    /** 求和项:补发费用 */
    private BigDecimal reshipmentCostSum;

    /** 求和项:毛利 */
    private BigDecimal grossProfitSum;

}
