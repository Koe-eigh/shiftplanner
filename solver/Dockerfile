FROM maven

WORKDIR /workspace

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# RUN ./mvnw dependency:go-offline
 
COPY src ./src

CMD ["java", "-jar", "target/solver-0.0.1-SNAPSHOT.jar"]
# CMD ["./mvnw","-X", "spring-boot:run"]