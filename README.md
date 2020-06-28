# Lambda Gateway

A simple project to both showcase some of my java skills, and create a little nice service that serves as a HTTP gateway to AWS lambdas. Uses SparkJava/Jetty for HTTP comunication, AWS Java API for lambda communication, and it may use Spring, Guice or just factories for DI. 

## Requirements 

* Java (v14)
* Maven

## Installation

* `mvn clean install` to build and run tests to ensure that everything is running ok. Artifact is built to deploy/target.

* There is a docker container described into the `deploy` directory that runs the application. You should pass aws credentials as enviroment variables to container <https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html>. 

## Application

The application has a single HTTP endpoint, `POST /invoke/:lambda_name`, that forwards the HTTP body of its message to the lambda passed as url parameter. Authentication and HTTPS is not supported by the application itself, as the initial intent is to use it behind a reverse proxy (as nginx) that does this job. 
In case of a invalid or non-existent lambda name, HTTP 400 is returned. If there is a unknown internal error, the application returns HTTP 500. The contents of the lambda response is ignored, and it there are no issues in the execution, the application returns 200 for a non empty response, and 204 for a empty response.
