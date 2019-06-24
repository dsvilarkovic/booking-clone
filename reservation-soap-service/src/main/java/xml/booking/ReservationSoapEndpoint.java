package xml.booking;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import xml.booking.model.Reservation;
import xml.booking.model.User;
import xml.booking.repositories.ReservationRepository;
import xml.booking.reservationsoap.CheckinReservationRequest;
import xml.booking.reservationsoap.GetMessagesRequest;
import xml.booking.reservationsoap.GetMessagesResponse;
import xml.booking.reservationsoap.GetReservationListResponse;
import xml.booking.reservationsoap.GetReservationRequest;
import xml.booking.reservationsoap.GetReservationResponse;

@Endpoint
public class ReservationSoapEndpoint {

	//TODO: izmeniti ovde za eureku
//	private static final String AUTH_URL = "http://localhost:9994/users";
	private static final String AUTH_URL = "http://localhost:8762/api/users"; // service-registry + api_gateway
	private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/tim1/reservationsoap";

	@Autowired
	private ReservationRepository reservationRepository;
	
	protected HttpServletRequest getHttpServletRequest() {
	    TransportContext ctx = TransportContextHolder.getTransportContext();
	    return ( null != ctx ) ? ((HttpServletConnection ) ctx.getConnection()).getHttpServletRequest() : null;
	}

	protected String getHttpHeaderValue( final String headerName ) {
	    HttpServletRequest httpServletRequest = getHttpServletRequest();
	    return ( null != httpServletRequest ) ? httpServletRequest.getHeader( headerName ) : null;
	}
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getReservationRequest")
	@ResponsePayload
	public GetReservationResponse getReservationRequest(@RequestPayload GetReservationRequest getReservationRequest) {
		Long reservationId = getReservationRequest.getReservationId();
		Reservation reservation = reservationRepository.findByIdAndDeleted(reservationId, false);
		
		GetReservationResponse getReservationResponse = new GetReservationResponse();
		
		getReservationResponse.setReservation(reservation);
		
		return getReservationResponse;
	}
	
	/**
	 * Trazi na osnovu korisnika za koga je rezervisano
	 * @param getReservationMessagesRequest
	 * @return
	 */
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getReservationListRequest")
	@ResponsePayload
	public GetReservationListResponse getReservationListRequest() {
		GetReservationListResponse getReservationListResponse = new GetReservationListResponse();
		
		//nadji trenutno ulogovanog user-a na osnovu /whoami
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", getHttpHeaderValue("Authorization"));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<User> response = restTemplate.exchange(AUTH_URL + "/whoami", HttpMethod.GET, entity, User.class);
		User user = response.getBody();

		
		List<Reservation> reservation = reservationRepository.findReservations(user.getId(), false);
		
	
		getReservationListResponse.setReservation(reservation);
		
		return getReservationListResponse;

	}
	

	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "checkinReservationRequest")
	public void checkinReservation(@RequestPayload CheckinReservationRequest checkinReservationRequest) {
		Long reservationId = checkinReservationRequest.getReservationId();
		
		Reservation reservation = reservationRepository.findByIdAndDeleted(reservationId, false);
		
		reservation.setCheckedIn(true);
		
		reservationRepository.save(reservation);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMessagesRequest")
	@ResponsePayload
	public GetMessagesResponse getMessages(@RequestPayload GetMessagesRequest getMessagesRequest ) {
		Long reservationId = getMessagesRequest.getReservationId();
		
		Reservation reservation = reservationRepository.findByIdAndDeleted(reservationId, false);


		GetMessagesResponse getMessagesResponse = new GetMessagesResponse();
		
		getMessagesResponse.setMessage(reservation.getMessage());
		
		return getMessagesResponse;
	}

	

}
