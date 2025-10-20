FROM eclipse-temurin:24-jdk-alpine

RUN addgroup -S app && adduser -S app -G app

USER app
WORKDIR /home/app

COPY target/socket-server-jar-with-dependencies.jar socket-server.jar

CMD ["java", "-jar", "/home/app/socket-server.jar"]
