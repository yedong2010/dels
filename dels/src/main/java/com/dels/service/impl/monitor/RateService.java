package com.dels.service.impl.monitor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dels.dao.monitor.RateMapper;
import com.dels.dao.monitor.TotalityMapper;
import com.dels.service.monitor.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dels.model.monitor.CommonModel;
import com.dels.utils.BasicConstants;

/**
 * @author z11595
 * @description 核心统计服务
 * @time 2016年12月22日 下午4:08:02
 */
@Service
public class RateService implements IRateService {
    @Autowired
    RateMapper cDao;
    @Autowired
    TotalityMapper tDao;

    /**
     * @return 三率一数包括行政审批和公共服务业务量详情
     * @description 按照类型查询三率一数详细数据
     */
    @Override
    public List<CommonModel> slys(String type, Integer year, Integer smonth, Integer emonth, String area, String category, List<String> cparams) {
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
        List<CommonModel> slysList = new ArrayList<>();
        if (year != 2016) {
            switch (type) {
            case BasicConstants.BMYWGK_TYPE_QS:
                slysList = qsslys(type, year, smonth, emonth, area);
                return slysList;
            case BasicConstants.BMYWGK_TYPE_SZ:
                if(year == c.get(Calendar.YEAR) && smonth == 1 && emonth == c.get(Calendar.MONTH) + 1){
                    slysList = cDao.szslysStatic(cparams);
                }else{
                    slysList = cDao.szslys(year, smonth, emonth, cparams);
                    if (slysList != null && slysList.size() > 1) {
                        slysList.addAll(slyszj(type, year, smonth, emonth, cparams, null, null));
                    }
                }
                return slysList;
            case BasicConstants.BMYWGK_TYPE_DS:
                if(year == c.get(Calendar.YEAR) && smonth == 1 && emonth == c.get(Calendar.MONTH) + 1){
                    slysList = cDao.dsslysStatic(cparams);
                    if (slysList != null && slysList.size() > 1) {
                        slysList.addAll(cDao.dsslyszjStatic(cparams));
                    }
                }else{
                    slysList = cDao.dsslys(year, smonth, emonth, cparams);
                    if (slysList != null && slysList.size() > 1) {
                        slysList.addAll(slyszj(type, year, smonth, emonth, cparams, null, null));
                    }
                }
                return slysList;
            case BasicConstants.BMYWGK_TYPE_QX:
                slysList = cDao.qxslys(year, smonth, emonth, cparams, category, area);
                if (slysList != null && slysList.size() > 1) {
                    slysList.addAll(slyszj(type, year, smonth, emonth, cparams, category, area));
                }
                return slysList;
            }
        } else {
            slysList = cDao.previousData(type, year, cparams);
            return slysList;
        }

        return null;
    }

