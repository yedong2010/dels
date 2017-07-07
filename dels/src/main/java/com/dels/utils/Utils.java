package com.dels.utils;

import com.dels.security.UserInfo;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description:uuid工具类
 * @author: l13595
 * @time: 2017/1/12 17:51
 */
public class Utils {
    private static Logger log = Logger.getLogger(Utils.class);

    /**
     * @description:生成记录id
     * @author: l13595
     * @param:
     * @return：随机生成的记录id
     * @time: 2017/1/13 15:19
     */
    public static String getUuid(){
       return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * @description: 获取客户端访问地址
     * @author: l13595
     * @param :request
     * @return: 客户端访问服务器的IP地址
     * @time: 2017/1/13 15:55
     */
    public static String getIpAddress(HttpServletRequest request){
        Logger log = Logger.getLogger(Utils.class);

        String ipAddress = request.getHeader("X-Forwarded-For");
        log.info("========X-Forwarded-for: "+ipAddress);

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
            log.info("===========Proxy-Client-IP:"+ ipAddress);
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
            log.info("===========WL-Proxy-Client-IP:"+ ipAddress);
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
            log.info("===========HTTP_CLIENT_IP:"+ ipAddress);
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.info("===========HTTP_X_FORWARDED_FOR:"+ ipAddress);
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Cdn-Src-Ip");
            log.info("===========Cdn-Src-Ip:"+ ipAddress);
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            log.info("===========getRemoteAddr:"+ ipAddress);
        }

        //存在多级代理的情况，去第一个非unknow的值
        String []ips = ipAddress.split(",");
        for(String ipStr: ips){
            if (!"unknown".equalsIgnoreCase(ipStr)){
                ipAddress = ipStr;
                log.info("============= 第一个非unknowip："+ipAddress);
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
        log.info("========= ipAddress:" + ipAddress);
        return ipAddress;
    }

    /**
     * @description: 获取当前用户
     * @author: l13595
     * @time: 2017/3/7 19:40
     */
    public static String getCurUserName() {
        try{

            String username = "";
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal instanceof UserDetails) {
                username = ((UserDetails)principal).getUsername();
            }
            return username;

        }catch (Exception e){
            log.info(e.getMessage());
            return null;
        }
    }

    /**
     * @description: 获取但钱用户ip
     * @author: l13595
     * @return:  返回ip信息
     * @time: 2017/3/7 19:41
     */
    public static String getCurUserIp(){
        String ipAddress = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            ipAddress = ((UserInfo)principal).getUserIpAddress();
        }
        return ipAddress;
    }

    /**
     * @description: 查询当前系统线程总数
     * @author: l13595
     * @return: 返回线程总数
     * @time: 2017/3/6 10:01
     */
    public static List<Thread> getTotalThread(){
        Map<Thread, StackTraceElement[]> map=Thread.getAllStackTraces();
        List<Thread> list = new ArrayList<>();
        Iterator it=map.keySet().iterator();
        while (it.hasNext()) {
            list.add((Thread) it.next());
        }
        return list;
    }

    /**
     * @description: 时间操作，增加天数
     * @author: l13595
     * @param d: 要修改的时间
     * @param dayNum: 要增加的天数
     * @return: 返回增加天数后的日期
     * @time: 2017/3/10 14:54
     */
    public static String dateOperation(String d,int dayNum){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(d);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, dayNum);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @description: 生成tikenid
     *            放在session中，给在线用户作为唯一标识
     * @author: l13595
     * @param userName:  用户名
     * @param ip: 用户ip
     * @return: 返回生成的字符串
     * @time: 2017/3/10 17:02
     */
    public static String createTokenId(String userName, String ip){
        StringBuffer b = new StringBuffer();
        b.append(userName).append(ip);
        Calendar c = Calendar.getInstance();
        b.append(c.getTimeInMillis());
        return b.toString();
    }

    /**
     * @description: 组装出入的int型参数为yyyy-mm格式的日期字符串
     * @author: l13595
     * @param: year-年份，month-月份
     * @return: 返回 yyyy-mm 格式的日期字符串
     * @time: 2017/3/24 16:11
     */
    public static String constractTimeByInt(Integer year, Integer month){
        if (month>9){
            return (String.valueOf(year) + "-" + String.valueOf(month));
        }else{
            return (String.valueOf(year) + "-0" + String.valueOf(month));
        }
    }


    
}
