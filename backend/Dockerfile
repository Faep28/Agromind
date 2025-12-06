#FROM eclipse-temurin:24-jdk AS build
#COPY . .
#RUN chmod +x mvnw
#RUN ./mvnw clean install -DskipTests
#
#FROM eclipse-temurin:24-jdk
#COPY --from=build /target/*.jar agromind.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "agromind.jar"]
# Etapa de build
FROM eclipse-temurin:24-jdk AS build
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline
COPY src src
RUN ./mvnw clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
