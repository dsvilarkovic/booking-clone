package xml.booking.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import xml.booking.dto.RegistrationDTO;

@FeignClient(name="auth-service")
public interface UserProxy {
	
	@PostMapping("/")
	public ResponseEntity<String> registerAdminOrAgent(@RequestBody RegistrationDTO user);

}