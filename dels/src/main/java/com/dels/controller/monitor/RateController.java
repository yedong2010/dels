package com.dels.controller.monitor;

import com.dels.service.monitor.IRateService;
import com.dels.model.monitor.CommonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author z11595
 * @description 数据指标统计-三率一数接口
 * @time:2016年12月22日 下午3:25:39
 */
@RestController
@RequestMapping("/rate")
public class RateController {
    /**
     * 业务层操作对象
     */
    @Autowired
    IRateService service;

    /**
     * 
     * @description 三率一数统计数据
     * @author z11595
     * @param type
     *            类型(province:省级,city:地市,county:区县)
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
     * @return 三率一数包括行政审批和公共服务业务量详情
     * @time 2016年12月22日 下午3:32:20
     */
    @RequestMapping(value = "slys/{type}", produces = {"application/json;charset=UTF-8"})
    public List<CommonModel> slys(@PathVariable String type, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "smonth", required = false) Integer smonth,
                                  @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "cparams", required = false) List<String> cparams,
                                  @RequestParam(value = "category", required = false) String category, @RequestParam(value = "area", required = false) String area) {
        return service.slys(type, year, smonth, emonth, area, category, cparams);
    }

    /**
     * @param type 类型(province:省级;city:地市)
     * @param name 省级部门/地市名称
     * @return 指定省级/地市名称的网上全流程事项列表
     * @description 网上全流程事项数详情
     * @author z11595
     * @time 2016年12月22日 下午4:02:24
     */
    @RequestMapping(value = "wsqlcsxsxq/{type}/{ifsxzs}", produces = {"application/json;charset=UTF-8"})
    public List<HashMap<String, Object>> wsqlcsxsxq(@PathVariable String type, @PathVariable Integer ifsxzs, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "choseYear", required = false) Integer choseYear, @RequestParam(value = "emonth", required = false) Integer emonth) {
        return service.wsqlcsxsxq(type, ifsxzs, name, choseYear, emonth);
    }

    /**
     * @param type     类型(city:地市;count：区县)
     * @param cparams  地市名称数组
     * @param category 行政区划类别
     * @return 指定地市/区县名称的事项进驻概况列表
     * @description 事项进驻统计
     * @author m13624
     * @time 2016年12月24日 下午:01:02
     */
    @RequestMapping(value = "sxjz/{type}", produces = {"application/json;charset=UTF-8"})
    public List<HashMap<String, Object>> sxjz(@PathVariable String type, @RequestParam(value = "cparams", required = false) List<String> cparams,
                                              @RequestParam(value = "category", required = false) String category, @RequestParam(value = "area", required = false) String area, @RequestParam(value = "choseYear", required = false) Integer choseYear, @RequestParam(value = "emonth", required = false) Integer emonth) {
        return service.sxjz(type, category, area, choseYear, emonth, cparams);
    }

    /**
     * @param type 类型(city:地市;count：区县)
     * @param name 地市名称/区县名称
     * @return 指定地市/区县名称的已进驻部门详情
     * @description 事项进驻——已进驻部门详情
     * @author m13624
     * @time 2016年12月27日 下午:03:09
     */
    @RequestMapping(value = "sxjzbmxq/{type}", produces = {"application/json;charset=UTF-8"})
    public List<HashMap<String, Object>> sxjzbmxq(@PathVariable String type, @RequestParam(value = "name", required = false) String name) {
        return service.sxjzbmxq(type, name);
    }

    /**
     * @param type   类型(city:地市;count：区县)
     * @param name   地市名称/区县名称
     * @param sxlxmc 事项类型名称
     * @return 指定地市/区县名称的已进驻事项详情
     * @description 事项进驻——已进驻事项详情
     * @author m13624
     * @time 2016年12月27日 下午:03:56
     */
    @RequestMapping(value = "sxjzsxxq/{type}", produces = {"application/json;charset=UTF-8"})
    public List<HashMap<String, Object>> sxjzsxxq(@PathVariable String type, @RequestParam(value = "name", required = false) String name,
                                                  @RequestParam(value = "sxlxmc", required = false) String sxlxmc, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "emonth", required = false) Integer emonth) {
        return service.sxjzsxxq(type, name, sxlxmc, year, emonth);
    }
    
    /**
     * 
     * @description 进驻事项明细
     * @author z11595
     * @param type 类型(city:地市;count：区县)
     * @param pdcs 跑动次数
     * @param name 省级|部门名称
     * @return 返回该跑动次数的事项信息
     * @time 2017年1月9日 下午3:54:11
     */
   @RequestMapping(value = "sxjzBypd/{type}", produces = { "application/json;charset=UTF-8" })
   public List<HashMap<String, Object>> sxjzBypd(@PathVariable String type,@RequestParam(value = "pdcs", required = false) String pdcs, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "choseYear", required = false) Integer choseYear, @RequestParam(value = "emonth", required = false) Integer emonth) {
       return service.sxjzBypd(type, pdcs, name, choseYear, emonth);
   }

    /**
     *
     * @description 指标统计（三率一数简化版）
     * @author m13624
     * @param type 类型(province:省级;city:地市;count：区县)
     * @param choseYear 选择年份
     * @return 返回三率一数简化版
     * @time 2017年3月13日 下午4:40:48
     */
    @RequestMapping(value = "simpleRate/{type}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> simpleRate(@PathVariable String type, @RequestParam(value = "choseYear", required = false) Integer choseYear, @RequestParam(value = "smonth", required = false) Integer smonth,
                                                    @RequestParam(value = "emonth", required = false) Integer emonth) {
        return service.simpleRate(type, choseYear, smonth, emonth);
    }

    /**
     *
     * @description 数据指标统计——综合排名
     * @author m13624
     * @param startTime
     *            查询起始年月
     * @param endTime
     *            查询截止年月
     * @return 查询日期范围内事项总数、总业务量、网上全流程办理率、上网办理率、网上办结率、到现场次数、审批时间压缩率、总分数等。
     * @time 2017年03月15日 19:26:51
     */
    @RequestMapping(value = "rateRank", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> rateRank(@RequestParam(value = "startTime", required = false) String startTime,
                                                         @RequestParam(value = "endTime", required = false) String endTime) {
        return service.rateRank(startTime, endTime);
    }

    /**
     *
     * @description 数据指标统计——数据监测异常情况
     * @author m13624
     * @param startTime
     *            查询起始年月
     * @param endTime
     *            查询截止年月
     * @return 查询日期范围内业务逻辑异常数、业务过程异常数、业务总量异常数、数据内容异常数、数据规范异常数
     * @time 2017年03月15日 20:12:35
     */
    @RequestMapping(value = "dataAbnormal", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> dataAbnormal(@RequestParam(value = "startTime", required = false) String startTime,
                                                  @RequestParam(value = "endTime", required = false) String endTime) {
        return service.dataAbnormal(startTime, endTime);
    }
}
