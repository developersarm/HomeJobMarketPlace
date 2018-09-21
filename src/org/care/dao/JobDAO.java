package org.care.dao;

import org.care.context.MyApplicationContext;
import org.care.model.Job;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

public class JobDAO implements DAO<Job> {

    @Override
    public Integer create(Job obj) throws DAOException {
        Integer id;
        try {
            Session session = MyApplicationContext.getHibSession();
            id = (Integer) session.save(obj);

        } catch (Exception e) {
            Logger.getLogger(JobDAO.class.getName()).severe("Can't insert job: " + e);
            throw new DAOException();
        }
        return id;
    }

    @Override
    public void update(Job obj) throws DAOException {
        try {
            Session session = MyApplicationContext.getHibSession();
            session.update(obj);
        } catch (Exception e) {
            Logger.getLogger(JobDAO.class.getName()).severe("Can't update job: " + e);
            throw new DAOException();
        }
    }

    @Override
    public void delete(Serializable id) throws DAOException {
        try {
            Session session = MyApplicationContext.getHibSession();
            Job job = session.get(Job.class, id);
            job.setStatus(Job.Status.INACTIVE);
            session.update(job);

        } catch (Exception e) {
            Logger.getLogger(JobDAO.class.getName()).severe("Can't delete job: " + e);
            throw new DAOException();
        }
    }

    @Override
    public Job get(Serializable id) throws DAOException {
        Job job;
        try {
            Session session = MyApplicationContext.getHibSession();
            job = session.get(Job.class, id);

        } catch (Exception e) {
            Logger.getLogger(JobDAO.class.getName()).severe("Can't retrieve job: " + e);
            throw new DAOException();
        }

        return job;
    }

    public List<Job> getJobsPostedBy(int userId) throws DAOException {
        List<Job> jobs;

        try {
            Session session = MyApplicationContext.getHibSession();

            Criteria criteria = session.createCriteria(Job.class);
            Criterion criterion = Restrictions.eq("seeker.memberId", userId);
            criteria.add(criterion);
            jobs = criteria.list();

//            CriteriaBuilder builder = session.getCriteriaBuilder();
//            CriteriaQuery<Job> criteria = builder.createQuery(Job.class);
//            Root<Job> root = criteria.from(Job.class);
//            criteria.select(root).where(builder.equal(root.get("seeker.memberId"), userId));
//            Query<Job> query = session.createQuery(criteria);
//            jobs = query.getResultList();

        } catch (Exception e) {
            Logger.getLogger(JobDAO.class.getName()).severe("Can't retrieve job specific to seeker: " + e);
            throw new DAOException();
        }

        return jobs;
    }
}
