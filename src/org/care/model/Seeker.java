package org.care.model;

import java.util.HashSet;
import java.util.Set;

public class Seeker extends Member {
    private int totalChildren;
    private String spouseName;
    private Set<Job> jobSet = new HashSet<>();

    public Seeker(String firstName, String lastName, String phoneNo, String emailId, String password,
                  String address, int pincode, int totalChildren, String spouseName) {
        super(firstName, lastName, phoneNo, emailId, password, MemberType.SEEKER, address, pincode);
        this.totalChildren = totalChildren;
        this.spouseName = spouseName;
    }

    public Seeker(Member member, int totalChildren, String spouseName) {
        super(member);
        this.totalChildren = totalChildren;
        this.spouseName = spouseName;
    }

    public Set<Job> getJobSet() {
        return jobSet;
    }

    public void setJobSet(Set<Job> jobSet) {
        this.jobSet = jobSet;
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
