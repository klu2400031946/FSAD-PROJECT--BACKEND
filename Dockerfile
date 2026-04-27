# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Create a directory for uploads
RUN mkdir -p uploads

# The application listens on the port set by the PORT environment variable
# Default to 6497 if not set
ENV PORT=6497
EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]
