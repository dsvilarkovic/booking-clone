package xml.booking.comments.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import xml.booking.comments.model.Accommodation;
import xml.booking.comments.model.AccommodationUnit;
import xml.booking.comments.model.Comment;
import xml.booking.comments.model.CommentState;
import xml.booking.comments.model.Reservation;
import xml.booking.comments.model.User;
import xml.booking.comments.repositories.AccommodationRepository;
import xml.booking.comments.repositories.CommentRepository;
import xml.booking.comments.repositories.ReservationRepository;

@RestController
@RequestMapping(value = "/comments",  produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentsController {

	// Create a bean for restTemplate to call services
	@Bean
	@LoadBalanced		// Load balance between service instances running at different ports.
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

	@Autowired
	RestTemplate restTemplate;

	
	@Autowired
	private CommentRepository commentRepository;
	
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	//TODO: pozivati mikroservis za smestaje
	@Autowired
	private AccommodationRepository accommodationRepository;
	
	//objavi/ne objavi komenta
	@RequestMapping(value = "/approve/{id}/{approve}", method = RequestMethod.PUT, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> approveComment(@PathVariable("id") Long id, @PathVariable("approve") String approve){
		
		Comment comment = commentRepository.findById(id).orElse(null);
		if(approve.equals("true")) {
			comment.setCommentState(CommentState.PUBLISHED.toString());
		}
		else {
			comment.setCommentState(CommentState.UNPUBLISHED.toString());
		}
		
		commentRepository.save(comment);
		return ResponseEntity.ok("Comment has been " + (approve.equals("true")? "" : "un") + "published");
	}
	
	/**
	 * Vraca komentare ulogovanog korisnika
	 * @return
	 */
	@RequestMapping(value = "/logged", method = RequestMethod.GET)
	public ResponseEntity<?> getComments(Pageable pageable, @RequestHeader("Authorization") String authorization){
		User user = getLoggedUser(authorization);
		
		Page<Comment> comments = commentRepository.findByUserEmail(user.getEmail(), pageable);
		
		for (Comment comment : comments) {
			User newUser = new User();
			newUser.setEmail(user.getEmail());
			comment.setUser(newUser);
		}
		return ResponseEntity.ok(comments);
	}
	
	
	
	/**
	 * Vraca komentare za odredjeni smestaj, u zavisnosti od uloge zavisi i sta ce videti
	 */
	@RequestMapping(value = "/accommodation/{accommodation_id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCommentByAccommodationId(@PathVariable("accommodation_id") Long accommodation_id){
		//TODO: ovo da se poziva iz feign clienta
		//TODO: vracaj pageable
		Accommodation accommodation = accommodationRepository.findById(accommodation_id).orElse(null);
		
		List<AccommodationUnit> accommodationUnits = accommodation.getAccommodationUnit();
		List<Comment> comments = new ArrayList<Comment>();
		
		for (AccommodationUnit accommodationUnit : accommodationUnits) {
			Long accommodationUnitId = accommodationUnit.getId();
			List<Reservation> reservations = reservationRepository.findByAccommodationUnitId(accommodationUnitId);
			
			for (Reservation reservation : reservations) {
				if(reservation.getComment() != null )
					comments.add(reservation.getComment());	
			}
		
		}
		
		for (Comment comment : comments) {
			User newUser = new User();
			newUser.setEmail(comment.getUser().getEmail());
			comment.setUser(newUser);
		}
		return ResponseEntity.ok(comments);
	}

	/**
	 * Vraca sve komentare u bazi, za admin review
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getAllComments(Pageable pageable){
		//User user = getLoggedUser(authorization);
		
		
		Page<Comment> comments = commentRepository.findAll(pageable);
		for (Comment comment : comments) {
			User newUser = new User();
			User user = comment.getUser();
			newUser.setEmail(user.getEmail());
			comment.setUser(newUser);
		}
		return ResponseEntity.ok(comments);
	}
	
	

//	private static final String AUTH_URL = "http://localhost:8762/api/users";
	private static final String AUTH_URL = "http://auth-service/users";


	public User getLoggedUser(String authorization) {
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authorization);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<User> response = restTemplate.exchange(AUTH_URL + "/whoami", HttpMethod.GET, entity, User.class);
		User user = response.getBody();
		
		return user;
	}
}
