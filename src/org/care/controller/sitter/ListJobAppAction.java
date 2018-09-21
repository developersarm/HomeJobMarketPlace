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
import org.care.context.MyApplicationContext;
import org.care.dto.SitterJobApplicationDTO;
import org.care.service.ServiceException;
import org.care.service.SitterService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created 9/20/2018 6:45 PM
 *
 * @author Abhay Yadav
 */
public class ListJobAppAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int userId = MyApplicationContext.get().getMember().getMemberId();

        String success = request.getParameter("success");
        if(success != null) {
            if(success.equalsIgnoreCase("true")) {
                request.setAttribute("msg", "Operation Successful!");
            } else if (success.equalsIgnoreCase("false")) {
                request.setAttribute("error", "Operation Failed!");
            }
        }
        try {
            List<SitterJobApplicationDTO> sitterJobApplicationDTOS = SitterService.getJobApplications(userId);
            request.setAttribute("jobApplications", sitterJobApplicationDTOS);
            return mapping.findForward("listJobAppPage");
        } catch (ServiceException e) {
            return mapping.findForward("failure");
        }
    }
}
