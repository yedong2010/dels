package com.dels.service.impl;/**
 * Created by h3c on 2017/1/4.
 */

import com.dels.model.sys.OperateLog;
import com.dels.dao.OperateLogMapper;
import com.dels.service.IOperateLogService;
import com.dels.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 审计日志
 * @author: liuchengjie 13595
 * @date:2017-01-04
 */
@Service
public class OperateLogService implements IOperateLogService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OperateLogMapper dao;

    public void insertLog(String operation){
        OperateLog log = new OperateLog();
        log.setUser_name(Utils.getCurUserName());
        log.setId(Utils.getUuid());
        log.setOperation(operation);
        try{
            dao.insertLog(log);
        }catch (Exception e){
            logger.info("插入操作日志错误："+e.getMessage());
        }

    }

    public  void saveLog(OperateLog log){
        try{
            dao.insertLog(log);
        }catch(Exception e){
            logger.info("保存操作日志错误："+e.getMessage());
        }

    }


    public List<Map<String,Object>> getLogList(Map<String,Object> param){
        try{
            return  dao.getLogList(param);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int countLogList(Map<String,Object> param){
        try{
            return dao.countLogList(param);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Integer countSum(String startTime, String endTime) {
        try{
            Integer i = dao.countSum(startTime, endTime);
            if(null != i){
                return i;
            }else{
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Integer countLogin(String startTime, String endTime) {
        try{
            Integer i = dao.countLogin(startTime, endTime);
            if(null != i){
                return i;
            }else{
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int countUser(String startTime, String endTime) {
        try{
            return dao.countUser(startTime, endTime);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Map<String, Object>> countPerUser(String startTime, String endTime) {
        try{
            return dao.countPerUser(startTime, endTime);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public int countRole(String startTime, String endTime) {
        try{
            return dao.countRole(startTime, endTime);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Map<String, Object>> countPerRole(String startTime, String endTime) {
        try{
            return dao.countPerRole(startTime, endTime);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}
