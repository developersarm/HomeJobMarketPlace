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
import org.care.dto.ProfileDTO;
import org.care.dto.SeekerProfileDTO;
import org.care.dto.SitterProfileDTO;
import org.care.form.ProfileForm;
import org.care.model.Member;
import org.care.service.SeekerService;
import org.care.service.ServiceException;
import org.care.service.SitterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created 9/20/2018 6:38 PM
 *
 * @author Abhay Yadav
 */
public class ViewProfileAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int userId = MyApplicationContext.get().getMember().getMemberId();
        Member.MemberType memberType = MyApplicationContext.get().getMember().getType();

        ProfileDTO profileDTO;
        ProfileForm profileForm = (ProfileForm) form;

        String success = request.getParameter("success");
        if (success != null) {
            if (success.equalsIgnoreCase("true")) {
                request.setAttribute("msg", "Operation Successful!");
            } else if (success.equalsIgnoreCase("false")) {
                request.setAttribute("error", "Operation Failed!");
            }
        }

        try {
            if (memberType == Member.MemberType.SEEKER) {
                profileDTO = SeekerService.getProfile(userId);
                profileForm.setFirstName(profileDTO.getFirstName());
                profileForm.setLastName(profileDTO.getLastName());
                profileForm.setPhoneNo(profileDTO.getPhoneNo());
                profileForm.setEmailId(profileDTO.getEmailId());
                profileForm.setAddress(profileDTO.getAddress());
                profileForm.setPincode(profileDTO.getPincode());
                profileForm.setType(Member.MemberType.SEEKER.toString());
                profileForm.setTotalChildren(((SeekerProfileDTO) profileDTO).getTotalChildren());
                profileForm.setSpouseName(((SeekerProfileDTO) profileDTO).getSpouseName());

            } else if (memberType == Member.MemberType.SITTER) {
                profileDTO = SitterService.getProfile(userId);
                profileForm.setFirstName(profileDTO.getFirstName());
                profileForm.setLastName(profileDTO.getLastName());
                profileForm.setPhoneNo(profileDTO.getPhoneNo());
                profileForm.setEmailId(profileDTO.getEmailId());
                profileForm.setAddress(profileDTO.getAddress());
                profileForm.setPincode(profileDTO.getPincode());
                profileForm.setType(Member.MemberType.SITTER.toString());
                profileForm.setExperience(((SitterProfileDTO) profileDTO).getExperience());
            } else {
                return mapping.findForward("failure");
            }
        } catch (ServiceException e) {
            return mapping.findForward("failure");
        }
        return mapping.findForward("profilePage");
    }
}
