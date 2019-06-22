package xml.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.cmeza.sdgenerator.annotation.SDGenerator;

@SDGenerator(
		entityPackage = "xml.booking.model",
        repositoryPackage = "xml.booking.repositories",
        managerPackage = "xml.booking.managers",
        repositoryPostfix = "Repository",
        managerPostfix = "Manager",
        onlyAnnotations = false,
        debug = false,
        overwrite = false
	)

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class AccommodationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccommodationServiceApplication.class, args);
	}

}
