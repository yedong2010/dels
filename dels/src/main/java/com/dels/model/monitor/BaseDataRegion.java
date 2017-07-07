package com.dels.model.monitor;

/**
 * @author: l13608
 * @date:2017-1-12
 * 基础数据区域
 */

public class BaseDataRegion {
    private  String XZQHDM; //行政区划代码
    private  String XZQHQC; //行政区划全称
    private  Integer SJDM;//市级代码
    private  String SJMC; //市级名称，从XZQHQC中手工获取
    private  Integer QYDM; //区域代码，0-市本身，1-市辖区,2~N表示各个下属区、县、市级县
    private  String QYMC; //区域名称，从XZQHQC中手工获取
    private  Integer QYJBDM; //区域级别代码  见dim_arealevel表
    private  String QYJBMC; //区域级别/部门级别名称： 市直部门
    private  Integer qybs; //区域划分(-1代表珠三角，0代表粤东西北)
    private Integer dssx;
    private  int qxsx; //区域划分(-1代表珠三角，0代表粤东西北)
    private  String originalQYMC; //未修改原来的区域名称

    public String getXZQHDM() {
        return XZQHDM;
    }

    public Integer getDssx() {
        return dssx;
    }

    public void setDssx(Integer dssx) {
        this.dssx = dssx;
    }

    public void setXZQHDM(String XZQHDM) {
        this.XZQHDM = XZQHDM;
    }

    public String getXZQHQC() {
        return XZQHQC;
    }

    public void setXZQHQC(String XZQHQC) {
        this.XZQHQC = XZQHQC;
    }

    public Integer getSJDM() {
        return SJDM;
    }

    public void setSJDM(Integer SJDM) {
        this.SJDM = SJDM;
    }

    public String getSJMC() {
        return SJMC;
    }

    public void setSJMC(String SJMC) {
        this.SJMC = SJMC;
    }

    public Integer getQYDM() {
        return QYDM;
    }

    public void setQYDM(Integer QYDM) {
        this.QYDM = QYDM;
    }

    public String getQYMC() {
        return QYMC;
    }

    public void setQYMC(String QYMC) {
        this.QYMC = QYMC;
    }

    public Integer getQYJBDM() {
        return QYJBDM;
    }

    public void setQYJBDM(Integer QYJBDM) {
        this.QYJBDM = QYJBDM;
    }

    public String getQYJBMC() {
        return QYJBMC;
    }

    public void setQYJBMC(String QYJBMC) {
        this.QYJBMC = QYJBMC;
    }

    public Integer getQybs() {
        return qybs;
    }

    public void setQybs(Integer qybs) {
        this.qybs = qybs;
    }

    public int getQxsx() {
        return qxsx;
    }

    public void setQxsx(int qxsx) {
        this.qxsx = qxsx;
    }

    public String getOriginalQYMC() {
        return originalQYMC;
    }

    public void setOriginalQYMC(String originalQYMC) {
        this.originalQYMC = originalQYMC;
    }
}
