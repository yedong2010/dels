package com.dels.dao;

import com.dels.model.sys.ExcelTmplate;
import com.dels.model.sys.FileInfo;
import com.dels.model.sys.Role;
import com.dels.model.sys.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by liuchengjie on 2016/12/23.
 * 角色管理：增加、删除
 * 用户管理：增加、删除
 * 菜单管理：查询
 */
public interface SysMngMapper {

    /**
     * @description:获取角色菜单
     * @author: l13595
     * @param roleId：用户的角色id
     * @return：返回查询的菜单信息，放到List中
     * @time: 2017/1/11 11:40
     */
    public abstract List<String> getRoleMenList(String roleId);

    /**
     * @description:新增用户
     * @author: l13595
     * @param:user
     * @return:
     * @time: 2017/1/11 11:40
     */
    public abstract  void addUser(User user);
    /**
     * @description:更新用户
     * @author: l13595
     * @param:  user
     * @return:
     * @time: 2017/1/11 11:41
     */
    public abstract void updateUserInfo(User user);

    /**
     * @description:新增用户角色
     * @author: l13595
     * @param user：用户id，角色id
     * @return:
     * @time: 2017/1/11 11:41
     */
    public abstract void addUserRole(User user);

     /**
      * @description:根据用户id修改用户状态
      * @author: l13595
      * @param userId:用户id
      * @param status:状态
      * @return:
      * @time: 2017/5/12 10:20
      */
    public abstract void updateUserStatus(@Param("userid") String userId, @Param("status")int status);

     /**
      * @description: 修改用户组织机构
      * @author: l13595
      * @param department: 组织机构名称
      * @param userId: 用户id
      * @time: 2017/5/18 10:29
      */
    public abstract void updateUserDepartMent(@Param("department") String department, @Param("type")String cityType, @Param("userid") String userId );

     /**
      * @description: 增加用户登录错误次数
      * @author: l13595
      * @param userName: 用户名称
      * @time: 2017/5/15 16:09
      */
    public abstract void updateUserErrorNum(@Param("userName")String userName);
     /**
      * @description: 更新用户状态为 1-禁止，当用户错误次数大于安全审计的标准次数时执行
      * @author: l13595
      * @param userName: 用户名称
      * @time: 2017/5/15 16:10
      */
    public abstract void updateUserStatusTo1(@Param("userName")String userName);

     /**
      * @description: 通过用户名获取用户当前状态
      * @author: l13595
      * @param userName:用户名称
      * @return:
      * @time: 2017/6/6 15:25
      */
    public abstract String selectUserStatusByName(@Param("userName") String userName);

     /**
      * @description: 通过角色id获取角色用户的手机号
      * @author: l13595
      * @param roleid: 角色id
      * @time: 2017/6/6 15:49
      */
    public abstract List<String> selectPhoneByRoleId(@Param("roleid")String roleid);

     /**
      * @description: 查询用户状态
      * @author: l13595
      * @param userId: 用户id
      * @return: 返回查询结果列表
      * @time: 2017/5/12 10:24
      */

    public abstract List<String> selectUserStatusById(@Param("userid")String userId);

    /**
     * @description:删除用户角色
     * @author: l13595
     * @param userId：用户id
     * @return:
     * @time: 2017/1/11 11:42
     */
    public abstract void deleteUserRole(String userId);

    /**
     * @description:获取所有用户信息
     * @author: l13595
     * @param param: user
     * @return: list
     * @time: 2017/1/11 11:42
     */
    public abstract List<Map<String,Object>> getUserList(Map<String,User> param);

    /**
     * @description:获取用户信息
     * @author: l13595
     * @param user：查询参数
     * @return:map
     * @time: 2017/1/11 14:18
     */
    public abstract Map<String,Object> getUserInfo(User user);

    /**
     * @description:删除用户
     * @author: l13595
     * @param userId：用户id
     * @return:
     * @time: 2017/1/11 14:18
     */
    public abstract  void deleteUser(String userId);

    /**
     * @description:增加角色
     * @author: l13595
     * @param: role
     * @return:
     * @time: 2017/1/11 14:19
     */
    public abstract void  addRole(Role role);

     /**
       * @description: 查询角色指定角色名称和id的记录条数，用户校验更新角色名称的唯一性
       * @author: l13595
       * @param roleId: 角色id
       * @param roleName: 角色名称
       * @return: 返回查询的记录的结果条数
       * @time: 2017/4/18 10:41
       */
    public abstract int selectRoleCount(@Param("roleid")String roleId, @Param("roleName")String roleName);

    /**
     * @description:更新角色信息
     * @author: l13595
     * @param role:角色id， 角色名称
     * @return:
     * @time: 2017/1/11 14:23
     */
    public abstract void updateRoleInfo(Role role);

