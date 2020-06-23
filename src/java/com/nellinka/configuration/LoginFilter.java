/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.configuration;

/**
 *
 * @author ComradeBaz
 */
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nellinka.beans.Login;

/**
 * Filter checks if LoginBean has loginIn property set to true. If it is not set
 * then request is being redirected to the index.xhml page.
 *
 * @author itcuties comradeBaz
 */
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Get the loginBean from session attribute
        Login login = (Login) ((HttpServletRequest) request).getSession().getAttribute("login");

        // For the first application request there is no loginBean in the session so user needs to log in
        // For other requests loginBean is present but we need to check if user has logged in successfully
        if (login == null || !login.isLoggedIn()) {
            String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml");
        }

        /*        HttpServletResponse resp = (HttpServletResponse) response;
        resp.addHeader("Pragma", "no-cache");
        resp.addHeader("Cache-Control", "no-cache");
        // Stronger according to blog comment below that references HTTP spec
        resp.addHeader("Cache-Control", "no-store");
        resp.addHeader("Cache-Control", "must-revalidate");
        // some date in the past
        resp.addHeader("Expires", "Mon, 8 Aug 2006 10:00:00 GMT");
        resp.setHeader("TestHeader", "hello");*/
        //chain.doFilter(request, response);
        chain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        // Nothing to do here!
    }

    @Override
    public void destroy() {
        // Nothing to do here!
    }
}
