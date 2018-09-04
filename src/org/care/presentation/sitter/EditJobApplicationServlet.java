package org.care.presentation.sitter;

import org.care.service.SitterService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditJobApplicationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        double expectedPay = Double.parseDouble(req.getParameter("ExpectedPay"));
        int jobAppId = Integer.parseInt(req.getParameter("JobApplicationId"));
        String title = SitterService.getJobApplicationTitle(jobAppId);

        req.setAttribute("JobApplicationId", jobAppId);
        req.setAttribute("ExpectedPay", expectedPay);
        req.setAttribute("Title", title);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/sitter/editjobapplication.jsp");
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int jobAppId = Integer.parseInt(req.getParameter("jobappid"));
        double expectedPay = Double.parseDouble(req.getParameter("expectedpay"));

        boolean isUpdated = SitterService.updateJobApplication(jobAppId, expectedPay);

        if (isUpdated) {
            req.setAttribute("msg", "Successfully updated the application!");

        } else {
            req.setAttribute("error", "Application updation failed!");
        }
        resp.sendRedirect("/HomeJobMarketplace/sitter/list-job-application");
    }
}
