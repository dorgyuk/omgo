FROM eclipse-temurin:21-jdk as builder
WORKDIR /workspace

COPY gradlew ./
COPY gradle/ ./gradle/
COPY build.gradle settings.gradle ./
COPY src/ ./src/

RUN sed -i 's/\r$//' gradlew \
    && chmod +x gradlew \
    && ./gradlew --no-daemon bootJar

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /workspace/build/libs/*.jar app.jar

# CVE-2026-114938(Java4Shell) 임시 완화: 패치된 이미지(21.0.13.1+)로 교체하면 제거
ENTRYPOINT ["java", "-XX:-UseBASE64Intrinsics", "-jar", "app.jar"]