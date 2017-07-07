package com.dels.service.impl.monitor;

import com.dels.dao.monitor.DataIndexMapper;
import com.dels.service.monitor.IDataIndexService;
import com.dels.model.dataIndexSys.DataIndexModel;
import com.dels.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by l13595 on 2017/3/17.
 * 数据指标体系
 *      预处理规则管理
 */
@Service
public class DataIndexService implements IDataIndexService {

    @Autowired
    DataIndexMapper dao;

    @Override
    public List<Map<String, Object>> findYCLGZ(DataIndexModel entity) {
        List<Map<String,Object>> list = dao.findYCLGZList(entity);
        return list;
    }

    @Override
    public List<Map<String, Object>> findYCLFZ(DataIndexModel entity) {
        List<Map<String,Object>> list = dao.findYCLFZList(entity);
        return list;
    }

    @Override
    public String addYCLGZ(DataIndexModel entity) {
        try{
            entity.setGroupName(dao.findGName(entity.getGroupId()));
            entity.setId(Utils.getUuid());
            entity.setCuser(Utils.getCurUserName());
            dao.insertYCLGZ(entity);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "新增失败，请联系系统管理员";
        }
    }

    @Override
    public String addYCLFZ(DataIndexModel entity) {
        try{
            entity.setId(Utils.getUuid());
            entity.setCuser(Utils.getCurUserName());
            dao.insertYCLFZ(entity);
            return "ok";
        }catch(Exception e){
            e.printStackTrace();
            return "新增失败，请联系系统关联元";
        }
    }

    @Override
    public String deleteYCLGZ(String id) {
        try{
            dao.deleteYCLGZById(id);
            return "ok";
        }catch(Exception e){
            e.printStackTrace();
            return "删除失败，请联系系统管理员";
        }
    }

    @Override
    public String deleteYCLFZ(String id) {
        try{
            if(0 < dao.countYCLGZByGId(id)){
                return "分组下存在规则记录，不能删除";
            }else{
                dao.deleteYCLFZById(id);
            }
            return "ok";
        }catch(Exception e){
            e.printStackTrace();
            return "删除失败，请联系系统管理员";
        }
    }

    @Override
    public String updateYCLGZ(DataIndexModel entity) {
        try{
            if(null != entity.getGroupId()){
                entity.setGroupName(dao.findGName(entity.getGroupId()));
            }
            dao.updateYCLGZ(entity);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "更新失败，请联系系统管理员";
        }
    }

    @Override
    public String updateYCLFZ(DataIndexModel entity) {
        try{
            dao.updateYCLFZ(entity);
            return "ok";
        }catch(Exception e){
            e.printStackTrace();
            return "更新失败，请联系系统管理员";
        }
    }

