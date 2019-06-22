package xml.booking.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xml.booking.model.User;

// TODO: Promeniti kasnije url
@FeignClient(name="auth-service", url="localhost:9994/user")
public interface AuthenticationProxy {
	@RequestMapping(value = "/whoami", method = RequestMethod.GET)
	public ResponseEntity<User> whoami(@RequestHeader("Authorization") String token);
}
