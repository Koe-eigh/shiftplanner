FROM maven AS build

WORKDIR /workspace

COPY pom.xml ./
COPY src ./src

RUN mvn clean package

# 実行ステージ
FROM openjdk:17

WORKDIR /app

COPY --from=build /workspace/target/solver-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "solver-0.0.1-SNAPSHOT.jar"]