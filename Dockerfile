# Imagen base para compilar el proyecto
FROM maven:3.8.6-openjdk-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Imagen base para ejecutar la aplicación
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/image-editor-api-0.0.1.jar app_image_editor.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_image_editor.jar"]