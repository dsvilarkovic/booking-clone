package xml.booking;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import xml.booking.messagingsoap.CreateMessageRequest;
import xml.booking.messagingsoap.CreateMessageResponse;
import xml.booking.messagingsoap.GetMessagesRequest;
import xml.booking.messagingsoap.GetMessagesResponse;
import xml.booking.model.Message;
import xml.booking.model.Reservation;
import xml.booking.model.User;
import xml.booking.repositories.MessageRepository;
import xml.booking.repositories.ReservationRepository;
import xml.booking.repositories.UserRepository;

@Endpoint
public class MessagingSoapEndpoint {
	private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/tim1/messagingsoap";

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createMessageRequest")
	@ResponsePayload
	public CreateMessageResponse createMessage(@RequestPayload CreateMessageRequest request) {
		//TODO: podesi vreme u longu
		long timeNow = System.currentTimeMillis();
		//snimi poruku u repozitorijum i uzmi id
		Message message = request.getMessage();
		CreateMessageResponse createMessageResponse = new CreateMessageResponse();
		Long reservationId = request.getReservationId();
		
		Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
		User user = userRepository.findByEmail(message.getUser().getEmail()).orElse(null);
		
		message.setUser(user);
		message.setDate(timeNow);
		message = messageRepository.save(message);
		
		reservation.getMessage().add(message);
	    reservationRepository.save(reservation);
	    
	    createMessageResponse.setMessageId(message.getId());
	    createMessageResponse.setTimestamp(timeNow);
		
		return createMessageResponse;
	}
	
	

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMessagesRequest")
	@ResponsePayload
	public GetMessagesResponse getMessages(@RequestPayload GetMessagesRequest request) {
		GetMessagesResponse getMessagesResponse = new GetMessagesResponse();
		Long reservationId = request.getReservationId();
		
		Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
		List<Message> messages = reservation.getMessage();
		
		getMessagesResponse.setMessage(messages);
		return getMessagesResponse;
	}
}