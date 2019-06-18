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
import xml.booking.managers.AdditionalServiceManager;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/additionalService", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdditionalServiceController {
	@Autowired
	private AdditionalServiceManager additionalServiceManager;

	@GetMapping("")
	public ResponseEntity<?> getAllAccommodationCategoriesPageable(@RequestParam(defaultValue = "0") int page) {
		return ResponseEntity.ok(additionalServiceManager.getAllAdditionalServices(PageRequest.of(page, 9)));
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllAccommodationCategories() {
		return ResponseEntity.ok(additionalServiceManager.getAllAdditionalServices());
	}

	@GetMapping("/{additionalServiceId}")
	public ResponseEntity<?> getAdditionalServiceById(@PathVariable Long additionalServiceId) {
		CodeBookDTO service = additionalServiceManager.findOne(additionalServiceId);
		return (service == null) ? ResponseEntity.status(404).build() : ResponseEntity.ok(service);
	}

	@PostMapping("")
	public ResponseEntity<?> addAdditionalService(@Valid @RequestBody CodeBookDTO additionalService) {
		CodeBookDTO dto = additionalServiceManager.save(additionalService);
		return (dto != null) ? new ResponseEntity<>(dto, HttpStatus.CREATED) : ResponseEntity.status(400).build();
	}

	@PutMapping("/{additionalServiceId}")
	public ResponseEntity<?> changeAdditionalService(@PathVariable Long additionalServiceId,
			@Valid @RequestBody CodeBookDTO additionalService) {
		return (additionalServiceManager.update(additionalService, additionalServiceId))
				? ResponseEntity.status(204).build()
				: ResponseEntity.status(400).build();
	}

	@DeleteMapping("/{additionalServiceId}")
	public ResponseEntity<?> deleteAdditionalService(@PathVariable Long additionalServiceId) {
		return (additionalServiceManager.remove(additionalServiceId)) ? ResponseEntity.status(200).build()
				: ResponseEntity.status(400).build();
	}
}
