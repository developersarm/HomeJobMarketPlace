package org.care.controller.visitor;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.care.dto.LoginDTO;
import org.care.form.LoginForm;
import org.care.model.Member;
import org.care.service.MemberService;
import org.care.utils.CommonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {

        LoginForm loginForm = (LoginForm) form;
        String email = loginForm.getEmailId();
        String password = CommonUtil.getHashedPassword(loginForm.getPassword());

        LoginDTO loginDTO = MemberService.authenticateUser(email, password);
        Member.MemberType mType = loginDTO.getmType();
        int userId = loginDTO.getUserId();
        Member.Status status = loginDTO.getStatus();


        if (userId > 0 && status == Member.Status.ACTIVE) {
            HttpSession session = request.getSession();
            session.setAttribute("UserId", userId);
            session.setAttribute("member", MemberService.getMemberForId(userId));
            session.setAttribute("MemberType", mType);

            if (mType == Member.MemberType.SITTER) {
                return mapping.findForward("sitter");
            } else {
                return mapping.findForward("seeker");
            }
        } else {
            request.setAttribute("error", "Invalid Username/password");
            return mapping.findForward("failure");
        }
    }
}
