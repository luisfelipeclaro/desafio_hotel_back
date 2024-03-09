package com.hotel.hotelchallengegradle.controllers;

import com.hotel.hotelchallengegradle.exception.ResourceNotFoundException;
import com.hotel.hotelchallengegradle.model.Hospedes;
import com.hotel.hotelchallengegradle.repository.HospedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import javax.validation.Valid;

@RestController
public class HospedesController {

	@Autowired
	private HospedeRepository hospedesRepository;
	
	@GetMapping("/hospedes")
	public Page<Hospedes> getHospedes(Pageable pageable){
		return hospedesRepository.findAll(pageable);
	}
	
	@PostMapping("/hospedes")
    public Hospedes createHospede(@Valid @RequestBody Hospedes hospede) {
        return hospedesRepository.save(hospede);
    }
	
	@GetMapping("/hospedes/{id}")
	public Hospedes getHospede(@PathVariable("id")Long id) {
		Optional<Hospedes> hospede = hospedesRepository.findById(id); 
		if(!hospede.isPresent()) {
			throw new ResourceNotFoundException("Hóspede ID: " + id + " não encontrado.");
		}
		return hospede.get();
	}
	
	@DeleteMapping("/hospedes/{id}")
	public void deleteHospede(@PathVariable("id") Long id) {
		hospedesRepository.deleteById(id);
	}
	
	@PutMapping("/hospedes/{id}")
	public Hospedes updateHospede(@RequestBody Hospedes hospede, @PathVariable Long id){
		Optional<Hospedes> hospedeOptional = hospedesRepository.findById(id);
		if(!hospedeOptional.isPresent()) {
			throw new ResourceNotFoundException("Hóspede não encontrado.");
		}
		
		hospede.setId(id);
		hospedesRepository.save(hospede);
		return hospedesRepository.getOne(id);
	}
	
	@GetMapping("/hospedes/buscar")
	@ResponseBody
	public Page<Hospedes> buscar(@RequestParam(name="searchString", required=true) String searchString, Pageable pageable) {
		if(! searchString.isEmpty() && ! "".equals(searchString) ){
			String nome = searchString;
			String documento = searchString;
			String telefone = searchString;
			return hospedesRepository.findByNomeContainingIgnoreCaseOrDocumentoContainingIgnoreCaseOrTelefoneContainingIgnoreCase(nome, documento, telefone, pageable);
		}
		return null;
	}
	
}
