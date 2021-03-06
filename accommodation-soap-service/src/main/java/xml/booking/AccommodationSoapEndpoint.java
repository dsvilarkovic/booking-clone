package xml.booking;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpServletConnection;


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
import xml.booking.model.AccommodationUnit;
import xml.booking.model.Image;
import xml.booking.model.Location;
import xml.booking.model.User;
import xml.booking.repositories.AccommodationRepository;
import xml.booking.repositories.AccommodationUnitRepository;
import xml.booking.repositories.ImageRepository;
import xml.booking.repositories.LocationRepository;
import xml.booking.repositories.UserRepository;

@Endpoint
@Transactional
public class AccommodationSoapEndpoint {
	//TODO:izmeni link
//	private static final String AUTH_URL = "http://localhost:9994/users";
//	private static final String AUTH_URL = "http://localhost:8762/api/users"; // service-registry + api_gateway
	private static final String AUTH_URL = "http://auth-service";

	private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/tim1/accommodationsoap";
	
	

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private AccommodationRepository accommodationRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired 
	private AccommodationUnitRepository accommodationUnitRepository; 

	protected HttpServletRequest getHttpServletRequest() {
	    TransportContext ctx = TransportContextHolder.getTransportContext();
	    return ( null != ctx ) ? ((HttpServletConnection ) ctx.getConnection()).getHttpServletRequest() : null;
	}

	protected String getHttpHeaderValue( final String headerName ) {
	    HttpServletRequest httpServletRequest = getHttpServletRequest();
	    return ( null != httpServletRequest ) ? httpServletRequest.getHeader( headerName ) : null;
	}
	
