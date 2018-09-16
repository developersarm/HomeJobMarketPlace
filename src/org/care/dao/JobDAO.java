package org.care.dao;

import org.care.context.MyApplicationContext;
import org.care.model.Job;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class JobDAO implements DAO<Job> {

    @Override
    public void create(Job obj) {
        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

            session.save(obj);

            transaction.commit();

        } catch (HibernateException e) {
            Logger.getLogger(JobDAO.class.getName()).severe("Can't insert job: " + e);
        }
    }

    @Override
    public void update(Job obj) {
        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

            session.update(obj);

            transaction.commit();
        } catch (HibernateException e) {
            Logger.getLogger(JobDAO.class.getName()).severe("Can't update job: " + e);
        }
    }

    @Override
    public boolean delete(Serializable id) {
        boolean isDeleted = false;

        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

            Job job = session.get(Job.class, id);
            job.setStatus(Job.Status.INACTIVE);
            session.update(job);

            transaction.commit();
            isDeleted = true;

        } catch (HibernateException e) {
            Logger.getLogger(JobDAO.class.getName()).severe("Can't delete job: " + e);
        }
        return isDeleted;
    }

    @Override
    public Job get(Serializable id) {
        Job job = null;
        try {
            Session session = MyApplicationContext.getHibSession();
            job = session.get(Job.class, id);

        } catch (HibernateException e) {
            Logger.getLogger(JobDAO.class.getName()).severe("Can't retrieve job: " + e);
        }

        return job;
    }

    public List<Job> getJobsPostedBy(int userId) {
        List<Job> jobs = new LinkedList<>();

        try {
            Session session = MyApplicationContext.getHibSession();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Job> criteria = builder.createQuery(Job.class);
            Root<Job> root = criteria.from(Job.class);
            criteria.select(root).where(builder.equal(root.get("seeker.id"), userId));

            Query<Job> query = session.createQuery(criteria);
            jobs = query.getResultList();

        } catch (HibernateException e) {
            Logger.getLogger(JobDAO.class.getName()).severe("Can't retrieve job specific to seeker: " + e);

        }

        return jobs;
    }
}
