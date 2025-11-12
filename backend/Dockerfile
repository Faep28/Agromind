FROM eclipse-temurin:24-jdk AS build
COPY . .
RUN chmod +x nvmw
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:24-jdk
COPY --from=build /target/*.jar agromind.jar
EXPOSE 8080
ENTRYPOINT ["java", "jar", "agromind.jar"]

