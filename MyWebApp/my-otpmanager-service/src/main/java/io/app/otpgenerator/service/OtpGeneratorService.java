package io.app.otpgenerator.service;

import io.app.otpgenerator.dto.OtpGeneratorResponseDto;
import io.app.otpgenerator.dto.OtpRequestdto;

public interface OtpGeneratorService {

	OtpGeneratorResponseDto getOtp(OtpRequestdto otpDto);

}