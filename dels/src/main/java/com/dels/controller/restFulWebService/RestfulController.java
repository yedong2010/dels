package com.dels.controller.restFulWebService;

import com.dels.service.monitor.IAnalysisService;
import com.dels.service.monitor.IDataIndexService;
import com.dels.service.monitor.IRateService;
import com.dels.utils.Utils;
import com.dels.service.monitor.IBusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by l13595 on 2017/3/22.
 * 对外提供接口开发
 *
 */
@RestController
    @RequestMapping("/api/v1")
public class RestfulController {

    @Autowired
    IRateService rateService;

    @Autowired
    IBusService busService;

    @Autowired
    IAnalysisService analysisService;

    @Autowired
    IDataIndexService dataIndexService;

    /**
     * @description: 部门业务量
     * @author: l13595
     * @param: [zzjgdm-部门名称, year-年, smonth-查询开始月份, emonth-查询结束月份]
     * @return: 返回部门、时间段、每月的业务量平均值、每个时间点业务量等
     * @time: 2017/3/22 11:35
     */
    @RequestMapping(value = "bmywl/{zzjgdm}/{year}/{smonth}/{emonth}", produces = {"application/json;charset=UTF-8"})
    public Map<String, Object> bmywl(@PathVariable String zzjgdm,
                                     @PathVariable Integer year, @PathVariable Integer smonth, @PathVariable Integer emonth){
        Map<String, Object> rep = new HashMap<>();
        List<Map<String, Object>> list = rateService.bmywzl(zzjgdm, year, smonth, emonth);
        int sumYWZL = rateService.sumBMYWZL(zzjgdm, year, smonth, emonth);
        if(null != list && list.size() > 0){
            rep.put("avgTraffic", sumYWZL/(list.size()));      //平均业务量
        }else{
            rep.put("avgTraffic", 0);      //平均业务量
        }
        rep.put("preTraffic",list);     //每个时间点的业务量、组织机构代码、组织机构名称、时间
        return rep;
    }

    /**
     * @description:部门业务能力查询
     * @author: l13595
     * @param : [zzjgdm-部门名称, year-年, smonth-查询开始月份, emonth-查询结束月份]
     * @return: 组织机构名称-zzjgmc、组织机构代码-zzjgdm、网上全流程办理率-wsqlcbll、上网办理率-swbll、网上办结率-wsbjl
     * @time: 2017/3/22 10:25
     */
    @RequestMapping(value = "bmywnl/{zzjgdm}/{year}/{smonth}/{emonth}", produces = {"application/json;charset=UTF-8"})
    public Map<String, Object> bmywnl(@PathVariable String zzjgdm,
                                      @PathVariable Integer year, @PathVariable Integer smonth, @PathVariable Integer emonth){
        Map<String, Object> rep = new HashMap<>();
        List<Map<String, Object>> list = rateService.bmywnlRestful(zzjgdm, year, smonth, emonth);
        rep.put("bmywnl", list);
        return rep;
    }

    /**
     * @description: 部门事项变更信息，输出 部门、时间段、“僵尸”事项、业务数
     * @author: l13595
     * @param :[zzjgdm-部门名称, year-年, smonth-查询开始月份, emonth-查询结束月份]
     * @return: 部门、时间、部门无业务事项数、部门事项总数
     * @time: 2017/3/22 10:29
     */
    @RequestMapping(value = "bmsxbg/{zzjgdm}/{year}/{smonth}/{emonth}", produces = {"application/json;charset=UTF-8"})
    public Map<String, Object> bmsxbg(@PathVariable String zzjgdm,
                                      @PathVariable Integer year, @PathVariable Integer smonth, @PathVariable Integer emonth){
        Map<String, Object> rep = new HashMap<>();
        List<Map<String, Object>> list = busService.wywsxRestful(zzjgdm, year, smonth, emonth);
        rep.put("bmsxbg", list);
        return rep;
    }

    /**
     * @description: 部门被认可程度
     * @author: l13595
     * @param :[zzjgdm-部门名称, year-年, smonth-查询开始月份, emonth-查询结束月份]
     * @return: 返回组织机构名称、投诉举报数、满意度百分比、业务量、认可程度百分比、统计时间（yyyy-mm）
     * @time: 2017/3/22 10:32
     */
    @RequestMapping(value = "bmbrkcd/{zzjgdm}/{year}/{smonth}/{emonth}", produces = {"application/json;charset=UTF-8"})
    public Map<String, Object> bmbrkcd(@PathVariable String zzjgdm,
                                       @PathVariable Integer year, @PathVariable Integer smonth, @PathVariable Integer emonth){
        Map<String, Object> rep = new HashMap<>();
        String startTime = Utils.constractTimeByInt(year, smonth);
        String endTime = Utils.constractTimeByInt(year, emonth);
        if(smonth > 12 || emonth > 12){
            rep.put("resultMsg","日期格式不正确");
        }else{
            rep.put("bmbrkcd", analysisService.bmbrkcdRestful(zzjgdm, startTime, endTime));
            rep.put("resultMsg", "ok");
        }
        return rep;
    }

    /**
     * @description: 部门行为信息服务
     * @author: l13595
     * @param :[zzjgdm-部门名称, year-年, smonth-查询开始月份, emonth-查询结束月份]
     * @return: 部门、时间段、不作为比例、慢作为比例、工作不严谨行为比例
     * @time: 2017/3/22 10:39
     */
    @RequestMapping(value = "bmxw/{zzjgdm}/{year}/{smonth}/{emonth}", produces = {"application/json;charset=UTF-8"})
    public Map<String, Object> bmxw(@PathVariable String zzjgdm,
                                    @PathVariable Integer year, @PathVariable Integer smonth, @PathVariable Integer emonth){
        Map<String, Object> rep = new HashMap<>();
        List<Map<String, Object>> list = dataIndexService.bzwRestful(zzjgdm, year, smonth, emonth);
        rep.put("bmxw", list);
        return rep;
    }






}
