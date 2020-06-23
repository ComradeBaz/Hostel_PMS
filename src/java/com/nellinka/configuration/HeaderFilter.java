/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nellinka.configuration;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class HeaderFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.addHeader(HTTPCacheHeader.CACHE_CONTROL.getName(), "no-store");
        response.addHeader(HTTPCacheHeader.PRAGMA.getName(), "no-cache"); // HTTP 1.0
        //response.addDateHeader(HTTPCacheHeader.EXPIRES.getName(), 0L); // Proxies
        
        fc.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }
}
