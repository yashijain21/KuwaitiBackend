# Use a lightweight base image with Java 17
FROM eclipse-temurin:17-jdk-alpine

# Expose the port your application is configured to use internally (1001).
EXPOSE 1001

# Create a volume for temporary files
VOLUME /tmp

# Define the JAR file name and path
ARG JAR_FILE=target/waffleshakes-0.0.1-SNAPSHOT.jar

# Copy the JAR file into the container
COPY ${JAR_FILE} app.jar

# Define the startup command (Entry Point). We will inject the PORT environment
# variable during the Render setup.
ENTRYPOINT ["java", "-jar", "/app.jar"]