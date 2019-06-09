package xml.booking.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import xml.booking.dto.MessageDTO;
import xml.booking.managers.MessageManager;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

	@Autowired
	private MessageManager messageManager;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("")
	public ResponseEntity<?> getAllMessages() {
		// proba rest template
		/*HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		String response=""; 
		try {
			response = restTemplate.getForObject("http://localhost:8762/api/reservations/reservation/" + 1,String.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(response);*/
		return ResponseEntity.ok(messageManager.getAllMessages());
	}

	@GetMapping("/{messageId}")
	public ResponseEntity<?> getMessageById(@PathVariable Long messageId) {
		return null;
	}

	@PostMapping("")
	public ResponseEntity<?> saveMessage(@RequestBody MessageDTO messageDTO) {

		return null;
	}

}
