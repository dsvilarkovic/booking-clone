package xml.booking.controllers;

import java.util.Arrays;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import xml.booking.dto.AccommodationUnitDTO;
import xml.booking.dto.SearchDTO;
import xml.booking.managers.AccommodationUnitManager;
import xml.booking.model.AccommodationCategory;
import xml.booking.model.AccommodationType;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*", maxAge = 3600)
@RequestMapping( value = "/", produces = MediaType.APPLICATION_JSON_VALUE )
public class SearchController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AccommodationUnitManager accommodationUnitManager;
	

	@GetMapping("/")
	public  ResponseEntity<?> normalSearch(@RequestParam String location, @RequestParam @DateTimeFormat(pattern = "yyy-MM-dd") Date beginningDate, @RequestParam @DateTimeFormat(pattern = "yyy-MM-dd")  Date endDate, @RequestParam Integer numberOfPersons, @RequestParam(defaultValue = "0") int page)
	{
		System.out.println("NORMAL SEARCH");
		
		if(location.isEmpty() || location == null || beginningDate == null || endDate == null || numberOfPersons == null) {
			
			return ResponseEntity.badRequest().build();
		}
		
		Page<AccommodationUnitDTO> accList = accommodationUnitManager.normalSearch(location, beginningDate, endDate, numberOfPersons, PageRequest.of(page, 10));
		
		return ResponseEntity.ok(accList);
		
	}
	
	@PostMapping("/")
	public  ResponseEntity<?> advancedSearch(@RequestBody SearchDTO searchObject, @RequestParam(defaultValue = "0") int page)
	{
		/*
		//proba rest template
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity <String> entity = new HttpEntity<String>(headers);	
	    
	    String codebookUrl = "http://localhost:8762/api/maintenanceOfCodeBook"; 
		
	    AccommodationType type = restTemplate.getForObject(codebookUrl+"/accommodationType/"+searchObject.getAccommodationType(), 
	    		AccommodationType.class);

	    AccommodationCategory category = restTemplate.getForObject(codebookUrl+"/accommodationCategory/"+searchObject.getAccommodationCategory(), 
	    		AccommodationCategory.class);
	    */
		
		Page<AccommodationUnitDTO> accList = accommodationUnitManager.advancedSearch(searchObject, PageRequest.of(page, 10));
		
		
		return ResponseEntity.ok(null);
	}
}
