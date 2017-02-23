### User Service

You will need [PostgresSql](https://hub.docker.com/_/postgres/) and optionally [PgAdmin4](https://hub.docker.com/r/fenglc/pgadmin4/).

*Add User*

POST /api/v1/User
```
{
	"username": "marko",
	"password": "marko",
	"email": "marko.radinovic@codecentric.de",
	"firstName": "marko",
	"lastName": "radinovic"
}
```

*Login*

POST /api/v1/login
```
{
	"username": "marko",
	"password": "marko"
}
```
