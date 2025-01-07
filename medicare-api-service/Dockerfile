
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/medicare-api-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3307/medicare_db?createDatabaseIfNotExist=true
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=1234

CMD ["java", "-jar", "app.jar"]
