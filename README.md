# Health Check and User Management 

This project focuses on ensuring the connectivity of the database through a health check mechanism and provides functionality for managing user details, including adding users and updating their information.

## Introduction

The Health Check and User Management Project is a Spring Boot application designed to perform database connectivity checks and manage user details. It includes two main controllers: `HealthCheckController` for verifying database connectivity and `UserController` for user-related operations.

## Prerequisites

Before building and deploying the application locally, ensure you have the following prerequisites:

- Java Development Kit (JDK) 17
- Maven

## Health Check Controller

The `HealthCheckController` is responsible for handling health check requests to verify the status of the database connection. It provides endpoints for checking the database connection, handling unsupported HTTP methods, and managing unknown URLs.

### `HealthCheckController`

This class includes functionalities for checking the database connection and handling different HTTP methods.

### Endpoints

- `/healthz`: Verifies the database connection and handles specific conditions.
- `/healthz` (POST, PUT, PATCH, DELETE, HEAD, OPTIONS, TRACE): Handles method-not-allowed for specific HTTP methods.
- `/**`: Manages unknown URLs with a standard response.

## User Controller

The `UserController` manages user-related operations, such as adding users, retrieving user details, and updating user information. It also handles unsupported HTTP methods for both general user operations and self-related operations.

### Endpoints

- `POST /v1/user`: Adds a new user to the system.
- `GET /v1/user/self`: Retrieves details of the authenticated user.
- `PUT /v1/user/self`: Updates information for the authenticated user.
- `/v1/user`, `/v1/user/self` (GET, PUT, PATCH, DELETE, HEAD, OPTIONS, TRACE): Handles method-not-allowed for user-related endpoints.

## Configuration

The project uses a configuration file (`application.properties`) to manage database connectivity settings, Hibernate properties, and connection pool properties.

## Usage

To run the project locally, follow these steps:

1. Clone the repository: `git clone git@github.com:cloud-computing-csye6225-neu/webapp.git`
2. Configure the `application.properties` file with your database settings.
3. Build the project using Maven: mvn clean install -DskipTests=true
4. Run the application.


## Build and Deploy Instructions

1. Fork the GitHub repository in your namespace.
2. Clone your fork: `git clone git@github.com:cloud-computing-csye6225-neu/webapp.git`
3. Configure the `application.properties` file with your database settings.
4. Build the project using Maven: `mvn clean install -DskipTests=true`
5. Run the application locally.

## Continuous Integration Workflow

The project includes a continuous integration workflow using GitHub Actions. The workflow is triggered on the opening of a pull request to the main branch.

### CI Workflow Details

The workflow focuses on integration tests for the /v1/user endpoint.
The workflow performs the following steps:

1. Checkout source code.
2. Set up JDK 17.
3. start mysql
4. Build Maven: `mvn -B package --file pom.xml`



