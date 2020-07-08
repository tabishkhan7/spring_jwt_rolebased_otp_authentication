package io.app.otpgenerator.dto;

import lombok.Data;

@Data
public class OtpGeneratorResponseDto {
	/**
	 * The generated OTP.
	 */
	private String otp;
	/**
	 * The status of the corresponding generation request.
	 */
	private String status;
}
