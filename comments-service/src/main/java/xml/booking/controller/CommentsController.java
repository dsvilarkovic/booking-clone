package xml.booking.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import xml.booking.model.Accommodation;
import xml.booking.model.Comment;
import xml.booking.model.CommentState;
import xml.booking.model.Reservation;
import xml.booking.model.User;
import xml.booking.repositories.AccommodationRepository;
import xml.booking.repositories.CommentRepository;
import xml.booking.repositories.ReservationRepository;
import xml.booking.repositories.UserRepository;

@RestController
@RequestMapping(value = "/comments",  produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentsController {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	//objavi/ne objavi komenta
	@RequestMapping(value = "/approve/{id}/{approve}", method = RequestMethod.POST)
	public ResponseEntity<?> approveComment(@PathVariable("id") Long id, @PathVariable("approve") Boolean approve){
		
		Comment comment = commentRepository.findById(id).orElse(null);
		if(comment.getCommentState().toUpperCase().equals(CommentState.NOT_REVIEWED.toString())) {
			if(approve) {
				comment.setCommentState(CommentState.PUBLISHED.toString());
			}
			else {
				comment.setCommentState(CommentState.UNPUBLISHED.toString());
			}
			
			commentRepository.save(comment);
			return ResponseEntity.ok("Comment has been " + (approve? "un" : "") + "published");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This comment has already been reviewed");
	}
	
	/**
	 * Vraca komentare ulogovanog korisnika
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getComments(Pageable pageable, Principal principal){
		User user = getLoggedUser(principal);
		
		Page<Comment> comments = commentRepository.findByUserEmail(user.getEmail(), pageable);
		
		
		return ResponseEntity.ok(comments);
	}
	
	
	
	/**
	 * Vraca komentare za odredjeni smestaj, u zavisnosti od uloge zavisi i sta ce videti
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCommentByAccommodationId(@PathVariable("accommodation_id") Long accommodation_id){
		return null;
	}

	/**
	 * Vraca sve komentare u bazi, u zavisnosti od uloge i koje vrste su podrzane za ulogu
	 */
	
	/**
	 * Vraca komentare na jedan smestaj
	 */
	
	

	
	public User getLoggedUser(Principal principal) {
		User user = userRepository.findByEmail(principal.toString()).orElse(null);
		
		return user;
	}
}
