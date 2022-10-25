FROM openjdk:8-jdk-alpine

COPY ./target/demo-0.0.1-SNAPSHOT.jar demo.jar

ENTRYPOINT ["java", "-jar", "demo.jar", "-Dspring.profiles.active=prod"]

