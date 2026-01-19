# ==============================
# BUILD STAGE
# ==============================
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copy Maven wrapper & config from inner project
COPY habit-tracker/pom.xml .
COPY habit-tracker/mvnw .
COPY habit-tracker/.mvn .mvn

RUN ./mvnw dependency:go-offline

# Copy ACTUAL source directory
COPY habit-tracker/src src

# Build the JAR
RUN ./mvnw clean package -DskipTests

# ==============================
# RUNTIME STAGE
# ==============================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
