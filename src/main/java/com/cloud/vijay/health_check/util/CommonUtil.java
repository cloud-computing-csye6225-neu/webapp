package com.cloud.vijay.health_check.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.util.Pair;
import java.beans.PropertyDescriptor;

import com.cloud.vijay.health_check.dto.UpdateUserDTO;
import com.cloud.vijay.health_check.exception.UnAuthorizedException;

import jakarta.servlet.http.HttpServletRequest;

public class CommonUtil {
	
	public static String getHashedPassword(String password) {
		return BCrypt.hashpw(password,BCrypt.gensalt());
	}
	
	public static Boolean hasRequestBody(HttpServletRequest request) {
		if ((request.getHeader("Content-Length")!=null && request.getHeader("Content-Length").equals("0") ) || request.getContentType()!=null || request.getParameterMap().size()>0) {
			return true;
		}
		return false;
	}
	
	public static Pair<String, String> getuserCredsFromToken(String token) throws UnAuthorizedException{
		if(token==null || !token.startsWith("Basic "))
			throw new UnAuthorizedException();
		String base64Credentials = token.substring("Basic ".length()).trim();
	    String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
	    String[] parts = credentials.split(":", 2);
	    if(parts[0].equals("") || parts[1].equals(""))
	    	throw new UnAuthorizedException();
	    return Pair.of(parts[0], parts[1]);
	}
	
	public static Boolean validatePassword(String unHashedPass, String hashedPass) {
		return BCrypt.checkpw(unHashedPass, hashedPass);
	}
	
	public static void copyNotNullProperties(Object source, Object target) {
		BeanWrapper srcWrapper = new BeanWrapperImpl(source);
        BeanWrapper targetWrapper = new BeanWrapperImpl(target);


        for (PropertyDescriptor descriptor : srcWrapper.getPropertyDescriptors()) {
            String propertyName = descriptor.getName();

            if (descriptor.getWriteMethod() != null && srcWrapper.getPropertyValue(propertyName) != null) {
                targetWrapper.setPropertyValue(propertyName, srcWrapper.getPropertyValue(propertyName));
            }
        }
	}
	
	public static Boolean isValidPostRequest(HttpServletRequest request) {
		if(request.getParameterMap().size()==0 && request.getHeader("Authorization")==null)
			return true;
		return false;
	}
	
	public static Boolean isValidPutRequest(HttpServletRequest request) {
		if(request.getParameterMap().size()!=0)
			return false;
		return true;
	}
	
	public static Boolean isvalidUpdateUserObject(UpdateUserDTO updateUserDTO) {
		if((updateUserDTO.getFirstName()!=null && updateUserDTO.getFirstName().trim().isEmpty()) ||
				(updateUserDTO.getLastName()!=null && updateUserDTO.getLastName().trim().isEmpty()) ||
				(updateUserDTO.getPassword()!=null && updateUserDTO.getPassword().trim().isEmpty()) ||
				(updateUserDTO.getFirstName()== null && updateUserDTO.getLastName()==null && updateUserDTO.getPassword()==null)
				) {
			System.out.println("false");
			return false;
		}
		System.out.println("true");
		return true;
	}
}
