package com.dels.dao.monitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dels.model.monitor.NoBusMatters;

/**
 * 
 * @description 业务办理Mapper持久层
 * @author z11595
 * @time 2016年12月28日 下午7:59:30
 */
public interface BusMapper {

    /**
     * 
     * @description 省级无业务事项数
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            起始月份
     * @param emonth
     *            截止月份
     * @param cparams
     *            省级部门名称范围
     * @return 省级无业务事项明细
     * @time 2016年11月30日 上午10:19:44
     */
    List<NoBusMatters> szwywsx(@Param("choseYear") Integer choseYear, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * 
     * @description 地市无业务事项数
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            起始月份
     * @param emonth
     *            截止月份
     * @param cparams
     *            查询当前地区范围
     * @return 地市无业务事项明细
     * @time 2016年11月30日 上午10:20:03
     */
    List<NoBusMatters> dswywsx(@Param("choseYear") Integer choseYear, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * 
     * @description 省级无业务事项数概况
     * @author z11595
     * @param year
     *            查询年份
     * @return 省级无业务事项概况
     * @time 2016年11月30日 上午10:20:03
     */
    NoBusMatters szwywsxgk(@Param("choseYear") Integer choseYear, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * 
     * @description 地市无业务事项数概况
     * @author z11595
     * @param year
     *            查询年份
     * @param cparams
     *            地市名称数组
     * @return 地市无业务事项概况
     * @time 2016年11月30日 下午2:22:05
     */
    NoBusMatters dswywsxgk(@Param("choseYear") Integer choseYear, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * 
     * @description 省级业务办理情况
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            省级部门名称数组
     * @return 省级业务办理明细数据
     * @time 2016年12月1日 下午3:27:35
     */
    List<HashMap<String, Object>> szywbl(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * 
     * @description 地市业务办理情况
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            查询区域
     * @return 地市业务办理明细数据
     * @time 2016年12月1日 下午3:28:29
     */
    List<HashMap<String, Object>> dsywbl(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * 
     * @description 省级业务办理概况
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @return 省级业务办理概况
     * @time 2016年12月1日 下午4:18:50
     */
    HashMap<String, Object> szywblgk(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * 
     * @description 地市业务办理概况
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组
     * @return 地市业务办理概况
     * @time 2016年12月1日 下午4:19:19
     */
    HashMap<String, Object> dsywblgk(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * 
     * @description 省级无业务事项详情
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            起始月份
     * @param emonth
     *            截止月份
     * @param name
     *            省级部门名称
     * @return 省级无业务事项明细数据
     * @time 2016年12月2日 下午3:14:08
     */
    List<HashMap<String, Object>> szwywsxxq(@Param("choseYear") Integer choseYear, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("name") String name);

    /**
     * 
     * @description 地市无业务事项详情
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            起始月份
     * @param emonth
     *            截止月份
     * @param name
     *            地市部门名称
     * @return 指定地市名称的无业务事项分类
     * @time 2016年12月2日 下午3:14:08
     */
    List<HashMap<String, Object>> dswywsxxq(@Param("choseYear") Integer choseYear, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("name") String name);

    /**
     * 
     * @description 查询省级异常办结明细
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param name
     *            省级部门名称
     * @return 省级异常办结类别信息
     * @time 2016年12月4日 下午2:02:53
     */
    List<HashMap<String, Object>> szycbj(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("name") String name);

    /**
     * 
     * @description 查询地市异常办结明细
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param name
     *            地市名称
     * @return 地市异常办结类别信息
     * @time 2016年12月4日 下午2:02:53
     */
    List<HashMap<String, Object>> dsycbj(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("name") String name);

    /**
     *
     * @description 查询省级异常办结明细-详情列表
     * @author m13624
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param name
     *            省级部门名称
     * @return 省级异常办结-详情明细列表
     * @time 2017年2月13日 下午2:02:53
     */
    List<HashMap<String, Object>> szycbjxq(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("name") String name);

    /**
     *
     * @description 查询地市异常办结明细-详情列表
     * @author m13624
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param name
     *            省级部门名称
     * @return 地市异常办结-详情明细列表
     * @time 2017年2月13日 下午2:02:53
     */
    List<HashMap<String, Object>> dsycbjxq(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("name") String name);

    /**
     *
     * @description 省级业务办理总计
     * @author z11700
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @return 省级三率一数总计数据
     * @time 2016/12/21 17:14
     */
    HashMap<String, Object> szywblzj(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * 
     * @description 地市业务办理总计
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param cparams
     *            地市名称数组
     * @return 地市三率一数总计数据
     * @time 2016年12月22日 下午5:58:44
     */
    HashMap<String, Object> dsywblzj(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     *
     * @description 省级无业务事项总计
     * @author z11700
     * @param year
     *            查询年份
     * @param smonth
     *            起始月份
     * @param emonth
     *            截止月份
     * @param cparams
     *            省级部门查询范围
     * @return 省级无业务事项总计数据
     * @time 2016/12/21 17:15
     */
    List<NoBusMatters> szwywsxzj(@Param("choseYear") Integer choseYear, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * 
     * @description 地市无业务事项总计
     * @author z11700
     * @param year
     *            查询年份
     * @param smonth
     *            起始月份
     * @param emonth
     *            截止月份
     * @param cparams
     *            地市名称数组
     * @return 地市无业务事项总计
     * @time 2016年12月22日 下午5:49:44
     */
    List<NoBusMatters> dswywsxzj(@Param("choseYear") Integer choseYear, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("cparams") List<String> cparams);

    /**
     * 
     * @description 行政审批的上网申办业务详情
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param name
     *            省级部门名称
     * @return 上网申办业务明细
     * @time 2017年1月5日 下午3:19:41
     */
    List<HashMap<String, Object>> swsbx(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("name") String name);

    /**
     * 
     * @description 行政审批的办结业务量
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param name
     *            省级部门名称
     * @return 办结业务明细
     * @time 2017年1月5日 下午3:24:58
     */
    List<HashMap<String, Object>> bjzsx(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("name") String name);

    /**
     * 
     * @description 行政审批的网上全流程办结业务
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param name
     *            省级部门名称
     * @return 网上全流程办结业务明细
     * @time 2017年1月5日 下午3:24:58
     */
    List<HashMap<String, Object>> wsqlcbjx(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("name") String name);

    /**
     * 
     * @description 公共服务的办结业务
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param name
     *            省级部门名称
     * @return 办结业务明细
     * @time 2017年1月5日 下午3:26:31
     */
    List<HashMap<String, Object>> bjzss(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("name") String name);

    /**
     * 
     * @description 公共服务的上网申办业务
     * @author z11595
     * @param year
     *            查询年份
     * @param smonth
     *            开始月份
     * @param emonth
     *            结束月份
     * @param name
     *            省级部门名称
     * @return 上网申办业务明细
     * @time 2017年1月5日 下午3:27:18
     */
    List<HashMap<String, Object>> swsbs(@Param("year") Integer year, @Param("smonth") Integer smonth, @Param("emonth") Integer emonth, @Param("name") String name);

    /**
     *
     * @description 全省——业务趋势图
     * @author m13624
     * @param startTime
     *             起始日期
     * @param endTime
     *             截止日期
     * @param area
     *             是否包含非行政区划（1：包含；0：不包含）
     * @return 全省的日期范围内的每天业务量
     * @time 2017年2月28日 下午2:34:28
     */
    List<HashMap<String, Object>> qstrend(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("area") Integer area);

    /**
     *
     * @description 省级——业务趋势图
     * @author m13624
     * @param startTime
     *             起始日期
     * @param endTime
     *             截止日期
     * @return 省级的日期范围内的每天业务量
     * @time 2017年2月28日 下午2:34:28
     */
    List<HashMap<String, Object>> sztrend(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("cparams") List<String> cparams);

    /**
     *
     * @description 地市——业务趋势图
     * @author m13624
     * @param startTime
     *             起始日期
     * @param endTime
     *             截止日期
     * @param cparams
     *             地市名称数组
     * @return 地市的日期范围内的每天业务量
     * @time 2017年2月28日 下午2:34:28
     */
    List<HashMap<String, Object>> dstrend(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("cparams") List<String> cparams);

    /**
     *
     * @description 区县——业务趋势图
     * @author m13624
     * @param startTime
     *             起始日期
     * @param endTime
     *             截止日期
     * @param area
     *             是否包含非行政区划（1：包含；0：不包含）
     * @param category
     *            区域类别(0:珠三角;1:粤东西北)
     * @return 区县的日期范围内的每天业务量
     * @time 2017年2月28日 下午2:34:28
     */
    List<HashMap<String, Object>> qxtrend(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("area") Integer area, @Param("category") String category, @Param("cparams") List<String> cparams);

    /**
     *
     * @description 全省——业务趋势图——网上办结
     * @author m13624
     * @param startTime
     *             起始日期
     * @param endTime
     *             截止日期
     * @param area
     *             是否包含非行政区划（1：包含；0：不包含）
     * @return 全省的日期范围内的网上办结量，包含行政审批和公共服务
     * @time 2017年2月28日 下午2:34:28
     */
    List<HashMap<String, Object>> qstrendwsbj(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("area") Integer area);

    /**
     *
     * @description 省级——业务趋势图——网上办结
     * @author m13624
     * @param startTime
     *             起始日期
     * @param endTime
     *             截止日期
     * @return 省级的日期范围内的网上办结量，包含行政射你和射虎
     * @time 2017年2月28日 下午2:34:28
     */
    List<HashMap<String, Object>> sztrendwsbj(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("cparams") List<String> cparams);

    /**
     *
     * @description 地市——业务趋势图
     * @author m13624
     * @param startTime
     *             起始日期
     * @param endTime
     *             截止日期
     * @param cparams
     *             地市名称数组
     * @return 地市的日期范围内的每天业务量
     * @time 2017年2月28日 下午2:34:28
     */
    List<HashMap<String, Object>> dstrendwsbj(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("cparams") List<String> cparams);

    /**
     *
     * @description 区县——业务趋势图
     * @author m13624
     * @param startTime
     *             起始日期
     * @param endTime
     *             截止日期
     * @param area
     *             是否包含非行政区划（1：包含；0：不包含）
     * @param category
     *            区域类别(0:珠三角;1:粤东西北)
     * @return 区县的日期范围内的每天业务量
     * @time 2017年2月28日 下午2:34:28
     */
    List<HashMap<String, Object>> qxtrendwsbj(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("area") Integer area, @Param("category") String category, @Param("cparams") List<String> cparams);

    List<Map<String, Object>> wywsxsRestful(@Param("zzjgdm")String zzjgdm, @Param("year")Integer year, @Param("smonth")Integer smonth, @Param("emonth")Integer emonth);
}