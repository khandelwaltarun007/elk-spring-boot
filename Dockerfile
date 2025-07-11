FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/user-service-elk-stack-*.jar app.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "app.jar"]