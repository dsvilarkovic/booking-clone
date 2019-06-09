package xml.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xml.booking.dto.ReservationDTO;
import xml.booking.managers.ReservationManager;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*", maxAge = 3600)
@RequestMapping( value = "/reservation", produces = MediaType.APPLICATION_JSON_VALUE )
public class ReservationController {
	@Autowired
	private ReservationManager reservationManager;
	
	@GetMapping("")
	public  ResponseEntity<?> getAllReservations()
	{
		 return ResponseEntity.ok("Veki");
	}
	
	@GetMapping("/{reservationId}")
	public  ResponseEntity<?> getAccommodationCategoryById(@PathVariable Long reservationId)
	{
		return ResponseEntity.ok("Veki");
	}
	
	@PostMapping("")
	public ResponseEntity<?> addAccommodationCategory(@RequestBody ReservationDTO reservationDTO)
	{
		return null;
	}
	
	@PutMapping("/{reservationId}")
	public ResponseEntity<?> changeAccommodationCategory(@RequestBody ReservationDTO reservationDTO )
	{
		return null;
	}

	@DeleteMapping("/{reservationId}")
	public ResponseEntity<?> deleteAccommodationCategory( @PathVariable Long reservationId)
	{
		return null;
	}
}
