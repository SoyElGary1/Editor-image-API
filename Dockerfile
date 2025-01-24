FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/image-editor-api-0.0.1.jar
COPY ${JAR_FILE} app_image_editor.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_image_editor.jar"]