	/**
	 * Vraca trazeni smestaj po id-u
	 * @return
	 */
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAccommodationRequest")
	@ResponsePayload
	public GetAccommodationResponse getAccommodationRequest(
			@RequestPayload GetAccommodationRequest getAccommodationRequest) {
		GetAccommodationResponse getAccommodationResponse = new GetAccommodationResponse();
		
		Accommodation accommodation = accommodationRepository.findByIdAndDeleted(getAccommodationRequest.getAccommodationId(), false).orElse(null);
		
		accommodation = new Accommodation(accommodation.getId(), accommodation.getName(), accommodation.getDescription(), 
										  accommodation.getAccommodationCategory(), accommodation.getAccommodationType(), 
										  accommodation.getAdditionalService(), null, accommodation.getLocation(), 
										  accommodation.getUser(), accommodation.getAccommodationUnit(), false);
		accommodation.setUser(deletePassword(accommodation.getUser()));
		
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
		
		//nadji trenutno ulogovanog user-a na osnovu /whoami
//		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", getHttpHeaderValue("Authorization"));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<User> response = restTemplate.exchange(AUTH_URL + "/whoami", HttpMethod.GET, entity, User.class);
		User user = response.getBody();
		
		GetAccommodationsResponse getAccommodationsResponse = new GetAccommodationsResponse();
		
		
		//TODO: treba vratiti samo one za koje je trenutni agent zaduzen kao vlasnik
		//potrebno logovanje
		
		List<Accommodation> accommodations = accommodationRepository.findByUserEmailAndDeleted(user.getEmail(),false);
		for (Accommodation accommodation : accommodations) {
			accommodation = new Accommodation(accommodation.getId(), accommodation.getName(), accommodation.getDescription(), 
					  accommodation.getAccommodationCategory(), accommodation.getAccommodationType(), 
					  accommodation.getAdditionalService(), null, accommodation.getLocation(), 
					  accommodation.getUser(), accommodation.getAccommodationUnit(), false);
			accommodation.setUser(deletePassword(accommodation.getUser()));
		}
		getAccommodationsResponse.setAccommodation(accommodations);
		
		return getAccommodationsResponse;		
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAccommodationRequest")
	@ResponsePayload
	public CreateAccommodationResponse createAccommodationRequest(
			@RequestPayload CreateAccommodationRequest createAccommodationRequest) {
		CreateAccommodationResponse createAccommodationResponse = new CreateAccommodationResponse();
		
	
		Accommodation accommodation = createAccommodationRequest.getAccommodation();
		Location location = accommodation.getLocation();
		location = locationRepository.save(location);
		
		//uzmi usera iz repository i sacuvaj ga za accommodation
		User user = userRepository.findByEmail(accommodation.getUser().getEmail());
		accommodation.setUser(user);
		//sacuvaj snimljenu lokaciju u accommdoation
		accommodation.setLocation(location);
		
		accommodation =	accommodationRepository.save(accommodation);
		createAccommodationResponse.setAccommodationId(accommodation.getId());
		createAccommodationResponse.setLocationId(location.getId());
		
		return createAccommodationResponse;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAccommodationRequest")
	@ResponsePayload
	public DeleteAccommodationResponse deleteAccommodationRequest
			(@RequestPayload DeleteAccommodationRequest deleteAccommodationRequest) {
		DeleteAccommodationResponse deleteAccommodationResponse = new DeleteAccommodationResponse();
		Accommodation accommodation = accommodationRepository.findByIdAndDeleted(deleteAccommodationRequest.getAccommodationId(), false).orElse(null);
		
		accommodation.setDeleted(true);
		accommodationRepository.save(accommodation);
		
		for (AccommodationUnit accommodationUnit: accommodation.getAccommodationUnit()) {
			accommodationUnit.setDeleted(true);
			accommodationUnitRepository.save(accommodationUnit);
		}
		for (Image image : accommodation.getImage()) {
			image.setDeleted(true);
			imageRepository.save(image);
		}
		
		deleteAccommodationResponse.setSuccess(true);
		
		return deleteAccommodationResponse;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateAccommodationRequest")
	@ResponsePayload
	public UpdateAccommodationResponse updateAccommodationRequest(
			@RequestPayload UpdateAccommodationRequest updateAccommodationRequest) {
		UpdateAccommodationResponse updateAccommodationResponse = new UpdateAccommodationResponse();

		//uzmi verziju smestaja iz request
		Accommodation requestAccommodation = updateAccommodationRequest.getAccommodation();
			
		//uzmi verziju smestaja iz repoziotrijuma
		Accommodation repositoryAccommodation = accommodationRepository.findByIdAndDeleted(requestAccommodation.getId(), false).orElse(null);
		
		//azuriraj nasu verziju
		repositoryAccommodation.setAccommodationCategory(requestAccommodation.getAccommodationCategory());
		repositoryAccommodation.setAccommodationType(requestAccommodation.getAccommodationType());
		repositoryAccommodation.setAdditionalService(requestAccommodation.getAdditionalService());
		
		repositoryAccommodation.setDescription(requestAccommodation.getDescription());
		repositoryAccommodation.setName(requestAccommodation.getName());
	
		
		
		accommodationRepository.save(repositoryAccommodation);
		
		updateAccommodationResponse.setSuccess(true);
		return updateAccommodationResponse;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAccommodationImagesRequest")
	@ResponsePayload
	public GetAccommodationImagesResponse getAccommodationImagesRequest(
			@RequestPayload GetAccommodationImagesRequest getAccommodationImagesRequest) {
		GetAccommodationImagesResponse getAccommodationImagesResponse = new GetAccommodationImagesResponse();
		
		Long id = getAccommodationImagesRequest.getAccommodationId();
		Accommodation accommodation = accommodationRepository.findByIdAndDeleted(id, false).orElse(null);
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
		
		//snimi i u acommodation
		Accommodation accommodation = accommodationRepository.findByIdAndDeleted(createImageRequest.getAccommodationId(), false).orElse(null);
		accommodation.getImage().add(image);
		
		accommodationRepository.save(accommodation);
		
		return createImageResponse;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteImageRequest")
	@ResponsePayload
	public DeleteImageResponse deleteImageRequest(
			@RequestPayload DeleteImageRequest deleteImageRequest) {
		DeleteImageResponse deleteImageResponse = new DeleteImageResponse();
		//imageRepository.deleteById(deleteImageRequest.getImageId());
		
		Image image = imageRepository.findById(deleteImageRequest.getImageId()).orElse(null);
		image.setDeleted(true);
		imageRepository.save(image);
		
		deleteImageResponse.setSuccess(true);
		
		return deleteImageResponse;
	}
	
	
	private User deletePassword(User user) {
		User noPasswordUser = new User();
		noPasswordUser.setFirstName(user.getFirstName());
		noPasswordUser.setLastName(user.getLastName());
		noPasswordUser.setAddress(user.getAddress());
		noPasswordUser.setDeleted(false);
		noPasswordUser.setEmail(user.getEmail());
		noPasswordUser.setId(user.getId());
		noPasswordUser.setPib(user.getPib());
		noPasswordUser.setUserType(user.getUserType());
		noPasswordUser.setPassword("");
		return noPasswordUser;
	}

	
	@SuppressWarnings("unused")
	private Boolean isAttemptedDeletion(Object o) {
		Class<?> c = o.getClass();
		Method method = null;		
		Boolean isAttempted = false;
		
		try {
			method = c.getDeclaredMethod("getDeleted");
			isAttempted = (Boolean) method.invoke(o);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return isAttempted;
	}
	
	

}