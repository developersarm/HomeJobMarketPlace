package org.care.dto;

import java.util.HashMap;
import java.util.Map;

public class MemberRegistrationFormDTO {
    Map<String, String> errors;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String emailId;
    private String password;
    private String type;
    private String address;
    private String pincode;

    MemberRegistrationFormDTO(String firstName, String lastName, String phoneNo, String emailId,
                              String password, String type, String address, String pincode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.emailId = emailId;
        this.password = password;
        this.type = type;
        this.address = address;
        this.pincode = pincode;
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

    public Map<String, String> getErrors() {
        return errors;
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

    public boolean validate() {
        Map<String, String> tempErrors = new HashMap<>();
        tempErrors = validateFirstName(tempErrors);
        tempErrors = validateLastName(tempErrors);
        tempErrors = validatePhoneNo(tempErrors);
        tempErrors = validateEmailId(tempErrors);
        tempErrors = validatePassword(tempErrors);
        tempErrors = validateType(tempErrors);
        tempErrors = validateAddress(tempErrors);
        tempErrors = validatePincode(tempErrors);
        this.errors = tempErrors;
        return tempErrors.isEmpty();
    }

    private Map<String, String> validateFirstName(Map<String, String> errors) {
        if (firstName.isEmpty()) {
            errors.put("firstName", "First Name can't be empty!");
        } else if (!firstName.matches("^[\\p{L}]+$")) {
            errors.put("firstName", "First name can only have characters");
        }
        return errors;
    }

    private Map<String, String> validateLastName(Map<String, String> errors) {
        if (lastName.isEmpty()) {
            errors.put("lastName", "Last Name can't be empty!");
        } else if (!lastName.matches("^[\\p{L}]+$")) {
            errors.put("lastName", "Last Name can only have characters");
        }
        return errors;
    }

    private Map<String, String> validatePhoneNo(Map<String, String> errors) {
        if (phoneNo.isEmpty()) {
            errors.put("phoneNo", "Phone no. can't be empty!");
        } else if (!phoneNo.matches("^[0-9]{10}$")) {
            errors.put("phoneNo", "Phone no. should be of 10 digits only.");
        }
        return errors;
    }

    private Map<String, String> validateEmailId(Map<String, String> errors) {
        if (emailId.isEmpty()) {
            errors.put("emailId", "Email id can't be empty!");
        } else if (!emailId.matches("^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+.)+[a-z]{2,5}$")) {
            errors.put("emailId", "Incorrect email format");
        }
        return errors;
    }

    private Map<String, String> validatePassword(Map<String, String> errors) {
        if (password.isEmpty()) {
            errors.put("password", "Password field can't be empty!");
        } else if (!password.matches("^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$")) {
            errors.put("password", "Password should contain atleast one number, one uppercase, one lowercase, one symbol and no space.");
        }
        return errors;
    }

    private Map<String, String> validateType(Map<String, String> errors) {
        if (type.isEmpty()) {
            errors.put("memberType", "Session not set. Go to homepage.");
        }
        return errors;
    }

    private Map<String, String> validateAddress(Map<String, String> errors) {
        if (address.isEmpty()) {
            errors.put("address", "Address can't be empty!");
        }
        return errors;
    }

    private Map<String, String> validatePincode(Map<String, String> errors) {
        if (pincode.isEmpty()) {
            errors.put("pincode", "Pincode can't be empty!");
        } else if (!pincode.matches("^[0-9]{6}$")) {
            errors.put("pincode", "Pincode should be of 6 digit only.");
        }
        return errors;
    }
}
