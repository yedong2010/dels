package com.dels.controller.sysMng;

import com.dels.service.IOperateLogService;
import com.dels.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by l13595 on 2017/5/23.
 * 系统操作日志审计
 */

@RestController
@RequestMapping(value = "/sysLog/")
public class OperateLogController {

    @Autowired
    IOperateLogService service;

    @RequestMapping(value = "logData")
    public Map<String, Object> findLogData(String startTime, String endTime){
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String nowdate = df.format(d);

        if(null == startTime || null ==endTime){
            startTime = nowdate;
            endTime = Utils.dateOperation(startTime, 1);
        }else if(null != startTime && startTime.equals(nowdate)){
            endTime = Utils.dateOperation(startTime, 1);
        }else if (null != startTime && null != endTime && !startTime.equals(nowdate)){
            endTime = Utils.dateOperation(endTime, 1);
        }

        Integer sumNum = service.countSum(startTime, endTime);//总操作数
        Integer loginNum = service.countLogin(startTime, endTime); //登录总数
        int userNum = service.countUser(startTime, endTime); // 用户数
        int roleNum = service.countRole(startTime, endTime); //角色数
        List<Map<String, Object>> perUser = service.countPerUser(startTime, endTime); //每个用户操作数
        List<Map<String, Object>> perRole = service.countPerRole(startTime, endTime); //每个角色操作数

        Map<String, Object> rep = new HashMap<>();
        rep.put("sumNum",sumNum);
        rep.put("loginNum",loginNum);
        rep.put("userNum",userNum);
        rep.put("roleNum", roleNum);
        rep.put("perUser", perUser);
        rep.put("perRole", perRole);

        return rep;
    }



}