    /**
     * @description:删除角色
     * @author: l13595
     * @param roleId：角色id
     * @return:
     * @time: 2017/1/11 14:24
     */
    public abstract void delRoleById(String roleId);

    /**
     * @description:获取所有角色信息
     * @author: l13595
     * @param:
     * @return: list
     * @time: 2017/1/11 14:24
     */
    public abstract List<Map<String,Object>> getRoleList();

     /**
      * @description: 获取除默认三个角色外的角色
      * @author: l13595
      * @return: 返回查询结果列表
      * @time: 2017/5/18 15:09
      */
    public abstract List<Map<String, String>> findRoleList();

    /**
     * @description:增加角色菜单关联表记录
     * @author: l13595
     * @param role：角色id，菜单id
     * @return:
     * @time: 2017/1/11 14:25
     */
    public abstract void addRolMenu(Role role);


    public abstract void updateMenuDel(@Param("list") List<String> menuid, @Param("roleid")String roleId);
    public abstract List<String> selectMenuidForRole(@Param("list") List<String> menuid, @Param("roleid")String roleId);

    /**
     * @description:删除角色菜单关联表记录
     * @author: l13595
     * @param  roleId 角色id
     * @return:
     * @time: 2017/1/11 14:25
     */
    public abstract void delRolMenu(String roleId);

    /**
     * @description:获取菜单列表
     * @author: l13595
     * @param:
     * @return: list
     * @time: 2017/1/11 14:29
     */
    public abstract  List<Map<String,Object>> getMenuList();

    /**
     * @description:获取用户侧边栏菜单列表
     * @author: l13595
     * @param:
     * @return:List
     * @time: 2017/1/11 14:29
     */
    public abstract  List<Map<String,Object>> getUserMenuList(Map<String, Object> param);

    /**
     * @description:获取用户导航栏菜单
     * @author: l13595
     * @param userName：当前用户名称
     * @return:List
     * @time: 2017/1/11 14:30
     */
    public abstract  List<Map<String,String>> getParentMenuList(String userName);

    /**
     * @description:根据用户id查询用户角色记录
     * @author: l13595
     * @param userId
     * @return: int
     * @time: 2017/1/11 14:30
     */
    public abstract int countUserRole(String userId);

    /**
     * @description:更新用户角色
     * @author: l13595
     * @param: user
     * @return:
     * @time: 2017/1/11 14:31
     */
    public abstract void updateUserRole(User user);

    /**
     * @description:检验用户是否存在
     * @author: l13595
     * @param user：用户名属性
     * @return: int 根据用户名查询得到记录条数
     * @time: 2017/1/11 14:31
     */
    public abstract int checkUserName(User user);

    /**
     * @description:检验角色是否存在
     * @author: l13595
     * @param: role  角色名称
     * @return: int 根据角色名称返回查询到得记录条数
     * @time: 2017/1/11 14:32
     */
    public abstract int checkRoleName(Role role);

    /**
     * @description:检验角色是否可以删除
     * @author: l13595
     * @param: roleId-角色id
     * @return：返回角色是否能否删除的标识：0-不能删除，1-可以删除
     * @time: 2017/1/11 14:33
     */
    public abstract String checkRoleDel(String roleId);

    /**
     * @description: 通过角色id获取用户数
     * @author: l13595
     * @param roleId: 角色id
     * @return: 返回查询的用户数目
     * @time: 2017/3/9 14:21
     */
    public abstract int getUserNumByRoleId(String roleId);

    /**
     * @description:修改用户密码
     * @author: l13595
     * @param user：用户id，密码
     * @return:
     * @time: 2017/1/11 14:34
     */
    public abstract void updatePasswd(User user);

    /**
     * @description:新增安全审计内容
     * @author: l13595
     * @param : id， ip-用户ip地址
     * @time: 2017/2/13 15:11
     */
    public abstract void addSaftyMsg(Map<String,Object> param);

    /**
     * @description:检验当前用户IP是否存在未关闭的安全消息
     * @author: l13595
     * @param :ip-用户IP
     * @return: id-数据库查询的记录id
     * @time: 2017/2/13 17:22
     */
    public abstract String getMsgId(String ip);
    /**
     * @description: 更新当前ip未关闭的消息，次数加1
     * @author: l13595
     * @param :IP-用户ip
     * @time: 2017/2/13 17:22
     */
    public abstract void updateMsgNum(String ip);
    /**
     * @description: 更新当前IP未关闭的消息，状态变更为关闭
     * @author: l13595
     * @param :ip-当前用户的ip
     * @time: 2017/2/13 17:23
     */
    public abstract void updateMsgState(String ip);

