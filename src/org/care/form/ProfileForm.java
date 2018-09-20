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
 * Created 9/20/2018 6:35 PM
 *
 * @author Abhay Yadav
 */
public class ProfileForm extends ActionForm {
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String emailId;
    private String address;
    private String pincode;
    private String type;
    private String experience;
    private String totalChildren;
    private String spouseName;

    public ProfileForm() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if (firstName.isEmpty()) {
            errors.add("firstName", new ActionMessage("Member.FirstName.Empty"));
        } else if (!firstName.matches("^[\\p{L}]+$")) {
            errors.add("firstName", new ActionMessage("Member.FirstName.NotValid"));
        }

        if (lastName.isEmpty()) {
            errors.add("lastName", new ActionMessage("Member.LastName.Empty"));
        } else if (!lastName.matches("^[\\p{L}]+$")) {
            errors.add("lastName", new ActionMessage("Member.LastName.NotValid"));
        }

        if (phoneNo.isEmpty()) {
            errors.add("phoneNo", new ActionMessage("Member.PhoneNo.Empty"));
        } else if (!phoneNo.matches("^[0-9]{10}$")) {
            errors.add("phoneNo", new ActionMessage("Member.PhoneNo.NotValid"));
        }

        if (address.isEmpty()) {
            errors.add("address", new ActionMessage("Member.Address.Empty"));
        }

        if (pincode.isEmpty()) {
            errors.add("pincode", new ActionMessage("Member.Pincode.Empty"));
        } else if (!pincode.matches("^[0-9]{6}$")) {
            errors.add("pincode", new ActionMessage("Member.Pincode.NotValid"));
        }

        if (type.equals("SITTER")) {
            if (experience.isEmpty()) {
                errors.add("experience", new ActionMessage("Sitter.Experience.Empty"));
            } else if (!experience.matches("^[0-9]{1,4}$")) {
                errors.add("experience", new ActionMessage("Sitter.Experience.NotValid"));
            } else if (Integer.parseInt(experience) > 120) {
                errors.add("experience", new ActionMessage("Sitter.Experience.UpperLimit"));
            }

        } else if (type.equals("SEEKER")) {
            if (totalChildren.isEmpty()) {
                errors.add("totalChildren", new ActionMessage("Seeker.TotalChildren.Empty"));
            } else if (!totalChildren.matches("^0$|^[1-9][0-9]*$")) {
                errors.add("totalChildren", new ActionMessage("Seeker.TotalChildren.NotValid"));
            }

            if (spouseName.isEmpty()) {
                errors.add("spouseName", new ActionMessage("Seeker.SpouseName.Empty"));
            } else if (!spouseName.matches("^[\\p{L}]+$")) {
                errors.add("spouseName", new ActionMessage("Seeker.SpouseName.NotValid"));
            }

        } else {
            errors.add("type", new ActionMessage("Member.Type.NotValid"));
        }

        return errors;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getTotalChildren() {
        return totalChildren;
    }

    public void setTotalChildren(String totalChildren) {
        this.totalChildren = totalChildren;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }
}
