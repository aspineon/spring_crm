# spring_crm
A CRM rest backend application written in Spring Boot. It allows you to create customers and appointments for your customers.

## Technology stack
This application was built using the Springboot framework (https://projects.spring.io/spring-boot/), using a MVC approach to provide a REST backend microservice.

## Requirements
- Java 8+
- Maven 3+

## Installation
- On the root folder, run
`mvn clean package spring-boot:run`
- The application should be available at `http://localhost:8080`

## Usage
- To create a customer, run

`curl -XPOST -H "Content-type: application/json" -H "Accept: application/json" -d '{"name" : "test"}' http://localhost:8080/customers`

It should output something like
`{"id":1,"name":"test"}`

- To create an appointment, run
`curl -XPOST -H "Content-type: application/json" -H "Accept: application/json" -d '{"customerId" : 1, "scheduledAt": 1479298389760}' http://localhost:8080/appointments`

OBS: The `sheduledAt` field is on Jackson json format. Check it how to format your input date on (http://fasterxml.github.io/jackson-annotations/javadoc/2.0.0/com/fasterxml/jackson/annotation/JsonFormat.html)

- Appointments can be rated. So, to update the ratinhg, run

- To get a list of appointments, run

`curl -XPATCH -H "Content-type: application/json" -H "Accept: application/json" -d '{"rating": 30}' http://localhost:8080/appointments/1`

`curl  -H "Content-type: application/json" -H "Accept: application/json" http://localhost:8080/appointments/?page=0&size=20&startAt=1479298389760`

OBS: The `page` and `size` params are used to navigate through results with pagination

- To get a list of appointments by customer, run

`curl  -H "Content-type: application/json" -H "Accept: application/json" http://localhost:8080/appointments/customers/1?page=0&size=20`

## Future improvements
There are a log of features can be added to this application, such as:
- Add authentication and authorization. My choice for that would be Spring Oauth2 (https://projects.spring.io/spring-security-oauth/docs/oauth2.html)
- Create more data validations, like checking if the appointment date is in the future, and if there is no two appointments on the same time. 
- Add a duration field to the appointment. 
- Use an external database instead of the in-memory embedded database. 
- Add more unit test coverage.
 
 

