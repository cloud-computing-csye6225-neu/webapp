package com.cloud.vijay.health_check.service;

import com.cloud.vijay.health_check.dao.UserDao;
import com.cloud.vijay.health_check.dao.VerificationDao;
import com.cloud.vijay.health_check.dto.UserDTO;
import com.cloud.vijay.health_check.model.User;
import com.cloud.vijay.health_check.model.VerificationToken;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.Date;

@Service
public class VerificationTokenService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private VerificationDao verificationDao;

    @Autowired
    private PubSubService pubSubService;

    public void sendVerification(UserDTO userDTO){
        try {
            User user = userDao.getUserwithUserName(userDTO.getUserName());
            VerificationToken token = new VerificationToken(user);
            verificationDao.save(token);
            String to = user.getUserName();
            String activationLink = "http://srivijaykalki.me:8080/validateAccount?token="+token.getConfirmationToken();

            pubSubService.publishMessage(to,activationLink);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Boolean isVerified(String token){
        VerificationToken verificationToken = verificationDao.findByConfirmationToken(token);
        if (verificationToken != null) {
            Date tokenCreationTime = verificationToken.getCreatedDate();
            Date currentTime = new Date();
            long timeDifferenceMillis = currentTime.getTime() - tokenCreationTime.getTime();
            long minutesElapsed = timeDifferenceMillis / (60 * 1000); // Convert milliseconds to minutes
            if (minutesElapsed <= 2) {
                User user = verificationToken.getUser();
                user.setEnabled(true);
                userDao.updateUser(user);
                return true; // Token is valid
            } else {
                return false; // Token is older than 2 minutes
            }
        } else {
            return false; // Token not found or null
        }
    }
}
