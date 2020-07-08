package io.app.otpgenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.app.otpgenerator.dto.OtpValidatorResponseDto;
import io.app.otpgenerator.dto.ValidateRequestDto;
import io.app.otpgenerator.service.OtpValidatorService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class OtpValidatorController {
	@Autowired
	OtpValidatorService otpValidatorService;
	@PostMapping(value="/getOtp/validate")
	public  ResponseEntity<OtpValidatorResponseDto> validateOtp(@RequestBody ValidateRequestDto validateRequestDto) {
		OtpValidatorResponseDto otpValidatorResponse=otpValidatorService.validateOtp(validateRequestDto.getKey(),validateRequestDto.getOtp());
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType("application/json")).body(otpValidatorResponse );
	}
}
