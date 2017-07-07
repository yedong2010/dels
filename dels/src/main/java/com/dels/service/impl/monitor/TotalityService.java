package com.dels.service.impl.monitor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.dels.dao.monitor.TotalityMapper;
import com.dels.service.monitor.ITotalityService;
import com.dels.utils.BasicConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author z11595
 * @description 总体统计服务
 * @time 2016年12月22日 下午4:08:02
 */
@Service
public class TotalityService implements ITotalityService {
    @Autowired
    TotalityMapper cDao;

    /**
     * @return 按月份从小到大排序的业务量数值
     * @description 办理概况列出近期月份的上网和非上网办理业务量
     */
    public HashMap<String, Object> handleSurvey(String type, Integer year, Integer smonth, Integer emonth, String category, String area, List<String> cparams) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Calendar c = Calendar.getInstance();
        if (year == null) {
            year = c.get(Calendar.YEAR);
        }
        if (smonth == null) {
            smonth = 1;
        }
        if (emonth == null) {
            emonth = year == c.get(Calendar.YEAR) ? c.get(Calendar.MONTH) + 1 : 12;
        }
        List<String> yf = new ArrayList<String>();
        for (int i = smonth; i < emonth + 1; i++) {
            yf.add(i + "月");
        }
        map.put("month", yf);
        switch (type) {
            case BasicConstants.BMYWGK_TYPE_QS:
                map.put("internet", cDao.qsywblByyf(year, smonth, emonth, BasicConstants.QUERY_TYPE_INTERNET, area));
                map.put("non_internet", cDao.qsywblByyf(year, smonth, emonth, BasicConstants.QUERY_TYPE_NONINTERNET, area));
                break;
            case BasicConstants.BMYWGK_TYPE_SZ:
                map.put("internet", cDao.szywblByyf(year, smonth, emonth, BasicConstants.QUERY_TYPE_INTERNET));
                map.put("non_internet", cDao.szywblByyf(year, smonth, emonth, BasicConstants.QUERY_TYPE_NONINTERNET));
                break;
            case BasicConstants.BMYWGK_TYPE_DS:
                map.put("internet", cDao.dsywblByyf(year, smonth, emonth, BasicConstants.QUERY_TYPE_INTERNET, cparams));
                map.put("non_internet", cDao.dsywblByyf(year, smonth, emonth, BasicConstants.QUERY_TYPE_NONINTERNET, cparams));
                break;
            case BasicConstants.BMYWGK_TYPE_QX:
                map.put("internet", cDao.qxywblByyf(year, smonth, emonth, BasicConstants.QUERY_TYPE_INTERNET, category, area, cparams));
                map.put("non_internet", cDao.qxywblByyf(year, smonth, emonth, BasicConstants.QUERY_TYPE_NONINTERNET, category, area, cparams));
                break;
        }
        return map;
    }

    /**
     * @description 查询三率信息
     * @return: 网上全流程办理率、上网办理率、网上办结率
     */
    @Override
    public HashMap<String, Object> rate(String type, Integer year, Integer smonth, Integer emonth, String area, String category, List<String> cparams) {
        Calendar c = Calendar.getInstance();
        if (year == null) {
            year = c.get(Calendar.YEAR);
        }
        if (smonth == null) {
            smonth = 1;
        }
        if (emonth == null) {
            emonth = year == c.get(Calendar.YEAR) ? c.get(Calendar.MONTH) + 1 : 12;
        }
        switch (type) {
            case BasicConstants.BMYWGK_TYPE_QS:
                return cDao.qssl(year, smonth, emonth, area);
            case BasicConstants.BMYWGK_TYPE_SZ:
                return cDao.qszsl(year, smonth, emonth, cparams);
            case BasicConstants.BMYWGK_TYPE_DS:
                return cDao.qdssl(year, smonth, emonth, cparams);
            case BasicConstants.BMYWGK_TYPE_QX:
                return cDao.qqxsl(year, smonth, emonth, cparams, category, area);
        }
        return null;
    }

    /**
     * @description 获取当前到现场次数
     * @return:跑动次数名称:name,跑动次数记录数:y
     */
    @Override
    public List<HashMap<String, Object>> times(String type, String area, String category, List<String> cparams) {
        return cDao.qsys(type, cparams, category, area);
    }

    /**
     * @return 进驻部门:jzbm,进驻事项:sxzs,行政审批:xzsp,公共服务:shfw
     * @description 查询当前事项概述和当前业务量概述信息
     */
    @Override
    public HashMap<String, Object> itemSurvey(String type, Integer year, Integer smonth, Integer emonth, String area, String category, List<String> cparams) {
        Calendar c = Calendar.getInstance();
        if (year == null) {
            year = c.get(Calendar.YEAR);
        }
        if (smonth == null) {
            smonth = 1;
        }
        if (emonth == null) {
            emonth = year == c.get(Calendar.YEAR) ? c.get(Calendar.MONTH) + 1 : 12;
        }
        switch (type) {
            case BasicConstants.BMYWGK_TYPE_QS:
                return cDao.itemsurvey(year, smonth, emonth, area);
            case BasicConstants.BMYWGK_TYPE_SZ:
                return cDao.qsz(year, smonth, emonth, cparams);
            case BasicConstants.BMYWGK_TYPE_DS:
                return cDao.qds(year, smonth, emonth, cparams);
            case BasicConstants.BMYWGK_TYPE_QX:
                return cDao.qqx(year, smonth, emonth, cparams, category, area);
        }
        return null;
    }

    /**
     * @return 业务量排名数组
     * @description 办结总业务量排名
     */
    @Override
    public List<HashMap<String, Object>> ywlph(String type, Integer year, Integer smonth, Integer emonth, String area, String category, List<String> cparams) {
        Calendar c = Calendar.getInstance();
        if (year == null) {
            year = c.get(Calendar.YEAR);
        }
        if (smonth == null) {
            smonth = 1;
        }
        if (emonth == null) {
            emonth = year == c.get(Calendar.YEAR) ? c.get(Calendar.MONTH) + 1 : 12;
        }
        switch (type) {
            case BasicConstants.BMYWGK_TYPE_QS:
                return cDao.qsywlph(year, smonth, emonth, area);
            case BasicConstants.BMYWGK_TYPE_SZ:
                return cDao.szywlph(year, smonth, emonth);
            case BasicConstants.BMYWGK_TYPE_DS:
                return cDao.dsywlph(year, smonth, emonth, null);
            case BasicConstants.BMYWGK_TYPE_QX:
                return cDao.qxywlph(year, smonth, emonth, area, category, null);
        }
        return null;
    }

    /**
     * @description 查询日期，指标计算过程的每一个计算步骤，和计算结果。
     */
    @Override
    public List<HashMap<String, Object>> countChase(String dataTime) {
        return cDao.szcountChase(dataTime);
    }

    /**
     * @return 业务量排名数组
     * @description 办结总业务量排名
     */
    @Override
    public List<HashMap<String, Object>> bjywlph(String type, String startTime, String endTime, String area, String category, List<String> cparams) {
        Calendar c = Calendar.getInstance();
        switch (type) {
            case BasicConstants.BMYWGK_TYPE_QS:
                return cDao.qsbjywlph(startTime, endTime, area);
            case BasicConstants.BMYWGK_TYPE_SZ:
                return cDao.szbjywlph(startTime, endTime);
            case BasicConstants.BMYWGK_TYPE_DS:
                return cDao.dsbjywlph(startTime, endTime, null);
            case BasicConstants.BMYWGK_TYPE_QX:
                return cDao.qxbjywlph(startTime, endTime, area, category, null);
        }
        return null;
    }

    /**
     * @return 业务量排名数组
     * @description 指定某个省级部门或者地市，查看业务量排名
     */
    @Override
    public List<HashMap<String, Object>> bjywlphDetail(String type, String startTime, String endTime, String id) {
        Calendar c = Calendar.getInstance();
        switch (type) {
            case BasicConstants.BMYWGK_TYPE_SZ:
                return cDao.szbjywlphDetail(startTime, endTime, id);
            case BasicConstants.BMYWGK_TYPE_DS:
                return cDao.dsbjywlphDetail(startTime, endTime, id);
            case BasicConstants.BMYWGK_TYPE_QX:
                String sjmc= id.substring(0,id.indexOf("市")+1);
                return cDao.qxbjywlphDetail(startTime, endTime, id, sjmc);
        }
        return null;
    }

    /**
     * @return 申办业务量排名数组
     * @description 申办总业务量排名
     */
    @Override
    public List<HashMap<String, Object>> sbywlph(String type, String startTime, String endTime, String area, String category, List<String> cparams) {
        Calendar c = Calendar.getInstance();
        switch (type) {
            case BasicConstants.BMYWGK_TYPE_QS:
                return cDao.qssbywlph(startTime, endTime, area);
            case BasicConstants.BMYWGK_TYPE_SZ:
                return cDao.szsbywlph(startTime, endTime);
            case BasicConstants.BMYWGK_TYPE_DS:
                return cDao.dssbywlph(startTime, endTime, null);
            case BasicConstants.BMYWGK_TYPE_QX:
                return cDao.qxsbywlph(startTime, endTime, area, category, null);
        }
        return null;
    }

    /**
     * @return 申办业务量排名数组
     * @description 指定某个省级部门或者地市，查看申办业务量排名
     */
    @Override
    public List<HashMap<String, Object>> sbywlphDetail(String type, String startTime, String endTime, String id) {
        Calendar c = Calendar.getInstance();
        switch (type) {
            case BasicConstants.BMYWGK_TYPE_SZ:
                return cDao.szsbywlphDetail(startTime, endTime, id);
            case BasicConstants.BMYWGK_TYPE_DS:
                return cDao.dssbywlphDetail(startTime, endTime, id);
            case BasicConstants.BMYWGK_TYPE_QX:
                String sjmc= id.substring(0,id.indexOf("市")+1);
                return cDao.qxsbywlphDetail(startTime, endTime, id, sjmc);
        }
        return null;
    }

}