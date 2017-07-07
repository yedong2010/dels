package com.dels.controller.analysis;

import java.util.HashMap;
import java.util.List;

import com.dels.service.ISysMngService;
import com.dels.service.monitor.IAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @description 数据分析
 * @author m13624
 * @time:2017年03月06日 上午10:10:39
 */
@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    /**
     * 业务层操作对象
     */
    @Autowired
    IAnalysisService service;

    @Autowired
    ISysMngService sysMngService;

    /**
     *
     * @description 业务办理时长分析
     * @author m13624
     * @param year
     *            查询年份
     * @return 查询年份的省级事项数，以及平均办理时间和承诺时间等信息
     * @time 2017年03月06日 15:26:05
     */
    @RequestMapping(value = "analysistime/{choseYear}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> analysistime(@PathVariable Integer choseYear) {
        return service.analysistime(choseYear);
    }

    /**
     *
     * @description 业务审批环节分析
     * @author m13624
     * @param year
     *            查询年份
     * @return 查询年份的省级事项数，以及审批平均次数时间和例外平均次数等信息
     * @time 2017年03月08日 09:37:38
     */
    @RequestMapping(value = "analysisflow/{choseYear}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> analysisflow(@PathVariable Integer choseYear) {
        return service.analysisflow(choseYear);
    }

    /**
     *
     * @description 全流程事项分析
     * @author m13624
     * @param year
     *            查询年份
     * @return 查询年份的省级行政审批事项数、实际全流程事项数、申报全流程事项数、无业务全流程事项数
     * @time 2017年03月13日 15:00:38
     */
    @RequestMapping(value = "analysisprocess/{choseYear}", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> analysisprocess(@PathVariable Integer choseYear) {
        return service.analysisprocess(choseYear);
    }

    /**
     *
     * @description 投诉举报分析
     * @author m13624
     * @param startTime
     *            查询起始年月
     * @param endTime
     *            查询截止年月
     * @return 查询日期范围内的业务量、业务办理能力、被认可程度、投诉举报和满意度评价
     * @time 2017年03月14日 21:08:52
     */
    @RequestMapping(value = "analysisreport", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> analysisreport(@RequestParam(value = "startTime", required = false) String startTime,
                                                        @RequestParam(value = "endTime", required = false) String endTime) {
        return service.analysisreport(startTime, endTime);
    }

    /**
     *
     * @description 业务能力分析
     * @author m13624
     * @param startTime
     *            查询起始年月
     * @param endTime
     *            查询截止年月
     * @return 查询日期范围内的业务受理、办结、事项无业务情况、服务积存量
     * @time 2017年03月15日 10:09:04
     */
    @RequestMapping(value = "analysisbusiness", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> analysisbusiness(@RequestParam(value = "startTime", required = false) String startTime,
                                                        @RequestParam(value = "endTime", required = false) String endTime) {
        return service.analysisbusiness(startTime, endTime);
    }

    /**
     *
     * @description 业务办理时长分析
     * @author m13624
     * @param startTime
     *            查询起始年月
     * @param endTime
     *            查询截止年月
     * @return 查询日期范围内同一事项在不同市、区、街道三级业务办理时长，分析各市、区、街道三级业务流程单一环节平均时长、最长时长以及最短时长
     * @time 2017年03月15日 17:14:27
     */
    @RequestMapping(value = "analysisBusTime", produces = { "application/json;charset=UTF-8" })
    public List<HashMap<String, Object>> analysisBusTime(@RequestParam(value = "startTime", required = false) String startTime,
                                                          @RequestParam(value = "endTime", required = false) String endTime) {
        return service.analysisBusTime(startTime, endTime);
    }
}
