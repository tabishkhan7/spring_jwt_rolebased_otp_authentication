package io.app.otpgenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.app.otpgenerator.dto.OtpGeneratorResponseDto;
import io.app.otpgenerator.dto.OtpRequestdto;
import io.app.otpgenerator.service.OtpGeneratorService;
import io.app.otpgenerator.utils.OtpUtils;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class OtpGeneratorController {
	@Autowired
	OtpGeneratorService otpGeneratorService;
	
	@Autowired
    private JavaMailSender javaMailSender;

	@PostMapping(value="/getOtp/generate")
	public ResponseEntity<OtpGeneratorResponseDto> getOtp(@RequestBody OtpRequestdto otpRequest) {
		OtpGeneratorResponseDto otpGeneratorResponse=otpGeneratorService.getOtp(otpRequest);
		OtpUtils otpUtils=new OtpUtils();
		SimpleMailMessage mailMessage=otpUtils.sendEmail(otpGeneratorResponse.getOtp(),otpRequest.getKey());
		javaMailSender.send(mailMessage);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType("application/json")).body(otpGeneratorResponse);
	}
}
