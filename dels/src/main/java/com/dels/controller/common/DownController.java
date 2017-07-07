package com.dels.controller.common;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.dels.model.monitor.CommonModel;
import com.dels.model.monitor.NoBusMatters;
import com.dels.service.ISysMngService;
import com.dels.service.monitor.IQualityService;
import com.dels.service.monitor.IRateService;
import com.dels.service.monitor.ITotalityService;
import com.dels.utils.BasicConstants;
import com.dels.utils.ExcelUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dels.service.monitor.IBusService;

/**
 * @author z11595
 * @description 数据下载接口RESTFUL
 * @time 2016年12月22日 下午3:07:24
 */
@RestController
@RequestMapping("/down")
public class DownController {

    @Autowired
    IRateService rservice;
    @Autowired
    ITotalityService tservice;
    @Autowired
    IBusService bservice;
    @Autowired
    IQualityService qservice;
    @Autowired
    ISysMngService sysMngService;

    /**
     * @param type     类型(province:省级,city:地市,county:区县)
     * @param year     查询年份
     * @param smonth   开始月份
     * @param emonth   结束月份
     * @param cparams  地市名称数组
     * @param category 行政区划类别(珠三角：0,粤东西北:1)
     * @param response http响应
     * @description 三率一数统计数据下载接口
     * @author z11595
     * @time 2016年12月22日 下午3:07:47
     */
    @RequestMapping(value = "excel/slys/{type}", produces = {"application/json;charset=UTF-8"})
    public void downSlys(@PathVariable String type, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "smonth", required = false) Integer smonth,
                         @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "cparams", required = false) String cparams,
                         @RequestParam(value = "category", required = false) String category, @RequestParam(value = "area", required = false) String area, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/force-download");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/x-excel");
        try {
            response.setHeader("Content-Disposition",
                    "attachment; fileName=" + java.net.URLEncoder.encode(year + "年" + BasicConstants.EXPORT_NAME_MAP.get(type) + "事项业务数据情况表汇总" + getDateString() + ".xls", "UTF-8"));
            List<CommonModel> cms = rservice.slys_down(type, year, smonth, emonth, category, area, toggleStr(cparams));
            Workbook wbPartModule = ExcelUtil.readTemplateBypath(type + "_slys_report.xls", cms, CommonModel.class, type);
            OutputStream os = response.getOutputStream();
            wbPartModule.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param type     类型(province:省级,city:地市,county:区县)
     * @param year     查询年份
     * @param smonth   开始月份
     * @param emonth   结束月份
     * @param cparams  地市/区划范围
     * @param response http响应
     * @description 业务办理下载接口
     * @author z11595
     * @time 2016年12月22日 下午3:09:47
     */
    @RequestMapping(value = "excel/ywbl/{type}", produces = {"application/json;charset=UTF-8"})
    public void downYwbl(@PathVariable String type, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "smonth", required = false) Integer smonth,
                         @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "cparams", required = false) String cparams, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/force-download");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/x-excel");
        try {
            response.setHeader("Content-Disposition",
                    "attachment; fileName=" + java.net.URLEncoder.encode(year + "年" + BasicConstants.EXPORT_NAME_MAP.get(type) + "业务办理情况表汇总" + getDateString() + ".xls", "UTF-8"));
            List<HashMap<String, Object>> list = bservice.ywbl(type, year, smonth, emonth, toggleStr(cparams));
            Workbook wbPartModule = ExcelUtil.readTemplateByClasspath(type + "_ywbl_report.xls", list);
            OutputStream os = response.getOutputStream();
            wbPartModule.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param type     类型(province:省级,city:地市,county:区县)
     * @param
     * @param cparams  地市/区划范围
     * @param response http响应
     * @description 无业务事项下载接口
     * @author z11595
     * @time 2016年12月22日 下午3:10:25
     */
    @RequestMapping(value = "excel/wywsx/{type}", produces = {"application/json;charset=UTF-8"})
    public void downWywsx(@PathVariable String type, @RequestParam(value = "choseYear", required = false) Integer choseYear, @RequestParam(value = "smonth", required = false) Integer smonth, @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "cparams", required = false) String cparams,
                          HttpServletResponse response) {
        response.setHeader("Content-Type", "application/force-download");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/x-excel");
        try {
            response.setHeader("Content-Disposition",
                    "attachment; fileName=" + java.net.URLEncoder.encode(choseYear + "年" + smonth + "月-" + emonth + "月" + BasicConstants.EXPORT_NAME_MAP.get(type) + "无业务事项情况表汇总" + getDateString() + ".xls", "UTF-8"));
            List<NoBusMatters> list = bservice.wywsx(type, choseYear, smonth, emonth, toggleStr(cparams));
            Workbook wbPartModule = ExcelUtil.readTemplateBypath(type + "_wywsx_report.xls", list, NoBusMatters.class, type);
            OutputStream os = response.getOutputStream();
            wbPartModule.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param type     类型(province:省级,city:地市,county:区县)
     * @param year     查询年份
     * @param smonth   开始月份
     * @param emonth   结束月份
     * @param cparams  地市/区划范围
     * @param response http响应
     * @description 数据质量报告下载接口
     * @author z11595
     * @time 2016年12月22日 下午3:11:11
     */
    @RequestMapping(value = "excel/sjzl/{type}", produces = {"application/json;charset=UTF-8"})
    public void downSjzl(@PathVariable String type, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "smonth", required = false) Integer smonth,
                         @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "cparams", required = false) String cparams, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/force-download");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/x-excel");
        try {
            response.setHeader("Content-Disposition",
                    "attachment; fileName=" + java.net.URLEncoder.encode(year + "年" + BasicConstants.EXPORT_NAME_MAP.get(type) + "数据质量情况表汇总" + getDateString() + ".xls", "UTF-8"));
            List<HashMap<String, Object>> list = qservice.sjzl(type, year, smonth, emonth, toggleStr(cparams));
            Workbook wbPartModule = ExcelUtil.readTemplateByClasspath(type + "_sjzl_report.xls", list);
            OutputStream os = response.getOutputStream();
            wbPartModule.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return yyyyMMddHHmm格式的时间戳
     * @description 下载文件生成当前时间戳函数
     * @author: z11595
     * @time 2016年12月22日 下午3:11:44
     */
    public String getDateString() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmm");
        return sdf.format(now);
    }

    /**
     * @param str 区划范围字符串
     * @return 字符串数组
     * @description 超链接传参字符串处理函数
     * @author: z11595
     * @time 2016年12月22日 下午3:12:24
     */
    public List<String> toggleStr(String str) {
        if (str.equals("") || str == null) {
            return null;
        } else {
            if (str.startsWith("[") && str.endsWith("]")) {
                str = str.substring(1, str.length() - 1).replaceAll("\"", "").replaceAll(" ", "");
                if (StringUtils.isEmpty(str)) {
                    return null;
                }
                String[] sp = str.split(",");
                List<String> list = Arrays.asList(sp);
                return list;
            } else {
                List<String> st = new ArrayList<>();
                st.add(str);
                return st;
            }

        }
    }

    /**
     * @param type     类型(province:省级,city:地市,county:区县)
     * @param cparams  地市名称数组
     * @param category 行政区划类别(珠三角：0,粤东西北:1)
     * @param response http响应
     * @description 事项进驻下载接口
     * @author m13624
     * @time 2016年12月26日 上午10:07:45
     */
    @RequestMapping(value = "excel/sxjz/{type}", produces = {"application/json;charset=UTF-8"})
    public void downSxjz(@PathVariable String type, @RequestParam(value = "cparams", required = false) String cparams, @RequestParam(value = "category", required = false) String category, @RequestParam(value = "area", required = false) String area, @RequestParam(value = "choseYear", required = false) Integer choseYear, @RequestParam(value = "emonth", required = false) Integer emonth, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/force-download");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/x-excel");
        try {
            response.setHeader("Content-Disposition",
                    "attachment; fileName=" + java.net.URLEncoder.encode(BasicConstants.EXPORT_NAME_MAP.get(type) + "事项进驻统计" + getDateString() + ".xls", "UTF-8"));
            List<HashMap<String, Object>> list = rservice.sxjz_down(type, category, area, choseYear, emonth, toggleStr(cparams));
            Workbook wbPartModule = ExcelUtil.readTemplateByClasspath(type + "_sxjz_report.xls", list);
            OutputStream os = response.getOutputStream();
            wbPartModule.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param type     类型(city:地市,county:区县)
     * @param name     地市/区县名称
     * @param response http响应
     * @description 事项进驻——指定已进驻部门详情下载接口
     * @author m13624
     * @time 2016年12月28日 上午11:22:23
     */
    @RequestMapping(value = "excel/sxjzbmxq/{type}/{name}", produces = {"application/json;charset=UTF-8"})
    public void downSxjzbmxq(@PathVariable String type, @PathVariable String name, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/force-download");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/x-excel");
        try {
            response.setHeader("Content-Disposition",
                    "attachment; fileName=" + java.net.URLEncoder.encode(name + "已进驻部门统计" + getDateString() + ".xls", "UTF-8"));
            List<HashMap<String, Object>> list = rservice.sxjzbmxq_down(type, name);
            Workbook wbPartModule = ExcelUtil.readTemplateByClasspath(type + "_sxjzbmxq_report.xls", list);
            OutputStream os = response.getOutputStream();
            wbPartModule.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param type     类型(province:省级,city:地市,county:区县)
     * @param name     省级/地市/区县名称
     * @param sxlxmc   事项类型名称
     * @param response http响应
     * @description 事项进驻——指定已进驻事项详情下载接口
     * @author m13624
     * @time 2016年12月28日 下午15:00:03
     */
    @RequestMapping(value = "excel/sxjzsxxq/{type}/{name}/{sxlxmc}", produces = {"application/json;charset=UTF-8"})
    public void downSxjzsxxq(@PathVariable String type, @PathVariable String name, @PathVariable String sxlxmc, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "emonth", required = false) Integer emonth, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/force-download");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/x-excel");
        try {
            response.setHeader("Content-Disposition",
                    "attachment; fileName=" + java.net.URLEncoder.encode(name + "已进驻事项统计" + getDateString() + ".xls", "UTF-8"));
            List<HashMap<String, Object>> list = rservice.sxjzsxxq_down(type, name, sxlxmc, year, emonth);
            Workbook wbPartModule = ExcelUtil.readTemplateByClasspath(type + "_sxjzsxxq_report.xls", list);
            OutputStream os = response.getOutputStream();
            wbPartModule.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param year     查询年份
     * @param smonth   开始月份
     * @param emonth   结束月份
     * @param name  省级或地市名称
     * @param response http响应
     * @description 数据质量统计——错误分类详情列表
     * @author m13624
     * @time 2017年04月20日 上午10:58:10
     */
    @RequestMapping(value = "excel/cwflxqlb/{name}", produces = {"application/json;charset=UTF-8"})
    public void downCwflxqlb(@PathVariable String name, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "smonth", required = false) Integer smonth,
                         @RequestParam(value = "emonth", required = false) Integer emonth, HttpServletResponse response) {
        response.setHeader("Content-Type", "application/force-download");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/x-excel");
        try {
            response.setHeader("Content-Disposition",
                    "attachment; fileName=" + java.net.URLEncoder.encode(name + year + "年" + smonth + "月" + "-" + emonth + "月" + "错误分类详情列表统计" + getDateString() + ".csv", "UTF-8"));
            List<HashMap<String, Object>> list = qservice.cwflxqlb(name, year, smonth, emonth);
            OutputStream os = response.getOutputStream();
            for(HashMap<String, Object> map : list){
                String line =  map.get("sblsh").toString() + "," +
                        map.get("sxbm").toString() +"," +
                        map.get("error").toString() + "," +
                        map.get("hj").toString() + "\n";
                byte[] b =line.getBytes();
                os.write(b);
                }
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
