# src: https://stackoverflow.com/a/27768965
#
# Build stage
#
FROM maven:3.8.6-eclipse-temurin-17-alpine AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

#
# Package stage
#
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /home/app/target/photorecognize-0.0.1-SNAPSHOT.jar /usr/local/lib/photorecognize.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/photorecognize.jar"]
