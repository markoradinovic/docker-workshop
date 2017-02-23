### User Service

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
