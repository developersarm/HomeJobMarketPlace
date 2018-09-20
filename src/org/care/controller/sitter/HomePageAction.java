/*
 * Copyright 2018 (c) Abhay
 * Bangalore, India.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Abhay Yadav. ("Confidential Information").  You shall not disclose
 * such Confidential Information and shall use it only in accordance with
 * the terms of an agreement between you and Abhay.
 */
package org.care.controller.sitter;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created 9/20/2018 6:37 PM
 *
 * @author Abhay Yadav
 */
public class HomePageAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String success = request.getParameter("success");
        if(success != null) {
            if(success.equalsIgnoreCase("true")) {
                request.setAttribute("msg", "Operation Successful!");
            } else if (success.equalsIgnoreCase("false")) {
                request.setAttribute("error", "Operation Failed!");
            }
        }

        return mapping.findForward("homepage");
    }
}
