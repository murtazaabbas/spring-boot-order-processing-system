FROM openjdk:17-jdk-slim-buster
# following command has been added to test the internet connection from the pod will be removed
RUN apt-get update && apt-get install -y curl && apt-get install -y stress
WORKDIR /app
COPY target/ConfigServer-1.0-SNAPSHOT.jar .
EXPOSE 8888
ENTRYPOINT ["java","-jar","ConfigServer-1.0-SNAPSHOT.jar"]