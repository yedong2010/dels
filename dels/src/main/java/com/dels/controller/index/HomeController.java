package com.dels.controller.index;

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
 * @description 主页控制器
 * @author MaytoMarry
 * @time: 2017-07-11
 */
@Controller
@RequestMapping("/index")
public class HomeController {

    private Logger log = Logger.getLogger(this.getClass());

    /**
     * 
     * @description 默认页控制器
     * @author MaytoMarry
     * @return 返回ModelAndView的跳转链接
     * @time 2017-07-11
     */
    @RequestMapping(method={RequestMethod.GET})
    public String index(HttpServletRequest request, HttpServletResponse response){
        return "index";
    }


}
