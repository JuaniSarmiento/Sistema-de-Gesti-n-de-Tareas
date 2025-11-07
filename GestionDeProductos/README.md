# 🛍️ API REST de Gestión de Productos

API REST profesional construida con Spring Boot para la gestión completa de productos (CRUD).

## 📋 Características

- ✅ CRUD completo de productos
- ✅ Validación de datos con Bean Validation
- ✅ Manejo global de excepciones
- ✅ Documentación interactiva con Swagger/OpenAPI
- ✅ Base de datos H2 en memoria
- ✅ Arquitectura en capas
- ✅ DTOs para request y response
- ✅ Filtrado por categorías

## 🏗️ Arquitectura

```
com.utn.productos
├── controller      # Controladores REST
├── service         # Lógica de negocio
├── repository      # Acceso a datos
├── model           # Entidades JPA
├── dto             # Data Transfer Objects
└── exception       # Manejo de excepciones
```

## 🚀 Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- IDE (IntelliJ IDEA o Eclipse)

## 📦 Instalación y Ejecución

### Desde IntelliJ IDEA

1. Abre el proyecto en IntelliJ IDEA
2. Espera a que Maven descargue las dependencias
3. Busca la clase `ProductosApiApplication.java`
4. Haz clic derecho → Run 'ProductosApiApplication'

### Desde Eclipse

1. File → Import → Existing Maven Projects
2. Selecciona la carpeta `productos-api`
3. Botón derecho en el proyecto → Run As → Spring Boot App

### Desde Línea de Comandos

```bash
mvn clean install
mvn spring-boot:run
```

La aplicación se iniciará en `http://localhost:8080`

## 📚 Documentación de la API (Swagger)

Una vez iniciada la aplicación, accede a:

🔗 **Swagger UI**: http://localhost:8080/swagger-ui/index.html

## 🗄️ Consola H2

Para ver la base de datos en memoria:

🔗 **H2 Console**: http://localhost:8080/h2-console

**Credenciales:**
- JDBC URL: `jdbc:h2:mem:productosdb`
- Username: `sa`
- Password: *(dejar vacío)*

## 🔌 Endpoints de la API

### Base URL: `/api/productos`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/productos` | Listar todos los productos |
| GET | `/api/productos/{id}` | Obtener producto por ID |
| GET | `/api/productos/categoria/{categoria}` | Filtrar por categoría |
| POST | `/api/productos` | Crear nuevo producto |
| PUT | `/api/productos/{id}` | Actualizar producto completo |
| PATCH | `/api/productos/{id}/stock` | Actualizar solo stock |
| DELETE | `/api/productos/{id}` | Eliminar producto |

## 📝 Ejemplos de Uso

### 1️⃣ Crear un Producto (POST)

**Endpoint:** `POST http://localhost:8080/api/productos`

**Body (JSON):**
```json
{
  "nombre": "Laptop HP Pavilion",
  "descripcion": "Laptop gaming con 16GB RAM y RTX 3060",
  "precio": 1299.99,
  "stock": 15,
  "categoria": "ELECTRONICA"
}
```

**Respuesta exitosa (201 Created):**
```json
{
  "id": 1,
  "nombre": "Laptop HP Pavilion",
  "descripcion": "Laptop gaming con 16GB RAM y RTX 3060",
  "precio": 1299.99,
  "stock": 15,
  "categoria": "ELECTRONICA"
}
```

### 2️⃣ Listar Todos los Productos (GET)

**Endpoint:** `GET http://localhost:8080/api/productos`

**Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Laptop HP Pavilion",
    "descripcion": "Laptop gaming con 16GB RAM y RTX 3060",
    "precio": 1299.99,
    "stock": 15,
    "categoria": "ELECTRONICA"
  }
]
```

### 3️⃣ Obtener Producto por ID (GET)

**Endpoint:** `GET http://localhost:8080/api/productos/1`

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "nombre": "Laptop HP Pavilion",
  "descripcion": "Laptop gaming con 16GB RAM y RTX 3060",
  "precio": 1299.99,
  "stock": 15,
  "categoria": "ELECTRONICA"
}
```

**Error - Producto no encontrado (404 Not Found):**
```json
{
  "timestamp": "2025-11-06T10:30:45",
  "status": 404,
  "error": "Producto no encontrado con ID: 999",
  "path": "/api/productos/999"
}
```

### 4️⃣ Filtrar por Categoría (GET)

**Endpoint:** `GET http://localhost:8080/api/productos/categoria/ELECTRONICA`

**Categorías disponibles:**
- ELECTRONICA
- ROPA
- ALIMENTOS
- HOGAR
- DEPORTES

### 5️⃣ Actualizar Producto Completo (PUT)

**Endpoint:** `PUT http://localhost:8080/api/productos/1`

**Body (JSON):**
```json
{
  "nombre": "Laptop HP Pavilion Gaming",
  "descripcion": "Laptop gaming actualizada",
  "precio": 1199.99,
  "stock": 20,
  "categoria": "ELECTRONICA"
}
```

