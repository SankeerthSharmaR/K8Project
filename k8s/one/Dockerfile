FROM maven:3.8.4-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src /app/src

RUN mvn package -DskipTests

# Stage 2: Copy the JAR file
FROM openjdk:11-slim AS final
WORKDIR /app
COPY --from=build /app/target/one.jar .
 
EXPOSE 6001

# Entry point
CMD ["java", "-jar", "one.jar"]