package com.dels.service.impl;

import com.dels.dao.SysMngMapper;
import com.dels.model.sys.FileInfo;
import com.dels.service.ISysMngService;
import com.dels.dao.monitor.BaseDataMapper;
import com.dels.model.sys.ExcelTmplate;
import com.dels.model.sys.Role;
import com.dels.model.sys.User;
import com.dels.utils.FileUtil;
import com.dels.utils.Utils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by l13595 on 2016/12/23.
 */

@Service
public class SysMngService implements ISysMngService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    SysMngMapper dao;

    @Autowired
    BaseDataMapper baseDataDao;

    /**
     * 获取当前用户的用户名
     * @return
     */
    public String getUserName(){
        return Utils.getCurUserName();
    }

    public  List<Map<String,Object>> getUserList(User user){
        try{
            Map<String,User> param = new HashMap<>();
            param.put("user",user);
            List<Map<String,Object>> list  = dao.getUserList(param);
            return list;
        }catch (Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

    public  List<Map<String,Object>> getRoleList(){
        List<Map<String,Object>> list = null;
        try{
            list = dao.getRoleList();
        }catch (Exception e){
            logger.info(e.getMessage());
            return list;
        }
        return list;
    }

    @Override
    public List<Map<String, String>> findRoleList() {
        try{
            return dao.findRoleList();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String,String>> getParentMenuList(String userName){
        List<Map<String,String>> list = dao.getParentMenuList(userName);
        return list;
    }

    /**
     * 获取用户侧边栏菜单
     * @param pList：name：当前登录用户名
     *             url：用户拥有权限的导航栏菜单
     * @return: 当前url下的侧边栏菜单
     */
    public  Map<String,Object> getUserMenuList(List<Map<String,String>> pList){
        Map<String,Object> dataMap = new HashMap<>();
        if(null!=pList&&pList.size()>0){
            for(int i=0;i<pList.size();i++){
                Map<String,Object> param = new HashMap<>();
                param.put("url",pList.get(i).get("url"));
                param.put("name",getUserName());
                List<Map<String ,Object>> tmpList = this.dao.getUserMenuList(param);
                if(null!=tmpList){
                    dataMap.put(pList.get(i).get("url"),tmpList);
                }
            }
        }
        return  dataMap;
    }

    public List<Map<String,Object>> getMenuList(){
        return dao.getMenuList();
    }

    public String addRole(String roleName,String roleId){
        Role role = new Role();
        role.setRol_name(roleName);
        role.setId(roleId);
        try {
            dao.addRole(role);
            return  "ok";
        }catch (Exception e){
            return "新增角色失败，请联系系统管理员！";
        }
    }

    @Override
    public boolean checkRoleName(String roleName) {
        Role role = new Role();
        role.setRol_name(roleName);
        if(dao.checkRoleName(role)>0){
            return false;
        }else{
            return  true;
        }
    }

    public String addRoleMent(String roleId,String[] menuId){
        if(null!=menuId&& menuId.length!=0){
            for(int i=0;i<menuId.length;i++){
                Role r = new Role();
                r.setRole_id(roleId);
                r.setMenu_id(menuId[i]);
                try{
                    dao.addRolMenu(r);
                }catch (Exception e){
                    logger.info(e.getMessage());
                    return "角色菜单权限添加失败，请联系系统管理员！";
                }
            }
        }
        return "ok";
    }

    @Override
    public String updateRoleMenu(String roleId, List<String> menuList) {
        try{
            dao.updateMenuDel(menuList, roleId);
            List<String> list = dao.selectMenuidForRole(menuList, roleId);

            Role role = new Role();
            role.setRole_id(roleId);
            for(String menuid: list){
                role.setMenu_id(menuid);
                dao.addRolMenu(role);
            }

            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "更新失败，请联系管理员";
        }

    }

    public String addUser(User user){
        try{
            if(dao.checkUserName(user)>0){
                return "用户名已存在，不能重复添加，请重新操作！";
            }else{
                user.setUser_id(user.getId());
                dao.addUser(user);
//                dao.addUserRole(user); 新增用户时，不添加用户角色
            }
        }catch (Exception e){
            logger.info(e.getMessage());
            return "新增用户失败，请联系系统管理员！";
        }
        return "ok";
    }

    @Override
    public String changeUserStatus(User user) {
        if(null == user.getId()){
            return "获取用户id失败";
        }
        try{
            List<String> statusList = dao.selectUserStatusById(user.getId());
            if(null != statusList && statusList.size()>0){
                String status = statusList.get(0);
                if("0".equals(status)){
                    dao.updateUserStatus(user.getId(), 1);
                }else if("1".equals(status)){
                    dao.updateUserStatus(user.getId(), 0);
                }
            }
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "变更用户状态失败，请联系管理员";
        }
    }

    public String deleteUser(User user){
        try{
            dao.deleteUser(user.getId());
            dao.deleteUserRole(user.getId());
        }catch (Exception e){
            logger.info(e.getMessage());
            return "删除用户失败，请联系系统管理员！";
        }
        return "ok";
    }

    public String ifDelRole(Role role){
        if("0".equals(dao.checkRoleDel(role.getId()))){
            return "没有删除权限！";
        }
        if(dao.getUserNumByRoleId(role.getId())>0){
            return "角色被赋予用户，不能删除";
        }
        return "ok";
    }

    public  String  delRoleById(Role role){
        try{
            dao.delRoleById(role.getId());
            dao.delRolMenu(role.getId()); //删除角色关联菜单   l13595 2017/3/9 14:47
        }catch (Exception e){
            logger.info(e.getMessage());
            return "删除角色失败，请联系系统管理员！";
        }
        return "ok";
    }

    public String updateUserInfo(User user){
        try{
            if(null == user.getId()){
                return "用户id为空";
            }
            user.setUser_id(user.getId());
            dao.updateUserInfo(user);
            /*if(dao.countUserRole(user.getId())>0){   //修改用户，三权分离，此处不再涉及角色和组织机构
                dao.updateUserRole(user);
            }else{
                dao.addUserRole(user);
            }*/
        }catch (Exception e){
            logger.info(e.getMessage());
            return "修改用户信息失败，请联系管理员！";
        }
        return "ok";
    }

    @Override
    public String updateUserRole(User user) {
        try{
            if(null == user.getId()){
                return "用户id为空";
            }
            user.setUser_id(user.getId());
            if(null != user.getDepartment()){
                dao.updateUserDepartMent(user.getDepartment(),user.getType(), user.getId());
            }
            if(dao.countUserRole(user.getId())>0){
                dao.updateUserRole(user);
            }else{
                dao.addUserRole(user);
            }
        }catch (Exception e){
            e.printStackTrace();
            return "配置用户角色失败，请联系管理员";
        }

        return "ok";
    }


    public String updatePasswd(User user){
        try{
            dao.updatePasswd(user);
        }catch (Exception e){
            logger.info("修改密码失败："+e.getMessage());
            return "修改密码失败，请联系系统管理员！";
        }
        return "ok";
    }

    @Override
    public boolean checkUpdateRoleName(String roleId, String roleName) {
        try{
            if(dao.selectRoleCount(roleId, roleName) > 0){
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public String  updateRoleInfo(Role role){
        try{
            dao.updateRoleInfo(role);
        }catch (Exception e){
            logger.info(e.getMessage());
            return "更新角色失败，请联系系统管理员！";
        }
        return "ok";
    }

    public List<String> getRoleMenList(String roleId) {
        List<String> list = dao.getRoleMenList(roleId);
        return  list;
    }

    public String delRolMenu(String roleId){
        try{
            dao.delRolMenu(roleId);
        }catch (Exception e){
            logger.info(e.getMessage());
            return "更新角色菜单失败，请联系系统管理员！";
        }
        return  "ok";
    }

    public  Map<String,Object> getUserInfo(){
        User user = new User();
        user.setUname(Utils.getCurUserName());
        Map<String,Object> rep  = dao.getUserInfo(user);
        return rep;
    }

        public User findUserInfo(){
        User user = new User();
        user.setUname(Utils.getCurUserName());
        Map<String,Object> userMap  = dao.getUserInfo(user);
        if(null!=userMap && userMap.size()>0){
            user.setType((String) userMap.get("type"));
            user.setDepartment((String) userMap.get("department"));
            return user;
        }else{
            return null;
        }
    }
    public boolean checkUserName(String userName){
        try{
            User user = new User();
            user.setUname(userName);
            if(dao.checkUserName(user)>0){
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            logger.info(e.getMessage());
            return false;
        }
    }

    public String insertFileInfo(HttpServletRequest request,String typeId){
        String upload_path = request.getSession().getServletContext().getRealPath("documents");
        File file = new File(upload_path);
        String fileName = null;
        if(!file.exists()&&!file.isDirectory()){
            file.mkdir();
        }
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
        fileUpload.setHeaderEncoding("UTF-8");

        //判断是普通的表单，还是带文件附件上传的表单
      /*  if(!fileUpload.isMultipartContent(request)){
            return "普通表单，获取文件失败";
        }*/

        try {
            List<FileItem> list = fileUpload.parseRequest(request);
            if(null == list || list.size()==0){
                return "获取文件失败，请联系管理员";
            }
            FileItem item = list.get(0);

            if(!item.isFormField()){
                fileName = item.getName();
                if(fileName==null||fileName.trim().equals("")){
                    return "获取文件名失败";
                }else{
                    List<String> fileNameList = dao.checkFileName(fileName);
                    if(null!=fileNameList&&fileNameList.size()>0){
                        return "文件已存在";
                    }
                }
                if(FileUtil.filterFileName(fileName)){
                    if(item.getSize()>(1024*1024*100)){
                        return "文件超过100M，不能上传";
                    }
                }else{
                    return "该格式文件不能上传";
                }
                fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
                InputStream is = item.getInputStream();
                FileOutputStream fos = new FileOutputStream(upload_path+File.separator+fileName);
                byte buffer[] = new byte[1024];
                int length = 0;
                while((length = is.read(buffer))>0){
                    fos.write(buffer, 0, length);
                }
                is.close();
                fos.close();
                item.delete(); //删除处理文件上传时生成的临时文件
            }
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(fileName);
            fileInfo.setId(Utils.getUuid());
            fileInfo.setUserName(Utils.getCurUserName());
            fileInfo.setAddress(upload_path);
            fileInfo.setTypeId(typeId);
            Map<String,FileInfo> param = new HashMap<>();
            param.put("file",fileInfo);
            dao.insertFileInfo(param);
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @Override
    public String fileUpload(MultipartFile file, HttpServletRequest request, String typeId) {
        if(file.isEmpty()){
            return "上传文件为空";
        }else if(file.getSize() > 100*1024*1024){
            return "上传文件大于100M";
        }

        String fileName = file.getOriginalFilename();
        String upload_path = request.getSession().getServletContext().getRealPath("documents") ;
        File uploadPathFile = new File(upload_path);
        if(!uploadPathFile.exists()&&(!uploadPathFile.isDirectory())){
            uploadPathFile.mkdir();
        }

        String filePath = upload_path + File.separator +file.getOriginalFilename();
        try {
            List<String> fileNameList = dao.checkFileName(fileName);
            if(null!=fileNameList&&fileNameList.size()>0){
                return "文件已存在";
            }

            if(!FileUtil.filterFileName(fileName)){
                return "该格式文件不能上传";
            }

            file.transferTo(new File(filePath));

            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(fileName);
            fileInfo.setId(Utils.getUuid());
            fileInfo.setUserName(Utils.getCurUserName());
            fileInfo.setAddress(upload_path);
            fileInfo.setTypeId(typeId);
            Map<String,FileInfo> param = new HashMap<>();
            param.put("file",fileInfo);
            dao.insertFileInfo(param);
        } catch (IOException e) {
            e.printStackTrace();
            return "上传文件出错，请联系管理员";
        }
        return "ok";
    }

    public List<Map<String,Object>> getFileInfoList(FileInfo fileInfo){
        Map<String,FileInfo> param = new HashMap<>();
        param.put("file",fileInfo);
        List<Map<String,Object>> list = dao.getFileInfoList(param);
        return list;
    }

    public boolean ifDownloadFile(FileInfo fileInfo){
        boolean flag = true;
        Map<String,FileInfo> param = new HashMap<>();
        param.put("file",fileInfo);
        List<Map<String,Object>> list = dao.getFileInfoList(param);
        if(null!=list&&list.size()>0) {
            String fileName = (String) list.get(0).get("file_name");
            String address = (String) list.get(0).get("address");
            File file = new File(address+File.separator+fileName);
            if(!file.exists()){
                flag = false;
            }
        }else{
            flag = false;
        }
        return  flag;
    }
    public String downloadFile(HttpServletRequest request, HttpServletResponse response,FileInfo fileInfo){
        Map<String,FileInfo> param = new HashMap<>();
        param.put("file",fileInfo);
        List<Map<String,Object>> list = dao.getFileInfoList(param);
        if(null!=list&&list.size()>0){
            String fileName = (String) list.get(0).get("file_name");
            String address = (String) list.get(0).get("address");
            try {
                FileUtil.downloadFile(address,fileName,request, response);
            } catch (IOException e) {
                e.printStackTrace();
                return "下载失败，请联系系统管理员";
            }
        }
        return "ok";
    }

    public String delFileById(FileInfo fileInfo){
        Map<String,FileInfo> param = new HashMap<>();
        param.put("file",fileInfo);
        List<Map<String,Object>> list = dao.getFileInfoList(param);
        if(null!=list&&list.size()!=0){
            Map<String,Object> fileMap = list.get(0);
            fileInfo.setAddress((String) fileMap.get("address"));
            fileInfo.setFileName((String) fileMap.get("file_name"));
            File file = new File(fileInfo.getAddress()+File.separator+fileInfo.getFileName());
            try{
                dao.delFileInfo(param);
                if(file.exists()){
                    file.delete();
                }
            }catch (Exception e){
                e.printStackTrace();
                return "删除文件失败，请联系系统管理员";
            }
        }
        return "ok";
    }

    @Override
    public String findFileNameById(String id) {
        if(null != id && !"".equals(id)){
            try{
                String fileName = dao.findFileNameById(id);
                return fileName;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
    }

    public String insertFileType(FileInfo fileInfo){
        Map<String,FileInfo> param = new HashMap<>();
        param.put("file",fileInfo);
        List<Map<String,Object>> list = dao.getFileTypeList(param);
        if(null!=list&&list.size()>0){
            return "文件类型已保存，请不要重复提交";
        }
        try{
            fileInfo.setId(Utils.getUuid());
            fileInfo.setUserName(Utils.getCurUserName());
            Map<String,FileInfo> param2 = new HashMap<>();
            param2.put("file",fileInfo);
            dao.insertFileType(param2);
            return "ok";
        }catch (Exception e){
            e.printStackTrace();
            return "新增文件类型失败，请联系系统管理员！";
        }
    }

    @Override
    public String editFileType(FileInfo fileInfo) {
        try{
            if(0 == dao.countTNameById(fileInfo)){
                dao.updateFileType(fileInfo);
                dao.updateFileByType(fileInfo);
                return "ok";
            }else{
                return "类型名称已存在";
            }
        }catch(Exception e){
            e.printStackTrace();
            return "编辑失败，请联系管理员";
        }
    }

    public  boolean checkFileType(FileInfo fileInfo){
        Map<String,FileInfo> param = new HashMap<>();
        param.put("file",fileInfo);
        List<Map<String,Object>> list = dao.getFileTypeList(param);
        if(null!=list&&list.size()>0){
            return false;
        }else{
            return true;
        }
    }

    public List<Map<String,Object>> getFileTypeList(FileInfo fileInfo){
        Map<String,FileInfo> param = new HashMap<>();
        param.put("file",fileInfo);
        List<Map<String,Object>> list = null;
        try{
            list = dao.getFileTypeList(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public String delFileTYpe(FileInfo fileInfo){
        try{
            int num = dao.countFileWtihType(fileInfo.getId());
            if(num>0){
                return "该类型下有文件，不能删除，请先删除文件";
            }
            Map<String,FileInfo> param = new HashMap<>();
            param.put("file",fileInfo);
            dao.delFileTYpe(param);
            return "ok";
        }catch(Exception e){
            e.printStackTrace();
            return "删除文件类型失败，请联系系统管理员";
        }
    }

    @Override
    public String findTypeNameById(String typeId) {
        if (null != typeId && !"".equals(typeId)){
            try{
                String typeName = dao.findTypeNameById(typeId);
                return typeName;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
    }

    public String checkUserPasswd(User user){
        try{
            String currenRoleId = dao.getRoleIdByUserName(Utils.getCurUserName());
            if(currenRoleId!=null&&currenRoleId.equals("1")){
                user.setId("");
            }
            List<String> list = dao.getPasswdByUser(user);
            if(null!=list &&list.size()==1){
                String passwd = list.get(0);
                if(user.getPasswd().equals(passwd)){
                    return "ok";
                }else{
                    return "原密码输入错误";
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return "修改密码出现错误，请联系系统管理员";
        }
        return "原密码输入错误";
    }

    public List<Map<String,Object>> getUserPermissions(){
        try{
            List<Map<String,Object>> list = dao.getUserPermissions(Utils.getCurUserName());
            return list;
        }catch(Exception e){
            e.printStackTrace();;
            return null;
        }
    }

    public String getUserNameById(String userId){
        User user = new User();
        user.setId(userId);
        Map<String,Object> userMap = dao.getUserInfo(user);
        if(null != userMap ){
            return (String) userMap.get("uname");
        }else{
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> findExcelTmpList(ExcelTmplate entity) {
        List<Map<String, Object>> list = dao.findExcelTmpList(entity);
        return list;
    }

    @Override
    public List<Map<String, Object>> findExcelGroupList(ExcelTmplate entity) {
        List<Map<String, Object>> list = dao.findExcelGroupList(entity);
        return list;
    }

    @Override
    public boolean checkInsertExcelGroup(String name) {
        try{
            if(0 < dao.checkExcelGroup(name, null)){
                return false;
            }else{
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkSaveExcelGroup(String id, String name) {
        try{
            if(0 < dao.checkExcelGroup(name, id)){
                return false;
            }else{
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String saveExcelGroup(ExcelTmplate entity) {
        try{
            if(null != entity.getGroupId()){ //更新
                dao.updateExcelGroup(entity);
            }else{ // 新增
                entity.setGroupId(Utils.getUuid());
                entity.setCuser(Utils.getCurUserName());
                dao.insertExcelGroup(entity);
            }
            return  "ok";
        }catch(Exception e){
            e.printStackTrace();
            return "保存失败，请联系管理员";
        }
    }

    @Override
    public String deleteExcelGroup(String groupId) {
        try{
            if(dao.findExcelNumByGroupid(groupId) > 0){
                return "分组下存在文件，不能删除";
            }else{
                dao.deleteExcelGroup(groupId);
                return "ok";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "删除文件出错，请联系管理员";
        }
    }

    @Override
    public Map<String, Object> findBaseDataList() {
        Map<String, Object> rep = new HashMap<>();
        try{
            List<String> dsList = baseDataDao.findDSMC();
            List<String> szList = baseDataDao.findSZMC();
            rep.put("szList",szList);
            rep.put("dsList", dsList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rep;
    }

    @Override
    public boolean chargeUserPhone(String phoneNumber, String userid) {
        try{
            int i = dao.countPhoneNum(phoneNumber, userid);
            if(i==0){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean ifUserExsitForPhone(String phone) {
        try{
            if(null == phone){
                return false;
            }
            int i = dao.countPhoneNum(phone, null);
            if(i==1){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改用户登录错误记录
     * 根据用户名称，直接清零
     * @param userName ：用户名称
     */
    @Override
    public void changeUserErrorNum(String userName) {
        dao.changeUserErrorNum(userName);
    }

    @Override
    public boolean checkExcelTmp(String name) {
        try{
            if(dao.checkExcelTmp(name)>0){
                return  false;
            }else{
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String saveExcelTmp(String id) {
        try{
            dao.updateUploadExcelInfo(id, Utils.getCurUserName());
            return "ok";
        }catch(Exception e){
            e.printStackTrace();
            return "保存excel模板失败，请联系管理员";
        }
    }

    @Override
    public String updateExcelTmp(ExcelTmplate entity) {
        try{
            dao.updateExcelTmp(entity);
            return  "ok";
        }catch(Exception e){
            e.printStackTrace();
            return  "更新出错，请联系管理员";
        }
    }

    @Override
    public String findExcelNameById(String id) {
        if(null == id){
            return null;
        }
        try{
            String fileName = dao.findExcelNameById(id);
            return fileName;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String deleteExcelFile(String fileName, String filePath) {
        String f = filePath + fileName;
        File file = new File(f);
        if(file.exists()){
            file.delete();
            return "ok";
        }else{
            return "文件不存在！";
        }
    }

    @Override
    public String deleteExcelTmp(String id) {
        try{
            dao.deleteExcelTmp(id);
            return "ok";
        }catch(Exception e){
            e.printStackTrace();
            return "删除excel模板失败，请联系管理员";
        }
    }

}
