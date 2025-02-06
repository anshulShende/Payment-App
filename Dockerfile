FROM openjdk:17
WORKDIR /app
COPY build/libs/Payment-App-0.0.1-SNAPSHOT.jar payment-app.jar
ENTRYPOINT ["java", "-jar", "payment-app.jar"]