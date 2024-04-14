package com.hotel.hotelchallengegradle.controllers;

import com.hotel.hotelchallengegradle.model.Guest;
import com.hotel.hotelchallengegradle.model.Booking;
import com.hotel.hotelchallengegradle.repository.GuestRepository;
import com.hotel.hotelchallengegradle.repository.BookingRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/hotel", produces = {"application/json"})
@Tag(name = "Booking")
public class BookingsController {

	@Autowired
	private GuestRepository guestRepository;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@PostMapping("/booking/check_in")
	@Operation(summary = "Create booking", method = "POST")
	public Booking createBooking(@Valid @RequestBody @RequestParam(value = "booking") Booking booking) {
		
		Guest guest = guestRepository.findOneByFullNameIgnoreCaseAndSocialIdentificationAndPhoneNumber(booking.getGuest().getFullName(), booking.getGuest().getSocialIdentification(), booking.getGuest().getPhoneNumber());
		if(guest == null) {
			guest = guestRepository.save(booking.getGuest());
		}
		booking.setGuest(guest);
		
		if(booking.getCheckOutDate() != null) {
			// calculate stay expense with check out date
			booking.setStayExpense(this.calculateStayExpense(booking.getCheckInDate(), booking.getCheckOutDate(), booking.getHasVehicle()));
		}
		
		booking = bookingRepository.save(booking);
		
		return booking;
	}
	
	@GetMapping("/booking/open")
	@Operation(summary = "Fetch opened bookings", method = "GET")
	public List<Booking> bookingsOpen(){
		List<Booking> bookingList = bookingRepository.findByCheckOutDateIsNull();
		if(!bookingList.isEmpty()) {
			for(Booking booking : bookingList) {
				if(!(booking.getStayExpense() > 0)) {
					booking.setStayExpense(this.calculateStayExpense(booking.getCheckInDate(), new Date(), booking.getHasVehicle()));
				}
			}
		}
		return bookingList;
	}
	
	@GetMapping("/booking/closed")
	@Operation(summary = "Fetch closed bookings", method = "GET")
	public List<Booking> bookingsClosed(){
		List<Booking> bookingsList = bookingRepository.findByCheckOutDateIsNotNull();
		if(!bookingsList.isEmpty()) {
			for(Booking booking : bookingsList) {
				if(!(booking.getStayExpense() > 0)) {
					booking.setStayExpense(this.calculateStayExpense(booking.getCheckInDate(), booking.getCheckOutDate(), booking.getHasVehicle()));
				}
			}
		}
		return bookingsList;
	}
	
	private double calculateStayExpense(Date checkInDate, Date checkOutDate, boolean hasVehicle) {
		double expenseValue = 0.0;
		Date currentDate = checkInDate;

		while(currentDate.before(checkOutDate)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			
			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
					|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				// weekend
				expenseValue = expenseValue + 150;
				if(hasVehicle) {
					expenseValue = expenseValue + 20;
				}
			} else {
				// weekday
				expenseValue = expenseValue + 120;
				if(hasVehicle) {
					expenseValue = expenseValue + 15;
				}
			}
			calendar.add(Calendar.DATE, 1);
			currentDate = calendar.getTime();
		}
		return expenseValue;
	}
	
}
