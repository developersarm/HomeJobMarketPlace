package org.care.service;

import org.care.context.MyApplicationContext;
import org.care.dao.JobApplicationDAO;
import org.care.dao.SitterDAO;
import org.care.dto.SitterJobsListDTO;
import org.care.dto.SitterProfileDTO;
import org.care.model.Sitter;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SitterService {

    public static void register(Sitter sitter) {
        MyApplicationContext.getFactory(SitterDAO.class).create(sitter);
    }

    public static int validateUser(String email, String password) {
        //todo hash the password
        return MyApplicationContext.getFactory(SitterDAO.class).get(email, password);
    }

    public static List<SitterJobsListDTO> getAppliedJobsList(int userId) {
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
        List<Map<String, Object>> sitterJobsList = jobApplicationDAO.getSitterJobsList(userId);
        List<SitterJobsListDTO> sitterJobsListDTO = new LinkedList<>();

        for (Map<String, Object> tempMap :
                sitterJobsList) {
            String title = (String) tempMap.get("title");
            Timestamp startDate = (Timestamp) tempMap.get("startDate");
            Double expectedPay = (Double) tempMap.get("expectedPay");
            sitterJobsListDTO.add(new SitterJobsListDTO(title, startDate, expectedPay));
        }
        return sitterJobsListDTO;
    }

    public static SitterProfileDTO getProfile(int userId) {
        SitterDAO sitterDAO = MyApplicationContext.getFactory(SitterDAO.class);
        Sitter sitter = sitterDAO.get(userId);
        String firstName = sitter.getFirstName();
        String lastName = sitter.getLastName();
        String phoneNo = sitter.getPhoneNo();
        String emailId = sitter.getEmailId();
        String address = sitter.getAddress();
        int pincode = sitter.getPincode();
        int experience = sitter.getExperience();
        return new SitterProfileDTO(firstName, lastName, phoneNo, emailId, address, pincode, experience);
    }

    public static void updateProfile(int userId, SitterProfileDTO sitterProfileDTO) {
        SitterDAO sitterDAO = MyApplicationContext.getFactory(SitterDAO.class);
        Sitter sitter = sitterDAO.get(userId);
        sitter.setFirstName(sitterProfileDTO.getFirstName());
        sitter.setLastName(sitterProfileDTO.getLastName());
        sitter.setPhoneNo(sitterProfileDTO.getPhoneNo());
        as;sdjf//start from here
    }
}
