package org.care.dto;

public class SeekerProfileDTO extends ProfileDTO {
    int totalChildren;
    String spouseName;

    public SeekerProfileDTO(String firstName, String lastName, String phoneNo, String emailId, String address,
                            int pincode, int totalChildren, String spouseName) {
        super(firstName, lastName, phoneNo, emailId, address, pincode);
        this.totalChildren = totalChildren;
        this.spouseName = spouseName;
    }

    public int getTotalChildren() {
        return totalChildren;
    }

    public void setTotalChildren(int totalChildren) {
        this.totalChildren = totalChildren;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }
}
