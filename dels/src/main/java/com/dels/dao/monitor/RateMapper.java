package com.dels.dao.monitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dels.model.monitor.CommonModel;

/**
 * @author z11595
 * @description 三率一数和事项进驻接口层
 * @time 2016年12月28日 下午7:44:15
 */
public interface RateMapper {

    /**
     * @param emonth
     *            结束月份
     * @param smonth
     *            开始月份
     * @param year
     *            查询年份
     * @param cparams
     *            省级部门名称数组
     * @return 省级三率一数明细数据
     * @description 获取省级的三率一数统计数据
     * @author z11595
     * @time 2016年11月28日 下午4:35:02
     */
    List<CommonModel> szslys(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * @return 省级三率一数明细数据
     * @description 获取省级的三率一数统计数据 —— 静态表抽取，性能测试
     * @author m13624
     * @time 2017年04月06日 18:26:41
     */
    List<CommonModel> szslysStatic(@Param("cparams") List<String> cparams);


    /**
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组
     * @return 地市三率一数明细数据
     * @description 获取地市的三率一数统计数据
     * @author z11595
     * @time 2016年12月22日 下午5:34:06
     */
    List<CommonModel> dsslys(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * @return 地市三率一数明细数据
     * @description 获取地市的三率一数统计数据 —— 静态表抽取，性能测试
     * @author m13624
     * @time 2017年04月06日 18:59:41
     */
    List<CommonModel> dsslysStatic(@Param("cparams") List<String> cparams);

    /**
     * @return 地市三率一数 —— 总计
     * @description 获取地市的三率一数统计数据 —— 计算抽取的静态数据总计
     * @author m13624
     * @time 2017年04月06日 18:59:41
     */
    List<CommonModel> dsslyszjStatic(@Param("cparams") List<String> cparams);

    /**
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组
     * @param category
     *            行政区划类别(珠三角:0,粤东西北:1)
     * @return 区县的三率一数明细数据
     * @description 获取区县的三率一数统计数据
     * @author z11595
     * @time 2016年12月22日 下午5:35:09
     */
    List<CommonModel> qxslys(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams, @Param("category") String category,
            @Param("area") String area);

    /**
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            省级部门名称数组
     * @return 下载所需的省级三率一数明细数据
     * @description 下载获取省级的三率一数统计数据
     * @author z11595
     * @time 2016年12月23日 上午10:52:24
     */
    List<CommonModel> szslysdown(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组
     * @return 下载所需的地市三率一数明细数据
     * @description 下载获取地市的三率一数统计数据
     * @author z11595
     * @time 2016年12月23日 上午10:53:27
     */
    List<CommonModel> dsslysdown(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组
     * @param category
     *            区划类别(珠三角:0,粤东西北:1)
     * @return 下载所需的区县三率一数明细数据
     * @description 下载获取区县的三率一数统计数据
     * @author z11595
     * @time 2016年12月23日 上午10:54:20
     */
    List<CommonModel> qxslysdown(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams,
            @Param("category") String category, @Param("area") String area);

    /**
     * @param name
     *            省级部门名称
     * @return 按照省级部门名称查询当前网上全流程事项明细数据
     * @description 省级网上全流程事项数详情
     * @author z11595
     * @time 2016年12月2日 下午3:14:08
     */
    List<HashMap<String, Object>> szwsqlcsxsxq(@Param("ifsxzs") Integer ifsxzs, @Param("name") String name, @Param("choseYear") Integer choseYear, @Param("emonth") Integer emonth);

    /**
     * @param name
     *            地市部门名称
     * @return 按照地市名称查询当前网上全流程事项明细数据
     * @description 地市网上全流程事项数详情
     * @author z11595
     * @time 2016年12月2日 下午3:14:08
     */
    List<HashMap<String, Object>> dswsqlcsxsxq(@Param("ifsxzs") Integer ifsxzs, @Param("name") String name, @Param("choseYear") Integer choseYear, @Param("emonth") Integer emonth);

    /**
     * @param name
     *            区县名称
     * @return 按照区县名称查询当前网上全流程事项明细数据
     * @description 区县网上全流程事项数详情
     * @author y11290
     * @time 2016年12月15日 下午5:14:08
     */
    List<HashMap<String, Object>> qxwsqlcsxsxq(@Param("ifsxzs") Integer ifsxzs, @Param("name") String name, @Param("choseYear") Integer choseYear, @Param("emonth") Integer emonth);

    /**
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @return 省级三率一数总计数据
     * @description 省级三率一数总计
     * @author z11700
     * @time 2016年12月22日 下午5:45:07
     */
    List<CommonModel> szslyszj(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组
     * @return 地市三率一数总计数据
     * @description 地市三率一数总计
     * @author z11700
     * @time 2016年12月22日 下午5:46:07
     */
    List<CommonModel> dsslyszj(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组
     * @param category
     *            行政区划类别(珠三角:0,粤东西北:1)
     * @return 区县三率一数总计数据
     * @description 区县三率一数总计
     * @author z11700
     * @time 2016/12/21 17:13
     */
    List<CommonModel> qxslyszj(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams,
            @Param("category") String category, @Param("area") String area);

    /**
     * @param
     * @return 省级数据质量总计数据
     * @description 省级事项进驻总计
     * @author m13624
     * @time 2016/12/27 9:15
     */
    HashMap<String, Object> szsxjzzj(@Param("cparams") List<String> cparams, @Param("choseYear") Integer choseYear, @Param("emonth") Integer emonth);

    /**
     * @param cparams
     *            地市名称数组
     * @return 地市数据质量总计数据
     * @description 地市事项进驻总计
     * @author m13624
     * @time 2016/12/27 9:15
     */
    HashMap<String, Object> dssxjzzj(@Param("cparams") List<String> cparams, @Param("choseYear") Integer choseYear, @Param("emonth") Integer emonth);

    /**
     * @param cparams
     *            地市名称数组
     * @param category
     *            行政区划类别
     * @return 地市数据质量总计数据
     * @description 区县事项进驻总计
     * @author m13624
     * @time 2016/12/27 9:35
     */
    HashMap<String, Object> qxsxjzzj(@Param("cparams") List<String> cparams, @Param("category") String category, @Param("area") String area, @Param("choseYear") Integer choseYear, @Param("emonth") Integer emonth);

    /**
     * @return 地市事项进驻
     * @description 获取省级的事项进驻统计
     * @author m13624
     * @time 2016年12月24日 下午5:34:06
     */
    List<HashMap<String, Object>> szsxjz(@Param("cparams") List<String> cparams, @Param("choseYear") Integer choseYear, @Param("emonth") Integer emonth, @Param("year") Integer year);

    /**
     * @param cparams
     *            地市名称数组
     * @return 地市事项进驻
     * @description 获取地市的事项进驻统计
     * @author m13624
     * @time 2016年12月24日 下午5:34:06
     */
    List<HashMap<String, Object>> dssxjz(@Param("cparams") List<String> cparams, @Param("choseYear") Integer choseYear, @Param("emonth") Integer emonth, @Param("year") Integer year);

    /**
     * @param cparams
     *            地市名称数组
     * @param category
     *            行政区划类别
     * @return 地市事项进驻
     * @description 获取区县的事项进驻统计
     * @author m13624
     * @time 2016年12月24日 下午5:34:06
     */

    List<HashMap<String, Object>> qxsxjz(@Param("cparams") List<String> cparams, @Param("category") String category, @Param("area") String area,  @Param("choseYear") Integer choseYear, @Param("emonth") Integer emonth, @Param("year") Integer year);

    /**
     * @param cparams
     *            省级部门名称数组
     * @return 下载所需的事项进驻数据
     * @description 下载获取省级事项进驻统计数据
     * @author m13624
     * @time 2016年12月28日 上午10:14:22
     */
    List<HashMap<String, Object>> szsxjzdown(@Param("cparams") List<String> cparams, @Param("choseYear") Integer choseYear, @Param("emonth") Integer emonth);

    /**
     * @param cparams
     *            地市名称数组
     * @return 下载所需的事项进驻数据
     * @description 下载获取地市事项进驻统计数据
     * @author m13624
     * @time 2016年12月26日 上午10:53:27
     */
    List<HashMap<String, Object>> dssxjzdown(@Param("cparams") List<String> cparams, @Param("choseYear") Integer choseYear, @Param("emonth") Integer emonth);

    /**
     * @param cparams
     *            地市名称数组
     * @param category
     *            区划类别(珠三角:0,粤东西北:1)
     * @return 下载所需的区县事项进驻数据
     * @description 下载获取区县事项进驻统计数据
     * @author m13624
     * @time 2016年12月26日 上午10:54:20
     */
    List<HashMap<String, Object>> qxsxjzdown(@Param("cparams") List<String> cparams, @Param("category") String category, @Param("area") String area, @Param("choseYear") Integer choseYear, @Param("emonth") Integer emonth);

    /**
     * @param name
     *            地市名称
     * @return 地市事项进驻已进驻部门详情
     * @description 下载——地市事项进驻已进驻部门详情
     * @author m13624
     * @time 2016年12月28日 下午2:10:18
     */

    List<HashMap<String, Object>> dssxjzbmxqdown(@Param("name") String name);

    /**
     * @param name
     *            区县名称
     * @return 区县事项进驻已进驻部门详情
     * @description 下载——区县事项进驻已进驻部门详情
     * @author m13624
     * @time 2016年12月28日 下午2:12:12
     */
    List<HashMap<String, Object>> qxsxjzbmxqdown(@Param("name") String name);

    /**
     * @param name
     *            区县名称
     * @return 区县事项进驻已进驻部门详情
     * @description 区县事项进驻已进驻部门详情
     * @author m13624
     * @time 2016年12月27日 下午3:19:08
     */
    List<HashMap<String, Object>> qxsxjzbmxq(@Param("name") String name);

    /**
     * @param name
     *            省级名称
     * @param sxlxmc
     *            事项类型名称
     * @return 省级事项进驻-已进驻事项详情
     * @description 省级事项进驻-已进驻事项详情
     * @author m13624
     * @time 2016年12月28日 上午9:37:08
     */
    List<HashMap<String, Object>> szsxjzsxxq(@Param("name") String name, @Param("sxlxmc") String sxlxmc, @Param("year") Integer year, @Param("emonth") Integer emonth);

    /**
     * @param name
     *            省级名称
     * @param sxlxmc
     *            事项类型名称
     * @return 省级事项进驻-已进驻事项详情
     * @description 下载——省级事项进驻-已进驻事项详情
     * @author m13624
     * @time 2016年12月28日 下午3:15:15
     */
    List<HashMap<String, Object>> szsxjzsxxqdown(@Param("name") String name, @Param("sxlxmc") String sxlxmc, @Param("year") Integer year, @Param("emonth") Integer emonth);

    /**
     * @param name
     *            地市名称
     * @param sxlxmc
     *            事项类型名称
     * @return 地市事项进驻-已进驻事项详情
     * @description 地市事项进驻-已进驻事项详情
     * @author m13624
     * @time 2016年12月27日 下午3:59:08
     */
    List<HashMap<String, Object>> dssxjzsxxq(@Param("name") String name, @Param("sxlxmc") String sxlxmc, @Param("year") Integer year, @Param("emonth") Integer emonth);

    /**
     * @param name
     *            地市名称
     * @return 地市事项进驻已进驻部门详情
     * @description 地市事项进驻已进驻部门详情
     * @author m13624
     * @time 2016年12月27日 下午3:19:08
     */

    List<HashMap<String, Object>> dssxjzbmxq(@Param("name") String name);

    /**
     * @param name
     *            地市名称
     * @param sxlxmc
     *            事项类型名称
     * @return 地市事项进驻-已进驻事项详情
     * @description 下载——地市事项进驻-已进驻事项详情
     * @author m13624
     * @time 2016年12月28日 下午3:16:08
     */
    List<HashMap<String, Object>> dssxjzsxxqdown(@Param("name") String name, @Param("sxlxmc") String sxlxmc, @Param("year") Integer year, @Param("emonth") Integer emonth);

    /**
     * @param name
     *            区县名称
     * @param sxlxmc
     *            事项类型名称
     * @return 区县事项进驻-已进驻事项详情
     * @description 区县事项进驻-已进驻事项详情
     * @author m13624
     * @time 2016年12月27日 下午3:59:08
     */
    List<HashMap<String, Object>> qxsxjzsxxq(@Param("name") String name, @Param("sxlxmc") String sxlxmc, @Param("year") Integer year, @Param("emonth") Integer emonth);

    /**
     * @param name
     *            区县名称
     * @param sxlxmc
     *            事项类型名称
     * @return 区县事项进驻-已进驻事项详情
     * @description 下载——区县事项进驻-已进驻事项详情
     * @author m13624
     * @time 2016年12月28日 下午3:18:09
     */
    List<HashMap<String, Object>> qxsxjzsxxqdown(@Param("name") String name, @Param("sxlxmc") String sxlxmc, @Param("year") Integer year, @Param("emonth") Integer emonth);

    /**
     * @param pdcs
     *            跑动次数
     * @param name
     *            省级部门名称
     * @return 省级进驻事项明细
     * @description 省级事项进驻按照跑动次数和部门名称查询
     * @author z11595
     * @time 2017年1月9日 下午4:10:48
     */
    List<HashMap<String, Object>> szsxjzBypd(@Param("pdcs") String pdcs, @Param("name") String name, @Param("choseYear") Integer choseYear, @Param("emonth") Integer emonth);

    /**
     * @param pdcs
     *            跑动次数
     * @param name
     *            地市名称
     * @return 地市进驻事项明细
     * @description 地市事项进驻按照跑动次数和地市名称查询
     * @author z11595
     * @time 2017年1月9日 下午4:15:10
     */
    List<HashMap<String, Object>> dssxjzBypd(@Param("pdcs") String pdcs, @Param("name") String name, @Param("choseYear") Integer choseYear, @Param("emonth") Integer emonth);

    /**
     * @param pdcs
     *            跑动次数
     * @param name
     *            区县名称
     * @return 区县进驻事项明细
     * @description 区县事项进驻按照跑动次数和区县名称查询
     * @author z11595
     * @time 2017年1月9日 下午4:16:49
     */
    List<HashMap<String, Object>> qxsxjzBypd(@Param("pdcs") String pdcs, @Param("name") String name, @Param("choseYear") Integer choseYear, @Param("emonth") Integer emonth);

    /**
     * 
     * @description 获取往年静态数据
     * @author z11595
     * @param type
     *            状态
     * @param year
     *            年份
     * @param cparams 查询当前范围
     * @return 三率一数总体数据
     * @time 2017年1月19日 上午11:22:06
     */
    List<CommonModel> previousData(@Param("type") String type, @Param("year") Integer year,@Param("cparams") List<String> cparams);

    /**
     *
     * @description 省级的三率一数简化版
     * @author m13624
     * @param choseYear
     *            年份
     * @return 三率一数总体数据
     * @time 2017年3月13日 下午4:46:08
     */
    List<HashMap<String, Object>> szsimpleRate(@Param("choseYear") Integer choseYear, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth);

    /**
     *
     * @description 地市的三率一数简化版
     * @author m13624
     * @param choseYear
     *            年份
     * @return 三率一数总体数据
     * @time 2017年3月13日 下午4:46:08
     */
    List<HashMap<String, Object>> dssimpleRate(@Param("choseYear") Integer choseYear, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth);

    /**
     *
     * @description 区县的三率一数简化版
     * @author m13624
     * @param choseYear
     *            年份
     * @return 三率一数总体数据
     * @time 2017年3月13日 下午4:46:08
     */
    List<HashMap<String, Object>> qxsimpleRate(@Param("choseYear") Integer choseYear, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth);

    /**
     * @description 数据指标统计——综合排名
     * @author m13624
     * @param startTime
     *            查询起始年月
     * @param endTime
     *            查询截止年月
     * @return 查询日期范围内事项总数、总业务量、网上全流程办理率、上网办理率、网上办结率、到现场次数、审批时间压缩率、总分数等。
     * @time 2017年03月15日 19:26:51
     */
    List<HashMap<String, Object>> szrateRank(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * @description 数据指标统计——综合排名
     * @author m13624
     * @param startTime
     *            查询起始年月
     * @param endTime
     *            查询截止年月
     * @return 查询日期范围内业务逻辑异常数、业务过程异常数、业务总量异常数、数据内容异常数、数据规范异常数
     * @time 2017年03月15日 20:12:35
     */
    List<HashMap<String, Object>> szdataAbnormal(@Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * @description: 获取部门业务总量
     * @author: l13595
     * @param: zzjgdm-组织机构代码，year-查询年份，smonth-查询起始月份，emonth-查询截止月份
     * @return: 返回部门代码、部门名称、办结总业务量、统计年份、统计月份
     * @time: 2017/3/22 14:50
     */
    List<Map<String, Object>> bmywzl(@Param("zzjgdm")String zzjgdm, @Param("year") Integer year, @Param("smonth")Integer smonth, @Param("emonth")Integer emonth);

    Integer sumBMYWZL(@Param("zzjgdm")String zzjgdm, @Param("year") Integer year, @Param("smonth")Integer smonth, @Param("emonth")Integer emonth);

    /**
     * @description: 获取部门 网上全流程办理率、上网办理率、网上办结率
     * @author: l13595
     * @param :zzjgmc-组织机构名称，year-查询年份，smonth-查询其实月份，emonth-查询截止月份
     * @return: 返回查询的组织机构名称、网上全流程办理率、上网办理率、网上办结率
     * @time: 2017/3/23 17:09
     */
    List<Map<String, Object>> bmywnl(@Param("zzjgdm")String zzjgdm, @Param("year")Integer year, @Param("smonth")Integer smonth, @Param("emonth")Integer emonth);

}