FROM openjdk:8-jdk-alpine
ADD /target/accommodation-service-0.0.1-SNAPSHOT.jar accommodation-service.
jar
ENTRYPOINT ["java", "-jar", "/accommodation-service.jar"]
EXPOSE 9970