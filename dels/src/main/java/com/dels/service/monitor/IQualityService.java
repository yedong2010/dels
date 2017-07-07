package com.dels.service.monitor;

import java.util.HashMap;
import java.util.List;

public interface IQualityService {

    /**
     * 
     * @description 查询当前质量概况
     * @author z11595
     * @param type (all:全省;province:省级部门;city:地市)
     * @param year 查询年份
     * @param smonth 开始月份
     * @param emonth 结束月份
     * @param cparams 区划范围(地市名称数组或区划类别)
     * @return 正确记录数、错误记录数、总记录、正确率
     * @time 2016年12月15日 上午11:28:07
     */
    HashMap<String, Object> zlgk(String type, Integer year, Integer smonth, Integer emonth, List<String> cparams);

    /**
     * 
     * @description 错误分类概况
     * @author z11595
     * @param type (all:全省;province:省级部门;city:地市)
     * @param year 查询年份
     * @param smonth 开始月份
     * @param emonth 结束月份
     * @param cparams 区划范围(地市名称数组或区划类别)
     * @return 错误类别:name,错误记录数：y
     * @time 2016年12月15日 上午11:52:14
     */
    List<HashMap<String, Object>> cwfl(String type, Integer year, Integer smonth, Integer emonth, List<String> cparams);

    /**
     * 
     * @description 通过地市或省级名称查询错误分类详情
     * @author z11595
     * @param name 省级或地市名称
     * @param year 查询年份
     * @param smonth 开始月份
     * @param emonth 结束月份
     * @return 错误类别码:error,错误记录数:zs,名称:name
     * @time 2016年12月17日 下午1:58:07
     */
    List<HashMap<String, Object>> cwflxq(String name, Integer year, Integer smonth, Integer emonth);

    /**
     *
     * @description 通过地市或省级名称查询错误分类详情列表
     * @author m13624
     * @param name 省级或地市名称
     * @param year 查询年份
     * @param smonth 开始月份
     * @param emonth 结束月份
     * @return 错误类别码:error,错误记录数:zs,名称:name
     * @time 2017年04月19日 上午10:32:51
     */
    List<HashMap<String, Object>> cwflxqlb(String name, Integer year, Integer smonth, Integer emonth);

    /**
     * 
     * @description 数据质量统计
     * @author z11595
     * @param type
     *            (province:省级;city:地市)
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组或区域类别
     * @return 名称,总量，正确记录数，错误记录数，正确率
     * @time 2016年12月5日 下午5:38:37
     */
    List<HashMap<String, Object>> sjzl(String type, Integer year, Integer smonth, Integer emonth, List<String> cparams);

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
    List<HashMap<String, Object>> qsjzsxs(String choseDate);

    /**
     *
     * @description 进驻事项数据版本——全省进驻事项数对比
     * @author m13624
     * @param startTime  endTime
     *            ('2016-01-01')
     * @return
     *            版本1日期和版本2日期的进驻事项数差异列表
     * @time 2017年01月07日 上午10:21:38
     */
    List<HashMap<String, Object>> qsjzsxsdb(String startTime, String endTime);

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
    List<HashMap<String, Object>> qtjzsxs(String type, String choseDate);

    /**
     *
     * @description 进驻事项数据版本——其他进驻事项数对比（省级、地市、区县）
     * @author m13624
     * @param type 省级、地市、区县  startTime 版本1日期  endTime 版本2日期
     *             如'2016-01-01'
     * @return
     *            对应类型 版本1日期和版本2日期的进驻事项数差异列表
     * @time 2017年01月07日 下午13:46:59
     */
    List<HashMap<String, Object>> qtjzsxsdb(String type, String startTime, String endTime);

    /**
     *
     * @description 进驻部门数据版本——进驻部门数
     * @author m13624
     * @param type , choseDate
     *            ('2016-01-01')
     * @return
     *            对应类型指定日期的进驻部门数
     * @time 2017年01月11日 下午15:11:02
     */
    List<HashMap<String, Object>> jzbms(String type, String choseDate);

    /**
     *
     * @description 进驻部门数据版本——进驻部门数对比
     * @author m13624
     * @param type , startTime 版本1日期 , endTime 版本2日期
     *            ('2016-01-01')
     * @return
     *            对应类型 版本1日期和版本2日期的进驻部门数差异列表
     * @time 2017年01月11日 下午15:11:02
     */
    List<HashMap<String, Object>> jzbmsdb(String type, String startTime, String endTime);

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
    List<HashMap<String, Object>> dataProcess(String startTime, String endTime);

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
    List<HashMap<String, Object>> buslogic(String startTime, String endTime);

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
    List<HashMap<String, Object>> ywlaccount(String startTime, String dateTime);
}
