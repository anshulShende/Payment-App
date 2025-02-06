# Use the official OpenJDK base image based on Debian
FROM openjdk:17-slim

# Set the working directory inside the container
WORKDIR /app

# Install necessary packages
RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
    unzip \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Copy the build.gradle file to the working directory
COPY build.gradle ./

# Copy the gradle wrapper files
COPY gradlew ./
COPY gradle/ ./gradle/

# Copy the source code to the container
COPY src ./src

# Build the application (skip tests)
RUN ./gradlew build

# Copy the built jar file to the app directory
COPY build/libs/Payment-App-0.0.1-SNAPSHOT.jar payment-app.jar

# Set the entry point for the container
ENTRYPOINT ["java", "-jar", "payment-app.jar"]
