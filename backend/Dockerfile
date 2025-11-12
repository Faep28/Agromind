FROM maven:3.8.3-openjdk-24 AS build
COPY . .
RUN mvn clean install

FROM eclipse-temurin:24-jdk
COPY --from=build /target/backend-0.0-1-SNAPSHOT-jar agromind.jar

EXPOSE 8080
ENTRYPOINT ["java", "jar", "agromind.jar"]