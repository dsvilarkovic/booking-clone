package xml.booking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xml.booking.dto.MessageDTO;
import xml.booking.dto.ReservationDTO;
import xml.booking.feign.AuthenticationProxy;
import xml.booking.feign.ReservationProxy;
import xml.booking.managers.MessageManager;
import xml.booking.model.Message;
import xml.booking.model.User;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

	@Autowired
	private MessageManager messageManager;

	@Autowired
	private ReservationProxy reservationProxy;

	@Autowired
	private AuthenticationProxy authenticationProxy;

	@Value("Authorization")
	private String AUTH_HEADER;

	@GetMapping("")
	public ResponseEntity<?> getAllMessages(@RequestParam(defaultValue = "0") int page) {
		return ResponseEntity.ok(messageManager.getAllMessages(PageRequest.of(page, 9)));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getAllUserMessages(@RequestParam(defaultValue = "0") int page, @PathVariable Long userId) {
		Page<MessageDTO> messages = messageManager.findByUserId(PageRequest.of(page, 9), userId);
		return (messages != null) ? ResponseEntity.ok(messages) : ResponseEntity.status(404).build();
	}

	@GetMapping("/{messageId}")
	public ResponseEntity<?> getMessageById(@PathVariable Long messageId) {
		MessageDTO messageDTO = messageManager.findOne(messageId);
		return (messageDTO == null) ? ResponseEntity.status(404).build() : ResponseEntity.ok(messageDTO);
	}

	@PostMapping("")
	public ResponseEntity<?> saveMessage(HttpServletRequest request, @RequestBody @Valid MessageDTO messageDTO) {
		ResponseEntity<ReservationDTO> reservation;
		try {
			reservation = reservationProxy.getReservationById(messageDTO.getReservationId());
		} catch (Exception e) {
			return ResponseEntity.status(400).build();
		}
		// 1. Provera da li postoji rezervacija navedena u messageDTO
		if (reservation.getStatusCode() != HttpStatus.OK)
			return ResponseEntity.status(400).build();

		ResponseEntity<User> user;
		try {
			user = authenticationProxy.whoami("Bearer " + getToken(request));
		} catch (Exception e) {
			return ResponseEntity.status(400).build();
		}

		// 2. Provera ulogovanog korisnika + da li je ulogovani korisnik isti kao onaj
		// koji je kreirao rezervaciju
		if (user.getStatusCode() != HttpStatus.OK || user.getBody().getId() != reservation.getBody().getUserId()) {
			return ResponseEntity.status(400).build();
		}
		// 3. Cuvanje poruke u message tabeli
		Message message = messageManager.save(messageDTO, user.getBody());

		// 4. Slanje servisu za rezervaciju da sacuva poruku
		ResponseEntity<?> savedResponse = reservationProxy.savedMessage(reservation.getBody().getId(), message);

		if (savedResponse.getStatusCode() == HttpStatus.OK)
			return ResponseEntity.ok(new MessageDTO(message));

		return ResponseEntity.status(400).build();

	}

	@GetMapping("/reservation/{reservationId}")
	public ResponseEntity<?> getReservationMessages(@PathVariable Long reservationId) {
		List<Message> message = messageManager.getMessagesReservation(reservationId);
		return (message == null) ? ResponseEntity.status(404).build() : ResponseEntity.ok(message);
	}

	private String getToken(HttpServletRequest request) {
		String authHeader = (String) request.getHeader(AUTH_HEADER);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}

		return null;
	}

}
