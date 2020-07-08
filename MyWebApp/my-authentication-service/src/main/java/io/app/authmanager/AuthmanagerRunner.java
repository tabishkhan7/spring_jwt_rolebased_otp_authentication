package io.app.authmanager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages="io.app.authmanager.*")
public class AuthmanagerRunner {
	public static void main(String[] args) {
		SpringApplication.run(AuthmanagerRunner.class, args);
	}
}
