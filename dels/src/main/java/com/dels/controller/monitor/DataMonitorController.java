package com.dels.controller.monitor;

import com.dels.service.monitor.IDataMonitorService;
import com.dels.model.monitor.DataMonitorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by l13595 on 2017/3/14.
 */
@RestController
@RequestMapping("/dataMonitor")
public class DataMonitorController {

    @Autowired
    IDataMonitorService service;

    /**
     * @description: 不作为慢作为分析
     * @author: l13595
     * @param entity:
     * @return:
     * @time: 2017/3/15 10:25
     */
    @RequestMapping(value = "bzw")
    public List<Map<String,Object>> findBZWInfo(DataMonitorModel entity){
       /* if(null != entity.getEndTime()){
            entity.setEndTime(Utils.dateOperationTwo(entity.getEndTime(), 1));
        }*/
        List<Map<String,Object>> list = service.findBZWLsit(entity);
        return list;
    }

    /**
     * @description: 不作为慢作为数据同比环比分析
     * @author: l13595
     * @param entity: 查询参数 lastMonth-时间
     * @return:  返回查询结果列表
     * @time: 2017/3/16 14:22
     */
    @RequestMapping(value = "TBHB")
    public List<Map<String,Object>> findHBTB(DataMonitorModel entity){

        if(null == entity.getLastMonth()){
            DateFormat df = new SimpleDateFormat("yyyy-MM");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, -1);
            entity.setLastMonth(df.format(c.getTime()));
        }
        Map<String,String> param = service.findTimeParam(entity.getLastMonth());
        List<Map<String,Object>> list = service.findHBTB(param);

        return list;
    }

    /**
     * @description: 数据监测分析
     * @author: l13595
     * @param startTime: 查询时间 -开始时间
     * @param endTime: 查询时间-结束时间
     * @return:
     * @time: 2017/3/16 14:24
     */
    @RequestMapping(value = "SJJC")
    public List<Map<String, Object>> findSJJC(String startTime, String endTime){
        DateFormat df = new SimpleDateFormat("yyyy-MM");

        try{
            if (null != startTime) {
                Date d = df.parse(startTime);
                startTime = df.format(d);
            }
            if (null != endTime) {
                Date d = df.parse(endTime);
                endTime = df.format(d);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        Map<String,Object> param = new HashMap<>();
        param.put("startTime",startTime);
        param.put("endTime",endTime);
        return service.findSJJC(param);
    }



}
