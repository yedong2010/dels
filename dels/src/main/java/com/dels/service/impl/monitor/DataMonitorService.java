package com.dels.service.impl.monitor;

import com.dels.dao.monitor.DataMonitorMapper;
import com.dels.model.monitor.DataMonitorModel;
import com.dels.service.monitor.IDataMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by l13595 on 2017/3/14.
 */
@Service
public class DataMonitorService implements IDataMonitorService {

    @Autowired
    DataMonitorMapper dao;


    @Override
    public Map<String, Object> findBZWInfo(DataMonitorModel entity) {
        Map<String,Object> rep = dao.findAllRates(entity);
        return rep;
    }

    @Override
    public List<Map<String, Object>> findBZWLsit(DataMonitorModel entity) {
        DateFormat df = new SimpleDateFormat("yyyy-MM");

        try {
            if(null != entity.getStartTime()){
                Date startDate = df.parse(entity.getStartTime());
                entity.setStartTime(df.format(startDate));
            }

            if (null != entity.getEndTime()){
                Date endtDate = df.parse(entity.getEndTime());
                entity.setEndTime(df.format(endtDate));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        List<Map<String,Object>> list = dao.findListByModel(entity);
        return list;
    }

    @Override
    public Map<String, String> findTimeParam(String time) {
        DateFormat df = new SimpleDateFormat("yyyy-MM");
        Map<String,String> param = new HashMap<>();
        try {
            Date d = df.parse(time);

            Calendar a = Calendar.getInstance();
            Calendar b = Calendar.getInstance();
            a.setTime(d);
            b.setTime(d);
            b.add(Calendar.YEAR, -1);

            String lastMonth = df.format(a.getTime());
            String lastYear = df.format(b.getTime());
            a.add(Calendar.MONTH, -1);
            String lastTowMonth = df.format(a.getTime());

            param.put("lastMonth",lastMonth);
            param.put("lastTowMonth",lastTowMonth);
            param.put("lastYear",lastYear);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return param;

    }

    @Override
    public Map<String, Object> findSumByTime(String param) {
        if(null != param && !"".equals(param)){
            return dao.findSumByTime(param);
        }else{
            return null;
        }
    }

    @Override
    public List<Map<String,Object>> findHBTB(Map<String, String> param) {
        return dao.findHBTB(param);
    }

    @Override
    public List<Map<String, Object>> findSJJC(Map<String, Object> param) {
        List<Map<String, Object>> list = dao.findSJJC(param);
        return list;
    }
}
