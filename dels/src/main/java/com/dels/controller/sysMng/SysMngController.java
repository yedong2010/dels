package com.dels.controller.sysMng;

import com.dels.model.sys.*;
import com.dels.security.UPEncode;
import com.dels.service.IOperateLogService;
import com.dels.service.ISaftyMessageService;
import com.dels.service.ISysMngService;
import com.dels.utils.FileUtil;
import com.dels.utils.LogDescription;
import com.dels.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * @author liuchengjie
 * @description 系统设置管理
 * @time 2016年12月23日
 */
@RestController
@RequestMapping("/sysMng/")
public class SysMngController {

    @Autowired
    ISysMngService service;

    @Autowired
    IOperateLogService logService;

    @Autowired
    ISaftyMessageService msgService;

    /**
     * @description:查询角色信息列表
     * @author: l13595
     * @param:
     * @return:List-角色id、名称
     * @time: 2017/1/11 14:59
     */
    @RequestMapping(value = "roleList")
    public List<Map<String, Object>> getRoleList() {
        List<Map<String, Object>> list = service.getRoleList();
        return list;
    }

     /**
      * @description: 获取角色列表，列表中不包含系统管理员、安全管理员、审计员
      * @author: l13595
      * @return:返回角色列表
      * @time: 2017/5/18 17:46
      */
    @RequestMapping(value = "roleLists")
    public List<Map<String, String>> findRoleList(){
        return service.findRoleList();
    }

     /**
      * @description: 获取省直部门名称、地市名称
      * @author: l13595
      * @return: 返回省直部门名称列表、地市名称列表
      * @time: 2017/5/18 17:48
      */
    @RequestMapping(value = "baseDataList")
    public Map<String, Object> findBaseDataList(){
        return service.findBaseDataList();
    }

    /**
     * @description:查询用户信息列表
     * @author: l13595
     * @param:
     * @return:List-用户id、名称、type、密码、邮箱、备注、角色id
     * @time: 2017/1/11 14:58
     */
    @RequestMapping(value = "userList")
    public List<Map<String, Object>> getUserList(User user) {
        List<Map<String, Object>> list = service.getUserList(user);
        return list;
    }

    /**
     * @description:获取菜单列表,增加角色页面使用
     * @author: l13595
     * @param:
     * @return:list-菜单url，菜单名称，菜单id
     * @time: 2017/1/11 15:04
     */
    @RequestMapping(value = "roleAddForm")
    public List<Map<String, Object>> roleAddForm() {
        List<Map<String, Object>> list = service.getMenuList();
        return list;
    }

    /**
     * @description:获取角色菜单，修改角色页面使用
     * @author: l13595
     * @param:roleId-角色id
     * @return:List 菜单url，名称，id
     * @time: 2017/1/11 15:06
     */
    @RequestMapping(value = "roleMenuList")
    public List<String> roleMenuList(@RequestParam(value = "roleId") String roleId) {
        List<String> list = service.getRoleMenList(roleId);
        return list;
    }

    /**
     * @description:增加角色
     * @author: l13595
     * @param roleName：角色名称
     * @param menuId：菜单id
     * @return: 操作的结果：ok-操作成功   其它-操作失败的提示信息
     * @time: 2017/1/11 15:08
     */
    @RequestMapping(value = "saveRoleInfo", produces = { "application/json;charset=UTF-8" })
    public Map<String, String> saveRoleInfo(@RequestParam(value = "role_name") String roleName, @RequestParam(value = "role_Jurisdiction") String[] menuId) {
        Map<String, String> rep = new HashMap<>();
        if(!service.checkRoleName(roleName)){
            rep.put("resultMsg", "角色名已存在，请重新添加");
            return rep;
        }
        String roleId = Utils.getUuid();
        String resultMsg = service.addRole(roleName, roleId);
        if ("ok".equals(resultMsg)) {
            resultMsg = service.addRoleMent(roleId, menuId);
        }
        rep.put("resultMsg", resultMsg);
        return rep;
    }

