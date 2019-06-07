FROM openjdk:8-jdk-alpine

ADD target/backend-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java", "-jar", "/backend.jar"]
	
EXPOSE 8888