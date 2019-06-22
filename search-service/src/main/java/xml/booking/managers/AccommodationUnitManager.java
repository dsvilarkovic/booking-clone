package xml.booking.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import xml.booking.dto.AccommodationUnitDTO;
import xml.booking.dto.CodeBookDTO;
import xml.booking.dto.SearchDTO;
import xml.booking.model.Accommodation;
import xml.booking.model.AccommodationCategory;
import xml.booking.model.AccommodationType;
import xml.booking.model.AccommodationUnit;
import xml.booking.model.AdditionalService;
import xml.booking.repositories.AccommodationCategoryRepository;
import xml.booking.repositories.AccommodationRepository;
import xml.booking.repositories.AccommodationTypeRepository;
import xml.booking.repositories.AccommodationUnitRepository;

/**
* Generated by Spring Data Generator on 18/05/2019
*/
@Component
public class AccommodationUnitManager {

	@Autowired
	private AccommodationUnitRepository accommodationUnitRepository;
	
	@Autowired
	private AccommodationTypeRepository accommodationTypeRepository;
	
	@Autowired
	private AccommodationCategoryRepository accommodationCategoryRepository;
	
	@Autowired
	private AccommodationRepository accommodationRepository;

	public Page<AccommodationUnitDTO> regularSearch(String location, Long beginningDate, Long endDate, Integer numberOfPersons, Pageable page) {
		
		return mapToDTO(accommodationUnitRepository.regularSearch(page, location, numberOfPersons, beginningDate, endDate, false));		
	}
	
	public Page<AccommodationUnitDTO> advancedSearch(SearchDTO searchObject, Pageable page) {
		
		AccommodationType type = null;
		if(searchObject.getAccommodationType() != null) {
			type = new AccommodationType(searchObject.getAccommodationType());
		}
		
		AccommodationCategory category = null;
		if(searchObject.getAccommodationCategory() != null) {
			category = new AccommodationCategory(searchObject.getAccommodationCategory());
		}
		
		List<AdditionalService> services = null;
		if(searchObject.getAdditionalServices() != null && !searchObject.getAdditionalServices().isEmpty()) {
			services = new ArrayList<AdditionalService>();
			for(CodeBookDTO dto : searchObject.getAdditionalServices()) {
				services.add(new AdditionalService(dto));
			}
		}
		
		return mapToDTO(accommodationUnitRepository.advancedSearch(page, searchObject.getLocation(), searchObject.getNumberOfPersons(), 
				                                                   searchObject.getBeginningDate(), searchObject.getEndDate(), false, 
				                                                   type, category, services));		
	}
		
	
	private Page<AccommodationUnitDTO> mapToDTO(Page<AccommodationUnit> accommodationUnits){
		
		Page<AccommodationUnitDTO> dtos = accommodationUnits.map(new Function<AccommodationUnit, AccommodationUnitDTO>() {
		    @Override
		    public AccommodationUnitDTO apply(AccommodationUnit accommodationUnit) {		    	
		    	
		    	Accommodation accommodation = accommodationRepository.findAccommodationByAccommodationUnitId(accommodationUnit.getId());		    	
		    	AccommodationCategory category = accommodationCategoryRepository.findCategoryByAccommodationUnitId(accommodationUnit.getId());		    	
		    	AccommodationType type = accommodationTypeRepository.findTypeByAccommodationUnitId(accommodationUnit.getId());
		    	
		    	AccommodationUnitDTO accommodationUnitDTO = new AccommodationUnitDTO(accommodationUnit, 0.0, accommodation.getName(), 
		    			                                                             category.getName(), type.getName(), accommodation.getDescription());
		    	
		   		return accommodationUnitDTO;
		    }
		});
		
		return dtos;		
	}

}
