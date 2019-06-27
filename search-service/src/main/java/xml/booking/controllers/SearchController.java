package xml.booking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping( value = "/", produces = MediaType.APPLICATION_JSON_VALUE )
public class SearchController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AccommodationUnitManager accommodationUnitManager;
	

	@GetMapping("/")
	public  ResponseEntity<?> regularSearch(@RequestParam String location, @RequestParam Long beginningDate, @RequestParam Long endDate, @RequestParam Integer numberOfPersons, @RequestParam(defaultValue = "0") int page)
	{
		System.out.println("REGULAR SEARCH");
		
		//ukoliko nema neki od obaveznih parametara
		if(location.isEmpty() || location == null || beginningDate == null || endDate == null || numberOfPersons == null) {
			
			return ResponseEntity.badRequest().build();
		}
		
		Page<AccommodationUnitDTO> accList = accommodationUnitManager.regularSearch(location, beginningDate, endDate, numberOfPersons, PageRequest.of(page, 10));
		
		return ResponseEntity.ok(accList);		
	}
	
	@PostMapping("/")
	public  ResponseEntity<?> advancedSearch(@RequestBody SearchDTO searchObject, @RequestParam(defaultValue = "0") int page)
	{
		System.out.println("ADVANCED SEARCH");
		
		//ukoliko nema neki od obaveznih parametara
		if(searchObject.getLocation().isEmpty() || searchObject.getLocation() == null 
				|| searchObject.getBeginningDate() == null || searchObject.getEndDate() == null || searchObject.getNumberOfPersons() == null
				|| (searchObject.getDistance() != null && (searchObject.getUserLatitude() == null || searchObject.getUserLongitude() == null))) {
			
			return ResponseEntity.badRequest().build();
		}
		
		Page<AccommodationUnitDTO> accList = accommodationUnitManager.advancedSearch(searchObject, PageRequest.of(page, 10));
			
		return ResponseEntity.ok(accList);
	}
}
