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
import org.care.utils.JobUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created 9/20/2018 6:36 PM
 *
 * @author Abhay Yadav
 */
public class JobForm extends ActionForm {
    private String id;
    private String title;
    private int postedBy;
    private String startDate;
    private String endDate;
    private String payPerHour;

    public JobForm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if (title.isEmpty()) {
            errors.add("title", new ActionMessage("Job.Title.Empty"));

        } else if (JobUtil.checkForBadWords(title)) {
            errors.add("title", new ActionMessage("Job.Title.BadWords"));
        }

        if (startDate == null) {
            errors.add("startDate", new ActionMessage("Job.StartDate.Empty"));
        } else if (startDate.isEmpty()) {
            errors.add("startDate", new ActionMessage("Job.StartDate.Empty"));
        } else {
            try {
                Date sDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                if (sDate.before(new Date())) {
                    errors.add("startDate", new ActionMessage("Job.StartDate.LowerLimit"));
                }
            } catch (ParseException e) {
                Logger logger = Logger.getLogger(JobForm.class.getName());
                logger.log(Level.SEVERE, "Cannot convert html date into java date format");
            }
        }

        if (endDate == null) {
            errors.add("endDate", new ActionMessage("Job.EndDate.Empty"));
        } else if (endDate.isEmpty()) {
            errors.add("endDate", new ActionMessage("Job.EndDate.Empty"));
        } else {
            try {
                Date sDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                Date eDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
                if (eDate.before(sDate)) {
                    errors.add("endDate", new ActionMessage("Job.EndDate.LowerLimit"));
                }
            } catch (ParseException e) {
                Logger logger = Logger.getLogger(JobForm.class.getName());
                logger.log(Level.SEVERE, "Cannot convert html date into java date format");
            }
        }

        if (payPerHour.isEmpty()) {
            errors.add("payPerHour", new ActionMessage("Job.PayPerHour.Empty"));
        } else if (!payPerHour.matches("^([0-9]+)$|^([0-9]+\\.[0-9]+)$")) {
            errors.add("payPerHour", new ActionMessage("Job.PayPerHour.NotValid"));
        } else if (!(Double.parseDouble(payPerHour) >= 0)) {
            errors.add("payPerHour", new ActionMessage("Job.PayPerHour.Negative"));
        }

        return errors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(int postedBy) {
        this.postedBy = postedBy;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPayPerHour() {
        return payPerHour;
    }

    public void setPayPerHour(String payPerHour) {
        this.payPerHour = payPerHour;
    }
}
