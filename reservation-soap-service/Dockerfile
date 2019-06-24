FROM openjdk:8-jdk-alpine

ADD /target/reservation-soap-service-0.0.1-SNAPSHOT.jar reservation-soap-service.jar
ENTRYPOINT ["java", "-jar", "/reservation-soap-service.jar"]
	
EXPOSE 9993