    /**
     * @return 指定省级/地市名称的网上全流程事项列表
     * @description 网上全流程事项数详情
     */
    @Override
    public List<HashMap<String, Object>> wsqlcsxsxq(String type, Integer ifsxzs, String name, Integer choseYear, Integer emonth) {
        Calendar c = Calendar.getInstance();
        if (choseYear == null) {
            choseYear = c.get(Calendar.YEAR);
        }
        if (emonth == null) {
            emonth = choseYear == c.get(Calendar.YEAR) ? c.get(Calendar.MONTH) + 1 : 12;
        }
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_SZ:
            return cDao.szwsqlcsxsxq(ifsxzs, name, choseYear, emonth);
        case BasicConstants.BMYWGK_TYPE_DS:
            return cDao.dswsqlcsxsxq(ifsxzs, name, choseYear, emonth);
        case BasicConstants.BMYWGK_TYPE_QX:
            return cDao.qxwsqlcsxsxq(ifsxzs, name, choseYear, emonth);
        }
        return null;
    }

    /*
     * 全省三率一数总计
     */
    private CommonModel qsslyszj(String type, Integer year, Integer smonth, Integer emonth, List<CommonModel> szzjList, List<CommonModel> dszjList, List<CommonModel> qxzjList, String area) {
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
        int pdcs, pdcs0, pdcs1, pdcs2, pdcs3;
        double zb0, zb1, zb2, zb3;
        CommonModel szModel = szzjList.get(0);
        CommonModel dsModel = dszjList.get(0);
        CommonModel qxModel = qxzjList.get(0);
        HashMap<String, Object> slMap = tDao.qssl(year, smonth, emonth, area);
        /*List<HashMap<String, Object>> ysList = tDao.qsys(type, null, null, area);
        pdcs0 = Integer.parseInt(ysList.get(0).get("y").toString());
        pdcs1 = Integer.parseInt(ysList.get(1).get("y").toString());
        pdcs2 = Integer.parseInt(ysList.get(2).get("y").toString());
        pdcs3 = Integer.parseInt(ysList.get(3).get("y").toString());
        pdcs = pdcs0 + pdcs1 + pdcs2 + pdcs3;
        zb0 = (double) pdcs0 * 100 / pdcs;
        zb1 = (double) pdcs1 * 100 / pdcs;
        zb2 = (double) pdcs2 * 100 / pdcs;
        zb3 = (double) pdcs3 * 100 / pdcs;*/
        CommonModel qszjModel = new CommonModel();
        qszjModel.setName(BasicConstants.SLYSZJ_TYPE_ALL_ZJ);
        qszjModel.setSname(BasicConstants.SLYSZJ_TYPE_ALL_ZJ);
        qszjModel.setWsqlcsxs(String.valueOf(Integer.parseInt(szModel.getWsqlcsxs()) + Integer.parseInt(dsModel.getWsqlcsxs()) + Integer.parseInt(qxModel.getWsqlcsxs())));
        qszjModel.setSxzs(String.valueOf(Integer.parseInt(szModel.getSxzs()) + Integer.parseInt(dsModel.getSxzs()) + Integer.parseInt(qxModel.getSxzs())));
        qszjModel.setShfwsxzs(String.valueOf(Integer.parseInt(szModel.getShfwsxzs()) + Integer.parseInt(dsModel.getShfwsxzs()) + Integer.parseInt(qxModel.getShfwsxzs())));
        qszjModel.setSwblzs(String.valueOf(Integer.parseInt(szModel.getSwblzs()) + Integer.parseInt(dsModel.getSwblzs()) + Integer.parseInt(qxModel.getSwblzs())));
        qszjModel.setWsqlcbjzs(String.valueOf(Integer.parseInt(szModel.getWsqlcbjzs()) + Integer.parseInt(dsModel.getWsqlcbjzs()) + Integer.parseInt(qxModel.getWsqlcbjzs())));
        qszjModel.setBjzs(String.valueOf(Integer.parseInt(szModel.getBjzs()) + Integer.parseInt(dsModel.getBjzs()) + Integer.parseInt(qxModel.getBjzs())));
        qszjModel.setWsqlcbll(slMap.get("wsqlcblv").toString());
        qszjModel.setSwbll(slMap.get("swblv").toString());
        qszjModel.setWsbjl(slMap.get("wsbjl").toString());

        qszjModel.setPdcs(Integer.parseInt(slMap.get("pdcs").toString()));
        qszjModel.setPdcs0(Integer.parseInt(slMap.get("pdcs0").toString()));
        qszjModel.setPdcs1(Integer.parseInt(slMap.get("pdcs1").toString()));
        qszjModel.setPdcs2(Integer.parseInt(slMap.get("pdcs2").toString()));
        qszjModel.setPdcs3(Integer.parseInt(slMap.get("pdcs3").toString()));

        qszjModel.setZb0(String.valueOf(Double.parseDouble(slMap.get("zb0").toString())));
        qszjModel.setZb1(String.valueOf(Double.parseDouble(slMap.get("zb1").toString())));
        qszjModel.setZb2(String.valueOf(Double.parseDouble(slMap.get("zb2").toString())));
        qszjModel.setZb3(String.valueOf(Double.parseDouble(slMap.get("zb3").toString())));

        qszjModel.setShfwswblzs((String.valueOf(Integer.parseInt(szModel.getShfwswblzs()) + Integer.parseInt(dsModel.getShfwswblzs()) + Integer.parseInt(qxModel.getShfwswblzs()))));
        qszjModel.setShfwbjzs((String.valueOf(Integer.parseInt(szModel.getShfwbjzs()) + Integer.parseInt(dsModel.getShfwbjzs()) + Integer.parseInt(qxModel.getShfwbjzs()))));

        return qszjModel;
    }

    /*
     * 汇总三率一数总计，包括全省级、全地市、全珠三角地市、全粤东西北地市、全区县、全珠三角区县、全粤东西北区县、全省
     */
    private List<CommonModel> qsslys(String type, Integer year, Integer smonth, Integer emonth, String area) {
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
        List<CommonModel> slysList = new ArrayList<>();
        List<String> zhusj = new ArrayList<String>() {
            {
                add("广州市");
                add("深圳市");
                add("珠海市");
                add("佛山市");
                add("顺德区");
                add("江门市");
                add("肇庆市");
                add("惠州市");
                add("东莞市");
                add("中山市");
            }
        };
        List<String> yuedxb = new ArrayList<String>() {
            {
                add("韶关市");
                add("汕头市");
                add("湛江市");
                add("茂名市");
                add("梅州市");
                add("汕尾市");
                add("河源市");
                add("阳江市");
                add("清远市");
                add("潮州市");
                add("揭阳市");
                add("云浮市");
            }
        };
        List<CommonModel> szzjList = slyszj(BasicConstants.BMYWGK_TYPE_SZ, year, smonth, emonth, null, null, null);
        List<CommonModel> dszjList = slyszj(BasicConstants.BMYWGK_TYPE_DS, year, smonth, emonth, null, null, null);
        List<CommonModel> qxzjList = slyszj(BasicConstants.BMYWGK_TYPE_QX, year, smonth, emonth, null, null, area);

        slysList.addAll(szzjList);
        slysList.addAll(dszjList);
        slysList.addAll(slyszj(BasicConstants.BMYWGK_TYPE_DS, year, smonth, emonth, zhusj, null, null));
        slysList.addAll(slyszj(BasicConstants.BMYWGK_TYPE_DS, year, smonth, emonth, yuedxb, null, null));
        slysList.addAll(qxzjList);
        slysList.addAll(slyszj(BasicConstants.BMYWGK_TYPE_QX, year, smonth, emonth, zhusj, BasicConstants.XZQH_NUMBER_ZSJ, area));
        // 全粤东西北区县包含肇庆
        yuedxb.add("肇庆市");
        slysList.addAll(slyszj(BasicConstants.BMYWGK_TYPE_QX, year, smonth, emonth, yuedxb, BasicConstants.XZQH_NUMBER_YDXB, area));
        slysList.add(qsslyszj(type, year, smonth, emonth, szzjList, dszjList, qxzjList, area));
        if (slysList.size() > 0) {
            slysList.get(0).setName(BasicConstants.SLYSZJ_TYPE_ALL_SZ);
            slysList.get(0).setSname(BasicConstants.SLYSZJ_TYPE_ALL_SZ);
        }
        if (slysList.size() > 1) {
            slysList.get(1).setName(BasicConstants.SLYSZJ_TYPE_ALL_DS);
            slysList.get(1).setSname(BasicConstants.SLYSZJ_TYPE_ALL_DS);
        }
        if (slysList.size() > 2) {
            slysList.get(2).setName(BasicConstants.SLYSZJ_TYPE_ALL_DS_ZSJ);
            slysList.get(2).setSname(BasicConstants.SLYSZJ_TYPE_ALL_DS_ZSJ);
        }
        if (slysList.size() > 3) {
            slysList.get(3).setName(BasicConstants.SLYSZJ_TYPE_ALL_DS_YDXB);
            slysList.get(3).setSname(BasicConstants.SLYSZJ_TYPE_ALL_DS_YDXB);
        }
        if (slysList.size() > 4) {
            slysList.get(4).setName(BasicConstants.SLYSZJ_TYPE_ALL_QX);
            slysList.get(4).setSname(BasicConstants.SLYSZJ_TYPE_ALL_QX);
        }
        if (slysList.size() > 5) {
            slysList.get(5).setName(BasicConstants.SLYSZJ_TYPE_ALL_QX_ZSJ);
            slysList.get(5).setSname(BasicConstants.SLYSZJ_TYPE_ALL_QX_ZSJ);
        }
        if (slysList.size() > 6) {
            slysList.get(6).setName(BasicConstants.SLYSZJ_TYPE_ALL_QX_YDXB);
            slysList.get(6).setSname(BasicConstants.SLYSZJ_TYPE_ALL_QX_YDXB);
        }
        return slysList;
    }

    /**
     * @param category
     * @return （指定省级/地市范围）的总计信息
     * @description 三率一数总计
     */
    private List<CommonModel> slyszj(String type, Integer year, Integer smonth, Integer emonth, List<String> cparams, String category, String area) {
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
        Map<String, Object> qsslMap = new HashMap<>();
        List<CommonModel> commonModelList = new ArrayList<>();
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_SZ:
            commonModelList = cDao.szslyszj(year, smonth, emonth, cparams);
            break;
        case BasicConstants.BMYWGK_TYPE_DS:
            commonModelList = cDao.dsslyszj(year, smonth, emonth, cparams);
            break;
        case BasicConstants.BMYWGK_TYPE_QX:
            commonModelList = cDao.qxslyszj(year, smonth, emonth, cparams, category, area);
            break;
        }
        if (commonModelList != null && commonModelList.size() > 0) {
            commonModelList.get(commonModelList.size() - 1).setName(BasicConstants.TYPE_ZJ);
            commonModelList.get(commonModelList.size() - 1).setSname(BasicConstants.TYPE_ZJ);
        }
        return commonModelList;
    }

    /**
     * @return （指定省级/地市范围）的总计信息
     * @description 数据质量总计为下载
     */
    @Override
    public List<CommonModel> slys_down(String type, Integer year, Integer smonth, Integer emonth, String category, String area, List<String> cparams) {
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
        List<CommonModel> slysList = new ArrayList<>();
        if (year != 2016) {
            switch (type) {
            case BasicConstants.BMYWGK_TYPE_QS:
                slysList = qsslys(type, year, smonth, emonth, area);
                return slysList;
            case BasicConstants.BMYWGK_TYPE_SZ:
                slysList = cDao.szslysdown(year, smonth, emonth, cparams);
                if (slysList != null && slysList.size() > 1) {
                    slysList.addAll(slyszj(type, year, smonth, emonth, cparams, null, null));
                }
                return slysList;
            case BasicConstants.BMYWGK_TYPE_DS:
                slysList = cDao.dsslysdown(year, smonth, emonth, cparams);
                if (slysList != null && slysList.size() > 1) {
                    slysList.addAll(slyszj(type, year, smonth, emonth, cparams, null, null));
                }
                return slysList;
            case BasicConstants.BMYWGK_TYPE_QX:
                slysList = cDao.qxslysdown(year, smonth, emonth, cparams, category, area);
                if (slysList != null && slysList.size() > 1) {
                    slysList.addAll(slyszj(type, year, smonth, emonth, cparams, category, area));
                }
                return slysList;
            }
        }else {
            slysList = cDao.previousData(type, year, cparams);
            return slysList;
        }
        return null;
    }

    /**
     * @return （省级/地市/区县范围）的总计信息
     * @description 事项进驻总计
     */
    private HashMap<String, Object> sxjzzj(String type, String category, String area, Integer choseYear, Integer emonth, List<String> cparams) {
        HashMap<String, Object> map = new HashMap<>();
        Calendar c = Calendar.getInstance();
        if (choseYear == null) {
            choseYear = c.get(Calendar.YEAR);
        }
        if (emonth == null) {
            emonth = choseYear == c.get(Calendar.YEAR) ? c.get(Calendar.MONTH) + 1 : 12;
        }
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_SZ:
            map = cDao.szsxjzzj(cparams, choseYear, emonth);
            break;
        case BasicConstants.BMYWGK_TYPE_DS:
            map = cDao.dssxjzzj(cparams, choseYear, emonth);
            break;
        case BasicConstants.BMYWGK_TYPE_QX:
            map = cDao.qxsxjzzj(cparams, category, area, choseYear, emonth);
            break;
        }
        map.put("zzjgdm", BasicConstants.TYPE_ZJ);
        map.put("zzjgmc", BasicConstants.TYPE_ZJ);
        map.put("zzjgjc", BasicConstants.TYPE_ZJ);
        map.put("sjmc", BasicConstants.TYPE_ZJ);
        map.put("xzqhqc", BasicConstants.TYPE_ZJ);
        return map;
    }

    /**
     * @description 事项进驻
     * @return:
     */
    @Override
    public List<HashMap<String, Object>> sxjz(String type, String category, String area, Integer choseYear, Integer emonth, List<String> cparams) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        if (choseYear == null) {
            choseYear = c.get(Calendar.YEAR);
        }
        if (emonth == null) {
            emonth = choseYear == c.get(Calendar.YEAR) ? c.get(Calendar.MONTH) + 1 : 12;
        }
        List<HashMap<String, Object>> sxjzList = new ArrayList<>();
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_SZ:
            sxjzList = cDao.szsxjz(cparams, choseYear, emonth, year);
            if (sxjzList != null && sxjzList.size() > 1) {
                sxjzList.add(sxjzzj(type, null, null, choseYear, emonth, cparams));
            }
            return sxjzList;
        case BasicConstants.BMYWGK_TYPE_DS:
            sxjzList = cDao.dssxjz(cparams, choseYear, emonth, year);
            if (sxjzList != null && sxjzList.size() > 1) {
                sxjzList.add(sxjzzj(type, null, null,choseYear, emonth, cparams));
            }
            return sxjzList;
        case BasicConstants.BMYWGK_TYPE_QX:
            sxjzList = cDao.qxsxjz(cparams, category, area, choseYear, emonth, year);
            if (sxjzList != null && sxjzList.size() > 1) {
                sxjzList.add(sxjzzj(type, category, area,choseYear, emonth, cparams));
            }
            return sxjzList;
        }
        return null;
    }

    /**
     * @return （指定省级/地市/区县范围）
     * @description 事项进驻下载
     */
    @Override
    public List<HashMap<String, Object>> sxjz_down(String type, String category, String area, Integer choseYear, Integer emonth, List<String> cparams) {
        List<HashMap<String, Object>> sxjzList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        if (choseYear == null) {
            choseYear = c.get(Calendar.YEAR);
        }
        if (emonth == null) {
            emonth = choseYear == c.get(Calendar.YEAR) ? c.get(Calendar.MONTH) + 1 : 12;
        }
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_SZ:
            sxjzList = cDao.szsxjzdown(cparams, choseYear, emonth);
            if (sxjzList != null && sxjzList.size() > 1) {
                sxjzList.add(sxjzzj(type, null, null, choseYear, emonth, cparams));
            }
            return sxjzList;
        case BasicConstants.BMYWGK_TYPE_DS:
            sxjzList = cDao.dssxjzdown(cparams, choseYear, emonth);
            if (sxjzList != null && sxjzList.size() > 1) {
                sxjzList.add(sxjzzj(type, null, null, choseYear, emonth, cparams));
            }
            return sxjzList;
        case BasicConstants.BMYWGK_TYPE_QX:
            sxjzList = cDao.qxsxjzdown(cparams, category, area, choseYear, emonth);
            if (sxjzList != null && sxjzList.size() > 1) {
                sxjzList.add(sxjzzj(type, category, area, choseYear, emonth, cparams));
            }
            return sxjzList;
        }
        return null;
    }

    /**
     * @description 事项进驻已进驻部门详情
     * @return:
     */
    @Override
    public List<HashMap<String, Object>> sxjzbmxq(String type, String name) {
        switch (type) {
        /*
         * case BasicConstants.BMYWGK_TYPE_SZ: return cDao.szwsqlcsxsxq(name);
         */
        case BasicConstants.BMYWGK_TYPE_DS:
            return cDao.dssxjzbmxq(name);
        case BasicConstants.BMYWGK_TYPE_QX:
            return cDao.qxsxjzbmxq(name);
        }
        return null;
    }

    /**
     * @description 事项进驻已进驻部门详情——下载
     * @return:
     */
    @Override
    public List<HashMap<String, Object>> sxjzbmxq_down(String type, String name) {
        switch (type) {
        /*
         * case BasicConstants.BMYWGK_TYPE_SZ: return cDao.szwsqlcsxsxq(name);
         */
        case BasicConstants.BMYWGK_TYPE_DS:
            return cDao.dssxjzbmxqdown(name);
        case BasicConstants.BMYWGK_TYPE_QX:
            return cDao.qxsxjzbmxqdown(name);
        }
        return null;
    }

    /**
     * @description 事项进驻已进驻事项详情
     * @return:
     */
    @Override
    public List<HashMap<String, Object>> sxjzsxxq(String type, String name, String sxlxmc, Integer year, Integer emonth) {
        List<HashMap<String, Object>> sxjzList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        if (year == null) {
            year = c.get(Calendar.YEAR);
        }
        if (emonth == null) {
            emonth = year == c.get(Calendar.YEAR) ? c.get(Calendar.MONTH) + 1 : 12;
        }
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_SZ:
            return cDao.szsxjzsxxq(name, sxlxmc, year, emonth);
        case BasicConstants.BMYWGK_TYPE_DS:
            return cDao.dssxjzsxxq(name, sxlxmc, year, emonth);
        case BasicConstants.BMYWGK_TYPE_QX:
            return cDao.qxsxjzsxxq(name, sxlxmc, year, emonth);
        }
        return null;
    }

    /**
     * @return 事项详情
     * @description 事项进驻已进驻事项详情——下载
     */
    @Override
    public List<HashMap<String, Object>> sxjzsxxq_down(String type, String name, String sxlxmc, Integer year, Integer emonth) {
        List<HashMap<String, Object>> sxjzList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        if (year == null) {
            year = c.get(Calendar.YEAR);
        }
        if (emonth == null) {
            emonth = year == c.get(Calendar.YEAR) ? c.get(Calendar.MONTH) + 1 : 12;
        }
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_SZ:
            return cDao.szsxjzsxxqdown(name, sxlxmc, year, emonth);
        case BasicConstants.BMYWGK_TYPE_DS:
            return cDao.dssxjzsxxqdown(name, sxlxmc, year, emonth);
        case BasicConstants.BMYWGK_TYPE_QX:
            return cDao.qxsxjzsxxqdown(name, sxlxmc, year, emonth);
        }
        return null;
    }

    /**
     * @return 事项详情
     * @description 事项进驻按照省级|部门名称、跑动次数
     */
    @Override
    public List<HashMap<String, Object>> sxjzBypd(String type, String pdcs, String name, Integer choseYear, Integer emonth) {
        Calendar c = Calendar.getInstance();
        if (choseYear == null) {
            choseYear = c.get(Calendar.YEAR);
        }
        if (emonth == null) {
            emonth = choseYear == c.get(Calendar.YEAR) ? c.get(Calendar.MONTH) + 1 : 12;
        }
        switch (type) {
        case BasicConstants.BMYWGK_TYPE_SZ:
            return cDao.szsxjzBypd(pdcs, name, choseYear, emonth);
        case BasicConstants.BMYWGK_TYPE_DS:
            return cDao.dssxjzBypd(pdcs, name, choseYear, emonth);
        case BasicConstants.BMYWGK_TYPE_QX:
            return cDao.qxsxjzBypd(pdcs, name, choseYear, emonth);
        }
        return null;
    }

    /**
     * @return 数据指标（三率一数简化版）
     * @description 通过年份，查询三率一数简化版
     */
    @Override
    public List<HashMap<String, Object>> simpleRate(String type, Integer choseYear, Integer smonth, Integer emonth) {
        Calendar c = Calendar.getInstance();
        if (choseYear == null) {
            choseYear = c.get(Calendar.YEAR);
        }
        if (smonth == null) {
            smonth = 1;
        }
        if (emonth == null) {
            emonth = choseYear == c.get(Calendar.YEAR) ? c.get(Calendar.MONTH) + 1 : 12;
        }
        switch (type) {
            case BasicConstants.BMYWGK_TYPE_SZ:
                return cDao.szsimpleRate(choseYear, smonth, emonth);
            case BasicConstants.BMYWGK_TYPE_DS:
                return cDao.dssimpleRate(choseYear, smonth, emonth);
            case BasicConstants.BMYWGK_TYPE_QX:
                return cDao.qxsimpleRate(choseYear, smonth, emonth);
        }
        return null;
    }

    /**
     * @description 查询日期范围内事项总数、总业务量、网上全流程办理率、上网办理率、网上办结率、到现场次数、审批时间压缩率、总分数等。
     */
    @Override
    public List<HashMap<String, Object>> rateRank(String startTime, String endTime) {
        return cDao.szrateRank(startTime, endTime);
    }

    /**
     * @description 查询日期范围内业务逻辑异常数、业务过程异常数、业务总量异常数、数据内容异常数、数据规范异常数
     */
    @Override
    public List<HashMap<String, Object>> dataAbnormal(String startTime, String endTime) {
        return cDao.szdataAbnormal(startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> bmywzl(String zzjgdm, Integer year, Integer smonth, Integer emonth) {
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
        return cDao.bmywzl(zzjgdm, year, smonth, emonth);
    }

    @Override
    public Integer sumBMYWZL(String zzjgdm, Integer year, Integer smonth, Integer emonth) {
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
        return cDao.sumBMYWZL(zzjgdm, year, smonth, emonth);
    }

    @Override
    public List<Map<String, Object>> bmywnlRestful(String zzjgdm, Integer year, Integer smonth, Integer emonth) {
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
        return cDao.bmywnl(zzjgdm, year, smonth, emonth);
    }
}
