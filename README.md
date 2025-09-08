DESCRIPTION

Spring boot project to manage portfolio data

LIVE DEPLOYMENT (GIT ACTIONS WITH AWS EC2 INSTANCE)

A. GIT ACTIONS YML

```
name: Portfolio CI/CD with Docker to EC2

on:
  push:
    branches:
      - main

jobs:
  build-and-deploy:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout repository
        uses: actions/checkout@v3

      - name: Set up JDK 23
        uses: actions/setup-java@v3
        with:
          java-version: '23'
          distribution: 'temurin'

      - name: Build JAR
        run: ./mvnw clean package -DskipTests

      - name: Build Docker image
        run: docker build -t ${{ secrets.DOCKER_HUB_USERNAME }}/portfolio:latest .

      - name: Docker Hub login
        run: echo "${{ secrets.DOCKER_HUB_PASSWORD }}" | docker login -u "${{ secrets.DOCKER_HUB_USERNAME }}" --password-stdin

      - name: Push image to Docker Hub
        run: docker push ${{ secrets.DOCKER_HUB_USERNAME }}/portfolio:latest

      - name: Deploy on EC2
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


            





