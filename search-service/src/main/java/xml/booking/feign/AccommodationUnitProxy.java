package xml.booking.feign;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

	@FeignClient(name="accommodation-service", url="localhost:8762/api/accommodationService/accommodationUnit")
	public interface AccommodationUnitProxy {
		
		@GetMapping("/price/{accommodationUnitId}")
		public ResponseEntity<BigDecimal> findAccommodationPrice(@RequestParam long from, @RequestParam long to, @PathVariable Long accommodationUnitId);
	
	}
