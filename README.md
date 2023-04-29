# Cactus-Calendar
Fancy app for every cactus lover and not only. Checking weather conditions has never been so easy.

## Getting Started

With the Cactus Calendar you can enjoy planning cactus planting without having to check the weather conditions. After selecting the city, the application itself will show you the probability of accepting a cactus. Then just create a schedule on the day that suits you best. When the execution date of the plan has passed, it will go to the "Done" tab. Thanks to secure login, you don't have to worry about your plans. Have fun.

![Animation](https://github.com/Maciek0475/Private-things/blob/main/Animation.gif)

### Technology Stack
Mostly used:
* [Spring Boot](https://spring.io/)
* [Mysql](https://www.mysql.com/)
* [Thymeleaf](https://www.thymeleaf.org/)
* [Bootstrap](https://getbootstrap.com/)

The project is an example of using several technologies, mainly including:
* Spring Security for register and login
* Spring Data Jpa for database operations
* Weather API for weather data
* Thymeleaf and Bootstrap for frontend
And others.

### Prerequisites

To run Cactus-Calendar you will need:

* [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* MYSQL

### Preparing Database

You have to create database and import `web-app.sql`. In `application.properties`
you can manipulate connection data as you like.

### Running Localy

You can run Cactus-Calendar from your IDE. All you have to do after import this project is execute `WebAppApplication.java` in package `com.mac2work.myfirstproject.webapp`.

Also you can run the following command in a terminal window (in the complete directory):

```
mvnw spring-boot:run
```

## Author

* **Maciej Jurczak** 

See also other projects of [Maciek0475](https://github.com/Maciek0475).

