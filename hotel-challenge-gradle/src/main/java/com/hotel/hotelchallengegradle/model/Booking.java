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
@Table(name = "BOOKING")
public class Booking extends AuditModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private Guest guest;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHECK_IN_DATE")
	private Date checkInDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHECK_OUT_DATE")
	private Date checkOutDate;

	@Column(name = "HAS_VEHICLE")
	private Boolean hasVehicle;
	
	@Column(name = "STAY_EXPENSE", nullable = false, columnDefinition = "double default 0.0")
	private double stayExpense;

	public double getStayExpense() {
		return stayExpense;
	}

	public void setStayExpense(double stayExpense) {
		this.stayExpense = stayExpense;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Boolean getHasVehicle() {
		return hasVehicle;
	}

	public void setHasVehicle(Boolean hasVehicle) {
		this.hasVehicle = hasVehicle;
	}

}
