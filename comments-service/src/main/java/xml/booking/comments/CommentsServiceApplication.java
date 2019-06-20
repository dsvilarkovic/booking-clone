package xml.booking.comments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import com.cmeza.sdgenerator.annotation.SDGenerator;

@SDGenerator(
		entityPackage = "xml.booking.comments.model",
        repositoryPackage = "xml.booking.comments.repositories",
        managerPackage = "xml.booking.comments.managers",
        repositoryPostfix = "Repository",
        managerPostfix = "Manager",
        onlyAnnotations = false,
        debug = false,
        overwrite = false
	)
@SpringBootApplication
@EnableEurekaClient
public class CommentsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentsServiceApplication.class, args);
		System.out.println("Aktiviran Comment Service");
	}

}
