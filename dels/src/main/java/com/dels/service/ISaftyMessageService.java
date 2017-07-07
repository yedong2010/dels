package com.dels.service;

import com.dels.utils.LogDescription;

import java.util.List;
import java.util.Map;

/**
 * Created by l13595 on 2017/2/13.
 */
public interface ISaftyMessageService {

    /**
     * @description: 更新ip消息，存在时更新，次数加1，不存在时新增
     * @author: l13595
     * @param :ip-用户登录ip
     * @time: 2017/2/13 17:26
     */
    public void  updateSaftyMsg(String ip);

    /**
     * @description: 关闭当前IP的消息，登录成功时调用
     * @author: l13595
     * @param :ip-当前登录用户使用的ip
     * @time: 2017/2/13 17:41
     */
    public void updateMsgState(String ip);

    /**
     * @description: 获取安全审计信息列表
     * @author: l13595
     * @return: list-查询到的安全审计信息列表
     * @time: 2017/2/13 19:30
     */
    public List<Map<String,Object>> getMsgList(Map<String,Object>param);

    /**
     * @description: 安全审计进行消息提醒的标准次数
     * @author: l13595
     * @return: 返回查询到的次数值
     * @time: 2017/2/13 19:31
     */
    public int getMsgMngNum();

    /**
     * @description: 更新安全审计的标准次数
     * @author: l13595
     * @param : num-次数值
     * @time: 2017/2/13 19:32
     */
    @LogDescription(description = "修改安全审计标准次数")
    public String updateMsgMngNum(int num);

    /**
     * @description: 更新安全审计提醒标准
     * @author: l13595
     * @param roleId: 角色id
     * @param num: 次数
     * @time: 2017/2/17 17:13
     */
    public String updateMsgMng(String roleId,int num);

    /**
     * @description: 获取消息提醒信息
     * @author: l13595
     * @return: 返回当天安全审计记录中，错误次数超过设置的标准的记录
     * @time: 2017/2/14 15:00
     */
    public List<Map<String,Object>> getMsgMngList();

    /**
     * @description: 获取消息提醒的记录数
     * @author: l13595
     * @return: 返回当天需要提醒管理员的消息记录条数
     * @time: 2017/2/15 9:48
     */
    public abstract int getMsgCount();

    /**
     * @description: 获取当天要推送给管理员的消息列表
     * @author: l13595
     * @return: 返回当天要推送给管理员的消息列表
     * @time: 2017/2/15 9:49
     */
    public abstract List<Map<String,Object>> getMsgForManager();

     /**
      * @description: 查询安全审计的信息
      * @author: l13595
      * @time: 2017/5/15 16:02
      */
    public Map<String,Object> getSecurityTime();

     /**
      * @description: 用户错误次数加1，变更用户状态（sql中判断条件）
      * @author: l13595
      * @param userName: 用户名称
      * @time: 2017/5/15 16:05
      */
    public abstract void updateUserErrorNum(String userName);

}
