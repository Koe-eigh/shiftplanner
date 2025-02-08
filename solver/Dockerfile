FROM maven

WORKDIR /workspace

COPY mvnw pom.xml ./
 
COPY src ./src

CMD ["java", "-jar", "target/solver-0.0.1-SNAPSHOT.jar"]
