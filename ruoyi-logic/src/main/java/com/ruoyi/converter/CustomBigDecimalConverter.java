package com.ruoyi.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 自定义BigDecimal转换器，处理Excel错误值（如#DIV/0!）
 * 遵循单一职责原则：只负责BigDecimal类型的转换和错误处理
 */
public class CustomBigDecimalConverter implements Converter<BigDecimal> {
    private static final Logger log = LoggerFactory.getLogger(CustomBigDecimalConverter.class);

    @Override
    public Class<BigDecimal> supportJavaTypeKey() {
        return BigDecimal.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public BigDecimal convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration) {
        // 获取单元格的字符串值
        String cellValue;
        BigDecimal originalNumberValue = cellData.getOriginalNumberValue();
        if (originalNumberValue != null) return originalNumberValue;
        else cellValue = cellData.getStringValue();

        // 检查是否为错误值（如#DIV/0!），这里可以扩展其他错误类型
        if (cellValue != null && cellValue.trim().equals("#DIV/0!")) {
            return null;
        }
        if (cellValue == null) return null;
        // 正常情况：尝试转换为BigDecimal
        try {
            return new BigDecimal(cellValue);
        } catch (NumberFormatException e) {
            log.error("转换失败，单元格值: {}, 错误: {}", cellValue, e.getMessage());
            return null;
        }
    }

    /**
     * 导出转换：Java BigDecimal对象 -> Excel单元格数据
     */
    @Override
    public WriteCellData<?> convertToExcelData(BigDecimal value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        // 处理空值
        if (value == null) {
            return new WriteCellData<>("");
        }
        try {
            String formattedValue;
            // 使用默认格式
            // 去除尾随的零和小数点（如果适用）
            formattedValue = value.stripTrailingZeros().toPlainString();
            return new WriteCellData<>(formattedValue);
        } catch (Exception e) {
            log.error("BigDecimal格式化失败: {}", value, e);
            // 如果格式化失败，返回原始值的字符串表示
            return new WriteCellData<>(value.toPlainString());
        }
    }
}
