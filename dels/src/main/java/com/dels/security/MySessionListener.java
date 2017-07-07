package com.dels.security;

import com.dels.model.sys.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

/**
 * Created by h3c on 2017/3/6.
 */
public class MySessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        String flag = null;
        if (null != session.getAttribute("userFlag")) {
            flag =  session.getAttribute("userFlag").toString();
        }
        ServletContext application = session.getServletContext();
        List<User> userList = (List<User>) application.getAttribute("userList");
        if(null != userList  && userList.size() > 0 && null != flag){
            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                if(flag.equals(user.getId())){
                    userList.remove(i);
                    break;
                }
            }
            application.setAttribute("userList",userList);
        }
    }
}
