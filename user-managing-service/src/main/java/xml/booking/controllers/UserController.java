package xml.booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xml.booking.dto.RegistrationDTO;
import xml.booking.dto.UserDTO;
import xml.booking.feign.UserProxy;
import xml.booking.managers.UserManager;
import xml.booking.model.User;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*", maxAge = 3600)
@RequestMapping( value = "/", produces = MediaType.APPLICATION_JSON_VALUE )
public class UserController {
	
	@Autowired
	public UserManager userManager;
	
	@Autowired
	private UserProxy userProxy;	
	
	@GetMapping("/{userType}")
	public ResponseEntity<?> getAdmins(@PathVariable String userType, @RequestParam(defaultValue = "0") int page)
	{		
		//TODO: samo administrator moze da pristupi
		System.out.println("GETTING " + userType.toUpperCase());
				
		Page<UserDTO> users = userManager.findAllUsersByTypeAndDeleted(userType, PageRequest.of(page, 10));
		
	    return ResponseEntity.ok(users);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<?> blockOrActivateUser(@PathVariable Long userId)
	{
		//TODO: samo administrator moze da pristupi
		System.out.println("BLOCKING/ACTIVATING USER");
		 
		User user = userManager.findUserByIdAndDeleted(userId);
		 
		//ukoliko korisnik sa tim id-om ne postoji
		if(user == null) {
			 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with this id does not exist.");
		}
		//ukoliko korisnik sa tim id-om nije obican korisnik
		else if(!user.getUserType().equals("registered")) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not a regular user.");
		}
		 		 
		//blokiranje/aktiviranje korisnika
		user.setActivated(user.isActivated() ? false : true);
		 
		userManager.updateUser(user);
		 
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/{userType}")
	public ResponseEntity<?> createAdminOrAgent(@PathVariable String userType, @RequestBody UserDTO userDTO)
	{
		//TODO: samo administrator moze da pristupi
		System.out.println("CREATING " + userType.toUpperCase());
		
		//ukoliko fali neko od obaveznih polja
		if(userDTO.getFirstName() == null || userDTO.getFirstName().isEmpty() || userDTO.getLastName() == null || userDTO.getLastName().isEmpty() ||
				userDTO.getEmail() == null || userDTO.getEmail().isEmpty() || 
				(userType.equals("agent") && (userDTO.getPib() == null || userDTO.getAddress() == null || userDTO.getAddress().isEmpty()))) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required fields are missing.");
		}
		
		//ukoliko vec postoji korisnik sa tom email adresom
		User checkUser = userManager.findUserByEmailAndDeleted(userDTO.getEmail());
		if(checkUser != null) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("A user with this email address already exists");
		}
		
		if(userType.equals("agent")) {
			
			RegistrationDTO regDTO = new RegistrationDTO(userDTO, "agent", "agent-password", userDTO.getPib());
			
			
			ResponseEntity<String> response = userProxy.registerAdminOrAgent(regDTO);
			
			if(response.getStatusCode() == HttpStatus.CREATED) {
				return ResponseEntity.ok().build();
			}
			else {
				return response;
			}
		}
		else if(userType.equals("admin")) {
			
			RegistrationDTO regDTO = new RegistrationDTO(userDTO, "admin", "admin-password", null);
			regDTO.setAddress("");
			
			ResponseEntity<String> response = userProxy.registerAdminOrAgent(regDTO);
			
			if(response.getStatusCode() == HttpStatus.CREATED) {
				return ResponseEntity.ok().build();
			}
			else {
				return response;
			}
			
		}
		else {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only admins and agents can be created.");
		}
		
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Long userId)
	{
		//TODO: samo administrator moze da pristupi
		System.out.println("DELETING USER");
		 
		User user = userManager.findUserByIdAndDeleted(userId);
		 
		//ukoliko korisnik sa tim id-om ne postoji
		if(user == null) {
			 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with this id does not exist.");
		}
		//ukoliko korisnik sa tim id-om nije obican korisnik
		else if(!user.getUserType().equals("registered")) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not a regular user.");
		}
		 		 
		//brisanje korisnika
		user.setDeleted(true);
				 
		User u = userManager.updateUser(user);
				
		return ResponseEntity.ok().build();
	}
	
	
}
