package io.app.login.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
	/**
	 * The validation request status.
	 */
	private String status;
	/**
	 * The validation request message.
	 */
	private String message;

}
