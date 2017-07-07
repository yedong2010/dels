package com.dels.service.monitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dels.model.monitor.NoBusMatters;

/**
 * 
 * @description 业务办理服务接口
 * @author z11595
 * @time 2016年12月22日 下午5:56:41
 */
public abstract interface IBusService {

    /**
     * 
     * @description 无业务事项数
     * @author z11595
     * @param type
     *            (province:省级部门;city:地市)
     * @param year
     *            查询年份
     * @param smonth
     *            起始月份
     * @param emonth
     *            截止月份
     * @param cparams
     *            地市名称数组
     * @return 无业务事项记录,总记录,无业务事项率,归属名称
     * @time 2016年11月30日 上午10:17:03
     */
    List<NoBusMatters> wywsx(String type, Integer choseYear, Integer smonth, Integer emonth, List<String> cparams);

    /**
     * 
     * @description 无业务事项数概况
     * @author z11595
     * @param type
     *            (province:省级部门;city:地市)
     * @param year
     *            查询年份
     * @param cparams
     *            地市名称数组
     * @return 无业务事项数:wywsxs,进驻事项数:jzsxs,无业务事项率:wywsxl
     * @time 2016年11月30日 下午2:19:59
     */
    NoBusMatters wywsxgk(String type, Integer choseYear, Integer emonth, List<String> cparams);

    /**
     * 
     * @description 业务办理详情
     * @author z11595
     * @param type
     *            (province:省级部门;city:地市)
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组
     * @return 名称:name,别名：sname,总数:zs,正常:zc,异常:yc
     * @time 2016年12月1日 下午3:23:18
     */
    List<HashMap<String, Object>> ywbl(String type, Integer year, Integer smonth, Integer emonth, List<String> cparams);

    /**
     * 
     * @description 业务办理概况
     * @author z11595
     * @param type
     *            (province:省级部门;city:地市)
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组
     * @return 总数:zs,正常:zc,异常:yc
     * @time 2016年12月1日 下午4:17:12
     */
    HashMap<String, Object> ywblgk(String type, Integer year, Integer smonth, Integer emonth, List<String> cparams);

    /**
     * 
     * @description 无业务事项详情
     * @author z11595
     * @param type
     *            (province:省级部门;city:地市;county:区县)
     * @param year
     *            查询年份
     * @param smonth
     *            起始月份
     * @param emonth
     *            截止月份
     * @param name
     *            省级部门/地市名称
     * @return 按部门或地市名称查询的无业务事项列表
     * @time 2016年12月2日 下午3:09:30
     */
    List<HashMap<String, Object>> wywsxxq(String type, Integer choseYear, Integer smonth, Integer emonth, String name);

    /**
     * 
     * @description 查询异常办结
     * @author z11595
     * @param type
     *            (province:省级;city:地市)
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param name
     *            地市名称/省级部门名称
     * @return 错误码:code,归属省级/地市名称:name,错误记录总数:zs
     * @time 2016年12月4日 下午2:00:29
     */
    List<HashMap<String, Object>> ycbj(String type, Integer year, Integer smonth, Integer emonth, String name);

    /**
     *
     * @description 查询异常办结-详情列表
     * @author m13624
     * @param type
     *            (province:省级;city:地市)
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param name
     *            地市名称/省级部门名称
     * @return 异常办结详情明细列表
     * @time 2017年2月13日 下午2:00:29
     */
    List<HashMap<String, Object>> ycbjxq(String type, Integer year, Integer smonth, Integer emonth, String name);

    /**
     * 
     * @description 业务明细查询
     * @author z11595
     * @param type
     *              类型('swsbx':行政审批的上网申办业务量,'bjzsx':行政审批的办结总量,'wsqlcbjx':行政审批的网上全流程办结量,'swsbs':公共服务的上网申办总量,'bjzss':公共服务的办结总量)
     * @param name
     *              省级部门名称
     * @param year
     *              查询年份
     * @param smonth
     *              开始月份
     * @param emonth
     *              结束月份
     * @return 业务详情数据
     * @time 2017年1月5日 下午2:46:44
     */
    List<HashMap<String, Object>> detail(String type, String name, Integer year, Integer smonth, Integer emonth);

    /**
     *
     * @description 数据指标统计——业务趋势图——网上办结
     * @author m13624
     * @param type
     *             类型('all':全省,'province':省级,'city':地市,'county':区县)
     * @param startTime
     *             起始日期
     * @param endTime
     *             截止日期
     * @param area
     *             是否包含非行政区划（1：包含；0：不包含）
     * @param cparams
     *             地市名称数组
     * @param category
     *            区域类别(0:珠三角;1:粤东西北)
     * @return 网上办结量，包含行政审批和公共服务
     * @time 2017年2月28日 下午2:31:20
     */
    List<HashMap<String, Object>> trendwsbj(String type, String startTime, String endTime, Integer area, String category, List<String> cparams);


    /**
     *
     * @description 数据指标统计——业务趋势图
     * @author m13624
     * @param type
     *             类型('all':全省,'province':省级,'city':地市,'county':区县)
     * @param startTime
     *             起始日期
     * @param endTime
     *             截止日期
     * @param area
     *             是否包含非行政区划（1：包含；0：不包含）
     * @param cparams
     *             地市名称数组
     * @param category
     *            区域类别(0:珠三角;1:粤东西北)
     * @return 业务趋势图数组，时间范围内每天的业务量
     * @time 2017年2月28日 下午2:31:20
     */
    List<HashMap<String, Object>> trend(String type, String startTime, String endTime, Integer area, String category, List<String> cparams);

    /**
     * @description: 部门事项变更信息服务，查询部门无业务事项数
     * @author: l13595
     * @param: zzjgdm-组织机构代码，year-查询年份， smonth-查询起始月份， emonth-查询截止月份
     * @return: 返回部门名称、部门无业务事项数、部门事项总数、信息统计年份、信息统计月份
     * @time: 2017/3/24 11:22
     */
    List<Map<String, Object>> wywsxRestful(String zzjgdm, Integer year, Integer smonth, Integer emonth);

}
