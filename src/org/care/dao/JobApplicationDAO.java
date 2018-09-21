package org.care.dao;

import org.care.context.MyApplicationContext;
import org.care.model.Job;
import org.care.model.JobApplication;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class JobApplicationDAO implements DAO<JobApplication> {


    @Override
    public Integer create(JobApplication obj) throws DAOException {
        Integer id;
        try {
            Session session = MyApplicationContext.getHibSession();

            id = (Integer) session.save(obj);

        } catch (Exception e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't insert job application: " + e);
            throw new DAOException();
        }
        return id;
    }

    @Override
    public void update(JobApplication obj) throws DAOException {
        try {
            Session session = MyApplicationContext.getHibSession();
            session.update(obj);
        } catch (Exception e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't update job application: " + e);
            throw new DAOException();
        }
    }

    @Override
    public void delete(Serializable id) throws DAOException {
        try {
            Session session = MyApplicationContext.getHibSession();

            JobApplication jobApp = session.get(JobApplication.class, id);
            jobApp.setStatus(JobApplication.Status.INACTIVE);

            session.update(jobApp);
        } catch (Exception e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't delete job application: " + e);
            throw new DAOException();
        }
    }

    @Override
    public JobApplication get(Serializable id) throws DAOException {
        JobApplication jobApplication;

        try {
            Session session = MyApplicationContext.getHibSession();
            jobApplication = session.get(JobApplication.class, id);

        } catch (Exception e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't retrieve job application: " + e);
            throw new DAOException();
        }

        return jobApplication;
    }

    public List<Map<String, Object>> getSitterJobsList(int userId) throws DAOException {
        List<Map<String, Object>> resultList = new LinkedList<>();

        try {
            Session session = MyApplicationContext.getHibSession();

            Criteria criteria = session.createCriteria(JobApplication.class);
            Criterion criterion = Restrictions.eq("sitter.memberId", userId);
            criteria.add(criterion);
            List<JobApplication> jobApplicationList = criteria.list();

//            CriteriaBuilder builder = session.getCriteriaBuilder();
//            CriteriaQuery<JobApplication> criteriaQuery = builder.createQuery(JobApplication.class);
//            Root<JobApplication> root = criteriaQuery.from(JobApplication.class);
//            criteriaQuery.select(root).where(builder.equal(root.get("sitter.memberId"), userId));
//            Query<JobApplication> query = session.createQuery(criteriaQuery);
//            List<JobApplication> jobApplicationList = query.getResultList();

            for (JobApplication tempJbApp :
                    jobApplicationList) {
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("title", tempJbApp.getJob().getTitle());
                tempMap.put("startDate", tempJbApp.getJob().getStartDate());
                tempMap.put("expectedPay", tempJbApp.getExpectedPay());
                resultList.add(tempMap);
            }

        } catch (Exception e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't get job application specific " +
                    "to particular sitter: " + e);
            throw new DAOException();
        }

        return resultList;
    }

    public List<Job> getSitterNAJobsList(int userId) throws DAOException {
        List<Job> jobs;
        try {
            Session session = MyApplicationContext.getHibSession();

            String hql = "from Job as job " +
                    "where job.jobId not in ( " +
                    "select jbApp.job.jobId from JobApplication as jbApp where jbApp.sitter.memberId = :userId " +
                    ") and job.status = :status ";

            Query<Job> query = session.createQuery(hql, Job.class)
                    .setParameter("userId", userId)
                    .setParameter("status", Job.Status.ACTIVE);
            jobs = query.list();

        } catch (Exception e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't get jobs not applied by " +
                    "particular sitter: " + e);
            throw new DAOException();
        }
        return jobs;
    }

    public List<Map<String, Object>> getAllByUserId(int userId) throws DAOException {
        List<Map<String, Object>> resultList = new LinkedList<>();

        try {
            Session session = MyApplicationContext.getHibSession();

            Criteria criteria = session.createCriteria(JobApplication.class);
            Criterion criterion = Restrictions.eq("sitter.memberId", userId);
            criteria.add(criterion);
            List<JobApplication> jobApplicationList = criteria.list();

//            CriteriaBuilder builder = session.getCriteriaBuilder();
//            CriteriaQuery<JobApplication> criteriaQuery = builder.createQuery(JobApplication.class);
//            Root<JobApplication> root = criteriaQuery.from(JobApplication.class);
//            criteriaQuery.select(root).where(builder.equal(root.get("sitter.memberId"), userId));
//            Query<JobApplication> query = session.createQuery(criteriaQuery);
//            List<JobApplication> jobApplicationList = query.getResultList();

            for (JobApplication tempJbApp :
                    jobApplicationList) {
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("id", tempJbApp.getJobAppId());
                tempMap.put("title", tempJbApp.getJob().getTitle());
                tempMap.put("expectedPay", tempJbApp.getExpectedPay());
                tempMap.put("payPerHour", tempJbApp.getJob().getPayPerHour());
                tempMap.put("status", tempJbApp.getStatus());
                resultList.add(tempMap);
            }

        } catch (Exception e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).info("Can't get job application specific " +
                    "to particular sitter: " + e);
            throw new DAOException();
        }

        return resultList;
    }

    public void update(int jobAppId, double expectedPay) throws DAOException {
        try {
            Session session = MyApplicationContext.getHibSession();

            JobApplication jobApplication = session.get(JobApplication.class, jobAppId);
            jobApplication.setExpectedPay(expectedPay);
            session.update(jobApplication);

        } catch (Exception e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't update job application: " + e);
            throw new DAOException();
        }
    }

    public int deleteByJobId(int jobId) throws DAOException {
        int totalDeletedApps;

        try {
            Session session = MyApplicationContext.getHibSession();

            String hql = "update JobApplication jbApp set jbApp.status = :status where jbApp.job.jobId = :jobId ";
            totalDeletedApps = session.createQuery(hql)
                    .setParameter("status", JobApplication.Status.INACTIVE)
                    .setParameter("jobId", jobId)
                    .executeUpdate();

        } catch (Exception e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't delete job application: " + e);
            throw new DAOException();
        }

        return totalDeletedApps;
    }

    public List<Map<String, Object>> getAllByJobId(int jobId) throws DAOException {
        List<Map<String, Object>> resultList = new LinkedList<>();

        try {
            Session session = MyApplicationContext.getHibSession();

            Criteria criteria = session.createCriteria(JobApplication.class);
            Criterion criterion = Restrictions.eq("job.jobId", jobId);
            criteria.add(criterion);
            List<JobApplication> jobApplicationList = criteria.list();

//            CriteriaBuilder builder = session.getCriteriaBuilder();
//            CriteriaQuery<JobApplication> criteriaQuery = builder.createQuery(JobApplication.class);
//            Root<JobApplication> root = criteriaQuery.from(JobApplication.class);
//            criteriaQuery.select(root).where(builder.equal(root.get("job.jobId"), jobId));
//            Query<JobApplication> query = session.createQuery(criteriaQuery);
//            List<JobApplication> jobApplicationList = query.getResultList();

            for (JobApplication tempJbApp :
                    jobApplicationList) {
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("id", tempJbApp.getJobAppId());
                tempMap.put("firstName", tempJbApp.getSitter().getFirstName());
                tempMap.put("status", tempJbApp.getStatus());
                tempMap.put("expectedPay", tempJbApp.getExpectedPay());
                resultList.add(tempMap);
            }

        } catch (Exception e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't get job application specific " +
                    "to particular job: " + e);
            throw new DAOException();
        }

        return resultList;
    }

    public void deleteByUserId(int userId) throws DAOException {
        try {
            Session session = MyApplicationContext.getHibSession();

            String hql = "update JobApplication jbApp set jbApp.status = :status where jbApp.sitter.memberId = :userId ";
            session.createQuery(hql)
                    .setParameter("status", JobApplication.Status.INACTIVE)
                    .setParameter("userId", userId)
                    .executeUpdate();

        } catch (Exception e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't delete job application: " + e);
            throw new DAOException();
        }
    }
}