### 6️⃣ Actualizar Solo Stock (PATCH)

**Endpoint:** `PATCH http://localhost:8080/api/productos/1/stock`

**Body (JSON):**
```json
{
  "stock": 50
}
```

### 7️⃣ Eliminar Producto (DELETE)

**Endpoint:** `DELETE http://localhost:8080/api/productos/1`

**Respuesta (204 No Content):** *(sin body)*

## ❌ Ejemplos de Errores

### Error de Validación (400 Bad Request)

**Request con datos inválidos:**
```json
{
  "nombre": "AB",
  "descripcion": "Descripción válida",
  "precio": -10,
  "stock": null,
  "categoria": null
}
```

**Respuesta:**
```json
{
  "timestamp": "2025-11-06T10:35:12",
  "status": 400,
  "error": "Error de validación: nombre: El nombre debe tener entre 3 y 100 caracteres, precio: El precio no puede ser negativo, stock: El stock no puede ser nulo, categoria: La categoría no puede ser nula",
  "path": "/api/productos"
}
```

### Producto No Encontrado (404 Not Found)

**Request:**
```
GET http://localhost:8080/api/productos/9999
```

**Respuesta:**
```json
{
  "timestamp": "2025-11-06T10:40:30",
  "status": 404,
  "error": "Producto no encontrado con ID: 9999",
  "path": "/api/productos/9999"
}
```

## 🧪 Cómo Probar en Swagger

1. Abre **Swagger UI**: http://localhost:8080/swagger-ui/index.html

2. **Crear un producto:**
   - Expande `POST /api/productos`
   - Click en "Try it out"
   - Pega el JSON de ejemplo
   - Click en "Execute"
   - Verifica el código de respuesta 201

3. **Listar todos:**
   - Expande `GET /api/productos`
   - Click en "Try it out"
   - Click en "Execute"
   - Verifica que aparece el producto creado

4. **Obtener por ID:**
   - Expande `GET /api/productos/{id}`
   - Ingresa el ID (ej: 1)
   - Click en "Execute"

5. **Filtrar por categoría:**
   - Expande `GET /api/productos/categoria/{categoria}`
   - Selecciona una categoría del dropdown
   - Click en "Execute"

6. **Actualizar stock:**
   - Expande `PATCH /api/productos/{id}/stock`
   - Ingresa el ID
   - Modifica el valor de stock
   - Click en "Execute"

7. **Eliminar producto:**
   - Expande `DELETE /api/productos/{id}`
   - Ingresa el ID
   - Click en "Execute"
   - Verifica código 204

## 🛠️ Tecnologías Utilizadas

- **Spring Boot 3.2.0** - Framework principal
- **Spring Data JPA** - Persistencia de datos
- **H2 Database** - Base de datos en memoria
- **Spring Validation** - Validación de datos
- **Lombok** - Reducción de código boilerplate
- **SpringDoc OpenAPI** - Documentación Swagger
- **Maven** - Gestión de dependencias

## 📁 Estructura del Proyecto

```
productos-api/
├── src/
│   ├── main/
│   │   ├── java/com/utn/productos/
│   │   │   ├── controller/
│   │   │   │   └── ProductoController.java
│   │   │   ├── service/
│   │   │   │   └── ProductoService.java
│   │   │   ├── repository/
│   │   │   │   └── ProductoRepository.java
│   │   │   ├── model/
│   │   │   │   ├── Producto.java
│   │   │   │   └── Categoria.java
│   │   │   ├── dto/
│   │   │   │   ├── ProductoDTO.java
│   │   │   │   ├── ProductoResponseDTO.java
│   │   │   │   └── ActualizarStockDTO.java
│   │   │   ├── exception/
│   │   │   │   ├── ProductoNotFoundException.java
│   │   │   │   ├── ErrorResponse.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   └── ProductosApiApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

## 💡 Validaciones Implementadas

### ProductoDTO:
- **nombre**: No nulo, no vacío, 3-100 caracteres
- **descripcion**: Máximo 500 caracteres
- **precio**: No nulo, no negativo
- **stock**: No nulo, no negativo
- **categoria**: No nulo, debe ser un valor válido del enum

### ActualizarStockDTO:
- **stock**: No nulo, no negativo

## 🎯 Características Técnicas

- ✅ Inyección de dependencias por constructor
- ✅ Arquitectura en capas (Controller → Service → Repository)
- ✅ Separación entre DTOs de request y response
- ✅ Manejo centralizado de excepciones con @ControllerAdvice
- ✅ Códigos HTTP apropiados (200, 201, 204, 400, 404)
- ✅ Transacciones con @Transactional
- ✅ Documentación completa con OpenAPI/Swagger

## 📧 Contacto

Proyecto creado para demostración de API REST con Spring Boot.

---

**¡Disfruta probando la API! 🚀**
