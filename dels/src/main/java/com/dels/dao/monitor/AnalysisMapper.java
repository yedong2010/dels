package com.dels.dao.monitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * @author m13624
 * @description 数据分析Mapper持久层
 * @time 2017年03月06日 14:22:01
 */
public interface AnalysisMapper {
    /**
     * @param choseYear 选择年份
     * @return 按业务量从小到大排序的事项数办理时长信息
     * @description 给的数据当前默认是省级部门的
     * @author m13624
     * @time 2017年03月06日 14:23:59
     */
    List<HashMap<String, Object>> szanalysistime(@Param("choseYear") Integer choseYear);

    /**
     * @param choseYear 选择年份
     * @return 按业务量从小到大排序的事项数审批环节信息
     * @description 给的数据当前默认是省级部门的
     * @author m13624
     * @time 2017年03月08日 09:43:23
     */
    List<HashMap<String, Object>> szanalysisflow(@Param("choseYear") Integer choseYear);

    /**
     * @param choseYear 选择年份
     * @description 查询年份的省级行政审批事项数、实际全流程事项数、申报全流程事项数、无业务全流程事项数
     * @author m13624
     * @time 2017年03月13日 15:03:23
     */
    List<HashMap<String, Object>> szanalysisprocess(@Param("choseYear") Integer choseYear);

    /**
     * @description 投诉举报分析
     * @author m13624
     * @param startTime
     *            查询起始年月
     * @param endTime
     *            查询截止年月
     * @return 查询日期范围内的业务量、业务办理能力、被认可程度、投诉举报和满意度评价
     * @time 2017年03月14日 21:08:52
     */
    List<HashMap<String, Object>> szanalysisreport(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * @description 业务能力分析
     * @author m13624
     * @param startTime
     *            查询起始年月
     * @param endTime
     *            查询截止年月
     * @return 查询日期范围内的业务受理、办结、事项无业务情况、服务积存量
     * @time 2017年03月15日 10:09:04
     */
    List<HashMap<String, Object>> szanalysisbusiness(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * @description 业务办理时长分析
     * @author m13624
     * @param startTime
     *            查询起始年月
     * @param endTime
     *            查询截止年月
     * @return 查询日期范围内同一事项在不同市、区、街道三级业务办理时长，分析各市、区、街道三级业务流程单一环节平均时长、最长时长以及最短时长
     * @time 2017年03月15日 17:14:27
     */
    List<HashMap<String, Object>> dsanalysisBusTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * @description: 部门被认可程度信息查询
     * @author: l13595
     * @param:  zzjgdm-组织机构代码， startTime-查询起始时间， endTime-查询截止时间
     * @return: 返回 组织结构名称、投诉举报数、满意度百分比、业务量、认可程度百分比、统计时间
     * @time: 2017/3/24 16:29
     */
    List<Map<String, Object>> bmbrkcdForRestful(@Param("zzjgdm")String zzjgdm, @Param("startTime")String startTime, @Param("endTime")String endTime);
}
