# Use a lightweight Java 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Expose the internal port your Spring Boot app uses
EXPOSE 1001

# Create a temp volume
VOLUME /tmp

# Automatically detect the JAR file inside target/
ARG JAR_FILE=target/*.jar

# Copy the JAR into the container
COPY ${JAR_FILE} app.jar

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app.jar"]
