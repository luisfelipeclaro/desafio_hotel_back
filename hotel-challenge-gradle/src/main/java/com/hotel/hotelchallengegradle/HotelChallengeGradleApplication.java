package com.hotel.hotelchallengegradle;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HotelChallengeGradleApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelChallengeGradleApplication.class, args);
	}
}
