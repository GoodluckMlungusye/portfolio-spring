FROM openjdk:23-jdk-slim
WORKDIR /portfolio_spring_app
COPY target/*.jar portfolio.jar
COPY wait-for-it.sh /portfolio_spring_app/wait-for-it.sh
RUN chmod +x /portfolio_spring_app/wait-for-it.sh
EXPOSE 8080
ENTRYPOINT ["java","-jar","portfolio.jar"]