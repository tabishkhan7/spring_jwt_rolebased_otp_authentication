package io.app.otpgenerator.dto;

import lombok.Data;


@Data
public class OtpValidatorResponseDto {
	/**
	 * The validation request status.
	 */
	private String status;
	/**
	 * The validation request message.
	 */
	private String message;
}
