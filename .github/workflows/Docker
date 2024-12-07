# Use the official Maven image with OpenJDK
FROM maven:3.8.6-openjdk-17-slim as build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY votingsystem/pom.xml ./votingsystem/pom.xml
COPY votingsystem/src ./votingsystem/src

# Install project dependencies
RUN mvn install -DskipTests -f votingsystem/pom.xml

# Build the application
RUN mvn package -DskipTests -f votingsystem/pom.xml

# Use a new, clean image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory for the app
WORKDIR /app

# Copy the built JAR from the build image
COPY --from=build /app/votingsystem/target/*.jar /app/votingsystem.jar

# Expose the port the app will run on (default for Spring Boot is 8080)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/votingsystem.jar"]
