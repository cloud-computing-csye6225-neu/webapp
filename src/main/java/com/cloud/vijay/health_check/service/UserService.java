package com.cloud.vijay.health_check.service;

import com.cloud.vijay.health_check.dao.UserDao;
import com.cloud.vijay.health_check.dto.UpdateUserDTO;
import com.cloud.vijay.health_check.dto.UserDTO;
import com.cloud.vijay.health_check.exception.BadRequestException;
import com.cloud.vijay.health_check.exception.ConflictException;
import com.cloud.vijay.health_check.exception.UnAuthorizedException;
import com.cloud.vijay.health_check.model.User;
import com.cloud.vijay.health_check.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    public UserDTO addUser(UserDTO userDTO, HttpServletRequest request) throws Exception {

        LOGGER.debug("validating the user Request while creating the User");
        if (!CommonUtil.isValidPostRequest(request) || userDTO.getId() != null) {
            LOGGER.error("Error occurred while validating the request");
            throw new BadRequestException("Error occurred while validating the request");
        }

        User user = userDao.getUserwithUserName(userDTO.getUserName());
        if (user != null) {
            LOGGER.error("User already exists");
            throw new ConflictException();
        }else {
            user = new User();
        }
        userDTO.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        userDTO.setModifiedOn(new Timestamp(System.currentTimeMillis()));
        userDTO.setPassword(CommonUtil.getHashedPassword(userDTO.getPassword()));

        BeanUtils.copyProperties(userDTO, user);
        userDao.save(user);
        BeanUtils.copyProperties(user, userDTO);

        return userDTO;
    }

    public UserDTO getUserDTO(HttpServletRequest request) throws Exception {
        UserDTO userDTO = new UserDTO();
        String authorizationHeader = request.getHeader("Authorization");
        Pair<String, String> creds = CommonUtil.getuserCredsFromToken(authorizationHeader);
        User user = userDao.getUserwithUserName(creds.getFirst());
        if (user == null || !CommonUtil.validatePassword(creds.getSecond(), user.getPassword()) || !user.getEnabled()) {
            LOGGER.error("User is not not authorized or inactive");
            throw new UnAuthorizedException();
        }
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    public void updateUser(UpdateUserDTO updateUserDTO, HttpServletRequest request) throws Exception {

        String authorizationHeader = request.getHeader("Authorization");
        LOGGER.debug("validating the user Request while updating the User");
        if (!CommonUtil.isValidPutRequest(request) || !CommonUtil.isvalidUpdateUserObject(updateUserDTO) || authorizationHeader == null) {
            LOGGER.error("Error occured while validating the request");
            throw new BadRequestException("error occured while validating the request");
        }

        Pair<String, String> creds = CommonUtil.getuserCredsFromToken(authorizationHeader);

        User user = userDao.getUserwithUserName(creds.getFirst());
        if (user == null || !CommonUtil.validatePassword(creds.getSecond(), user.getPassword()) || !user.getEnabled()) {
            LOGGER.error("Error occured while validating user");
            throw new UnAuthorizedException("Error occured while validating credentials");
        }

        if (updateUserDTO.getPassword() != null)
            updateUserDTO.setPassword(CommonUtil.getHashedPassword(updateUserDTO.getPassword()));

        CommonUtil.copyNotNullProperties(updateUserDTO, user);

        user.setModifiedOn(new Timestamp(System.currentTimeMillis()));
        userDao.updateUser(user);
    }
}
