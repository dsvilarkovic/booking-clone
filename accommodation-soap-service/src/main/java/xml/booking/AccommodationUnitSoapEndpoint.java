package xml.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import xml.booking.accommodationsoap.CreateAccommodationUnitRequest;
import xml.booking.accommodationsoap.CreateAccommodationUnitResponse;
import xml.booking.accommodationsoap.DeleteAccommodationUnitRequest;
import xml.booking.accommodationsoap.DeleteAccommodationUnitResponse;
import xml.booking.accommodationsoap.GetAccommodationUnitRequest;
import xml.booking.accommodationsoap.GetAccommodationUnitResponse;
import xml.booking.accommodationsoap.UpdateAccommodationUnitRequest;
import xml.booking.accommodationsoap.UpdateAccommodationUnitResponse;
import xml.booking.model.AccommodationUnit;
import xml.booking.model.Day;
import xml.booking.repositories.AccommodationUnitRepository;
import xml.booking.repositories.DayRepository;

@Endpoint
public class AccommodationUnitSoapEndpoint {
	private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/tim1/accommodationsoap";

	
	@Autowired
	private AccommodationUnitRepository accommodationUnitRepository;
	
	@Autowired
	private DayRepository dayRepository;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAccommodationUnitRequest")
	@ResponsePayload
	public GetAccommodationUnitResponse getAccommodationUnitRequest(
			@RequestPayload GetAccommodationUnitRequest getAccommodationUnitRequest) {
		GetAccommodationUnitResponse getAccommodationUnitResponse = new GetAccommodationUnitResponse();
		AccommodationUnit accommodationUnit = accommodationUnitRepository.
				findById(getAccommodationUnitRequest.getAccommodationUnitId()).orElse(null);
		
		getAccommodationUnitResponse.setAccommodationUnit(accommodationUnit);
		
		return getAccommodationUnitResponse;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAccommodationUnitRequest")
	@ResponsePayload
	//TODO: za svaki accommodation unit mora da se zalepi za accommodation pri create, al proveriti
	public CreateAccommodationUnitResponse createAccommodationUnitRequest(
			@RequestPayload CreateAccommodationUnitRequest createAccommodationUnitRequest) {
		CreateAccommodationUnitResponse createAccommodationUnitResponse = new CreateAccommodationUnitResponse();
		
		AccommodationUnit accommodationUnit = createAccommodationUnitRequest.getAccommodationUnit();
		
		//snimi sve dane
		for (Day day : accommodationUnit.getDay()) {
			dayRepository.save(day);
		}
		accommodationUnit =  accommodationUnitRepository.save(createAccommodationUnitRequest.getAccommodationUnit());
		createAccommodationUnitResponse.setAccommodationUnitId(accommodationUnit.getId());
		
		
		return createAccommodationUnitResponse;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAccommodationUnitRequest")
	@ResponsePayload
	public DeleteAccommodationUnitResponse deleteAccommodationUnitRequest(
			@RequestPayload DeleteAccommodationUnitRequest deleteAccommodationUnitRequest) {
		DeleteAccommodationUnitResponse deleteAccommodationUnitResponse = new DeleteAccommodationUnitResponse();
		
		accommodationUnitRepository.deleteById(deleteAccommodationUnitRequest.getAccommodationUnitId());
		deleteAccommodationUnitResponse.setSuccess(true);
		
		return deleteAccommodationUnitResponse;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateAccommodationUnitRequest")
	@ResponsePayload
	public UpdateAccommodationUnitResponse updateAccommodationUnitRequest(
			@RequestPayload UpdateAccommodationUnitRequest updateAccommodationUnitRequest) {
		UpdateAccommodationUnitResponse updateAccommodationUnitResponse = new UpdateAccommodationUnitResponse();
		
		AccommodationUnit accommodationUnit = updateAccommodationUnitRequest.getAccommodationUnit();
		//snimi sve dane, mozda neki ne postoji
		for (Day day : accommodationUnit.getDay()) {
			dayRepository.save(day);
		}
		
		accommodationUnit = accommodationUnitRepository.save(accommodationUnit);
		
		updateAccommodationUnitResponse.setSuccess(true);
		
		return updateAccommodationUnitResponse;
	}
	
	
}
