package com.dels.controller.sysMng;/**
 * Created by l13595 on 2017/1/16.
 */

import com.dels.model.sys.PlanInfo;
import com.dels.service.IPlanService;
import com.dels.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 方案管理
 * @author: l13595
 * @date:2017-01-16
 */
@RestController
@RequestMapping("/sysMng/")
public class PlanController {

    @Autowired
    IPlanService service;

    /**
     * @description:方案管理
     * @author: l13595
     * @param:
     * @return:方案列表
     * @time: 2017/1/16 11:32
     */
    @RequestMapping(value = "planList")
    public List<Map<String,String>> getPlanList(){
        List<Map<String,String>> list = service.getPlanList();
        return list;
    }

    /**
     * @description:方案详情
     * @author: l13595
     * @param: planId
     * @return:方案详情列表
     * @time: 2017/1/16 16:40
     */
    @RequestMapping(value = "planInfoList")
    public List<Map<String,String>> getPlanInflList(@RequestParam(value="planId") String planId){
        return service.getPlanInfoList(planId);
    }

    /**
     * @description:删除方案
     * @author: l13595
     * @param planInfo: 方案id
     * @return:返回操作结果标识：ok-删除成功，其他-删除失败的提示信息
     * @time: 2017/1/16 16:50
     */
    @RequestMapping(value = "deletePlan")
    public Map<String, String> deletePlan(PlanInfo planInfo){
        Map<String, String> rep = new HashMap<>();
        String resultMsg =service.deletePlan(planInfo);
        rep.put("resultMsg", resultMsg);
        return rep;
    }

    /**
     * @description:删除方案详情
     * @author: l13595
     * @param planInfoId: 方案详情id
     * @return:返回操作结果标识：ok-删除成功，其他-删除失败的提示信息
     * @time: 2017/1/16 16:56
     */
    /*
    @RequestMapping(value = "deletePlanInfo")
    public Map<String,String> deletePlanInfo(@RequestParam(value = "id")String planInfoId){
        Map<String, String> rep = new HashMap<>();
        String resultMsg =service.deletePlanInfo(planInfoId);
        rep.put("resultMsg", resultMsg);
        return rep;
    }
    */
    /**
     * @description:增加方案
     * @author: l13595
     * @param plan:id-方案id，name-方案名称，userName-创建人名称，descr-方案描述
     * @return:返回增加操作的结果：ok-增加成功；其他-增加失败的提示信息
     * @time: 2017/1/16 17:37
     */
    @RequestMapping(value = "addPlan")
    public Map<String,String> addPlan(PlanInfo plan){
        plan.setId(Utils.getUuid());
        Map<String, String> rep = new HashMap<>();
        String resultMsg =service.insertPlan(plan);
        if("ok".equals(resultMsg)){
            rep.put("planId", plan.getId());
        }
        return rep;
    }

    /**
     * @description:增加方案详情
     * @author: l13595
     * @param: id，colName-字段名称，commentName-字段中文名，planId-方案id
     * @return:返回操作结果标识：ok-增加成功；其他-增加失败
     * @time: 2017/1/16 17:42
     */
    @RequestMapping(value = "addPlanInfo")
    public Map<String,String> addPlanInfo(PlanInfo plan){
        plan.setId(Utils.getUuid());
        Map<String, String> rep = new HashMap<>();
        String resultMsg =service.insertPlanInfo(plan);
        rep.put("resultMsg", resultMsg);
        return rep;
    }

    /**
     * @description:编辑方案
     * @author: l13595
     * @param: name-方案名称，descr-方案描述，state-是否启用状态，id-方案id
     * @return:编辑操作结果标识：ok-编辑成功；其他-编辑失败提示信息
     * @time: 2017/1/16 17:48
     */
    @RequestMapping(value = "editPlan")
    public Map<String,String> editPlan(PlanInfo plan){
        Map<String, String> rep = new HashMap<>();
        String resultMsg =service.editPlan(plan);
        rep.put("resultMsg", resultMsg);
        return rep;
    }

    /**
     * @description:编辑方案信息
     * @author: l13595
     * @param: passLevel-及格率，color-及格显示颜色，id-方案属性id
     * @return:返回操作结果标识：ok-编辑成功；其他-编辑失败提示信息
     * @time: 2017/1/16 17:52
     */
    @RequestMapping(value = "editPlanInfo")
    public Map<String,String> editPlanInfo(PlanInfo plan){
        Map<String, String> rep = new HashMap<>();
        String resultMsg =service.editPlanInfo(plan);
        rep.put("resultMsg", resultMsg);
        return rep;
    }

    /**
     * @description:获取方案
     * @author: l13595
     * @param:name-方案名称
     * @return:
     * @time: 2017/1/18 17:28
     */
    @RequestMapping(value = "getPlanByName")
    public Map<String,Object> getPlan(String name){
        return service.getPlan(name);
    }

    /**
     * @description: 校验方案唯一性
     * @author: l13595
     * @param: name-方案名称
     * @return: true-方案名不存在，可以使用；false-方案名存在，不能使用
     * @time: 2017/1/19 17:05
     */
    @RequestMapping(value = "checkPlanName")
    public Map<String,Boolean> checkPlanName(String addPlanName){
        Map<String,Boolean> rep = new HashMap<>();
        Boolean flag = service.checkPlanName(addPlanName);
        rep.put("resultMsg",flag);
        return rep;
    }

}