     /**
       * @description: 校验增加角色名称的唯一性
       * @author: l13595
       * @param roleName: 角色名称
       * @return: 返回校验结果，角色名称存在，返回false；角色名称不存在，返回true
       * @time: 2017/4/24 11:13
       */
    @RequestMapping(value = "checkAddRole")
    public Map<String, Boolean> checkAddRole(@RequestParam("addRoleName") String roleName){
        Map<String, Boolean>  rep = new HashMap<>();
        rep.put("resultMsg", service.checkRoleName(roleName));
        return rep;
    }

     /**
       * @description: 校验编辑角色名称的唯一性
       * @author: l13595
       * @param roleId: 角色id
       * @param roleName: 角色名称
       * @return: 存在，返回false； 不存在，返回true
       * @time: 2017/4/18 10:53
       */
     @RequestMapping(value = "checkUpdateRole")
    public Map<String, Boolean> checkRoleForUpdate(String roleId, @RequestParam("editRoleName")String roleName){
        Map<String, Boolean>  rep = new HashMap<>();
        rep.put("resultMsg", service.checkUpdateRoleName(roleId, roleName));
        return rep;
    }

    /**
     * @description:编辑角色
     * @author: l13595
     * @param roleName：角色名称
     * @param menuId：菜单id数组
     * @return:Map
     * @time: 2017/1/11 15:08
     */
    @RequestMapping(value = "upadteRoleInfo", produces = { "application/json;charset=UTF-8" })
    public Map<String, String> upadteRoleInfo(@RequestParam(value = "role_name") String roleName, @RequestParam(value = "roleId") String roleId,
            @RequestParam(value = "role_Jurisdiction") List<String> menuId) {

        Map<String, String> rep = new HashMap<>();
        Role role = new Role();
        role.setId(roleId);
        role.setRol_name(roleName);

        if(!service.checkUpdateRoleName(roleId, roleName)){
            rep.put("resultMsg", "角色名称已存在");
            return rep;
        }

        String resultMsg = service.updateRoleInfo(role);

        if("1".equals(roleId)){  // 系统管理员（id为1），默认添加用户管理、角色管理菜单
            menuId.add("3");
            menuId.add("4");
            menuId.add("5");
        }

        service.updateRoleMenu(roleId, menuId); /* 更新角色菜单 l13595 2017/4/14 16:34*/

        rep.put("resultMsg", resultMsg);
        return rep;
    }
    /* public Map<String, String> upadteRoleInfo(@RequestParam(value = "role_name") String roleName, @RequestParam(value = "roleId") String roleId,
            @RequestParam(value = "role_Jurisdiction") String[] menuId) {
        Role role = new Role();
        role.setId(roleId);
        role.setRol_name(roleName);
        String resultMsg = service.updateRoleInfo(role);
        if ("ok".equals(resultMsg)) {
            resultMsg = service.delRolMenu(roleId);
            if ("ok".equals(resultMsg)) {
                resultMsg = service.addRoleMent(roleId, menuId);
            }
        }
        Map<String, String> rep = new HashMap<>();
        rep.put("resultMsg", resultMsg);
        return rep;
    }*/

    /**
     * @description:获取用户导航栏菜单
     * @author: l13595
     * @param:
     * @return:页面导航栏菜单
     * @time: 2017/1/11 15:09
     */
    @RequestMapping(value = "ParentMenu")
    public Map<String, Object> getParentMenuList() {

        Map rep = new HashMap();
        List<Map<String, String>> list = service.getParentMenuList(Utils.getCurUserName());
         /*地市用户隐藏“数据监测”菜单   l13595 2017/3/3 9:36*/
       /* if(null!=list&&list.get(0).get("type").equals("city")){
            for(int i=0;i<list.size();i++){
                if(list.get(i).get("url").equals("quality")){
                    list.remove(i);
                    break;
                }
            }
        }*/
        rep.put("menu", list);
        rep.put("loginName", Utils.getCurUserName());
        return rep;
    }

