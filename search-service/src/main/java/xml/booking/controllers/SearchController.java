package xml.booking.controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import xml.booking.dto.SearchDTO;
import xml.booking.model.AccommodationCategory;
import xml.booking.model.AccommodationType;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*", maxAge = 3600)
@RequestMapping( value = "/", produces = MediaType.APPLICATION_JSON_VALUE )
public class SearchController {
	
	@Autowired
	private RestTemplate restTemplate;
	

	@GetMapping("/")
	public  ResponseEntity<?> normalSearch(@RequestParam String location, @RequestParam Long beginningDate, @RequestParam Long endDate, @RequestParam Integer numberOfPersons)
	{
		
		 return ResponseEntity.ok(null);
	}
	
	@PostMapping("/")
	public  ResponseEntity<?> advancedSearch(@RequestBody SearchDTO searchObject)
	{
		//proba rest template
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity <String> entity = new HttpEntity<String>(headers);	
	    
	    String codebookUrl = "http://localhost:8762/api/maintenanceOfCodeBook"; 
		
	    AccommodationType type = restTemplate.getForObject(codebookUrl+"/accommodationType/"+searchObject.getAccommodationType(), 
	    		AccommodationType.class);

	    AccommodationCategory category = restTemplate.getForObject(codebookUrl+"/accommodationCategory/"+searchObject.getAccommodationCategory(), 
	    		AccommodationCategory.class);
		
		 return ResponseEntity.ok(null);
	}
}