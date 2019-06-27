	package xml.booking.auth.controllers;


import java.util.Arrays;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import xml.booking.auth.dto.UserDTO;
import xml.booking.auth.model.Role;
import xml.booking.auth.model.RoleType;
import xml.booking.auth.model.User;
import xml.booking.auth.repositories.RoleRepository;
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
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> registration(@RequestBody UserDTO userDTO){
		
		//prvo proveri da li ima ulogu
		Role role = roleRepository.findByRole(userDTO.getUserType().toUpperCase());
		if(role == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This role does not exist");
		}
		//pa proveri da li ta uloga zahteva pib broj, tj da li je to agent
		if(userDTO.getUserType().toUpperCase().equals(RoleType.AGENT.toString())
				&& (userDTO.getPib() == null || userDTO.getPib().equals(""))) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role of type Agent needs  to have PIB number");
		}
		
		
		
		//zatim proveri da li je ulogovan
		try {
		userService.loadUserByUsername(userDTO.getUsername());
		}
		catch(UsernameNotFoundException exception) {
			User user = new User();
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setAddress(userDTO.getAddress());
			user.setEmail(userDTO.getUsername());			
			user.setPassword(encoder.encode(userDTO.getPassword()));
			user.setPib(userDTO.getPib());
			user.setUserType(userDTO.getUserType());
			user.setRoles(new HashSet<Role>(Arrays.asList(role)));
			
			userRepository.save(user);
			
			return ResponseEntity.status(HttpStatus.CREATED).body("Registered");
		}
		
		
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Already exists with same username " + userDTO.getUsername());

	}
	
	
	
	/**
	 * Update
	 * @param userDTO
	 * @return
	 */
	//TODO: promena email-a moze da utice na promene u nacinu rada posto se na osnovu njega podesava token
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(Authentication authentication, @RequestBody UserDTO userDTO){
		//probaj da nadjes updateovanog usera
		User user = null;
		String username = (String) authentication.getPrincipal();
		try {
			userService.loadUserByUsername(username);
			user = userRepository.findByEmail(username);
		}
		catch(UsernameNotFoundException exception) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with that email " + userDTO.getUsername() + " does not exist in our system");
		}
		
		//probaj da proveris passworde korisnika
		//ako je password prazan ili null, teraj dalje
		if(userDTO.getPassword() != null && !userDTO.getPassword().equals("")) {
			//ako password nije prazan, onda proveri old password da li je isti kao 
			//i onaj u bazi sa BCrypt encoder-om (bitno da old password ne bude null)
			if(userDTO.getPassword().length() > 0) {
				Boolean isEqual = encoder.matches(userDTO.getOldPassword(), user.getPassword());
				if(!isEqual)
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password does not match password in our database");
			}
			//proveri da li je novi password kratak
			if(userDTO.getPassword().length() < 4) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This password is too short, needs to be longer than 4 characters");
			}
		}
		
		
		//konacno, updateuj nadjenog korisnika  i snimi ga
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setPib(userDTO.getPib());
		if(userDTO.getUsername().equals(user.getEmail()) == false) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is unique and cannot be changed");
		}
		user.setAddress(userDTO.getAddress());
	
		userRepository.save(user);
		
		return ResponseEntity.ok("Successfully changed user data");
	}	
	
	@RequestMapping(value = "/whoami", method = RequestMethod.GET)
	public ResponseEntity<?> whoami(Authentication authentication){
		
		if(authentication instanceof AnonymousAuthenticationToken)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user");
		
		String username = (String) authentication.getPrincipal(); 
		
		User user = userRepository.findByEmail(username);
		UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getEmail(), "", "",
										user.getFirstName(), 
										user.getLastName(), 
										user.getUserType(), 
										user.getAddress(), 
										user.getPib());
		
		return ResponseEntity.ok(userDTO);
	}
	
}
