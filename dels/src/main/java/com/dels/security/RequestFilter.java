package com.dels.security;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @description request请求过滤器，拦截非法Referer
 * @author y11290
 * @time 2017年05月15日 15:48:00
 */
public class RequestFilter implements Filter {

    public FilterConfig config;

    private String[] referes;

    private String[] urls;


    public void init(FilterConfig filterConfig) {
        this.config= filterConfig;
        String acceptReferers = config.getInitParameter("refererAccepts");
        referes = acceptReferers.split(";");
        urls  = new String[]{"/"};
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String referer = request.getHeader("referer");
        String path = request.getServletPath();
        response.setHeader("Content-Security-Policy", "");
        if ((referer != null && isAccept(referer)) || passUrl(path)){
            chain.doFilter(req, resp);
            return;
        }else {
            response.sendError(406,"非法请求");
        }
    }

    private boolean isAccept(String referer){
        if(referes.length == 0) return true;
        for(String ref : referes){
            if(referer.startsWith(ref)){
                return true;
            }
        }
        return false;
    }

    private boolean passUrl(String url){
        for(String u : urls){
            if(u.equalsIgnoreCase(url))
                return true;
        }
        return false;
    }

    public void destroy(){
        this.config = null;
        this.referes=null;
    }
}
