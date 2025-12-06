#FROM eclipse-temurin:24-jdk AS build
#COPY . .
#RUN chmod +x mvnw
#RUN ./mvnw clean install -DskipTests
#
#FROM eclipse-temurin:24-jdk
#COPY --from=build /target/*.jar agromind.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "agromind.jar"]

FROM eclipse-temurin:24-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