    /**
     * @description:获取侧边栏菜单
     * @author: l13595
     * @param:
     * @return:左侧菜单
     * @time: 2017/1/11 15:10
     */
    @RequestMapping(value = "menuInfo")
    public Map<String, Object> getMenuList() {
        List<Map<String, String>> pMenu = service.getParentMenuList(Utils.getCurUserName());
        /*地市用户隐藏“数据监测”菜单  l13595 2017/3/3 10:28 */
       /* if(null!=pMenu&&pMenu.get(0).get("type").equals("city")){
            for(int i=0;i<pMenu.size();i++){
                if(pMenu.get(i).get("url").equals("quality")){
                    pMenu.remove(i);
                    break;
                }
            }
        }*/
        Map<String, Object> dataMap = service.getUserMenuList(pMenu);
        return dataMap;
    }

    /**
     * @description:校验用户名称是否存在
     * @author: l13595
     * @param: user属性-用户名称
     * @return:true-不存在,名称可以使用
     *             false-名称已经存在，不能使用
     * @time: 2017/1/11 15:10
     */
    @RequestMapping(value = "checkUserName")
    public Map<String,Object> checkUserName(User user){
        boolean resultMsg = service.checkUserName(user.getUname());
        Map<String,Object> rep = new HashMap<>();
        rep.put("resultMsg",resultMsg);
        return rep;
    }

    /**
     * @description:增加用户
     *        l13595 2017/5/18 9:36  三权分离改造：增加用户不添加角色和组织机构
     * @author l13595
     * @param user:用户名称uname、省级地市类型 type、角色id role_id、省级地市信息 department、  用户邮箱 email、密码 passwd、用户备注descr
     * @return: ok-用户增加成功 其他-增加失败，并反馈失败提示信息
     * @time 2017/1/11 9:57
     */
    @RequestMapping(value = "addUser", produces = { "application/json;charset=UTF-8" })
    public Map<String, String> addUser(User user) {
        Map<String, String> rep = new HashMap<>();

        //判断手机号是否唯一
        if(null != user && null != user.getPhoneNumer()){
            if(!service.chargeUserPhone(user.getPhoneNumer(), null)){
                rep.put("resultMsg", "用户手机号已经存在");
                return rep;
            }
        }

        if(!service.checkUserName(user.getUname())){
            rep.put("resultMsg", "用户已存在");
        }else{
            user.setId(Utils.getUuid());
            UPEncode e = new UPEncode();
            user.setPasswd(e.encode(user.getPasswd()));
            rep.put("resultMsg", service.addUser(user));
        }

        return rep;
    }

    /**
     * @description:修改用户
     *      三权分离，修改用户功能中不涉及组织机构和角色的配置  l13595 2017/5/18 10:05
     * @author l13595
     * @param user:用户表信息
     * @return： ok-用户增加成功 其他-增加失败，并反馈失败提示信息
     * @time 2017/1/11 9:57
     */
    @RequestMapping(value = "updateUser", produces = { "application/json;charset=UTF-8" })
    public Map<String, String> updateUserInfo(User user) {
//        if(null!=user.getRole_id()&&(!"2".equals(user.getRole_id()))){ /* 分析师以外的角色，不设置省级地市部门  l13595 2017/3/6 17:13  */
//            user.setDepartment("");
//        }
        Map<String, String> rep = new HashMap<>();

        if(null != user && null != user.getPhoneNumer()){
            if(null == user.getId()){
                rep.put("resultMsg", "用户id为空！");
                return rep;
            }else if(!service.chargeUserPhone(user.getPhoneNumer(), user.getId())){
                rep.put("resultMsg", "手机号已存在！");
                return rep;
            }
        }

        String resultMsg = service.updateUserInfo(user);
        rep.put("resultMsg", resultMsg);
        return rep;
    }

    /**
     * @description:删除用户
     * @author l13595
     * @param ：user-用户id，用户名称
     * @return：ok-用户删除成功 其他-删除失败，并反馈失败提示信息
     * @time 2017/1/10 18:22
     */
    @RequestMapping(value = "deleteUser")
    public Map<String, String> deleteUser(User user) {
        Map<String, String> rep = new HashMap<>();
        String resultMsg = service.deleteUser(user);
        rep.put("resultMsg", resultMsg);
        return rep;
    }

