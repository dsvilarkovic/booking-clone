package xml.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xml.booking.dto.AccommodationDTO;
import xml.booking.dto.AccommodationUnitDTO;
import xml.booking.managers.AccommodationManager;
import xml.booking.model.Accommodation;
import xml.booking.model.AdditionalService;
import xml.booking.model.Image;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/accommodation", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccommodationController {
	
	@Autowired
	private AccommodationManager accommodationManager;
	
	@GetMapping("")
	public ResponseEntity<?> getAllAccommodationPageable(@RequestParam(defaultValue = "0") int page) {
		return ResponseEntity.ok(accommodationManager.getAllAccommodationPageable(PageRequest.of(page, 9)));
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllAccommodationList() {
		return ResponseEntity.ok(accommodationManager.getAllAccommodation());
	}

	@GetMapping("/{accommodationId}")
	public ResponseEntity<?> getAccommodationById(@PathVariable Long accommodationId) {
		Accommodation accommodation = accommodationManager.findById(accommodationId);
		return (accommodation == null) ? ResponseEntity.status(404).build() : ResponseEntity.ok(new AccommodationDTO(accommodation));
	}
	
	@GetMapping("/images/{accommodationId}")
	public ResponseEntity<?> getAccommodationImages(@PathVariable Long accommodationId) {
		List<Image> images = this.accommodationManager.getAccommodationImageList(accommodationId);
		return (images == null) ? ResponseEntity.status(404).build() : ResponseEntity.ok(images);
	}
	
	@GetMapping("/additionalServices/{accommodationId}")
	public ResponseEntity<?> getAccommodationAdditionalServices(@RequestParam(defaultValue = "0") int page,@PathVariable Long accommodationId) {
		Page<AdditionalService> additionalServices = this.accommodationManager.getAdditionalServices(PageRequest.of(page, 9), accommodationId);
		return (additionalServices == null) ? ResponseEntity.status(404).build() : ResponseEntity.ok(additionalServices);
	}
	
	@GetMapping("/accommodationUnits/{accommodationId}")
	public ResponseEntity<?> getAccommodationUnits(@RequestParam(defaultValue = "0") int page, @PathVariable Long accommodationId) {
		Page<AccommodationUnitDTO> accommodationUnits = this.accommodationManager.getAccommodationUnit(PageRequest.of(page, 9), accommodationId);
		return (accommodationUnits == null) ? ResponseEntity.status(404).build() : ResponseEntity.ok(accommodationUnits);
	}
	

}
