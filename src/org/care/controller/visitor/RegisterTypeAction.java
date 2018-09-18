package org.care.controller.visitor;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.care.form.RegistrationForm;
import org.care.model.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterTypeAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        String memberType = request.getParameter("type");

        RegistrationForm regForm = (RegistrationForm) form;
        if (memberType != null && memberType.equals("seeker")) {
            request.setAttribute("memberType", "SEEKER");
            regForm.setType(Member.MemberType.SEEKER.name());
            return mapping.findForward("registerPage");
        } else if (memberType != null && memberType.equals("sitter")) {
            request.setAttribute("memberType", "SITTER");
            regForm.setType(Member.MemberType.SITTER.name());
            return mapping.findForward("registerPage");
        } else {
            request.setAttribute("error", "Please choose your purpose before registration");
            return mapping.findForward("home");
        }
    }
}
