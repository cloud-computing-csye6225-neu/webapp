package com.cloud.vijay.health_check.dao;

import com.cloud.vijay.health_check.model.User;
import com.cloud.vijay.health_check.model.VerificationToken;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class VerificationDao {
    @Autowired
    private EntityManager entitymanager;

    public VerificationToken save(VerificationToken verificationToken) {
        Session currentSession = entitymanager.unwrap(Session.class);
        currentSession.persist(verificationToken);
        return verificationToken;
    }

    public VerificationToken findByConfirmationToken(String confirmationToken) {
        Session currentSession = entitymanager.unwrap(Session.class);
        return currentSession.createQuery("FROM VerificationToken WHERE confirmationToken = :confirmationToken", VerificationToken.class)
                .setParameter("confirmationToken", confirmationToken)
                .uniqueResult();
    }

}
