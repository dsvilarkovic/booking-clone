FROM openjdk:8-jdk-alpine
ADD /target/maintenance-of-code-book-0.0.1-SNAPSHOT.jar maintenance-of-code-book.
jar
ENTRYPOINT ["java", "-jar", "/maintenance-of-code-book.jar"]
EXPOSE 9979