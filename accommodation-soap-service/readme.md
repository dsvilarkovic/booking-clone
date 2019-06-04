Pre pokretanja ovoga, mora se pokrenuti backend <br>
Nakon toga, sledi zakomentarisanje sledeceg dependencija u pom.xml: <br>
		<code> <dependency>
			<groupId>io.pivotal.spring.cloud</groupId>
			<artifactId>spring-cloud-services-starter-service-registry</artifactId>
		</dependency> </code>
<br>
Zatim, zakomentarisati i @EnableEurekaClient u AccommodationSoapServiceApplication

