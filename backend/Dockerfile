FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY ./pom.xml ./
COPY ./src ./src

RUN apt-get update && apt-get install -y maven
RUN mvn -f /app/pom.xml -DskipTests package

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/target/backend-0.0.1-SNAPSHOT.jar"]
