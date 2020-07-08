package io.app.otpgenerator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.app.otpgenerator.entity.OtpEntity;


public interface OtpRepository extends JpaRepository<OtpEntity, String> {
}
