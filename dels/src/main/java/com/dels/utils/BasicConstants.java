package com.dels.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @description 基础常量
 * @author z11595
 * @time 2016年12月22日 下午4:41:19
 */
public interface BasicConstants {

    /**
     * 默认的同比月份，用于判断前端显示的年份是否应为今年
     */
    int DEFAULT_MONTH_EDGE = 4;
    // 查询业务办理情况上网办理状况
    int QUERY_TYPE_INTERNET = 0;
    // 查询非上网办理业务情况
    int QUERY_TYPE_NONINTERNET = 1;
    // 事项概述(全省)
    String BMYWGK_TYPE_QS = "all";
    // 事项概述(省级部门)
    String BMYWGK_TYPE_SZ = "province";
    // 事项概述(地市)
    String BMYWGK_TYPE_DS = "city";
    // 事项概述(区县)
    String BMYWGK_TYPE_QX = "county";
    // 行政审批的上网申办业务量
    String BUS_TYPE_SWSBX = "swsbx";
    // 行政审批办结总数
    String BUS_TYPE_BJZSX = "bjzsx";
    // 行政审批网上全流程办结业务
    String BUS_TYPE_WSQLCBJX = "wsqlcbjx";
    // 公共服务的办结总量
    String BUS_TYPE_BJZSS = "bjzss";
    // 公共服务的上网申办总量
    String BUS_TYPE_SWSBS = "swsbs";
    // 设置当前模板的目录存放地址
    String TEMPLATE_URL = "excel/";

    String SLYSZJ_TYPE_ALL_ALL = "全省";

    String SLYSZJ_TYPE_ALL_SZ = "省级部门";

    String SLYSZJ_TYPE_ALL_DS = "地市";

    String SLYSZJ_TYPE_ALL_DS_ZSJ = "珠三角地市";

    String SLYSZJ_TYPE_ALL_DS_YDXB = "粤东西北地市";

    String SLYSZJ_TYPE_ALL_QX = "区县";

    String SLYSZJ_TYPE_ALL_QX_ZSJ = "珠三角区县";

    String SLYSZJ_TYPE_ALL_QX_YDXB = "粤东西北区县";

    String SLYSZJ_TYPE_ALL_ZJ = "总计";

    String TYPE_ZJ = "总计";

    // EXCEL模板名称映射
    Map EXPORT_NAME_MAP = new HashMap() {
        {
            put("all", "全省");
            put("province", "省级部门");
            put("city", "地市分厅");
            put("county", "区县");
        }
    };

    // EXCEL模板读取配置信息的行数（实际行数-1）
    Map TEMPLATE_NAME_MAP = new HashMap() {
        {
            put("all_slys_report.xls", 4);
            put("province_slys_report.xls", 4);
            put("city_slys_report.xls", 4);
            put("county_slys_report.xls", 4);

            put("province_ywbl_report.xls", 3);
            put("city_ywbl_report.xls", 3);

            put("province_wywsx_report.xls", 2);
            put("city_wywsx_report.xls", 2);

            put("province_sjzl_report.xls", 2);
            put("city_sjzl_report.xls", 2);

            put("cwflxqlb_report.xls", 2);

            put("province_sxjz_report.xls", 2);
            put("city_sxjz_report.xls", 2);
            put("county_sxjz_report.xls", 2);

            put("city_sxjzbmxq_report.xls", 2);
            put("county_sxjzbmxq_report.xls", 2);

            put("province_sxjzsxxq_report.xls", 2);
            put("city_sxjzsxxq_report.xls", 2);
            put("county_sxjzsxxq_report.xls", 2);
        }
    };

    String SYLS_EXPORT_SHEETNAME = "自检情况表（含使用国家级、省级垂直业务系统事项）";
    // 定义当前EXCEL模板的标识符
    String CELL_TYPE_STRING = "$";// 字符串
    String CELL_TYPE_INTEGER = "&";// 整数
    String CELL_TYPE_PERCENT = "#";// 百分比

    String XZQH_TYPE_ZSJ = "ZSJ";// 珠三角
    String XZQH_TYPE_YDXB = " YDXB";// 粤东西北

    String XZQH_NUMBER_ZSJ = "0";// 珠三角
    String XZQH_NUMBER_YDXB = "1";// 粤东西北

    String AUTH_EX_STR = "OP_";
    String NOTE_STATUE_DRAFT = "0";// 内容讨论草稿状态
    String NOTE_STATUE_PUBLIC = "1";// 内容讨论发布状态
    String NOTE_STATUE_DELETE = "-1";// 内容讨论删除状态
    String REPLY_STATUE_PUBLIC = "0";// 评论回复未审核状态 0:未审核；1：审核通过；-1：删除
    String REPLY_STATUE_DROP = "-1";// 删除回复状态

    //put("bj", "bjsj-办结");   其中'bj':表名； 'bjsj':用来时间判断的字段名； '办结':中文名
    Map<String, String> CWFLTABLE_NAME = new HashMap<String, String>() {
        {
            put("bj", "bjsj-办结");
            put("bzgz", "bzgzsj-补证告知");
            put("bzsl", "bzsj-补证受理");
            put("lqdj", "lqsj-领取登记");
            put("sb", "sbsj-申办");
            put("sl", "slsj-受理");
            put("spcl", "spsj-审批处理");
            put("tbcxjg", "tbcxjsrq-特别程序结果");
            put("tbcxsq", "tbcxksrq-特别程序申请");
            put("wsysl", "yslsj-网上预受理");
        }
    };
}
