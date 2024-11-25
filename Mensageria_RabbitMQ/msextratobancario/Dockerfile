# p/ Build
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests


# imagem c build
FROM openjdk:21
WORKDIR /app
COPY --from=build ./app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

ENV RABBITMQ_SERVER=rabbitmqms
ENV RABBIT_USERNAME=guest
ENV RABBIT_PASSWORD=guest
ENV DATASOURCE_URL="jdbc:mysql://localhost:3306/dbbanco?useSSL=false&serverTimezone=UTC"
ENV DATASOURCE_USERNAME=root
ENV DATASOURCE_PASSWORD=1234
