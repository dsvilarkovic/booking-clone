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
import xml.booking.repositories.AccommodationUnitRepository;

@Endpoint
public class AccommodationUnitSoapEndpoint {
	private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/tim1/accommodationsoap";

	
	@Autowired
	private AccommodationUnitRepository accommodationUnitRepository;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAccommodationUnitRequest")
	@ResponsePayload
	public GetAccommodationUnitResponse getAccommodationUnitRequest(
			@RequestPayload GetAccommodationUnitRequest getAccommodationUnitRequest) {
		GetAccommodationUnitResponse getAccommodationUnitResponse = new GetAccommodationUnitResponse();
		AccommodationUnit accommodationUnit = accommodationUnitRepository.getOne(getAccommodationUnitRequest.getAccommodationUnitId());
		
		getAccommodationUnitResponse.setAccommodationUnit(accommodationUnit);
		
		return getAccommodationUnitResponse;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAccommodationUnitRequest")
	@ResponsePayload
	public CreateAccommodationUnitResponse createAccommodationUnitRequest(
			@RequestPayload CreateAccommodationUnitRequest createAccommodationUnitRequest) {
		CreateAccommodationUnitResponse createAccommodationUnitResponse = new CreateAccommodationUnitResponse();
		
		
		AccommodationUnit accommodationUnit =  accommodationUnitRepository.save(createAccommodationUnitRequest.getAccommodationUnit());
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
		
		accommodationUnit = accommodationUnitRepository.save(accommodationUnit);
		
		updateAccommodationUnitResponse.setSuccess(true);
		
		return updateAccommodationUnitResponse;
	}
	
	
}
