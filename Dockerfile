FROM maven:3.6-openjdk-17-slim AS build
COPY src src
COPY pom.xml .
RUN mvn -f pom.xml clean package

FROM openjdk:17-jdk-slim
COPY --from=build /target/banking-app-aston-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]