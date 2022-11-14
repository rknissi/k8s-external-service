#Sets an image as a base for subsequent instructions
FROM openjdk:8-jdk-alpine

#Copies a file from the host filesystem to the image internal filesystem
COPY ./target/demo-0.0.1-SNAPSHOT.jar demo.jar

#Configure the container that will run as a executable
ENTRYPOINT ["java", "-jar", "demo.jar", "-Dspring.profiles.active=prod"]

