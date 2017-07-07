package com.dels.service;

import com.dels.model.sys.ExcelTmplate;
import com.dels.model.sys.FileInfo;
import com.dels.model.sys.Role;
import com.dels.model.sys.User;
import com.dels.utils.LogDescription;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by liuchengjie  on 2016/12/23.
 */
public interface ISysMngService {

    /**
     * @description:获取用户信息列表
     * @author l13595
     * @param
     * @return：List
     * @time 2017/1/11 11:07
     */
    public  List<Map<String,Object>> getUserList(User user);

    /**
     * @description:获取所有角色信息
     * @author l13595
     * @param
     * @return：List
     * @time 2017/1/11 11:15
     */
    public  List<Map<String,Object>> getRoleList();

     /**
      * @description: 获取角色列表，除默认三个角色以外
      * @author: l13595
      * @return: 返回角色列表信息
      * @time: 2017/5/18 15:16
      */
    public List<Map<String, String>> findRoleList();


    /**
     * @description:获取用户侧边栏菜单
     * @author l13595
     * @param: pList：userName：当前登录用户名
     *             url：用户拥有权限的导航栏菜单
     * @return:当前url下的侧边栏菜单
     * @time 2017/1/11 11:16
     */
    public Map<String,Object> getUserMenuList(List<Map<String, String>> pList);

    /**
     * @description:获取菜单树信息:新增角色页面菜单树显示
     * @author l13595
     * @param
     * @return：List
     * @time 2017/1/11 11:16
     */
    public List<Map<String,Object>> getMenuList();

    /**
     * @description:获取当前用户的导航栏菜单
     * @author l13595
     * @param:   userName：当前用户的登录名
     * @return:  当前用户的导航栏菜单
     * @time 2017/1/11 11:18
     */
    public List<Map<String,String>> getParentMenuList(String userName);

    /**
     * @description: 获取当前用户信息
     * @author: l13595
     * @return: 返回用户信息
     * @time: 2017/1/11 11:19
     */
    public abstract Map<String,Object> getUserInfo();
    /**
     * @description: 查询当前用户信息
     * @author: l13595
     * @return: 返回用户信息类
     * @time: 2017/3/8 15:55
     */
    public abstract User findUserInfo();

    /**
     * @description:新增角色信息
     * @author: l13595
     * @param:
     * @return:ok-操作成功  其它-操作失败，返回提示信息
     * @time: 2017/1/11 11:23
     */
    @LogDescription(description = "新增角色",pOrder = 0)
    public abstract String addRole(String roleName, String roleId);

    /**
     * @description:校验角色名称是否存在
     * @param roleName
     * @return：存在，返回false；不存在，返回true
     */
    public abstract boolean checkRoleName(String roleName);

    /**
     * @description:新增角色关联菜单
     * @author: l13595
     * @param roleId:角色id
     * @param menuId：菜单id
     * @return:
     * @time: 2017/1/11 11:24
     */
    public abstract String addRoleMent(String roleId, String[] menuId);

     /**
       * @description: 更新角色菜单
       * @author: l13595
       * @param roleId: 角色id
       * @param menuList: 菜单id
       * @return: 更新菜单操作的结果。更新成功，返回ok；更新失败，返回失败提醒
       * @time: 2017/4/14 15:54
       */
    public abstract String updateRoleMenu(String roleId, List<String> menuList);

    /**
     * @description: 获取角色菜单
     * @author: l13595
     * @param roleId：角色id
     * @return:
     * @time: 2017/1/11 11:28
     */
    public abstract List<String> getRoleMenList(String roleId);

    /**
     * @description:删除角色菜单
     * @author: l13595
     * @param roleId：角色id
     * @return:
     * @time: 2017/1/11 11:29
     */
    public abstract String delRolMenu(String roleId);

    /**
     * @description:增加用户
     * @author: l13595
     * @param:
     * @return:
     * @time: 2017/1/11 11:29
     */
    @LogDescription(description = "增加用户",pOrder = 0,pType = "User")
    public abstract  String addUser(User user);

     /**
      * @description: 更新用户状态。o变更为1,1变更为0
      * @author: l13595
      * @param user: User类，用户名称，用户id
      * @return: 返回执行结果，执行失败，返回失败提醒；执行成功，返回ok
      * @time: 2017/5/15 16:16
      */
    @LogDescription(description = "变更用户账号状态", pOrder = 0, pType = "User")
    public abstract String changeUserStatus(User user);

