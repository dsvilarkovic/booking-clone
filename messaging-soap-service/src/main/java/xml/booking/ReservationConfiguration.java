package xml.booking;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ReservationConfiguration {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath("messagingsoap.wsdl");
		return marshaller;
	}
	
	@Bean ReservationClient reservationClient(Jaxb2Marshaller marshaller) {
		ReservationClient client = new ReservationClient();
		client.setDefaultUri(ReservationClient.ACC_SOAP_URL);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		
		
		return client;
	}
}
