package com.dels.controller.sysMng;

import com.dels.service.ISysMngService;
import com.dels.service.monitor.IRateService;
import com.dels.model.monitor.CommonModel;
import com.dels.model.sys.User;
import com.dels.utils.Utils;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by l13595 on 2017/3/4.
 * 系统监控
 */
@RestController
@RequestMapping("/sysMonitor/")
public class SystemMonitorController {

    /**
     * @description: 业务量查询
     * @author: l13595
     * @time: 2017/3/8 14:45
     */
    @Autowired
    IRateService rateService;

    /**
     * @description: 系统管理业务层
     * @author: l13595
     * @time: 2017/3/8 15:52
     */
    @Autowired
    ISysMngService mngService;


    /**
     * @description: 获取当前在线用户信息、ip信息
     * @author: l13595
     * @param request: HttpServletRequest
     * @return: map-在线用户列表、在线用户数、线程列表、线程数
     * @time: 2017/3/4 14:41
     */
    @RequestMapping(value = "onlineUser")
    public Map<String,Object> getUserInfo(HttpServletRequest request){
        ServletContext application = request.getServletContext();
        List<User> userList = (List<User>) application.getAttribute("userList");
        if(userList == null){
            userList = new ArrayList<User>();
        }
        List<Thread> threadList = Utils.getTotalThread();
        Map<String,Object> rep = new HashMap<>();
        rep.put("userlist",userList);
        rep.put("userNum",userList.size());
        rep.put("threadList",threadList);
        rep.put("threadNum",threadList.size());
        return rep;
    }

    /**
     * @description: 获取系统当前cpu使用情况
     * @author: l13595
     * @return: 返回cpu使用率
     * @time: 2017/3/8 14:35
     */
    @RequestMapping(value = "cpuInfo")
    public double cpuInfo(){
        Sigar sigar = new Sigar();
        try {
            CpuPerc perc = sigar.getCpuPerc();
            DecimalFormat df = new DecimalFormat("#.00");
            String cpuper = df.format(perc.getCombined() * 100);
            return Double.valueOf(cpuper);
        } catch (SigarException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    /**
     * @description: 业务量少提醒
     * @author: l13595
     * @return:
     * @time: 2017/3/8 14:41
     */
    @RequestMapping(value = "busWarning")
    public Map<String,String> businessWarning(){
        Map<String,String> rep = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH,-1);
        Integer month = cal.get(Calendar.MONTH)+1;
        List<String> cityParams = new ArrayList<>();
        User user = mngService.findUserInfo();
        if(null == user){
            return null;
        }
        cityParams.add(user.getDepartment());
        List<CommonModel> list = rateService.slys(user.getType(), cal.get(Calendar.YEAR), month, month, null, null, cityParams);
        if(null!=list && list.size()==1){
            CommonModel cModel = list.get(0);
            int businessNum = Integer.valueOf(cModel.getBjzs())+Integer.valueOf(cModel.getShfwbjzs());
            rep.put("businessNum",String.valueOf(businessNum));
            rep.put("departMent",cModel.getSname());
            return rep;
        }else{
            return null;
        }
    }

}
