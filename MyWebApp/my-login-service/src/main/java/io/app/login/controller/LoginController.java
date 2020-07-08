package io.app.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.app.login.service.LoginService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class LoginController {
	@Autowired
	LoginService loginService;

	@PostMapping(value="/user/authenticate")
	public <T> ResponseEntity<?> login(@RequestParam(value="key") String key, @RequestParam(value="otp") String otp) {
		
		ResponseEntity<Object> responseBody=loginService.validateUser(key, otp);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType("application/json")).body(responseBody.getBody());
		
	}
}
