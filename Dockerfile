# Build stage
FROM eclipse-temurin:21-jdk AS build 

RUN apt-get update && apt-get install -y maven

COPY src /home/app/src
COPY pom.xml /home/app

WORKDIR /home/app

# Skip tests during Docker build
RUN mvn -f pom.xml clean package -DskipTests

#
# Package stage
#
FROM openjdk:21-jdk

# Copy the built JAR file
COPY --from=build /home/app/target/backend-2.0.jar /usr/local/lib/backend.jar

# Expose port 8080
EXPOSE 8080

# Set environment variables for production (optional, depending on your app)
# ENV SPRING_DATASOURCE_URL=<your-database-url>
# ENV SPRING_DATASOURCE_USERNAME=<your-db-username>
# ENV SPRING_DATASOURCE_PASSWORD=<your-db-password>

# Run the application
ENTRYPOINT ["java", "-jar", "/usr/local/lib/backend.jar", "--spring.profiles.active=production"]
