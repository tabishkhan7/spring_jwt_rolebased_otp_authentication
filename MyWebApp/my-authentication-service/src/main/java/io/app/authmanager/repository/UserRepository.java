package io.app.authmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.app.authmanager.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
}
