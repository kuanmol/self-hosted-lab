```
docker run -d \
  --name mysql \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=shop \
  -e MYSQL_USER=app \
  -e MYSQL_PASSWORD=app \
  -p 3306:3306 \
  mysql:8
```

## Postman

```
http://localhost:8081/users

{
  "name": "John",
  "email": "john@mail.com"
}
```

```
POST http://localhost:8082/orders

{
  "product": "Laptop",
  "userId": 1
}
```

`GET http://localhost:8082/orders/1`

`kind create cluster --name spring-cluster`

`
helm uninstall user
helm uninstall order
helm uninstall gateway
helm uninstall mysql
`