    @Override
    public boolean checkRuleName(String rname) {
        DataIndexModel entity = new DataIndexModel();
        entity.setRname(rname);
        List<Map<String,Object>> list = dao.findYCLGZList(entity);
        if(null!=list && list.size()>0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean checkGroupName(String name) {
        DataIndexModel entity = new DataIndexModel();
        entity.setGroupName(name);
        List<Map<String,Object>> list = dao.findYCLFZList(entity);
        if(null!=list && list.size()>0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public List<Map<String, Object>> findFXZTKList(DataIndexModel entity) {
        List<Map<String, Object>> list = dao.findFXZTK(entity);
        return list;
    }

    @Override
    public List<Map<String, Object>> findFXZTList(DataIndexModel entity) {
        List<Map<String, Object>> list = dao.findFXZT(entity);
        return list;
    }

    @Override
    public String saveFXZTK(DataIndexModel entity) {
        try{
            if(null == entity.getThemeId()){
                if(!checkInsertFXZTK(entity)){
                    return "主题库已存在";
                }else{
                    entity.setCuser(Utils.getCurUserName());
                    entity.setThemeId(Utils.getUuid());
                    dao.insertFXZTK(entity);
                }
            }else{
                if(!checkUpdateFXZTK(entity)){
                    return "主题库已存在";
                }else{
                    dao.updateFXZTK(entity);
                }
            }
            return  "ok";
        }catch (Exception e){
            e.printStackTrace();
            return  "保存失败，请联系管理员";
        }
    }

    @Override
    public String saveFXZT(DataIndexModel entity) {
        try{
            if(null != entity.getThemeId()){
                entity.setTheme(dao.findFXZTKNameById(entity.getThemeId()));
            }
            if(null == entity.getId()){
                if(!checkInsertFXZT(entity)){
                    return "主题已存在";
                }else{
                    entity.setCuser(Utils.getCurUserName());
                    entity.setId(Utils.getUuid());
                    dao.insertFXZT(entity);
                }
            }else{
                if(!checkUpdateFXZT(entity)){
                    return "主题已存在";
                }else{
                    dao.updateFXZT(entity);
                }
            }
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "保存失败，请联系管理员";
        }
    }

    @Override
    public String deleteFXZTKById(String id) {
        try{
            DataIndexModel param = new DataIndexModel();
            param.setThemeId(id);
            if(0 < dao.checkDeleteFXZTK(param)){
                return "库中存在主题，不能删除";
            }else{
                dao.deleteFXZTKById(id);
                return "ok";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "删除失败，请联系管理";
        }
    }

    @Override
    public String deleteFXZTById(String id) {
        try{
            dao.deleteFXZTById(id);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "删除失败，请联系管理";
        }
    }

    @Override
    public boolean checkInsertFXZTK(DataIndexModel entity) {
        try{
            int i = dao.checkInsertFXZTK(entity);
            if(i==0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkInsertFXZT(DataIndexModel entity) {
        try{
            int i = dao.checkInsertFXZT(entity);
            if(i==0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Map<String, Object>> findZBTX(DataIndexModel entity) {
        List<Map<String, Object>> list = dao.findZBTX(entity);
        return list;
    }

    @Override
    public String saveZBTX(DataIndexModel entity) {
        try{
            if(null != entity.getZbid()){
                entity.setZbid(Utils.getUuid());
                dao.insertZBTX(entity);
            }else{
                dao.updateZBTX(entity);
            }
            return "ok";
        }catch(Exception e){
            e.printStackTrace();
            return "保存失败，请联系管理员";
        }
    }

    @Override
    public String deleteZBTX(DataIndexModel entity) {
        try{
            if(null != entity.getZbid()){
                dao.deleteZBTX(entity);
                return "ok";
            }else{
                return "id为空";
            }
        }catch(Exception e){
            e.printStackTrace();
            return "删除失败，请联系管理员";
        }
    }

    @Override
    public List<Map<String, Object>> findJSGZ(DataIndexModel entity) {
        List<Map<String, Object>> list = dao.findJSGZ(entity);
        return list;
    }

    @Override
    public String saveJSGZ(DataIndexModel entity) {
        try{
            if(0 < dao.checkGZMC(entity)){
                return "规则名称已存在";
            }
            if(null != entity.getGzid()){ //更新
                dao.updateJSGZ(entity);
            }else{  //新增
                entity.setGzid(Utils.getUuid());
                entity.setCuser(Utils.getCurUserName());
                dao.insertJSGZ(entity);
            }
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "保存失败，请联系管理员";
        }
    }

    @Override
    public String deleteJSGZ(String id) {
        try{
            if(null != id){
                dao.deleteJSGZ(id);
                dao.deleteGZCS(id);
                return "ok";
            }else{
                return "id为空";
            }
        }catch(Exception e){
            e.printStackTrace();
            return "删除失败，请联系管理员";
        }
    }

    @Override
    public List<Map<String, Object>> findGZCSByGZId(String gzid) {
        List<Map<String, Object>> list = dao.findGZCSByGZId(gzid);
        return list;
    }

    @Override
    public List<Map<String, Object>> findZBCS(String gzid) {
        return dao.findZBCS(gzid);
    }

    @Override
    public String saveZBCS(DataIndexModel entity) {
        try{
            if(null != entity.getCsid()){
                dao.updateZBCS(entity);
            }else{
                entity.setCsid(Utils.getUuid());
                dao.insertZBCS(entity);
            }
            return "ok";
        }catch(Exception e){
            e.printStackTrace();
            return "保存失败，请联系管理员";
        }
    }

    @Override
    public String deleteZBCS(String csid) {
        try{
            if(null != csid){
                dao.deleteZBCS(csid);
                return  "ok";
            }else{
                return  "id为空";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "删除失败，请联系管理员";
        }
    }

    @Override
    public String deleteGZCSbyGZid(String gzcsid) {
        try{
            if(null != gzcsid){
                dao.deleteGZCSById(gzcsid);
                return "ok";
            }else{
                return "id为空";
            }
        }catch(Exception e){
            e.printStackTrace();
            return "删除失败，请联系管理员";
        }
    }

    @Override
    public boolean checkGZMC(DataIndexModel entity) {
        if(0 < dao.checkGZMC(entity)){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public String saveGZCS(DataIndexModel entity) {
        try{
            entity.setId(Utils.getUuid());
            dao.insertGZCS(entity);
            return "ok";
        }catch(Exception e){
            e.printStackTrace();
            return "保存失败，请联系管理员";
        }
    }

    /**
     * @description: 校验更新分析主题库的唯一性，根据id和名称进行检验
     * @author: l13595
     * @param entity: DataIndexModel 进行校验的参数
     * @return: 返回校验的结果。存在，返回false；不存在，返回true
     * @time: 2017/3/20 15:28
     */
    public boolean checkUpdateFXZTK(DataIndexModel entity) {
        try{
            int i = dao.checkUpdateFXZTK(entity);
            if(i==0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @description: 校验分析主题的唯一性
     * @author: l13595
     * @param entity: DataIndexModel 校验字段参数
     * @return: 返回校验结果。存在，返回false；不存在，返回true
     * @time: 2017/3/20 15:30
     */
    public boolean checkUpdateFXZT(DataIndexModel entity) {
        try{
            int i = dao.checkUpdateFXZT(entity);
            if(i==0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public String updateJSGS(String jsgs, String gzid){
        try{
            dao.saveJSGSByGZId(gzid, jsgs);
            return "ok";
        }catch(Exception e){
            e.printStackTrace();
            return "保存公式失败，请联系管理员";
        }
    }

    @Override
    public String exeJSGS(String jsgs, String gzid) {
        List<Map<String, Object>> paramList = dao.findGZCSByGZId(gzid);
        if(null != paramList && paramList.size() > 0){
            for(Map<String, Object> map: paramList){
                jsgs = jsgs.replace(map.get("csmc").toString(), map.get("colname").toString());
            }
        }
        StringBuffer sql = new StringBuffer();
        sql.append("update t_fx_jt_ywblfx set sll=(");
        sql.append(jsgs).append(")");
        try{
            dao.exeJSGS(sql.toString());
        }catch(Exception e){
            e.printStackTrace();
            return "计算公式执行失败,请联系管理员";
        }

        return "ok";
    }

    @Override
    public List<Map<String, Object>> bzwRestful(String zzjgdm, Integer year, Integer smonth, Integer emonth) {
        return dao.bmzwRestful(zzjgdm, year, smonth, emonth);
    }
}
