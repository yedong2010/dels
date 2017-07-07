package com.dels.model.sys;

/**
 * @author: liuchengjie 13595
 * @date:2017-1-4
 * 日志审计
 */

public class OperateLog {
    private String id; //记录id
    private String operation; //操作记录
    private String user_name;//操作用户

    /*添加属性 l13595 2017/1/12 17:46*/
    private String className; //方法路径
    private String methodName; //方法名称
    private String ipAdress; //客户端IP地址

    private String startTime; //查询条件：开始时间点
    private String endTime; // 查询条件：结束时间点
    private String param;//查询条件：模糊查询匹配参数

    /*分页  l13595 2017/1/17 15:24*/
    private int size;
    private Integer index;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {

        this.operation = operation;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
