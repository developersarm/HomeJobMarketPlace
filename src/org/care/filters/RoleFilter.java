/*
 * Copyright 2006-2018 (c) Care.com, Inc.
 * 1400 Main Street, Waltham, MA, 02451, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Care.com, Inc. ("Confidential Information").  You shall not disclose
 * such Confidential Information and shall use it only in accordance with
 * the terms of an agreement between you and CZen.
 */
package org.care.filters;

import org.care.context.MyApplicationContext;
import org.care.model.Member;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RoleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        Member member;
        boolean hasErrors = false;

        if (httpRequest.getRequestURI().contains("/member")) {
            hasErrors = MyApplicationContext.get().getMember() == null;

        } else if (httpRequest.getRequestURI().contains("/seeker")) {
            member = MyApplicationContext.get().getMember();
            hasErrors = member == null || member.getType() != Member.MemberType.SEEKER;

        } else if (httpRequest.getRequestURI().contains("/sitter")) {
            member = MyApplicationContext.get().getMember();
            hasErrors = member == null || member.getType() != Member.MemberType.SITTER;

        } else if ((member = MyApplicationContext.get().getMember()) != null){

            if (member.getType() == Member.MemberType.SITTER) {
                ((HttpServletResponse) servletResponse).sendRedirect("/HomeJobMarketplace/sitter/home.do");
//                servletRequest.getRequestDispatcher("/sitter/home.do").forward(servletRequest, servletResponse);

            } else if (member.getType() == Member.MemberType.SEEKER) {
                ((HttpServletResponse) servletResponse).sendRedirect("/HomeJobMarketplace/seeker/home.do");
//                servletRequest.getRequestDispatcher("/seeker/home.do").forward(servletRequest, servletResponse);
            }
        }

        if (hasErrors) {
            httpRequest.getRequestDispatcher("/WEB-INF/jsp/404error.jsp").forward(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
