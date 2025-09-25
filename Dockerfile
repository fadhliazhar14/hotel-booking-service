# Stage 1: Build JAR
FROM maven:3.9.2-eclipse-temurin-22 AS build
WORKDIR /app

# Copy pom & source
COPY pom.xml .
COPY src ./src

# Build fat jar
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:22-jdk
WORKDIR /app

# Copy JAR hasil build
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Jalankan aplikasi
ENTRYPOINT ["java", "-jar", "app.jar"]