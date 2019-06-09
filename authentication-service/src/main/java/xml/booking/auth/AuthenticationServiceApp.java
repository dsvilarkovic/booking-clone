package xml.booking.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.cmeza.sdgenerator.annotation.SDGenerator;

@SpringBootApplication
@EnableEurekaClient
@SDGenerator(
		entityPackage = "xml.booking.auth.model",
        repositoryPackage = "xml.booking.auth.repositories",
        managerPackage = "xml.booking.auth.managers",
        repositoryPostfix = "Repository",
        managerPostfix = "Manager",
        onlyAnnotations = false,
        debug = false,
        overwrite = false
	)
public class AuthenticationServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApp.class, args);
		System.out.println("Ajde Auth");

	}
}
