package com.swamy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.dto.AuthResponseDto;
import com.swamy.dto.LoginDto;
import com.swamy.dto.RegisterDto;
import com.swamy.service.AuthService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<String> saveUser(@RequestBody RegisterDto registerDto) {
		return new ResponseEntity<>(authService.register(registerDto), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDto> loginUser(@RequestBody LoginDto loginDto) {

		return ResponseEntity.ok(authService.login(loginDto));
	}

	@GetMapping("/welcome")
	public ResponseEntity<String> welcome() {
		return ResponseEntity.ok(authService.welcome());
	}
}
