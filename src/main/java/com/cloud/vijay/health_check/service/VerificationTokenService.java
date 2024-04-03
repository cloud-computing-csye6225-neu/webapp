package com.cloud.vijay.health_check.service;

import com.cloud.vijay.health_check.dao.UserDao;
import com.cloud.vijay.health_check.dao.VerificationDao;
import com.cloud.vijay.health_check.dto.UserDTO;
import com.cloud.vijay.health_check.model.User;
import com.cloud.vijay.health_check.model.VerificationToken;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${domain.name}")
    private String domainName;

    @Value("${server.port}")
    private int portNumber;

    private static final Logger LOGGER = LogManager.getLogger(VerificationTokenService.class);
    public void sendVerificationMail(UserDTO userDTO){
        try {
            User user = userDao.getUserwithUserName(userDTO.getUserName());
            VerificationToken token = new VerificationToken(user);
            verificationDao.save(token);
            String to = user.getUserName();
            String activationLink = "https://" + domainName  + "/validateAccount?token=" + token.getConfirmationToken();
            System.out.println(activationLink);
            pubSubService.publishMessage(to,token.getConfirmationToken(),activationLink);
            LOGGER.info("sent email successfully to the user");
        } catch (Exception e) {
            LOGGER.error("error occurred while verifying the user :"+e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public Boolean isVerified(String token){
        try {
            VerificationToken verificationToken = verificationDao.findByConfirmationToken(token);
            if (verificationToken != null) {
                Date expirationTime = verificationToken.getExpirationDate();
                Date currentTime = new Date();
                if (!currentTime.after(expirationTime)) {
                    User user = verificationToken.getUser();
                    user.setEnabled(true);
                    userDao.updateUser(user);
                    LOGGER.debug("Successfully fetched token and enabled the user");
                    return true; // Token is valid
                } else {
                    LOGGER.debug("The given token is expired");
                    return false; // Token is older than 2 minutes
                }
            } else {
                LOGGER.warn("No token/user is present relative to token : "+token);
                return false; // Token not found or null
            }
        }catch(Exception e){
            LOGGER.error("Error occurred while validating the token. Cause : " + e.getMessage());
            return false;
        }
    }
}
