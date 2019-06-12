package xml.booking.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import xml.booking.auth.dto.UserDTO;
import xml.booking.auth.model.User;
import xml.booking.auth.repositories.UserRepository;
import xml.booking.auth.security.UserDetailsServiceImpl;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	
	
	@Autowired
	private UserDetailsServiceImpl userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<?> registration(@RequestBody User user){
		
		try {
		userService.loadUserByUsername(user.getEmail());
		}
		catch(UsernameNotFoundException exception) {
			user.setPassword(encoder.encode(user.getPassword()));
			userRepository.save(user);
			
			return ResponseEntity.ok("Registered");
		}
		
		
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Already exists with same username " + user.getEmail());

	}
	
}
