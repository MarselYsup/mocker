#Stage 1: Build the application
FROM maven:3.8.4-openjdk-17-slim as builder

# Copy the project files to the container
COPY . /usr/src/myapp

# Set the working directory in the container
WORKDIR /usr/src/myapp

# Run maven package to build the application
RUN mvn -f pom.xml clean package

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-slim

# Set the deployment directory
WORKDIR /demo

# Copy the built JAR file from the builder stage to the runtime container
COPY --from=builder /usr/src/myapp/target/*.jar /demo/app.jar

# The command to run the application when the container starts
CMD ["java", "-jar", "/demo/app.jar"]

# Expose the port the application will run on
EXPOSE 8080