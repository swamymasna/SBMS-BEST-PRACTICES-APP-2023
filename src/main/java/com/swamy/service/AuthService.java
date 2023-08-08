package com.swamy.service;

import com.swamy.dto.AuthResponseDto;
import com.swamy.dto.LoginDto;
import com.swamy.dto.RegisterDto;

public interface AuthService {

	public String register(RegisterDto registerDto);

	public AuthResponseDto login(LoginDto loginDto);
	
	public String welcome();
}
