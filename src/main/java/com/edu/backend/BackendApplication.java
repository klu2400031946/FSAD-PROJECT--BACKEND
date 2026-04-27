package com.edu.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.edu.backend.model.User;
import com.edu.backend.repository.UserRepository;

@SpringBootApplication
public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			if (userRepository.findByEmail("admin@eduportal.com").isEmpty()) {
				User admin = new User();
				admin.setName("System Admin");
				admin.setEmail("admin@eduportal.com");
				admin.setPassword("admin123");
				admin.setRole("admin");
				admin.setStatus("active");
				userRepository.save(admin);
				System.out.println("Default admin user created");
			}
		};
	}
}
