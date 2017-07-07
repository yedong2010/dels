package com.dels.dao.monitor;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * @author z11595
 * @description 总体统计Mapper持久层
 * @time 2016年12月28日 下午7:35:01
 */
public interface TotalityMapper {

    /**
     * @param t        类型(全省:all,省级:province,地市:city,区县:county)
     * @param cparams  地市名称数组
     * @param category 行政区划类别(珠三角:0,粤东西北:1)
     * @return 跑动类别和跑动事项记录数
     * @description 查询全省跑动次数
     * @author z11595
     * @time 2016年12月22日 下午5:24:59
     */
    List<HashMap<String, Object>> qsys(@Param("t") String t, @Param("cparams") List<String> cparams, @Param("category") String category, @Param("area") String area);

    /**
     * @param year   查询年份
     * @param smonth 开始月份
     * @param emonth 结束月份
     * @return 全省进驻事项、进驻部门、行政审批、公共服务记录数
     * @description 查询全省进驻事项、分类、进驻部门
     */
    HashMap<String, Object> itemsurvey(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("area") String area);

    /**
     * @param year   查询年份
     * @param smonth 开始月份
     * @param emonth 结束月份
     * @return 全省进驻事项、进驻部门、行政审批、公共服务记录数
     * @description 查询全省级进驻事项、分类、进驻部门
     */
    HashMap<String, Object> qsz(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * @param year    查询年份
     * @param smonth  开始月份
     * @param emonth  结束月份
     * @param cparams 地市名称数组
     * @return 按照地市范围查询的进驻事项、进驻部门、行政审批、公共服务记录数
     * @description 查询地市进驻事项、分类、进驻部门
     */
    HashMap<String, Object> qds(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * @param year     查询年份
     * @param smonth   开始月份
     * @param emonth   结束月份
     * @param cparams  地市名称数组
     * @param category 行政区域类别(珠三角:0,粤东西北:1)
     * @return 按照行政区划类别查询的进驻事项、进驻部门、行政审批、公共服务记录数
     * @description 查询区县进驻事项、分类、进驻部门
     */
    HashMap<String, Object> qqx(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams,
                                @Param("category") String category, @Param("area") String area);

    /**
     * @param year   查询年份
     * @param smonth 开始月份
     * @param emonth 结束月份
     * @return 网上全流程办理率，上网办结率，网上办理率
     * @description 全省三率
     * @author z11595
     * @time 2016年12月22日 下午5:30:08
     */
    HashMap<String, Object> qssl(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("area") String area);

    /**
     * @param year   查询年份
     * @param smonth 开始月份
     * @param emonth 结束月份
     * @return 网上全流程办理率，上网办结率，网上办理率
     * @description 省级三率
     * @author z11595
     * @time 2016年12月22日 下午5:30:49
     */
    HashMap<String, Object> qszsl(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * @param year    查询年份
     * @param smonth  开始月份
     * @param emonth  结束月份
     * @param cparams 地市名称数组
     * @return 网上全流程办理率，上网办结率，网上办理率
     * @description 地市三率
     * @author z11595
     * @time 2016年12月22日 下午5:31:32
     */
    HashMap<String, Object> qdssl(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * @param year     查询年份
     * @param smonth   开始月份
     * @param emonth   结束月份
     * @param cparams  地市名称数组
     * @param category 区划类别(珠三角:0,粤东西北:1)
     * @return 网上全流程办理率，上网办结率，网上办理率
     * @description 区县三率
     * @author z11595
     * @time 2016年12月22日 下午5:32:09
     */
    HashMap<String, Object> qqxsl(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams,
                                  @Param("category") String category, @Param("area") String area);

    /**
     * @param year   查询年
     * @param smonth 开始月
     * @param emonth 结束月
     * @param type   类型（上网办理和非上网办理）
     * @return 查询全省业务办理信息（业务按月办理量）数组
     * @description 查询全省业务办理信息（业务按月办理量）
     * @author z11595
     * @time 2016年12月22日 上午11:43:23
     */
    List<Integer> qsywblByyf(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("type") Integer type, @Param("area") String area);

    /**
     * @param year    查询年
     * @param smonth  开始月
     * @param emonth  结束月
     * @param type    类型（上网办理和非上网办理）
     * @param cparams 省级部门名称数组
     * @return 省级业务办理信息（业务按月办理量）
     * @description 查询省级业务办理信息（业务按月办理量）
     * @author z11595
     * @time 2016年12月22日 上午11:43:23
     */
    List<Integer> szywblByyf(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("type") Integer type);

    /**
     * @param year    查询年
     * @param smonth  开始月
     * @param emonth  结束月
     * @param cparams 查询地市范围
     * @param type    类型（上网办理和非上网办理）
     * @return 地市业务办理信息（业务按月办理量）
     * @description 查询地市业务办理信息（业务按月办理量）
     * @author z11595
     * @time 2016年12月22日 上午11:43:23
     */
    List<Integer> dsywblByyf(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("type") Integer type, @Param("cparams") List<String> cparams);

    /**
     * @param year     查询年
     * @param smonth   开始月
     * @param emonth   结束月
     * @param category 查询区划类型
     * @param type     类型（上网办理和非上网办理）
     * @param cparams  查询地市范围
     * @return 地市业务办理信息（业务按月办理量）
     * @description 查询区县业务办理信息（业务按月办理量）
     * @author z11595
     * @time 2016年12月22日 上午11:43:23
     */
    List<Integer> qxywblByyf(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("type") Integer type, @Param("category") String category,
                             @Param("area") String area, @Param("cparams") List<String> cparams);

    /**
     * @param year   查询年
     * @param smonth 开始月
     * @param emonth 结束月
     * @param area 是否包含非行政区划
     * @return 查询全省业务量排名数组
     * @description 查询全省业务量排名数组（业务按月办理量）
     * @author m13624
     * @time 2017年03月08日 下午3:30:17
     */
    List<HashMap<String, Object>> qsywlph(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("area") String area);

    /**
     * @param year    查询年
     * @param smonth  开始月
     * @param emonth  结束月
     * @return 查询省级业务量排名数组
     * @description 查询省级业务量排名数组（业务按月办理量）
     * @author m13624
     * @time 2017年03月08日 下午3:30:17
     */
    List<HashMap<String, Object>> szywlph(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth);

    /**
     * @param year    查询年
     * @param smonth  开始月
     * @param emonth  结束月
     * @param cparams 查询地市范围
     * @return 查询地市业务量排名数组
     * @description 查询地市业务量排名数组（业务按月办理量）
     * @author m13624
     * @time 2017年03月08日 下午3:30:17
     */
    List<HashMap<String, Object>> dsywlph(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * @param year     查询年
     * @param smonth   开始月
     * @param emonth   结束月
     * @param area 是否包含非行政区划
     * @param category 查询区划类型
     * @param cparams  查询地市范围
     * @return 查询区县业务量排名数组
     * @description 查询区县业务量排名数组（业务按月办理量）
     * @author m13624
     * @time 2017年03月08日 下午3:35:36
     */
    List<HashMap<String, Object>> qxywlph(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("area") String area, @Param("category") String category, @Param("cparams") List<String> cparams);

    /**
     * @description 计算过程追溯
     * @author m13624
     * @param dateTime
     *            查询年月日
     * @return 查询日期，指标计算过程的每一个计算步骤，和计算结果。
     * @time 2017年03月16日 11:16:21
     */
    List<HashMap<String, Object>> szcountChase(@Param("dateTime") String dateTime);

    /**
     * @param startTime 开始日期
     * @param endTime 结束日期
     * @param area 是否包含非行政区划
     * @return 查询全省业务量排名数组
     * @description 查询全省业务量排名数组（业务按月办理量）
     * @author m13624
     * @time 2017年03月28日 19:35:17
     */
    List<HashMap<String, Object>> qsbjywlph(@Param("startTime") String  startTime, @Param("endTime") String endTime, @Param("area") String area);

    /**
     * @param startTime 开始日期
     * @param endTime 结束日期
     * @return 查询省级业务量排名数组
     * @description 查询省级业务量排名数组（业务按月办理量）
     * @author m13624
     * @time 2017年03月28日 19:35:17
     */
    List<HashMap<String, Object>> szbjywlph(@Param("startTime") String  startTime, @Param("endTime") String endTime);

    /**
     * @param startTime 开始日期
     * @param endTime 结束日期
     * @param cparams 查询地市范围
     * @return 查询地市业务量排名数组
     * @description 查询地市业务量排名数组（业务按月办理量）
     * @author m13624
     * @time 2017年03月28日 19:35:17
     */
    List<HashMap<String, Object>> dsbjywlph(@Param("startTime") String  startTime, @Param("endTime") String endTime, @Param("cparams") List<String> cparams);

    /**
     * @param startTime 开始日期
     * @param endTime 结束日期
     * @param area 是否包含非行政区划
     * @param category 查询区划类型
     * @param cparams  查询地市范围
     * @return 查询区县业务量排名数组
     * @description 查询区县业务量排名数组（业务按月办理量）
     * @author m13624
     * @time 2017年03月28日 19:35:17
     */
    List<HashMap<String, Object>> qxbjywlph(@Param("startTime") String  startTime, @Param("endTime") String endTime, @Param("area") String area, @Param("category") String category, @Param("cparams") List<String> cparams);

    /**
     * @description 指定省级，查看事项业务量排名
     * @author m13624
     * @param startTime
     *            起始日期
     * @param endTime
     *            截止日期
     * @param id
     *            如果是type为province，即为组织机构zzjgdm；如果type为city，即为市级名称sjmc
     * @return 查询日期，指标计算过程的每一个计算步骤，和计算结果。
     * @time 2017年03月28日 20:07:02
     */
    List<HashMap<String, Object>> szbjywlphDetail(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("id") String id);

    /**
     * @description 指定地市，根据事项维度，查看业务量排名
     * @author m13624
     * @param startTime
     *            起始日期
     * @param endTime
     *            截止日期
     * @param id
     *            如果是type为province，即为组织机构zzjgdm；如果type为city，即为市级名称sjmc
     * @return 查询日期，指标计算过程的每一个计算步骤，和计算结果。
     * @time 2017年03月21日 14:25:02
     */
    List<HashMap<String, Object>> dsbjywlphDetail(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("id") String id);

    /**
     * @description 指定区县，根据事项维度，查看业务量排名
     * @author m13624
     * @param startTime
     *            起始日期
     * @param endTime
     *            截止日期
     * @param id
     *            如果是type为province，即为组织机构zzjgdm；如果type为city，即为市级名称sjmc
     * @return 查询日期，指标计算过程的每一个计算步骤，和计算结果。
     * @time 2017年03月25日 14:25:02
     */
    List<HashMap<String, Object>> qxbjywlphDetail(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("id") String id, @Param("sjmc") String sjmc);

    /**
     * @param startTime 开始日期
     * @param endTime 结束日期
     * @param area 是否包含非行政区划
     * @return 查询全省申办业务量排名数组
     * @description 查询全省申办业务量排名数组（业务按月办理量）
     * @author m13624
     * @time 2017年03月27日 09:45:59
     */
    List<HashMap<String, Object>> qssbywlph(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("area") String area);

    /**
     * @param startTime 开始日期
     * @param endTime 结束日期
     * @param area 是否包含非行政区划
     * @return 查询省级申办业务量排名数组
     * @description 查询省级申办业务量排名数组（业务按月办理量）
     * @author m13624
     * @time 2017年03月27日 09:45:59
     */
    List<HashMap<String, Object>> szsbywlph(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * @param startTime 开始日期
     * @param endTime 结束日期
     * @param area 是否包含非行政区划
     * @return 查询地市申办业务量排名数组
     * @description 查询地市申办业务量排名数组（业务按月办理量）
     * @author m13624
     * @time 2017年03月27日 09:45:59
     */
    List<HashMap<String, Object>> dssbywlph(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("cparams") List<String> cparams);

    /**
     * @param startTime 开始日期
     * @param endTime 结束日期
     * @param area 是否包含非行政区划
     * @return 查询区县申办业务量排名数组
     * @description 查询区县申办业务量排名数组（业务按月办理量）
     * @author m13624
     * @time 2017年03月27日 09:45:59
     */
    List<HashMap<String, Object>> qxsbywlph(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("area") String area, @Param("category") String category, @Param("cparams") List<String> cparams);

    /**
     * @description 指定省级，根据事项维度，查看申办业务量排名
     * @author m13624
     * @param startTime
     *            起始日期
     * @param endTime
     *            截止日期
     * @param id
     *            如果是type为province，即为组织机构zzjgdm；如果type为city，即为市级名称sjmc
     * @return 查询日期，指标计算过程的每一个计算步骤，和计算结果。
     * @time 2017年03月27日 14:25:02
     */
    List<HashMap<String, Object>> szsbywlphDetail(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("id") String id);

    /**
     * @description 指定地市，根据事项维度，查看申办业务量排名
     * @author m13624
     * @param startTime
     *            起始日期
     * @param endTime
     *            截止日期
     * @param id
     *            如果是type为province，即为组织机构zzjgdm；如果type为city，即为市级名称sjmc
     * @return 查询日期，指标计算过程的每一个计算步骤，和计算结果。
     * @time 2017年03月27日 10:45:18
     */
    List<HashMap<String, Object>> dssbywlphDetail(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("id") String id);

    /**
     * @description 指定区县，根据事项维度，查看申办业务量排名
     * @author m13624
     * @param startTime
     *            起始日期
     * @param endTime
     *            截止日期
     * @param id
     *            如果是type为province，即为组织机构zzjgdm；如果type为city，即为市级名称sjmc
     * @return 查询日期，指标计算过程的每一个计算步骤，和计算结果。
     * @time 2017年03月27日 10:45:18
     */
    List<HashMap<String, Object>> qxsbywlphDetail(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("id") String id, @Param("sjmc") String sjmc);

}