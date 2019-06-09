package xml.booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import xml.booking.model.Reservation;
import xml.booking.model.User;
import xml.booking.repositories.ReservationRepository;
import xml.booking.reservationsoap.CreateReservationRequest;
import xml.booking.reservationsoap.CreateReservationResponse;
import xml.booking.reservationsoap.DeclineReservationRequest;
import xml.booking.reservationsoap.DeclineReservationResponse;
import xml.booking.reservationsoap.GetReservationMessagesRequest;
import xml.booking.reservationsoap.GetReservationMessagesResponse;
import xml.booking.reservationsoap.GetReservationRequest;
import xml.booking.reservationsoap.GetReservationResponse;
import xml.booking.reservationsoap.GetReservationsListRequest;
import xml.booking.reservationsoap.GetReservationsListResponse;
import xml.booking.reservationsoap.UpdateReservationRequest;
import xml.booking.reservationsoap.UpdateReservationResponse;

@Endpoint
public class ReservationSoapEndpoint {

	private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/tim1/reservationsoap";

	@Autowired
	private ReservationRepository reservationRepository;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getReservation")
	@ResponsePayload
	public GetReservationResponse getReservationRequest(@RequestPayload GetReservationRequest getReservationRequest) {
		Long reservationId = (long) getReservationRequest.getReservationId();
		Reservation reservation = reservationRepository.getOne(reservationId);
		
		GetReservationResponse getReservationResponse = new GetReservationResponse();
		
		getReservationResponse.setReservation(reservation);
		
		return getReservationResponse;
	}
	
	/**
	 * Trazi na osnovu korisnika za koga je rezervisano
	 * @param getReservationMessagesRequest
	 * @return
	 */
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getReservationList")
	@ResponsePayload
	public GetReservationsListResponse getReservationsListRequest(@RequestPayload GetReservationsListRequest getReservationMessagesRequest) {
		GetReservationsListResponse getReservationsListResponse = new GetReservationsListResponse();
		
		User user = getReservationMessagesRequest.getUser();
		
		List<Reservation> reservation = reservationRepository.findByUserId(user.getId());
		
	
		getReservationsListResponse.setReservation(reservation);
		
		return getReservationsListResponse;

	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createReservation")
	@ResponsePayload
	public CreateReservationResponse createReservationResponse(@RequestPayload CreateReservationRequest createReservationRequest) {
		CreateReservationResponse createReservationResponse = new CreateReservationResponse();
		Reservation reservation = createReservationRequest.getReservation();
		reservationRepository.save(reservation);
		createReservationResponse.setSuccess(true);
		
		return createReservationResponse;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "declineReservation")
	@ResponsePayload
	public DeclineReservationResponse declineReservation(@RequestPayload DeclineReservationRequest declineReservationRequest) {
		DeclineReservationResponse declineReservationResponse = new DeclineReservationResponse();
		
		Long id = (long) declineReservationRequest.getReservationId();
		
		
		reservationRepository.deleteById(id);
		
		declineReservationResponse.setSuccess(true);
		
		return declineReservationResponse;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateReservation")	
	@ResponsePayload
	public UpdateReservationResponse updateReservationResponse(@RequestPayload UpdateReservationRequest updateReservationRequest) {
		UpdateReservationResponse updateReservationResponse = new UpdateReservationResponse();
		
		Reservation reservation =  updateReservationRequest.getReservation();
		
		reservationRepository.save(reservation);
		
		
		updateReservationResponse.setSuccess(true);
		
		return updateReservationResponse;
	}
	

}
