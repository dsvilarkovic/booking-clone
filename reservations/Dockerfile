FROM openjdk:8-jdk-alpine
ADD /target/reservations-0.0.1-SNAPSHOT.jar reservations.jar
ENTRYPOINT ["java", "-jar", "/reservations-0.0.1-SNAPSHOT.jar"]
EXPOSE 9977