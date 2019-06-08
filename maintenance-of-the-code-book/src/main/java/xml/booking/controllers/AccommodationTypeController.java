package xml.booking.controllers;

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

import xml.booking.dto.CodebookDTO;
import xml.booking.managers.AccommodationTypeManager;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*", maxAge = 3600)
@RequestMapping( value = "/accommodationType", produces = MediaType.APPLICATION_JSON_VALUE )
public class AccommodationTypeController {
	@Autowired
	private AccommodationTypeManager accommodationTypeManager;
	
	@GetMapping("")
	public  ResponseEntity<?> getAllAccommodationTypes()
	{
		 return null;
	}
	
	@GetMapping("/{accommodationTypeId}")
	public  ResponseEntity<?> getAccommodationTypeById(@PathVariable Long accommodationTypeId)
	{
		 return null;
	}
	
	@PostMapping("")
	public ResponseEntity<?> addAccommodationType(@RequestBody CodebookDTO accommodationType)
	{
		return null;
	}
	
	@PutMapping("/{accommodationTypeId}")
	public ResponseEntity<?> changeAccommodationType(@RequestBody CodebookDTO accommodationType )
	{
		return null;
	}

	@DeleteMapping("/{accommodationTypeId}")
	public ResponseEntity<?> deleteAccommodationType( @PathVariable Long accommodationTypeId)
	{
		return null;
	}
}
