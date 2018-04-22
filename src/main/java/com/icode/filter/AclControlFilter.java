package com.icode.filter;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.icode.common.ApplicationContextHelper;
import com.icode.common.JsonData;
import com.icode.common.RequestHolder;
import com.icode.model.SysUser;
import com.icode.service.interfaces.SysCoreService;
import com.icode.util.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AclControlFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AclControlFilter.class);

    private static Set<String> exclusionUrlSet = Sets.newConcurrentHashSet();

    private static final String noAuthUrl = "/sys/user/noAuth.page";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String exclusion = filterConfig.getInitParameter("exclusionUrls");
        List<String> exclusionUrlList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(exclusion);
        exclusionUrlSet = Sets.newConcurrentHashSet(exclusionUrlList);
        exclusionUrlSet.add(noAuthUrl);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        Map requestMap = request.getParameterMap();

        if (exclusionUrlSet.contains(servletPath)){
            filterChain.doFilter(servletRequest, servletResponse);
        }

        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser == null){
            logger.debug("someone visit {}, but no login,parameter:{}", servletPath, JsonMapper.obj2String(requestMap));
            noAuth(request, response);
            return;
        }

        SysCoreService sysCoreService = ApplicationContextHelper.popBean(SysCoreService.class);
        if (!sysCoreService.hasUrlAcl(servletPath)){
            logger.info("{} visit {}, but no login,parameter:{}", JsonMapper.obj2String(sysUser),
                    servletPath, JsonMapper.obj2String(requestMap));
            noAuth(request, response);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void noAuth(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String servletPath = request.getServletPath();
        if (servletPath.endsWith(".json")){
            JsonData jsonData = JsonData.failed("没有访问权限,如需要访问请联系管理员");
            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(JsonMapper.obj2String(jsonData));
        }else {
            clientRedirect(noAuthUrl, response);
        }
    }

    private void clientRedirect(String url, HttpServletResponse response) throws IOException{
        response.setHeader("Content-Type", "text/html");
        response.getWriter().print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n" + "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>\n"
                + "<title>跳转中...</title>\n" + "</head>\n" + "<body>\n" + "跳转中，请稍候...\n" + "<script type=\"text/javascript\">//<![CDATA[\n"
                + "window.location.href='" + url + "?ret='+encodeURIComponent(window.location.href);\n" + "//]]></script>\n" + "</body>\n" + "</html>\n");
    }

    @Override
    public void destroy() {

    }
}
