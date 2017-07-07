package com.dels.service.monitor;

import com.dels.model.dataIndexSys.DataIndexModel;

import java.util.List;
import java.util.Map;

/**
 * Created by l13595 on 2017/3/17.
 * 数据指标体系
 *     预处理规则
 */
public interface IDataIndexService {

    /**
     * @description: 查询预处理规则
     * @author: l13595
     * @param entity: DataIndexModel 查询参数
     * @return: 返回查询结果列表
     * @time: 2017/3/17 18:04
     */
    public abstract List<Map<String,Object>> findYCLGZ(DataIndexModel entity);

    /**
     * @description: 获取预处理分组列表
     * @author: l13595
     * @param entity: DataIndexModel 查询参数
     * @return: 返回查询结果列表
     * @time: 2017/3/17 18:07
     */
    public abstract List<Map<String,Object>> findYCLFZ(DataIndexModel entity);

    /**
     * @description: 新增预处理规则
     * @author: l13595
     * @param entity: DataIndexModel 新增字段参数
     * @return: 返回新增操作结果， 新增成功，返回ok；新增失败，返回操作失败的提醒
     * @time: 2017/3/17 18:10
     */
    public abstract String addYCLGZ(DataIndexModel entity);

    /**
     * @description: 新增预处理分组
     * @author: l13595
     * @param entity: DataIndexModel 新增字段参数
     * @return: 返回新增操作结果。新增成功，返回ok，新增失败，返回新增失败的提醒
     * @time: 2017/3/17 18:11
     */
    public abstract String addYCLFZ(DataIndexModel entity);

    /**
     * @description: 删除预处理规则
     * @author: l13595
     * @param id: 规则id
     * @return: 返回删除操作的结果。删除成功，返回ok；删除失败，返回删除失败的提醒。
     * @time: 2017/3/17 18:12
     */
    public abstract String deleteYCLGZ(String id);

    /**
     * @description: 删除预处理分组记录
     * @author: l13595
     * @param id: 分组数据的id
     * @return: 返回删除操作的结果。删除成功，返回ok；删除失败，返回删除失败的提醒。
     * @time: 2017/3/17 18:12
     */
    public abstract  String deleteYCLFZ(String id);

    /**
     * @description: 更新预处理规则
     * @author: l13595
     * @param entity: DataIndexModel 更新字段参数
     * @return: 返回更新操作的结果。更新成功，返回ok；更新失败，返回更新失败的提醒
     * @time: 2017/3/17 18:14
     */
    public abstract String updateYCLGZ(DataIndexModel entity);

    /**
     * @description: 更新预处理分组
     * @author: l13595
     * @param entity: DataIndexModel 更新字段参数
     * @return: 返回更新操作的结果。更新成功，返回ok；更新失败，返回更新失败的提醒
     * @time: 2017/3/17 18:15
     */
    public abstract String updateYCLFZ(DataIndexModel entity);

    /**
     * @description: 校验规则名称是否唯一
     * @author: l13595
     * @param rname: 规则名称
     * @return: 存在-返回false；不存在-返回true
     * @time: 2017/3/18 10:47
     */
    public abstract boolean checkRuleName(String rname);

    /**
     * @description: 校验规则分组名称的唯一性
     * @author: l13595
     * @param name: 分组名称
     * @return: 存在-返回false；不存在-返回true
     * @time: 2017/3/18 10:48
     */
    public abstract boolean checkGroupName(String name);

    /**
     * @description: 获取分析主题库列表
     * @author: l13595
     * @param entity: DataIndexModel 查询字段参数
     * @return: 返回查询到的结果列表
     * @time: 2017/3/20 15:19
     */
    public abstract List<Map<String, Object>> findFXZTKList(DataIndexModel entity);

    /**
     * @description: 获取分析主题列表
     * @author: l13595
     * @param entity: DataIndexModel 查询字段参数
     * @return:  返回查询到的结果列表
     * @time: 2017/3/20 15:20
     */
    public abstract List<Map<String, Object>> findFXZTList(DataIndexModel entity);