     /**
      * @description: 修改用户状态，启用/禁用
      * @author: l13595
      * @param userId: 用户id
      * @return: 返回修改结果，修改成功，返回ok；修改失败，返回操作失败提醒
      * @time: 2017/5/12 10:15
      */
    @RequestMapping(value = "changeUserStatus", produces = {"application/json;charset=UTF-8"})
    public Map<String,String> changeUserStatus(String userId, String userName){
        Map<String, String> rep = new HashMap<>();
        if(null == userId){
            rep.put("resultMsg","用户id为空");
            return rep;
        }
        User user = new User();
        if(null != userName){
            user.setUname(userName);
        }
        user.setId(userId);
        rep.put("resultMsg",service.changeUserStatus(user));
        return rep;
    }

    /**
     * @description:删除角色
     * @author l13595
     * @param ：角色id
     * @return：ok-用户增加成功 其他-增加失败，并反馈失败提示信息
     * @time 2017/1/11 11:03
     */
    @RequestMapping(value = "deleteRole")
    public Map<String, String> deleteRole(Role role) {
        Map<String, String> rep = new HashMap<>();
        String resultMsg = service.ifDelRole(role);
        if("ok".equals(resultMsg)){
            resultMsg = service.delRoleById(role);
        }
        rep.put("resultMsg", resultMsg);
        return rep;
    }

    /**
     * @description:获取系统当前用户信息
     * @author l13595
     * @param
     * @return:Map 用户所有信息
     * @time 2017/1/11 11:04
     */
    @RequestMapping(value = "userInfo")
    public Map<String, Object> getUserInfo() {
        Map<String, Object> rep = service.getUserInfo();
        Map<String,Object> msgMap = msgService.getSecurityTime();
        rep.put("securityRole",msgMap.get("role_name"));
        return rep;
    }

    /**
     * @description:修改用户登录密码
     * @author l13595
     * @param userId：用户id
     * @param passwd：新密码
     * @param oldPasswd：旧密码
     * @return: 返回操作结果：ok-修改成功, 其他-提醒修改失败
     * @time 2017/1/11 11:05
     */
    @RequestMapping(value = "updatePassword")
    public Map<String, String> updatePassword(String userId, @RequestParam(value = "newPass") String passwd,String oldPasswd,String userName) {
        Map<String, String> rep = new HashMap<>();
        User user = new User();
        user.setId(userId);
        user.setUname(userName);
        user.setPasswd(new UPEncode().encode(oldPasswd));
        String resultMsg = service.checkUserPasswd(user);
        if(!"ok".equals(resultMsg)){
            rep.put("resultMsg", resultMsg);
            return rep;
        }
        String userName2 = service.getUserNameById(userId);
        User u2 = new User();
        u2.setId(userId);
        u2.setUname(userName2);
        u2.setPasswd(new UPEncode().encode(passwd));
        resultMsg = service.updatePasswd(u2);
        rep.put("resultMsg", resultMsg);
        return rep;
    }

     /**
      * @description: 编辑用户角色
      * @author: l13595
      * @param user: 用户id userid: 用户id;roleid: 角色id; department: 用户组织机构;  cityType: 组织机构类型
      * @return: 返回操作结果。操作成功，返回ok； 操作失败，返回操作失败提醒
      * @time: 2017/5/18 11:49
      */
    @RequestMapping(value = "updateUserRole")
    public Map<String, String> updateUserRole(User user){
        Map<String, String> rep = new HashMap<>();
        user.setUser_id(user.getId());
        service.updateUserRole(user);
        return rep;
    }

    /**
     * @description:查询操作日志列表
     * @author l13595
     * @param
     * @return:list
     * @time 2017/1/11 11:06
     */
    @RequestMapping(value = "logList")
    public Map<String,Object> getOperaLogList(OperateLog operaLog){
        Map<String,Object> param = new HashMap<>();
        if(null != operaLog.getEndTime()){
            operaLog.setEndTime(Utils.dateOperation(operaLog.getEndTime(),1));
        }
        param.put("operaLog",operaLog);
        Map<String,Object> rep = new HashMap<>();
        rep.put("list",logService.getLogList(param));
        rep.put("size",logService.countLogList(param));
        return rep;
    }

