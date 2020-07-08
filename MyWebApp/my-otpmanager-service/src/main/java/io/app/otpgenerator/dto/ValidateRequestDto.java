package io.app.otpgenerator.dto;

import lombok.Data;

@Data
public class ValidateRequestDto {
	public String key;
	public String otp;
}
