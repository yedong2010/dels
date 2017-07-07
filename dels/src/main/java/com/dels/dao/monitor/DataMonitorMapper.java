package com.dels.dao.monitor;

import com.dels.model.monitor.DataMonitorModel;

import java.util.List;
import java.util.Map;

/**
 * Created by l13595 on 2017/3/14.
 */
public interface DataMonitorMapper {

    public List<Map<String,Object>> findListByModel(DataMonitorModel entity);

    public Map<String,Object> findAllRates(DataMonitorModel entity);

    public Map<String,Object> findSumByTime(String blsj);

    public List<Map<String,Object>> findHBTB(Map<String,String> param);

    public List<Map<String,Object>> findSJJC(Map<String,Object> param);

}
