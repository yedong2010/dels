package com.dels.service.impl.monitor;

import com.dels.dao.orcl.BaseDataOrclMapper;
import com.dels.dao.monitor.BaseDataMapper;
import com.dels.model.monitor.BaseDataRegion;
import com.dels.service.monitor.IBaseDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BaseDataService implements IBaseDataService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BaseDataMapper dao;
    @Autowired
    BaseDataOrclMapper orclDao;

    @Override
    public List<Map<String,Object>> getBaseDateRegionList(BaseDataRegion BDR){
        List<Map<String,Object>> list = null;
        try{
            list = dao.getBaseDateReginList(BDR);
        }catch (Exception e){
            e.printStackTrace();
            return list;
        }
        return list;
    }

    @Override
    public String updateBaseDateRegionList(BaseDataRegion BDR){
        try{
            dao.updateBaseDateRegionList(BDR);
            if (BDR.getQxsx() != -1){
                dao.updateBaseDateDeveArea(BDR);
            }else {
                dao.updateBaseDateNoDeveArea(BDR);
            }
            orclDao.updateBaseData(BDR);  /** oracle数据库更新区域基础数据 l13595 2017/3/2 10:44 **/
        }catch (Exception e){
            logger.info(e.getMessage());
            return "修改区域失败，请联系管理员！";
        }
        return "ok";
    }

    public String addBaseData(BaseDataRegion bdr){
        try{
            if(!checkXZQHDM(bdr)){
                return "行政区划代码已保存，请不要重复保存";
            }
            if(0==bdr.getQxsx()){
                bdr.setQxsx(dao.getMaxQxsx(bdr.getSJMC()));
            }
            dao.addBaseData(bdr);
            orclDao.insertBaseData(bdr);/** oracle数据库增加基础数据 l13595 2017/3/2 10:19 **/
        }catch (Exception e){
            e.printStackTrace();
            return "新增失败，请联系系统管理员";
        }
        return "ok";
    }

    public String delBaseData(BaseDataRegion bdr){
        try{
            dao.delBaseData(bdr);
            orclDao.deleteBaseData(bdr);/** oracle数据库删除基础数据 l13595 2017/3/2 10:20 **/
        }catch (Exception e){
            e.printStackTrace();
            return "删除失败，请联系系统管理员";
        }
        return "ok";
    }

    public Boolean checkXZQHDM(BaseDataRegion baseData){
        try{
            List<String> list =  dao.getXzqudm(baseData);
            if(null!=list&&list.size()>0){
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Map<String,Object>> getDsName(){
        return dao.getDsName();
    }


}
