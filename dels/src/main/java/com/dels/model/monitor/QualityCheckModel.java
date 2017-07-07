package com.dels.model.monitor;

import java.util.List;

/**
 * Created by l13595 on 2017/3/13.
 *   数据质量检测：
 *      数据质量核查反馈
 *      数据质量申诉
 *      数据质量修正
 */
public class QualityCheckModel {

    public final static String STATE_FEED = "feed";                //处理状态：反馈
    public final static String STATE_REJECT = "reject";            //处理状态：驳回
    public final static String STATE_WAIT = "wait";                //处理状态：待修正
    public final static String STATE_CORRECT = "corrected";        //处理状态：已修正

    private String id;
    private String title;     //标题
    private String question;  //录入的问题
    private String resultMsg; //处理的结果
    private String state;     //状态【feed-反馈，reject-驳回，wait-待修正，corrected-已修正】
    private String editUser;  //问题创建的用户
    private String editTime;  //问题创建的时间
    private String appealUser;//处理申诉的用户
    private String appealTime;//处理申诉的时间
    private String resolveUser;//修正负责人
    private String resolveTime;//修正处理时间
    private List<String> states;//状态集合


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getAppealUser() {
        return appealUser;
    }

    public void setAppealUser(String appealUser) {
        this.appealUser = appealUser;
    }

    public String getAppealTime() {
        return appealTime;
    }

    public void setAppealTime(String appealTime) {
        this.appealTime = appealTime;
    }

    public String getResolveUser() {
        return resolveUser;
    }

    public void setResolveUser(String resolveUser) {
        this.resolveUser = resolveUser;
    }

    public String getResolveTime() {
        return resolveTime;
    }

    public void setResolveTime(String resolveTime) {
        this.resolveTime = resolveTime;
    }
}
