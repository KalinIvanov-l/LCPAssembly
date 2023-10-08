FROM maven:3-openjdk-17 AS build

WORKDIR /project

COPY . .

RUN mvn clean package -pl core -am

# Use a smaller base image for running the application
FROM openjdk:17-jdk-slim

COPY --from=build /project/core /core

WORKDIR /core

COPY --from=build /project/core/target/core-0.0.1-SNAPSHOT.jar ./target/core-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "target/core-0.0.1-SNAPSHOT.jar"]
