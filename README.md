# API REST para un eCommerce

Este proyecto consiste en una **API REST para un eCommerce** con funcionalidades completas para la gestión de productos, carritos y pedidos. Implementa seguridad robusta con **Spring Security**, registro/login de usuarios, y confirmación de correos electrónicos mediante **Mailtrap**.

## Características principales

### Gestión de Productos
- CRUD completo (Crear, Leer, Actualizar, Eliminar).
- Endpoint seguro para acceso a datos de productos.

### Gestión del Carrito
- Añadir productos al carrito.
- Visualización del contenido del carrito.
- Opción para vaciar el carrito.

### Gestión de Pedidos
- Crear pedidos desde el carrito.
- Confirmación de pedidos a través de correo electrónico.

### Seguridad
- **Spring Security** para proteger rutas sensibles.
- Manejo de roles (usuarios y administradores).
- Registro y autenticación de usuarios.

### Confirmación por correo
- Uso de **Mailtrap** para envío simulado de correos:
  - Confirmación de registro.
  - Notificaciones de pedido.

### Persistencia de datos
- **Spring Data JPA** para la gestión de repositorios.
- Uso de **Hibernate** para la conexión con una base de datos relacional.

## Tecnologías utilizadas

- **Framework**: Spring Boot.
- **Seguridad**: Spring Security.
- **Base de Datos**: MySQL.
- **Correo Electrónico**: Mailtrap.
- **Pruebas**: Postman para testeo de endpoints.

## Endpoints principales

| Método HTTP | Ruta                 | Descripción                             |
|-------------|----------------------|-----------------------------------------|
| `POST`      | `/register`          | Registro de usuarios                    |
| `POST`      | `/login`             | Inicio de sesión                        |
| `GET`       | `/products`          | Listar todos los productos              |
| `POST`      | `/cart/add`          | Añadir un producto al carrito           |
| `POST`      | `/orders/create`     | Crear un nuevo pedido                   |
| `POST`      | `/orders/confirm`    | Confirmación de pedidos vía correo      |

