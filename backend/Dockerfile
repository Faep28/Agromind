# ========================
# STAGE 1: Build
# ========================
FROM eclipse-temurin:24-jdk AS builder

# Ir a carpeta del backend
WORKDIR /app

# Copiar solo la carpeta backend
COPY backend/ .

# Dar permisos a mvnw
RUN chmod +x mvnw

# Compilar el proyecto (usa mvnw dentro de backend)
RUN ./mvnw clean package -DskipTests


# ========================
# STAGE 2: Run
# ========================
FROM eclipse-temurin:24-jre

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
