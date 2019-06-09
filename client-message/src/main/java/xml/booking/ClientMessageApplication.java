package xml.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.cmeza.sdgenerator.annotation.SDGenerator;

@SDGenerator(entityPackage = "xml.booking.model", repositoryPackage = "xml.booking.repositories", managerPackage = "xml.booking.managers", repositoryPostfix = "Repository", managerPostfix = "Manager", onlyAnnotations = false, debug = false, overwrite = false)
@SpringBootApplication
@EnableEurekaClient
public class ClientMessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientMessageApplication.class, args);
	}

	@Configuration
	class RestTemplateConfig {

		@Bean
		public RestTemplate getRestTemplate() {
			return new RestTemplate();
		}
		
	}
}
