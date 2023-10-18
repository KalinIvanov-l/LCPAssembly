FROM maven:3-openjdk-17 AS build

WORKDIR /project

COPY . .

RUN mvn clean package -pl core -am

# Runtime stage
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /project/core/target/core-0.0.1-SNAPSHOT.jar ./core-0.0.1-SNAPSHOT.jar

RUN mkdir -p ./core/src/main/resources

COPY --from=build /project/core/src/main/resources/test.txt ./core/src/main/resources/test.txt

COPY --from=build /project/core/config.json ./core/config.json

EXPOSE 8080

CMD ["java", "-jar", "core-0.0.1-SNAPSHOT.jar"]
