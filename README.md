# Cactus-Calendar
It is a fancy app for every cactus lover and not only. Checking weather conditions has never been so easy.

## Getting Started

With the Cactus Calendar, you can enjoy planning cactus planting without analyzing the weather conditions. After selecting the city, the application itself will show you the probability of accepting a cactus. Then just create a schedule on the day that suits you best. When the execution date of the plan has passed, it will go to the "Done" tab. Thanks to secure login, you don't have to worry about your plans. Have fun.

![Animation](https://github.com/Maciek0475/Private-things/blob/main/Animation.gif)

## About Project

The application is a combination of Spring MVC, on which the front end is built, and Spring Cloud, on which microservices are built. Initially, Cactus-Calendar was a monolith, but over time I changed its architecture to microservices. Calendar service is responsible for the front end and sending queries to other services to obtain data or save it in the database. Authentication is carried out via JWT tokens, The Calendar service creates a token for each query and sends it in the header to the appropriate service, api-gateway captures the header and verifies its correctness. I attached the necessary tests to the project and finally containerized everything.

### Technology Stack
Mostly used:
* [Spring Boot](https://spring.io/)
* [Mysql](https://www.mysql.com/)
* [JWT](https://jwt.io/)
* [Docker](https://www.docker.com/)
* [Zipkin](https://zipkin.io/)
* [Thymeleaf](https://www.thymeleaf.org/)
* [Bootstrap](https://getbootstrap.com/)

The project is an example of using several technologies, mainly including:
* Spring Security for register and login
* Spring Data JPA for database operations
* OpenFeign
* REST
* JWT
* Mysql
* Docker
* Weather API for weather data
* Thymeleaf and Bootstrap for frontend
And others.

### Running Locally

If you want to easily run this application, you must first have docker on your machine.

#### Start Application

Only one command is required to start Cactus-Calendar:

```console
docker compose up
```

#### Stop Application

If you want to stop the application you should use the command below:

```console
docker compose down
```

## Rest endpoints
Check out the list of microservices REST endpoints.

### User Panel

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET    | /api/panel/my-account | Get account info | USER | N/A |
| GET    | /api/panel/my-account/cities | Get cities | USER | N/A |
| GET    | /api/panel/my-account/get-city-id/{userId} | Get city id | USER | N/A |
| GET    | /api/panel/my-account/get-city/{cityId} | Get city by id | USER | N/A |
| PUT    | /api/panel/my-account/set-city | Set account city | USER | [JSON](#setcity) |

### Forecast

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET    | /api/forecast/{lat}/{lon} | Get forecast | USER | N/A |
| GET    | /api/forecast/{month}/{lat}/{lon} | Get daily forecast | USER | N/A |

### Plans

| Method | Url | Decription | Required privileges | Example of request body | 
| ------ | --- | ---------- | ------------------- | ----------------------- |
| GET    | /api/plans/{status} | Get plans by done status | USER | N/A |
| POST   | /api/plans | Save new plan | USER | [JSON](#savenewplan) |
| DELETE   | /api/plans/{id} | Delete plan | USER | N/A |


## Request body examples

#### <a id="setcity">Set City (/api/panel/my-account/set-city)</a>
```json
{
	"cityId": 1	
}
```

#### <a id="savenewplan">Save new plan (/api/plans)</a>
```json
{
	"date": "2024-06-24",
	"note": "Good day to plant my cactuses.",
	"successPropability": 78.3
}
```

## Author

* **Maciej Jurczak** 

See also other projects of [Maciek0475](https://github.com/Maciek0475).


