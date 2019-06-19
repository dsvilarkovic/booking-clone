package xml.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xml.booking.dto.AccommodationUnitDTO;
import xml.booking.managers.AccommodationUnitManager;
import xml.booking.model.Day;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/accommodationUnit", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccommodationUnitController {
	
	@Autowired
	private AccommodationUnitManager accommodationUnitManager;
	
	@GetMapping("")
	public ResponseEntity<?> getAllAccommodationUnitPageable(@RequestParam(defaultValue = "0") int page) {
		return ResponseEntity.ok(accommodationUnitManager.getAllAccommodationUnitsPage(PageRequest.of(page, 9)));
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllAccommodationUnitList() {
		return ResponseEntity.ok(accommodationUnitManager.getAllAccommodationUnitsList());
	}

	@GetMapping("/{accommodationUnitId}")
	public ResponseEntity<?> getAccommodationUnitById(@PathVariable Long accommodationUnitId) {
		AccommodationUnitDTO accommodationUnit = accommodationUnitManager.findById(accommodationUnitId);
		return (accommodationUnit == null) ? ResponseEntity.status(404).build() : ResponseEntity.ok(accommodationUnit);
	}
	
	@GetMapping("/days/all/{accommodationUnitId}")
	public ResponseEntity<?> getAccommodationUnitDays(@PathVariable Long accommodationUnitId) {
		List<Day> days = this.accommodationUnitManager.findAllDays(accommodationUnitId);
		return (days == null) ? ResponseEntity.status(404).build() : ResponseEntity.ok(days);
	}
	
	@GetMapping("/days/{accommodationUnitId}")
	public ResponseEntity<?> getAccommodationUnitDaysBetween(@RequestParam long from,@RequestParam long to,@PathVariable Long accommodationUnitId) {
		List<Day> days = this.accommodationUnitManager.findDaysBetween(accommodationUnitId, from, to);
		return (days == null) ? ResponseEntity.status(404).build() : ResponseEntity.ok(days);
	}
	

}
