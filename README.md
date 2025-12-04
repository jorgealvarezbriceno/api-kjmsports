# KJM Sports API

API REST para la tienda deportiva KJM. Implementada con Spring Boot 3, MySQL y JPA. Incluye documentación OpenAPI (Swagger UI) y un cargador de datos de ejemplo para desarrollo.

## Stack
- Spring Boot 3.5.x
- Java 21
- Spring Data JPA (Hibernate)
- MySQL 8.x
- Lombok
- springdoc-openapi (Swagger UI)

## Endpoints
Base: `/api`

- Usuarios `/usuarios`
	- GET `/api/usuarios`
	- GET `/api/usuarios/{id}`
	- POST `/api/usuarios`
	- POST `/api/usuarios/login`
	- PUT `/api/usuarios/{id}`
	- DELETE `/api/usuarios/{id}`

- Productos `/productos`
	- GET `/api/productos`
	- GET `/api/productos/{id}`
	- POST `/api/productos`
	- PUT `/api/productos/{id}`
	- DELETE `/api/productos/{id}`

- Categorías `/categorias`
	- GET `/api/categorias`
	- GET `/api/categorias/{id}`
	- POST `/api/categorias`
	- PUT `/api/categorias/{id}`
	- DELETE `/api/categorias/{id}`

- Boletas `/boletas`
	- GET `/api/boletas`
	- POST `/api/boletas`
