package com.dels.security;

import com.dels.service.IUserService;
import com.dels.utils.MD5Util;
import com.dels.model.sys.User;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by l13595 on 2017/4/20.
 * 扩展用户验证过滤，添加用户登录验证码验证
 */
public class VerificationAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private IUserService userService;

    private String verificationCodeParameter = "randomCode";
    private boolean postOnly = true;
    private String usernameParameter = "j_username";
    private String passwordParameter = "j_password";
    private String phoneParameter = "j_phonenum";
    private String oneTimePdParameter = "j_onetimepd";

    public String getPhoneParameter() {
        return phoneParameter;
    }

    public void setPhoneParameter(String phoneParameter) {
        this.phoneParameter = phoneParameter;
    }

    public String getOneTimePdParameter() {
        return oneTimePdParameter;
    }

    public void setOneTimePdParameter(String oneTimePdParameter) {
        this.oneTimePdParameter = oneTimePdParameter;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String username = this.obtainUsername(request);
            String password = this.obtainPassword(request);
            String phoneNum = request.getParameter(phoneParameter);
            if(null == phoneNum){
                phoneNum = "";
            }
            String oneTimePasswd = request.getParameter(oneTimePdParameter);

            HttpSession session = request.getSession();
            String phoneTmp = (String) session.getAttribute("phoneNum");
            String oneTimePd = (String) session.getAttribute("oneTimePd");


            if(!"".equals(username) && !"".equals(password) && "".equals(phoneNum)){  //用户账号登录

                request.setAttribute("userName", username); //request中放入用户名，共登录失败记录用户错误次数时抽取用户名

                String verificationCode = this.obtainVerificationCode(request);
                String systemCode = obtainSessionVerificationCode(request);
                /*if(!systemCode.equals(verificationCode)){
                    session.setAttribute("phoneNum", null);
                    throw new AuthenticationServiceException("验证码错误");
                }*/

                username = username.trim();
                password = MD5Util.getMD5Str(MD5Util.getMD5Str(new Object[] { password }) + "sjjc");
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                this.setDetails(request, authRequest);
                session.setAttribute("phoneNum", null);
                return this.getAuthenticationManager().authenticate(authRequest);

            }else{ //手机短信登录

                if(phoneTmp ==null || !phoneNum.equals(phoneTmp)){
                    throw new AuthenticationServiceException("");
                }else if (!"".equals(oneTimePasswd)&& !oneTimePasswd.equals(oneTimePd)) {
                    throw new AuthenticationServiceException("动态口令错误");
                }else if("".equals(oneTimePasswd)){
                    throw new AuthenticationServiceException(null);
                }

                User user = userService.selectUserByPhone(phoneNum);
                if(null == user){
                    session.setAttribute("phoneNum", null);
                    throw new AuthenticationServiceException("用户不存在");
                }

                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUname(), user.getPasswd());
                this.setDetails(request, authRequest);
                session.setAttribute("phoneNum", null);
                return this.getAuthenticationManager().authenticate(authRequest);

            }

        }
    }


    protected String obtainVerificationCode(HttpServletRequest request) {
        String verificationCode = request.getParameter(this.verificationCodeParameter);
        if(null == verificationCode){
            return "";
        }else{
            return verificationCode.toLowerCase();
        }
    }

    protected String obtainSessionVerificationCode(HttpServletRequest request){
        String sessionCode = (String) request.getSession().getAttribute(this.verificationCodeParameter);
        if(null == sessionCode){
            return "";
        }else{
            return sessionCode;
        }
    }

    @Override
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        String userName = request.getParameter(this.usernameParameter);
        return null==userName?"":userName;
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        String passwd = request.getParameter(this.passwordParameter);
        return null==passwd?"":passwd;
    }

    public String getVerificationCodeParameter() {
        return verificationCodeParameter;
    }

    public void setVerificationCodeParameter(String verificationCodeParameter) {
        this.verificationCodeParameter = verificationCodeParameter;
    }

    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    @Override
    public void setPasswordParameter(String passwordParameter) {
        this.passwordParameter = passwordParameter;
    }
}
