package org.care.model;

public class Seeker extends Member {
    int totalChildren;
    String spouseName;

    public Seeker(int id, String firstName, String lastName, int phoneNo, String emailId, String password,
                  String address, int totalChildren, String spouseName) {
        super(id, firstName, lastName, phoneNo, emailId, password, MemberType.SEEKER, address);
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
