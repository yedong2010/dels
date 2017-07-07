package com.dels.model.sys;
/**
 * Created by l13595 on 2017/1/16.
 */

/**
 * @description：方案信息
 * @author: l13595
 * @date:2017-01-16
 */
public class PlanInfo {
    private String id;  //id
    private String name;//方案名称
    private String userName; //创建用户名称
    private String descr;//方案描述
    private String createTime;//方案创建时间
    private String state;//是否启用【0-不启用，1-启用。默认为1】
    private String colName; //字段名称
    private String commentName;  //字段中文名
    private String passLevel;  //合格率【%】
    private String color; //颜色设置
    private String planId;  //方案id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescr() {
        return descr;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

    public String getPassLevel() {
        return passLevel;
    }

    public void setPassLevel(String passLevel) {
        this.passLevel = passLevel;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
