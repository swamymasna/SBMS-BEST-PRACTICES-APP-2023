package com.swamy.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDto {

	private String name;
	private String email;
	private String username;
	private String password;
}
