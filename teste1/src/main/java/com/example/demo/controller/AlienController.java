package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Alien;
import com.example.demo.repository.AlienRepository;


@RestController
@RequestMapping("/api/v1")
public class AlienController {
	
	@Autowired
	private AlienRepository alienRepository;
	
	// get alien
	@GetMapping("/aliens")
	public List<Alien> getAllAlien(){
		return this.alienRepository.findAll();
	}
	
	//get alien id 
	@GetMapping("/aliens/{id}")
	public ResponseEntity<Alien> getAlienById(@PathVariable(value="id") Long  alienid)
			throws ResourceNotFoundException{
		Alien alien = alienRepository.findById(alienid)
				.orElseThrow(()-> new ResourceNotFoundException("Alien not found for this id ::"+ alienid));
			return ResponseEntity.ok().body(alien);
	}
	//save alien
	@PostMapping("/aliens")
	public Alien createAlien(@Validated @RequestBody Alien alien) {
		return alienRepository.save(alien);
		
	}
	//update alien
	@PutMapping("/aliens/{id}")
	public ResponseEntity<Alien> updateAlien(@PathVariable(value="id") Long alienid,
			@Validated @RequestBody Alien alienDetails) throws ResourceNotFoundException{
		Alien alien = alienRepository.findById(alienid)
				.orElseThrow(()-> new ResourceNotFoundException("Alien not found for this id ::"+ alienid));
		alien.setEmailId(alienDetails.getEmailId());
		alien.setFirstName(alienDetails.getFirstName());
		alien.setLastName(alienDetails.getLastName());
		
		return ResponseEntity.ok(this.alienRepository.save(alien));
	}
	// delete alien
	@DeleteMapping("/aliens/{id}")
	public Map<String,Boolean> deleteAlien(@PathVariable(value = "id") Long alienid) throws ResourceNotFoundException{
		Alien alien = alienRepository.findById(alienid)
				.orElseThrow(()-> new ResourceNotFoundException("Alien not found for this id ::"+ alienid));
		this.alienRepository.delete(alien);
		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted",Boolean.TRUE);
		return response;			
	}
	
}
