package com.dels.dao.monitor;

import com.dels.model.dataIndexSys.DataIndexModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by l13595 on 2017/3/17.
 * 数据指标体系
 *     预处理规则
 */
public interface DataIndexMapper {

    /**
     * @description: 获取预处理规则列表
     * @author: l13595
     * @param entity: DataIndexModel 查询参数
     * @return:  返回结果列表
     * @time: 2017/3/17 16:40
     */
    public abstract List<Map<String,Object>> findYCLGZList(DataIndexModel entity);

    /**
     * @description: 获取预处理规则分组列表
     * @author: l13595
     * @param entity: DataIndexModel 查询参数
     * @return: 返回查询结果列表
     * @time: 2017/3/17 16:41
     */
    public abstract List<Map<String,Object>> findYCLFZList(DataIndexModel entity);

    /**
     * @description: 新增预处理规则
     * @author: l13595
     * @param entity: DataIndexModel 增加字段参数
     * @time: 2017/3/17 16:41
     */
    public abstract void insertYCLGZ(DataIndexModel entity);

    /**
     * @description: 新增预处理规则分析
     * @param entity：DataIndexModel 增加字段参数
     */
    public abstract void insertYCLFZ(DataIndexModel entity);

    /**
     * @description: 删除预处理规则
     * @author: l13595
     * @param id:预处理记录id
     * @time: 2017/3/17 17:42
     */
    public abstract void deleteYCLGZById(String id);

    /**
     * @description: 深处预处理分组
     * @author: l13595
     * @param id: 分组id
     * @time: 2017/3/17 17:42
     */
    public abstract void deleteYCLFZById(String id);

    /**
     * @description: 更新预处理规则
     * @author: l13595
     * @param entity: DataIndexModel 更新字段参数
     * @time: 2017/3/17 17:43
     */
    public abstract void updateYCLGZ(DataIndexModel entity);

    /**
     * @description: 更新预处理分组
     * @author: l13595
     * @param entity: 更新分组字段参数
     * @time: 2017/3/17 17:43
     */
    public abstract void updateYCLFZ(DataIndexModel entity);

    /**
     * @description: 通过id查找预处理规则的分组名称
     * @author: l13595
     * @param id: 分组id
     * @return: 返回分组名称
     * @time: 2017/3/20 15:31
     */
    public abstract String findGName(String id);

    /**
     * @description: 根据分组id获取该分组下的预处理规则的数量
     * @author: l13595
     * @param groupId: 分组id
     * @return: 返回获取到的预处理规则的数量
     * @time: 2017/3/20 15:32
     */
    public abstract int countYCLGZByGId(String groupId);

    /**
     * @description: 查询分析主题库
     * @author: l13595
     * @param entity: DataIndexModel 查询的字段参数
     * @return:  返回查询结果列表
     * @time: 2017/3/20 15:34
     */
    public abstract List<Map<String,Object>> findFXZTK(DataIndexModel entity);

    /**
     * @description: 查询分析主题
     * @author: l13595
     * @param entity: DataIndexModel 查询的字段参数
     * @return:  返回查询结果列表
     * @time: 2017/3/20 15:34
     */
    public abstract List<Map<String, Object>> findFXZT(DataIndexModel entity);

    /**
     * @description: 新增分析主题库记录
     * @author: l13595
     * @param entity: DataIndexModel 新增的字段参数
     * @time: 2017/3/20 15:34
     */
    public abstract void insertFXZTK(DataIndexModel entity);

    /**
     * @description: 通过主题库的id获取主题库名称
     * @author: l13595
     * @param themeId: 主题库id
     * @return:  返回分析主题库名称
     * @time: 2017/3/20 15:34
     */
    public abstract String findFXZTKNameById(String themeId);

    /**
     * @description: 新增分析主题记录
     * @author: l13595
     * @param entity: DataIndexModel 新增字段参数
     * @time: 2017/3/20 15:34
     */
    public abstract void insertFXZT(DataIndexModel entity);

    /**
     * @description: 更新分析主题库
     * @author: l13595
     * @param entity: DataIndexModel 更新的字段参数
     * @time: 2017/3/20 15:38
     */
    public abstract void updateFXZTK(DataIndexModel entity);

    /**
     * @description: 更新分析主题
     * @author: l13595
     * @param entity: DataIndexModel 更新的字段参数
     * @return:
     * @time: 2017/3/20 15:38
     */
    public abstract void updateFXZT(DataIndexModel entity);

    /**
     * @description: 根据id删除分析主题库
     * @author: l13595
     * @param themeId:
     * @time: 2017/3/20 15:39
     */
    public abstract void deleteFXZTKById(String themeId);

    /**
     * @description: 根据id删除分析主题
     * @author: l13595
     * @param id: 分析主题id
     * @time: 2017/3/20 15:39
     */
    public abstract void deleteFXZTById(String id);

    /**
     * @description: 根据分析主题库的名称，查询分析主题库记录数
     * @author: l13595
     * @param entity: theme字段 分析主题库名称
     * @return: 返回查询到的个数
     * @time: 2017/3/20 15:40
     */
    public abstract int checkInsertFXZTK(DataIndexModel entity);

    /**
     * @description: 根据分析主题的名称，获取分析主题的记录数
     * @author: l13595
     * @param entity: tName 分析主题的名称
     * @return: 返回查询结果数量
     * @time: 2017/3/20 15:43
     */
    public abstract int checkInsertFXZT(DataIndexModel entity);

    /**
     * @description: 根据分析主题库的id和名称，查询分析主题库的记录数量
     * @author: l13595
     * @param entity: theme 主题库名称； themeId 主题库id
     * @return: 返回查询到的结果数量
     * @time: 2017/3/20 15:44
     */
    public abstract int checkUpdateFXZTK(DataIndexModel entity);

