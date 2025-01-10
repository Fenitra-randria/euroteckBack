#
# Build stage
#
FROM eclipse-temurin:21-jdk AS build 

RUN apt-get update && apt-get install -y maven

COPY src /home/app/src
COPY pom.xml /home/app

WORKDIR /home/app

RUN ls 

RUN mvn -f pom.xml clean package
#
# Package stage
#
FROM openjdk:21-jdk
COPY --from=build /home/app/target/backend-2.0.jar /usr/local/lib/backend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/backend.jar","--spring.profiles.active=production"]