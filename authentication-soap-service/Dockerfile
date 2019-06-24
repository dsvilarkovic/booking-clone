FROM openjdk:8-jdk-alpine

ADD /target/authentication-soap-service-0.0.1-SNAPSHOT.jar authentication-soap-service.jar
ENTRYPOINT ["java", "-jar", "/authentication-soap-service.jar"]
	
EXPOSE 9992