package xml.booking.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xml.booking.dto.CodeBookDTO;
import xml.booking.managers.AccommodationCategoryManager;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/accommodationCategory", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccommodationCategoryController {

	@Autowired
	private AccommodationCategoryManager accommodationCategoryManager;

	@GetMapping("")
	public ResponseEntity<?> getAllAccommodationCategories(@RequestParam(defaultValue = "0") int page) {
		return ResponseEntity.ok(accommodationCategoryManager.getAllAccommodationCategories(PageRequest.of(page, 9)));
	}

	@GetMapping("/{accommodationCategoryId}")
	public ResponseEntity<?> getAccommodationCategoryById(@PathVariable Long accommodationCategoryId) {
		CodeBookDTO category = accommodationCategoryManager.findOne(accommodationCategoryId);
		return (category == null) ? ResponseEntity.status(404).build() : ResponseEntity.ok(category);
	}

	@PostMapping("")
	public ResponseEntity<?> addAccommodationCategory(@Valid @RequestBody CodeBookDTO accommodationCategory) {
		CodeBookDTO dto = accommodationCategoryManager.save(accommodationCategory);
		return (dto != null) ? new ResponseEntity<>(dto, HttpStatus.CREATED) : ResponseEntity.status(400).build();
	}

	@PutMapping("/{accommodationCategoryId}")
	public ResponseEntity<?> changeAccommodationCategory(@PathVariable Long accommodationCategoryId,
			@Valid @RequestBody CodeBookDTO accommodationCategory) {
		return (accommodationCategoryManager.update(accommodationCategory, accommodationCategoryId))
				? ResponseEntity.status(204).build()
				: ResponseEntity.status(400).build();
	}

	@DeleteMapping("/{accommodationCategoryId}")
	public ResponseEntity<?> deleteAccommodationCategory(@PathVariable Long accommodationCategoryId) {
		return (accommodationCategoryManager.remove(accommodationCategoryId)) ? ResponseEntity.status(200).build()
				: ResponseEntity.status(400).build();
	}

}