    /**
     * @description: 安全审计
     * @author: l13595
     * @return: map-安全审计消息列表，安全审计消息标准次数
     * @time: 2017/2/13 20:01
     */
    @RequestMapping(value = "msgList")
    public Map<String,Object> getMsgList(String ip,String startNum,String endNum,String startTime,String endTime){
        Map<String,Object> param = new HashMap<>();
        param.put("ip",ip==null?"":ip);
        param.put("startNum",(null==startNum||"".equals(startNum))?0: Integer.valueOf(startNum));
        param.put("endNum",(null==endNum||"".equals(endNum))?0: Integer.valueOf(endNum));
        param.put("startTime",null==startTime?"":startTime);
        param.put("endTime",null==endTime?"":endTime);

        Map<String,Object> rep = new HashMap<>();
        rep.put("list",msgService.getMsgList(param));
        rep.put("msgNum",msgService.getMsgMngNum());
        return rep;
    }

    /**
     * @description: 更新安全提醒标准次数
     * @author: l13595
     * @param : num-次数值
     * @return: 返回操作结果标识 ok-更细成功 ； 失败返回操作失败提示
     * @time: 2017/2/14 10:08
     */
    @RequestMapping(value = "updateMsgNum")
    public Map<String,String> updateMsgNum(int num, String role_id){
        Map<String,String> rep = new HashMap<>();
//        String resultMsg = msgService.updateMsgMngNum(num);
        if(num<1){
            rep.put("resultMsg","安全登录次数必须大于0");
            return rep;
        }
        String resultMsg = msgService.updateMsgMng(role_id, num);
        rep.put("resultMsg",resultMsg);
        return rep;
    }

    /**
     * @description: 获取安全审计提醒消息
     * @author: l13595
     * @return: 返回安全审计记录中错误次数不小于设定的标准的记录
     * @time: 2017/2/14 15:03
     */
    @RequestMapping(value = "msgMngList")
    public List<Map<String,Object>> getMsgMngList(){
        return msgService.getMsgMngList();
    }

    /**
     * @description: 获取当天要推送给管理员的消息列表
     * @author: l13595
     * @return:  返回消息列表
     * @time: 2017/2/15 9:53
     */
    @RequestMapping(value = "msgForMng")
    public List<Map<String,Object>> getMsgForManager(){
        return msgService.getMsgForManager();
    }

    /**
     * @description: 获取当天要推送给管理员的消息记录数
     * @author: l13595
     * @return: 返回当天要推送的消息记录数
     * @time: 2017/2/15 9:54
     */
    @RequestMapping(value = "msgCount")
    public int getMngCount(){
        return msgService.getMsgCount();
    }

    /**
     * @description: 文件上传
     * @author: l13595
     * @param request: HttpServletRequest
     * @param response: HttpServletResponse
     * @return: 返回操作结果标识 成功-ok ； 失败-返回操作失败提示
     * @time: 2017/2/16 15:24
     */
    @RequestMapping(value = "uploadFile")
    public Map<String,String> uploadFile(HttpServletRequest request,HttpServletResponse response,String typeId){
        Map<String,String> rep = new HashMap<>();
        if("".equals(typeId) || null == typeId){
            rep.put("resultMsg","文件类型不能为空");
            return rep;
        }
        String resultMsg = service.insertFileInfo(request,typeId);
        rep.put("resultMsg",resultMsg);
        return rep;
    }

    /**
     * @description: 文件信息列表
     * @author: l13595
     * @param  fileInfo: FileInfo- 查询参数设置
     * @return:  返回查询结果列表
     * @time: 2017/2/16 15:29
     */
    @RequestMapping(value = "fileInfoList")
    public List<Map<String,Object>> getFileInfoList(FileInfo fileInfo){
        return service.getFileInfoList(fileInfo);
    }

