package io.app.login.service;

import org.springframework.http.ResponseEntity;

public interface LoginService {

	ResponseEntity<Object> validateUser(String email, String otp);

}