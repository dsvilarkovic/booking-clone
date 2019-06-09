package xml.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserManagingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagingServiceApplication.class, args);
	}

}
