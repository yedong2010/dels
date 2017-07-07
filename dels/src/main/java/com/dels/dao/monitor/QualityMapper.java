package com.dels.dao.monitor;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
/**
 * 
 * @description 质量检测模块接口层
 * @author z11595
 * @time 2016年12月22日 下午6:31:37
 */
public interface QualityMapper {
    /**
     * 
     * @description 全省质量概况查询
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @return zs、zq、cw、zql
     * @time 2016年12月15日 上午11:39:11
     */
    HashMap<String, Object> qszl(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth);

    /**
     * 
     * @description 省级质量概况查询
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @return zs、zq、cw、zql
     * @time 2016年12月15日 上午11:39:11
     */
    HashMap<String, Object> szzl(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth);

    /**
     * 
     * @description 地市质量概况查询
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams 查询地市范围
     * @return zs、zq、cw、zql
     * @time 2016年12月15日 上午11:39:11
     */
    HashMap<String, Object> dszl(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * 
     * @description 全省错误分类
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @return error、error_name、zs
     * @time 2016年12月15日 下午2:14:30
     */
    List<HashMap<String, Object>> qscwfl(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth);

    /**
     * 
     * @description 省级错误分类
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @return error、error_name、zs
     * @time 2016年12月15日 下午2:16:17
     */
    List<HashMap<String, Object>> szcwfl(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth);

    /**
     * 
     * @description 地市错误分类
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams 查询地市范围
     * @return error、error_name、zs
     * @time 2016年12月15日 下午2:17:17
     */
    List<HashMap<String, Object>> dscwfl(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth,@Param("cparams") List<String> cparams);

    /**
     * 
     * @description 通过名称查询详细的错误分类
     * @author z11595
     * @param name 省级部门/地市名称
     * @param year 查询年份
     * @param smonth 开始月份
     * @param emonth 结束月份
     * @return 通过名称查询详细的错误分类
     * @time 2016年12月17日 下午2:01:45
     */
    List<HashMap<String, Object>> cwflByName(@Param("name") String name, @Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth);

    /**
     *
     * @description 通过名称查询详细的错误分类
     * @author m13624
     * @param name 省级部门/地市名称
     * @param year 查询年份
     * @param smonth 开始月份
     * @param emonth 结束月份
     * @return 通过名称查询详细的错误分类
     * @time 2017年04月19日 上午11:14:02
     */
    List<String> cwtbByName(@Param("name") String name, @Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth);

    /**
     *
     * @description 通过名称查询详细的错误分类
     * @author m13624
     * @param hjName 环节名称
     * @param timeName 时间的字段名称
     * @param tableName 表名
     * @param name 省级部门/地市名称
     * @param year 查询年份
     * @param smonth 开始月份
     * @param emonth 结束月份
     * @return 通过名称查询详细的错误分类
     * @time 2017年04月19日 上午11:14:02
     */
    List<HashMap<String, Object>> cwfllbByTable(@Param("hjName") String hjName, @Param("timeName") String timeName, @Param("tableName") String tableName, @Param("name") String name, @Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth);

    /**
     * 
     * @description 地市数据质量
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            查询地市名称数组范围
     * @return 地市质量报告总计数据(按查询地市范围)
     * @time 2016年12月22日 下午5:57:38
     */
    List<HashMap<String, Object>> dssjzl(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     *
     * @description 省级数据质量
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @return 省级数据质量总计数据
     * @time 2016年12月8日 上午10:19:44
     */
    List<HashMap<String, Object>> szsjzl(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);
    /**
    *
    * @description 省级数据质量总计
    * @author z11700
    * @param year
    *            查询年份
    * @param smonth
    *            开始月份
    * @param emonth
    *            结束月份
    * @return 省级数据质量总计数据
    * @time 2016/12/21 17:15
    */
   List<HashMap<String, Object>> szsjzlzj(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

   /**
    *
    * @description 地市数据质量总计
    * @author z11700
    * @param year
    *            查询年份
    * @param smonth
    *            开始月份
    * @param emonth
    *            结束月份
    * @param cparams
    *            地市名称数组
    * @return 地市数据质量总计数据
    * @time 2016/12/21 17:15
    */
   List<HashMap<String, Object>> dssjzlzj(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     *
     * @description 进驻事项数据版本——全省进驻事项数
     * @author m13624
     * @param choseDate
     *            ('2016-01-01')
     * @return
     *            指定日期的全省进驻事项数
     * @time 2017年01月06日 上午10:10:02
     */
    List<HashMap<String, Object>> qsjzsxs(@Param("choseDate") String choseDate);

    /**
     *
     * @description 进驻事项数据版本——全省进驻事项数对比
     * @author m13624
     * @param startTime endTime
     *            ('2016-01-01')
     * @return
     *            版本1日期和版本2日期的进驻事项数差异列表
     * @time 2017年01月07日 上午10:21:38
     */
    List<HashMap<String, Object>> qsjzsxsdb(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     *
     * @description 进驻事项数据版本——省级进驻事项数
     * @author m13624
     * @param choseDate 查询日期,如'2016-01-01'
     *
     * @return
     *            指定日期的省级进驻事项数
     * @time 2017年01月07日 下午13:46:59
     */
    List<HashMap<String, Object>> szjzsxs(@Param("choseDate") String choseDate);

    /**
     *
     * @description 进驻事项数据版本——省级进驻事项数对比
     * @author m13624
     * @param startTime 版本1日期  endTime 版本2日期
     *                  如'2016-01-01'
     * @return
     *            版本1日期和版本2日期 的省级进驻事项数差异列表
     * @time 2017年01月07日 下午13:46:59
     */
    List<HashMap<String, Object>> szjzsxsdb(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     *
     * @description 进驻事项数据版本——地市进驻事项数
     * @author m13624
     * @param  choseDate 查询日期,如'2016-01-01'
     *
     * @return
     *            指定日期的地市进驻事项数
     * @time 2017年01月07日 下午13:46:59
     */
    List<HashMap<String, Object>> dsjzsxs( @Param("choseDate") String choseDate);

    /**
     *
     * @description 进驻事项数据版本——地市进驻事项数对比
     * @author m13624
     * @param startTime 版本1日期  endTime 版本2日期
     *                  如'2016-01-01'
     * @return
     *            版本1日期和版本2日期 的地市进驻事项数差异列表
     * @time 2017年01月07日 下午13:46:59
     */
    List<HashMap<String, Object>> dsjzsxsdb(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     *
     * @description 进驻事项数据版本——区县进驻事项数
     * @author m13624
     * @param choseDate 查询日期,如'2016-01-01'
     *
     * @return
     *            指定日期的区县进驻事项数
     * @time 2017年01月07日 下午13:46:59
     */
    List<HashMap<String, Object>> qxjzsxs( @Param("choseDate") String choseDate);

    /**
     *
     * @description 进驻事项数据版本——区县进驻事项数对比
     * @author m13624
     * @param startTime 版本1日期  endTime 版本2日期
     *                  如'2016-01-01'
     * @return
     *            版本1日期和版本2日期 的区县进驻事项数差异列表
     * @time 2017年01月07日 下午13:46:59
     */
    List<HashMap<String, Object>> qxjzsxsdb(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     *
     * @description 进驻部门数据版本——全省进驻部门数
     * @author m13624
     * @param choseDate
     *            ('2016-01-01')
     * @return
     *            指定日期的全省进驻部门数
     * @time 2017年01月11日 下午15:15:28
     */
    List<HashMap<String, Object>> qsjzbms(@Param("choseDate") String choseDate);

    /**
     *
     * @description 进驻部门数据版本——全省进驻部门数对比
     * @author m13624
     * @param startTime 版本1日期  endTime 版本2日期
     *                  如'2016-01-01'
     * @return
     *            版本1日期和版本2日期 的全省进驻部门数差异列表
     * @time 2017年01月11日 下午15:27:29
     */
    List<HashMap<String, Object>> qsjzbmsdb(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     *
     * @description 进驻部门数据版本——地市进驻部门数
     * @author m13624
     * @param choseDate
     *            ('2016-01-01')
     * @return
     *            指定日期的地市进驻部门数
     * @time 2017年01月11日 下午15:15:28
     */
    List<HashMap<String, Object>> dsjzbms(@Param("choseDate") String choseDate);

    /**
     *
     * @description 进驻部门数据版本——地市进驻部门数对比
     * @author m13624
     * @param startTime 版本1日期  endTime 版本2日期
     *                  如'2016-01-01'
     * @return
     *            版本1日期和版本2日期 的地市进驻部门数差异列表
     * @time 2017年01月11日 下午15:27:29
     */
    List<HashMap<String, Object>> dsjzbmsdb(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     *
     * @description 进驻部门数据版本——区县进驻部门数
     * @author m13624
     * @param choseDate
     *            ('2016-01-01')
     * @return
     *            指定日期的区县进驻部门数
     * @time 2017年01月11日 下午15:15:28
     */
    List<HashMap<String, Object>> qxjzbms(@Param("choseDate") String choseDate);

    /**
     *
     * @description 进驻部门数据版本——区县进驻部门数对比
     * @author m13624
     * @param startTime 版本1日期  endTime 版本2日期
     *                  如'2016-01-01'
     * @return
     *            版本1日期和版本2日期 的区县进驻部门数差异列表
     * @time 2017年01月11日 下午15:27:29
     */
    List<HashMap<String, Object>> qxjzbmsdb(@Param("startTime") String startTime, @Param("endTime") String endTime);

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
    List<HashMap<String, Object>> szdataProcess(@Param("startTime") String startTime, @Param("endTime") String endTime);

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
    List<HashMap<String, Object>> szbuslogic(@Param("startTime") String startTime, @Param("endTime") String endTime);

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
    List<HashMap<String, Object>> ywlaccount(@Param("startTime") String startTime, @Param("dateTime") String dateTime);
}