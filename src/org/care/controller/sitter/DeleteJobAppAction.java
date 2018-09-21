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
import org.care.service.SitterService;
import org.care.utils.CommonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created 9/20/2018 6:46 PM
 *
 * @author Abhay Yadav
 */
public class DeleteJobAppAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int userId = MyApplicationContext.get().getMember().getMemberId();

        String jobAppIdRaw = request.getParameter("JobApplicationId");
        if (jobAppIdRaw != null && !jobAppIdRaw.isEmpty() && jobAppIdRaw.matches("^[0-9]+$")) {
            int jobAppId = Integer.parseInt(jobAppIdRaw);

            if (userId == SitterService.getUserIdforJobAppId(jobAppId)) {
                boolean isDeleted = false;
                isDeleted = SitterService.deleteJobApplication(jobAppId);

                if (isDeleted) {
                    return mapping.findForward("success");
                }
            }
        }
        return mapping.findForward("failure");
    }
}
