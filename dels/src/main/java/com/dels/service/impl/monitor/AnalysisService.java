package com.dels.service.impl.monitor;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dels.dao.monitor.AnalysisMapper;
import com.dels.service.monitor.IAnalysisService;

/**
 * @author m13624
 * @description 数据分析
 * @time 2017年03月06日 14:15:02
 */
@Service
public class AnalysisService implements IAnalysisService {
    @Autowired
    AnalysisMapper cDao;

    /**
     * @return 按业务量从小到大排序的事项数办理时长信息
     * @description 给的数据当前默认是省级部门的
     */
    @Override
    public List<HashMap<String, Object>> analysistime(Integer choseYear) {
        Calendar c = Calendar.getInstance();
        if (choseYear == null) {
            choseYear = c.get(Calendar.YEAR);
        }
        return cDao.szanalysistime(choseYear);
    }

    /**
     * @return 按业务量从小到大排序的事项数审批环节次数信息
     * @description 给的数据当前默认是省级部门的
     */
    @Override
    public List<HashMap<String, Object>> analysisflow(Integer choseYear) {
        Calendar c = Calendar.getInstance();
        if (choseYear == null) {
            choseYear = c.get(Calendar.YEAR);
        }
        return cDao.szanalysisflow(choseYear);
    }

    /**
     * @description 查询年份的省级行政审批事项数、实际全流程事项数、申报全流程事项数、无业务全流程事项数
     */
    @Override
    public List<HashMap<String, Object>> analysisprocess(Integer choseYear) {
        Calendar c = Calendar.getInstance();
        if (choseYear == null) {
            choseYear = c.get(Calendar.YEAR);
        }
        return cDao.szanalysisprocess(choseYear);
    }

    /**
     * @description 查询日期范围内的业务量、业务办理能力、被认可程度、投诉举报和满意度评价
     */
    @Override
    public List<HashMap<String, Object>> analysisreport(String startTime, String endTime) {
        return cDao.szanalysisreport(startTime, endTime);
    }

    /**
     * @description 查询日期范围内的业务受理、办结、事项无业务情况、服务积存量
     */
    @Override
    public List<HashMap<String, Object>> analysisbusiness(String startTime, String endTime) {
        return cDao.szanalysisbusiness(startTime, endTime);
    }

    /**
     * @description 查询日期范围内同一事项在不同市、区、街道三级业务办理时长，分析各市、区、街道三级业务流程单一环节平均时长、最长时长以及最短时长
     */
    @Override
    public List<HashMap<String, Object>> analysisBusTime(String startTime, String endTime) {
        return cDao.dsanalysisBusTime(startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> bmbrkcdRestful(String zzjgdm, String startTime, String endTime) {
        return cDao.bmbrkcdForRestful(zzjgdm, startTime, endTime);
    }
}
