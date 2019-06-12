package xml.booking.authenticationsoapservice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import xml.booking.auth.soap.LoginRequest;
import xml.booking.auth.soap.LoginResponse;
import xml.booking.auth.soap.dto.UserDTO;

@Endpoint
public class AuthenticationSoapEndpoint {
	private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/tim1/loginsoap";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginRequest")
	@ResponsePayload	
	public LoginResponse loginSoap(@RequestPayload LoginRequest loginRequest) {
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();

		LoginResponse loginResponse = new LoginResponse();
		
		String url = "http://localhost:9994/auth/"; // "http://auth-service/auth/"  kad bude pod jednim gateway-om
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<?> responseEntity = restTemplate.postForEntity(url, new UserDTO(username, password), ResponseEntity.class);
		//
		HttpHeaders headers = responseEntity.getHeaders();
		String authContent = null;
		try {
			authContent = headers.get("Authorization").get(0);
		}
		catch(Exception e) {
			loginResponse.setSuccess(false);
			return loginResponse;
		}
		
		loginResponse.setSuccess(true);
		
		return loginResponse;
	}
}
