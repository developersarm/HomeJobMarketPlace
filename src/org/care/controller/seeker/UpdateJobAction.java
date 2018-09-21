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
package org.care.controller.seeker;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.care.context.MyApplicationContext;
import org.care.form.JobForm;
import org.care.service.SeekerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created 9/20/2018 6:42 PM
 *
 * @author Abhay Yadav
 */
public class UpdateJobAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int userId = MyApplicationContext.get().getMember().getMemberId();

        JobForm jobForm = (JobForm) form;
        String jobIdRaw = jobForm.getId();

        if (jobIdRaw != null && !jobIdRaw.isEmpty() && jobIdRaw.matches("^[0-9]+$")) {
            int jobId = Integer.parseInt(jobIdRaw);

            if (userId == SeekerService.getUserIdforJobId(jobId)) {
                boolean isUpdated = SeekerService.updateJob(userId, jobForm);

                if (isUpdated) {
                    return mapping.findForward("success");
                } else {
                    return mapping.findForward("failure");
                }
            } else {
                return mapping.findForward("failure");
            }
        } else {
            return mapping.findForward("failure");
        }
    }
}
