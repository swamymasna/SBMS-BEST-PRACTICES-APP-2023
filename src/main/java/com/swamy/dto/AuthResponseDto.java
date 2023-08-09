package com.swamy.dto;

import lombok.Data;

@Data
public class AuthResponseDto {

	private String accessToken;
	private String tokenType = "Bearer";
	private String role;
}
