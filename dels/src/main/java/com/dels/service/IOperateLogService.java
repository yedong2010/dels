package com.dels.service;

import com.dels.model.sys.OperateLog;

import java.util.List;
import java.util.Map;

/**
 * 审计日志
 * @author: liuchengjie 13595
 * @date:2017-01-04
 */
public interface IOperateLogService {


    /**
     * 插入操作日志
     * @param operation：操作路径
     */
    public abstract void insertLog(String operation);

    /**
     * @description:增加操作日志记录
     * @author: l13595
     * @param log: 日志信息
     * @return:
     * @time: 2017/1/12 18:06
     */
    public abstract void saveLog(OperateLog log);

    /**
     * 获取操作日志数据
     */
    public abstract List<Map<String,Object>> getLogList(Map<String,Object> param);

    /**
     * @description:获取记录总数
     * @author: l13595
     * @param:
     * @return:
     * @time: 2017/1/17 15:08
     */
    public abstract int countLogList(Map<String,Object> param);


     /**
      * @description: 总操作次数
      * @author: l13595
      * @param startTime: 起始日期查询条件
      * @param endTime: 结束时间查询条件
      * @return: 返回查询时间段内的总操作次数
      * @time: 2017/5/23 17:19
      */
    public abstract Integer countSum(String startTime, String endTime);

     /**
      * @description:登录操作次数
      * @author: l13595
      * @param startTime: 起始日期查询条件
      * @param endTime: 结束时间查询条件
      * @return:返回登录操作次数
      * @time: 2017/5/23 17:21
      */
    public abstract Integer countLogin(String startTime, String endTime);

     /**
      * @description:访问的用户数
      * @author: l13595
      * @param startTime: 起始日期查询条件
      * @param endTime: 结束时间查询条件
      * @return:返回查询时间段内的访问用户数
      * @time: 2017/5/23 17:21
      */
    public abstract int countUser(String startTime, String endTime);

     /**
      * @description:每个用户的操作数
      * @author: l13595
      * @param startTime: 起始日期查询条件
      * @param endTime: 结束时间查询条件
      * @return: 返回每个用户的操作数
      * @time: 2017/5/23 17:22
      */
    public abstract List<Map<String, Object>> countPerUser(String startTime, String endTime);

     /**
      * @description:访问的角色数
      * @author: l13595
      * @param startTime: 起始日期查询条件
      * @param endTime: 结束时间查询条件
      * @return: 返回访问的角色个数
      * @time: 2017/5/23 17:23
      */
    public abstract int countRole(String startTime, String endTime);

     /**
      * @description:每个角色的访问次数
      * @author: l13595
      * @param startTime: 起始日期查询条件
      * @param endTime: 结束时间查询条件
      * @return: 返回每个角色的访问次数
      * @time: 2017/5/23 17:23
      */
    public abstract List<Map<String, Object>> countPerRole(String startTime, String endTime);


}
