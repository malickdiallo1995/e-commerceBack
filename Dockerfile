FROM openjdk:8u212-jdk-alpine
VOLUME /tmp
ADD /target/employees-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
