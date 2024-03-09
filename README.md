This is a sample Spring Webflux project, complete with Swagger

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Swagger can use traditional REST controller, or the reactive Router Function

This project also has r2dbc database that works with a postgres database
Settings in application.properties

Please create a table

```
CREATE TABLE person (
  id SERIAL PRIMARY KEY, 
  lastname VARCHAR (50)  NOT NULL, 
  firstname VARCHAR (50) NOT NULL, 
  email VARCHAR (255) 
);
```