    /**
     * @description: 保存新增或更新的分析主题库
     * @author: l13595
     * @param entity: DataIndexModel 保存的字段参数
     * @return: 返回保存操作结果。保存成功，返回ok；保存失败，返回失败提醒
     * @time: 2017/3/20 15:21
     */
    public abstract String saveFXZTK(DataIndexModel entity);

    /**
     * @description: 保存新增或更新的分析主题
     * @author: l13595
     * @param entity: DataIndexModel 保存的字段参数
     * @return:  返回保存操作的结果。保存成功，返回ok；保存失败，返回失败提醒
     * @time: 2017/3/20 15:22
     */
    public abstract String saveFXZT(DataIndexModel entity);

    /**
     * @description: 删除分析主题库
     * @author: l13595
     * @param id: 分析主题的id
     * @return: 返回删除操作的结果。删除成功，返回ok；删除失败，返回失败提醒。
     * @time: 2017/3/20 15:23
     */
    public abstract String deleteFXZTKById(String id);

    /**
     * @description: 删除分析主题
     * @author: l13595
     * @param id: 分析主题的id
     * @return:  返回删除操作的结果。删除成功，返回ok；删除失败，返回失败提醒
     * @time: 2017/3/20 15:24
     */
    public abstract String deleteFXZTById(String id);

    /**
     * @description: 校验新增分析主题库名称
     * @author: l13595
     * @param entity: DataIndexModel 分析主题库的名称参数
     * @return: 返回校验结果。名称已存在，返回false；名称不存在，返回true
     * @time: 2017/3/20 15:25
     */
    public abstract boolean checkInsertFXZTK(DataIndexModel entity);

    /**
     * @description: 校验新增分析主题的名称
     * @author: l13595
     * @param entity: DataIndexModel 分析主题的名称参数
     * @return: 返回校验结果。名称已存在，返回false；名称不存在，返回true
     * @time: 2017/3/20 15:27
     */
    public abstract boolean checkInsertFXZT(DataIndexModel entity);


    public abstract List<Map<String, Object>> findZBTX(DataIndexModel entity);
    public abstract String saveZBTX(DataIndexModel entity);
    public abstract String deleteZBTX(DataIndexModel entity);
    public abstract List<Map<String, Object>> findJSGZ(DataIndexModel entity);
    public abstract String saveJSGZ(DataIndexModel entity);
    public abstract String deleteJSGZ(String id);
    public abstract List<Map<String , Object>> findGZCSByGZId(String gzid);
    public abstract List<Map<String, Object>> findZBCS(String gzid);
    public abstract String saveZBCS(DataIndexModel entity);
    public abstract String deleteZBCS(String csid);

    /**
     * @description:
     * @author: l13595
     * @param :
     * @return:
     * @time: 2017/3/22 19:41
     */
    public String deleteGZCSbyGZid(String gzcsid);

    public abstract boolean checkGZMC(DataIndexModel entity);

    /**
     * @description:
     * @author: l13595
     * @param :
     * @return:
     * @time: 2017/3/22 19:57
     */
    public String saveGZCS(DataIndexModel entity);


    /**
     * @description: 保存计算公式
     * @author: l13595
     * @param :
     * @return:
     * @time: 2017/3/22 20:10
     */
    public String updateJSGS(String jsgs, String gzid);

    /**
     * @description:执行保存的计算公式
     * @author: l13595
     * @param: jsgs-计算公式， gzid-规则id
     * @return: 返回执行结果，执行成功，返回ok；执行失败，返回失败提醒
     * @time: 2017/3/23 19:33
     */
    public String exeJSGS(String jsgs, String gzid);


    /**
     * @description: 部门行为信息服务接口
     * @author: l13595
     * @param: zzjgdm-组织机构代码， year-查询年份， smonth-查询起始月份， emonth-查询截止月份
     * @return: 返回 组织机构名称、部门不作为白分析、部门慢作为百分比、部门办事不严谨百分比
     * @time: 2017/3/25 17:10
     */
    public List<Map<String, Object>> bzwRestful(String zzjgdm, Integer year, Integer smonth, Integer emonth);

}
