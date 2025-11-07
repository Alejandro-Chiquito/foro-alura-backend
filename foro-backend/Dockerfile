# ---- Etapa de Construcción (Build Stage) ----
# Usamos una imagen con Maven y JDK para compilar el proyecto
FROM maven:3.8.5-openjdk-17 AS build

# Setear el directorio de trabajo
WORKDIR /app

# Copiar los archivos de definición del proyecto y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el resto del código fuente y construir el JAR
COPY src ./src
RUN mvn package -DskipTests

# ---- Etapa de Ejecución (Runtime Stage) ----
# Usamos una imagen ligera que solo tiene el JRE, no el JDK completo
FROM openjdk:17-jre-slim

WORKDIR /app

# Copiar únicamente el JAR construido desde la etapa anterior
COPY --from=build /app/target/foro-backend-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto y definir el comando de ejecución
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]