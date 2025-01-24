FROM openjdk:21-jdk-slim
RUN apt-get update && apt-get install -y \
    libfreetype6 \
    libfontconfig1 \
    --no-install-recommends \
    && rm -rf /var/lib/apt/lists/*
ARG JAR_FILE=target/image-editor-api-0.0.1.jar
COPY ${JAR_FILE} app_image_editor.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_image_editor.jar"]