package com.miku.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Utopiamiku
 * @date 2020/8/9 12:14
 * @File VisitFilter.py
 */
public class VisitFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        //如果用户没有登录，则返回到登录页面
        boolean is =  request.getRequestURI().toString().indexOf("ogin")==-1;
        if(is && session.getAttribute("user")==null){

            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
