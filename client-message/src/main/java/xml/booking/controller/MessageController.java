package xml.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xml.booking.dto.MessageDTO;
import xml.booking.managers.MessageManager;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*", maxAge = 3600)
@RequestMapping( value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE )
public class MessageController {
	
	@Autowired
	private MessageManager messageManager;
	
	@GetMapping("")
	public  ResponseEntity<?> getAllMessages()
	{
		return ResponseEntity.ok(messageManager.getAllMessages());
	}
	
	@GetMapping("/{messageId}")
	public  ResponseEntity<?> getMessageById(@PathVariable Long messageId)
	{
		 return null;
	}
	
	@PostMapping("")
	public ResponseEntity<?> saveMessage(@RequestBody MessageDTO messageDTO)
	{
		return null;
	}
	
}
