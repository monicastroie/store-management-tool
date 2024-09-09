# Store Management Tool

This implementation mostly represents the management of products in a store. Within the project, different operations that can be performed on the products are presented.

## Technology Stack

- **Backend**: Java with Spring Boot - for creating REST calls
- **Database**: H2 Database - in memory database
- **Build and Dependency Management**: Managed with Maven
- **Security**: Spring Security with JWT for authentication
- **Object-Relational Mapping**: for database interaction
- **Code Simplification**: Lombok - for simplifying Java code
- **API Design**: RESTful APIs - for scalable and maintainable service architecture.

## Features

- Creation of products: Allows user to create a product
- List all the products: Allows admin to see all the products
- Delete a product: Allows admin to delete a product by ID
- Update a product: Allows a user to update a product by ID and a new version of Product object
- Update a product by price field: Allows a user to update the price of a product
- Update a product by quantity field: Allows a user to update the quantity of a product

## User Roles

- **Admin**: can see all the products and delete any product
- **User**: can create or update a product

## Implementation

I have generated my Spring Boot project using Spring Initialzr where I choose **Maven**, **Java 17**, **Spring Boot 3.3.3** and added all the **dependencies** that I considered important to start the implementation.
After all these I generated the project and I opened it in IntelliJ. 

### Database connection

1. Add the H2 dependency to my _pom.xml_ file.
2. Configure H2 Database in _application.properties_ file:
    - spring.datasource.url = sets the URL for the H2 Database
    - spring.h2.console.enabled = enables H2 web console, which can be accessed at http://localhost:8080/h2-console
    - username and password
3. Once the application is running, you can access the H2 console.

### Application Architecture

1. Controller Layer - handle incoming HTTP requests
This layer will include @RestController classes that map to specific API endpoints.
2. Service Layer - business logic
This layer will contain service classes that include business logic for managing products and users.
3. Repository Layer - data access
This layer will user spring Data JPA repositories to interact with database.
4. Entity Layer - data model
This layer represents the database tables, and they are annotated with JPA annotations like @Entity, @Table, @Id.
5. Database - to persist the data.

### Entity layer
I defined 2 entities in this application:
 - Product: represents the products in the store
 - User: represents the user management in authorization and authentication process

### Repository Layer
Repositories are interfaces that extend JpaRepository for each entity and they are used to interact with database.

### Service Layer
The service layer may handle product related logic, like adding new product, deleting products, updating price or quantity.

### Controller Layer
The @RestController classes handle incoming HTTP requests. They are using @RequestMapping/@GetMapping/@PostMapping, etc, to define the API endpoints.

### Security Layer
JWT (Json Web Token) is a stateless, token-based authentication system, where users authenticate by receiving a signed token that is then sent in the headers of requests to authenticate and authorize access to resources.
When a client send an HTTP request to our running application.
The first thing that is executed when our application receive the call is a filter which is once per request filter and has the role to validate and check everythinh regarding the token.
The first thing that will happen will be an internal check that verify that we have the JWT token present or not. If the token will be missing an error will be raised and the client will receive a 403 response.
If the JWT token is present then we will start the process of validation . This process will make a call using UserDetailsService to try to fetch the user information from the database. This validation will be done using the email value (extracted from the token).
Once the user is fetched, we will receive a response from the database. In case the user does not exist than we will send a response to client an HTTP 403 status and a message that the given user doesn't exist.
In case we receive a valid user from database, it will be started the validation process for the given JWT token.
Given the fact that JWT token is generated for a specific user, we have to validate this token based on his user.
So we have the validate JWT token mechanism that will call a service that will take as parameters the user itself and the token.
After the execution of this validation process we have two cases. First one, the token is not valid (ex: the token is expired, the token does not match that specific user). So, in this case we will send to client an HTTP 403 status with message "Invalid JWT token".
Othewise, we will update the SecurityContextHolder and will set this user as authenticated, and we will update the authentication manager. 
In moment in SecurityContextHolder will be updated, automatically dispatch the request and it be sent to the dispatcher servlet and from there will be sent to the controller. 
In the end, a response will be sent back to the client.

In order to implement this I needed a class that generate and validate a JWT token, process that uses a SECRET_KEY that has been stored in a properties file, and it is read from there.
I've created an User class that implements UserDetails, used to fetch user information during authentication.
Also, a filter class was created for intercepting request, do the checks for the JWT token, and set the authentication if the token is valid.
In the end, I need to configure Spring Security class to authenticate with JWT and to secure my API endpoints.

For register and login process, I've needed to create some DTOs (Data Transfer Objects) classes that represents the request body and the response, which contains the JWT token.

To test the security process implementation, I've created some user with different roles, and I defined role-based access control on APIs implementation.

### Exception Handling

### Application testing

### Flow example

### Conclusion