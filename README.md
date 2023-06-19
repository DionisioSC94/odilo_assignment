# odilo_assignment

# OdiloSigningApp

[![Build Status](https://travis-ci.org/your-username/your-repo.svg?branch=master)](https://travis-ci.org/your-username/your-repo)

OdiloSigningApp is a Java 8 application built using Spring Boot and deployed on AWS Elastic Beanstalk. It integrates with an AWS RDS (PostgreSQL) database and an S3 bucket to provide a secure login and registration system for users. The application allows users to manage their certificates by uploading, updating, and viewing them on their personal pages.

## Introduction

OdiloSigningApp is designed to streamline the process of managing certificates for users. Upon successful login, users are redirected to their personal page, where they can see if they have a certificate uploaded. If no certificate is found, they have the option to upload one. If a certificate is already uploaded, they can update it with a new version. Only a reference to the latest certificate is stored in the database, while the actual document is securely stored in an S3 bucket.

The application leverages the power of AWS Elastic Beanstalk for deployment, providing scalability, reliability, and easy management of the application environment. The integration with AWS RDS ensures a robust and scalable database solution, while AWS S3 offers secure and durable storage for user certificates.

## Features

- User registration and login functionality
- Personalized user pages for managing certificates
- Upload, update, and view certificates securely
- Integration with AWS RDS for database storage
- Integration with AWS S3 for secure document storage

## Requirements

To run the OdiloSigningApp locally or deploy it on AWS Elastic Beanstalk, ensure that you have the following requirements met:

- Java 8 or higher installed
- Spring Boot framework
- AWS Elastic Beanstalk environment
- AWS RDS (PostgreSQL) database instance
- AWS S3 bucket for document storage
- Other dependencies as specified in the project

## Setup

Since the deployment has already been made and is not intended to be reproduced by the user, there is no specific setup required. The application is already deployed on AWS Elastic Beanstalk and connected to the RDS database and S3 bucket.

## Usage

To use OdiloSigningApp, follow these steps:

1. Access the deployed application using the provided URL: http://odilo-assignment.us-east-1.elasticbeanstalk.com/
2. Register a new user account or log in with an existing account.
3. Once logged in, you will be redirected to your personal page.
4. If no certificate is uploaded, you can upload one by following the provided instructions.
5. If a certificate is already uploaded, you can update it with a new version by following the provided instructions.
6. View your uploaded certificates and manage them as needed.

In the future, it is intended that the application hosts a well-functioning signature services which allows the user to upload documents and sign them user their certificate.

## Architecture

OdiloSigningApp follows a client-server architecture, where the client-side consists of the user interface (HTML (Thymeleaf), CSS, JavaScript) and the server-side is implemented using Java 8 and the Spring framework. 
The application is deployed on AWS Elastic Beanstalk, which provides easy management of the application environment.

The server-side interacts with the AWS RDS database for user and certificate data storage. The actual certificate documents are securely stored in an AWS S3 bucket. The integration with AWS services ensures a secure and scalable infrastructure for the application.

## Contributing

Contributions to OdiloSigningApp are welcome! If you have any bug reports, feature requests, or improvements, please submit them via GitHub issues or create pull requests following the provided guidelines. 

## Future plans: multitenancy 
The current implementation does not support multitenancy and lacks many security features to be used in real-life contexts. 
#### Deficiencies:
* Unit testing: for time constraints, no unit tests have been implemented so far. Junits tests should be implemented ensuring good coverage and gradle tasks should be configured to execute them. Moreover, this tasks should be executed before allowing merge of new codes as a form of regression testing (Jenkins pipeline for new PR)
* Security: security practices should be more present. This applies at the level of architecture (for example, hosting the DB on prive subnets, using IAM-roles for database managements, limiting access to database from specific IP addresses etc), as well as the level of the code (e.g. implementing strong requirements for the passwords, currently not done)
## License

OdiloSigningApp is distributed under the [MIT License](https://opensource.org/licenses/MIT).

---

.
