package org.care.dto;

import java.util.Map;

public class SeekerRegistrationFormDTO extends MemberRegistrationFormDTO {
    private String totalChildren;
    private String spouseName;

    public SeekerRegistrationFormDTO(String firstName, String lastName, String phoneNo, String emailId,
                                     String password, String type, String address, String pincode,
                                     String totalChildren, String spouseName) {
        super(firstName, lastName, phoneNo, emailId, password, type, address, pincode);
        this.totalChildren = totalChildren;
        this.spouseName = spouseName;
    }

    @Override
    public boolean validate() {
        super.validate();
        Map<String, String> errors = super.errors;
        errors = validateTotalChildren(errors);
        errors = validateSpouseName(errors);
        super.errors = errors;
        if (errors.isEmpty()) {
            return true;
        }
        return false;
    }

    private Map<String, String> validateTotalChildren(Map<String, String> errors) {
        if (totalChildren.isEmpty()) {
            errors.put("totalChildren", "Can't be empty!");
        } else if (!totalChildren.matches("^0$|^[1-9][0-9]*$")) {
            errors.put("totalChildren", "No zeros before any number allowed!");
        }
        return errors;
    }

    private Map<String, String> validateSpouseName(Map<String, String> errors) {
        if (spouseName.isEmpty()) {
            errors.put("spouseName", "Can't be empty!");
        } else if (!spouseName.matches("^[\\p{L}]+$")) {
            errors.put("spouseName", "Only characters allowed!");
        }
        return errors;
    }

    public String getTotalChildren() {
        return totalChildren;
    }

    public String getSpouseName() {
        return spouseName;
    }
}
