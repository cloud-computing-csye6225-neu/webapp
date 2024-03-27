package com.cloud.vijay.health_check.controller;

import com.cloud.vijay.health_check.dto.UpdateUserDTO;
import com.cloud.vijay.health_check.dto.UserDTO;
import com.cloud.vijay.health_check.exception.BadRequestException;
import com.cloud.vijay.health_check.service.UserService;
import com.cloud.vijay.health_check.service.VerificationTokenService;
import com.cloud.vijay.health_check.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired VerificationTokenService verificationTokenService;
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @PostMapping(path = "/v1/user")
    public ResponseEntity<UserDTO> addUser(@RequestBody @Valid UserDTO userDTO, HttpServletRequest request) throws Exception {
        userDTO = userService.addUser(userDTO, request);
        LOGGER.info("Successfully Created User :"+userDTO.getUserName());
        verificationTokenService.sendVerificationMail(userDTO);;
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping("/v1/user/self")
    public ResponseEntity<UserDTO> getSelfDetails(HttpServletRequest request) throws Exception {

        if (CommonUtil.hasRequestBody(request)) {
            LOGGER.error("A bad Request request received for the path : /V1/user/self");
            throw new BadRequestException();
        }

        UserDTO userDTO = userService.getUserDTO(request);
        LOGGER.info("Fetched User Details of the user : "+userDTO.getUserName());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping(path = "/v1/user/self")
    public ResponseEntity<Void> updateSelfDetails(@RequestBody @Valid UpdateUserDTO updateUserDTO, HttpServletRequest request) throws Exception {

        userService.updateUser(updateUserDTO, request);
        LOGGER.info("Successfully updated the user :"+updateUserDTO.getFirstName() + " " + updateUserDTO.getLastName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(path = "/v1/user", method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.TRACE})
    public ResponseEntity<Void> unSupportedMethodsForUser() {
        LOGGER.error("Requested method is not unsupported : /v1/user");
        return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @RequestMapping(path = "/v1/user/self", method = {RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.TRACE})
    public ResponseEntity<Void> unSupportedMethodsForSelf() {
        LOGGER.error("Requested method is not unsupported for the path : /v1/user/self");
        return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @RequestMapping(path = "/validateAccount", method = { RequestMethod.GET})
    public ResponseEntity<String> validateAccount(@RequestParam  String token) throws Exception {
        if(token.isEmpty()) {
            LOGGER.error("Received invalid token :" + token);
            throw new BadRequestException();
        }
        Boolean isVerified = verificationTokenService.isVerified(token);
        LOGGER.info("The user status updated to:" + isVerified);
        if (isVerified) {
            return ResponseEntity.ok("Account is verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Account verification failed");
        }
    }

    @RequestMapping(path = "/validateAccount", method = {RequestMethod.PUT,RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.TRACE})
    public ResponseEntity<Void> unSupportedMethodsForVerification() {
        LOGGER.error("Requested method is not unsupported path : /validateAccount");
        return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
