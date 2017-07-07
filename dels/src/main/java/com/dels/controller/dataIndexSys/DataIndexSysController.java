package com.dels.controller.dataIndexSys;

import com.dels.model.dataIndexSys.DataIndexModel;
import com.dels.service.monitor.IDataIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by l13595 on 2017/3/17.
 * 数据指标体系
 */
@RestController
@RequestMapping("/dataIndex")
public class DataIndexSysController {

    @Autowired
    IDataIndexService service;


    /**
     * @description: 获取预处理列表
     * @author: l13595
     * @param entity: DataIndexModel 查询参数
     * @return: 返回查询的预处理结果列表
     * @time: 2017/3/17 18:19
     */
    @RequestMapping(value = "YCLGZ")
    public List<Map<String,Object>> YCLGZlist(DataIndexModel entity){
        List<Map<String,Object>> list = service.findYCLGZ(entity);
        return list;
    }

    /**
     * @description: 获取预处理分组列表
     * @author: l13595
     * @param entity: DataIndexModel 查询参数
     * @return: 返回查询结果列表
     * @time: 2017/3/18 10:17
     */
    @RequestMapping(value = "YCLFZ")
    public List<Map<String,Object>> YCLFZList(DataIndexModel entity){
        List<Map<String,Object>> list = service.findYCLFZ(entity);
        return list;
    }

    /**
     * @description: 校验预处理规则名称的唯一性
     * @author: l13595
     * @param addRuleName: 规则名称
     * @return: false-已存在；true-不存在
     * @time: 2017/3/18 10:54
     */
    @RequestMapping(value = "checkRName")
    public Map<String, Boolean> checkRuleName(String addRuleName){
        Map<String, Boolean> rep = new HashMap<>();
        rep.put("resultMsg",service.checkRuleName(addRuleName));
        return rep;
    }
    /**
     * @description: 新增或更新预处理规则
     * @author: l13595
     * @param entity: DataIndexModel 保存的字段参数
     * @return: 返回保存操作的结果信息。保存成功，返回ok；保存失败，返回失败提醒
     * @time: 2017/3/18 10:19
     */
    @RequestMapping(value = "saveYCLGZ")
    public Map<String,String> saveYCLGZ(DataIndexModel entity){
        Map<String,String> rep = new HashMap<>();

        if(null == entity.getGroupId()){
            rep.put("resultMsg","分组id为空");
            return rep;
        }
        if (null == entity.getId()){
            if(!service.checkRuleName(entity.getRname())){
                rep.put("resultMsg","名称已存在");
            }else{
                rep.put("resultMsg",service.addYCLGZ(entity));
            }
        }else{
            rep.put("resultMsg",service.updateYCLGZ(entity));
        }

        return rep;
    }

    /**
     * @description: 校验预处理规则分组名称的唯一性
     * @author: l13595
     * @param addGroup: 分组名称
     * @return: false-已存在； true-不存在
     * @time: 2017/3/18 10:56
     */
    @RequestMapping(value = "checkGName")
    public Map<String, Boolean> checkGroupName(String addGroup){
        Map<String, Boolean> rep = new HashMap<>();
        rep.put("resultMsg",service.checkGroupName(addGroup));
        return rep;
    }
    /**
     * @description: 新增或更新预处理分组
     * @author: l13595
     * @param entity: DataIndexModel 保存字段的参数
     * @return: 返回操作结果。保存成功，返回ok；保存失败，返回失败提醒
     * @time: 2017/3/18 10:27
     */
    @RequestMapping(value = "saveYCLFZ")
    public Map<String,String> saveYCLFZ(DataIndexModel entity) {
         Map<String,String> rep = new HashMap<>();

        if (null == entity.getId()){
            if(!service.checkGroupName(entity.getGroupName())){
                rep.put("resultMsg","名称已存在");
            }else{
                rep.put("resultMsg",service.addYCLFZ(entity));
            }
        }else{
            rep.put("resultMsg",service.updateYCLFZ(entity));
        }

        return rep;
    }

    /**
     * @description: 删除预处理规则
     * @author: l13595
     * @param entity: id 规则id
     * @return: 返回删除结果。删除成功，返回ok；删除失败，返回操作失败提醒。
     * @time: 2017/3/18 10:32
     */
    @RequestMapping(value = "deleteYCLGZ")
    public Map<String,String> deleteYCLGZ(DataIndexModel entity){
        Map<String,String> rep = new HashMap<>();

        if(null == entity.getId()){
            rep.put("resultMsg","删除的规则id为空");
        }else{
            rep.put("resultMsg",service.deleteYCLGZ(entity.getId()));
        }

        return rep;
    }

