package org.care.context;

import org.care.dao.*;
import org.care.model.Member;

class DAOFactory {

    public DAO getObj(String className) {
        switch (className) {
            case "MemberDAO":
                return new MemberDAO<Member>();
            case "SeekerDAO":
                return new SeekerDAO();
            case "SitterDAO":
                return new SitterDAO();
            case "JobDAO":
                return new JobDAO();
            case "JobApplicationDAO":
                return new JobApplicationDAO();
        }
        return null;
    }
}
