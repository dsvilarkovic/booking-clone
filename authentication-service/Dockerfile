FROM openjdk:8-jdk-alpine

ADD target/authentication-service-0.0.1-SNAPSHOT.jar authentication-service.jar
ENTRYPOINT ["java", "-jar", "/authentication-service.jar"]
	
EXPOSE 9994