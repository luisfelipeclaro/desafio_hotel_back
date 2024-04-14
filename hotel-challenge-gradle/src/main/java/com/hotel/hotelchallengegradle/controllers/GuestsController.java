package com.hotel.hotelchallengegradle.controllers;

import com.hotel.hotelchallengegradle.exception.ResourceNotFoundException;
import com.hotel.hotelchallengegradle.model.Guest;
import com.hotel.hotelchallengegradle.repository.GuestRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/hotel", produces = {"application/json"})
@Tag(name = "Guests")
public class GuestsController {

	@Autowired
	private GuestRepository guestRepository;
	
	@GetMapping("/guest")
	@Operation(summary = "Fetch all guests", method = "GET")
	public List<Guest> getGuests(){
		return guestRepository.findAll();
	}
	
	@PostMapping("/guest")
	@Operation(summary = "Creates new guest", method = "POST")
    public Guest createGuest(@RequestParam("guest") @Valid @RequestBody Guest guest) {
        return guestRepository.save(guest);
    }
	
	@GetMapping("/guest/{id}")
	@Operation(summary = "Get one guest by its ID", method = "GET")
	public Guest getGuest(@PathVariable("id") Long id) {
		Optional<Guest> optionalGuest = guestRepository.findById(id);
		if(!optionalGuest.isPresent()) {
			throw new ResourceNotFoundException("Guest ID: " + id + " not found.");
		}
		return optionalGuest.get();
	}
	
	@DeleteMapping("/guest/{id}")
	@Operation(summary = "Delete one guest by its ID", method = "DELETE")
	public void deleteGuest(@PathVariable("id") Long id) {
		guestRepository.deleteById(id);
	}
	
	@PutMapping("/guest/{id}")
	@Operation(summary = "Update one guest")
	public Guest updateGuest(@RequestBody @RequestParam(value = "guest") Guest guest,
							 @PathVariable("id") Long id){
		Optional<Guest> optionalGuest = guestRepository.findById(id);
		if(!optionalGuest.isPresent()) {
			throw new ResourceNotFoundException("Guest not found.");
		}
		
		guest.setId(id);
		guestRepository.save(guest);
		return guestRepository.getOne(id);
	}
	
	@GetMapping("/guest/search")
	@Operation(summary = "Search guest", method = "GET")
	@ResponseBody
	public List<Guest> searchGuest(@RequestParam(name="searchString") String searchString) {
		if(!searchString.isEmpty()){
            return guestRepository
					.findByFullNameContainingIgnoreCaseOrSocialIdentificationContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(
							searchString, searchString, searchString);
		}
		return Collections.emptyList();
	}
	
}
