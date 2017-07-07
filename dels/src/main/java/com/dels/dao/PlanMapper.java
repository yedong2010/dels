package com.dels.dao;

import com.dels.model.sys.PlanInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by l13595 on 2017/1/16.
 */
public interface PlanMapper {

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
     * @description: 增加方案记录
     * @author: l13595
     * @param: id-方案id，name-方案名称，userName-创建人名称，descr-方案描述
     * @return:
     * @time: 2017/1/16 15:01
     */
    public abstract void insertPlan(PlanInfo plan);

    /**
     * @description:增加方案详细信息
     * @author: l13595
     * @param:id，colName-字段名称，commentName-字段中文名，planId-方案id
     * @return:
     * @time: 2017/1/16 15:04
     */
    public abstract void insertPlanInfo(PlanInfo plan);

    /**
     * @description:删除方案
     * @author: l13595
     * @param: id-方案id
     * @return:
     * @time: 2017/1/16 16:21
     */
    public abstract void deletePlan(String id);

    /**
     * @description:删除方案详细信息
     * @author: l13595
     * @param: 记录id
     * @return:
     * @time: 2017/1/16 16:21
     */
    public abstract void deletePlanInfo(String id);

    /**
     * @description:根据方案id删除方案详细信息
     * @author: l13595
     * @param: planId-方案id
     * @return:
     * @time: 2017/1/16 16:21
     */
    public abstract void deletePlanInfoByPlanId(String planId);

    /**
     * @description:编辑方案
     * @author: l13595
     * @param: name-方案名称，descr-方案描述，state-是否启用状态，id-方案id
     * @return:
     * @time: 2017/1/16 16:22
     */
    public abstract void editPlan(PlanInfo plan);

    /**
     * @description:编辑方案信息
     * @author: l13595
     * @param: passLevel-及格率，color-及格显示颜色，id-方案属性id
     * @return:
     * @time: 2017/1/16 16:24
     */
    public abstract void editPlanInfo(PlanInfo plan);

    /**
     * @description:獲取方案信息
     * @author: l13595
     * @param:id-方案id 、name-方案名稱
     * @return:返回查詢到得方案信息
     * @time: 2017/1/18 17:06
     */
    public abstract List<Map<String,Object>> getPlan(String name);

    /**
     * @description: 查询方案id
     * @author: l13595
     * @param name: 方案名称
     * @return: 方案id
     * @time: 2017/2/22 16:25
     */
    public abstract String getPlanByName(String name);
}
