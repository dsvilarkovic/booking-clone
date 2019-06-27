FROM openjdk:8-jdk-alpine

ADD /target/user-managing-service-0.0.1-SNAPSHOT.jar user-managing-service.jar
ENTRYPOINT ["java", "-jar", "/user-managing-service.jar"]

EXPOSE 8096