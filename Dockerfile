# =========================================================================
# Runtime Stage (Using Eclipse Temurin for Java 25)
# =========================================================================
FROM eclipse-temurin:25-jre-alpine

# Set the execution directory inside the container
WORKDIR /app

# Create a secure, non-root system user for enterprise cloud compliance
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the fat JAR directly from the child module's target directory
# This path exactly matches the 'core-application' folder seen in image_55bcb7.png
COPY core-application/target/core-application-1.0.0-SNAPSHOT.jar app.jar

# Expose the standard Spring Boot embedded Tomcat web port
EXPOSE 8080

# Run the standalone executable JAR
ENTRYPOINT ["java", "-jar", "app.jar"]