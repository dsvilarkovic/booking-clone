FROM openjdk:8-jdk-alpine

ADD /target/accommodation-soap-service-0.0.1-SNAPSHOT.jar accommodation-soap-service.jar
ENTRYPOINT ["java", "-jar", "/accommodation-soap-service.jar"]
	
EXPOSE 9998