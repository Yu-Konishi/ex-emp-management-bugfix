package jp.co.sample.emp_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ExEmpManageAnswerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExEmpManageAnswerApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder passworEncoder() {
		return new BCryptPasswordEncoder();
	}
}
