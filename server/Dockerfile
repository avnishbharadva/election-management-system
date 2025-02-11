# Use an official OpenJDK runtime as a base image
FROM openjdk:23-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target folder into the container
COPY target/election-management-system-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on (default Spring Boot port 8080)
EXPOSE 8082

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar","--server.port=8082"]
