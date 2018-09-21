package org.care.form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.care.service.MemberService;
import org.care.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

public class RegistrationForm extends ActionForm {
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String emailId;
    private String password;
    private String type;
    private String address;
    private String pincode;
    private String totalChildren;
    private String spouseName;
    private String experience;

    public RegistrationForm() {}

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

        try {
            if (emailId.isEmpty()) {
                errors.add("emailId", new ActionMessage("Member.EmailId.Empty"));
            } else if (!emailId.matches("^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$")) {
                errors.add("emailId", new ActionMessage("Member.EmailId.NotValid"));
            } else if (MemberService.isEmailIdRegistered(emailId)) {
                errors.add("emailId", new ActionMessage("Member.EmailId.Registered"));
            }
        } catch (ServiceException e) {
            Logger.getLogger(RegistrationForm.class.getName()).severe("Can't check emailId with db: " + e);
        }

        if (password.isEmpty()) {
            errors.add("password", new ActionMessage("Member.Password.Empty"));
        } else if (!password.matches("^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$")) {
            errors.add("password", new ActionMessage("Member.Password.NotValid"));
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
