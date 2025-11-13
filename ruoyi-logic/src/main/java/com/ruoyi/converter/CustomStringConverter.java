package com.ruoyi.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.ruoyi.common.utils.StringUtils;

/**
 * 自定义String转换器，处理Excel错误值（如#N/A）
 * 遵循单一职责原则：只负责String类型的转换和错误处理
 */
public class CustomStringConverter implements Converter<String> {

    @Override
    public Class<String> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING; // #N/A在Excel中作为字符串读取
    }

    @Override
    public String convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                   GlobalConfiguration globalConfiguration) {
        // 获取单元格的字符串值
        String cellValue = cellData.getStringValue();

        // 检查是否为错误值#N/A，忽略大小写
        if (cellValue != null && cellValue.trim().equalsIgnoreCase("#N/A")) {
            return StringUtils.EMPTY;
        }

        // 正常情况：返回原字符串值
        return cellValue;
    }

    /**
     * 导出转换：Java String对象 -> Excel单元格数据
     */
    @Override
    public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        // 处理空值
        if (value == null) {
            return new WriteCellData<>("");
        }
        // 处理空字符串
        if (value.trim().isEmpty()) {
            return new WriteCellData<>("");
        }

        return new WriteCellData<>(value);
    }
}
