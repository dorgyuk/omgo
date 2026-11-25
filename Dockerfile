FROM eclipse-temurin:21.0.13.1_2-jdk as builder
WORKDIR /workspace

COPY gradlew ./
COPY gradle/ ./gradle/
COPY build.gradle settings.gradle ./
COPY src/ ./src/

RUN sed -i 's/\r$//' gradlew \
    && chmod +x gradlew \
    && ./gradlew --no-daemon bootJar

FROM eclipse-temurin:21.0.13.1_2-jre

WORKDIR /app

COPY --from=builder /workspace/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]