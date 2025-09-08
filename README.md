DESCRIPTION: Spring boot project to manage portfolio data

LIVE DEPLOYMENT (GIT ACTIONS WITH AWS EC2 INSTANCE)

A. GIT ACTIONS YML

```
name: Portfolio CI/CD with Docker to EC2

on:
  push:
    branches:
      - main

jobs:

  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout repository
        uses: actions/checkout@v3

      - name: Set up JDK 23
        uses: actions/setup-java@v3
        with:
          java-version: '23'
          distribution: 'temurin'

      - name: Cache Maven packages
        uses: actions/cache@v3
        with:
          path: ~/.m2/repository
          key: ${{ runner.os }}-maven-${{ hashFiles('**/pom.xml') }}
          restore-keys: |
            ${{ runner.os }}-maven-

      - name: Build JAR
        run: ./mvnw clean package -DskipTests

  docker:
    runs-on: ubuntu-latest
    needs: build
    steps:
      - name: Checkout repository
        uses: actions/checkout@v3

      - name: Log in to Docker Hub
        uses: docker/login-action@v2
        with:
          username: ${{ secrets.DOCKER_HUB_USERNAME }}
          password: ${{ secrets.DOCKER_HUB_PASSWORD }}

      - name: Build and push Docker image
        uses: docker/build-push-action@v4
        with:
          context: .
          push: true
          tags: ${{ secrets.DOCKER_HUB_USERNAME }}/portfolio:latest
          cache-from: type=registry,ref=${{ secrets.DOCKER_HUB_USERNAME }}/portfolio:latest
          cache-to: type=inline

  deploy:
    runs-on: ubuntu-latest
    needs: docker
    steps:
      - name: Deploy via SSH
        uses: appleboy/ssh-action@v0.1.9
        with:
          host: ${{ secrets.EC2_HOST }}
          username: ${{ secrets.EC2_USER }}
          key: ${{ secrets.EC2_KEY }}
          script: |
            cd ~/portfolio
            docker-compose pull portfolio
            docker-compose up -d
            docker image prune -f
```

B. EC2 DOCKER COMPOSE YML (Within EC2 make directory porfolio)

```
version: '3.8'

services:
  portfolio:
    image: yourdockerhubusername/portfolio:latest
    container_name: portfolio_app
    depends_on:
      - db
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/portfolio
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: db-password
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
      SPRING_SERVLET_MULTIPART_MAX_FILE_SIZE: 10MB
      SPRING_SERVLET_MULTIPART_MAX_REQUEST_SIZE: 10MB
      IMAGE_PATH: public/images/
    volumes:
      - ./uploads:/portfolio_spring_app/public/images
    restart: always
    networks:
      - app-network

  db:
    image: postgres:15
    container_name: portfolio_db
    restart: always
    environment:
      POSTGRES_DB: portfolio
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: db-password
    volumes:
      - pgdata:/var/lib/postgresql/data
    networks:
      - app-network

volumes:
  pgdata:

networks:
  app-network:
    driver: bridge
```


            





