package com.ruoyi.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.reflect.ReflectUtils;
import com.ruoyi.purchase.domain.PurchasePaymentPeriod;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.ruoyi.common.utils.poi.ExcelUtil.SEPARATOR;

@EqualsAndHashCode(callSuper = true)
@Data
public class ImportImageListener extends AnalysisEventListener<PurchasePaymentPeriod> {
    private static final Logger log = LoggerFactory.getLogger(ImportImageListener.class);
    private final List<PurchasePaymentPeriod> dataList = new ArrayList<>();
    private final Workbook workbook;
    private final String urlFiled = "imageUrl";
    private final Map<String, List<PictureData>> pictures;

    public ImportImageListener(InputStream inputStream) throws IOException {
        this.workbook = new XSSFWorkbook(inputStream);
        this.pictures= getAllPictures();
    }

    @SneakyThrows
    @Override
    public void invoke(PurchasePaymentPeriod data, AnalysisContext analysisContext) {
        ReadRowHolder readRowHolder = analysisContext.readRowHolder();
        // 获取图片数据
        List<PictureData> pictureData = pictures.get(String.valueOf(readRowHolder.getRowIndex()));
        // 设置字段值
        StringBuilder propertyString = new StringBuilder();
        if (pictureData != null && !pictureData.isEmpty()){
            for (PictureData picture : pictureData) {
                String fileName = FileUtils.writeImportBytes(picture.getData());
                propertyString.append(fileName).append(SEPARATOR);
            }
        }
        String val = StringUtils.stripEnd(propertyString.toString(), SEPARATOR);
        ReflectUtils.invokeSetter(data, urlFiled, val);
        dataList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        try {
            this.workbook.close();
        } catch (IOException e) {
            log.error("关闭Workbook失败", e);
        }
        log.info("数据读取完成，共{}条记录", dataList.size());
    }

    /**
     * 获取图片数据
     * @return Map key:图片单元格索引（1_1）String，value:图片流PictureData
     */
    public Map<String, List<PictureData>> getAllPictures() {
        Sheet sheet = workbook.getSheetAt(0);
        boolean isXSSFWorkbook = !(workbook instanceof HSSFWorkbook);
        Map<String, List<PictureData>> pictures = null;
        if (isXSSFWorkbook)
        {
            pictures = ExcelUtil.getSheetPictures07((XSSFSheet) sheet, (XSSFWorkbook) workbook);
        }
        else
        {
            pictures = ExcelUtil.getSheetPictures03((HSSFSheet) sheet, (HSSFWorkbook) workbook);
        }
        Map<String, List<PictureData>> finalPictures = new HashMap<>();
        pictures.forEach((key, value) -> {
            finalPictures.put(key.split("_")[0], value);
        });
        return finalPictures;
    }
}
