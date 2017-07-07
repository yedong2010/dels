package com.dels.model.sys;

/**
 * Created by l13595 on 2017/3/28.
 * excel 模板
 */
public class ExcelTmplate {
    private String id 		; //
    private String name;   //模板名称
    private String content  ; //模板说明
    private String groupId  ; //分组id
    private String groupName; //分组名称
    private String cuser    ; //创建用户
    private String ctime    ; //创建时间


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
