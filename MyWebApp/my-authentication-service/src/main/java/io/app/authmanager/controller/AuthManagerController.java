package io.app.authmanager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.app.authmanager.dto.AuthManagerRequestDto;
import io.app.authmanager.dto.AuthManagerResponseDto;
import io.app.authmanager.entity.Role;
import io.app.authmanager.entity.User;
import io.app.authmanager.serviceImpl.UserDetailServiceImpl;
import io.app.authmanager.serviceImpl.UserValidateServiceImpl;
import io.app.authmanager.utils.JwtUtil;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthManagerController {
	@Autowired
	UserDetailServiceImpl userDetailServiceImpl;
	
	@Autowired
	UserValidateServiceImpl userValidateSeviceImpl;
	
	@Autowired
	JwtUtil jwtUtil;
	
	
	@RequestMapping(value="/validateUser")
	public ResponseEntity<?> home(@RequestBody AuthManagerRequestDto authManagerRequestDto) {
		AuthManagerResponseDto managerResponseDto=new AuthManagerResponseDto();
		HashMap<String, String> responseBody =(HashMap<String, String>)userValidateSeviceImpl.validateUser(authManagerRequestDto.getUsername(), authManagerRequestDto.getOtp()).getBody();
		if(responseBody.get("status").equals("FAILED")) {
			managerResponseDto.setJwt("User Does Not Exist");
		} else if(responseBody.get("status").equals("EXPIRED"))
			managerResponseDto.setJwt("User's Otp Validation Failed");
		else  {
			userDetailServiceImpl.saveDetails(authManagerRequestDto);
		UserDetails userDetails=userDetailServiceImpl.loadUserByUsername(authManagerRequestDto.getUsername());
		final String jwt=jwtUtil.createToken(userDetails);
	
		managerResponseDto.setJwt(jwt);
		}
		return ResponseEntity.ok(managerResponseDto);
		
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/admin/all")
	public String securedHello() {
		return "Secured Hello";
	}
}
