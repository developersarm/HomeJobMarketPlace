package org.care.dao;

import org.care.context.MyApplicationContext;
import org.care.model.Job;
import org.care.model.JobApplication;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class JobApplicationDAO implements DAO<JobApplication> {


    @Override
    public void create(JobApplication obj) {
        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

            session.save(obj);

            transaction.commit();
        } catch (HibernateException e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't insert job application: " + e);
        }
    }

    @Override
    public void update(JobApplication obj) {
        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

            session.update(obj);

            transaction.commit();
        } catch (HibernateException e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't update job application: " + e);
        }
    }

    @Override
    public boolean delete(Serializable id) {
        boolean isDeleted = false;

        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

            JobApplication jobApp = session.get(JobApplication.class, id);
            jobApp.setStatus(JobApplication.Status.INACTIVE);

            session.update(jobApp);

            transaction.commit();
            isDeleted = true;
        } catch (HibernateException e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't delete job application: " + e);
        }

        return isDeleted;
    }

    @Override
    public JobApplication get(Serializable id) {
        JobApplication jobApplication = null;

        try {
            Session session = MyApplicationContext.getHibSession();
            jobApplication = session.get(JobApplication.class, id);

        } catch (HibernateException e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't retrieve job application: " + e);
        }

        return jobApplication;
    }

    public List<Map<String, Object>> getSitterJobsList(int userId) {
        List<Map<String, Object>> resultList = new LinkedList<>();

        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

//            Criteria criteria = session.createCriteria(JobApplication.class);
//            Criterion criterion = Restrictions.eq("sitter.id", userId);
//            criteria.add(criterion);
//            List<JobApplication> jobApplicationList = criteria.list();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<JobApplication> criteriaQuery = builder.createQuery(JobApplication.class);
            Root<JobApplication> root = criteriaQuery.from(JobApplication.class);
            criteriaQuery.select(root).where(builder.equal(root.get("sitter.id"), userId));
            Query<JobApplication> query = session.createQuery(criteriaQuery);
            List<JobApplication> jobApplicationList = query.getResultList();

            for (JobApplication tempJbApp :
                    jobApplicationList) {
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("title", tempJbApp.getJob().getTitle());
                tempMap.put("startDate", tempJbApp.getJob().getStartDate());
                tempMap.put("expectedPay", tempJbApp.getExpectedPay());
                resultList.add(tempMap);
            }

            transaction.commit();
        } catch (HibernateException e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't get job application specific " +
                    "to particular sitter: " + e);
        }

        return resultList;
    }

    public List<Job> getSitterNAJobsList(int userId) {
        List<Job> jobs = new LinkedList<>();
        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

            String hql = "from Job as job " +
                    "where job.id not in ( " +
                    "select jbApp.jobId from JobApplication as jbApp where jbApp.sitter.id = :userId " +
                    ") and job.status = :status ";

            Query<Job> query = session.createQuery(hql, Job.class)
                    .setParameter("userId", userId)
                    .setParameter("status", Job.Status.ACTIVE);
            jobs = query.list();

            transaction.commit();
        } catch (HibernateException e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't get jobs not applied by " +
                    "particular sitter: " + e);

        }
        return jobs;
    }

    public List<Map<String, Object>> getAllByUserId(int userId) {
        List<Map<String, Object>> resultList = new LinkedList<>();

        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

//            Criteria criteria = session.createCriteria(JobApplication.class);
//            Criterion criterion = Restrictions.eq("sitter.id", userId);
//            criteria.add(criterion);
//            List<JobApplication> jobApplicationList = criteria.list();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<JobApplication> criteriaQuery = builder.createQuery(JobApplication.class);
            Root<JobApplication> root = criteriaQuery.from(JobApplication.class);
            criteriaQuery.select(root).where(builder.equal(root.get("sitter.id"), userId));
            Query<JobApplication> query = session.createQuery(criteriaQuery);
            List<JobApplication> jobApplicationList = query.getResultList();

            for (JobApplication tempJbApp :
                    jobApplicationList) {
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("id", tempJbApp.getId());
                tempMap.put("title", tempJbApp.getJob().getTitle());
                tempMap.put("expectedPay", tempJbApp.getExpectedPay());
                tempMap.put("payPerHour", tempJbApp.getJob().getPayPerHour());
                tempMap.put("status", tempJbApp.getStatus());
                resultList.add(tempMap);
            }

            transaction.commit();
        } catch (HibernateException e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't get job application specific " +
                    "to particular sitter: " + e);
        }

        return resultList;
    }

    public boolean update(int jobAppId, double expectedPay) {
        boolean isUpdated = false;

        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

            JobApplication jobApplication = session.get(JobApplication.class, jobAppId);
            jobApplication.setExpectedPay(expectedPay);
            session.update(jobApplication);

            transaction.commit();
            isUpdated = true;
        } catch (HibernateException e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't update job application: " + e);
        }

        return isUpdated;
    }

    public int deleteByJobId(int jobId) {
        int totalDeletedApps = -1;

        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

            String hql = "update JobApplication jbApp set jbApp.status = :status where jbApp.job.id = :jobId ";
            totalDeletedApps = session.createQuery(hql)
                    .setParameter("status", JobApplication.Status.INACTIVE)
                    .setParameter("jobId", jobId)
                    .executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't delete job application: " + e);
        }

        return totalDeletedApps;
    }

    public List<Map<String, Object>> getAllByJobId(int jobId) {
        List<Map<String, Object>> resultList = new LinkedList<>();

        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

//            Criteria criteria = session.createCriteria(JobApplication.class);
//            Criterion criterion = Restrictions.eq("job.id", jobId);
//            criteria.add(criterion);
//            List<JobApplication> jobApplicationList = criteria.list();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<JobApplication> criteriaQuery = builder.createQuery(JobApplication.class);
            Root<JobApplication> root = criteriaQuery.from(JobApplication.class);
            criteriaQuery.select(root).where(builder.equal(root.get("job.id"), jobId));
            Query<JobApplication> query = session.createQuery(criteriaQuery);
            List<JobApplication> jobApplicationList = query.getResultList();

            for (JobApplication tempJbApp :
                    jobApplicationList) {
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("id", tempJbApp.getId());
                tempMap.put("firstName", tempJbApp.getSitter().getFirstName());
                tempMap.put("status", tempJbApp.getStatus());
                tempMap.put("expectedPay", tempJbApp.getExpectedPay());
                resultList.add(tempMap);
            }

            transaction.commit();
        } catch (HibernateException e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't get job application specific " +
                    "to particular job: " + e);
        }

        return resultList;
    }

    public void deleteByUserId(int userId) {
        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

            String hql = "update JobApplication jbApp set jbApp.status = :status where jbApp.sitter.id = :userId ";
            session.createQuery(hql)
                    .setParameter("status", JobApplication.Status.INACTIVE)
                    .setParameter("userId", userId)
                    .executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {
            Logger.getLogger(JobApplicationDAO.class.getName()).severe("Can't delete job application: " + e);
        }
    }
}
