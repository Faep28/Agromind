# Etapa 1: Build
FROM eclipse-temurin:24-jdk AS builder

WORKDIR /app
COPY . .
RUN chmod +x mvnw || true
RUN mkdir -p .mvn/wrapper && \
    echo "distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.6/apache-maven-3.9.6-bin.zip" > .mvn/wrapper/maven-wrapper.properties && \
    if [ ! -f mvnw ]; then \
        echo "Maven Wrapper no encontrado, generando uno..."; \
        apt-get update && apt-get install -y maven && \
        mvn -N io.takari:maven:wrapper; \
    fi
RUN ./mvnw clean package -DskipTests

# Etapa 2: Run
FROM eclipse-temurin:24-jre

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
