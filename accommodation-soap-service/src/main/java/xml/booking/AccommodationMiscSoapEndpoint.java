package xml.booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import xml.booking.accommodationsoap.GetAccommodationCategoriesResponse;
import xml.booking.accommodationsoap.GetAccommodationTypesResponse;
import xml.booking.accommodationsoap.GetAdditionalServicesResponse;
import xml.booking.model.AccommodationCategory;
import xml.booking.model.AccommodationType;
import xml.booking.model.AdditionalService;
import xml.booking.repositories.AccommodationCategoryRepository;
import xml.booking.repositories.AccommodationTypeRepository;
import xml.booking.repositories.AdditionalServiceRepository;

@Endpoint
public class AccommodationMiscSoapEndpoint {

	private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/tim1/accommodationsoap";

	@Autowired
	private AccommodationCategoryRepository accommodationCategoryRepository;
	
	@Autowired
	private AccommodationTypeRepository accommodationTypeRepository;
	
	@Autowired
	private AdditionalServiceRepository additionalServiceRepository;
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAccommodationTypesRequest")
	@ResponsePayload
	public GetAccommodationTypesResponse getAccommodationTypesRequest() {
		GetAccommodationTypesResponse getAccommodationTypesResponse = new GetAccommodationTypesResponse();
		List<AccommodationType> accommodationTypes = accommodationTypeRepository.findAll();
		
		getAccommodationTypesResponse.setAccommodationTypes(accommodationTypes);
		
		return getAccommodationTypesResponse;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAccommodationCategoriesRequest")
	@ResponsePayload
	public GetAccommodationCategoriesResponse getAccommodationCategoriesRequest() {
		GetAccommodationCategoriesResponse getAccommodationCategoriesResponse = new GetAccommodationCategoriesResponse();
		
		List<AccommodationCategory> accommodationCategories = accommodationCategoryRepository.findAll();
		
		getAccommodationCategoriesResponse.setAccommodationCategories(accommodationCategories);
		
		return getAccommodationCategoriesResponse;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAdditionalServicesRequest")
	@ResponsePayload
	public GetAdditionalServicesResponse getAccommodationCategoriesResponse() {
		GetAdditionalServicesResponse getAdditionalServicesResponse = new GetAdditionalServicesResponse();
		List<AdditionalService> additionalServices = additionalServiceRepository.findAll();
		
		getAdditionalServicesResponse.setAdditionalServices(additionalServices);
		
		return getAdditionalServicesResponse;
	}
}
