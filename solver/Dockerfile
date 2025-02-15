# Build stage
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app

RUN groupadd -g 1001 appuser && \
    useradd -r -u 1001 -g appuser appuser

COPY --chown=appuser:appuser --from=build /app/target/*.jar app.jar

EXPOSE 8080
USER appuser

ENTRYPOINT ["java", "-jar", "app.jar"]