    /**
     * @description:删除用户
     * @author: l13595
     * @param user：用户id
     * @return:删除操作的结果：ok-删除成功   其他-删除失败的提示信息
     * @time: 2017/1/11 11:30
     */
    @LogDescription(description = "删除用户",pOrder = 0,pType = "User")
    public abstract  String deleteUser(User user);

    /**
     * @description: 删除角色
     * @author: l13595
     * @param role：角色
     * @return:删除操作的结果：ok-删除成功   其他-删除失败的提示信息
     * @time: 2017/1/11 11:32
     */
    @LogDescription(description = "删除角色",pOrder = 0,pType = "Role")
    public abstract String  delRoleById(Role role);

    /**
     * @description: 判断角色是否能删除。1）角色是否能够删除的标识 2）角色下是否存在用户
     * @author: l13595
     * @param role:角色id
     * @return: 可以删除-ok，不能删除-返回提示信息
     * @time: 2017/3/9 14:31
     */
    public abstract String ifDelRole(Role role);

    /**
     * @description: 更新用户
     * @author: l13595
     * @param user
     * @return: 操作的结果：ok-修改成功        其他-修改失败的提示信息
     * @time: 2017/1/11 11:32
     */
    @LogDescription(description = "修改用户",pOrder = 0,pType = "User")
    public abstract String updateUserInfo(User user);

     /**
      * @description: 配置用户角色，同时配置用户组织机构
      * @author: l13595
      * @param user: 用户id； 用户部门组织机构； 用户角色
      * @return: 返回操作结果。操作成功，返回ok； 操作失败，返回操作失败提醒
      * @time: 2017/5/18 10:44
      */
    @LogDescription(description = "用户角色配置", pOrder = 0, pType = "User")
    public abstract String updateUserRole(User user);

    /**
     * @description: 修改用户密码
     * @author: l13595
     * @param user：用户id，密码
     * @return: 操作的结果：ok-修改        其他-修改失败的提示信息
     * @time: 2017/1/11 11:33
     */
    @LogDescription(description = "修改用户密码" ,pOrder = 0,pType = "User")
    public abstract String updatePasswd(User user);

     /**
       * @description: 校验角色名称唯一性。根据角色名称和id，判断要更新的角色名是否除原名称外唯一存在
       * @author: l13595
       * @param roleId: 角色id
       * @param roleName: 角色名称
       * @return: 名称存在，返回false；名称不存在，返回true
       * @time: 2017/4/18 10:35
       */
    public boolean checkUpdateRoleName(String roleId, String roleName);

    /**
     * @description:更新角色
     * @author: l13595
     * @param role：id,role_name
     * @return:操作的结果：ok-更新成功     其他-更新失败的提示信息
     * @time: 2017/1/11 11:33
     */
    @LogDescription(description = "修改角色",pOrder = 0,pType = "Role")
    public abstract String updateRoleInfo(Role role);

    /**
     * @description: 校验用户名是否已经存在
     * @author: l13595
     * @param userName：用户名
     * @return：true-不存在，可以使用； false-名称已经存在
     * @time: 2017/1/11 11:34
     */
    public abstract boolean checkUserName(String userName);

    /**
     * @description: 插入文件信息
     * @author: l13595
     * @param  request: 文件信息
     * @time: 2017/2/16 15:14
     */
    @LogDescription(description = "文件上传")
    public abstract String insertFileInfo(HttpServletRequest request,String typeId);

    @LogDescription(description = "文件上传")
    public abstract String fileUpload(MultipartFile file, HttpServletRequest request, String typeId);

    /**
     * @description: 查询文件信息列表
     * @author: l13595
     * @param fileInfo: 查询参数类
     * @return:  返回查询结果列表
     * @time: 2017/2/16 15:20
     */
    public abstract List<Map<String,Object>> getFileInfoList(FileInfo fileInfo);

