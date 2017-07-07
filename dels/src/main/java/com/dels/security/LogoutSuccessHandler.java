package com.dels.security;

import com.dels.model.sys.OperateLog;
import com.dels.model.sys.User;
import com.dels.service.IOperateLogService;
import com.dels.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by l13595 on 2017/3/4.
 * 用户退出系统处理
 */
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Autowired
    IOperateLogService logService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        if(null!=user){
            String userName=user.getUsername();
            String ipAddress = Utils.getIpAddress(request);
//            deleteOnlineUser(request, ipAddress, userName);
            constructLog(userName,ipAddress);  /** 用户退出系统添加日志 l13595 2017/2/16 10:05 **/
            logger.info("用戶：" + user.getUsername() + "  IP：" + ipAddress + " 退出系统");
        }
        super.onLogoutSuccess(request, response, authentication);
    }

    /**
     * @description: 用户退出系统，删除系统中保存的在线用户信息,由于一个账户号可以同时多人在线，需要根据用户ip删除用户信息
     * @param ip: 用户ip
     * @param request: HttpServletRequest
     * @author: l13595
     * @time: 2017/3/4 14:15
     */
    public void deleteOnlineUser(HttpServletRequest request, String ip, String userName){
        HttpSession session = request.getSession();
        String userFlag = session.getAttribute("userFlag").toString();
        ServletContext application = request.getServletContext();
        List<User> userList = (List<User>) application.getAttribute("userList");
        if(userList != null && null != userFlag){
            for(int i=0;i<userList.size();i++) {
                User user = userList.get(i);
//                if ((ip.equals(user.getUserIp())) && (userName.equals(user.getUname()))) {
                if (userFlag.equals(user.getId())){
                    userList.remove(i);
                    break;
                }
            }
        }
        application.setAttribute("userList", userList);
    }

    /**
     * @description: 保存登录成功日志,暂时弃用，登录成功不添加日志
     * @author: l13595
     * @param userName: 用户名
     * @param ipAdress: IP地址
     * @time: 2017/2/15 17:10
     */
    public void constructLog(String userName, String ipAdress){
        OperateLog operateLog = new OperateLog();
        operateLog.setId(Utils.getUuid());
        operateLog.setUser_name(userName);
        operateLog.setOperation("退出");
        operateLog.setClassName("");
        operateLog.setMethodName("");
        operateLog.setIpAdress(ipAdress);
        logService.saveLog(operateLog);
    }
}