    /**
     * @description: 文件下载
     * @author: l13595
     * @param fileInfo: id-下载文件的id
     * @return: 返回下载结果标识。成功-ok，失败-返回操作失败提醒
     * @time: 2017/2/17 17:09
     */
    @RequestMapping(value = "downloadFile")
    public String downLoadFile(HttpServletRequest request,HttpServletResponse response,FileInfo fileInfo){
        if(null != fileInfo.getId() && !"".equals(fileInfo.getId())){
            String fileName = service.findFileNameById(fileInfo.getId());
            fileInfo.setFileName(fileName);
        }
        return  service.downloadFile(request, response,fileInfo);
    }

    @RequestMapping(value = "ifDownloadFile")
    public boolean ifDownloadFile(FileInfo fileInfo){
        return service.ifDownloadFile(fileInfo);
    }

    /**
     * @description: 删除文件
     * @author: l13595
     * @param fileInfo: id-文件id
     * @return: 返回下载结果。成功-ok，失败-返回操作失败提醒
     * @time: 2017/2/20 10:13
     */
    @RequestMapping(value = "delFile")
    public Map<String,String> deleteFileById(FileInfo fileInfo){
        Map<String,String> rep = new HashMap<>();
        String resultMsg = service.delFileById(fileInfo);
        rep.put("resultMsg",resultMsg);
        return rep;
    }

    /**
     * @description: 获取安全提醒标准次数
     * @author: l13595
     * @return: 返回安全提醒的标准次数
     * @time: 2017/2/28 11:00
     */
    @RequestMapping(value = "securityTime")
    public Map<String,Object> getSecurityTime(){
        return msgService.getSecurityTime();
    }

    /**
     * @description: 查询文件类型
     * @author: l13595
     * @param : fileInfo FileInfo类的属性作为查询参数
     * @return:  返回查询结果列表
     * @time: 2017/2/28 11:08
     */
    @RequestMapping(value = "fileTypeList")
    public List<Map<String,Object>> getFileTypeList(FileInfo fileInfo){
        List<Map<String,Object>> list = new ArrayList<>();
        list = service.getFileTypeList(fileInfo);
        return list;
    }

    /**
     * @description: 校验文件类型名称的唯一性
     * @author: l13595
     * @param : fileInfo.fileName 文件名作为查询参数
     * @return: true-文件类型不存在；  false-文件类型已经存在
     * @time: 2017/2/28 11:09
     */
    @RequestMapping(value = "checkFileType")
    public Map<String,Boolean> checkFielType(String addFType){
        Map<String,Boolean> rep = new HashMap<>();
        if(null==addFType){
            addFType="";
        }
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileType(addFType);
        rep.put("resultMsg",service.checkFileType(fileInfo));
        return rep;
    }

    /**
     * @description: 新增文件类型
     * @author: l13595
     * @param : fileInfo 文件id，文件类型名称fileName，创建用户userName
     * @return:  返回新增结果标识   成功-ok，失败-返回新增失败提醒
     * @time: 2017/2/28 11:26
     */
    @RequestMapping(value = "addFileType")
    public Map<String,String> insertFileType(FileInfo fileInfo){
        String resultMsg = service.insertFileType(fileInfo);
        Map<String,String> rep = new HashMap<>();
        rep.put("resultMsg",resultMsg);
        return rep;
    }

    @RequestMapping(value = "editFileType")
    public Map<String,String> editFileType(FileInfo fileInfo){
        String resultMsg = service.editFileType(fileInfo);
        Map<String,String> rep = new HashMap<>();
        rep.put("resultMsg",resultMsg);
        return rep;
    }

    /**
     * @description: 删除文件类型
     * @author: l13595
     * @param : fileInfo 文件类型id
     * @return: 返回删除结果标识， 成功-ok，失败-返回删除失败提醒
     * @time: 2017/2/28 11:32
     */
    @RequestMapping(value = "delFielType")
    public Map<String,String> delFileType(FileInfo fileInfo){
        String typeName = service.findTypeNameById(fileInfo.getId());
        fileInfo.setFileType(typeName);
        String resultMsg = service.delFileTYpe(fileInfo);
        Map<String,String> rep = new HashMap<>();
        rep.put("resultMsg",resultMsg);
        return rep;
    }

