FROM openjdk:8-jdk-alpine

ADD /target/messaging-soap-service-0.0.1-SNAPSHOT.jar messaging-soap-service.jar
ENTRYPOINT ["java", "-jar", "/messaging-soap-service.jar"]
	
EXPOSE 9999