package org.care.form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

public class LoginForm extends ActionForm {
    private String emailId;
    private String password;

    public LoginForm() {}

    public LoginForm(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if (emailId.isEmpty()) {
            errors.add("emailId", new ActionMessage("Member.EmailId.Empty"));
        } else if (!emailId.matches("^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$")) {
            errors.add("emailId", new ActionMessage("Member.EmailId.NotValid"));
        }

        if (password.isEmpty()) {
            errors.add("password", new ActionMessage("Member.Password.Empty"));
        }

        return errors;
    }
}
