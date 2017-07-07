package com.dels.model.dataIndexSys;

/**
 * Created by l13595 on 2017/3/17.
 * 数据指标体系
 *    预处理规则模型
 */
public class DataIndexModel {
    private String id ;				// id

    /** 预处理规则 **/
    private String rname;           // 规则名称
    private String rcontent;        // 规则内容
    private String groupId ;       // 分组id
    private String groupName;      // 分组名称

    /** 分析主题库 **/
    private String tName;        // 主题名称
    private String tContent;     // 主题内容
    private String themeId;     // 主题库id
    private String theme;       // 主题库名称

    /**指标提示*/
    private String zbid;        //指标id
    private String zbmc;        //指标名称
    private String gzid;        //规则id
    private String gzmc;        //计算规则名称
    private String jsgs;        //计算公式

    private String gzcsid;      //规则参数id
    private String csid;        //参数id
    private String csmc;        //参数名称
    private int cswz;        //参数位置

    private String cuser ;          // 创建用户
    private String ctime;           // 创建时间


    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String getZbid() {
        return zbid;
    }

    public String getGzid() {
        return gzid;
    }

    public String getCsid() {
        return csid;
    }

    public void setCsid(String csid) {
        this.csid = csid;
    }

    public String getCsmc() {
        return csmc;
    }

    public void setCsmc(String csmc) {
        this.csmc = csmc;
    }

    public int getCswz() {
        return cswz;
    }

    public void setCswz(int cswz) {
        this.cswz = cswz;
    }

    public void setGzid(String gzid) {
        this.gzid = gzid;
    }

    public void setZbid(String zbid) {
        this.zbid = zbid;
    }

    public String getGzmc() {
        return gzmc;
    }

    public void setGzmc(String gzmc) {
        this.gzmc = gzmc;
    }

    public String getJsgs() {
        return jsgs;
    }

    public void setJsgs(String jsgs) {
        this.jsgs = jsgs;
    }

    public String gettContent() {
        return tContent;
    }

    public void settContent(String tContent) {
        this.tContent = tContent;
    }

    public String getThemeId() {
        return themeId;
    }

    public String getZbmc() {
        return zbmc;
    }

    public void setZbmc(String zbmc) {
        this.zbmc = zbmc;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRcontent() {
        return rcontent;
    }

    public void setRcontent(String rcontent) {
        this.rcontent = rcontent;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCuser() {
        return cuser;
    }

    public void setCuser(String cuser) {
        this.cuser = cuser;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
}
