package io.app.otpgenerator.service;

import io.app.otpgenerator.dto.OtpValidatorResponseDto;

public interface OtpValidatorService {

	OtpValidatorResponseDto validateOtp(String key, String otp);

}