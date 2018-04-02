package com.icode.common;

import com.icode.exception.ParamException;
import com.icode.exception.PermissionException;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 */
public class SpringExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(SpringExceptionResolver.class);


    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        String url = request.getRequestURL().toString();
        ModelAndView mv;
        String defaultMsg = "System error";

        //.json,.page
        //要求项目所有请求json数据的都以.json结尾，请求页面的都.page结尾
        if (url.endsWith(".json")){
            if (e instanceof PermissionException || e instanceof ParamException){
                JsonData result = JsonData.failed(e.getMessage());
                mv = new ModelAndView("jsonView", result.toMap());
            }else {
                logger.error("unknow json exception, url:" + url, e);
                JsonData result = JsonData.failed(defaultMsg);
                mv = new ModelAndView("jsonView", result.toMap());
            }
        }else if (url.endsWith(".page")){
            logger.error("unknow page exception, url:" + url, e);
            JsonData result = JsonData.failed(defaultMsg);
            mv = new ModelAndView("exception", result.toMap());
        }else {
            logger.error("unknow exception, url:" + url, e);
            JsonData result = JsonData.failed(defaultMsg);
            mv = new ModelAndView("jsonView", result.toMap());
        }
        return mv;
    }

}
