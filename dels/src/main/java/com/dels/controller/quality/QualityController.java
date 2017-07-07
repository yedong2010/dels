package com.dels.controller.quality;

import com.dels.service.ISysMngService;
import com.dels.service.monitor.IQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @description 数据指标统计API接口
 * @author z11595
 * @time 2016年12月22日 下午3:15:27
 */
@RestController
@RequestMapping("/quality")
public class QualityController {
    /**
     * 业务层操作对象
     */
    @Autowired
    IQualityService service;

    @Autowired
    ISysMngService sysMngService;
    /**
     *
     * @description 质量概况信息
     * @author z11595
     * @param type
     *            类型(all:全省,province:省级,city:地市)
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            区划范围(地市名称数组或区划类别)
     * @return 正确记录数、错误记录数、总记录、正确率
     * @time 2016年12月22日 下午3:16:03
     */
    @RequestMapping(value = "zl/{type}", produces = { "application/json;charset=UTF-8" })
    public HashMap<String, Object> zlgk(@PathVariable String type, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "smonth", required = false) Integer smonth,
            @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "cparams", required = false) List<String> cparams) {
        return service.zlgk(type, year, smonth, emonth, cparams);
    }

    /**
     *
     * @description 错误分类概况
     * @author z11595
     * @param type
     *            (all:全省,province:省级,city:地市)
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            区划范围(地市名称数组或区划类别)
     * @return 错误类别:name,错误记录数：y
     * @time 2016年12月22日 下午3:20:03
     */
    @RequestMapping(value = "cwfl/{type}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> cwfl(@PathVariable String type, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "smonth", required = false) Integer smonth,
            @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "cparams", required = false) List<String> cparams) {
        return service.cwfl(type, year, smonth, emonth, cparams);
    }

    /**
     *
     * @description 通过地市或省级名称查询错误分类详情
     * @author z11595
     * @param name
     *            地市名称/省级部门名称
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @return 错误类别码:error,错误记录数:zs,名称:name
     * @time 2016年12月22日 下午3:21:51
     */
    @RequestMapping(value = "cwflxq/{name}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> cwflxq(@PathVariable String name, @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "smonth", required = false) Integer smonth, @RequestParam(value = "emonth", required = false) Integer emonth) {
        return service.cwflxq(name, year, smonth, emonth);
    }

    /**
     *
     * @description 通过地市或省级名称查询错误分类详情列表
     * @author m13624
     * @param name
     *            地市名称/省级部门名称
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @return 错误类别码:error,错误记录数:zs,名称:name
     * @time 2017年04月19日 上午10:32:12
     */
    @RequestMapping(value = "cwflxqlb/{name}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> cwflxqlb(@PathVariable String name, @RequestParam(value = "year", required = false) Integer year,
                                                @RequestParam(value = "smonth", required = false) Integer smonth, @RequestParam(value = "emonth", required = false) Integer emonth) {
        return service.cwflxqlb(name, year, smonth, emonth);
    }

    /**
     *
     * @description 数据质量统计
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
     *            地市名称数组或区域类别
     * @return 名称,总量，正确记录数，错误记录数，正确率
     * @time 2016年12月22日 下午3:59:43
     */
    @RequestMapping(value = "sjzl/{type}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> sjzl(@PathVariable String type, @RequestParam(value = "year", required = false) Integer year, @RequestParam(value = "smonth", required = false) Integer smonth,
            @RequestParam(value = "emonth", required = false) Integer emonth, @RequestParam(value = "cparams", required = false) List<String> cparams) {
        return service.sjzl(type, year, smonth, emonth, cparams);
    }

    /**
     *
     * @description 进驻事项数据版本——全省进驻事项数
     * @author m13624
     * @param choseDate
     *            查询日期,如'2016-01-01'
     * @return
     *            指定日期的全省进驻事项数
     * @time 2017年01月06日 上午10:10:02
     */
    @RequestMapping(value = "qsjzsxs/{choseDate}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> qsjzsxs( @PathVariable String choseDate) {
        return service.qsjzsxs(choseDate);
    }

    /**
     *
     * @description 进驻事项数据版本——全省进驻事项数版本对比
     * @author m13624
     * @param startTime 版本1日期 endTime 版本2日期
     *            版本1日期和版本2日期,格式如'2016-01-01'
     * @return
     *            版本1和版本2之间的差异事项数列表
     * @time 2017年01月07日 上午10:21:38
     */
    @RequestMapping(value = "qsjzsxsdb/{startTime}/{endTime}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> qsjzsxsdb( @PathVariable String startTime, @PathVariable String endTime) {
        return service.qsjzsxsdb(startTime, endTime);
    }

    /**
     *
     * @description 进驻事项数据版本——其他进驻事项数（省级、地市、区县）
     * @author m13624
     * @param type 省级、地市、区县  choseDate 查询日期,如'2016-01-01'
     *
     * @return
     *            对应类型指定日期的进驻事项数
     * @time 2017年01月07日 下午13:46:59
     */
    @RequestMapping(value = "qtjzsxs/{type}/{choseDate}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> qtjzsxs( @PathVariable String type, @PathVariable String choseDate) {
        return service.qtjzsxs(type, choseDate);
    }

    /**
     *
     * @description 进驻事项数据版本——其他进驻事项数对比（省级、地市、区县）
     * @author m13624
     * @param type 省级、地市、区县  startTime 版本1日期   endTime 版本2日期
     *
     * @return
     *            对应类型  指定版本日期1和版本2日期 的事项数差异列表
     * @time 2017年01月07日 下午13:46:59
     */
    @RequestMapping(value = "qtjzsxsdb/{type}/{startTime}/{endTime}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> qtjzsxsdb( @PathVariable String type, @PathVariable String startTime, @PathVariable String endTime) {
        return service.qtjzsxsdb(type, startTime, endTime);
    }

    /**
     *
     * @description 进驻部门数据版本——进驻部门数
     * @author m13624
     * @param type 全省、地市、区县(省级没有进驻部门) , choseDate 查询日期,如'2016-01-01'
     * @return
     *            对应类型指定日期的进驻部门数
     * @time 2017年01月11日 下午15:09:47
     */
    @RequestMapping(value = "jzbms/{type}/{choseDate}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> jzbms( @PathVariable String type, @PathVariable String choseDate) {
        return service.jzbms(type, choseDate);
    }

    /**
     *
     * @description 进驻部门数据版本——进驻部门数对比
     * @author m13624
     * @param type 全省、地市、区县(省级没有进驻部门) , startTime 版本1日期   endTime 版本2日期
     * @return
     *            对应类型 版本日期1和版本2日期 的进驻部门数差异列表
     * @time 2017年01月11日 下午15:09:47
     */
    @RequestMapping(value = "jzbmsdb/{type}/{startTime}/{endTime}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> jzbmsdb( @PathVariable String type, @PathVariable String startTime, @PathVariable String endTime) {
        return service.jzbmsdb(type, startTime, endTime);
    }

    /**
     *
     * @description 数据质量监测——数据过程完整性
     * @author m13624
     * @param startTime
     *            查询起始年月日
     * @param endTime
     *            查询截止年月日
     * @return 查询日期范围内网上办事过程数据存在缺失的列表数据。网上办事正常包括申办、受理、办结等过程
     * @time 2017年03月16日 16:48:29
     */
    @RequestMapping(value = "dataProcess", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> dataProcess(@RequestParam(value = "startTime", required = false) String startTime,
                                                      @RequestParam(value = "endTime", required = false) String endTime) {
        return service.dataProcess(startTime, endTime);
    }

    /**
     *
     * @description 数据质量监测——业务逻辑异常
     * @author m13624
     * @param startTime
     *            查询起始年月日
     * @param endTime
     *            查询截止年月日
     * @return 查询日期范围内存在逻辑混乱、不合理的列表。正常情况下，业务申办后才能受理，然后是办结
     * @time 2017年03月17日 09:33:01
     */
    @RequestMapping(value = "buslogic", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> buslogic(@RequestParam(value = "startTime", required = false) String startTime,
                                                     @RequestParam(value = "endTime", required = false) String endTime) {
        return service.buslogic(startTime, endTime);
    }

    /**
     *
     * @description 数据质量监测——业务总量对账
     * @author m13624
     * @param startTime
     *            对准日期，一般为某年的01-01
     * @param dateTime
     *            查询截止年月日
     * @return 查询某年01-01，到截止月日的业务量
     * @time 2017年03月23日 11:13:31
     */
    @RequestMapping(value = "ywlaccount", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> ywlaccount(@RequestParam(value = "startTime", required = false) String startTime,
                                                    @RequestParam(value = "dateTime", required = false) String dateTime) {
        return service.ywlaccount(startTime, dateTime);
    }
}
