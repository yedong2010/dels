package com.dels.controller.monitor;

import java.util.HashMap;
import java.util.List;

import com.dels.service.ISysMngService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dels.service.monitor.ITotalityService;

/**
 * 
 * @description 数据指标统计-总体统计接口
 * @author z11595
 * @time:2016年12月22日 下午3:25:39
 */
@RestController
@RequestMapping("/totality")
public class TotalityController {
    /**
     * 业务层操作对象
     */
    @Autowired
    ITotalityService service;

    @Autowired
    ISysMngService sysMngService;
    /**
     * 
     * @description 办理概况信息
     * @author z11595
     * @param type
     *            类型(all:全省,province:省级,city:地市,county:区县)
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组
     * @param category
     *            区域类别
     * @return 月份信息:yf、上网办理:internet、非上网办理业务量:no_internet
     * @time 2016年12月22日 下午3:26:05
     */
    @RequestMapping(value = "handle/{type}", produces = { "application/json;charset=UTF-8" })
    public HashMap<String, Object> handleSurvey(@PathVariable String type, @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "smonth", required = false) Integer smonth, @RequestParam(value = "emonth", required = false) Integer emonth,
            @RequestParam(value = "cparams", required = false) List<String> cparams, @RequestParam(value = "category", required = false) String category, @RequestParam(value = "area", required = false) String area) {
        return service.handleSurvey(type, year, smonth, emonth, category, area, cparams);
    }

    /**
     * 
     * @description 三率(网上全流程办理、上网办理、网上办结)
     * @author z11595
     * @param type
     *            类型(all:全省,province:省级,city:地市,county:区县)
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组
     * @param category
     *            区域类别
     * @return 网上全流程办理、上网办理、网上办结
     * @time 2016年12月22日 下午3:27:34
     */
    @RequestMapping(value = "rate/{type}", produces = { "application/json;charset=UTF-8" })
    public HashMap<String, Object> rate(@PathVariable String type, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "smonth", required = false) Integer smonth,
            @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "cparams", required = false) List<String> cparams,
            @RequestParam(value = "category", required = false) String category, @RequestParam(value = "area", required = false) String area) {
        return service.rate(type, year, smonth, emonth, area, category, cparams);
    }

    /**
     * 
     * @description 到现场次数概况查询
     * @author z11595
     * @param type
     *            类型(all:全省,province:省级,city:地市,county:区县)
     * @param cparams
     *            地市名称数组
     * @param category
     *            区域类别(0:珠三角,1:粤东西北)
     * @return 跑动次数名称:name,跑动次数记录数:y
     * @time 2016年12月22日 下午3:28:45
     */
    @RequestMapping(value = "times/{type}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> times(@PathVariable String type, @RequestParam(value = "cparams", required = false) List<String> cparams,
            @RequestParam(value = "category", required = false) String category, @RequestParam(value = "area", required = false) String area) {
        return service.times(type, area, category, cparams);
    }

    /**
     * 
     * @description 进驻部门、事项、业务量统计、线上业务量统计
     * @author z11595
     * @param type
     *            类型(all:全省,province:省级,city:地市,county:区县)
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组
     * @param category
     *            区域类别
     * @return 进驻部门:jzbm,进驻事项:sxzs,行政审批:xzsp,公共服务:shfw
     * @time 2016年12月22日 下午3:30:06
     */
    @RequestMapping(value = "itemsurvey/{type}", produces = { "application/json;charset=UTF-8" })
    public HashMap<String, Object> itemSurvey(@PathVariable String type, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "smonth", required = false) Integer smonth,
            @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "cparams", required = false) List<String> cparams,
            @RequestParam(value = "category", required = false) String category, @RequestParam(value = "area", required = false) String area) {
        return service.itemSurvey(type, year, smonth, emonth, area, category, cparams);
    }

    /**
     *
     * @description 总体统计页面——办结总业务量排名
     * @author m13624
     * @param type
     *            类型(all:全省,province:省级,city:地市,county:区县)
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组
     * @param category
     *            区域类别
     * @return 业务量排名数组
     * @time 2017年03月08日 下午3:24:28
     */
    @RequestMapping(value = "ywlph/{type}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> ywlph(@PathVariable String type, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "smonth", required = false) Integer smonth,
                                              @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "cparams", required = false) List<String> cparams,
                                              @RequestParam(value = "category", required = false) String category, @RequestParam(value = "area", required = false) String area) {
        return service.ywlph(type, year, smonth, emonth, area, category, cparams);
    }

    /**
     *
     * @description 计算过程追溯
     * @author m13624
     * @param dateTime
     *            查询年月日
     * @return 查询日期，指标计算过程的每一个计算步骤，和计算结果。
     * @time 2017年03月16日 11:16:21
     */
    @RequestMapping(value = "countChase", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> countChase(@RequestParam(value = "dateTime", required = false) String dateTime) {
        return service.countChase(dateTime);
    }

    /**
     *
     * @description 办结总业务量排名
     * @author m13624
     * @param type
     *            类型(all:全省,province:省级,city:地市,county:区县)
     * @param startTime
     *            开始日期
     * @param endTime
     *            结束日期
     * @param cparams
     *            地市名称数组
     * @param category
     *            区域类别
     * @return 办结业务量排名数组
     * @time 2017年03月28日 19:26:18
     */
    @RequestMapping(value = "bjywlph/{type}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> bjywlph(@PathVariable String type, @RequestParam(value = "startTime", required = false) String startTime,
                                               @RequestParam(value = "endTime", required = false) String endTime, @RequestParam(value = "cparams", required = false) List<String> cparams,
                                               @RequestParam(value = "category", required = false) String category, @RequestParam(value = "area", required = false) String area) {
        return service.bjywlph(type, startTime, endTime, area, category, cparams);
    }

    /**
     *
     * @description 办结业务量排名——指定某个省级部门或者地市，查看业务量排名
     * @author m13624
     * @param type
     *            province:省级；city：地市；county:区县
     * @param startTime
     *            起始日期
     * @param endTime
     *            截止日期
     * @param id
     *            如果是type为province，即为组织机构zzjgdm；如果type为city，即为市级名称sjmc
     * @return 查询日期，指标计算过程的每一个计算步骤，和计算结果。
     * @time 2017年03月28日 20:04:47
     */
    @RequestMapping(value = "bjywlphDetail/{type}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> bjywlphDetail(@PathVariable String type, @RequestParam(value = "startTime", required = false) String  startTime,
                                                     @RequestParam(value = "endTime", required = false) String endTime, @RequestParam(value = "id", required = false) String id) {
        return service.bjywlphDetail(type, startTime, endTime, id);
    }

    /**
     *
     * @description 申办业务量排名
     * @author m13624
     * @param type
     *            类型(all:全省,province:省级,city:地市,county:区县)
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组
     * @param category
     *            区域类别
     * @return 申办业务量排名数组
     * @time 2017年03月27日 09:42:01
     */
    @RequestMapping(value = "sbywlph/{type}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> sbywlph(@PathVariable String type, @RequestParam(value = "startTime", required = false) String startTime,
                                               @RequestParam(value = "endTime", required = false) String endTime, @RequestParam(value = "cparams", required = false) List<String> cparams,
                                               @RequestParam(value = "category", required = false) String category, @RequestParam(value = "area", required = false) String area) {
        return service.sbywlph(type, startTime, endTime, area, category, cparams);
    }

    /**
     *
     * @description 申办业务量排名——指定某个省级部门或者地市，查看申办业务量排名
     * @author m13624
     * @param type
     *            province:省级；city：地市；county:区县
     * @param year
     *            年
     * @param smonth
     *            起始月份
     * @param emonth
     *            截止月份
     * @param id
     *            如果是type为province，即为组织机构zzjgdm；如果type为city，即为市级名称sjmc
     * @return 查询日期，指标计算过程的每一个计算步骤，和计算结果。
     * @time 2017年03月27日 10:37:22
     */
    @RequestMapping(value = "sbywlphDetail/{type}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> sbywlphDetail(@PathVariable String type, @RequestParam(value = "startTime", required = false) String startTime,
                                                     @RequestParam(value = "endTime", required = false) String endTime, @RequestParam(value = "id", required = false) String id) {
        return service.sbywlphDetail(type, startTime, endTime, id);
    }
}
