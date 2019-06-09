package xml.booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import xml.booking.dto.UserDTO;
import xml.booking.managers.UserManager;
import xml.booking.model.User;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*", maxAge = 3600)
@RequestMapping( value = "/", produces = MediaType.APPLICATION_JSON_VALUE )
public class UserController {
	
	@Autowired
	public UserManager userManager;
	
	@GetMapping("/admin")
	public ResponseEntity<?> getAdmins(@RequestParam(defaultValue = "0") int page)
	{
		System.out.println("GETTING ADMINISTRATORS");
		
		Page<UserDTO> admins = userManager.findAllUsersByType("admin", PageRequest.of(page, 10));
		
	    return ResponseEntity.ok(admins);
	}
	
	@GetMapping("/agent")
	public ResponseEntity<?> getAgents(@RequestParam(defaultValue = "0") int page)
	{
		System.out.println("GETTING AGENTS");
	
		Page<UserDTO> agents = userManager.findAllUsersByType("agent", PageRequest.of(page, 10));
		
		return ResponseEntity.ok(agents);
	}
	
	@GetMapping("/user")
	public ResponseEntity<?> getUsers(@RequestParam(defaultValue = "0") int page)
	{
		System.out.println("GETTING REGULAR USERS");

		Page<UserDTO> users = userManager.findAllUsersByType("registered", PageRequest.of(page, 10));
		
		return ResponseEntity.ok(users);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<?> blockOrActivateUser(@PathVariable Long userId)
	{
		 return ResponseEntity.ok(null);
	}
	
	@PostMapping("/")
	public ResponseEntity<?> createAdminOrAgent(@RequestBody UserDTO user)
	{
		 return ResponseEntity.ok(null);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Long userId)
	{
		 return ResponseEntity.ok(null);
	}
	

	
	
}
