package org.care.controller.visitor;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.care.context.MyApplicationContext;
import org.care.form.RegistrationForm;
import org.care.model.Member;
import org.care.service.SeekerService;
import org.care.service.SitterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberRegistrationAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        RegistrationForm regForm = (RegistrationForm) form;
        Member.MemberType memberType = Member.MemberType.valueOf(regForm.getType());

        if (memberType == Member.MemberType.SITTER) {
            Integer userId = SitterService.register(regForm);
            request.getSession().setAttribute("UserId", userId);
            return mapping.findForward("sitter");

        } else if (memberType == Member.MemberType.SEEKER) {
            Integer userId = SeekerService.register(regForm);
            request.getSession().setAttribute("UserId", userId);
            return mapping.findForward("seeker");
        }

        return mapping.findForward("failure");
    }
}