    /**
     * @description: 根据分析主题的id和名称。查询结果记录数
     * @author: l13595
     * @param entity: id-分析主题的id；  tName-分析主题的名称
     * @return:返回查询结果的记录数量
     * @time: 2017/3/20 15:46
     */
    public abstract int checkUpdateFXZT(DataIndexModel entity);

    /**
     * @description: 根据分析主题库的id，获取主题库下的主题数量
     * @author: l13595
     * @param entity: themeId-主题库id
     * @return:
     * @time: 2017/3/20 15:47
     */
    public abstract int checkDeleteFXZTK(DataIndexModel entity);

    /**
     * @description: 查询指标体系列表
     * @author: l13595
     * @param entity: 查询参数
     * @return: 返回查询结果列表
     * @time: 2017/3/25 17:20
     */
    public abstract List<Map<String, Object>> findZBTX(DataIndexModel entity);

    /**
     * @description: 新增指标体系记录
     * @author: l13595
     * @param entity: 新增指标体系的字段参数
     * @time: 2017/3/25 17:21
     */
    public abstract void insertZBTX(DataIndexModel entity);

    /**
     * @description: 更新指标体系
     * @author: l13595
     * @param entity: 更新指标体系的字段参数
     * @time: 2017/3/25 17:22
     */
    public abstract void updateZBTX(DataIndexModel entity);

    /**
     * @description: 删除指标体系数据
     * @author: l13595
     * @param entity: 要删除的指标体系的id
     * @time: 2017/3/25 17:23
     */
    public abstract void deleteZBTX(DataIndexModel entity);

    /**
     * @description: 获取计算规则
     * @author: l13595
     * @param entity: 查询参数
     * @return: 返回查询的结果列表
     * @time: 2017/3/25 17:23
     */
    public abstract List<Map<String, Object>> findJSGZ(DataIndexModel entity);

    /**
     * @description: 新增计算规则
     * @author: l13595
     * @param entity: 新增计算规则的字段参数
     * @time: 2017/3/25 17:24
     */
    public abstract void insertJSGZ(DataIndexModel entity);

    /**
     * @description: 新增规则参数
     * @author: l13595
     * @param entity： 新增规则的字段参数
     * @time: 2017/3/25 17:24
     */
    public abstract void insertGZCS(DataIndexModel entity);

    /**
     * @description: 更新计算规则
     * @author: l13595
     * @param entity: 更新规则的字段参数
     * @time: 2017/3/25 17:25
     */
    public abstract void updateJSGZ(DataIndexModel entity);

    /**
     * @description: 删除计算规则
     * @author: l13595
     * @param gzid: 要删除的规则的id
     * @time: 2017/3/25 17:26
     */
    public abstract void deleteJSGZ(String gzid);

    /**
     * @description: 删除规则参数
     * @author: l13595
     * @param gzid: 要删除的规则参数的规则id
     * @time: 2017/3/25 17:26
     */
    public abstract void deleteGZCS(String gzid);

    /**
     * @description: 获取规则参数
     * @author: l13595
     * @param gzid: 要查询的规则参数的规则id
     * @return: 返回查询的结果列表
     * @time: 2017/3/25 17:27
     */
    public abstract List<Map<String , Object>> findGZCSByGZId(String gzid);
    /**
     * @description: 通过规则参数id删除规则参数
     * @author: l13595
     * @param gzcsid: 规则参数id
     * @time: 2017/3/22 19:40
     */
    public abstract void deleteGZCSById(String gzcsid);

    /**
     * @description:获取指标参数
     * @author: l13595
     * @param gzid: 规则id
     * @return: 返回查询结果列表
     * @time: 2017/3/25 17:28
     */
    public abstract List<Map<String, Object>> findZBCS(@Param("gzid")String gzid);

    /**
     * @description: 新增指标参数
     * @author: l13595
     * @param entity: 新增指标参数的字段参数
     * @time: 2017/3/25 17:29
     */
    public abstract void insertZBCS(DataIndexModel entity);

    /**
     * @description: 更新指标参数
     * @author: l13595
     * @param entity: 要更新的指标参数的字段参数
     * @time: 2017/3/25 17:30
     */
    public abstract void updateZBCS(DataIndexModel entity);

    /**
     * @description: 删除指标参数
     * @author: l13595
     * @param csid: 要删除的参数的id
     * @time: 2017/3/25 17:31
     */
    public abstract void deleteZBCS(String csid);

    /**
     * @description: 校验规则名称是否唯一
     * @author: l13595
     * @param entity: 进行校验查询的字段参数
     * @return: 返回查询到的记录数
     * @time: 2017/3/25 17:32
     */
    public abstract int checkGZMC(DataIndexModel entity);

    /**
     * @description: 保存计算公式
     * @author: l13595
     * @param :gzid-规则id，jsgs-要保存的计算公式
     * @return:
     * @time: 2017/3/22 20:06
     */
    public abstract void saveJSGSByGZId(@Param("gzid")String gzid, @Param("jsgs")String jsgs);

    /**
     * @description: 执行计算公式
     * @author: l13595
     * @param sql: 执行的sql语句
     * @time: 2017/3/25 17:32
     */
    public abstract void exeJSGS(@Param("sql")String sql);

    /**
     * @description: 部门行为接口
     * @author: l13595
     * @param: zzjgdm-组织机构代码、 year-查询年份、smonth-查询起始月份、emonth-查询截止月份
     * @return: 返回查询的结果列表
     * @time: 2017/3/25 17:33
     */
    public abstract List<Map<String, Object>> bmzwRestful(@Param("zzjgdm")String zzjgdm, @Param("year")Integer year, @Param("smonth")Integer smonth, @Param("emonth")Integer emonth);

}
