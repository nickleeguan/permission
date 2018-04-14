package com.icode.common;

import com.icode.util.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class HttpInterceptor extends HandlerInterceptorAdapter{

    private static final String START_TIME = "requestStartTime";

    private static final Logger logger = LoggerFactory.getLogger(HttpInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI().toString();
        Map map = request.getParameterMap();
        logger.info("request start, url:{},param{}", url, JsonMapper.obj2String(map));
        long start = System.currentTimeMillis();
        request.setAttribute(START_TIME, start);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        String url = request.getRequestURI().toString();
//        Map map = request.getParameterMap();
//        long start = (Long) request.getAttribute(START_TIME);
//        long end = System.currentTimeMillis();
//
//        logger.info("request finished, url:{},cost{}", url, end - start);

        removeThreadLocalInfo();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String url = request.getRequestURI().toString();
        Map map = request.getParameterMap();
        long start = (Long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();

        logger.info("request complete, url:{},cost{}", url, end - start);

        removeThreadLocalInfo();
    }

    public void removeThreadLocalInfo(){
        RequestHolder.remove();
    }
}
