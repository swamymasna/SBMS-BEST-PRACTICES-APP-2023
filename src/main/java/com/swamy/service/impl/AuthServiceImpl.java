package com.swamy.service.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.swamy.dto.AuthResponseDto;
import com.swamy.dto.LoginDto;
import com.swamy.dto.RegisterDto;
import com.swamy.entity.Role;
import com.swamy.entity.User;
import com.swamy.exception.ApiException;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.props.AppProperties;
import com.swamy.repository.RoleRepository;
import com.swamy.repository.UserRepository;
import com.swamy.security.JwtTokenProvider;
import com.swamy.service.AuthService;
import static com.swamy.utils.AppConstants.*;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

	private UserRepository userRepository;

	private RoleRepository roleRepository;

	private PasswordEncoder passwordEncoder;

	private AuthenticationManager authenticationManager;

	private AppProperties appProperties;

	private JwtTokenProvider jwtTokenProvider;

	@Override
	public String register(RegisterDto registerDto) {

		if (Boolean.TRUE.equals(userRepository.existsByUsername(registerDto.getUsername()))) {
			throw new ApiException(appProperties.getMessages().get(USER_EXISTS_BY_USERNAME));
		}

		if (Boolean.TRUE.equals(userRepository.existsByEmail(registerDto.getEmail()))) {
			throw new ApiException(appProperties.getMessages().get(USER_EXISTS_BY_EMAIL));
		}

		Role role = roleRepository.findByName(USER_ROLE).orElseThrow(
				() -> new ResourceNotFoundException(appProperties.getMessages().get(ROLE_NOT_FOUND_BY_NAME)));

		User user = User.builder().name(registerDto.getName()).email(registerDto.getEmail())
				.username(registerDto.getUsername()).password(passwordEncoder.encode(registerDto.getPassword()))
				.roles(Set.of(role)).build();

		userRepository.save(user);

		return appProperties.getMessages().get(USER_REGISTRATION_SUCCESS);
	}

	@Override
	public AuthResponseDto login(LoginDto loginDto) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		Optional<User> optUser = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(),
				loginDto.getUsernameOrEmail());

		String roleName = null;
		if (optUser.isPresent()) {
			User loggedInUser = optUser.get();
			Optional<Role> optRole = loggedInUser.getRoles().stream().findFirst();
			if (optRole.isPresent()) {
				roleName = optRole.get().getName();
			}
		}

		AuthResponseDto authResponseDto = new AuthResponseDto();
		authResponseDto.setAccessToken(token);
		authResponseDto.setRole(roleName);

		return authResponseDto;
	}

	@Override
	public String welcome() {
		return appProperties.getMessages().get(WELCOME_MSG);
	}

}
