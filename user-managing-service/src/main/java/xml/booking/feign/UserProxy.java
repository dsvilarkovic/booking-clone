package xml.booking.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import xml.booking.dto.RegistrationDTO;

//TODO: promeniti putanju na zuul
@FeignClient(name="auth-service", url="localhost:9994/users")
public interface UserProxy {
	
	@PostMapping("/")
	public ResponseEntity<String> registerAdminOrAgent(@RequestBody RegistrationDTO user);

}
