package io.app.authmanager.service;

import org.springframework.http.ResponseEntity;

public interface UserValidateService {

	/* (non-Javadoc)
	 * @see io.app.login.serviceimpl.LoginService#validateUser(java.lang.String, java.lang.String)
	 */
	ResponseEntity<Object> validateUser(String email, String otp);

}