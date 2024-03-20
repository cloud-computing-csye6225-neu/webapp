package com.cloud.vijay.health_check.dao;

import com.cloud.vijay.health_check.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserDao {

    @Autowired
    private EntityManager entitymanager;
    private static final Logger LOGGER = LogManager.getLogger(UserDao.class);

    public User save(User user) {
        Session currentSession = entitymanager.unwrap(Session.class);
        currentSession.persist(user);
        return user;
    }

    public User getUserwithUserName(String userName) throws Exception {
        User user = null;

        try {
            user = entitymanager.createQuery("SELECT u FROM User u where u.userName = :userName", User.class).setParameter("userName", userName).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.warn("The error with the user name doesn't exit");
            return null;
        }
        return user;
    }

    public User getUser(String userName, String password) throws Exception {
        User user = null;

        try {
            TypedQuery<User> query = entitymanager.createQuery("SELECT u FROM User u where u.userName = :userName AND u.password = :password", User.class);
            query.setParameter("userName", userName);
            query.setParameter("password", password);
            System.out.println("userName" + userName);
            System.out.println("password" + password);
            System.out.println("Executing query: " + query.unwrap(Query.class).getQueryString());
            user = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return user;
    }

    public User updateUser(User user) {
        Session currentSession = entitymanager.unwrap(Session.class);
        currentSession.merge(user);
        return user;
    }
}
