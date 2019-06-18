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
import xml.booking.managers.AccommodationTypeManager;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/accommodationType", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccommodationTypeController {
	@Autowired
	private AccommodationTypeManager accommodationTypeManager;

	@GetMapping("")
	public ResponseEntity<?> getAllAccommodationTypesPageable(@RequestParam(defaultValue = "0") int page) {
		return ResponseEntity.ok(accommodationTypeManager.getAllAccommodationTypes(PageRequest.of(page, 9)));
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllAccommodationTypes() {
		return ResponseEntity.ok(accommodationTypeManager.getAllAccommodationTypes());
	}

	@GetMapping("/{accommodationTypeId}")
	public ResponseEntity<?> getAccommodationTypeById(@PathVariable Long accommodationTypeId) {
		CodeBookDTO type = accommodationTypeManager.findOne(accommodationTypeId);
		return (type == null) ? ResponseEntity.status(404).build() : ResponseEntity.ok(type);
	}

	@PostMapping("")
	public ResponseEntity<?> addAccommodationType(@Valid @RequestBody CodeBookDTO accommodationType) {
		CodeBookDTO dto = accommodationTypeManager.save(accommodationType);
		return (dto != null) ? new ResponseEntity<>(dto, HttpStatus.CREATED) : ResponseEntity.status(400).build();
	}

	@PutMapping("/{accommodationTypeId}")
	public ResponseEntity<?> changeAccommodationType(@PathVariable Long accommodationTypeId,
			@Valid @RequestBody CodeBookDTO accommodationType) {
		return (accommodationTypeManager.update(accommodationType, accommodationTypeId))
				? ResponseEntity.status(204).build()
				: ResponseEntity.status(400).build();
	}

	@DeleteMapping("/{accommodationTypeId}")
	public ResponseEntity<?> deleteAccommodationType(@PathVariable Long accommodationTypeId) {
		return (accommodationTypeManager.remove(accommodationTypeId)) ? ResponseEntity.status(200).build()
				: ResponseEntity.status(400).build();
	}
	
	@GetMapping("/accommodationUnit/{accommodationUnitId}")
	public ResponseEntity<?> getAccommodationTypeByAccommodationUnitId(@PathVariable Long accommodationUnitId) {
		CodeBookDTO category = accommodationTypeManager.findTypeByAccommodationUnitId(accommodationUnitId);
		return (category == null) ? ResponseEntity.status(404).build() : ResponseEntity.ok(category);
	}
}
