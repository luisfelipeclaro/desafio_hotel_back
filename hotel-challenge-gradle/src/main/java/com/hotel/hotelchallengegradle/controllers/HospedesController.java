package com.hotel.hotelchallengegradle.controllers;

import com.hotel.hotelchallengegradle.exception.ResourceNotFoundException;
import com.hotel.hotelchallengegradle.model.Hospedes;
import com.hotel.hotelchallengegradle.repository.HospedeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/hotel", produces = {"application/json"})
@Tag(name = "Guests")
public class HospedesController {

	@Autowired
	private HospedeRepository hospedesRepository;
	
	@GetMapping("/hospedes")
	@Operation(summary = "Fetch all guests", method = "GET")
	public Page<Hospedes> getHospedes(@RequestParam(value = "pageable", required = false) Pageable pageable){
		return hospedesRepository.findAll(pageable);
	}
	
	@PostMapping("/hospedes")
	@Operation(summary = "Creates new guest", method = "POST")
    public Hospedes createHospede(@RequestParam("hospede") @Valid @RequestBody Hospedes hospede) {
        return hospedesRepository.save(hospede);
    }
	
	@GetMapping("/hospedes/{id}")
	@Operation(summary = "Get one guest by its ID", method = "GET")
	public Hospedes getHospede(@PathVariable("id") Long id) {
		Optional<Hospedes> hospede = hospedesRepository.findById(id); 
		if(!hospede.isPresent()) {
			throw new ResourceNotFoundException("Hóspede ID: " + id + " não encontrado.");
		}
		return hospede.get();
	}
	
	@DeleteMapping("/hospedes/{id}")
	@Operation(summary = "Delete one guest by its ID", method = "DELETE")
	public void deleteHospede(@PathVariable("id") Long id) {
		hospedesRepository.deleteById(id);
	}
	
	@PutMapping("/hospedes/{id}")
	@Operation(summary = "Update one guest")
	public Hospedes updateHospede(@RequestBody @RequestParam(value = "hospede", required = true) Hospedes hospede,
								  @PathVariable("id") Long id){
		Optional<Hospedes> hospedeOptional = hospedesRepository.findById(id);
		if(!hospedeOptional.isPresent()) {
			throw new ResourceNotFoundException("Hóspede não encontrado.");
		}
		
		hospede.setId(id);
		hospedesRepository.save(hospede);
		return hospedesRepository.getOne(id);
	}
	
	@GetMapping("/hospedes/buscar")
	@Operation(summary = "Search guest", method = "GET")
	@ResponseBody
	public Page<Hospedes> buscar(@RequestParam(name="searchString") String searchString,
								 @RequestParam(value = "pageable", required = false) Pageable pageable) {
		if(!searchString.isEmpty()){
            return hospedesRepository
					.findByNomeContainingIgnoreCaseOrDocumentoContainingIgnoreCaseOrTelefoneContainingIgnoreCase(
							searchString, searchString, searchString, pageable);
		}
		return null;
	}
	
}
