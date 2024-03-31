FROM openjdk:17-jdk-alpine
COPY target/team8-1.jar team8-1.jar
ENTRYPOINT ["java","-jar","/team8-1.jar"]
