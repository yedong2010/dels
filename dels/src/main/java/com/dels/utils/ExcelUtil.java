package com.dels.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

/**
 * 
 * @description excel文件导出工具类
 * @author z11595
 * @time:2016年12月22日 下午4:41:36
 */
public class ExcelUtil {
    public static void main(String[] args) {
        DecimalFormat f = new DecimalFormat("#.0000");
        f.setRoundingMode(RoundingMode.HALF_UP); 
        System.out.println(f.format(0.65625d));
    }

    public static Workbook readTemplateByClasspath(String path, List<HashMap<String, Object>> list) {// BasicConstants.TEMPLATE_URL
        Integer index = (Integer) BasicConstants.TEMPLATE_NAME_MAP.get(path);
        path = ExcelUtil.class.getClassLoader().getResource("").getPath() + BasicConstants.TEMPLATE_URL + path;
        Workbook wbPartModule = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            if (path.endsWith(".xlsx")) {
                wbPartModule = new XSSFWorkbook(fis);
            } else if (path.endsWith(".xls")) {
                wbPartModule = new HSSFWorkbook(fis);
            }

            Sheet sheet = wbPartModule.getSheetAt(0);
            CellStyle style = setCellBorder(wbPartModule);
            Row row = sheet.getRow(index);
            List<String> format = readTemplate(sheet, index);
            System.out.println(format);
            for (int i = 0; i < list.size(); i++) {
                row = sheet.getRow(index + i);// 第五行
                if (row == null) {
                    row = sheet.createRow(index + i);
                }
                row.setHeight((short) (24.75 * 20));
                Cell cell = row.createCell(0);
                cell.setCellStyle(style);
                cell.setCellValue(i + 1);
                HashMap<String, Object> mp = list.get(i);
                for (int j = 0; j < format.size(); j++) {
                    cell = row.createCell(j + 1);
                    cell.setCellStyle(style);
                    String val = mp.get(getMethodName(format.get(j))).toString();
                    String t = format.get(j).substring(0, 1);
                    if (t.equals(BasicConstants.CELL_TYPE_INTEGER)) {
                        cell.setCellValue(Integer.parseInt(val));
                    } else if (t.equals(BasicConstants.CELL_TYPE_PERCENT) && isNumeric(val)) {
                        CellStyle cellStyle = wbPartModule.createCellStyle();
                        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
                        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
                        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
                        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
                        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
                        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);// 居中
                        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
                        cell.setCellStyle(cellStyle);
                        DecimalFormat f = new DecimalFormat("#.0000");
                        f.setRoundingMode(RoundingMode.HALF_UP); 
                        cell.setCellValue(Double.parseDouble(f.format(Double.parseDouble(val) / 100)));
                    } else {
                        cell.setCellValue(val);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close(); // 关闭流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return wbPartModule;
    }

    public static Workbook readTemplateBypath(String path, List<?> cms, Class<?> clz, String type) {
        Integer index = (Integer) BasicConstants.TEMPLATE_NAME_MAP.get(path);
        System.out.println(index);
        path = ExcelUtil.class.getClassLoader().getResource("").getPath() + BasicConstants.TEMPLATE_URL + path;

        FileInputStream fis = null;
        Workbook wbPartModule = null;
        try {
            fis = new FileInputStream(path);

            if (path.endsWith(".xlsx")) {
                wbPartModule = new XSSFWorkbook(fis);
            } else if (path.endsWith(".xls")) {
                wbPartModule = new HSSFWorkbook(fis);
            }
            Sheet sheet = wbPartModule.getSheetAt(0);
            wbPartModule.setSheetName(0, BasicConstants.EXPORT_NAME_MAP.get(type) + BasicConstants.SYLS_EXPORT_SHEETNAME);
            CellStyle style = setCellBorder(wbPartModule);
            Row row = sheet.getRow(index);
            List<String> format = readTemplate(sheet, index);
            // 写数据
            Object obj = null;
            for (int i = 0; i < cms.size(); i++) {
                row = sheet.getRow(index + i);
                if (row == null) {
                    row = sheet.createRow(index + i);
                }
                row.setHeight((short) (24.75 * 20));
                obj = cms.get(i);
                Cell cell = row.createCell(0);
                cell.setCellStyle(style);
                cell.setCellValue(i + 1);
                for (int j = 0; j < format.size(); j++) {
                    cell = row.createCell(j + 1);
                    cell.setCellStyle(style);
                    String val = BeanUtils.getProperty(obj, getMethodName(format.get(j)));
                    String t = format.get(j).substring(0, 1);
                    if (t.equals(BasicConstants.CELL_TYPE_INTEGER)) {
                        cell.setCellValue(Integer.parseInt(val));
                    } else if (t.equals(BasicConstants.CELL_TYPE_PERCENT) && isNumeric(val)) {
                        CellStyle cellStyle = wbPartModule.createCellStyle();
                        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
                        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
                        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
                        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
                        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
                        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);// 居中
                        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
                        cell.setCellStyle(cellStyle);
                        DecimalFormat f = new DecimalFormat("#.0000");
                        f.setRoundingMode(RoundingMode.HALF_UP); 
                        cell.setCellValue(Double.parseDouble(f.format(Double.parseDouble(val) / 100)));
                    } else {
                        cell.setCellValue(val);
                    }
                }
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return wbPartModule;
    }

    /**
     * 
     * @description 设置单元格边框样式
     * @author z11595
     * @param wbPartModule
     *            EXCEL工作表
     * @return
     * @time 2016年12月14日 下午5:40:01
     */
    private static CellStyle setCellBorder(Workbook wbPartModule) {
        CellStyle style = wbPartModule.createCellStyle();
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setAlignment(CellStyle.ALIGN_CENTER);// 居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
        return style;
    }

    /**
     * 
     * @description 读取当前excel模板的配置信息
     * @author z11595
     * @param sheet
     * @param index
     * @return
     * @time 2016年12月15日 上午9:39:19
     */
    private static List<String> readTemplate(Sheet sheet, Integer index) {
        Row row = sheet.getRow(index);
        List<String> format = new ArrayList<String>();
        for (int r = 1; r < 21; r++) {
            Cell cell = row.getCell(r);
            if (StringUtils.isEmpty(cell.getStringCellValue())
                    || !(cell.getStringCellValue().startsWith("$") || cell.getStringCellValue().startsWith("#") || cell.getStringCellValue().startsWith("&"))) {
                break;
            }
            format.add(cell.getStringCellValue());
        }
        return format;
    }

    /**
     * 根据标题获取相应的方法名称
     * 
     * @param eh
     * @return
     */
    private static String getMethodName(String eh) {
        String mn = eh.substring(1);
        mn = mn.toLowerCase();
        return mn;
    }

    /**
     * 
     * @description 判断是否是数字
     * @author z11595
     * @param str
     *            待处理字符串
     * @return 是否数字的布尔值
     * @time 2016年12月8日 下午3:47:19
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

}
