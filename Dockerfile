FROM eclipse-temurin:17-jdk-focal

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

CMD ["./mvnw", "spring-boot:run"]