# Spring-REST-API


Java Spring based API that performs CRUD operations for a user resource.
Uses a H2 database that writes to a file called `h2-database.mv.db`.

#### Running the application

You can run this app by loading it into IntelliJ any other IDE that can run Java projects and pressing the play button.

To run from the command line use the command: `./mvnw spring-boot:run` while inside the directory.

#### Endpoints

• POST /users 
• GET /users
• GET /users/{id}
• PUT /users/{id}
• DELETE /users/{id}

You can also access the H2 Database console using the endpoint `http://localhost:8080/h2-console/`

The JDBC URL is: `jdbc:h2:./h2-database`
The username is: `username`
The password is: `password`


