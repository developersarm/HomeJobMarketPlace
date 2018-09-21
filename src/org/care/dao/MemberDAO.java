package org.care.dao;

import org.care.context.MyApplicationContext;
import org.care.model.Member;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MemberDAO<T extends Member> implements DAO<T> {

    @Override
    public Integer create(T obj) {
        Integer id = null;
        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

            id = (Integer) session.save(obj);

            transaction.commit();
        } catch (Exception e) {
            Logger.getLogger(MemberDAO.class.getName()).severe("Can't insert member: " + e);
        }
        return id;
    }

    @Override
    public void update(T obj) {
        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

            session.update(obj);

            transaction.commit();
        } catch (Exception e) {
            Logger.getLogger(MemberDAO.class.getName()).severe("Can't update member: " + e);
        }
    }

    @Override
    public boolean delete(Serializable id) {
        boolean isDeleted = false;

        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

            Member member = session.get(Member.class, id);
            member.setStatus(Member.Status.INACTIVE);
            session.update(member);

            transaction.commit();
            isDeleted = true;

        } catch (Exception e) {
            Logger.getLogger(MemberDAO.class.getName()).severe("Can't delete member: " + e);
        }
        return isDeleted;
    }

    @Override
    public T get(Serializable id) {
        Member member = null;
        try {
            Session session = MyApplicationContext.getHibSession();
            member = session.get(Member.class, id);

        } catch (Exception e) {
            Logger.getLogger(MemberDAO.class.getName()).severe("Can't retrieve member: " + e);
        }

        return (T) member;
    }

    public Map<String, Object> get(String email, String password) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            Session session = MyApplicationContext.getHibSession();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Member> criteria = builder.createQuery(Member.class);
            Root<Member> root = criteria.from(Member.class);

            Predicate emailPredicate = builder.equal(root.get("emailId"), email);
            Predicate passPredicate = builder.equal(root.get("password"), password);
            criteria.select(root).where(emailPredicate, passPredicate);

            Query<Member> query = session.createQuery(criteria);
            Member member = query.getSingleResult();

            resultMap.put("UserId", member.getMemberId());
            resultMap.put("Status", member.getStatus());
            resultMap.put("MemberType", member.getType());

        } catch (Exception e) {
            Logger.getLogger(MemberDAO.class.getName()).info("Can't retrieve login info: " + e);
            resultMap.put("UserId", -1);
        }

        return resultMap;
    }

    public int getByEmailId(String emailId) {
        int userId = -1;

        try {
            Session session = MyApplicationContext.getHibSession();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Member> criteria = builder.createQuery(Member.class);
            Root<Member> root = criteria.from(Member.class);

            criteria.select(root).where(builder.equal(root.get("emailId"), emailId));

            Query<Member> query = session.createQuery(criteria);
            Member member = query.getSingleResult();
            userId = member.getMemberId();

        } catch (Exception e) {
            Logger.getLogger(MemberDAO.class.getName()).info("Can't retrieve login info: " + e);
        }

        return userId;
    }
}
