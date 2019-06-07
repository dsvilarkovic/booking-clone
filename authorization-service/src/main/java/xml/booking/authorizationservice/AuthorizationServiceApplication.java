package xml.booking.authorizationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.cmeza.sdgenerator.annotation.SDGenerator;

@SpringBootApplication
@EnableEurekaClient

@SDGenerator(
		entityPackage = "xml.booking.authorizationservice.model",
        repositoryPackage = "xml.booking.authorizationservice.repositories",
        managerPackage = "xml.booking.authorizationservice.managers",
        repositoryPostfix = "Repository",
        managerPostfix = "Manager",
        onlyAnnotations = false,
        debug = false,
        overwrite = false
	)
public class AuthorizationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServiceApplication.class, args);
		System.out.println("Authorization Service pokrenut");
	}

}
