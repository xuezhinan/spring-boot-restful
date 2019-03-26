package com.asiainfo.springbootrestful.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "encodingFilter",urlPatterns = "/*")
public class EncodingFilter implements Filter {

    Logger log = LoggerFactory.getLogger(getClass());
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String encod = request.getCharacterEncoding();
        request.setCharacterEncoding("UTF-8");
        //放行请求
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
       log.info("x销毁");
    }
}
