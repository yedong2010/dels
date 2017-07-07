package com.dels.security;/**
 * Created by l13595 on 2017/1/13.
 */

import com.dels.model.sys.OperateLog;
import com.dels.service.IOperateLogService;
import com.dels.service.ISaftyMessageService;
import com.dels.service.ISysMngService;
import com.dels.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: l13595
 * @date:2017-01-13
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String defaultTargetUrl;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    ISaftyMessageService service;
    @Autowired
    IOperateLogService logService;
    @Autowired
    ISysMngService sysService;

    /**
     * @description: 获取用户登录名称及前端ip地址，并保存到系统中，同时关闭安全提醒消息
     * @author: l13595
     * @time: 2017/1/13 16:45
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                HttpServletResponse response, Authentication authentication)throws IOException, ServletException {


        service.updateMsgState(Utils.getIpAddress(request));
        UserInfo user = (UserInfo) authentication.getPrincipal();
        if(null!=user){
            String userName=user.getUsername();
            String ipAddress = Utils.getIpAddress(request);
            constructLog(userName,ipAddress);  /** 登录成功添加日志 l13595 2017/2/16 10:05 **/
            logger.info("用戶：" + user.getUsername() + "  IP：" + Utils.getIpAddress(request));
            user.setUserIpAddress(ipAddress);

            addOnlineUser(request, userName, ipAddress);
            sysService.changeUserErrorNum(userName);

        }
        this.redirectStrategy.sendRedirect(request, response, this.defaultTargetUrl);
    }

    /**
     * @description: 保存登录用户的信息，并将用户的唯一标识保存到session中。
     * @author: l13595
     * @param request:HttpServletRequest
     * @param userName:登录用户名
     * @param ip:客户端在线登录ip
     * @time: 2017/3/7 10:06
     */
    public void addOnlineUser(HttpServletRequest request,String userName,String ip){
        ServletContext application = request.getServletContext();
        List<com.dels.model.sys.User> userList = (List<com.dels.model.sys.User>) application.getAttribute("userList");
        if(userList == null){
            userList = new ArrayList<>();
        }
        com.dels.model.sys.User user = new com.dels.model.sys.User();
        String userFlag = Utils.createTokenId(userName, ip);
        user.setUname(userName);
        user.setUserIp(ip);
        user.setId(userFlag);
        userList.add(user);
        HttpSession session = request.getSession();
        session.setAttribute("userFlag",userFlag);
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
        operateLog.setOperation("登录");
        operateLog.setClassName("");
        operateLog.setMethodName("");
        operateLog.setIpAdress(ipAdress);
        logService.saveLog(operateLog);
    }

    public void setDefaultTargetUrl(String defaultTargetUrl) {
        this.defaultTargetUrl = defaultTargetUrl;
    }
}
