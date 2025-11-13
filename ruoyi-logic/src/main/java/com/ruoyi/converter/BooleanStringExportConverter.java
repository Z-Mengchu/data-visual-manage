package com.ruoyi.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义Integer转换器，将字符串“是”映射为1、“否”映射为0，并处理null和#N/A
 * 遵循单一职责原则：只负责Integer类型的转换和业务映射
 */
public class BooleanStringExportConverter  implements Converter<String> {

    private static final Logger logger = LoggerFactory.getLogger(BooleanStringExportConverter .class);

    @Override
    public Class<Integer> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING; // 单元格值作为字符串读取
    }

    @Override
    public String convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                    GlobalConfiguration globalConfiguration) {
        // 获取单元格的字符串值，如果单元格为空或错误，getStringValue可能返回null
        String cellValue = cellData.getStringValue();

        // 处理null或空值
        if (cellValue == null || cellValue.trim().isEmpty()) {
            logger.debug("单元格值为空，转换为null");
            return null;
        }

        // 处理错误值#N/A（不区分大小写）
        if (cellValue.trim().equalsIgnoreCase("#N/A")) {
            logger.debug("检测到错误值#N/A，转换为null");
            return null;
        }

        // 根据业务规则映射字符串到整数
        return switch (cellValue.trim()) {
            case "是" -> "1";
            case "否" -> "0";
            default -> {
                // 如果值不是“是”或“否”，记录警告并返回null
                logger.warn("无法映射的单元格值: '{}'，位置: 行{}列{}，已转换为null",
                        cellValue, cellData.getRowIndex(), cellData.getColumnIndex());
                yield null;
            }
        };
    }

    @Override
    public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        // 处理空值
        if (value == null) {
            return new WriteCellData<>("");
        }

        // 根据数据库值进行转换
        String displayValue;
        switch (value.trim()) {
            case "0":
                displayValue = "否";
                break;
            case "1":
                displayValue = "是";
                break;
            default:
                // 如果不是0或1，保持原值（或者根据需求返回空字符串）
                logger.warn("无法识别的布尔字符串值: '{}'，将保持原值", value);
                displayValue = value;
                break;
        }

        return new WriteCellData<>(displayValue);
    }
}
