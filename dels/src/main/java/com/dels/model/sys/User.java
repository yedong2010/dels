package com.dels.model.sys;

import java.util.Date;

/**
 * 
 * @description 用户数据模型
 * @author z11595
 * @time 2016年12月22日 下午4:04:24
 */
public class User {

    /*客户用用户访问的ip地址 l13595 2017/1/12 17:53*/

    private String id;
    private String uname;// 用户名称
    private String passwd;// 用户密码
    private String email;// 用户邮箱
    private String descr;// 用户描述
    private Integer status;// 状态
    private Date cdate;// 创建时间
    private String roleName; //角色名称-查询参数
    private String userIp; //用户IP地址

    private String phoneNumer; //用户手机号

    private String dep_id;//部门或地市id
    private String department;//地市或部门名称
    private String type;//部门或地市类型【province,city】

    /*用户角色关联表属性*/
    private String role_id; //角色id
    private String user_id;//用户id

    public String getId() {
        return id;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public String getDep_id() {
        return dep_id;
    }

    public String getPhoneNumer() {
        return phoneNumer;
    }

    public void setPhoneNumer(String phoneNumer) {
        this.phoneNumer = phoneNumer;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }
}
