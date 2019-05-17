package com.va;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.va.entity.UserInfo;
import com.va.service.impl.AuditorAwareImpl;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class BlogSimpleApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogSimpleApplication.class, args);
	}

	@Bean
	public AuditorAware<UserInfo> auditorAware() {
		return new AuditorAwareImpl();
	}
}
