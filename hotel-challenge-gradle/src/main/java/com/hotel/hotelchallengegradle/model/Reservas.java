package com.hotel.hotelchallengegradle.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "reservas")
public class Reservas extends AuditModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private Hospedes hospede;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEntrada;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataSaida;
	
	private Boolean adicionalVeiculo;
	
	@Column(name = "valor", nullable = false, columnDefinition = "double default 0.0")
	private double Valor;

	public double getValor() {
		return Valor;
	}

	public void setValor(double valor) {
		Valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Hospedes getHospede() {
		return hospede;
	}

	public void setHospede(Hospedes hospede) {
		this.hospede = hospede;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}

	public Boolean getAdicionalVeiculo() {
		return adicionalVeiculo;
	}

	public void setAdicionalVeiculo(Boolean adicionalVeiculo) {
		this.adicionalVeiculo = adicionalVeiculo;
	}

}
