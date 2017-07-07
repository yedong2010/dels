package com.dels.service.impl;/**
 * Created by l13595 on 2017/1/16.
 */

import com.dels.dao.PlanMapper;
import com.dels.model.sys.PlanInfo;
import com.dels.service.IPlanService;
import com.dels.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: l13595
 * @date:2017-01-16
 */
@Service
public class PlanService implements IPlanService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PlanMapper dao;

    public List<Map<String,String>> getPlanList(){
        try{
            return dao.getPlanList();
        }catch (Exception e){
            logger.info("查询日志出错：");
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String,String>> getPlanInfoList(String planId){
        try{
            return dao.getPlanInfoList(planId);
        }catch (Exception e){
            logger.info("查询方案详细信息出错：");
            e.printStackTrace();
            return null;
        }
    }

    public String deletePlan(PlanInfo planInfo){
        try {
            dao.deletePlan(planInfo.getId());
            dao.deletePlanInfoByPlanId(planInfo.getId());
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "删除方案失败，请联系系统管理员！";
        }
    }

    public String deletePlanInfo(String id){
        try{
            dao.deletePlanInfo(id);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "删除方案详情失败，请联系系统管理员！";
        }
    }

    public String insertPlan(PlanInfo plan){
        try {
            if(!checkPlanName(plan.getName())){
                return "方案名称已存在，请不要重复保存";
            }
            plan.setUserName(Utils.getCurUserName());
            dao.insertPlan(plan);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "增加方案失败，请联系系统管理员！";
        }
    }

    public String insertPlanInfo(PlanInfo plan){
        try {
            dao.insertPlanInfo(plan);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "删除失败，请联系系统管理员！";
        }
    }

    public String editPlan(PlanInfo plan){
        try{
            dao.editPlan(plan);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "编辑方案失败，请联系系统管理员！";
        }
    }

    public String editPlanInfo(PlanInfo plan){
        try{
            dao.editPlanInfo(plan);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "编辑方案详情失败，请联系系统管理员！";
        }
    }

    public Map<String,Object> getPlan(String name){
        try{
            Map<String,Object> rep = new HashMap<>();
            List<Map<String,Object>> list = dao.getPlan(name);
            if(null!=list&&list.size()>0){
                for(Map<String,Object>p:list){
                    rep.put(String.valueOf(p.get("colName")),p.get("passLevel"));
                }
            }
            return rep;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Boolean checkPlanName(String name){
        String planName = dao.getPlanByName(name);
        if(null==planName){
            return true;
        }else{
            return false;
        }
    }


}
