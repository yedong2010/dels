package com.dels.controller.index;

import com.dels.model.index.Accomment;
import com.dels.model.index.Message;
import com.dels.service.index.IAccommentService;
import com.dels.service.index.IMessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @description 主页控制器
 * @author MaytoMarry
 * @time: 2017-07-11
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    private Logger log = Logger.getLogger(this.getClass());

    @Autowired
    IAccommentService acService;

    @Autowired
    IMessageService msService;

    /**
     * 查询ac评论
     * @param req
     * @return
     */
    @RequestMapping(value = "queryaccbyid", method = {RequestMethod.POST})
    public List<Accomment> queryAccById(@RequestBody Map<String, String> req){
        log.info("query acid:" + req);
        return acService.getDelAccBAcId(req.get("acid"));
    }

    /**
     * 查看留言板
     * @return
     */
    @RequestMapping(value = "querycontents", method = {RequestMethod.GET})
    public List<Message> queryContents(){
        log.info("query contents:" + new Date());
        return msService.getMessages();
    }

    /**
     * 插入留言
     * @param req
     * @return
     */
    @RequestMapping(value = "insercontent", method = {RequestMethod.POST})
    public Map<String, String> insertContent(@RequestBody Map<String, String> req){
        log.info("insert contents:" + req);
        Map<String, String> result = new HashMap<>();
        result.put("msg", msService.insertMessage(req.get("content")));
        return result;
    }



}