    /**
     * @description: 删除预处理规则分组
     * @author: l13595
     * @param entity: id 分组id
     * @return:  返回操作结果提醒。删除成功，返回ok；删除失败，返回删除失败提醒。
     * @time: 2017/3/18 10:34
     */
    @RequestMapping(value = "deleteYCLFZ")
    public Map<String,String> deleteYCLFZ(DataIndexModel entity){
        Map<String,String> rep = new HashMap<>();
        if(null == entity.getId()){
            rep.put("resultMsg", "id为空");
        }else{
            rep.put("resultMsg",service.deleteYCLFZ(entity.getId()));
        }

        return rep;
    }


    /**
     * @description: 分析主题库列表
     * @author: l13595
     * @param entity: DataIndexModel 查询参数字段
     * @return: 返回查询结果列表
     * @time: 2017/3/19 14:58
     */
    @RequestMapping(value = "FXZTK")
    public List<Map<String, Object>> FXZTKList(DataIndexModel entity){
        List<Map<String, Object>> list = service.findFXZTKList(entity);
        return list;
    }

    /**
     * @description: 校验分析主题库名称的唯一性
     * @author: l13595
     * @param addThemeLibName: 要新增的主题库名称
     * @return: 返回校验结果。 存在-返回false； 不存在-返回true
     * @time: 2017/3/19 15:17
     */
    @RequestMapping(value = "checkInsertFXZTK")
    public Map<String, Boolean> checkInsertFXZTK(String addThemeLibName){
        Map<String, Boolean> rep = new HashMap<>();
        if(null != addThemeLibName && !"".equals(addThemeLibName)){
            DataIndexModel param = new DataIndexModel();
            param.setTheme(addThemeLibName);
            rep.put("resultMsg", service.checkInsertFXZTK(param));
        }else{
            rep.put("resultMsg", false);
        }
        return rep;
    }

    /**
     * @description: 保存新增、修改的主题库信息
     * @author: l13595
     * @param entity: 保存主题库的字段参数
     * @return: 返回保存操作的结果。保存成功，返回“ok”；保存失败，返回失败提醒
     * @time: 2017/3/19 15:25
     */
    @RequestMapping(value = "saveFXZTK")
    public Map<String, String> saveFXZTK(DataIndexModel entity){
        Map<String, String> rep = new HashMap<>();
        rep.put("resultMsg", service.saveFXZTK(entity));
        return rep;
    }

    /**
     * @description: 删除分析主题库记录
     * @author: l13595
     * @param entity: themeId-分析主题的id
     * @return: 返回删除操作的结果。删除成功，返回ok；删除失败，返回删除失败的提醒
     * @time: 2017/3/19 15:32
     */
    @RequestMapping(value = "deleteFXZTK")
    public Map<String, String> deleteFXZTK(DataIndexModel entity){
        Map<String, String> rep = new HashMap<>();
        if(null != entity.getThemeId()){
            rep.put("resultMsg", service.deleteFXZTKById(entity.getThemeId()));
        }else{
            rep.put("resultMsg", "id为空");
        }
        return rep;
    }

    /**
     * @description: 获取分析主题列表
     * @author: l13595
     * @param entity: DataIndexModel 查询字段参数。themeId-主题库id
     * @return: 返回查询结果列表
     * @time: 2017/3/19 15:36
     */
    @RequestMapping(value = "FXZT")
    public List<Map<String, Object>> FXZTList(DataIndexModel entity){
        List<Map<String, Object>> list = service.findFXZTList(entity);
        return list;
    }

    /**
     * @description: 校验分析主题的名称唯一性
     * @author: l13595
     * @param addThemeName: 要增加的主题名称
     * @return: 返回校验结果。 主题存在，返回false；不存在，返回true
     * @time: 2017/3/19 15:48
     */
    @RequestMapping(value = "checkInsertFXZT")
    public Map<String, Boolean> checkInsertFXZT(String addThemeName){
        Map<String, Boolean> rep = new HashMap<>();
        if(null != addThemeName && !"".equals(addThemeName)){
            DataIndexModel param = new DataIndexModel();
            param.settName(addThemeName);
            rep.put("resultMsg", service.checkInsertFXZT(param));
        }else{
            rep.put("resultMsg", false);
        }
        return rep;
    }

    /**
     * @description: 保存新增、修改的分析主题
     * @author: l13595
     * @param entity: DataIndexModel 保存分析主题的字段参数
     * @return: 返回保存操作的结果。保存成功，返回ok；保存失败，返回保存失败的提醒
     * @time: 2017/3/19 15:50
     */
    @RequestMapping(value = "saveFXZT")
    public Map<String, String> saveFXZT(DataIndexModel entity){
        Map<String, String> rep = new HashMap<>();
        rep.put("resultMsg", service.saveFXZT(entity));
        return rep;
    }

