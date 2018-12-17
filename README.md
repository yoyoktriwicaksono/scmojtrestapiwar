# This the example of Template Java WAR

## Requirement
1. Java 8
2. JaxRS
3. Swagger
4. Dependency Injection
5. Morphia for mongodb
6. Lombok
7. Modelmapper
8. cfg4j for configuration
9. Junit & Mockito for unit testing

## Deployment
Using WAR packaging

## Continues Integration
I use circleCI for continues integration

## Continues Deployment
I use heroku for continues Deployment.
A pipeline is created for QA and Production deployment.
1. scmojtrestapiqa pipeline is for QA. Please check https://scmojtrestapiqa.herokuapp.com/api/
2. scmojtrestapi pipeline is for Production. Please check https://scmojtrestapi.herokuapp.com/api/

## Issues
Please refer to Issue tab of github in this repository

## Project
Please refer to OJT project of project tab in this repository

## Developer Notes
### SWAGGER
1. Json    = http://localhost:8080/v1/swagger.json
2. UI      = http://localhost:8080/swagger/

### REST API
http://localhost:8080/v1/customer?customerName=a&phoneNumber=0&email=t

### JOBS
`
    <listener>
        <listener-class>org.scm.ojt.rest.jobs.SiteMapGenerator</listener-class>
    </listener>
`