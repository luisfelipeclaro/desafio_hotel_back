package com.hotel.hotelchallengegradle.repository;

import com.hotel.hotelchallengegradle.model.Reservas;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

public interface ReservaRepository extends JpaRepository<Reservas, Long>{
	Page<Reservas> findByDataSaidaIsNull(Pageable pageable);
	Page<Reservas> findByDataSaidaIsNotNull(Pageable pageable);
}
