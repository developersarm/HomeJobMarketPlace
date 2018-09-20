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
package org.care.controller.member;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.care.context.MyApplicationContext;
import org.care.service.MemberService;
import org.care.utils.CommonUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created 9/20/2018 6:40 PM
 *
 * @author Abhay Yadav
 */
public class DeleteProfileAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        int userId = MyApplicationContext.get().getMember().getMemberId();
        boolean isDeleted = MemberService.deleteUser(userId);

        if (isDeleted) {
            session.invalidate();
            return mapping.findForward("success");
        } else {
            request.setAttribute("error", "Can't delete profile");
            return mapping.findForward("failure");
        }
    }
}
