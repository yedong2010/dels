package com.dels.model.monitor;

/**
 * 
 * @description 核心统计表-（三率一数）数据模型
 * @author z11595
 * @time 2016年12月22日 下午3:01:38
 */
public class CommonModel {
    private String name;// 组织机构名称
    private String sname;// 组织机构简称
    private String wsqlcsxs;// 网上全流程事项数
    private String sxzs;// 事项总数(行政审批事项)
    private String shfwsxzs;// 公共服务事项总数(公共服务事项)
    private String wsqlcbll;// 网上全流程办理率
    private String swblzs;// 上网申办且办结业务量
    private String wsqlcbjzs;// 网上全流程办理且办结业务量
    private String bjzs;// 总业务量
    private String swbll;// 上网办理率
    private String wsbjl;// 网上办结率

    private Integer pdcs;// 跑动次数总数
    private Integer pdcs0;// 零次跑动次数
    private Integer pdcs1;// 一次跑动次数
    private Integer pdcs2;// 二次跑动次数
    private Integer pdcs3;// 二次以上跑动次数

    private String zb0;// 零次跑动占比
    private String zb1;// 一次跑动占比
    private String zb2;// 二次跑动占比
    private String zb3;// 二次以上跑动占比

    private String shfwswblzs;
    private String shfwbjzs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWsqlcsxs() {
        return wsqlcsxs;
    }

    public void setWsqlcsxs(String wsqlcsxs) {
        this.wsqlcsxs = wsqlcsxs;
    }

    public String getSxzs() {
        return sxzs;
    }

    public void setSxzs(String sxzs) {
        this.sxzs = sxzs;
    }

    public String getShfwsxzs() {
        return shfwsxzs;
    }

    public void setShfwsxzs(String shfwsxzs) {
        this.shfwsxzs = shfwsxzs;
    }

    public String getWsqlcbll() {
        return wsqlcbll;
    }

    public void setWsqlcbll(String wsqlcbll) {
        this.wsqlcbll = wsqlcbll;
    }

    public String getSwblzs() {
        return swblzs;
    }

    public void setSwblzs(String swblzs) {
        this.swblzs = swblzs;
    }

    public String getWsqlcbjzs() {
        return wsqlcbjzs;
    }

    public void setWsqlcbjzs(String wsqlcbjzs) {
        this.wsqlcbjzs = wsqlcbjzs;
    }

    public String getBjzs() {
        return bjzs;
    }

    public void setBjzs(String bjzs) {
        this.bjzs = bjzs;
    }

    public String getSwbll() {
        return swbll;
    }

    public void setSwbll(String swbll) {
        this.swbll = swbll;
    }

    public String getWsbjl() {
        return wsbjl;
    }

    public void setWsbjl(String wsbjl) {
        this.wsbjl = wsbjl;
    }

    public Integer getPdcs() {
        return pdcs;
    }

    public void setPdcs(Integer pdcs) {
        this.pdcs = pdcs;
    }

    public Integer getPdcs0() {
        return pdcs0;
    }

    public void setPdcs0(Integer pdcs0) {
        this.pdcs0 = pdcs0;
    }

    public Integer getPdcs1() {
        return pdcs1;
    }

    public void setPdcs1(Integer pdcs1) {
        this.pdcs1 = pdcs1;
    }

    public Integer getPdcs2() {
        return pdcs2;
    }

    public void setPdcs2(Integer pdcs2) {
        this.pdcs2 = pdcs2;
    }

    public Integer getPdcs3() {
        return pdcs3;
    }

    public void setPdcs3(Integer pdcs3) {
        this.pdcs3 = pdcs3;
    }

    public String getZb0() {
        return zb0;
    }

    public void setZb0(String zb0) {
        this.zb0 = zb0;
    }

    public String getZb1() {
        return zb1;
    }

    public void setZb1(String zb1) {
        this.zb1 = zb1;
    }

    public String getZb2() {
        return zb2;
    }

    public void setZb2(String zb2) {
        this.zb2 = zb2;
    }

    public String getZb3() {
        return zb3;
    }

    public void setZb3(String zb3) {
        this.zb3 = zb3;
    }

    public String getShfwswblzs() {
        return shfwswblzs;
    }

    public void setShfwswblzs(String shfwswblzs) {
        this.shfwswblzs = shfwswblzs;
    }

    public String getShfwbjzs() {
        return shfwbjzs;
    }

    public void setShfwbjzs(String shfwbjzs) {
        this.shfwbjzs = shfwbjzs;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
