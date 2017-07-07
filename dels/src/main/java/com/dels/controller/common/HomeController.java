package com.dels.controller.common;

import com.dels.service.ISysMngService;
import com.dels.utils.SendMessageUtil;
import com.dels.utils.VerificationCode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 
 * @description 页面控制器
 * @author z11595
 * @time:2016年12月22日 下午4:03:19
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    private Logger log = Logger.getLogger(this.getClass());

    @Autowired
    ISysMngService sysService;
    
    /**
     * 
     * @description 默认页控制器
     * @author z11595
     * @return 返回ModelAndView的跳转链接
     * @time 2016年12月22日 下午4:03:40
     */
    @RequestMapping(method={RequestMethod.GET})
    public String index(HttpServletRequest request, HttpServletResponse response){
        return "index";
    }
    
    /**
     * 
     * @description 登录控制器
     * @author z11595
     * @return 返回ModelAndView的跳转链接
     * @time 2016年12月22日 下午4:04:00
     */
    @RequestMapping(value={"login"}, method={RequestMethod.GET})
    public String login(HttpServletRequest request, HttpServletResponse response){
        return "login";
    }

     /**
      * @description:
      * @author: l13595
      * @param request:
      * @param phoneNumber:
      * @return:
      * @time: 2017/5/25 15:01
      */
    @RequestMapping(value = "oneTimePasswd")
    public void oneTimePasswd(HttpServletRequest request, HttpServletResponse response, String phoneNumber){

        HttpSession session = request.getSession();
        session.setAttribute("phoneNum", phoneNumber);
        session.setAttribute("oneTimePd", null);

        if(sysService.ifUserExsitForPhone(phoneNumber)){

            VerificationCode vc = new VerificationCode();
            String oneTimePd = vc.oneTimePassword(4); //动态口令的位数

            session.setAttribute("oneTimePd", oneTimePd);
            session.setAttribute("message",null);
            session.setAttribute("sendMsg","1");

            //发送动态口令给用户
            SendMessageUtil.sendmsg(oneTimePd, phoneNumber);

            log.info("========动态口令："+oneTimePd);
        }else{
            log.info("========手机号不存在");
            session.setAttribute("message","手机号不存在");
        }

    }

     /**
       * @description: 获取验证码
       * @author: l13595
       * @return: 返回生成的随机验证码
       * @time: 2017/4/21 11:21
       */
    @RequestMapping(value = "verificationImg")
    public void getVerificationIMG(HttpServletRequest request, HttpServletResponse response){
        Logger log = Logger.getLogger(this.getClass());
        try {
            VerificationCode vf = new VerificationCode();
            vf.getVerificationCode(request, response);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("生成验证码出错");
        }
    }

}
