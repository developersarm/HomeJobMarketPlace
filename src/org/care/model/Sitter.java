package org.care.model;

public class Sitter extends Member {
    private int experience;

    public Sitter(int id, String firstName, String lastName, int phoneNo, String emailId, String password,
                  String address, int pincode, int experience) {
        super(id, firstName, lastName, phoneNo, emailId, password, MemberType.SITTER, address, pincode);
        this.experience = experience;
    }

    public Sitter(String firstName, String lastName, int phoneNo, String emailId, String password, String address,
                  int pincode, int experience) {
        super(firstName, lastName, phoneNo, emailId, password, MemberType.SITTER, address, pincode);
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
