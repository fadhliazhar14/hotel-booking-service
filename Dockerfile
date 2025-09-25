FROM eclipse-temurin:22-jdk
WORKDIR /app

# Copy JAR hasil build dari runner
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]