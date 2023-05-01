FROM eclipse-temurin:17-jdk-focal

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN --mount=type=cache,target=/root/.m2 ./mvnw install -DskipTests


CMD ["./mvnw", "spring-boot:run"]