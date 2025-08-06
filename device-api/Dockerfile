FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app
COPY . .

RUN mvn clean package -pl device-api -am -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /app/device-api/target/device-api-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
