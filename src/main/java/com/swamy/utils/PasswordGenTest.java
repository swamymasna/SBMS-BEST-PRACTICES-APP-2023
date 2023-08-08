package com.swamy.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenTest {

	public static void main(String[] args) {

		PasswordEncoder encoder = new BCryptPasswordEncoder();

		System.out.println("swamy : " + encoder.encode("swamy"));
		System.out.println("admin : " + encoder.encode("admin"));
	}
}
