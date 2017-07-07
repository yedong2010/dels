package com.dels.controller.monitor;

import com.dels.model.monitor.NoBusMatters;
import com.dels.service.ISysMngService;
import com.dels.service.monitor.IBusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @description 数据指标统计-业务办理接口
 * @author z11595
 * @time:2016年12月22日 下午3:25:39
 */
@RestController
@RequestMapping("/bus")
public class BusController {
    /**
     * 业务层操作对象
     */
    @Autowired
    IBusService service;

    @Autowired
    ISysMngService sysMngService;
    /**
     * 
     * @description 无业务事项数
     * @author z11595
     * @param type
     *            类型(province:省级,city:地市)
     * @param year
     *            查询年份
     * @param smonth
     *            起始月份
     * @param emonth
     *            截止月份
     * @param cparams
     *            地市名称数组或区域类别
     * @return 无业务事项记录,总记录,无业务事项率,归属名称
     * @time 2016年12月22日 下午3:33:40
     */
    @RequestMapping(value = "wywsx/{type}/{choseYear}", produces = { "application/json;charset=UTF-8" })
    public List<NoBusMatters> wywsx(@PathVariable String type, @PathVariable Integer choseYear, @RequestParam(value = "smonth", required = false) Integer smonth, @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "cparams", required = false) List<String> cparams) {
        return service.wywsx(type, choseYear, smonth, emonth, cparams);
    }

    /**
     * 
     * @description 无业务事项数概况(查询无业务事项率)
     * @author z11595
     * @param type
     *            类型(province:省级,city:地市)
     * @param year
     *            查询年份
     * @param cparams
     *            地市名称数组或区域类别
     * @return 无业务事项数:wywsxs,进驻事项数:jzsxs,无业务事项率:wywsxl
     * @time 2016年12月22日 下午3:42:57
     */
    @RequestMapping(value = "wywsxgk/{type}/{choseYear}", produces = { "application/json;charset=UTF-8" })
    public NoBusMatters wywsxgk(@PathVariable String type, @PathVariable Integer choseYear, @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "cparams", required = false) List<String> cparams) {
        return service.wywsxgk(type, choseYear, emonth, cparams);
    }

    /**
     * 
     * @description 业务办理情况
     * @author z11595
     * @param type
     *            类型(province:省级;city:地市)
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组
     * @return 名称:name,别名：sname,总数:zs,正常:zc,异常:yc
     * @time 2016年12月22日 下午3:45:44
     */
    @RequestMapping(value = "ywbl/{type}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> ywbl(@PathVariable String type, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "smonth", required = false) Integer smonth,
            @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "cparams", required = false) List<String> cparams) {
        return service.ywbl(type, year, smonth, emonth, cparams);
    }

    /**
     * 
     * @description 获取业务量明细数据
     * @author z11595
     * @param type 类型('swsbx':行政审批的上网申办业务量,'bjzsx':行政审批的办结总量,'wsqlcbjx':行政审批的网上全流程办结量,'swsbs':公共服务的上网申办总量,'bjzss':公共服务的办结总量)
     * @param name 省级部门名称
     * @param year 查询年份
     * @param smonth 开始月份
     * @param emonth 结束月份
     * @return 业务明细记录
     * @time 2017年1月5日 下午2:45:35
     */
    @RequestMapping(value = "detail/{type}/{name}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> detail(@PathVariable String type, @PathVariable String name, @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "smonth", required = false) Integer smonth, @RequestParam(value = "emonth", required = false) Integer emonth) {
        return service.detail(type, name, year, smonth, emonth);
    }

    /**
     * 
     * @description 业务办理概况
     * @author z11595
     * @param type
     *            类型(province:省级;city:地市)
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组
     * @return 总数:zs,正常:zc,异常:yc
     * @time 2016年12月22日 下午3:48:17
     */
    @RequestMapping(value = "ywblgk/{type}", produces = { "application/json;charset=UTF-8" })
    public HashMap<String, Object> ywblgk(@PathVariable String type, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "smonth", required = false) Integer smonth,
            @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "cparams", required = false) List<String> cparams) {
        return service.ywblgk(type, year, smonth, emonth, cparams);
    }

    /**
     * 
     * @description 无业务事项详情
     * @author z11595
     * @param type
     *            类型(province:省级;city:地市)
     * @param year
     *            查询年份
     * @param name
     *            省级部门/地市名称
     * @return 按部门或地市名称查询的无业务事项列表
     * @time 2016年12月22日 下午3:49:43
     */
    @RequestMapping(value = "wywsxxq/{type}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> wywsxxq(@PathVariable String type, @RequestParam(value = "choseYear", required = false) Integer choseYear, @RequestParam(value = "smonth", required = false) Integer smonth, @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "name", required = false) String name) {
        return service.wywsxxq(type, choseYear, smonth, emonth, name);
    }

    /**
     * 
     * @description 异常办结情况
     * @author z11595
     * @param type
     *            类型(province:省级;city:地市)
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param name
     *            地市名称/省级部门名称
     * @return 错误码:code,归属省级/地市名称:name,错误记录总数:zs
     * @time 2016年12月22日 下午3:50:46
     */
    @RequestMapping(value = "ycbj/{type}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> ycbj(@PathVariable String type, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "smonth", required = false) Integer smonth,
            @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "name", required = false) String name) {
        return service.ycbj(type, year, smonth, emonth, name);
    }

    /**
     *
     * @description 异常办结情况-详情列表
     * @author m13624
     * @param type
     *            类型(province:省级;city:地市)
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param name
     *            地市名称/省级部门名称
     * @return 异常办结详情明细列表
     * @time 2017年2月13日 下午3:50:46
     */
    @RequestMapping(value = "ycbjxq/{type}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> ycbjxq(@PathVariable String type, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "smonth", required = false) Integer smonth,
                                              @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "name", required = false) String name) {
        return service.ycbjxq(type, year, smonth, emonth, name);
    }

    /**
     *
     * @description 数据指标统计——业务趋势图——网上办结量
     * @author m13624
     * @param type
     *            类型(all:全省；province:省级;city:地市；county:区县)
     * @param startTime
     *            起始日期
     * @param endTime
     *            截止日期
     * @param cparams
     *            地市名称数组
     * @param area
     *            是否包含非行政区划（1：包含；0:不包含）
     * @param category
     *            区域类别(0:珠三角;1:粤东西北)
     * @return 网上办结量，包含公共服务和行政审批
     * @time 2017年2月28日 上午11:47:46
     */
    @RequestMapping(value = "trendwsbj/{type}/{startTime}/{endTime}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> trendwsbj(@PathVariable String type, @PathVariable String startTime, @PathVariable String endTime, @RequestParam(value = "category", required = false) String category,
                                               @RequestParam(value = "area", required = false) Integer area, @RequestParam(value = "cparams", required = false) List<String> cparams) {
        return service.trendwsbj(type, startTime, endTime, area, category, cparams);
    }

    /**
     *
     * @description 数据指标统计——业务趋势图
     * @author m13624
     * @param type
     *            类型(all:全省；province:省级;city:地市；county:区县)
     * @param startTime
     *            起始日期
     * @param endTime
     *            截止日期
     * @param cparams
     *            地市名称数组
     * @param area
     *            是否包含非行政区划（1：包含；0:不包含）
     * @param category
     *            区域类别(0:珠三角;1:粤东西北)
     * @return 业务趋势图，时间范围内每天的业务量
     * @time 2017年2月28日 上午11:47:46
     */
    @RequestMapping(value = "trend/{type}/{startTime}/{endTime}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> trend(@PathVariable String type, @PathVariable String startTime, @PathVariable String endTime,@RequestParam(value = "category", required = false) String category,
                                               @RequestParam(value = "area", required = false) Integer area, @RequestParam(value = "cparams", required = false) List<String> cparams) {
        return service.trend(type, startTime, endTime, area, category, cparams);
    }
}
