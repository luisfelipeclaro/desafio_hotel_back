package com.hotel.hotelchallengegradle.repository;

import com.hotel.hotelchallengegradle.model.Guest;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;


@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

	List<Guest> findByFullNameContainingIgnoreCaseOrSocialIdentificationContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(String fullName, String socialIdentification, String phoneNumber);
	
	Guest findOneByFullNameIgnoreCaseAndSocialIdentificationAndPhoneNumber(String fullName, String socialIdentification, String phoneNumber);
	
	Guest findOneByFullNameContainingIgnoreCaseOrSocialIdentificationContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(String fullName, String socialIdentification, String phoneNumber);
}
