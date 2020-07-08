package io.app.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.app.login.entity.LoginEntity;

public interface LoginRepository extends JpaRepository<LoginEntity, String> {

}
