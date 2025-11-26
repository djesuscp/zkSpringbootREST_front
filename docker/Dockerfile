FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/frontend_app-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

# # Build.
# FROM maven:3.9.9-eclipse-temurin-21 AS builder
# WORKDIR /build
# COPY backend/pom.xml .
# COPY backend/src ./src
# RUN mvn -B clean verify

# # Runtime.
# FROM eclipse-temurin:21-jre
# WORKDIR /app
# COPY --from=builder /build/target/backendApp-1.0.0.jar app.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","app.jar"]