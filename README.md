# Banking System

## Introduction

This documentation provides detailed information on setting up and configuring a banking system project, including a reverse proxy setup using Nginx on an EC2 instance to route traffic to a Metabase instance on Lightsail. It also covers Docker configuration, local development, and running the project.

## Project Overview

- Banking System Application: Core application managing accounts, transactions, and bank agencies.
- MongoDB: Database for storing application data.
- Metabase: Analytics tool for creating dashboards and visualizing data.
- Nginx: Reverse proxy server to route traffic to the Metabase instance.
- EC2 Instance: Hosts Nginx for reverse proxy.
- Lightsail Instance: Hosts the Metabase application.

## Customizing the Application

Before setting up and running the project, you might want to adjust certain parameters in the Main class. These parameters control various aspects of the application, such as the number of accounts, bank agencies, threads, and transactions, as well as geographical coordinates for data initialization.
```java
    private static final int NUMBER_OF_ACCOUNTS = 10;
private static final int NUMBER_OF_BANK_AGENCIES = 5;
private static final int NUMBER_OF_THREADS = 10;
private static final int TRANSACTIONS_PER_THREAD = 10;

// Coordinates range for Salvador - Bahia
private static final double MIN_LATITUDE = -13.0500;
private static final double MAX_LATITUDE = -12.9500;
private static final double MIN_LONGITUDE = -38.6000;
private static final double MAX_LONGITUDE = -38.4000;
```

## Setup Instructions

#### Clone the Repository
```shell
git clone git@github.com:acn3to/banking-system.git
```

#### Navigate to the Project Directory
```shell
cd banking-system
```

#### Build the Project

- Before running the application, build the project and package it into a JAR file.
- Ensure you have Maven installed to run the following command:
```shell
mvn clean package
```

#### Build and Start Docker Containers

- Ensure Docker and Docker Compose are installed on your system.
```shell
docker compose up -d
```

## MongoDB Access

To interact with MongoDB running in Docker, use the following command:

```shell
docker exec -it mongodb mongosh
```

## Metabase Access

After starting the Docker containers, access Metabase at http://localhost:3000 to visualize and analyze data.

## Deploy

You can also access the deployed Metabase instance on AWS to visualize and analyze data. Here are the details:
- URL: http://ec2-54-146-93-64.compute-1.amazonaws.com
- Login: test.user.metabase@gmail.com
- Password: u0efs5jr1z

> Feel free to explore the dashboards and play with the data generated. Metabase provides an intuitive interface for creating custom queries and visualizations, making it easy to gain insights and analyze the banking system's data.

![Metabase Screenshot](https://i.postimg.cc/wMhTYPxH/Screenshot-from-2024-07-25-20-03-17.png)

Here is a sharable link for this dashboard: [Access Dashboard](http://ec2-54-146-93-64.compute-1.amazonaws.com/public/dashboard/de36f4cd-c920-4bde-9895-51890b160420)

## Nginx Configuration

### EC2 Instance (Reverse Proxy)

The Nginx configuration on the EC2 instance routes traffic to the Metabase instance on Lightsail:
```
server {
    listen 80 default_server;
    listen [::]:80 default_server;

    server_name _;

    location / {
        proxy_pass http://your-lightsail-instance-ip:3000;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    root /var/www/html;
    index index.html index.htm;
}
```

### Note
> The Lightsail instance hosting the application is configured to be accessible only from the EC2 instance IP for security reasons. Ensure that your EC2 instance IP is whitelisted in the Lightsail instance settings to allow communication between the two instances.

## TODO

### 1. Implement Spring Boot API
- [ ] Transition the core banking application to use Spring Boot.
- [ ] Define RESTful endpoints for account management, transaction processing, and bank agency operations.

### 2. Migrate to Relational Database (PostgreSQL)
- [ ] Replace MongoDB with PostgreSQL.
- [ ] Update data models and repository layers to use JPA/Hibernate for database interactions.

### 3. Improve AWS Deployment Documentation
- [ ] Create detailed documentation for deploying the application on AWS, including:
  - **Setting up EC2 instances**: Step-by-step instructions for launching and configuring EC2 instances.
  - **Configuring Nginx**: How to configure Nginx as a reverse proxy for the application.
  - **Deploying Docker Containers**: Instructions for deploying Docker containers on EC2.
  - **Setting up RDS for PostgreSQL**: How to set up and connect to PostgreSQL RDS.
  - **Configuring Security Groups**: Steps for setting up appropriate security groups and firewall rules.
  - **Setting up Load Balancers**: How to configure AWS Elastic Load Balancing for your application.
  - **Automating Deployment with Elastic Beanstalk**: Instructions for using AWS Elastic Beanstalk for application deployment and management.

### 4. Testing and Quality Assurance
- [ ] Implement unit tests and integration tests for the new Spring Boot application.
- [ ] Set up code quality tools and ensure that the codebase adheres to quality standards.

### 5. Implement CI/CD Pipeline
- [ ] Set up a CI/CD pipeline using tools like GitHub Actions, Jenkins, or GitLab CI.
- [ ] Automate build, test, and deployment processes.
- [ ] Configure the pipeline to deploy to AWS upon successful build and tests.

### 6. Integrate Payment API
- [ ] Research and implement a payment API (e.g., Stripe, PayPal).
- [ ] Update transaction handling logic to interface with the payment provider's API.

### 7. Implement Microservices Architecture
- [ ] Decompose the monolithic banking application into microservices, such as:
  - **Account Service**: Manages user accounts.
  - **Transaction Service**: Handles transaction processing.
  - **Bank Agency Service**: Manages bank agency data.
  - **Payment Service**: Integrates with payment APIs.
- [ ] Define clear API contracts for inter-service communication.
- [ ] Implement service discovery (e.g., using Eureka or Consul) for locating and managing microservices.
- [ ] Configure API Gateway (e.g., using Spring Cloud Gateway) for routing and aggregating requests.

### 8. Enhance Security
- [ ] Implement SSL/TLS for secure connections to the application.
- [ ] Set up user authentication and authorization for accessing sensitive data and functionality.

### 9. Optimize Docker Setup
- [ ] Explore multi-stage builds in Docker to reduce image size.
- [ ] Set up automated testing and continuous integration for Docker builds.

### 10. Monitoring and Logging
- [ ] Set up application monitoring and logging using AWS CloudWatch or similar tools.
- [ ] Configure alerts and notifications for application performance and errors.

### 11. User Documentation
- [ ] Create comprehensive user documentation for interacting with the application.
- [ ] Include guides for managing accounts, performing transactions, and using additional features.

### 12. Developer Documentation
- [ ] Update developer documentation with information on the new Spring Boot setup.
- [ ] Include guidelines for contributing to the project, coding standards, and development workflow.
