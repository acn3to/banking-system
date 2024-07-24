FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/banking-system-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
COPY src/main/resources /app/resources
ENTRYPOINT ["java", "-cp", "app.jar:app/resources", "com.acn3to.Main"]
