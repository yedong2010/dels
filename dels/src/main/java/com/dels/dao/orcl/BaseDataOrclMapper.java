package com.dels.dao.orcl;

import com.dels.model.monitor.BaseDataRegion;

/**
 * Created by l13595 on 2017/2/24.
 * 基础数据管理：oracle数据库
 */
public interface BaseDataOrclMapper {
    /**
     * @description: 新增基础数据
     * @author: l13595
     * @param :baseData 实体类属性参数
     * @time: 2017/3/2 10:11
     */
    public abstract void insertBaseData(BaseDataRegion baseData);

    /**
     * @description: 更新基础数据
     * @author: l13595
     * @param baseData: QYMC-区域全称  ； XZQHDM-行政区划代码
     * @time: 2017/3/2 10:12
     */
    public abstract void updateBaseData(BaseDataRegion baseData);
    /**
     * @description: 删除基础数据
     * @author: l13595
     * @param baseData:XZQHDM-行政区划代码
     * @time: 2017/3/2 10:13
     */
    public abstract void deleteBaseData(BaseDataRegion baseData);

}