    /**
     * @description: 获取安全审计信息列表
     * @author: l13595
     * @return: list-安全审计信息列表
     * @time: 2017/2/13 19:26
     */
    public List<Map<String,Object>> getMsgList(Map<String,Object> param);

    /**
     * @description: 获取安全审计消息提醒的标准次数
     * @author: l13595
     * @return: int类型 次数
     * @time: 2017/2/14 14:55
     */
    public abstract int getMsgMngNum();

    /**
     * @description: 更新安全审计消息提醒的标准次数
     * @author: l13595
     * @param : num-次数
     * @time: 2017/2/14 14:56
     */
    public abstract void updateMsgMngNum(int num);

    /**
     * @description: 获取安全审计提醒消息；错误次数不小于标准次数的记录
     * @author: l13595
     * @return: 返回错误不小于标准次数的记录
     * @time: 2017/2/14 14:57
     */
    public abstract List<Map<String,Object>> getMsgMngList();

    /**
     * @description: 获取提醒信息的条数
     * @author: l13595
     * @return: 返回当天需要提醒的消息条数
     * @time: 2017/2/15 9:43
     */
    public abstract int getMsgCount();

    /**
     * @description: 获取当天需要提醒的消息列表
     * @author: l13595
     * @return: 返回当天所有需要提醒的消息列表
     * @time: 2017/2/15 9:43
     */
    public abstract List<Map<String,Object>> getMsgForManager();

    /**
     * @description: 更新安全审计提醒标准
     * @author: l13595
     * @param param: num-次数，roleId-角色id
     * @time: 2017/2/17 17:13
     */
    public abstract void updateMsgMng(Map<String,Object> param);

    /**
     * @description: 根据文件名获取文件记录的条数
     * @author: l13595
     * @param fileName: 文件名称
     * @return: 返回文件记录的条数
     * @time: 2017/3/27 10:18
     */
    public List<String> checkFileName(String fileName);
    /**
     * @description: 通过文件id查询文件名称
     * @author: l13595
     * @param id: 文件id
     * @return: 返回文件名称
     * @time: 2017/3/27 10:17
     */
    public abstract String findFileNameById(String id);
    /**
     * @description: 插入文件信息
     * @author: l13595
     * @param param: FileInfo  file 参数
     * @time: 2017/2/17 17:15
     */
    public abstract void insertFileInfo(Map<String,FileInfo> param);

    /**
     * @description: 获取文件列表
     * @author: l13595
     * @param  param: FileInfo file -查询参数
     * @return: 返回查询结果
     * @time: 2017/2/17 17:16
     */
    public abstract List<Map<String,Object>> getFileInfoList(Map<String,FileInfo> param);

    /**
     * @description: 删除文件
     * @author: l13595
     * @param  param: FileInfo.id 文件id
     * @time: 2017/2/18 14:34
     */
    public abstract void delFileInfo(Map<String,FileInfo> param);
    /**
     * @description: 查询安全标准次数
     * @author: l13595
     * @time: 2017/2/24 11:47
     */
    public abstract Map<String,Object> getSecurityTime();

    /**
     * @description: 新增文件类型
     * @author: l13595
     * @param param:fileinfo: id，fileType
     * @return:
     * @time: 2017/2/24 11:48
     */
    public abstract void insertFileType(Map<String,FileInfo> param);

    /**
     * @description: 通过文件类型的id查询文件类型的名称
     * @author: l13595
     * @param id: 文件类型的id
     * @return: 返回文件类型的名称
     * @time: 2017/3/27 10:01
     */
    public abstract String findTypeNameById(@Param("id")String id);
    /**
     * @description: 更新文件类型
     * @author: l13595
     * @param fileType: FileInfo.typeId-类型id；FileInfo.fileType-文件类型名称
     * @time: 2017/3/18 15:40
     */
    public abstract void updateFileType(FileInfo fileType);
    public abstract int countTNameById(FileInfo fileType);
    public abstract void updateFileByType(FileInfo fileType);

    /**
     * @description: 获取文件类型列表
     * @author: l13595
     * @return: 返回查询结果列表
     * @time: 2017/3/2 17:50
     */
    public abstract List<Map<String,Object>> getFileTypeList(Map<String,FileInfo> param);
    /**
     * @description: 删除文件类型
     * @author: l13595
     * @param param:id-文件类型id
     * @time: 2017/3/2 17:50
     */
    public abstract void delFileTYpe(Map<String,FileInfo> param);
    /**
     * @description: 查询文件类型下的文件数
     * @author: l13595
     * @param typeId: 文件类型id
     * @return: 返回文件数
     * @time: 2017/3/2 17:51
     */
    public abstract int countFileWtihType(String typeId);

