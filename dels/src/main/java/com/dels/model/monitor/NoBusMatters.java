package com.dels.model.monitor;

/**
 * 
 * @description 无业务事项数据模型
 * @author z11595
 * @time 2016年12月22日 下午3:02:43
 */
public class NoBusMatters {
    private String name;// 归属名称
    private String sname;// 归属简称
    private String wywsxs;// 无业务事项数
    private String jzsxs;// 进驻事项数
    private String wywsxl;// 无业务事项率

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWywsxs() {
        return wywsxs;
    }

    public void setWywsxs(String wywsxs) {
        this.wywsxs = wywsxs;
    }

    public String getJzsxs() {
        return jzsxs;
    }

    public void setJzsxs(String jzsxs) {
        this.jzsxs = jzsxs;
    }

    public String getWywsxl() {
        return wywsxl;
    }

    public void setWywsxl(String wywsxl) {
        this.wywsxl = wywsxl;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
