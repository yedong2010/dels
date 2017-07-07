package com.dels.model.monitor;

/**
 * Created by l13595 on 2017/3/14.
 */
public class DataMonitorModel {
    private String blsj;         // 办理时间
    private  int blsc;           //办理时长
    private int ywzl;            // 业务总量
    private int ywsl;            // 业务受理
    private int ywbj;            // 业务办结
    private String xzqhdm;       // 行政区划代码
    private String department;   // 机构名称


    /** 查询参数 */
    private String startTime;     //开始时间
    private String endTime;       //结束时间
    private String lastMonth;     //时间月份 格式“yyyy-MM”

    public int getYwzl() {
        return ywzl;
    }

    public String getBlsj() {
        return blsj;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(String lastMonth) {
        this.lastMonth = lastMonth;
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

    public void setBlsj(String blsj) {
        this.blsj = blsj;
    }

    public int getBlsc() {
        return blsc;
    }

    public void setBlsc(int blsc) {
        this.blsc = blsc;
    }

    public void setYwzl(int ywzl) {
        this.ywzl = ywzl;
    }

    public int getYwsl() {
        return ywsl;
    }

    public void setYwsl(int ywsl) {
        this.ywsl = ywsl;
    }

    public int getYwbj() {
        return ywbj;
    }

    public void setYwbj(int ywbj) {
        this.ywbj = ywbj;
    }


    public String getXzqhdm() {
        return xzqhdm;
    }

    public void setXzqhdm(String xzqhdm) {
        this.xzqhdm = xzqhdm;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
