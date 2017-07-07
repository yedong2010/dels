package com.dels.dao;

import com.dels.model.sys.OperateLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 审计日志
 * liuchengjie 13595
 */
public interface OperateLogMapper {

    /**
     * 日志表插入记录
     * @param log：日志
     */
    public abstract void insertLog(OperateLog log);

    /**
     * 获取日志表记录
     * @return：返回查询到得结果集
     */
    public abstract List<Map<String,Object>> getLogList(Map<String,Object> param);

    /**
     * @description:获取数据总条数
     * @author: l13595
     * @return:返回记录数
     * @time: 2017/1/17 15:07
     */
    public abstract int countLogList(Map<String,Object> param);

    /**
     * 查询总操作次数
     * @param startTime：查询条件-起始时间
     * @param endTime：查询条件-结束时间
     * @return 返回总的操作次数
     */
    public abstract Integer countSum(@Param("startTime") String startTime, @Param("endTime")String endTime);

    /**
     * 登陆操作次数
     * @param startTime：查询条件-起始时间
     * @param endTime：查询条件-结束时间
     * @return 返回登陆操作的次数
     */
    public abstract Integer countLogin(@Param("startTime") String startTime, @Param("endTime")String endTime);

    /**
     * 访问的用户数
     * @param startTime：查询条件-起始时间
     * @param endTime：查询条件-结束时间
     * @return 返回时间段内访问系统的用户数
     */
    public abstract int countUser(@Param("startTime") String startTime, @Param("endTime")String endTime);

    /**
     * 每个用户的操作数
     * @param startTime：查询条件-起始时间
     * @param endTime：查询条件-结束时间
     * @return 返回时间段内的每个用户的操作次数
     */
    public abstract List<Map<String, Object>> countPerUser(@Param("startTime") String startTime, @Param("endTime")String endTime);

    /**
     * 访问的角色数
     * @param startTime：查询条件-起始时间
     * @param endTime：查询条件-结束时间
     * @return 返回时间段内的访问角色个数
     */
    public abstract int countRole(@Param("startTime") String startTime, @Param("endTime")String endTime);

    /**
     * 每个角色的访问次数
     * @param startTime：查询条件-起始时间
     * @param endTime：查询条件-结束时间
     * @return 返回时间段内的每个角色访问系统的次数
     */
    public abstract List<Map<String, Object>> countPerRole(@Param("startTime") String startTime, @Param("endTime")String endTime);

}