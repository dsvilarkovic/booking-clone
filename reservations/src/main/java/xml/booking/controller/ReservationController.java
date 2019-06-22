package xml.booking.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
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
			return ResponseEntity.status(400).build();
		ResponseEntity<AccommodationUnit> accommodationUnit = this.accommodationUnitProxy.checkReservationInfo(reservation.getBeginningDate(),  reservation.getEndDate(), reservation.getNumberOfPersons(), reservation.getAccommodationUnitId());
		System.out.println(accommodationUnit);
		if(accommodationUnit.getStatusCode() != HttpStatus.OK || accommodationUnit.getBody() == null)
			return ResponseEntity.status(400).build();
		
		ResponseEntity<BigDecimal> price = this.accommodationUnitProxy.findAccommodationPrice(reservation.getBeginningDate(),reservation.getEndDate(), reservation.getAccommodationUnitId());
		
		if(price.getStatusCode() != HttpStatus.OK || price.getBody() == null)
			return ResponseEntity.status(400).build();
		System.out.println(price.getBody());
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
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getUserReservation(@PathVariable Long userId) {
		// TODO: Dodati proveru za korisnika da li postoji !
		return ResponseEntity.ok(this.reservationManager.getAllUserReservations(userId));
		
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

}