    /**
     * @description: 查询用户权限列表
     * @author: l13595
     * @return: 返回用户权限列表
     * @time: 2017/3/3 15:37
     */
    @RequestMapping(value = "permissionsList")
    public Map<String,List> getUserPermissions(){
        List<Map<String,Object>> list = service.getUserPermissions();
        Map<String,List> rep = new HashMap<>();
        rep.put("list",list);
        return rep;
    }

    /**
     * @description: 查询excel模板列表
     * @author: l13595
     * @param entity:  查询字段参数
     * @return: 返回模板列表结果
     * @time: 2017/3/28 15:04
     */
    @RequestMapping(value = "excelTmp")
    public List<Map<String, Object>> excelTmp(ExcelTmplate entity){
        return service.findExcelTmpList(entity);
    }

    /**
     * @description: 查询excel模板分组列表
     * @author: l13595
     * @param entity: 查询字段参数
     * @return: 返回模板分组列表
     * @time: 2017/3/28 15:05
     */
    @RequestMapping(value = "excelGroup")
    public List<Map<String, Object>> excelGroupList(ExcelTmplate entity){
        return service.findExcelGroupList(entity);
    }

    /**
     * @description: 文件上传
     * @author: l13595
     * @param request: HttpServletRequest
     * @param uploadTmpId: 上传模板的id
     * @return: 返回文件上传结果。
     * @time: 2017/3/28 20:46
     */
    @LogDescription(description = "上传报表模板", pOrder = 1)
    @RequestMapping(value = "uploadExcel", produces = "text/html; charset=utf-8")
    public String uploadExcelTmp(MultipartFile file, HttpServletRequest request, String uploadTmpId){
        String fileName = service.findExcelNameById(uploadTmpId);

        if(null==fileName){
            return "文件名称获取失败";
        }else if(file.isEmpty()){
            return "上传文件为空";
        }else if(file.getSize() > (10*1024*1024)){
            return "文件大于10M";
        } else if (!fileName.equals(file.getOriginalFilename())){
            return "报表模板名称格式错误";
        }else {

            long filesize = file.getSize();
            String filePath = Thread.currentThread().getContextClassLoader().getResource("excel").getPath();
            File excelPath = new File(filePath);
            if((!excelPath.exists())&&(!excelPath.isDirectory())){
                excelPath.mkdir();
            }

            String resultMsg = service.deleteExcelFile(fileName, filePath);
            if(!"ok".equals(resultMsg)){
                return resultMsg;
            }else{
                String uploadPath = filePath + file.getOriginalFilename();
                try {
                    file.transferTo(new File(uploadPath));
                    service.saveExcelTmp(uploadTmpId);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "上传模板错误，请联系管理员";
                }
            }

        }

//        rep.put("resultMsg", FileUtil.uploadFile(request, fileName, filePath));
        return  "ok";
    }

    /**
     * @description: 删除模板文件
     * @author: l13595
     * @param id: 模板文件id
     * @return: 返回删除文件结果
     * @time: 2017/3/29 9:29
     */
    @RequestMapping("deleteExcelTmp")
    public Map<String, String> deleteExcelTmp(HttpServletRequest request, String id){
        Map<String, String> rep = new HashMap<>();
        if(null != id){
            String fileName = service.findExcelNameById(id);
            String filepath = request.getSession().getServletContext().getRealPath("excel");
            String resultMsg = service.deleteExcelTmp(id);
            if("ok".equals(resultMsg)){
                service.deleteExcelFile(fileName, filepath);
            }
            rep.put("resultMsg", resultMsg);
        }else{
            rep.put("resultMsg", "文件id为空");
        }

        return rep;
    };

