package com.dels.model.sys;

/**
 * Created by l13595 on 2017/2/16.
 * 操作文件信息
 */

public class FileInfo {
    private  String id;
    private String address;  //文件路径
    private String fileName;  // 文件名称
    private String userName;  // 用户名
    private String createTime;  //创建时间

    private String fileType;   /** 文件类型名称 l13595 2017/2/24 11:43 **/
    private String typeId;  /** 文件类型id l13595 2017/2/24 11:43 **/


    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
