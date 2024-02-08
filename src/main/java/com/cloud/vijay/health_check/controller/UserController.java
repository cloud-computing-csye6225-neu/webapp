package com.cloud.vijay.health_check.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import com.cloud.vijay.health_check.dto.UpdateUserDTO;
import com.cloud.vijay.health_check.dto.UserDTO;
import com.cloud.vijay.health_check.exception.BadRequestException;
import com.cloud.vijay.health_check.exception.UnAuthorizedException;
import com.cloud.vijay.health_check.service.HealthCheckService;
import com.cloud.vijay.health_check.service.UserService;
import com.cloud.vijay.health_check.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import javax.naming.ServiceUnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.MediaType;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
    
	@PostMapping(path = "/v1/user")
	public ResponseEntity<UserDTO> addUser(@RequestBody @Valid UserDTO userDTO, HttpServletRequest request) throws Exception {
		userDTO = userService.addUser(userDTO, request);
		
		return new ResponseEntity<>(userDTO,HttpStatus.CREATED);
	}
	
	@GetMapping("/v1/user/self")
	public ResponseEntity<UserDTO> getSelfDetails(HttpServletRequest request) throws Exception {
		
		if(CommonUtil.hasRequestBody(request))
			throw new BadRequestException();
		
		UserDTO userDTO = userService.getUserDTO(request);
		
		return new ResponseEntity<>(userDTO,HttpStatus.OK);
	}
	
	@PutMapping(path = "/v1/user/self")
	public ResponseEntity<Void> updateSelfDetails(@RequestBody @Valid UpdateUserDTO updateUserDTO, HttpServletRequest request) throws Exception {

		userService.updateUser(updateUserDTO, request);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(path = "/v1/user", method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.TRACE})
	public ResponseEntity<Void>unSupportedMethodsForUser(){
		return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@RequestMapping(path = "/v1/user/self", method = {RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.TRACE})
	public ResponseEntity<Void>unSupportedMethodsForSelf(){
		return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
	}
}