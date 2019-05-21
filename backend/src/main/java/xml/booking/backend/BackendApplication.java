package xml.booking.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.cmeza.sdgenerator.annotation.SDGenerator;

/**
 * Generise @SDGenerator Repositoryje i Manager-e za svaki postojeci entitet (cool stuff)
 * @author Dusan
 *
 */
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
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		System.out.println("Radi");
	}

}
