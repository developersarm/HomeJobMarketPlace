package org.care.controller.seeker;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.care.context.MyApplicationContext;
import org.care.form.JobForm;
import org.care.service.SeekerService;
import org.care.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveJobAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int userId = MyApplicationContext.get().getMember().getMemberId();

        JobForm jobForm = (JobForm) form;
        jobForm.setPostedBy(userId);

        try {
            SeekerService.postJob(jobForm);
            return mapping.findForward("success");
        } catch (ServiceException e) {
            return mapping.findForward("failure");
        }
    }
}
