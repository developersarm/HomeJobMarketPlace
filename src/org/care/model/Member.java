package org.care.model;

public class Member {
    private int id;
    private String firstName;
    private String lastName;
    private int phoneNo;
    private String emailId;
    private String password;
    private enum type {seeker, sitter}
    private String address;
    private enum status {active, inactive}

    public Member(String firstName, String lastName, int phoneNo, String emailId, String password, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.emailId = emailId;
        this.password = password;
        this.address = address;
    }

    public Member(int id, String firstName, String lastName, int phoneNo, String emailId, String password, String address) {
        this(firstName, lastName, phoneNo, emailId, password, address);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(int phoneNo) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
