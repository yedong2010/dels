package com.dels.dao.monitor;

import com.dels.model.monitor.BaseDataRegion;

import java.util.List;
import java.util.Map;

/**
 * Created by l13608 on 2017/1/12.
 * 基础数据管理：修改、删除
 */
public interface BaseDataMapper {
    /**
     * 获取基础数据区域菜单
     * @return：返回查询的菜单信息，放到List中
     */

    public abstract List<Map<String,Object>> getBaseDateReginList(BaseDataRegion BDR);

    /**
     * 更新基础数据区域表中辖区分布中珠三角和粤东西北
     */
    public abstract void updateBaseDateRegionList(BaseDataRegion BDR);

    /*
    *更新基础数据中为非开发区
     */
    public abstract void updateBaseDateDeveArea(BaseDataRegion BDR);

    /*
    *更新基础数据中为开发区
     */
    public abstract void updateBaseDateNoDeveArea(BaseDataRegion BDR);

    /**
     * @description: 新增
     * @author: l13595
     * @param bdr: 实体类属性对应的参数
     * @time: 2017/2/21 14:44
     */
    public abstract void addBaseData(BaseDataRegion bdr);

    /**
     * @description: 删除记录
     * @author: l13595
     * @param bdr: BaseDataRegion.XZQHDM 行政区划代码
     * @time: 2017/2/21 15:00
     */
    public abstract void delBaseData(BaseDataRegion bdr);

    /**
     * @description: 根据行政区划代码查询记录
     * @author: l13595
     * @param : 行政区划代码
     * @return: 返回查询结果列表
     * @time: 2017/2/21 16:49
     */
    public abstract List<String> getXzqudm(BaseDataRegion baseData);

    /**
     * @description: 获取所有市名称和代码
     * @author: l13595
     * @return: 返回去重的市名称列表
     * @time: 2017/2/21 16:49
     */
    public abstract List<Map<String,Object>> getDsName();

    public abstract int getMaxQxsx(String SJMC);

     /**
      * @description: 获取省直地区名称列表
      * @author: l13595
      * @time: 2017/5/18 17:34
      */
    public abstract List<String> findSZMC();
     /**
      * @description: 获取地市名称列表
      * @author: l13595
      * @time: 2017/5/18 17:35
      */
    public abstract List<String> findDSMC();

}
