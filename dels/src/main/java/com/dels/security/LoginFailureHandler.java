package com.dels.security;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dels.service.ISaftyMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

/**
 * 
 * @description 登录失败处理方法
 * @author z11595
 * @time 2016年12月22日 下午4:05:45
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private String defaultFailureUrl;

    @Autowired
    ISaftyMessageService saftyService;

    /**
     * 
     * @description 保存异常
     * @author: z11595
     * @param request
     * @param e
     * @time 2016年12月22日 下午4:06:13
     */
    protected final void saveException(HttpServletRequest request,AuthenticationException e) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", e);
        }
    }

    public String getDefaultFailureUrl() {
        return this.defaultFailureUrl;
    }

    public void setDefaultFailureUrl(String defaultFailureUrl) {
        this.defaultFailureUrl = defaultFailureUrl;
    }

    /**
     * 认证失败处理方法
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException e) throws IOException, ServletException {
        String msg = e.getLocalizedMessage();
        HttpSession session = request.getSession();
        if (e instanceof BadCredentialsException) {
            msg = "登录失败，用户名或密码错误！";

            session.setAttribute("phoneNum", null);
            Object uname = request.getAttribute("userName");
            String userName = (String) (null!=uname?uname:"");
            if(!"admin".equals(uname) && !"sjjc_sys".equals(uname)){
                saftyService.updateUserErrorNum(userName); //数据库更新用户登录错误信息
            }

        } else if (e instanceof SessionAuthenticationException) {
            msg = "当前用户已经登录";
            session.setAttribute("phoneNum", null);
        }
        request.getSession().setAttribute("message", msg);
        saveException(request, e);
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        /** 登录失败后，更新安全审计消息 l13595 2017/2/13 17:52 **/
        String userIp = getIpAddress(request);
        saftyService.updateSaftyMsg(userIp);

        redirectStrategy.sendRedirect(request, response, this.defaultFailureUrl);
    }

    /**
     * @description:获取客户端访问地址
     * @author: l13595
     * @param :request
     * @return: 客户端访问服务器的IP地址
     * @time: 2017/1/13 15:55
     */
    private  String getIpAddress(HttpServletRequest request){
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        //存在多级代理的情况，去第一个非unknow的值
        String []ips = ipAddress.split(",");
        for(String ipStr: ips){
            if (!"unknown".equalsIgnoreCase(ipStr)){
                ipAddress = ipStr;
                break;
            }
        }

        //根据网卡取本机配置的IP  l13595 2017/1/13 15:55
        if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
            InetAddress inet=null;
            try {
                inet = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            ipAddress= inet.getHostAddress();
        }
        return ipAddress;
    }
}
