package xml.booking;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import xml.booking.messagingsoap.GetMessagesRequest;
import xml.booking.messagingsoap.GetMessagesResponse;

public class ReservationClient extends WebServiceGatewaySupport{

	//TODO: izmeniti ovde za eureku
	public static final String ACC_SOAP_URL = "http://localhost:9993/ws/";
	private static final String ACC_SOAP_NAMESPACE = "http://www.ftn.uns.ac.rs/tim1/reservationsoap";
	
	public GetMessagesResponse getMessagesResponse(Long reservationId) {
		GetMessagesRequest getMessagesRequest = new GetMessagesRequest();
		getMessagesRequest.setReservationId(reservationId);
		
		GetMessagesResponse response = (GetMessagesResponse) getWebServiceTemplate().
						marshalSendAndReceive(ACC_SOAP_URL, getMessagesRequest, 
								new SoapActionCallback(ACC_SOAP_NAMESPACE + "/GetMessagesRequest"));
	
		return response;
	}
}		