    /**
     * @description: 获取用户密码
     * @author: l13595
     * @param user: id-用户id，userName-用户名
     * @return: 返回用户密码
     * @time: 2017/3/2 17:53
     */
    public abstract List<String> getPasswdByUser(User user);

    /**
     * @description: 获取角色id
     * @author: l13595
     * @param userName: 用户名
     * @return: 返回角色id
     * @time: 2017/3/3 15:25
     */
    public abstract String getRoleIdByUserName(String userName);

    /**
     * @description: 查询用户权限列表
     * @author: l13595
     * @param userName: 用户名称
     * @return: 返回查询结果列表
     * @time: 2017/3/3 15:29
     */
    public abstract List<Map<String,Object>> getUserPermissions(String userName);



    /**
     * @description: 查询excel模板列表
     * @author: l13595
     * @param: 查询字段参数
     * @return:返回查询模板结果列表
     * @time: 2017/3/28 11:21
     */
    public abstract List<Map<String, Object>> findExcelTmpList(ExcelTmplate entity);

    /**
     * @description: 根据模板名称，查询记录数
     * @author: l13595
     * @param name: 模板文件名称
     * @return: 返回查询的记录数
     * @time: 2017/4/1 13:52
     */
    public abstract int checkExcelTmp(@Param("name")String name);

    /**
     * @description: 插入模板记录
     * @author: l13595
     * @param:  entity 插入模板记录的字段参数
     * @time: 2017/4/1 13:53
     */
    public abstract void insertExcelTmp(ExcelTmplate entity);

    /**
     * @description: 更新模板信息
     * @author: l13595
     * @param entity: 更新操作的字段参数
     * @time: 2017/4/1 13:54
     */
    public abstract void updateExcelTmp(ExcelTmplate entity);

    /**
     * @description: 更新上传模板的用户名和时间
     * @author: l13595
     * @param id: 模板id
     * @param userName: 用户名称
     * @time: 2017/4/1 14:11
     */
    public abstract void updateUploadExcelInfo(@Param("id")String id, @Param("userName")String userName);
    /**
     * @description: 通过模板id查询模板名称
     * @author: l13595
     * @param id: 模板id
     * @return: 返回模板名称
     * @time: 2017/4/1 13:55
     */
    public abstract String findExcelNameById(@Param("id")String id);

    /**
     * @description: 根据模板id删除模板
     * @author: l13595
     * @param id: 要删除的模板id
     * @time: 2017/4/1 13:56
     */
    public abstract void deleteExcelTmp(@Param("id")String id);

    /**
     * @description:查询excel模板分组的列表
     * @author: l13595
     * @param: 查询字段参数
     * @return:返回查询的分组结果列表
     * @time: 2017/3/28 11:22
     */
    public abstract List<Map<String, Object>> findExcelGroupList (ExcelTmplate entity);

    /**
     * @description: 根据分组名称和id查询分组记录数
     * @author: l13595
     * @param name: 分组名称
     * @param id: 分组id
     * @return: 返回查询记录数
     * @time: 2017/4/1 13:56
     */
    public abstract int checkExcelGroup (@Param("name")String name, @Param("id")String id);

    /**
     * @description: 插入分组记录
     * @author: l13595
     * @param entity: 插入操作的字段参数
     * @time: 2017/4/1 13:57
     */
    public abstract void insertExcelGroup (ExcelTmplate entity);

    /**
     * @description: 更新分组记录
     * @author: l13595
     * @param entity: 更新操作的字段参数
     * @time: 2017/4/1 13:58
     */
    public abstract void updateExcelGroup (ExcelTmplate entity);

    /**
     * @description: 根据分组id查询分组下的模板数量
     * @author: l13595
     * @param groupId: 分组id
     * @return: 返回查询到的记录数
     * @time: 2017/4/1 13:59
     */
    public abstract int findExcelNumByGroupid(@Param("groupId")String groupId);

    /**
     * @description: 删除分析记录
     * @author: l13595
     * @param id: 分组id
     * @time: 2017/4/1 14:00
     */
    public abstract void deleteExcelGroup (@Param("id")String id);

    /**
     * 查询指定手机号的用户信息是否存在
     * @param phoneNumber:用户手机号
     * @param userid：用户id，可为空。为空时用于判断新增用户的手机号是否存在；不为空时，判断编辑用户的手机是否存在
     * @return：返回结果记录条数
     */
    public abstract int countPhoneNum(@Param("phone") String phoneNumber, @Param("userid") String userid);

    public abstract void changeUserErrorNum(@Param("userName") String userName);

}
