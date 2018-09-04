package org.care.presentation.sitter;

import org.care.service.SitterService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteJobApplicationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int jobAppId = Integer.parseInt(req.getParameter("JobApplicationId"));
        boolean isDeleted = false;
        isDeleted = SitterService.deleteJobApplication(jobAppId);

        if (isDeleted) {
            req.setAttribute("msg", "Successfully deleted!");
        } else {
            req.setAttribute("error", "Deletion failed");
        }
        resp.sendRedirect("/HomeJobMarketplace/sitter/list-job-application");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
