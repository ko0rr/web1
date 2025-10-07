
FROM openjdk:17-jdk-slim
COPY build/libs/labweb1-1.0-SNAPSHOT.jar .
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "-DFCGI_PORT=9000", "labweb1-1.0-SNAPSHOT.jar"]