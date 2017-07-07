package com.dels.controller.monitor;

import com.dels.model.monitor.BaseDataRegion;
import com.dels.service.monitor.IBaseDataService;

import com.dels.service.IOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @description 基础数据管理
 * @author l13608
 * @time 2017年1月12日
 */
@RestController
@RequestMapping("/sysMng/")
public class BaseDataController {

    @Autowired
    IBaseDataService baseDateService;

    @Autowired
    IOperateLogService logService;
    /**
     * 查询用户区域基础信息信息列表
     */
    @RequestMapping(value = "baseDateReginList")
    public List<Map<String, Object>> getBaseDateList() {
        BaseDataRegion BDR = new BaseDataRegion();
        List<Map<String, Object>> list = baseDateService.getBaseDateRegionList(BDR);
        return list;
    }

    /**
     *更新用户区域基础数据和区域为行政区或者非行政区
     * @param : 行政区划表XZQHDM(行政区划代码) qybs(要修改成-1:珠三角 0:粤东西北) qxsx(-1:行政区划 其他数字：非行政区划)
     */
    @RequestMapping(value = "baseDateRegionUpdate")
    public Map<String, String> updateBaseDate(@RequestParam(value = "XZQHDM") String XZQHDM,@RequestParam(value = "qybs") int qybs,@RequestParam(value = "qxsx") int qxsx,@RequestParam(value = "QYMC") String QYMC,@RequestParam(value = "originalQYMC") String originalQYMC){
        BaseDataRegion BDR = new BaseDataRegion();
        BDR.setXZQHDM(XZQHDM);
        BDR.setQybs(qybs);
        BDR.setQxsx(qxsx);
        BDR.setQYMC(QYMC);
        BDR.setOriginalQYMC(originalQYMC);
        String resultMsg = baseDateService.updateBaseDateRegionList(BDR);
        Map<String, String> rep = new HashMap<>();
        rep.put("resultMsg", resultMsg);
        return rep;
    }

    /**
     * @description: 新增
     * @author: l13595
     * @param baseData: BaseDataRegion 实体类属性参数
     * @return: 返回操作结果标识。成功-ok，失败-返回操作失败提醒
     * @time: 2017/2/21 15:10
     */
    @RequestMapping(value = "addBaseData")
    public Map<String,String> addBaseData(BaseDataRegion baseData){
        String resultMsg = baseDateService.addBaseData(baseData);
        Map<String,String> rep = new HashMap<>();
        rep.put("resutMsg",resultMsg);
        return rep;
    }

    /**
     * @description: 删除记录
     * @author: l13595
     * @param baseData: BaseDataRegion 实体类属性参数
     * @return:  返回操作结果标识。成功-ok，失败-返回操作失败提醒
     * @time: 2017/2/21 15:11
     */
    @RequestMapping(value = "delBaseData")
    public Map<String,String> delBaseData(BaseDataRegion baseData){
        String resultMsg = baseDateService.delBaseData(baseData);
        Map<String,String> rep = new HashMap<>();
        rep.put("resultMsg",resultMsg);
        return rep;
    }

    /**
     * @description: 校验行政区划代码的唯一性
     * @author: l13595
     * @param baseData: XZQHDM
     * @return: 存在-false ， 不存在-true
     * @time: 2017/2/21 15:40
     */
    @RequestMapping(value = "checkXZQHDM")
    public Map<String,Boolean> checkXZQHDM(BaseDataRegion baseData){
        Boolean resultMsg = baseDateService.checkXZQHDM(baseData);
        Map<String,Boolean> rep = new HashMap<>();
        rep.put("resultMsg",resultMsg);
        return rep;
    }

    /**
     * @description: 获取市名称和代码
     * @author: l13595
     * @return:  返回查询结果列表
     * @time: 2017/2/21 16:47
     */
    @RequestMapping(value = "getDsName")
    public List<Map<String,Object>> getDsName(){
        return baseDateService.getDsName();
    }
}
