package org.care.model;

public class Sitter extends Member {
    int experience;

    public Sitter(int id, String firstName, String lastName, int phoneNo, String emailId, String password, String address, int experience) {
        super(id, firstName, lastName, phoneNo, emailId, password, address);
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
