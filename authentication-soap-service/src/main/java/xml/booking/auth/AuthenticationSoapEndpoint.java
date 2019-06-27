package xml.booking.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import xml.booking.auth.dto.UserDTO;
import xml.booking.auth.soap.LoginRequest;
import xml.booking.auth.soap.LoginResponse;

@Endpoint
public class AuthenticationSoapEndpoint {
	private static final String NAMESPACE_URI = "http://www.ftn.uns.ac.rs/tim1/loginsoap";

	
	// Create a bean for restTemplate to call services
	@Bean
	@LoadBalanced		// Load balance between service instances running at different ports.
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

	@Autowired
	RestTemplate restTemplate;

	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginRequest")
	@ResponsePayload	
	public LoginResponse loginSoap(@RequestPayload LoginRequest loginRequest) {
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();

		LoginResponse loginResponse = new LoginResponse();
		
		String url = "http://auth-service/auth/"; // "http://auth-service/auth/"  kad bude pod jednim gateway-om
//		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<?> responseEntity = restTemplate.postForEntity(url, new UserDTO(username, password), ResponseEntity.class);
		//
		HttpHeaders headers = responseEntity.getHeaders();
		String authContent = null;
		try {
			authContent = headers.get("Authorization").get(0);
		}
		catch(Exception e) {
			loginResponse.setAuthorization("");;
			return loginResponse;
		}
		
		loginResponse.setAuthorization(authContent);
		
		return loginResponse;
	}
}
