# FROM eclipse-temurin:17-jre-alpine
# WORKDIR /app
# COPY target/*.jar app.jar
# EXPOSE 8080
# ENTRYPOINT ["java", "-jar", "app.jar"]

# Stage One - build
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy the pom then the src
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage Two - run
FROM eclipse-temurin:17-jre-alpine AS run

WORKDIR /app
# Copy the jar from build
COPY --from=build /app/target/*SNAPSHOT.jar app.jar

EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
