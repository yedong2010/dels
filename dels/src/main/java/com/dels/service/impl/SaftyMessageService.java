package com.dels.service.impl;

import com.dels.dao.SysMngMapper;
import com.dels.utils.SendMessageUtil;
import com.dels.service.ISaftyMessageService;
import com.dels.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by l13595 on 2017/2/13.
 * 安全审计，消息提醒
 */
@Service
public class SaftyMessageService implements ISaftyMessageService {

    @Autowired
    private SysMngMapper dao;

    public void  updateSaftyMsg(String ip){
        String id = dao.getMsgId(ip);
        try{
            if(null==id||"".equals(id)){
                Map<String,Object> param = new HashMap<>();
                param.put("id",Utils.getUuid());
                param.put("ip",ip);
                dao.addSaftyMsg(param);
            }else{
                dao.updateMsgNum(ip);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void updateMsgState(String ip){
        dao.updateMsgState(ip);
    }

    public List<Map<String,Object>> getMsgList(Map<String,Object>param){
        try{
            List<Map<String,Object>> list = dao.getMsgList(param);
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int getMsgMngNum(){
        return dao.getMsgMngNum();
    }

    public String updateMsgMngNum(int num){
        try{
            dao.updateMsgMngNum(num);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "更新数据错误，请联系系统管理员！";
        }
    }
    public String updateMsgMng(String roleId,int num){
        Map<String,Object> param = new HashMap<>();
        param.put("num",num);
        param.put("roleId",roleId);
        try{
            dao.updateMsgMng(param);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "更新数据错误，请联系系统管理员！";
        }
    }

    public List<Map<String,Object>> getMsgMngList(){
        List<Map<String,Object>> list = new ArrayList<>();
        try{
            list = dao.getMsgMngList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public int getMsgCount(){
        int tmp = 0;
        try{
            tmp = dao.getMsgCount();
        }catch (Exception e){
            e.printStackTrace();
        }
        return tmp;
    }

    public List<Map<String,Object>> getMsgForManager(){
        List<Map<String,Object>> list = new ArrayList<>();
        try{
            list = dao.getMsgForManager();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public Map<String,Object> getSecurityTime(){
        return dao.getSecurityTime();
    }

    @Override
    public void updateUserErrorNum(String userName) {
        try{
            dao.updateUserErrorNum(userName);
            dao.updateUserStatusTo1(userName);

            //TODO 审计短信 l13595 2017/6/5 19:33
            String userStatus = dao.selectUserStatusByName(userName);
            if("1".equals(userStatus)){ //如果当前用户状态为禁用，则发送短信给审计用户，角色id=4
                List<String> phoneList = dao.selectPhoneByRoleId("4");
                if(null!=phoneList && phoneList.size()>0){
                    String msg = "用户"+userName+" 登录错误超过安全限制，账号已被禁用";
                    for(String phone: phoneList){
                        SendMessageUtil.sendmsg(msg , phone);
                    }
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
