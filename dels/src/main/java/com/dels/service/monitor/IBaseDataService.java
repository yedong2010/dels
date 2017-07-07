package com.dels.service.monitor;


import com.dels.utils.LogDescription;
import com.dels.model.monitor.BaseDataRegion;

import java.util.List;
import java.util.Map;

/**
 * Created by l13608  on 2017/1/12 .
 */
public interface IBaseDataService {
    /**
     * 获取区域基础数据列表
     */
    public  List<Map<String,Object>> getBaseDateRegionList(BaseDataRegion BDR);

    /*
    * 更新区域基础数据列表
    */
    @LogDescription(description = "修改基础数据", pOrder = 0, pType = "baseData")
    public  String updateBaseDateRegionList(BaseDataRegion BDR);

    /**
     * @description: 新增
     * @author: l13595
     * @param bdr: 实体类属性对应的参数
     * @return: 返回操作结果标识。成功-ok，失败-返回操作失败标识
     * @time: 2017/2/21 14:44
     */
    @LogDescription(description = "新增基础数据", pOrder = 0, pType = "baseData")
    public abstract String addBaseData(BaseDataRegion bdr);

    /**
     * @description: 删除记录
     * @author: l13595
     * @param bdr: BaseDataRegion.XZQHDM 行政区划代码
     * @return: 返回结果标识。成功-ok，失败-返回操作失败提醒
     * @time: 2017/2/21 15:00
     */
    @LogDescription(description = "删除基础数据", pOrder = 0, pType = "baseData")
    public abstract String delBaseData(BaseDataRegion bdr);

    /**
     * @description: 校验行政区划代码唯一性
     * @author: l13595
     * @param baseData: XZQHDM-区划代码
     * @return: 存在-false，不存在-true
     * @time: 2017/2/21 16:45
     */
    public Boolean checkXZQHDM(BaseDataRegion baseData);

    /**
     * @description: 查询市名称
     * @author: l13595
     * @time: 2017/2/21 16:48
     */
    public abstract List<Map<String,Object>> getDsName();

}
