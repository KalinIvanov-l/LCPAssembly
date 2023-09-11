FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /usr/src

COPY pom.xml .
COPY ../ /usr/src/

RUN mvn clean package -DskipTests

# Create a smaller runtime image
FROM openjdk:17-jdk-slim
WORKDIR /usr/src

COPY --from=build /usr/src/target/LCPA-0.0.1-SNAPSHOT.jar /usr/src/LCPA-0.0.1-SNAPSHOT.jar

EXPOSE 8080

## shell form for execution runtime commands
CMD ["java", "-jar", "LCPA-0.0.1-SNAPSHOT.jar"]