package com.dels.service;

import com.dels.model.sys.PlanInfo;
import com.dels.utils.LogDescription;

import java.util.List;
import java.util.Map;

/**
 * Created by l13595 on 2017/1/16.
 */
public interface IPlanService {

    /**
     * @description:方案管理列表
     * @author: l13595
     * @param:
     * @return:
     * @time: 2017/1/16 11:44
     */
    public abstract List<Map<String,String>> getPlanList();

    /**
     * @description:方案管理详细信息列表
     * @author: l13595
     * @param planId: 方案id
     * @return:对应方案id的详细方案信息列表
     * @time: 2017/1/16 11:45
     */
    public abstract List<Map<String,String>> getPlanInfoList(String planId);

    /**
     * @description:删除方案
     * @author: l13595
     * @param: 方案id
     * @return:ok-删除成功，其他-方案删除失败提示信息
     * @time: 2017/1/16 16:43
     */
    @LogDescription(description = "删除方案",pOrder = 0,pType = "PlanInfo")
    public abstract String deletePlan(PlanInfo planInfo);

    /**
     * @description:删除方案详细信息
     * @author: l13595
     * @param: 方案详情id
     * @return:返回删除操作的结果：ok-删除成功，其他-删除失败的提示
     * @time: 2017/1/16 16:54
     */
    public abstract String deletePlanInfo(String id);

    /**
     * @description:增加方案
     * @author: l13595
     * @param:  id-方案id，name-方案名称，userName-创建人名称，descr-方案描述
     * @return:返回操作结果：ok-删除成功；其他-删除失败
     * @time: 2017/1/16 17:34
     */
    @LogDescription(description = "增加方案",pOrder = 0,pType = "PlanInfo")
    public abstract String insertPlan(PlanInfo plan);

    /**
     * @description:增加方案详情
     * @author: l13595
     * @param: id，colName-字段名称，commentName-字段中文名，planId-方案id
     * @return: 返回操作结果：ok-增加成功，其他-增加失败提示
     * @time: 2017/1/16 17:40
     */
    public abstract String insertPlanInfo(PlanInfo plan);

    /**
     * @description:编辑方案
     * @author: l13595
     * @param plan: name-方案名称，descr-方案描述，state-是否启用状态，id-方案id
     * @return:返回编辑操作结果：ok-编辑成功；其他-编辑失败提示信息
     * @time: 2017/1/16 17:45
     */
    @LogDescription(description = "编辑方案",pOrder = 0,pType = "PlanInfo")
    public abstract String editPlan(PlanInfo plan);

    /**
     * @description:编辑方案详情
     * @author: l13595
     * @param: passLevel-及格率，color-及格显示颜色，id-方案属性id
     * @return:
     * @time: 2017/1/16 16:24
     */
    public abstract String editPlanInfo(PlanInfo plan);

    /**
     * @description:獲取方案
     * @author: l13595
     * @param:name-方案名稱
     * @return:返回方案信息
     * @time: 2017/1/18 17:17
     */
    public abstract Map<String,Object> getPlan(String name);

    /**
     * @description: 校验方案名称的唯一性
     * @author: l13595
     * @param name: 方案名称
     * @return: 返回校验结果。方案存在，返回false；方案不存在，返回true
     * @time: 2017/3/27 14:55
     */
    public abstract Boolean checkPlanName(String name);

}
