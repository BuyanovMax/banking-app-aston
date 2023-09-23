# Banking-app-aston

Application allows to manage bank accounts (create account, deposit/withdraw/transfer funds) via validated REST requests.
Pin-codes are hashed before being put in the database.

## Technologies (17 in total)
- **H2 database** for in-memory storing
- **Spring boot** for using starter dependencies
- **Spring Web** for REST implementation
- **Spring JPA, Liquibase, Hibernate** for database interaction
- **Spring Validation** for DTO checking
- **Spring Security Crytpto** for password encoding
- **Mockito, JUnit** for unit testing
- **JavaFaker** for preparing test data
- **Springdoc OpenApi, Swagger** for API description 
- **Lombok, Mapstruct** for code generation
- **Maven** as build tool
- **Docker** for building image and container

## Launch via Docker
  1) Download the project
  2) Open command console and move to the project folder
  3) Use this command to build the image:
```
docker build -t banking-app-aston -f Dockerfile .
 ```
  4) Then run the container with the command:
```
docker run -p 8080:8080 banking-app-aston
```
## Access the running app
- [API interaction via Swagger](http://localhost:8080/swagger-ui/index.html#/)
- [Direct database access](http://localhost:8080/h2-console/login.jsp?jsessionid=439f71ba29cf6816a3a01b22194cd606) > Make sure **JDBC URL** is set to ***jdbc:h2:mem:test***
