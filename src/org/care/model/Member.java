package org.care.model;

import org.omg.PortableInterceptor.INACTIVE;

public class Member {
    private int id;
    private String firstName;
    private String lastName;
    private int phoneNo;
    private String emailId;
    private String password;
    private MemberType type;
    private String address;
    private int pincode;
    Status status;

    public enum MemberType {SEEKER, SITTER;};
    public enum Status {ACTIVE, INACTIVE}

    public Member(String firstName, String lastName, int phoneNo, String emailId, String password, MemberType type,
                  String address, int pincode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.emailId = emailId;
        this.password = password;
        this.type = type;
        this.address = address;
        this.pincode = pincode;
        this.status = Status.ACTIVE;
    }

    public Member(int id, String firstName, String lastName, int phoneNo, String emailId, String password,
                  MemberType type, String address, int pincode) {
        this(firstName, lastName, phoneNo, emailId, password, type, address, pincode);
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

    public MemberType getType() {
        return type;
    }

    public void setType(MemberType type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }
}
