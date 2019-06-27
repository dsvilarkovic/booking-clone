package xml.booking.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import xml.booking.dto.ReservationDTO;
import xml.booking.model.Message;

@FeignClient(name="reservations")
public interface ReservationProxy {
	
	@GetMapping("/{reservationId}")
	public  ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long reservationId);
	
	@PutMapping("/messages/{reservationId}")
	public ResponseEntity<?> savedMessage(@PathVariable Long reservationId, @RequestBody Message message);
}
