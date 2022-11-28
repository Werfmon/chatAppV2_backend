FROM openjdk:17-alpine3.12
RUN mvn install
RUN mvnw spring-boot:run
EXPOSE 8080