package xml.booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import xml.booking.accommodationsoap.CreateAccommodationRequest;
import xml.booking.accommodationsoap.CreateAccommodationResponse;
import xml.booking.accommodationsoap.CreateImageRequest;
import xml.booking.accommodationsoap.CreateImageResponse;
import xml.booking.accommodationsoap.DeleteAccommodationRequest;
import xml.booking.accommodationsoap.DeleteAccommodationResponse;
import xml.booking.accommodationsoap.DeleteImageRequest;
import xml.booking.accommodationsoap.DeleteImageResponse;
import xml.booking.accommodationsoap.GetAccommodationImagesRequest;
import xml.booking.accommodationsoap.GetAccommodationImagesResponse;
import xml.booking.accommodationsoap.GetAccommodationRequest;
import xml.booking.accommodationsoap.GetAccommodationResponse;
import xml.booking.accommodationsoap.GetAccommodationsResponse;
import xml.booking.accommodationsoap.UpdateAccommodationRequest;
import xml.booking.accommodationsoap.UpdateAccommodationResponse;
import xml.booking.model.Accommodation;
import xml.booking.model.Image;
import xml.booking.repositories.AccommodationRepository;
import xml.booking.repositories.ImageRepository;

@Endpoint
public class AccommodationSoapEndpoint {
	private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/tim1/accommodationsoap";
	
	@Autowired
	private AccommodationRepository accommodationRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	

	/**
	 * Vraca trazeni smestaj po id-u
	 * @return
	 */
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAccommodationRequest")
	@ResponsePayload
	public GetAccommodationResponse getAccommodationRequest(
			@RequestPayload GetAccommodationRequest getAccommodationRequest) {
		GetAccommodationResponse getAccommodationResponse = new GetAccommodationResponse();
		Accommodation accommodation = accommodationRepository.getOne(getAccommodationRequest.getAccommodationId());
		
		getAccommodationResponse.setAccommodation(accommodation);
		
		return getAccommodationResponse;
	}
	/**
	 * Vraca listu smestaja
	 * @return
	 */	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAccommodationsRequest")
	@ResponsePayload
	public GetAccommodationsResponse getAccommodationsRequest() {
		GetAccommodationsResponse getAccommodationsResponse = new GetAccommodationsResponse();
			
		//TODO: treba vratiti samo one za koje je trenutni agent zaduzen kao vlasnik
		//potrebno logovanje
		List<Accommodation> accommodations = accommodationRepository.findAll();
		
		getAccommodationsResponse.setAccommodation(accommodations);
		
		return getAccommodationsResponse;		
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAccommodationRequest")
	@ResponsePayload
	public CreateAccommodationResponse createAccommodationRequest(
			@RequestPayload CreateAccommodationRequest createAccommodationRequest) {
		CreateAccommodationResponse createAccommodationResponse = new CreateAccommodationResponse();
		
		Accommodation accommodation = accommodationRepository.save(createAccommodationRequest.getAccommodation());
		createAccommodationResponse.setAccommodationId(accommodation.getId());
		
		return createAccommodationResponse;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAccommodationRequest")
	@ResponsePayload
	public DeleteAccommodationResponse deleteAccommodationRequest
			(@RequestPayload DeleteAccommodationRequest deleteAccommodationRequest) {
		DeleteAccommodationResponse deleteAccommodationResponse = new DeleteAccommodationResponse();
		Accommodation accommodation = accommodationRepository.getOne(deleteAccommodationRequest.getAccommodationId());
		
		accommodationRepository.deleteById(accommodation.getId());
		
		return deleteAccommodationResponse;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateAccommodationRequest")
	@ResponsePayload
	public UpdateAccommodationResponse updateAccommodationRequest(
			@RequestPayload UpdateAccommodationRequest updateAccommodationRequest) {
		UpdateAccommodationResponse updateAccommodationResponse = new UpdateAccommodationResponse();
		accommodationRepository.getOne(updateAccommodationRequest.getAccommodation().getId());
		accommodationRepository.save(updateAccommodationRequest.getAccommodation());
		
		updateAccommodationResponse.setSuccess(true);
		
		return updateAccommodationResponse;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAccommodationImagesRequest")
	@ResponsePayload
	public GetAccommodationImagesResponse getAccommodationImagesRequest(
			@RequestPayload GetAccommodationImagesRequest getAccommodationImagesRequest) {
		GetAccommodationImagesResponse getAccommodationImagesResponse = new GetAccommodationImagesResponse();
		
		Accommodation accommodation = accommodationRepository.getOne(getAccommodationImagesRequest.getAccommodation().getId());
		List<Image> images =  accommodation.getImage();
		
		getAccommodationImagesResponse.setImage(images);
		
		return getAccommodationImagesResponse;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createImageRequest")
	@ResponsePayload
	public CreateImageResponse createImageRequest(
			@RequestPayload CreateImageRequest createImageRequest) {
		CreateImageResponse createImageResponse = new CreateImageResponse();
		
		Image image = imageRepository.save(createImageRequest.getImage());
		createImageResponse.setImageId(image.getId());
		
		return createImageResponse;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteImageRequest")
	@ResponsePayload
	public DeleteImageResponse deleteImageRequest(
			@RequestPayload DeleteImageRequest deleteImageRequest) {
		DeleteImageResponse deleteImageResponse = new DeleteImageResponse();
		imageRepository.deleteById(deleteImageRequest.getImageId());
		deleteImageResponse.setSuccess(true);
		
		return deleteImageResponse;
	}
	
	
	
	
	
	

}