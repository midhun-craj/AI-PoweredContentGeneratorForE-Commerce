FROM openjdk:17-jdk-slim

WORKDIR /user-service

COPY target/user-service.jar user-service.jar

EXPOSE 8100

ENTRYPOINT ["java", "-jar", "user-service.jar"]