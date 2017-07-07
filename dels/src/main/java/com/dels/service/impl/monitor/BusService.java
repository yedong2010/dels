package com.dels.service.impl.monitor;

import java.util.*;

import com.dels.dao.monitor.BusMapper;
import com.dels.model.monitor.NoBusMatters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dels.service.monitor.IBusService;
import com.dels.utils.BasicConstants;

/**
 * 
 * @description 业务办理服务
 * @author z11595
 * @time 2016年12月22日 下午4:08:02
 */
@Service
public class BusService implements IBusService {
    @Autowired
    BusMapper cDao;

    /**
     * @description 无业务事项数
     * @return 无业务事项记录,总记录,无业务事项率,归属名称
     */
    @Override
    public List<NoBusMatters> wywsx(String type, Integer choseYear, Integer smonth, Integer emonth, List<String> cparams) {
        Calendar c = Calendar.getInstance();
        if (choseYear == null) {
            choseYear = c.get(Calendar.YEAR);
        }
        if (emonth == null) {
            emonth = choseYear == c.get(Calendar.YEAR) ? c.get(Calendar.MONTH) + 1 : 12;
        }
        List<NoBusMatters> wywsxList = new ArrayList<>();
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_SZ:
            wywsxList = cDao.szwywsx(choseYear, smonth, emonth, cparams);
            if (wywsxList != null && wywsxList.size() > 1) {
                wywsxList.addAll(wywsxzj(type, choseYear, smonth, emonth, cparams));
            }
            return wywsxList;
        case BasicConstants.BMYWGK_TYPE_DS:
            wywsxList = cDao.dswywsx(choseYear, smonth, emonth, cparams);
            if (wywsxList != null && wywsxList.size() > 1) {
                wywsxList.addAll(wywsxzj(type, choseYear, smonth, emonth, cparams));
            }
            return wywsxList;
        case BasicConstants.BMYWGK_TYPE_QX:
            return null;
        }

