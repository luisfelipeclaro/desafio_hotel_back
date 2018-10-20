package com.hotel.hotelchallengegradle.controllers;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.hotel.hotelchallengegradle.exception.ResourceNotFoundException;
import com.hotel.hotelchallengegradle.model.Hospedes;
import com.hotel.hotelchallengegradle.model.Reservas;
import com.hotel.hotelchallengegradle.repository.HospedeRepository;
import com.hotel.hotelchallengegradle.repository.ReservaRepository;

import ch.qos.logback.core.net.SyslogOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

@RestController
public class ReservasController {

	@Autowired
	private HospedeRepository hospedesRepository;
	
	@Autowired
	private ReservaRepository reservasRepository;
	
	@PostMapping("/reservas/checkin")
	public Reservas createReserva(@Valid @RequestBody Reservas reserva) {
		
		Hospedes hospede = hospedesRepository.findOneByNomeIgnoreCaseAndDocumentoAndTelefone(reserva.getHospede().getNome(), reserva.getHospede().getDocumento(), reserva.getHospede().getTelefone());
		if(hospede == null) {
			hospede = hospedesRepository.save(reserva.getHospede());
		}
		reserva.setHospede(hospede);
		
		if(reserva.getDataSaida() != null) {
			// calcular valor com data de saida
			reserva.setValor(this.calcularValor(reserva.getDataEntrada(), reserva.getDataSaida(), reserva.getAdicionalVeiculo()));
		}
		
		reserva = reservasRepository.save(reserva);
		
		return reserva;
	}
	
	@GetMapping("/reservas/abertas")
	public Page<Reservas> reservasAbertas(Pageable pageable){
		Page<Reservas> reservas = reservasRepository.findByDataSaidaIsNull(pageable);
		if(reservas.getSize() > 0) {
			for(Reservas res : reservas) {
				if(!(res.getValor() > 0)) {
					res.setValor(this.calcularValor(res.getDataEntrada(), new Date(), res.getAdicionalVeiculo()));
				}
			}
		}
		return reservas;
	}
	
	@GetMapping("/reservas/fechadas")
	public Page<Reservas> reservasFechadas(Pageable pageable){
		Page<Reservas> reservas = reservasRepository.findByDataSaidaIsNotNull(pageable);
		if(reservas.getSize() > 0) {
			for(Reservas res : reservas) {
				if(!(res.getValor() > 0)) {
					res.setValor(this.calcularValor(res.getDataEntrada(), res.getDataSaida(), res.getAdicionalVeiculo()));
				}
			}
		}
		return reservas;
	}
	
	private double calcularValor(Date dataEntrada, Date dataSaida, boolean adicionalVeiculo) {
		double valor = 0.0;
		Date current = dataEntrada;

		/*
		 * domingo 1
		 * segunda 2
		 * terca 3
		 * quarta 4
		 * quinta 5
		 * sexta 6
		 * sabado 7
		 * */

		while(current.before(dataSaida)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(current);
			
			if(calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 7) {
				// final de semana
				valor = valor + 150;
				if(adicionalVeiculo) {
					valor = valor + 20;
				}
			} else {
				// dia de semana
				valor = valor + 120;
				if(adicionalVeiculo) {
					valor = valor + 15;
				}
			}
			calendar.add(Calendar.DATE, 1);
			current = calendar.getTime();
		}
		return valor;
	}
	
}
