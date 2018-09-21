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
package org.care.form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * Created 9/20/2018 6:36 PM
 *
 * @author Abhay Yadav
 */
public class JobAppForm extends ActionForm {
    private String jobAppId;
    private int userId;
    private String jobId;
    private String expectedPay;
    private String jobTitle;

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobAppId() {
        return jobAppId;
    }

    public void setJobAppId(String jobAppId) {
        this.jobAppId = jobAppId;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if (jobId == null) {
            errors.add("jobId", new ActionMessage("JobApp.JobId.Invalid"));
        } else if (jobId.isEmpty()) {
            errors.add("jobId", new ActionMessage("JobApp.JobId.Empty"));
        } else if (!jobId.matches("^[0-9]+$")) {
            errors.add("jobId", new ActionMessage("JobApp.JobId.Invalid"));
        }

        if (expectedPay.isEmpty()) {
            errors.add("expectedPay", new ActionMessage("JobApp.ExpectedPay.Empty"));
        } else if (!expectedPay.matches("^([0-9]+)$|^([0-9]+\\.[0-9]+)$")) {
            errors.add("expectedPay", new ActionMessage("JobApp.ExpectedPay.Invalid"));
        } else if (!(Double.parseDouble(expectedPay) >= 0)) {
            errors.add("expectedPay", new ActionMessage("JobApp.ExpectedPay.Negative"));
        }

        return errors;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getExpectedPay() {
        return expectedPay;
    }

    public void setExpectedPay(String expectedPay) {
        this.expectedPay = expectedPay;
    }
}
