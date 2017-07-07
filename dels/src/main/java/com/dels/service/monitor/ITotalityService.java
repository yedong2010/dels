package com.dels.service.monitor;

import java.util.HashMap;
import java.util.List;

/**
 * @author z11595
 * @description 总体统计服务接口
 * @time 2016年12月22日 下午5:56:41
 */
public abstract interface ITotalityService {

    /**
     * @param type     查询类型
     * @param emonth   结束月份
     * @param smonth   开始月份
     * @param year     查询年份
     * @param cparams  选定区划地市
     * @param category 区划类别(珠三角：0，粤东西北：1)
     * @return 按月份从小到大排序的业务量数值
     * @description 办理概况列出近期月份的上网和非上网办理业务量
     * @author z11595
     * @time 2016年12月22日 下午3:05:04
     */
    HashMap<String, Object> handleSurvey(String type, Integer year, Integer smonth, Integer emonth, String category, String area, List<String> cparams);

    /**
     * @param type     查询类型
     * @param emonth   结束月份
     * @param smonth   开始月份
     * @param year     查询年份
     * @param cparams  查询区域范围
     * @param category 区域类别
     * @description 查询三率信息
     * @author z11595
     * @return: 网上全流程办理率、上网办理率、网上办结率
     * @time 2016年12月22日 下午3:05:04
     */
    HashMap<String, Object> rate(String type, Integer year, Integer smonth, Integer emonth, String area, String category, List<String> cparams);

    /**
     * @param type     类型 (all:全省;province:省级部门;city:地市;county:区县)
     * @param cparams  地市名称数组
     * @param category 区域类别(珠三角:0,粤东西北:1)
     * @return 跑动次数名称:name,跑动次数记录数:y
     * @description 获取当前到现场次数
     */
    List<HashMap<String, Object>> times(String type, String area, String category, List<String> cparams);

    /**
     * @param type     (all:全省;province:省级部门;city:地市;county:区县)
     * @param year     查询年份
     * @param smonth   开始月份
     * @param emonth   结束月份
     * @param cparams  地市名称数组
     * @param category 区域类别
     * @return 进驻部门:jzbm,进驻事项:sxzs,行政审批:xzsp,公共服务:shfw
     * @description 查询当前事项概述
     */
    HashMap<String, Object> itemSurvey(String type, Integer year, Integer smonth, Integer emonth, String area, String category, List<String> cparams);

    /**
     * @param type     (all:全省;province:省级部门;city:地市;county:区县)
     * @param year     查询年份
     * @param smonth   开始月份
     * @param emonth   结束月份
     * @param cparams  地市名称数组
     * @param category 区域类别
     * @return    业务量排名数组
     * @description 办结总业务量排名
     */
    List<HashMap<String, Object>> ywlph(String type, Integer year, Integer smonth, Integer emonth, String area, String category, List<String> cparams);

    /**
     * @description 计算过程追溯
     * @author m13624
     * @param dateTime
     *            查询年月日
     * @return 查询日期，指标计算过程的每一个计算步骤，和计算结果。
     * @time 2017年03月16日 11:16:21
     */
    List<HashMap<String, Object>> countChase(String dateTime);

    /**
     * @param type     (all:全省;province:省级部门;city:地市;county:区县)
     * @param startTime   开始日期
     * @param endTime   结束日期
     * @param cparams  地市名称数组
     * @param category 区域类别
     * @return    业务量排名数组
     * @description 办结总业务量排名
     * @time 2017年03月28日 19:35:17
     */
    List<HashMap<String, Object>> bjywlph(String type, String  startTime, String  endTime, String area, String category, List<String> cparams);


    /**
     * @description 指定某个省级部门或者地市，查看业务量排名
     * @author m13624
     * @param type
     *            province:省级；city：地市
     * @param startTime
     *            起始月份
     * @param endTime
     *            截止月份
     * @param id
     *            如果是type为province，即为组织机构zzjgdm；如果type为city，即为市级名称sjmc
     * @return 查询日期，指标计算过程的每一个计算步骤，和计算结果。
     * @time 2017年03月28日 20:25:02
     */
    List<HashMap<String, Object>> bjywlphDetail(String type, String startTime, String endTime, String id);

    /**
     * @param type     (all:全省;province:省级部门;city:地市;county:区县)
     * @param startTime   开始日期
     * @param endTime   结束日期
     * @param cparams  地市名称数组
     * @param category 区域类别
     * @return    申办业务量排名数组
     * @description 申办业务量排名
     */
    List<HashMap<String, Object>> sbywlph(String type, String startTime, String endTime, String area, String category, List<String> cparams);

    /**
     * @description 指定某个省级部门或者地市，查看业务量排名
     * @author m13624
     * @param type
     *            province:省级；city：地市
     * @param year
     *            年
     * @param smonth
     *            起始月份
     * @param emonth
     *            截止月份
     * @param id
     *            如果是type为province，即为组织机构zzjgdm；如果type为city，即为市级名称sjmc
     * @return 查询日期，指标计算过程的每一个计算步骤，和计算结果。
     * @time 2017年03月27日 10:40:49
     */
    List<HashMap<String, Object>> sbywlphDetail(String type, String startTime, String endTime, String id);
}