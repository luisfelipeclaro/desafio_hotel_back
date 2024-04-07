package com.hotel.hotelchallengegradle.controllers;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.hotel.hotelchallengegradle.exception.ResourceNotFoundException;
import com.hotel.hotelchallengegradle.model.Hospedes;
import com.hotel.hotelchallengegradle.model.Reservas;
import com.hotel.hotelchallengegradle.repository.HospedeRepository;
import com.hotel.hotelchallengegradle.repository.ReservaRepository;

import ch.qos.logback.core.net.SyslogOutputStream;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(value = "/hotel", produces = {"application/json"})
@Tag(name = "Booking")
public class ReservasController {

	@Autowired
	private HospedeRepository hospedesRepository;
	
	@Autowired
	private ReservaRepository reservasRepository;
	
	@PostMapping("/reservas/checkin")
	@Operation(summary = "Create booking", method = "POST")
	public Reservas createReserva(@Valid @RequestBody @RequestParam(value = "reserva") Reservas reserva) {
		
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
	@Operation(summary = "Fetch opened bookings", method = "GET")
	public Page<Reservas> reservasAbertas(@RequestParam(value = "pageable", required = false) Pageable pageable){
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
	@Operation(summary = "Fetch closed bookings", method = "GET")
	public Page<Reservas> reservasFechadas(@RequestParam(value = "pageable", required = false) Pageable pageable){
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

		while(current.before(dataSaida)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(current);
			
			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
					|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
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
