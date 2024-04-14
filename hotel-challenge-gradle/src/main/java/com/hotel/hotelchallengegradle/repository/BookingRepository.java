package com.hotel.hotelchallengegradle.repository;

import com.hotel.hotelchallengegradle.model.Booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long>{
	List<Booking> findByCheckOutDateIsNull();
	List<Booking> findByCheckOutDateIsNotNull();
}
