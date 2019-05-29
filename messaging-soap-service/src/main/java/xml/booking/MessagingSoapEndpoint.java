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
import xml.booking.repositories.MessageRepository;

@Endpoint
public class MessagingSoapEndpoint {
	private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/tim1/messagingsoap";

	@Autowired
	private MessageRepository messageRepository;
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createMessageRequest")
	@ResponsePayload
	private CreateMessageResponse createMessage(@RequestPayload CreateMessageRequest request) {
		CreateMessageResponse createMessageResponse = new CreateMessageResponse();
		
		//TODO: podesi vreme u longu
		long timeNow = System.currentTimeMillis();
		
		//snimi poruku u repozitorijum i uzmi id
		Message message = request.getMessage();
		message.setDate(timeNow);
		message = messageRepository.save(message);
		
		createMessageResponse.setMessageId(message.getId());
		
		return createMessageResponse;
	}
	
	

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMessagesRequest")
	@ResponsePayload
	private GetMessagesResponse getMessages(@RequestPayload GetMessagesRequest request) {
		GetMessagesResponse getMessagesResponse = new GetMessagesResponse();
		
		//TODO: dodati poziv ka servisu za rezervacije da se dobije id od rezervacije
		List<Message> messages = new ArrayList<Message>();
		messages.add(new Message());
		messages.add(new Message());
		messages.add(new Message());
		
		getMessagesResponse.setMessage(messages);
		
		return getMessagesResponse;		
	}
}