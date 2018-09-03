package org.care.dto;

public class SitterProfileDTO extends ProfileDTO {
    int experience;

    public SitterProfileDTO(String firstName, String lastName, String phoneNo, String emailId, String address,
                            int pincode, int experience) {
        super(firstName, lastName, phoneNo, emailId, address, pincode);
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
