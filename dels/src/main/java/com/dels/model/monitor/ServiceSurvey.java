package com.dels.model.monitor;

/**
 * 
 * @description 业务办理数据模型
 * @author z11595
 * @time 2016年12月22日 下午3:03:38
 */
public class ServiceSurvey {
    private String wssb;// 网上申办业务量
    private String sl;// 网上受理业务量
    private String bsl;// 网上不受理业务量
    private String zcbj;// 网上正常办结数
    private String abnormals;// 异常办结数
    public String getWssb() {
        return wssb;
    }
    public void setWssb(String wssb) {
        this.wssb = wssb;
    }
    public String getSl() {
        return sl;
    }
    public void setSl(String sl) {
        this.sl = sl;
    }
    public String getBsl() {
        return bsl;
    }
    public void setBsl(String bsl) {
        this.bsl = bsl;
    }
    public String getZcbj() {
        return zcbj;
    }
    public void setZcbj(String zcbj) {
        this.zcbj = zcbj;
    }
    public String getAbnormals() {
        return abnormals;
    }
    public void setAbnormals(String abnormals) {
        this.abnormals = abnormals;
    }

}
