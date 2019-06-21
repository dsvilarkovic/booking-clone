package xml.booking.feign;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import xml.booking.dto.AccommodationUnitDTO;
import xml.booking.model.AccommodationUnit;

@FeignClient(name="accommodation-service", url="localhost:8762/api/accommodationService/accommodationUnit")
public interface AccommodationUnitProxy {
	
	@GetMapping("/{accommodationUnitId}")
	public ResponseEntity<AccommodationUnitDTO> getAccommodationUnitById(@PathVariable Long accommodationUnitId);
	
	@GetMapping("/available/{accommodationUnitId}")
	public ResponseEntity<AccommodationUnit> checkReservationInfo(@RequestParam long from, @RequestParam long to,
			@RequestParam int numberOfPersons, @PathVariable Long accommodationUnitId);
	
	@GetMapping("/price/{accommodationUnitId}")
	public ResponseEntity<BigDecimal> findAccommodationPrice(@RequestParam long from, @RequestParam long to, @PathVariable Long accommodationUnitId);
}
