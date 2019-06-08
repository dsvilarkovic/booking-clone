package xml.booking.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import xml.booking.dto.UserDTO;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*", maxAge = 3600)
@RequestMapping( value = "/", produces = MediaType.APPLICATION_JSON_VALUE )
public class UserController {
	
	@GetMapping("/admin")
	public ResponseEntity<?> getAdmins()
	{
		 return ResponseEntity.ok(null);
	}
	
	@GetMapping("/agent")
	public ResponseEntity<?> getAgents()
	{
		 return ResponseEntity.ok(null);
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
