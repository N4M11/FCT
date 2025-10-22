package com.rgb.training.app.common.rest;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nami
 */

import com.rgb.training.app.boundary.viewbeans.LoginBean;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class LoginFilter implements Filter{
    
   private static final Logger LOGGER = LoggerFactory.getLogger(LoginFilter.class);
   public static final String LOGIN_URL = "/login.xhtml";
 
    
    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
      public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String contextPath = req.getContextPath();
        String requestURI = req.getRequestURI();
        
         if (requestURI.startsWith(contextPath + "/resources/")
            || requestURI.endsWith(LOGIN_URL)
            || requestURI.contains("javax.faces.resource")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
         
        HttpSession session = req.getSession(false);
        
        LoginBean loginBean = null;
        if (session != null) {
            Object attr = session.getAttribute("loginBean");
            if (attr instanceof LoginBean) {
                loginBean = (LoginBean) attr;
            }
        }
        
          if (loginBean != null && loginBean.islogged()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            res.sendRedirect(req.getContextPath() + LOGIN_URL);
        }
        
        
  }
    
}
