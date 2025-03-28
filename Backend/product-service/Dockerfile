# Building the .jar file using maven
FROM maven:3.9.5-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .
RUN mvn clean package -DskipTests

# Ensure correct permissions during build
RUN chmod 755 target/*.jar

# Creating the image
FROM openjdk:17-jdk-slim
WORKDIR /app

RUN useradd -m appuser
USER appuser

COPY --from=builder --chown=appuser:appuser /app/target/*.jar app.jar

RUN chmod +x app.jar

ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"]