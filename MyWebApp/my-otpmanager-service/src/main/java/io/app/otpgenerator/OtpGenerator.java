package io.app.otpgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@EnableJpaRepositories(basePackages="io.app.otpgenerator.*")
public class OtpGenerator {
	public static void main(String[] args) {
		SpringApplication.run(OtpGenerator.class, args);
	}
}
