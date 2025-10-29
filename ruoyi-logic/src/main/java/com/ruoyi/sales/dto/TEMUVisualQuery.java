package com.ruoyi.sales.dto;

import com.ruoyi.sales.domain.TEMUOrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Date;

/**
 * TEMU可视化数据查询对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TEMUVisualQuery extends TEMUOrderDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    /** 开始日期 */
    private Date startDate;

    /** 结束日期 */
    private Date endDate;

    /** 销售站点 */
    private String salesSite;

    /** 分组类型 */
    private String groupType;

    /** 图表类型 */
    private String chartType;

    /** 排序字段 */
    private String sortField;

    /** 排序方式 */
    private String sortOrder;
}
