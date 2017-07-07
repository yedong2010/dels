package com.dels.service.monitor;

import com.dels.model.monitor.DataMonitorModel;

import java.util.List;
import java.util.Map;

/**
 * Created by l13595 on 2017/3/14.
 */
public interface IDataMonitorService {

    public abstract Map<String,Object> findBZWInfo(DataMonitorModel entity);
    public abstract List<Map<String,Object>> findBZWLsit(DataMonitorModel entity);

    /**
     * @description: 获取查询同比、环比所需的时间参数
     * @author: l13595
     * @return : 返回上月时间、上上月时间、去年的上月时间点。时间格式“yyyy-MM”
     * @time: 2017/3/15 14:26
     */
    public abstract Map<String,String> findTimeParam(String time);


    public Map<String,Object> findSumByTime(String param);

    /**
     * @description: 获取不作为分析等数据的同比环比的数据
     * @author: l13595
     * @param param: 查询参数-lastMonth上个月时间，lastTwoMonth-上上个月时间， lastYear-去年的上个月时间
     * @return: 返回查询结果列表
     * @time: 2017/3/16 14:06
     */
    public List<Map<String,Object>> findHBTB(Map<String,String> param);

    /**
     * @description: 获取数据监测分析的结果列表
     * @author: l13595
     * @param param: 查询参数 paramTime-获取数据的时间
     * @return:  返回查询结果列表
     * @time: 2017/3/16 14:05
     */
    public List<Map<String,Object>> findSJJC(Map<String,Object> param);

}
