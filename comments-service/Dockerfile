FROM openjdk:8-jdk-alpine

ADD /target/comments-service-0.0.1-SNAPSHOT.jar comments-service.jar
ENTRYPOINT ["java", "-jar", "/comments-service.jar"]
	
EXPOSE 9991