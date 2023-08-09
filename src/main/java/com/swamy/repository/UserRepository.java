package com.swamy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swamy.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);

	Optional<User> findByUsernameOrEmail(String username, String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
