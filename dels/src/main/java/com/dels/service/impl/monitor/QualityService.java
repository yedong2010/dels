package com.dels.service.impl.monitor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.dels.dao.monitor.QualityMapper;
import com.dels.service.monitor.IQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dels.utils.BasicConstants;

/**
 * 
 * @description 质量监督模块服务实现类
 * @author z11595
 * @time:2016年12月22日 下午4:13:17
 */
@Service
public class QualityService implements IQualityService {
    @Autowired
    QualityMapper qDao;

    /**
     * @description 查询当前质量概况
     */
    @Override
    public HashMap<String, Object> zlgk(String type, Integer year, Integer smonth, Integer emonth, List<String> cparams) {
        Calendar c = Calendar.getInstance();
        if (year == null) {
            year = c.get(Calendar.YEAR);
        }
        if (smonth == null) {
            smonth = 1;
        }
        if (emonth == null) {
            emonth= year==c.get(Calendar.YEAR)?c.get(Calendar.MONTH) + 1:12;
        }
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_QS:
            return qDao.qszl(year, smonth, emonth);
        case BasicConstants.BMYWGK_TYPE_SZ:
            return qDao.szzl(year, smonth, emonth);
        case BasicConstants.BMYWGK_TYPE_DS:
            //2017-03-06注释质量模块数据权限
            return qDao.dszl(year, smonth, emonth, cparams);
        default:
            return qDao.qszl(year, smonth, emonth);
        }
    }

    /**
     * @description 错误分类概况
     */
    @Override
    public List<HashMap<String, Object>> cwfl(String type, Integer year, Integer smonth, Integer emonth, List<String> cparams) {
        Calendar c = Calendar.getInstance();
        if (year == null) {
            year = c.get(Calendar.YEAR);
        }
        if (smonth == null) {
            smonth = 1;
        }
        if (emonth == null) {
            emonth= year==c.get(Calendar.YEAR)?c.get(Calendar.MONTH) + 1:12;
        }
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_QS:
            return qDao.qscwfl(year, smonth, emonth);
        case BasicConstants.BMYWGK_TYPE_SZ:
            return qDao.szcwfl(year, smonth, emonth);
        case BasicConstants.BMYWGK_TYPE_DS:
            //2017-03-06注释质量模块数据权限
            return qDao.dscwfl(year, smonth, emonth, null);
        default:
            return qDao.qscwfl(year, smonth, emonth);
        }
    }

    /**
     * @description 通过地市或省级名称查询错误分类详情
     */
    @Override
    public List<HashMap<String, Object>> cwflxq(String name, Integer year, Integer smonth, Integer emonth) {
        Calendar c = Calendar.getInstance();
        if (year == null) {
            year = c.get(Calendar.YEAR);
        }
        if (smonth == null) {
            smonth = 1;
        }
        if (emonth == null) {
            emonth= year==c.get(Calendar.YEAR)?c.get(Calendar.MONTH) + 1:12;
        }
        return qDao.cwflByName(name, year, smonth, emonth);
    }

    /**
     * @description 通过地市或省级名称查询错误分类详情列表
     */
    @Override
    public List<HashMap<String, Object>> cwflxqlb(String name, Integer year, Integer smonth, Integer emonth) {
        List<HashMap<String, Object>> flist = new ArrayList<>();
        List<String> list = new ArrayList<>();
        List<HashMap<String, Object>> templist = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        if (year == null) {
            year = c.get(Calendar.YEAR);
        }
        if (smonth == null) {
            smonth = 1;
        }
        if (emonth == null) {
            emonth= year==c.get(Calendar.YEAR)?c.get(Calendar.MONTH) + 1:12;
        }
        list = qDao.cwtbByName(name, year, smonth, emonth); //先查询环节分类，即table分类
        for(int i = 0; i < list.size(); i++){
            String tableName = list.get(i);
            String[] splits = BasicConstants.CWFLTABLE_NAME.get(tableName).split("-");
            String timeName = splits[0];
            String hjName = splits[1];
            tableName = "dms.ex_gdbs_"+tableName;
            if(flist.size() < 100000){     //只查出前20W的数据量，缓解数据库和浏览器压力，增加速度
                templist = qDao.cwfllbByTable(hjName, timeName, tableName, name, year, smonth, emonth);
                flist.addAll(templist);
            }else {
                break;
            }
        }
        return flist.size()>=100000?flist.subList(0,100000):flist;
    }

    /**
     * @description 数据质量统计
     * @return 名称,总量，正确记录数，错误记录数，正确率
     */
    @Override
    public List<HashMap<String, Object>> sjzl(String type, Integer year, Integer smonth, Integer emonth, List<String> cparams) {
        Calendar c = Calendar.getInstance();
        if (year == null) {
            year = c.get(Calendar.YEAR);
        }
        if (smonth == null) {
            smonth = 1;
        }
        if (emonth == null) {
            emonth= year==c.get(Calendar.YEAR)?c.get(Calendar.MONTH) + 1:12;
        }
        List<HashMap<String, Object>> list = new ArrayList<>();
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_SZ:
            //2017-03-06注释质量模块数据权限
            list = qDao.szsjzl(year, smonth, emonth, null);
            if (list != null && list.size() > 1) {
                //2017-03-06注释质量模块数据权限
                list.addAll(sjzlzj(type, year, smonth, emonth, null));
            }
            return list;
        case BasicConstants.BMYWGK_TYPE_DS:
            //2017-03-06注释质量模块数据权限
            list = qDao.dssjzl(year, smonth, emonth, null);
            if (list != null && list.size() > 1) {
                //2017-03-06注释质量模块数据权限
                list.addAll(sjzlzj(type, year, smonth, emonth, null));
            }
            return list;
        }
        return null;
    }

    /**
     * @description 数据质量总计
     * @return （指定省级/地市范围）的总计信息
     */
    private List<HashMap<String, Object>> sjzlzj(String type, Integer year, Integer smonth, Integer emonth, List<String> cparams) {
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
        List<HashMap<String, Object>> list = new ArrayList<>();
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_SZ:
            //2017-03-06注释质量模块数据权限
            list = qDao.szsjzlzj(year, smonth, emonth, null);
            break;
        case BasicConstants.BMYWGK_TYPE_DS:
            //2017-03-06注释质量模块数据权限
            list = qDao.dssjzlzj(year, smonth, emonth, null);
            break;
        case BasicConstants.BMYWGK_TYPE_QX:
            break;
        }
        list.get(list.size() - 1).put("name", BasicConstants.TYPE_ZJ);
        list.get(list.size() - 1).put("same", BasicConstants.TYPE_ZJ);
        return list;
    }

    /**
     * @description 进驻事项数据版本——全省进驻事项数
     * @return 指定日期的全省进驻事项数
     */
    public List<HashMap<String, Object>> qsjzsxs(String choseDate) {
        List<HashMap<String, Object>> list = new ArrayList<>();
        list = qDao.qsjzsxs(choseDate);
        return list;
    }

    /**
     * @description 进驻事项数据版本——全省进驻事项数对比
     * @return 版本1和版本2的事项数差异列表
     */
    public List<HashMap<String, Object>> qsjzsxsdb(String startTime, String endTime) {
        List<HashMap<String, Object>> list = new ArrayList<>();
        list = qDao.qsjzsxsdb(startTime, endTime);
        return list;
    }

    /**
     * @description 进驻事项数据版本——其他进驻事项数（省级、地市、区县）
     * @return 对应类型指定日期的进驻事项数
     */
    public List<HashMap<String, Object>> qtjzsxs(String type, String choseDate) {
        List<HashMap<String, Object>> list = new ArrayList<>();
        switch (type) {
            case BasicConstants.BMYWGK_TYPE_SZ:
                list = qDao.szjzsxs(choseDate);
                break;
            case BasicConstants.BMYWGK_TYPE_DS:
                list = qDao.dsjzsxs(choseDate);
                break;
            case BasicConstants.BMYWGK_TYPE_QX:
                list = qDao.qxjzsxs(choseDate);
                break;
        }
        return list;
    }

    /**
     * @description 进驻事项数据版本——其他进驻事项数对比（省级、地市、区县）
     * @return 对应类型  版本1日期和版本2日期 的事项数差异列表
     */
    public List<HashMap<String, Object>> qtjzsxsdb(String type, String startTime, String endTime) {
        List<HashMap<String, Object>> list = new ArrayList<>();
        switch (type) {
            case BasicConstants.BMYWGK_TYPE_SZ:
                list = qDao.szjzsxsdb(startTime, endTime);
                break;
            case BasicConstants.BMYWGK_TYPE_DS:
                list = qDao.dsjzsxsdb(startTime, endTime);
                break;
            case BasicConstants.BMYWGK_TYPE_QX:
                list = qDao.qxjzsxsdb(startTime, endTime);
                break;
        }
        return list;
    }

    /**
     * @description 进驻部门数据版本——进驻部门数
     * @return 对应类型指定日期的进驻部门数
     */
    public List<HashMap<String, Object>> jzbms(String type, String choseDate) {
        List<HashMap<String, Object>> list = new ArrayList<>();
        switch (type) {
            case BasicConstants.BMYWGK_TYPE_QS:
                list = qDao.qsjzbms(choseDate);
                break;
            case BasicConstants.BMYWGK_TYPE_DS:
                list = qDao.dsjzbms(choseDate);
                break;
            case BasicConstants.BMYWGK_TYPE_QX:
                list = qDao.qxjzbms(choseDate);
                break;
        }
        return list;
    }

    /**
     * @description 进驻部门数据版本——进驻部门数对比
     * @return 对应类型 版本1日期和版本2日期 的进驻部门数差异列表
     */
    public List<HashMap<String, Object>> jzbmsdb(String type, String startTime, String endTime) {
        List<HashMap<String, Object>> list = new ArrayList<>();
        switch (type) {
            case BasicConstants.BMYWGK_TYPE_QS:
                list = qDao.qsjzbmsdb(startTime, endTime);
                break;
            case BasicConstants.BMYWGK_TYPE_DS:
                list = qDao.dsjzbmsdb(startTime, endTime);
                break;
            case BasicConstants.BMYWGK_TYPE_QX:
                list = qDao.qxjzbmsdb(startTime, endTime);
                break;
        }
        return list;
    }

    /**
     * @description 查询日期范围内网上办事过程数据存在缺失的列表数据。网上办事正常包括申办、受理、办结等过程
     */
    @Override
    public List<HashMap<String, Object>> dataProcess(String startTime, String endTime) {
        return qDao.szdataProcess(startTime, endTime);
    }

    /**
     * @description 查询日期范围内网上办事过程数据存在缺失的列表数据。网上办事正常包括申办、受理、办结等过程
     */
    @Override
    public List<HashMap<String, Object>> buslogic(String startTime, String endTime) {
        return qDao.szbuslogic(startTime, endTime);
    }

    /**
     * @description 查询某年01-01，到截止月日的业务量
     */
    @Override
    public List<HashMap<String, Object>> ywlaccount(String startTime, String dateTime) {
        return qDao.ywlaccount(startTime, dateTime);
    }
}