    /**
     * @description: 文件下载
     * @author: l13595
     * @param response: HttpServletResponse
     * @param fileInfo: FileInfo-需要下载文件的id作为查询条件
     * @return: 返回文件下载结果。成功-ok，失败-操作失败提醒
     * @time: 2017/2/17 17:11
     */
    @LogDescription(description = "下载文件", pOrder = 1, pType = "file")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response,FileInfo fileInfo);

    /**
     * @description: 判断文件是否能够下载
     * @author: l13595
     * @param fileInfo: 文件id
     * @return: 文件存在，可以下载-true，文件不存在，不能下载-false
     * @time: 2017/3/9 15:38
     */
    public boolean ifDownloadFile(FileInfo fileInfo);
    /**
     * @description: 删除文件
     * @author: l13595
     * @param  fileInfo: id-文件id
     * @return:  返回操作结果标识。成功-ok，失败-返回操作失败提醒
     * @time: 2017/2/20 10:14
     */
    @LogDescription(description = "删除文件", pOrder = 0, pType = "file")
    public String delFileById(FileInfo fileInfo);

    /**
     * @description: 通过id获取文件名称
     * @author: l13595
     * @param id: 文件id
     * @return: 返回文件名称
     * @time: 2017/3/27 10:13
     */
    public String findFileNameById(String id);

    /**
     * @description: 新增文件类型
     * @author: l13595
     * @param fileInfo: id-类型id，fileType-类型名称，userName-创建类型用户名
     * @return: 返回新增结果。成功-ok，失败-返回新增失败提醒
     * @time: 2017/3/2 11:35
     */
    @LogDescription(description = "新增文件类型", pOrder = 0, pType = "addFileType")
    public abstract String insertFileType(FileInfo fileInfo);

    /**
     * @description: 修改文件类型
     * @author: l13595
     * @param fileInfo: FileInfo typeId-类型id；fileType-类型名称
     * @return: 返回更新结果。成功-ok，失败-返回败提醒
     * @time: 2017/3/18 15:36
     */
    @LogDescription(description = "修改文件类型", pOrder = 0, pType = "FileInfo")
    public abstract String editFileType(FileInfo fileInfo);

    /**
     * @description: 校验文件类型的唯一性
     * @author: l13595
     * @param fileInfo: fileType-文件类型名称
     * @return: 返回true-文件类型名称不存在，返回false-文件类型名称已存在
     * @time: 2017/3/2 11:38
     */
    public abstract boolean checkFileType(FileInfo fileInfo);

    /**
     * @description: 查询文件类型
     * @author: l13595
     * @param fileInfo: fileType-文件类型名称
     * @return :  返回查询结果列表
     * @time: 2017/3/2 11:39
     */
    public abstract List<Map<String,Object>> getFileTypeList(FileInfo fileInfo);

    /**
     * @description: 删除文件类型
     * @author: l13595
     * @param fileInfo:  id-文件类型id
     * @return: 返回操作结果标识。 删除成功-ok，删除失败-返回删除失败提醒
     * @time: 2017/3/2 11:41
     */
    @LogDescription(description = "删除文件类型", pOrder = 0, pType = "FileInfo")
    public abstract String delFileTYpe(FileInfo fileInfo);

    /**
     * @description: 通过类型id获取文件类型的名称
     * @author: l13595
     * @param typeId: 文件类型id
     * @return: 返回文件类型名称
     * @time: 2017/3/27 9:58
     */
    public abstract String findTypeNameById(String typeId);

    /**
     * @description: 校验用户密码是否正确
     * @author: l13595
     * @param user: id-用户名称 ，uname-用户名称
     * @return: 返回校验结果。用户密码与前端传递的密码是否相同。相同-返回true，不相同-返回false
     * @time: 2017/3/2 18:45
     */
    public abstract String checkUserPasswd(User user);

    /**
     * @description: 查询用户权限列表
     * @author: l13595
     * @return: 返回查询权限结果列表
     * @time: 2017/3/3 15:31
     */
    public abstract List<Map<String,Object>> getUserPermissions();

    /**
     * @description: 获取用户名称
     * @author: l13595
     * @param userId: 用户id
     * @return: 返回用户名称
     * @time: 2017/3/9 11:30
     */
    public abstract String getUserNameById(String userId);

    /**
     * @description: 查询模板列表
     * @author: l13595
     * @param entity: 查询字段参数
     * @return: 返回查询结果列表
     * @time: 2017/4/1 14:12
     */
    public abstract List<Map<String, Object>> findExcelTmpList(ExcelTmplate entity);

    /**
     * @description: 校验模板明后才能是否已经存在
     * @author: l13595
     * @param name: 模板名称
     * @return: 存在，返回false；不存在，返回false
     * @time: 2017/4/1 14:13
     */
    public abstract boolean checkExcelTmp(String name);

    /**
     * @description: 保存上传模板的用户名和上传时间
     * @author: l13595
     * @param id: 上传模板文件的id
     * @return: 返回保存结果信息。保存成功，返回ok；保存失败，返回保存失败的操作提醒
     * @time: 2017/4/1 14:14
     */
    public abstract String saveExcelTmp(String id);

    /**
     * @description: 更新模板文件的信息
     * @author: l13595
     * @param entity: 更新操作的字段参数
     * @return: 返回更新操作的结果信息。更新成功，返回ok；更新失败，返回操作失败的提醒信息
     * @time: 2017/4/1 14:15
     */
    public abstract String updateExcelTmp(ExcelTmplate entity);

    /**
     * @description: 通过模板文件的id查询名称
     * @author: l13595
     * @param id: 模板文件的id
     * @return: 返回模板名称
     * @time: 2017/4/1 14:16
     */
    public abstract String findExcelNameById(String id);

    /**
     * @description: 删除模板文件
     * @author: l13595
     * @param fileName: 文件名称
     * @param filePath: 文件路径
     * @return: 返回删除操作的结果。删除成功，返回ok；删除失败，返回操作失败的提醒
     * @time: 2017/4/1 14:17
     */
    public abstract String deleteExcelFile(String fileName, String filePath);

    /**
     * @description: 删除数据库模板信息
     * @author: l13595
     * @param id:模板文件id
     * @return: 返回删除操作的结果。删除成功，返回ok；删除失败，返回失败提醒
     * @time: 2017/4/1 14:19
     */
    public abstract String deleteExcelTmp(String id);


    /**
     * @description: 查询分组列表
     * @author: l13595
     * @param entity: 查询字段参数
     * @return: 返回查询结果列表
     * @time: 2017/4/1 14:20
     */
    public abstract List<Map<String, Object>> findExcelGroupList(ExcelTmplate entity);

    /**
     * @description: 校验要插入的分组名称是否存在
     * @author: l13595
     * @param name: 分组名称
     * @return: 返回查询结果。分组名称存在，返回false；分组名称不存在，返回true
     * @time: 2017/4/1 14:21
     */
    public abstract boolean checkInsertExcelGroup(String name);

    /**
     * @description: 校验更新操作的分组名称是否存在
     * @author: l13595
     * @param id:分组id
     * @param name: 分组名称
     * @return: 返回校验结果。存在，返回false；不存在，返回true
     * @time: 2017/4/1 14:22
     */
    public abstract boolean checkSaveExcelGroup (String id, String name);

    /**
     * @description: 保存分组信息
     * @author: l13595
     * @param entity: 保存字段参数
     * @return: 返回保存操作的结果。保存成功，返回ok；保存失败，返回操作失败的提醒
     * @time: 2017/4/1 14:23
     */
    public abstract String saveExcelGroup(ExcelTmplate entity);

    /**
     * @description: 删除分组信息
     * @author: l13595
     * @param groupId:要删除的分组id
     * @return: 返回删除操作的结果信息
     * @time: 2017/4/1 14:24
     */
    public abstract String deleteExcelGroup(String groupId);

     /**
      * @description: 获取用户省直部门、地市名称列表信息
      * @author: l13595
      * @return: 返回查询结果列表
      * @time: 2017/5/18 17:37
      */
    public abstract Map<String, Object> findBaseDataList();

    /**
     * 判断用户手机号是否存在
     * 可同时用户新增、编辑的判断
     * @param phoneNumber：用户手机号
     * @param userid：用户id
     * @return：存在，返回true；不存在，返回false
     */
    public abstract boolean chargeUserPhone(String phoneNumber, String userid);

     /**
      * @description: 根据手机号判断用户是否存在
      * @author: l13595
      * @param phone: 手机号
      * @return:存在，返回true； 不存在，返回false
      * @time: 2017/5/25 17:26
      */
    public abstract boolean ifUserExsitForPhone(String phone);

    /**
     * 修改用户登录错误记录
     * 根据用户名称，直接清零
     * @param userName：用户名称
     */
    public abstract void changeUserErrorNum(String  userName);

}
