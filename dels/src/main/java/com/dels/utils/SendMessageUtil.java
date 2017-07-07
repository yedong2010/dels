package com.dels.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * Created by l13595 on 2017/6/5.
 * 短信发送
 */
public class SendMessageUtil {

    public static String sendmsg(String msg, String tel) {

        String url = null;
        String account = null;
        String pwd = null;

        Properties p = new Properties();
        try {
            p.load(SendMessageUtil.class.getResourceAsStream("/config.properties"));
            url = p.getProperty("msgurl");
            account = p.getProperty("account");
            pwd = p.getProperty("pwd");

        } catch (IOException e) {
            e.printStackTrace();
        }

        OutputStream out = null;
        try {
            URL u = new URL(url);
            HttpURLConnection h = (HttpURLConnection) u.openConnection();
            h.setDoOutput(true);
            h.setRequestMethod("POST"); // 设置为post请求
            h.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 设置发送数据的类型
            out = h.getOutputStream();
            String strMobile = tel;
            String strMsg = URLEncoder.encode(msg, "UTF-8");
            String postData = "Account="+account+"&PWD="+pwd+"&Msg1=" + strMsg+ "&Mobile1=" + strMobile;
            out.write(postData.getBytes());
            BufferedReader r = new BufferedReader(new InputStreamReader(h.getInputStream()));
            String line = r.readLine();
            if (line == "OK") {
                return "0";
            } else {
                return "1";
            }
        }catch (Exception ex) {
            return "9";
        }finally{
            if(out != null){
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
/*
    public static void main(String args[]) {
        String sms1 ="短信发送";
        String res = sendmsg(sms1, "手机号");
        System.out.print(res);

    }*/
}
