FROM openjdk:8-jdk-alpine
ADD /target/client-message-0.0.1-SNAPSHOT.jar client-message.
jar
ENTRYPOINT ["java", "-jar", "/client-message.jar"]
EXPOSE 9978