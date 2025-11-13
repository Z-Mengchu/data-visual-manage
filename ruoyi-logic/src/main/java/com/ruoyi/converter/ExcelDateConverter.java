package com.ruoyi.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Excel日期数值转换器
 * 专门处理Excel内部日期数值格式，同时处理错误值
 */
public class ExcelDateConverter implements Converter<Date> {

    private static final Logger logger = LoggerFactory.getLogger(ExcelDateConverter.class);

    // Excel日期起始点（1900年1月1日）与Java日期起始点（1970年1月1日）的差异
    private static long EXCEL_DATE_OFFSET;

    // 使用ThreadLocal确保SimpleDateFormat线程安全
    private static final ThreadLocal<SimpleDateFormat> threadLocalDateFormat =
            ThreadLocal.withInitial(SimpleDateFormat::new);

    // 支持的常见日期格式（按优先级排序）
    private static final String[] SUPPORTED_DATE_FORMATS = {
            "yyyy/MM/dd HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy/MM/dd HH:mm",
            "yyyy-MM-dd HH:mm",
            "yyyy/MM/dd",
            "yyyy-MM-dd",
            "yyyy年MM月dd日 HH时mm分ss秒",
            "yyyy年MM月dd日 HH时mm分",
            "yyyy年MM月dd日",
            "MM/dd/yyyy HH:mm:ss",
            "MM-dd-yyyy HH:mm:ss",
            "dd/MM/yyyy HH:mm:ss",
            "dd-MM-yyyy HH:mm:ss",
            "yyyyMMddHHmmss",
            "yyyyMMdd"
    };

    static {
        // 计算Excel日期起始点与Java日期起始点的毫秒差
        // Excel使用1900年1月1日作为起始，Java使用1970年1月1日
        // 注意：Excel错误地将1900年视为闰年，所以需要减去1天
        try {
            Calendar excelEpoch = Calendar.getInstance();
            excelEpoch.set(1900, Calendar.JANUARY, 1, 0, 0, 0); // 1900年1月1日
            excelEpoch.set(java.util.Calendar.MILLISECOND, 0);

            java.util.Calendar javaEpoch = java.util.Calendar.getInstance();
            javaEpoch.set(1970, Calendar.JANUARY, 2, 0, 0, 0); // 1970年1月2日
            javaEpoch.set(java.util.Calendar.MILLISECOND, 0);

            // 计算毫秒差，并减去1天（因为Excel错误地将1900年视为闰年）
            EXCEL_DATE_OFFSET = excelEpoch.getTimeInMillis() - javaEpoch.getTimeInMillis()
                              - (24 * 60 * 60 * 1000L);
        } catch (Exception e) {
            // 如果计算失败，使用一个近似值
            logger.warn("计算Excel日期偏移量失败，使用默认值", e);
            EXCEL_DATE_OFFSET = -2209161600000L; // 1900年1月1日到1970年1月1日的毫秒数，减去1天
        }
    }

    @Override
    public Class<Date> supportJavaTypeKey() {
        return Date.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        // 不限制单元格类型，处理所有可能的情况
        return null;
    }

    @Override
    public Date convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                 GlobalConfiguration globalConfiguration) {
        // 处理错误值
        if (isValueError(cellData)) {
            logger.debug("检测到错误值，转换为null");
            return null;
        }

        // 处理数值类型的日期（Excel内部日期格式）
        if (cellData.getType() == CellDataTypeEnum.NUMBER) {
            return convertExcelNumberToDate(cellData.getNumberValue().doubleValue());
        }

        // 处理字符串类型的日期
        if (cellData.getType() == CellDataTypeEnum.STRING) {
            return parseDateString(cellData.getStringValue(), contentProperty);
        }

        // 其他情况返回null，让Easy Excel处理
        return null;
    }

    /**
     * 导出转换：Java Date对象 -> Excel单元格数据
     * 使用EasyExcel中配置的日期格式
     */
    @Override
    public WriteCellData<?> convertToExcelData(Date value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        // 处理空值
        if (value == null) {
            return new WriteCellData<>("");
        }
        // 使用EasyExcel的日期格式化机制
        try {
            // 如果有内容属性且配置了日期格式，使用配置的格式
            if (contentProperty != null && contentProperty.getDateTimeFormatProperty() != null) {
                String format = contentProperty.getDateTimeFormatProperty().getFormat();
                // 使用EasyExcel内置的日期格式化工具
                String formattedDate = DateUtils.format(value, format);
                return new WriteCellData<>(formattedDate);
            }

            String formattedDate = DateUtils.format(value, DateUtils.DATE_FORMAT_19);
            return new WriteCellData<>(formattedDate);

        } catch (Exception e) {
            logger.warn("日期格式化失败，使用toString: {}", value, e);
            // 如果格式化失败，返回日期的字符串表示
            return new WriteCellData<>(value.toString());
        }
    }

    /**
     * 将Excel数值转换为Java Date
     * Excel日期是从1900年1月1日开始的天数（注意：Excel错误地将1900年视为闰年）
     */
    private Date convertExcelNumberToDate(double excelDateValue) {
        try {
            // Excel日期数值是从1900年1月1日开始的天数
            // 转换为毫秒：天数 * 24小时 * 60分钟 * 60秒 * 1000毫秒
            long milliseconds = (long) ((excelDateValue * 24 * 60 * 60 * 1000) + EXCEL_DATE_OFFSET);
            return new Date(milliseconds);
        } catch (Exception e) {
            logger.warn("Excel日期数值转换失败: {}", excelDateValue, e);
            return null;
        }
    }

    /**
     * 解析日期字符串
     * 支持多种日期格式，优先尝试配置的格式，然后尝试常见格式
     */
    private Date parseDateString(String dateString, ExcelContentProperty contentProperty) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        String trimmedDateString = dateString.trim();

        // 首先尝试使用EasyExcel中配置的格式
        if (contentProperty != null && contentProperty.getDateTimeFormatProperty() != null) {
            String configuredFormat = contentProperty.getDateTimeFormatProperty().getFormat();
            try {
                SimpleDateFormat sdf = threadLocalDateFormat.get();
                sdf.applyPattern(configuredFormat);
                sdf.setLenient(false); // 严格模式
                return sdf.parse(trimmedDateString);
            } catch (Exception e) {
                // 继续尝试其他格式
            }
        }
        // 尝试所有支持的日期格式
        for (String format : SUPPORTED_DATE_FORMATS) {
            try {
                SimpleDateFormat sdf = threadLocalDateFormat.get();
                sdf.applyPattern(format);
                sdf.setLenient(false); // 严格模式
                return sdf.parse(trimmedDateString);
            } catch (Exception e) {
                // 忽略异常，继续尝试其他格式
            }
        }
        logger.warn("无法解析日期字符串: {}", trimmedDateString);
        return null;
    }

    /**
     * 检查单元格是否为错误值
     */
    private boolean isValueError(ReadCellData<?> cellData) {
        // 检查单元格类型是否为错误类型
        if (cellData.getType() == CellDataTypeEnum.ERROR) {
            return true;
        }

        // 检查字符串值是否为常见错误值
        if (cellData.getType() == CellDataTypeEnum.STRING &&
            cellData.getStringValue() != null) {
            String value = cellData.getStringValue().trim().toUpperCase();
            return value.equals("#VALUE!") || value.equals("#N/A") ||
                   value.equals("#DIV/0!") || value.equals("#REF!");
        }

        return false;
    }
}
