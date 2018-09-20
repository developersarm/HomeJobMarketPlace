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
import org.care.form.ProfileForm;
import org.care.model.Member;
import org.care.service.SeekerService;
import org.care.service.SitterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created 9/20/2018 6:39 PM
 *
 * @author Abhay Yadav
 */
public class SaveProfileAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int userId = MyApplicationContext.get().getMember().getMemberId();
        Member.MemberType memberType = MyApplicationContext.get().getMember().getType();

        ProfileForm profileForm = (ProfileForm) form;

        if (memberType == Member.MemberType.SITTER) {
            SitterService.updateProfile(userId, profileForm);
            return mapping.findForward("success");

        } else if (memberType == Member.MemberType.SEEKER) {
            SeekerService.updateProfile(userId, profileForm);
            return mapping.findForward("success");

        } else {
            return mapping.findForward("failure");
        }
    }
}
