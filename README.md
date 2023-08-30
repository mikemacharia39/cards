# Cards

This a card API that allows you to create, edit, delete and view cards.

## Documentation
The Swagger API documentation is available on: `http://localhost:8000/swagger-ui/index.html`

The Open api documentation is available on: `http://localhost:8000/v3/api-docs`

## Running the application
To run the application, you need MySQL installed and then create the database card. \

You can also setup the application to run on h2 database by changing the application.properties file to use h2 database. \
You can uncomment the h2 database properties and comment the MySQL properties.
````
#spring.h2.console.enabled=true
#spring.datasource.url=jdbc:h2:mem:card;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
#spring.datasource.driverClassName=org.h2.Driver
#spring.datasource.username=sa
#spring.datasource.password=password
#spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
````
The only downside to running with *h2* is a prepared statement error, not sure why it's happening

Then run the application using the command below:
````
mvn spring-boot:run
````
