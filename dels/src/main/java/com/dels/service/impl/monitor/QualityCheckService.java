package com.dels.service.impl.monitor;

import com.dels.dao.QualityCheckMapper;
import com.dels.model.monitor.QualityCheckModel;
import com.dels.service.monitor.IQualityCheckService;
import com.dels.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by l13595 on 2017/3/13.
 *    数据质量检测：
 *      数据质量核查反馈
 *      数据质量申诉
 *      数据质量修正
 */
@Service
public class QualityCheckService implements IQualityCheckService {

    @Autowired
    QualityCheckMapper dao;

    @Override
    public List<QualityCheckModel> findQList(QualityCheckModel entity) {
        String roleId = dao.findRoleId(Utils.getCurUserName());
        if(null == entity.getEditUser() && !"1".equals(roleId)){
            entity.setEditUser(Utils.getCurUserName());
        }
        return dao.findQKList(entity);
    }


    @Override
    public String updateQualityCheck(QualityCheckModel entity) {
        String userName = Utils.getCurUserName();
        if((QualityCheckModel.STATE_WAIT.equals(entity.getState())) || (QualityCheckModel.STATE_REJECT.equals(entity.getState()))){
            entity.setAppealUser(userName);
        }else if(QualityCheckModel.STATE_CORRECT.equals(entity.getState())){
            entity.setResolveUser(userName);
        }
        if(null != entity.getId()){
            try{
                dao.updateQuality(entity);
                return "ok";
            }catch(Exception e){
                e.printStackTrace();
                return "更新失败";
            }
        }else{
            return "id为空，更新失败";
        }
    }

    @Override
    public String findSeq() {

        StringBuffer tmp = new StringBuffer();
        DateFormat df = new SimpleDateFormat("yyyyMMddHH");
        tmp.append(df.format(new Date()));

        int num = dao.findSeq();
        String tmp2 = "0000" + num;
        tmp.append(tmp2.substring(tmp2.length()-4));

        return tmp.toString();
    }

    @Override
    public String addQualityCheck(QualityCheckModel entity) {
        entity.setEditUser(Utils.getCurUserName());
        entity.setState(QualityCheckModel.STATE_FEED);
        try{
            dao.insertQuality(entity);
            return  "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "录入失败";
        }
    }

    @Override
    public List<QualityCheckModel> findRepreList(QualityCheckModel entity) {
        return dao.findQKList(entity);
    }

    @Override
    public List<QualityCheckModel> findCorrectList(QualityCheckModel entity) {
        return dao.findQKList(entity);
    }
}
