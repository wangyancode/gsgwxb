package com.jsdc.gsgwxb.utils;

import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;


/**
 * 导入导出工具类

 */
public class ExportDataUtils {

    /**
     * 设置下拉选项
     */
    public static DataValidation setSelectCol(StyleSet styleSet, Sheet sheet, String[] capacityAvi, int firstRow, int firstCol) {

        CellStyle cellStyle = styleSet.getCellStyle();
        //规定格式
        cellStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("text"));

        DataValidationHelper helper = sheet.getDataValidationHelper();
        // 设置辅修下拉框数据
//        String[] capacityAvi = {"是", "否"};
        DataValidationConstraint capacityConstraint = helper.createExplicitListConstraint(capacityAvi);
        //需要被设置为下拉数据的单元格范围
        CellRangeAddressList capacityList = new CellRangeAddressList(firstRow, 5000, firstCol, firstCol);
        return helper.createValidation(capacityConstraint, capacityList);
    }
    /**
     * 写到excel
     */
    public static void writeData(List<List<?>> rows, ExcelWriter writer, Integer[] firstCol){
        String[] types = {"A", "B","C", "D", "E", "F", "G", "H", "I", "J", "K", "L","M", "N","O", "P", "Q", "R", "S", "T", "U", "V", "W","X","Y","Z"};
        for (int i=0; i< rows.size(); i++){
            List<?> cols = rows.get(i);
            String dictSheet = "dict" + i;
            //创建第二个Sheet
            writer.setSheet(dictSheet);
            //将Sheet2中的数据引用到Sheet1中的下拉框
            Workbook workbook = writer.getWorkbook();
            Name namedCell = workbook.createName();
            namedCell.setNameName(dictSheet);
            //加载数据,将名称为hidden的
            DVConstraint constraint = DVConstraint.createFormulaListConstraint(dictSheet);
            for (int j=0; j<cols.size(); j++){
                writer.writeCellValue(i, j, cols.get(j));
            }

            if (CollectionUtils.isEmpty(cols)){
                continue;
            }
            namedCell.setRefersToFormula(dictSheet + "!$" + types[i] + "$1:$" + types[i] + "$" + cols.size());
            // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
            HSSFDataValidation validation = new HSSFDataValidation(new CellRangeAddressList(1, 1000, firstCol[i], firstCol[i]), constraint);
            writer.getSheets().get(0).addValidationData(validation);

            workbook.setSheetHidden(i+1, true);
        }
    }

    /**
     * 输入标题到excel
     * @param writer excel对象
     * @param column 当前列位置
     * @param cellValue 标题内容
     * @param requiredFlag 是否标红
     */
    public static void writeCell(ExcelWriter writer, int column, String cellValue, Boolean requiredFlag){
        // 根据x,y轴设置单元格内容
        writer.writeCellValue(column , 0, cellValue);
        Font font = writer.createFont();
        font.setBold(true);
        if (requiredFlag){
            // 根据x,y轴获取当前单元格样式
            CellStyle cellStyle = writer.createCellStyle(column, 0);
            // 内容水平居中
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            // 内容垂直居中
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            // 设置边框
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);

            // 设置高度
            writer.setColumnWidth(column, 15);
            // 字体颜色标红
            font.setColor(Font.COLOR_RED);
            cellStyle.setFont(font);
            // 填充前景色
            cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }else {
            // 根据x,y轴获取当前单元格样式
            CellStyle cellStyle = writer.createCellStyle(column, 0);
            // 内容水平居中
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            // 内容垂直居中
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            // 设置边框
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            // 设置高度
            writer.setColumnWidth(column, 15);
            // 填充前景色
            cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            cellStyle.setFont(font);
        }
    }

    /**
     * 得到value
     * 在 list<?>集合中, 根据传入的fieldName对象字段, 比对传入的value, 如果存在,则返回入参 resultFieldName 的字段值,否则返回空字符串
     * */
    public static String getListValue(List<?> list, String fieldName, String value, String resultFieldName) {
        if (CollectionUtils.isEmpty(list) || StringUtils.isBlank(fieldName) || StringUtils.isBlank(value) || StringUtils.isBlank(resultFieldName)) {
            return "";
        }
        for (Object obj : list) {
            try {
                Object fieldValue = obj.getClass().getMethod("get" + firstCharUpperCase(fieldName)).invoke(obj);
                if (value.equals(fieldValue)) {
                    Object resultFieldValue = obj.getClass().getMethod("get" + firstCharUpperCase(resultFieldName)).invoke(obj);
                    if (resultFieldValue != null) {
                        return resultFieldValue.toString();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return "";
    }

    /**
     * 判断对象是否存在,判断对象属性是否存在,如果存在则返回对象属性,否则返回空字符串
     *
     * @param map       例如:Map<Integer, SysUser>
     * @param mapKey    例如:Integer
     * @param fieldName 例如:'user_name','login_name'
     */
    public static String getValue(Map<Integer, ?> map, Integer mapKey, String fieldName) {
        if (map == null || mapKey == null || StringUtils.isBlank(fieldName)) {
            return "";
        }
        Object obj = map.get(mapKey);
        if (obj == null) {
            return "";
        }
        try {
            Object value = obj.getClass().getMethod("get" + firstCharUpperCase(fieldName)).invoke(obj);
            if (value != null) {
                return value.toString();
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }

    public static String firstCharUpperCase(String s) {
        if (s == null || "".equals(s)) {
            return ("");
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
