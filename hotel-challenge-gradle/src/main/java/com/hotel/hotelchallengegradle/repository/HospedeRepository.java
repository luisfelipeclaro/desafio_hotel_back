package com.hotel.hotelchallengegradle.repository;

import com.hotel.hotelchallengegradle.model.Hospedes;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


@Repository
public interface HospedeRepository extends JpaRepository<Hospedes, Long> {

	Page<Hospedes> findByNomeContainingIgnoreCaseOrDocumentoContainingIgnoreCaseOrTelefoneContainingIgnoreCase(String nome, String documento, String telefone, Pageable pageable);
	
	Hospedes findOneByNomeIgnoreCaseAndDocumentoAndTelefone(String nome, String documento, String telefone);
	
	Hospedes findOneByNomeContainingIgnoreCaseOrDocumentoContainingIgnoreCaseOrTelefoneContainingIgnoreCase(String nome, String documento, String telefone);
}
