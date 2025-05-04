FROM openjdk:21-jdk-slim
WORKDIR /backendapp
COPY target/*.jar portfolio.jar
COPY wait-for-it.sh /backendapp/wait-for-it.sh
EXPOSE 8080
ENTRYPOINT ["java","-jar","portfolio.jar"]