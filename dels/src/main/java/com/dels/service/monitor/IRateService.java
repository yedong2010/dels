package com.dels.service.monitor;

import com.dels.utils.LogDescription;
import com.dels.model.monitor.CommonModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author z11595
 * @description 三率一数统计服务接口
 * @time 2016年12月22日 下午5:56:41
 */
public abstract interface IRateService {
    /**
     * @param type       (province:省级部门;city:地市;county:区县)
     * @param emonth     结束月份
     * @param smonth     开始月份
     * @param year       年份
     * @param cityParams 地市名称数组
     * @param category   区域类别
     * @return 三率一数包括行政审批和公共服务业务量详情
     * @description 按照类型查询三率一数详细数据
     * @author z11595
     * @time 2016年11月28日 上午11:49:24
     */
    @LogDescription(description = "三率一数统计-查询")
    List<CommonModel> slys(String type, Integer year, Integer smonth, Integer emonth, String area, String category, List<String> cityParams);

    /**
     * @param type 类型(province:省级部门;city:地市;county:区县)
     * @param name 省级部门/地市名称
     * @return 指定省级/地市名称的网上全流程事项列表
     * @description 网上全流程事项数详情
     * @author z11595
     * @time 2016年12月8日 下午3:09:30
     */
    List<HashMap<String, Object>> wsqlcsxsxq(String type, Integer ifsxzs, String name, Integer choseYear, Integer emonth);

    /**
     * @param type     (province:省级部门;city:地市;county:区县)
     * @param year     年份
     * @param smonth   开始月份
     * @param emonth   结束月份
     * @param cparams  地市名称数组
     * @param category 区域类别
     * @return 三率一数包括行政审批和公共服务业务量详情
     * @description 下载按照类型查询三率一数详细数据
     * @author z11595
     * @time 2016年12月23日 上午10:45:54
     */
    List<CommonModel> slys_down(String type, Integer year, Integer smonth, Integer emonth, String category, String area, List<String> cparams);

    /**
     * @param type(city:地市;county:区县)
     * @param cparams                 地市名称数组
     * @param category                行政区划类别
     * @return 部门总数:BMZS,事项总数：SXZS,行政审批总数:XZSPZS，公共服务总数：SHFWZS
     * @description 事项进驻
     * @author m13624
     * @time 2016年12月24日 下午2:19:59
     */
    List<HashMap<String, Object>> sxjz(String type, String category, String area, Integer choseYear, Integer emonth, List<String> cparams);

    /**
     * @param type     (city:地市;county:区县)
     * @param cparams  地市名称数组
     * @param category 行政区划类别
     * @return 部门总数:BMZS,事项总数：SXZS,行政审批总数:XZSPZS，公共服务总数：SHFWZS
     * @description 事项进驻下载
     * @author m13624
     * @time 2016年12月26日 上午10:02:21
     */
    List<HashMap<String, Object>> sxjz_down(String type, String category, String area, Integer choseYear, Integer emonth, List<String> cparams);

    /**
     * @param type 类型(city:地市;county:区县)
     * @param name 省级|地市名称
     * @return 事项进驻详情
     * @description 事项进驻——已进驻部门详情
     * @author m13624
     * @time 2016年12月27日 下午3:15:09
     */
    List<HashMap<String, Object>> sxjzbmxq(String type, String name);

    /**
     * @param name
     * @return
     * @description 事项进驻——已进驻部门下载
     * @author m13624
     * @time 2016年12月28日 下午2:15:09
     */
    List<HashMap<String, Object>> sxjzbmxq_down(String type, String name);

    /**
     * @param type   类型(city:地市;county:区县)
     * @param name   省级|地市名称
     * @param sxlxmc 事项类型名称
     * @return array
     * @description 事项进驻——已进驻事项详情
     * @author m13624
     * @time 2016年12月27日 下午3:56:23
     */
    List<HashMap<String, Object>> sxjzsxxq(String type, String name, String sxlxmc, Integer year, Integer emonth);

    /**
     * @param type   类型(city:地市;county:区县)
     * @param name   省级|地市名称
     * @param sxlxmc 事项类型名称
     * @return 已进驻事项详情
     * @description 事项进驻——已进驻事项详情下载
     * @author m13624
     * @time 2016年12月28日 下午3:07:23
     */
    List<HashMap<String, Object>> sxjzsxxq_down(String type, String name, String sxlxmc, Integer year, Integer emonth);

    /**
     * @param type 类型(province:省级;city:地市;county:区县)
     * @param name 省级|地市名称
     * @param pdcs 跑动次数(0-3)
     * @return 事项详细信息
     * @description 进驻事项通过名称和跑动次数查询明细
     * @author z11595
     * @time 2017年1月9日 下午3:58:44
     */
    List<HashMap<String, Object>> sxjzBypd(String type, String pdcs, String name, Integer choseYear, Integer emonth);

    /**
     * @param type 类型(province:省级;city:地市;county:区县)
     * @param choseYear 选择年份
     * @return 三率一数等信息
     * @description 通过年份，查询三率一数简化版
     * @author m13624
     * @time 2017年3月13日 下午4:42:12
     */
    List<HashMap<String, Object>> simpleRate(String type, Integer choseYear, Integer smonth, Integer emonth);

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
    List<HashMap<String, Object>> rateRank(String startTime, String endTime);

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
    List<HashMap<String, Object>> dataAbnormal(String startTime, String endTime);

    /**
     * @description:
     * @author: l13595
     * @param: zzjgdm-组织机构代码，year-查询年份，smonth-查询起始月份，emonth-查询截止月份
     * @return: 返回组织机构代码、组织机构名称、总办结业务量、统计年份、统计月份
     * @time: 2017/3/22 14:56
     */
    List<Map<String, Object>> bmywzl(String zzjgdm, Integer year, Integer smonth, Integer emonth);

    /**
     * @description: 部门业务总量
     * @author: l13595
     * @param:
     * @return:
     * @time: 2017/3/23 17:22
     */
    Integer sumBMYWZL(String zzjgdm, Integer year, Integer smonth, Integer emonth);

    /**
     * @description: 查询部门业务能力数据
     * @author: l13595
     * @param: zzjgmc-组织机构名称，year-查询年份，smonth-查询起始月份，emonth-查询截止月份
     * @return: 返回查询的组织机构名称、网上全流程办理率、上网办理率、网上办结率
     * @time: 2017/3/23 17:22
     */
    List<Map<String, Object>> bmywnlRestful(String zzjgdm, Integer year, Integer smonth, Integer emonth);
}
