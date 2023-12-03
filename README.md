* Hospital Management System

The Hospital Management System is a Java-based project designed to efficiently manage hospital staff, patients, and related activities. It encompasses features such as staff signup, patient admission, and more.
Table of Contents

    - Features
    - Getting Started
        - Prerequisites
        - Installation
    - Usage
    - Configuration
    - API Endpoints
    - Tests
    - Database Structure and relation

* Features

    - Staff signup with different roles (doctor, nurse, administrator).
    - Patient admission and discharge management.
    - Authentication and authorization using Spring Security.
    - RESTful API for interacting with the system.

* Getting Started
- Prerequisites

- Make sure you have the following installed:

    - Java Development Kit (JDK) 17 or later
    - Maven
    - PostgreSQL database

- Installation

    - Clone the repository:

    - bash

- git clone https://github.com/AtrijSharma/hospital-management-system.git

- Build the project using Maven:

- bash

    - cd hospital-management-system
    - mvn clean install

    - Create a PostgreSQL database and update the configuration in application.properties.

* Usage

    - Run the application:

    - bash

    - mvn spring-boot:run

    - Access the application at http://localhost:8080.

* Configuration

    Update the database configuration in src/main/resources/application.properties.
    Customize Spring Security configurations in com.hospitalManagement.staff.security.SecurityConfig.

* API Endpoints

    - POST /hospital/auth/signup: Signup a hospital staff member.
    - POST /hospital/auth/login: Authenticate and login a hospital staff member.
    - POST /hospital/patients/admit: Admit a new patient.
    - GET /hospital/patients/all: Get a list of all patients.
    - POST /hospital/patients/discharge/{patientId}: Discharge a patient.
    - GET /hospital/auth/current-user: Get information about the current authenticated user.

* Tests

- The project includes JUnit tests for services and controllers. Run the tests using:

- bash

- mvn test

- Contributing

* Database Structure and relation

- Table: HospitalStaff
  - id (INT)
  - username (VARCHAR)
  - password (VARCHAR)
  - type (VARCHAR)

- Table: Patient 
  - id (INT)
  - name (VARCHAR)
  - age (INT)
  - room (VARCHAR)
  - doctorName (VARCHAR)
  - admitDate (DATE)
  - expenses(DOUBLE)
  - status (VARCHAR)


Note : In order to connect DB , Please change the below credentails in application.properties (PATH : \staff\src\main\resources\application.properties )

spring.datasource.url=jdbc:postgresql://localhost:5432/hospitalmanagement (DB URL where Tables are present ) 
spring.datasource.username=postgres (Username to connect DB)
spring.datasource.password=root (Password to connect DB)


