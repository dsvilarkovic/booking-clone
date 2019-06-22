package xml.booking.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import xml.booking.dto.ReservationDTO;

@FeignClient(name="reservations", url="localhost:8762/api/reservations")
public interface ReservationProxy {
	
	@GetMapping("/{reservationId}")
	public  ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long reservationId);
	
}