    /**
     * @description: 删除分析主题
     * @author: l13595
     * @param entity: 删除操作的字段参数。 id-要删除的分析主题的id
     * @return:
     * @time: 2017/3/19 15:53
     */
    @RequestMapping(value = "deleteFXZT")
    public Map<String, String> deleteFXZT(DataIndexModel entity){
        Map<String, String> rep = new HashMap<>();
        if(null != entity.getId()){
            rep.put("resultMsg", service.deleteFXZTById(entity.getId()));
        }else{
            rep.put("resultMsg", "id为空");
        }
        return rep;
    }

    @RequestMapping(value = " ZBTX")
    public List<Map<String, Object>> ZBTX(DataIndexModel entity){
        return service.findZBTX(entity);
    }

    @RequestMapping(value = "JSGZ")
    public Map<String, Object> JSGZ(DataIndexModel entity){
        Map<String, Object> rep = new HashMap<>();
        List<Map<String, Object>> list1 = service.findJSGZ(entity);
        if(null != list1 && list1.size() == 1){
            rep = list1.get(0);
            rep.put("resultMsg","ok");
        }else{
            rep.put("resultMsg","计算规则不存在");
        }
        List<Map<String, Object>> list = service.findGZCSByGZId(entity.getGzid());
        List<Map<String, Object>> listTmp = new ArrayList<>();
        for(Map<String, Object> map:list){
            map.put("value", "");
            listTmp.add(map);
        }
        rep.put("list", listTmp);
        return rep;
    }


    /**
     * @description:
     * @author: l13595
     * @param :
     * @return:
     * @time: 2017/3/22 20:39
     */
    @RequestMapping(value = "JSGZList")
    public List< Map<String, Object>> JSGZList(DataIndexModel entity){
        List<Map<String, Object>> list = service.findJSGZ(entity);
        return list;
    }


    /**
     * @description:
     * @author: l13595
     * @param :
     * @return:
     * @time: 2017/3/22 19:45
     */
    @RequestMapping(value = "deleteGZCS")
    public Map<String, String> deleteGZCS(String id){
        Map<String, String> rep = new HashMap<>();
        rep.put("resultMsg", service.deleteGZCSbyGZid(id));
        return rep;
    }

    @RequestMapping(value = "GZCS")
    public Map<String, Object> GZCS(DataIndexModel entity){
        Map<String, Object> rep = new HashMap<>();

        List<Map<String, Object>> zbcs = service.findZBCS(entity.getGzid());
        List<Map<String, Object>> gzcs = service.findGZCSByGZId(entity.getGzid());

        rep.put("zbcs", zbcs);
        rep.put("gzcs", gzcs);
        return rep;
    }

    @RequestMapping(value = "ZBCS")
    public List<Map<String, Object>> ZBCS(String gzid){
        return service.findZBCS(gzid);
    }

    @RequestMapping(value = "checkJSGZ")
    public Map<String, Boolean> checkJSGZ(DataIndexModel entity){
        Map<String, Boolean> rep = new HashMap<>();
        rep.put("resultMsg", service.checkGZMC(entity));
        return rep;
    }

    @RequestMapping(value = "saveJSGZ")
    public Map<String, String> saveJSGZ(DataIndexModel entity){
        Map<String, String> rep = new HashMap<>();
        rep.put("resultMsg", service.saveJSGZ(entity));
        return rep;
    }

    @RequestMapping(value = "saveGZCS")
    public Map<String, String> saveGZCS(DataIndexModel entity){
        Map<String, String> rep = new HashMap<>();
        rep.put("resultMsg", service.saveGZCS(entity));
        return rep;
    }


    @RequestMapping(value = "deleteJSGZ")
    public Map<String, String> deleteJSGZ(String id){
        Map<String, String> rep = new HashMap<>();
        rep.put("resultMsg", service.deleteJSGZ(id));
        return rep;
    }


    /**
     * @description:
     * @author: l13595
     * @param :
     * @return:
     * @time: 2017/3/22 20:15
     */
    @RequestMapping(value = "saveJSGS")
    public Map<String, String> saveJSGS(String jsgs, String gzid){
        Map<String, String> rep = new HashMap<>();
        String resultMsg = service.updateJSGS(jsgs, gzid);
        if("ok".equals(resultMsg)){
            if(jsgs.matches(".*[a-zA-z].*")){    //安全过滤，不能包含英文，防止sql注入
                rep.put("resultMsg", "公式不合法，不能包含英文");
            }else{
                rep.put("resultMsg", service.exeJSGS(jsgs, gzid));
            }
        }else{
            rep.put("resultMsg", resultMsg);
        }
        return rep;
    }

}
