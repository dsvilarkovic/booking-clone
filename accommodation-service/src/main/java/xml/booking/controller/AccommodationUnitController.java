package xml.booking.controller;

import java.math.BigDecimal;
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
import xml.booking.model.AccommodationUnit;
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
		AccommodationUnit accommodationUnit = accommodationUnitManager.findById(accommodationUnitId);
		return (accommodationUnit == null) ? ResponseEntity.status(404).build() : ResponseEntity.ok(new AccommodationUnitDTO(accommodationUnit));
	}

	@GetMapping("/days/all/{accommodationUnitId}")
	public ResponseEntity<?> getAccommodationUnitDays(@PathVariable Long accommodationUnitId) {
		List<Day> days = this.accommodationUnitManager.findAllDays(accommodationUnitId);
		return (days == null) ? ResponseEntity.status(404).build() : ResponseEntity.ok(days);
	}

	@GetMapping("/days/{accommodationUnitId}")
	public ResponseEntity<?> getAccommodationUnitDaysBetween(@RequestParam long from, @RequestParam long to,
			@PathVariable Long accommodationUnitId) {
		List<Day> days = this.accommodationUnitManager.findDaysBetween(accommodationUnitId, from, to);
		return (days == null) ? ResponseEntity.status(404).build() : ResponseEntity.ok(days);
	}

	@GetMapping("/available/{accommodationUnitId}")
	public ResponseEntity<?> checkReservationInfo(@RequestParam long from, @RequestParam long to,
			@RequestParam int numberOfPersons, @PathVariable Long accommodationUnitId) {
		
		System.out.println("USAO SAM OVDE");
		// 1. Provera da li je smestaj postojeci ili obrisan
		AccommodationUnit accommodationUnit = accommodationUnitManager.findById(accommodationUnitId);
		System.out.println(accommodationUnit + "STIGAO");
		if (accommodationUnit == null)
			return ResponseEntity.ok(null);

		// 2. Da li je smestaj slobodan u tom periodu po danima koje je kreirao admin i
		// po rezervacijama korisnika
		boolean free = accommodationUnitManager.isAccommodationUnitFreeForReservation(accommodationUnit, from, to);
		System.out.println("FREE " + free);
		if (!free)
			return ResponseEntity.ok(null);
		// 3. Da li broj osoba veci od smestajnog kapaciteta
		boolean hasCapacity = accommodationUnit.getCapacity() >= numberOfPersons;
		System.out.println("hasCapacity " + hasCapacity);
		if (!hasCapacity)
			return ResponseEntity.ok(null);

		return ResponseEntity.ok(accommodationUnit);
	}

	@GetMapping("/price/{accommodationUnitId}")
	public ResponseEntity<?> findAccommodationPrice(@RequestParam long from, @RequestParam long to, @PathVariable Long accommodationUnitId) {
		AccommodationUnit accommodationUnit = accommodationUnitManager.findById(accommodationUnitId);
		if (accommodationUnit == null)
			return ResponseEntity.status(400).build();
		
		BigDecimal price = this.accommodationUnitManager.findPrice(accommodationUnit, from, to);
		return ResponseEntity.ok(price);
	}

}
