package io.app.otpgenerator.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class OtpRequestdto {
	@NotBlank(message = "Key can't be empty or null")
	@Size(min = 3, max = 64, message = "length should be in the range of 3-64")
	public String key;
	
	
}
