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
import xml.booking.managers.AccommodationCategoryManager;
import xml.booking.model.AccommodationCategory;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*", maxAge = 3600)
@RequestMapping( value = "/accommodationCategory", produces = MediaType.APPLICATION_JSON_VALUE )
public class AccommodationCategoryController {
	
	@Autowired
	private AccommodationCategoryManager accommodationCategoryManager;
	
	@GetMapping("")
	public  ResponseEntity<?> getAllAccommodationCategories()
	{
		 return ResponseEntity.ok(accommodationCategoryManager.getAllAccommodationCategories());
	}
	
	@GetMapping("/{accommodationCategoryId}")
	public  ResponseEntity<?> getAccommodationCategoryById(@PathVariable Long accommodationCategoryId)
	{
		 return null;
	}
	
	@PostMapping("")
	public ResponseEntity<?> addAccommodationCategory(@RequestBody CodebookDTO accommodationCategory)
	{
		return null;
	}
	
	@PutMapping("/{accommodationCategoryId}")
	public ResponseEntity<?> changeAccommodationCategory(@RequestBody CodebookDTO accommodationCategory )
	{
		return null;
	}

	@DeleteMapping("/{accommodationCategoryId}")
	public ResponseEntity<?> deleteAccommodationCategory( @PathVariable Long accommodationCategoryId)
	{
		return null;
	}
	
}
