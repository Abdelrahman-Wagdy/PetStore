# PetStore API Testing Project

## Overview

This project contains automated API tests for the PetStore API using TestNG, RestAssured, and Allure for reporting. The tests cover CRUD operations for the User endpoints of the Swagger PetStore API.

## 🛠 Prerequisites

* Java 18 or higher
* Maven 3.6 or higher
* Docker (optional, for containerized execution)

## 📂 Project layout

```
PetStore/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── models/
│   │       │   └── UserModel.java
│   │       ├── services/
│   │       │   └── UserServices.java
│   │       └── utils/
│   │           └── Config.java
│   └── test/
│       ├── java/
│       │   └── usertests/
│       │       └── UserTests.java
│       └── resources/
│           ├── endpoints.properties
│           └── testng.xml
├── pom.xml
├── Dockerfile
```

## Dependencies

The project uses the following main dependencies:

- TestNG - Test framework
- RestAssured - API testing library
- Gson - JSON serialization/deserialization
- AssertJ - Fluent assertions
- JavaTuples - Tuple data structures
- Allure - Test reporting

## 🚀 Getting started
### Running Tests Locally

#### 1. Clone the repository

Run:
```bash
git clone https://github.com/Abdelrahman-Wagdy/quality-pro-task.git
cd PetStore
mvn clean test 
```
### Running Tests using Docker

#### 1. Clone the docker repo
```bash
docker pull abdelrahmanawagdy/petstoreapi:latest
```
#### 2. Run the docker image in a container
```bash
docker run -d --name myapp-container abdelrahmanawagdy/petstoreapi:latest
```