        return null;
    }

    /**
     * @description 无业务事项数概况
     * @return 无业务事项数:wywsxs,进驻事项数:jzsxs,无业务事项率:wywsxl
     */
    @Override
    public NoBusMatters wywsxgk(String type, Integer choseYear, Integer emonth, List<String> cparams) {
        Calendar c = Calendar.getInstance();
        if (choseYear == null) {
            choseYear = c.get(Calendar.YEAR);
        }
        if (emonth == null) {
            emonth = choseYear == c.get(Calendar.YEAR) ? c.get(Calendar.MONTH) + 1 : 12;
        }
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_SZ:
            return cDao.szwywsxgk(choseYear, emonth, cparams);
        case BasicConstants.BMYWGK_TYPE_DS:
            return cDao.dswywsxgk(choseYear, emonth, cparams);
        case BasicConstants.BMYWGK_TYPE_QX:
            return null;
        }

        return null;
    }

    /**
     * @description 业务办理详情
     * @return 名称:name,别名：sname,总数:zs,正常:zc,异常:yc
     */
    @Override
    public List<HashMap<String, Object>> ywbl(String type, Integer year, Integer smonth, Integer emonth, List<String> cparams) {
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
        List<HashMap<String, Object>> ywblList = new ArrayList<>();
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_SZ:
            ywblList = cDao.szywbl(year, smonth, emonth, cparams);
            if (ywblList != null && ywblList.size() > 1) {
                ywblList.add(ywblzj(type, year, smonth, emonth, cparams));
            }
            return ywblList;
        case BasicConstants.BMYWGK_TYPE_DS:
            ywblList = cDao.dsywbl(year, smonth, emonth, cparams);
            if (ywblList != null && ywblList.size() > 1) {
                ywblList.add(ywblzj(type, year, smonth, emonth, cparams));
            }
            return ywblList;
        case BasicConstants.BMYWGK_TYPE_QX:
            return null;
        }

        return null;
    }

    /**
     * @description 业务办理概况
     * @return 总数:zs,正常:zc,异常:yc
     */
    @Override
    public HashMap<String, Object> ywblgk(String type, Integer year, Integer smonth, Integer emonth, List<String> cparams) {
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
        case BasicConstants.BMYWGK_TYPE_SZ:
            return cDao.szywblgk(year, smonth, emonth, cparams);
        case BasicConstants.BMYWGK_TYPE_DS:
            return cDao.dsywblgk(year, smonth, emonth, cparams);
        case BasicConstants.BMYWGK_TYPE_QX:
            return null;
        }

        return null;
    }

    /**
     * @description 无业务事项详情
     * @return 按部门或地市名称查询的无业务事项列表
     */
    @Override
    public List<HashMap<String, Object>> wywsxxq(String type, Integer choseYear, Integer smonth, Integer emonth, String name) {
        Calendar c = Calendar.getInstance();
        if (choseYear == null) {
            choseYear = c.get(Calendar.YEAR);
        }
        if (emonth == null) {
            emonth = choseYear == c.get(Calendar.YEAR) ? c.get(Calendar.MONTH) + 1 : 12;
        }
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_SZ:
            return cDao.szwywsxxq(choseYear, smonth, emonth, name);
        case BasicConstants.BMYWGK_TYPE_DS:
            return cDao.dswywsxxq(choseYear, smonth, emonth, name);
        case BasicConstants.BMYWGK_TYPE_QX:
            return null;
        }
        return null;
    }

    /**
     * @description 查询异常办结
     * @return 错误码:code,归属省级/地市名称:name,错误记录总数:zs
     */
    @Override
    public List<HashMap<String, Object>> ycbj(String type, Integer year, Integer smonth, Integer emonth, String name) {
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
        case BasicConstants.BMYWGK_TYPE_SZ:
            return cDao.szycbj(year, smonth, emonth, name);
        case BasicConstants.BMYWGK_TYPE_DS:
            return cDao.dsycbj(year, smonth, emonth, name);
        case BasicConstants.BMYWGK_TYPE_QX:
            return null;
        }
        return null;
    }

    /**
     * @description 查询异常办结-详情列表
     * @return 异常办结详情明细列表
     */
    @Override
    public List<HashMap<String, Object>> ycbjxq(String type, Integer year, Integer smonth, Integer emonth, String name) {
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
            case BasicConstants.BMYWGK_TYPE_SZ:
                return cDao.szycbjxq(year, smonth, emonth, name);
            case BasicConstants.BMYWGK_TYPE_DS:
                return cDao.dsycbjxq(year, smonth, emonth, name);
            case BasicConstants.BMYWGK_TYPE_QX:
                return null;
        }
        return null;
    }

    /**
     * @description 业务办理总计
     * @return （指定省级/地市范围）的总计信息
     */
    private HashMap<String, Object> ywblzj(String type, Integer year, Integer smonth, Integer emonth, List<String> cparams) {
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
        HashMap<String, Object> map = new HashMap<>();
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_SZ:
            map = cDao.szywblzj(year, smonth, emonth, cparams);
            break;
        case BasicConstants.BMYWGK_TYPE_DS:
            map = cDao.dsywblzj(year, smonth, emonth, cparams);
            break;
        case BasicConstants.BMYWGK_TYPE_QX:
            break;
        }
        map.put("name", BasicConstants.TYPE_ZJ);
        map.put("sname", BasicConstants.TYPE_ZJ);
        return map;
    }

    /**
     * @description 无业务总计
     * @return （指定省级/地市范围）的总计信息
     */
    private List<NoBusMatters> wywsxzj(String type, Integer choseYear, Integer smonth, Integer emonth, List<String> cparams) {
        Calendar c = Calendar.getInstance();
        if (choseYear == null) {
            choseYear = c.get(Calendar.YEAR);
        }
        if (emonth == null) {
            emonth = choseYear == c.get(Calendar.YEAR) ? c.get(Calendar.MONTH) + 1 : 12;
        }
        List<NoBusMatters> list = new ArrayList<>();
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_SZ:
            list = cDao.szwywsxzj(choseYear, smonth, emonth, cparams);
            break;
        case BasicConstants.BMYWGK_TYPE_DS:
            list = cDao.dswywsxzj(choseYear, smonth, emonth, cparams);
            break;
        case BasicConstants.BMYWGK_TYPE_QX:
            break;
        }
        list.get(list.size() - 1).setName(BasicConstants.TYPE_ZJ);
        list.get(list.size() - 1).setSname(BasicConstants.TYPE_ZJ);
        return list;
    }

    @Override
    public List<HashMap<String, Object>> detail(String type, String name, Integer year, Integer smonth, Integer emonth) {
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
        case BasicConstants.BUS_TYPE_SWSBX:
            return cDao.swsbx(year, smonth, emonth, name);
        case BasicConstants.BUS_TYPE_BJZSX:
            return cDao.bjzsx(year, smonth, emonth, name);
        case BasicConstants.BUS_TYPE_WSQLCBJX:
            return cDao.wsqlcbjx(year, smonth, emonth, name);
        case BasicConstants.BUS_TYPE_BJZSS:
            return cDao.bjzss(year, smonth, emonth, name);
        case BasicConstants.BUS_TYPE_SWSBS:
            return cDao.swsbs(year, smonth, emonth, name);
        }
        return null;
    }

    /**
     * @description 数据指标统计——业务趋势图——网上办结
     * @return 所选日期范围内的网上办结量
     */
    @Override
    public List<HashMap<String, Object>>trendwsbj(String type, String startTime, String endTime, Integer area, String category, List<String> cparams) {
        switch (type) {
            case BasicConstants.BMYWGK_TYPE_QS:
                return cDao.qstrendwsbj(startTime, endTime, area);
            case BasicConstants.BMYWGK_TYPE_SZ:
                return cDao.sztrendwsbj(startTime, endTime, cparams);
            case BasicConstants.BMYWGK_TYPE_DS:
                return cDao.dstrendwsbj(startTime, endTime, cparams);
            case BasicConstants.BMYWGK_TYPE_QX:
                return cDao.qxtrendwsbj(startTime, endTime, area, category, cparams);
        }
        return null;
    }

    /**
     * @description 数据指标统计——业务趋势图
     * @return 所选日期范围内的业务量
     */
    @Override
    public List<HashMap<String, Object>>trend(String type, String startTime, String endTime, Integer area, String category, List<String> cparams) {
        switch (type) {
            case BasicConstants.BMYWGK_TYPE_QS:
                return cDao.qstrend(startTime, endTime, area);
            case BasicConstants.BMYWGK_TYPE_SZ:
                return cDao.sztrend(startTime, endTime, cparams);
            case BasicConstants.BMYWGK_TYPE_DS:
                return cDao.dstrend(startTime, endTime, cparams);
            case BasicConstants.BMYWGK_TYPE_QX:
                return cDao.qxtrend(startTime, endTime, area, category, cparams);
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> wywsxRestful(String zzjgdm, Integer year, Integer smonth, Integer emonth) {
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
        return cDao.wywsxsRestful(zzjgdm, year, smonth, emonth);
    }

}
