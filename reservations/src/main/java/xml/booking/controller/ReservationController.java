package xml.booking.controller;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xml.booking.dto.AccommodationUnitDTO;
import xml.booking.dto.ReservationDTO;
import xml.booking.dto.UserDTO;
import xml.booking.feign.AccommodationUnitProxy;
import xml.booking.feign.AuthenticationProxy;
import xml.booking.managers.ReservationManager;
import xml.booking.model.AccommodationUnit;
import xml.booking.model.Comment;
import xml.booking.model.Message;
import xml.booking.model.Rating;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationController {
	@Autowired
	private ReservationManager reservationManager;
	
	@Autowired
	private AccommodationUnitProxy accommodationUnitProxy;
	
	@Autowired
	private AuthenticationProxy authenticationProxy;
	
	@Value("Authorization")
	private String AUTH_HEADER;

	@GetMapping("")
	public ResponseEntity<?> getAllReservations(@RequestParam(defaultValue = "0") int page) {
		return ResponseEntity.ok(this.reservationManager.getAllReservations(PageRequest.of(page, 9)));
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllReservations() {
		return ResponseEntity.ok(this.reservationManager.getAllReservationsList());
	}

	@GetMapping("/{reservationId}")
	public ResponseEntity<?> getReservationById(@PathVariable Long reservationId) {
		ReservationDTO reservation = this.reservationManager.getReservation(reservationId);
		return (reservation == null) ? ResponseEntity.status(404).build() : ResponseEntity.ok(reservation);
	}
	
	@PostMapping("")
	public ResponseEntity<?> createReservation(HttpServletRequest request, @RequestBody  @Valid ReservationDTO reservation) {
		
		if(reservation.getBeginningDate() > reservation.getEndDate())
			return ResponseEntity.status(400).body("The start date of the reservation can't be higher than the end date");
		ResponseEntity<AccommodationUnit> accommodationUnit = this.accommodationUnitProxy.checkReservationInfo(reservation.getBeginningDate(),  reservation.getEndDate(), reservation.getNumberOfPersons(), reservation.getAccommodationUnitId());

		if(accommodationUnit.getStatusCode() != HttpStatus.OK || accommodationUnit.getBody() == null)
			return ResponseEntity.status(400).body("Booking of the accommodation unit is not possible");
		
		ResponseEntity<BigDecimal> price = this.accommodationUnitProxy.findAccommodationPrice(reservation.getBeginningDate(),reservation.getEndDate(), reservation.getAccommodationUnitId());
		
		if(price.getStatusCode() != HttpStatus.OK || price.getBody() == null)
			return ResponseEntity.status(400).build();
		
		ResponseEntity<UserDTO> user;
		try {
			user = authenticationProxy.whoami("Bearer "+ getToken(request));
		} catch (Exception e) {
			return ResponseEntity.status(400).build();
		}
		if(user.getStatusCode() != HttpStatus.OK || user.getBody() == null)
			return ResponseEntity.status(401).build();
		
		ReservationDTO saved = this.reservationManager.createReservation(reservation, accommodationUnit.getBody(), price.getBody(), user.getBody());
		return (saved == null)? ResponseEntity.status(500).build() : ResponseEntity.ok(saved);
	}

	private String getToken(HttpServletRequest request) {
		String authHeader = (String)request.getHeader(AUTH_HEADER);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}

		return null;
	}
	
	@GetMapping("/accommodationUnit/{accommodationUnitId}")
	public ResponseEntity<?> getAccommodationUnitReservation(@PathVariable Long accommodationUnitId) {
		ResponseEntity<AccommodationUnitDTO> accommodationUnitDTO = this.accommodationUnitProxy.getAccommodationUnitById(accommodationUnitId);
		if(accommodationUnitDTO.getStatusCode() != HttpStatus.OK)
			return ResponseEntity.status(400).build();
		
		return ResponseEntity.ok(this.reservationManager.getAllAccommodationUnitReservations(accommodationUnitId));
		
	}
	
	// Preuzima rezervacije ulogovanog korisnika
	@GetMapping("/user")
	public ResponseEntity<?> getUserReservation(HttpServletRequest request) {
		ResponseEntity<UserDTO> user;
		try {
			user = authenticationProxy.whoami("Bearer "+ getToken(request));
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
		if(user.getStatusCode() != HttpStatus.OK || user.getBody() == null)
			return ResponseEntity.status(401).build();
		
		return ResponseEntity.ok(this.reservationManager.getAllUserReservations(user.getBody().getId()));
		
	}

	@PutMapping("/messages/{reservationId}")
	public ResponseEntity<?> saveMessage(@PathVariable Long reservationId, @RequestBody Message message) {

		Boolean saved = this.reservationManager.saveReservationMessage(message, reservationId);

		return (saved) ? ResponseEntity.status(200).build() : ResponseEntity.status(400).build();
	}

	@PutMapping("/rating/{reservationId}")
	public ResponseEntity<?> saveRating(@PathVariable Long reservationId, @RequestBody Rating rating) {

		Boolean saved = this.reservationManager.saveReservationRating(rating, reservationId);

		return (saved) ? ResponseEntity.status(200).build() : ResponseEntity.status(400).build();
	}

	@PutMapping("/comment/{reservationId}")
	public ResponseEntity<?> saveComment(@PathVariable Long reservationId, @RequestBody Comment comment) {

		Boolean saved = this.reservationManager.saveReservationComment(comment, reservationId);

		return (saved) ? ResponseEntity.status(200).build() : ResponseEntity.status(400).build();
	}
	
	@DeleteMapping("/cancel/{reservationId}")
	public ResponseEntity<?> cancelReservation(HttpServletRequest request, @PathVariable Long reservationId) {
		ReservationDTO reservation = this.reservationManager.getReservation(reservationId);
		if(reservation == null) {
			return ResponseEntity.status(400).body("Reservation does not exist");
		}
		
		
		if(reservation.isCheckedIn())
			return ResponseEntity.status(400).body("Reservation has already been confirmed.");
		
		ResponseEntity<UserDTO> user;
		try {
			user = authenticationProxy.whoami("Bearer "+ getToken(request));
		} catch (Exception e) {
			return ResponseEntity.status(400).build();
		}
		if(user.getStatusCode() != HttpStatus.OK || user.getBody() == null || reservation.getUserId()!= user.getBody().getId())
			return ResponseEntity.status(401).build();
		
		ResponseEntity<AccommodationUnitDTO> accommodationUnitDTO = this.accommodationUnitProxy.getAccommodationUnitById(reservation.getAccommodationUnitId());
		if(accommodationUnitDTO.getStatusCode() != HttpStatus.OK) {
			return ResponseEntity.status(400).body("Accommodation unit of reservation does not exist");
		}
		
		long days = numberOfDays(new Date(), new Date(reservation.getBeginningDate()));
		if(days < accommodationUnitDTO.getBody().getCancelationPeriod())
			return ResponseEntity.status(400).body("Reservation can't be canceled");
		
		boolean success = this.reservationManager.removeReservation(reservationId);
		return (success)? ResponseEntity.status(200).build(): ResponseEntity.status(500).body("An error has occurred while trying to cancel the reservation");
	}
	
	
	/**
	 * Metoda za izracuvanje dana proteklih izmedju dva datuma
	 * 
	 * @param date1 datum 1
	 * @param date2 datum 2
	 * @return broj dana
	 */
	private long numberOfDays(Date date1, Date date2) {

		java.util.Date localDate1 = new java.util.Date(date1.getTime());
		java.util.Date localDate2 = new java.util.Date(date2.getTime());
		Long number;
		if (localDate1.after(localDate2)) {
			number = localDate1.getTime() - localDate2.getTime();
		} else {
			number = localDate2.getTime() - localDate1.getTime();
		}


		return number / 86400000;
	}

	
}
