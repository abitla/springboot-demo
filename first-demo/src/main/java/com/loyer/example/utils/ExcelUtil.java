package com.loyer.example.utils;

import com.loyer.example.enums.HintEnum;
import com.loyer.example.exception.BusinessException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author kuangq
 * @projectName example
 * @title ExcelUtil
 * @description Excel工具类
 * @date 2019-09-23 22:45
 */
public class ExcelUtil {

    public void dataStats(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String city = request.getParameter("city");
        List<Object> list = null;
        HSSFWorkbook wb = new HSSFWorkbook(); // 创建Excel
        String[] sheetName = {"所有数据", "贫困出院", "报销情况", "预交金", "目录外费用", "妇幼所有数据", "妇幼贫困出院", "妇幼报销情况", "妇幼预交金", "妇幼目录外费用", "周期数据汇总"};
        for (int i = 0; i < list.size(); i++) {
            List<LinkedHashMap<String, Object>> result = (List<LinkedHashMap<String, Object>>) list.get(i);
            if (result.size() > 1) {
                HSSFSheet sheet = wb.createSheet(sheetName[i]); // 创建sheet
                for (int m = 0; m < result.size(); m++) {
                    HSSFRow row = sheet.createRow(m); // 创建行
                    int n = 0;
                    for (String key : result.get(m).keySet()) {
                        HSSFCell cell = row.createCell(n++); // 创建单元格
                        if (result.get(m).get(key) == null) {
                            cell.setCellValue("");
                        } else {
                            String value = result.get(m).get(key).toString();
                            cell.setCellValue(value); // 创建值
                        }
                    }
                }
            }
        }
        DownloadExcel(city + ".xls", wb, response);
    }

    public static void DownloadExcel(String fileName, HSSFWorkbook workbook, HttpServletResponse response) {
        OutputStream outputStream = null;
        try {
            response.setContentType("application/vnd.ms-excel; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e) {
            throw new BusinessException(HintEnum.HINT_1023);
        } finally {
            try {
                if (outputStream != null) outputStream.close();
            } catch (Exception e) {
                throw new BusinessException(HintEnum.HINT_1011);
            }
        }
    }
}
