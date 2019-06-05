package xml.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

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

//@EnableEurekaClient
@SpringBootApplication
public class AccommodationSoapServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccommodationSoapServiceApplication.class, args);
		System.out.println("Spojen i pokrentu Eureka CLient za AccSoap");
	}

}