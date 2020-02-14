FROM adoptopenjdk/openjdk8:jdk8u242-b08-alpine-slim as builder

WORKDIR /app
COPY . .
RUN ./gradlew bootJar

FROM adoptopenjdk/openjdk8:jdk8u242-b08-alpine-slim
COPY --from=builder /app/build/libs/eventhub-*.jar /boot.jar

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/boot.jar"]
