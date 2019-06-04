package xml.booking.serviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import com.cmeza.sdgenerator.annotation.SDGenerator;

@SpringBootApplication
@EnableEurekaServer
@EnableZuulProxy
@SDGenerator(
		entityPackage = "xml.booking.serviceregistry.model",
        repositoryPackage = "xml.booking.serviceregistry.repositories",
        managerPackage = "xml.booking.managers",
        repositoryPostfix = "Repository",
        managerPostfix = "Manager",
        onlyAnnotations = false,
        debug = false,
        overwrite = false
	)
public class ServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegistryApplication.class, args);
		System.out.println("Upaljen Servis Registar preko Eureka Servera");
	}

}
