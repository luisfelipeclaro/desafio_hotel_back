package com.hotel.hotelchallengegradle;

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
