FROM gradle:7.2.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test

FROM openjdk:17-jdk-slim
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/lse-backend.jar
CMD ["java", "-jar", "/app/lse-backend.jar"]
