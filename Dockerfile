FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/smart-hire-1.0.jar smart-hire.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "smart-hire.jar"]
