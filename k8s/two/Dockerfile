FROM maven:3.8.4-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src /app/src

RUN mvn package -DskipTests

# Stage 2: Copy the JAR file
FROM openjdk:11-slim AS final
WORKDIR /app
COPY --from=build /app/target/two.jar .

EXPOSE 5000

# Entry point
CMD ["java", "-jar", "two.jar"]