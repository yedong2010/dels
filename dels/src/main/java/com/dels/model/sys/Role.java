package com.dels.model.sys;

/**
 * @author: liuchengjie 13595
 * @date:2016-12-26
 */

public class Role {
    //角色表属性
    private  String id;  //角色id
    private  String rol_name;  //角色名称
    private  String can_del;  //角色是否可以删除【0-否，1-是】：系统管理员、数据分析师暂定为不能删除的固定角色

    //角色关联菜单表属性  dms_role_menu
    private  String role_id; //关联菜单角色id
    private  String menu_id; //菜单id


    public String getId() {
        return id;
    }

    public String getRol_name() {
        return rol_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRol_name(String rol_name) {
        this.rol_name = rol_name;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }
    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getCan_del() {
        return can_del;
    }

    public void setCan_del(String can_del) {
        this.can_del = can_del;
    }
}
