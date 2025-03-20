FROM openjdk:17-jdk-slim

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests
COPY ./target/election-management-system-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/app.jar"]