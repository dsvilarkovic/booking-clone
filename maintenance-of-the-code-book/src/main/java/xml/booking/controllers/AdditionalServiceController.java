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
import xml.booking.managers.AdditionalServiceManager;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*", maxAge = 3600)
@RequestMapping( value = "/additionalService", produces = MediaType.APPLICATION_JSON_VALUE )
public class AdditionalServiceController {
	@Autowired
	private AdditionalServiceManager additionalServiceManager;
	
	@GetMapping("")
	public  ResponseEntity<?> getAllAccommodationCategories()
	{
		 return null;
	}
	
	@GetMapping("/{additionalServiceId}")
	public  ResponseEntity<?> getAdditionalServiceById(@PathVariable Long additionalServiceId)
	{
		 return null;
	}
	
	@PostMapping("")
	public ResponseEntity<?> addAdditionalService(@RequestBody CodebookDTO additionalService)
	{
		return null;
	}
	
	@PutMapping("/{additionalServiceId}")
	public ResponseEntity<?> changeAdditionalService(@RequestBody CodebookDTO additionalService )
	{
		return null;
	}

	@DeleteMapping("/{additionalServiceId}")
	public ResponseEntity<?> deleteAdditionalService( @PathVariable Long additionalServiceId)
	{
		return null;
	}
}
