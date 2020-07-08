package io.app.authmanager.dto;

import java.util.List;

import io.app.authmanager.entity.Role;
import lombok.Data;

@Data
public class AuthManagerRequestDto {
	private String username;
	private String Otp;
	private List<String> role;
}