    /**
     * @description: 判断要下载的文件是否存在
     * @author: l13595
     * @param id: 要下载的文件的id
     * @return: 文件存在，返回ok；文件不存在，返回文件不存在的提示
     * @time: 2017/3/29 9:33
     */
    @RequestMapping(value = "ifDownloadExcelFile")
    public Map<String, Object> getExcelNameById(HttpServletRequest request, String id){
        String filePath = Thread.currentThread().getContextClassLoader().getResource("excel").getPath();
        Map<String, Object> rep = new HashMap<>();
        String fileName = service.findExcelNameById(id);
        if(null != fileName){
            String realPath = filePath + fileName;
            File file = new File(realPath);
            if(file.exists()){
                rep.put("resultMsg", "ok");
            }else{
                rep.put("resultMsg", "文件不存在");
            }
        }else{
            rep.put("resultMsg", "文件不存在");
        }
        return rep;
    }

    /**
     * @description: excel 模板下载
     * @author: l13595
     * @param response: HttpServletResponse
     * @param request: HttpServletRequest
     * @param id: 要下载的文件id
     * @time: 2017/3/29 9:48
     */
    @RequestMapping(value = "downloadExcel")
    public void downloadExcel(HttpServletResponse response, HttpServletRequest request, String id){
        String filePath = Thread.currentThread().getContextClassLoader().getResource("excel").getPath();
        String fileName = service.findExcelNameById(id);
        try {
            FileUtil.downloadFile(filePath, fileName, request, response);

        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * @description: 校验模板分组的名称是否存在
     * @author: l13595
     * @param addExcelGroup: 分组名称
     * @return: 存在，返回true； 不存在，返回false
     * @time: 2017/3/29 10:30
     */
    @RequestMapping(value = "checkExcelGroup")
    public Map<String, Boolean> checkExcelGroup(String addExcelGroup){
        Map<String, Boolean> rep = new HashMap<>();
        rep.put("resultMsg", service.checkInsertExcelGroup(addExcelGroup));
        return rep;
    }

    /**
     * @description: 保存报表模板的分组信息：新增、更新
     * @author: l13595
     * @param entity: ExcelTmplate 保存的字段参数
     * @return: 返回保存的操作结果。保存成功，返回ok；保存失败，返回操作失败的提醒
     * @time: 2017/3/29 14:35
     */
    @RequestMapping(value = "saveExcelGroup")
    public Map<String, String> saveExcelGroup(ExcelTmplate entity){
        Map<String, String> rep = new HashMap<>();

        if(service.checkSaveExcelGroup(entity.getGroupId(), entity.getGroupName())){
            rep.put("resultMsg", service.saveExcelGroup(entity));
        }else{
            rep.put("resultMsg", "分组名称已存在");
        }

        return rep;
    }

    /**
     * @description: 删除模板分组
     * @author: l13595
     * @param id: 分组id
     * @return: 返回删除操作的结果。删除成功，返回ok；删除失败，返回操作失败提醒
     * @time: 2017/3/29 14:44
     */
    @RequestMapping(value = "deleteExcelGroup")
    public Map<String, String> deleteExcelGroup(String id){
        Map<String, String> rep = new HashMap<>();
        rep.put("resultMsg", service.deleteExcelGroup(id));
        return  rep;
    }

    /**
     * @description: 更新分组信息
     * @author: l13595
     * @param entity: id-报表id；content-说明； groupId-分组id
     * @return: 返回更新操作的结果。更新成功，返回ok；更新失败，返回操作失败的提醒
     * @time: 2017/3/29 16:03
     */
    @RequestMapping(value = "updateExcelTmp")
    public Map<String, String> saveExcelContent(ExcelTmplate entity){
        Map<String, String> rep = new HashMap<>();
        rep.put("resultMsg", service.updateExcelTmp(entity));
        return rep;
    }


     /**
       * @description: 文件上传
       * @author: l13595
       * @param :
       * @return:
       * @time: 2017/4/24 19:53
       */
    @RequestMapping(value = "fileUpload", produces = "text/html; charset=utf-8")
    public String fileUpload(@RequestParam("file")MultipartFile file, String typeId ,HttpServletRequest request){
        Map<String, String> rep = new HashMap<>();
        if("".equals(typeId) || null == typeId){
            return "文件类型不能为空";
        }
        return service.fileUpload(file, request, typeId);
    }

}
