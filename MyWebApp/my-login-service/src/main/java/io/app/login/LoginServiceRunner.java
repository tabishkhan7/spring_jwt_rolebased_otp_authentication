package io.app.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@EnableJpaRepositories(basePackages="io.app.login.*")
public class LoginServiceRunner {
	public static void main(String[] args) {
		SpringApplication.run(LoginServiceRunner.class, args);
	}
}
