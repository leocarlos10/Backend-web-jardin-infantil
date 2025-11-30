# 📚 API REST - Documentación Completa

## 🌐 Base URL

```
http://localhost:8080/api/v1
```

## 🔐 Autenticación

Todos los endpoints (excepto `/auth/login` y `/auth/register`) requieren un token JWT en el header:

```
Authorization: Bearer <tu_token_jwt>
```

## 📋 Formato de Respuesta Estándar

Todas las respuestas siguen este formato:

```json
{
  "responseCode": 200,
  "responseMessage": "Mensaje descriptivo",
  "data": { ... },
  "errorList": null
}
```

---

# 🔑 AUTENTICACIÓN

## 1. Registro de Usuario

**POST** `/auth/register`

### Request Body
```json
{
  "nombre": "Juan Pérez",
  "email": "juan.perez@example.com",
  "password": "password123"
}
```

### Validaciones
- `nombre`: Requerido, 3-25 caracteres
- `email`: Requerido, formato email válido, máx 50 caracteres
- `password`: Requerido, máx 100 caracteres

### Response (201 Created)
```json
{
  "responseCode": 201,
  "responseMessage": "SUCCESS",
  "data": {
    "idUsuario": 1,
    "nombreUsuario": "Juan Pérez",
    "email": "juan.perez@example.com",
    "rol": "USUARIO"
  }
}
```

---

## 2. Iniciar Sesión

**POST** `/auth/login`

### Request Body
```json
{
  "email": "juan.perez@example.com",
  "password": "password123"
}
```

### Validaciones
- `email`: Requerido, formato email válido
- `password`: Requerido

### Response (200 OK)
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "user": {
    "idUsuario": 1,
    "nombreUsuario": "Juan Pérez",
    "email": "juan.perez@example.com",
    "rol": "USUARIO"
  }
}
```

### Errores Comunes
```json
// 401 Unauthorized - Credenciales inválidas
{
  "responseCode": 401,
  "responseMessage": "Credenciales inválidas"
}
```

---

# 📝 RESERVAS

## 1. Crear Reserva

**POST** `/reservas`  
🔒 Requiere autenticación

### Request Body
```json
{
  "estudianteId": 1,
  "gradoSolicitado": "Jardín"
}
```

### Validaciones
- `estudianteId`: Requerido, debe existir
- `gradoSolicitado`: Requerido, no vacío

### Response (201 Created)
```json
{
  "responseCode": 201,
  "responseMessage": "Reserva creada exitosamente",
  "data": {
    "idReserva": 1,
    "estudianteId": 1,
    "nombreEstudiante": "Sofía García",
    "estadoReserva": "PENDIENTE",
    "gradoSolicitado": "Jardín",
    "createdAt": "2024-11-30T10:15:30",
    "updatedAt": "2024-11-30T10:15:30"
  }
}
```

### Estados de Reserva
- `PENDIENTE`: Reserva creada, esperando aprobación
- `ACEPTADA`: Reserva aprobada por administrador
- `RECHAZADA`: Reserva rechazada

---

## 2. Obtener Reserva por ID

**GET** `/reservas/{id}`  
🔒 Requiere autenticación

### Path Parameters
- `id`: ID de la reserva

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Reserva encontrada",
  "data": {
    "idReserva": 1,
    "estudianteId": 1,
    "nombreEstudiante": "Sofía García",
    "estadoReserva": "ACEPTADA",
    "gradoSolicitado": "Jardín",
    "createdAt": "2024-11-30T10:15:30",
    "updatedAt": "2024-11-30T11:20:15"
  }
}
```

### Errores
```json
// 404 Not Found
{
  "responseCode": 404,
  "responseMessage": "Reserva no encontrada"
}
```

---

## 3. Listar Todas las Reservas

**GET** `/reservas`  
🔒 Requiere rol: **ADMIN**

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Lista de reservas obtenida",
  "data": [
    {
      "idReserva": 1,
      "estudianteId": 1,
      "nombreEstudiante": "Sofía García",
      "estadoReserva": "ACEPTADA",
      "gradoSolicitado": "Jardín",
      "createdAt": "2024-11-30T10:15:30",
      "updatedAt": "2024-11-30T11:20:15"
    },
    {
      "idReserva": 2,
      "estudianteId": 2,
      "nombreEstudiante": "Mateo García",
      "estadoReserva": "PENDIENTE",
      "gradoSolicitado": "Pre-jardín",
      "createdAt": "2024-11-30T12:30:00",
      "updatedAt": "2024-11-30T12:30:00"
    }
  ]
}
```

---

## 4. Listar Reservas por Estudiante

**GET** `/reservas/estudiante/{estudianteId}`  
🔒 Requiere autenticación

### Path Parameters
- `estudianteId`: ID del estudiante

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Reservas del estudiante obtenidas",
  "data": [
    {
      "idReserva": 1,
      "estudianteId": 1,
      "nombreEstudiante": "Sofía García",
      "estadoReserva": "ACEPTADA",
      "gradoSolicitado": "Jardín",
      "createdAt": "2024-11-30T10:15:30",
      "updatedAt": "2024-11-30T11:20:15"
    }
  ]
}
```

---

## 5. Aprobar Reserva

**PUT** `/reservas/{id}/aprobar`  
🔒 Requiere rol: **ADMIN**

### Path Parameters
- `id`: ID de la reserva

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Reserva aprobada exitosamente",
  "data": {
    "idReserva": 1,
    "estudianteId": 1,
    "nombreEstudiante": "Sofía García",
    "estadoReserva": "ACEPTADA",
    "gradoSolicitado": "Jardín",
    "createdAt": "2024-11-30T10:15:30",
    "updatedAt": "2024-11-30T14:45:20"
  }
}
```

### Eventos Generados
- 🎯 `RESERVA_APROBADA` - Notifica a listeners (email, logs, stats)

---

## 6. Rechazar Reserva

**PUT** `/reservas/{id}/rechazar`  
🔒 Requiere rol: **ADMIN**

### Path Parameters
- `id`: ID de la reserva

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Reserva rechazada",
  "data": {
    "idReserva": 2,
    "estudianteId": 2,
    "nombreEstudiante": "Mateo García",
    "estadoReserva": "RECHAZADA",
    "gradoSolicitado": "Pre-jardín",
    "createdAt": "2024-11-30T12:30:00",
    "updatedAt": "2024-11-30T14:50:10"
  }
}
```

### Eventos Generados
- 🎯 `RESERVA_RECHAZADA` - Notifica a listeners

---

# 🎓 MATRÍCULAS

## 1. Crear Matrícula

**POST** `/matriculas`  
🔒 Requiere autenticación

### Request Body
```json
{
  "estudianteId": 1,
  "fecha": "2024-01-15",
  "grado": "Jardín",
  "valorTotal": 1500000.00,
  "contratoFirmado": 1500000.00
}
```

### Validaciones
- `estudianteId`: Requerido, debe existir
- `fecha`: Requerido, formato YYYY-MM-DD
- `grado`: Requerido, no vacío
- `valorTotal`: Requerido, debe ser positivo
- `contratoFirmado`: Requerido

### Response (201 Created)
```json
{
  "responseCode": 201,
  "responseMessage": "Matrícula creada exitosamente",
  "data": {
    "idMatricula": 1,
    "estudianteId": 1,
    "nombreEstudiante": "Sofía García",
    "fecha": "2024-01-15",
    "grado": "Jardín",
    "valorTotal": 1500000.00,
    "contratoFirmado": 1500000.00,
    "estadoMatricula": "ACTIVA",
    "createdAt": "2024-11-30T10:15:30",
    "updatedAt": "2024-11-30T10:15:30"
  }
}
```

### Eventos Generados
- 🎯 `MATRICULA_CREADA` - Notifica a listeners (email, logs, stats)

---

## 2. Obtener Matrícula por ID

**GET** `/matriculas/{id}`  
🔒 Requiere autenticación

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Matrícula encontrada",
  "data": {
    "idMatricula": 1,
    "estudianteId": 1,
    "nombreEstudiante": "Sofía García",
    "fecha": "2024-01-15",
    "grado": "Jardín",
    "valorTotal": 1500000.00,
    "contratoFirmado": 1500000.00,
    "estadoMatricula": "ACTIVA",
    "createdAt": "2024-11-30T10:15:30",
    "updatedAt": "2024-11-30T10:15:30"
  }
}
```

---

## 3. Listar Todas las Matrículas

**GET** `/matriculas`  
🔒 Requiere rol: **ADMIN**

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Lista de matrículas obtenida",
  "data": [
    {
      "idMatricula": 1,
      "estudianteId": 1,
      "nombreEstudiante": "Sofía García",
      "fecha": "2024-01-15",
      "grado": "Jardín",
      "valorTotal": 1500000.00,
      "contratoFirmado": 1500000.00,
      "estadoMatricula": "ACTIVA",
      "createdAt": "2024-11-30T10:15:30",
      "updatedAt": "2024-11-30T10:15:30"
    }
  ]
}
```

---

## 4. Listar Matrículas por Estudiante

**GET** `/matriculas/estudiante/{estudianteId}`  
🔒 Requiere autenticación

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Matrículas del estudiante obtenidas",
  "data": [
    {
      "idMatricula": 1,
      "estudianteId": 1,
      "nombreEstudiante": "Sofía García",
      "fecha": "2024-01-15",
      "grado": "Jardín",
      "valorTotal": 1500000.00,
      "contratoFirmado": 1500000.00,
      "estadoMatricula": "ACTIVA",
      "createdAt": "2024-11-30T10:15:30",
      "updatedAt": "2024-11-30T10:15:30"
    }
  ]
}
```

---

## 5. Actualizar Matrícula

**PUT** `/matriculas/{id}`  
🔒 Requiere autenticación

### Request Body
```json
{
  "estudianteId": 1,
  "fecha": "2024-01-15",
  "grado": "Jardín",
  "valorTotal": 1600000.00,
  "contratoFirmado": 1600000.00
}
```

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Matrícula actualizada exitosamente",
  "data": {
    "idMatricula": 1,
    "estudianteId": 1,
    "nombreEstudiante": "Sofía García",
    "fecha": "2024-01-15",
    "grado": "Jardín",
    "valorTotal": 1600000.00,
    "contratoFirmado": 1600000.00,
    "estadoMatricula": "ACTIVA",
    "createdAt": "2024-11-30T10:15:30",
    "updatedAt": "2024-11-30T15:20:45"
  }
}
```

---

## 6. Cancelar Matrícula

**PUT** `/matriculas/{id}/cancelar`  
🔒 Requiere rol: **ADMIN**

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Matrícula cancelada",
  "data": {
    "idMatricula": 1,
    "estudianteId": 1,
    "nombreEstudiante": "Sofía García",
    "fecha": "2024-01-15",
    "grado": "Jardín",
    "valorTotal": 1500000.00,
    "contratoFirmado": 1500000.00,
    "estadoMatricula": "CANCELADA",
    "createdAt": "2024-11-30T10:15:30",
    "updatedAt": "2024-11-30T16:10:20"
  }
}
```

### Eventos Generados
- 🎯 `MATRICULA_CANCELADA` - Notifica a listeners

---

# 💰 PAGOS

## 1. Registrar Pago

**POST** `/pagos`  
🔒 Requiere autenticación

### Request Body
```json
{
  "matriculaId": 1,
  "fechaPago": "2024-01-15",
  "monto": 500000.00,
  "metodoPago": "Transferencia",
  "referencia": "REF001",
  "comprobante": "comprobante001.pdf"
}
```

### Validaciones
- `matriculaId`: Requerido, debe existir
- `fechaPago`: Requerido, formato YYYY-MM-DD
- `monto`: Requerido, debe ser positivo
- `metodoPago`: Requerido (Efectivo, Transferencia, Tarjeta)
- `referencia`: Opcional
- `comprobante`: Opcional

### Response (201 Created)
```json
{
  "responseCode": 201,
  "responseMessage": "Pago registrado exitosamente",
  "data": {
    "idPago": 1,
    "matriculaId": 1,
    "fechaPago": "2024-01-15",
    "monto": 500000.00,
    "metodoPago": "Transferencia",
    "referencia": "REF001",
    "estadoPago": "PENDIENTE",
    "comprobante": "comprobante001.pdf",
    "createdAt": "2024-11-30T10:15:30",
    "updatedAt": "2024-11-30T10:15:30"
  }
}
```

### Estados de Pago
- `PENDIENTE`: Pago registrado, esperando verificación
- `VERIFICADO`: Pago verificado por administrador
- `RECHAZADO`: Pago rechazado

### Eventos Generados
- 🎯 `PAGO_REGISTRADO` - Notifica a listeners

---

## 2. Obtener Pago por ID

**GET** `/pagos/{id}`  
🔒 Requiere autenticación

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Pago encontrado",
  "data": {
    "idPago": 1,
    "matriculaId": 1,
    "fechaPago": "2024-01-15",
    "monto": 500000.00,
    "metodoPago": "Transferencia",
    "referencia": "REF001",
    "estadoPago": "VERIFICADO",
    "comprobante": "comprobante001.pdf",
    "createdAt": "2024-11-30T10:15:30",
    "updatedAt": "2024-11-30T11:20:15"
  }
}
```

---

## 3. Listar Pagos por Matrícula

**GET** `/pagos/matricula/{matriculaId}`  
🔒 Requiere autenticación

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Historial de pagos obtenido",
  "data": [
    {
      "idPago": 1,
      "matriculaId": 1,
      "fechaPago": "2024-01-15",
      "monto": 500000.00,
      "metodoPago": "Transferencia",
      "referencia": "REF001",
      "estadoPago": "VERIFICADO",
      "comprobante": "comprobante001.pdf",
      "createdAt": "2024-11-30T10:15:30",
      "updatedAt": "2024-11-30T11:20:15"
    },
    {
      "idPago": 2,
      "matriculaId": 1,
      "fechaPago": "2024-02-15",
      "monto": 500000.00,
      "metodoPago": "Efectivo",
      "referencia": "REF002",
      "estadoPago": "VERIFICADO",
      "comprobante": null,
      "createdAt": "2024-11-30T12:30:00",
      "updatedAt": "2024-11-30T13:15:20"
    }
  ]
}
```

---

## 4. Listar Todos los Pagos

**GET** `/pagos`  
🔒 Requiere rol: **ADMIN**

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Lista de pagos obtenida",
  "data": [
    {
      "idPago": 1,
      "matriculaId": 1,
      "fechaPago": "2024-01-15",
      "monto": 500000.00,
      "metodoPago": "Transferencia",
      "referencia": "REF001",
      "estadoPago": "VERIFICADO",
      "comprobante": "comprobante001.pdf",
      "createdAt": "2024-11-30T10:15:30",
      "updatedAt": "2024-11-30T11:20:15"
    }
  ]
}
```

---

## 5. Verificar Pago

**PUT** `/pagos/{id}/verificar`  
🔒 Requiere rol: **ADMIN**

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Pago verificado exitosamente",
  "data": {
    "idPago": 1,
    "matriculaId": 1,
    "fechaPago": "2024-01-15",
    "monto": 500000.00,
    "metodoPago": "Transferencia",
    "referencia": "REF001",
    "estadoPago": "VERIFICADO",
    "comprobante": "comprobante001.pdf",
    "createdAt": "2024-11-30T10:15:30",
    "updatedAt": "2024-11-30T14:45:20"
  }
}
```

### Eventos Generados
- 🎯 `PAGO_VERIFICADO` - Notifica a listeners (email, logs, stats)

---

## 6. Rechazar Pago

**PUT** `/pagos/{id}/rechazar`  
🔒 Requiere rol: **ADMIN**

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Pago rechazado",
  "data": {
    "idPago": 3,
    "matriculaId": 2,
    "fechaPago": "2024-01-20",
    "monto": 600000.00,
    "metodoPago": "Transferencia",
    "referencia": "REF003",
    "estadoPago": "RECHAZADO",
    "comprobante": "comprobante003.pdf",
    "createdAt": "2024-11-30T13:00:00",
    "updatedAt": "2024-11-30T14:50:10"
  }
}
```

### Eventos Generados
- 🎯 `PAGO_RECHAZADO` - Notifica a listeners

---

# 👨‍👩‍👧‍👦 ESTUDIANTES

## 1. Crear Estudiante

**POST** `/estudiantes`  
🔒 Requiere autenticación

### Request Body
```json
{
  "acudienteId": 1,
  "nombre": "Sofía",
  "apellido": "García",
  "fechaNacimiento": "2019-03-15",
  "tipoDocumento": "TI",
  "numeroDocumento": "1111111111",
  "genero": "F",
  "direccion": "Calle 1 #2-3",
  "telefono": "3001111111"
}
```

### Validaciones
- `acudienteId`: Requerido, debe existir
- `nombre`: Requerido, no vacío
- `apellido`: Requerido, no vacío
- `fechaNacimiento`: Requerido, formato YYYY-MM-DD
- `tipoDocumento`: Requerido (TI, RC, CC)
- `numeroDocumento`: Requerido, único
- `genero`: Requerido (M, F)
- `direccion`: Opcional
- `telefono`: Opcional

### Response (201 Created)
```json
{
  "responseCode": 201,
  "responseMessage": "Estudiante creado exitosamente",
  "data": {
    "estudianteId": 1,
    "acudienteId": 1,
    "nombreAcudiente": "María García",
    "nombre": "Sofía",
    "apellido": "García",
    "fechaNacimiento": "2019-03-15",
    "tipoDocumento": "TI",
    "numeroDocumento": "1111111111",
    "genero": "F",
    "direccion": "Calle 1 #2-3",
    "telefono": "3001111111",
    "createdAt": "2024-11-30T10:15:30",
    "updatedAt": "2024-11-30T10:15:30"
  }
}
```

### Eventos Generados
- 🎯 `ESTUDIANTE_CREADO` - Notifica a listeners

### Errores Comunes
```json
// 409 Conflict - Documento duplicado
{
  "responseCode": 409,
  "responseMessage": "Ya existe un estudiante con el documento 1111111111"
}
```

---

## 2. Obtener Estudiante por ID

**GET** `/estudiantes/{id}`  
🔒 Requiere autenticación

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Estudiante encontrado",
  "data": {
    "estudianteId": 1,
    "acudienteId": 1,
    "nombreAcudiente": "María García",
    "nombre": "Sofía",
    "apellido": "García",
    "fechaNacimiento": "2019-03-15",
    "tipoDocumento": "TI",
    "numeroDocumento": "1111111111",
    "genero": "F",
    "direccion": "Calle 1 #2-3",
    "telefono": "3001111111",
    "createdAt": "2024-11-30T10:15:30",
    "updatedAt": "2024-11-30T10:15:30"
  }
}
```

---

## 3. Listar Todos los Estudiantes

**GET** `/estudiantes`  
🔒 Requiere rol: **ADMIN**

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Lista de estudiantes obtenida",
  "data": [
    {
      "estudianteId": 1,
      "acudienteId": 1,
      "nombreAcudiente": "María García",
      "nombre": "Sofía",
      "apellido": "García",
      "fechaNacimiento": "2019-03-15",
      "tipoDocumento": "TI",
      "numeroDocumento": "1111111111",
      "genero": "F",
      "direccion": "Calle 1 #2-3",
      "telefono": "3001111111",
      "createdAt": "2024-11-30T10:15:30",
      "updatedAt": "2024-11-30T10:15:30"
    },
    {
      "estudianteId": 2,
      "acudienteId": 1,
      "nombreAcudiente": "María García",
      "nombre": "Mateo",
      "apellido": "García",
      "fechaNacimiento": "2020-07-22",
      "tipoDocumento": "TI",
      "numeroDocumento": "2222222222",
      "genero": "M",
      "direccion": "Calle 1 #2-3",
      "telefono": "3001111111",
      "createdAt": "2024-11-30T11:20:15",
      "updatedAt": "2024-11-30T11:20:15"
    }
  ]
}
```

---

## 4. Listar Estudiantes por Acudiente

**GET** `/estudiantes/acudiente/{acudienteId}`  
🔒 Requiere autenticación

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Estudiantes del acudiente obtenidos",
  "data": [
    {
      "estudianteId": 1,
      "acudienteId": 1,
      "nombreAcudiente": "María García",
      "nombre": "Sofía",
      "apellido": "García",
      "fechaNacimiento": "2019-03-15",
      "tipoDocumento": "TI",
      "numeroDocumento": "1111111111",
      "genero": "F",
      "direccion": "Calle 1 #2-3",
      "telefono": "3001111111",
      "createdAt": "2024-11-30T10:15:30",
      "updatedAt": "2024-11-30T10:15:30"
    }
  ]
}
```

---

## 5. Actualizar Estudiante

**PUT** `/estudiantes/{id}`  
🔒 Requiere autenticación

### Request Body
```json
{
  "acudienteId": 1,
  "nombre": "Sofía",
  "apellido": "García Rodríguez",
  "fechaNacimiento": "2019-03-15",
  "tipoDocumento": "TI",
  "numeroDocumento": "1111111111",
  "genero": "F",
  "direccion": "Calle 1 #2-3, Apto 101",
  "telefono": "3001111111"
}
```

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Estudiante actualizado exitosamente",
  "data": {
    "estudianteId": 1,
    "acudienteId": 1,
    "nombreAcudiente": "María García",
    "nombre": "Sofía",
    "apellido": "García Rodríguez",
    "fechaNacimiento": "2019-03-15",
    "tipoDocumento": "TI",
    "numeroDocumento": "1111111111",
    "genero": "F",
    "direccion": "Calle 1 #2-3, Apto 101",
    "telefono": "3001111111",
    "createdAt": "2024-11-30T10:15:30",
    "updatedAt": "2024-11-30T15:30:45"
  }
}
```

### Eventos Generados
- 🎯 `ESTUDIANTE_ACTUALIZADO` - Notifica a listeners

---

## 6. Eliminar Estudiante

**DELETE** `/estudiantes/{id}`  
🔒 Requiere rol: **ADMIN**

### Response (200 OK)
```json
{
  "responseCode": 200,
  "responseMessage": "Estudiante eliminado exitosamente"
}
```

### Errores
```json
// 404 Not Found
{
  "responseCode": 404,
  "responseMessage": "Estudiante no encontrado"
}
```

---

# 📊 CÓDIGOS DE RESPUESTA HTTP

| Código | Significado | Cuándo se usa |
|--------|-------------|---------------|
| 200 | OK | Operación exitosa (GET, PUT, DELETE) |
| 201 | Created | Recurso creado exitosamente (POST) |
| 400 | Bad Request | Datos de entrada inválidos |
| 401 | Unauthorized | Token inválido o no proporcionado |
| 403 | Forbidden | No tiene permisos para esta operación |
| 404 | Not Found | Recurso no encontrado |
| 409 | Conflict | Conflicto (ej: documento duplicado) |
| 500 | Internal Server Error | Error del servidor |

---

# 🔒 ROLES Y PERMISOS

## Roles Disponibles

| Rol | Descripción |
|-----|-------------|
| `USUARIO` | Usuario básico registrado |
| `ACUDIENTE` | Padre/tutor de estudiantes |
| `ADMINISTRADOR` | Acceso completo al sistema |

## Matriz de Permisos

| Endpoint | USUARIO | ACUDIENTE | ADMIN |
|----------|---------|-----------|-------|
| POST /auth/register | ✅ | ✅ | ✅ |
| POST /auth/login | ✅ | ✅ | ✅ |
| POST /reservas | ✅ | ✅ | ✅ |
| GET /reservas/{id} | ✅ | ✅ | ✅ |
| GET /reservas | ❌ | ❌ | ✅ |
| PUT /reservas/{id}/aprobar | ❌ | ❌ | ✅ |
| PUT /reservas/{id}/rechazar | ❌ | ❌ | ✅ |
| POST /matriculas | ✅ | ✅ | ✅ |
| GET /matriculas | ❌ | ❌ | ✅ |
| PUT /matriculas/{id}/cancelar | ❌ | ❌ | ✅ |
| POST /pagos | ✅ | ✅ | ✅ |
| GET /pagos | ❌ | ❌ | ✅ |
| PUT /pagos/{id}/verificar | ❌ | ❌ | ✅ |
| PUT /pagos/{id}/rechazar | ❌ | ❌ | ✅ |
| POST /estudiantes | ✅ | ✅ | ✅ |
| GET /estudiantes | ❌ | ❌ | ✅ |
| DELETE /estudiantes/{id} | ❌ | ❌ | ✅ |

---

# 🎯 EVENTOS DEL SISTEMA (Patrón Observer)

Cuando ocurren ciertas acciones, el sistema genera eventos automáticamente:

| Evento | Cuándo se genera | Listeners activos |
|--------|------------------|-------------------|
| `RESERVA_CREADA` | Al crear una reserva | Logging, Statistics |
| `RESERVA_APROBADA` | Al aprobar una reserva | Logging, Email, Statistics |
| `RESERVA_RECHAZADA` | Al rechazar una reserva | Logging, Email, Statistics |
| `MATRICULA_CREADA` | Al crear una matrícula | Logging, Email, Statistics |
| `MATRICULA_CANCELADA` | Al cancelar una matrícula | Logging, Statistics |
| `PAGO_REGISTRADO` | Al registrar un pago | Logging, Statistics |
| `PAGO_VERIFICADO` | Al verificar un pago | Logging, Email, Statistics |
| `PAGO_RECHAZADO` | Al rechazar un pago | Logging, Email, Statistics |
| `ESTUDIANTE_CREADO` | Al crear un estudiante | Logging, Statistics |
| `ESTUDIANTE_ACTUALIZADO` | Al actualizar un estudiante | Logging, Statistics |

### Listeners Activos

- **LoggingListener**: Registra todos los eventos en logs de auditoría
- **EmailNotificationListener**: Envía emails para eventos importantes
- **StatisticsListener**: Mantiene contadores de eventos

---

# 🧪 EJEMPLOS DE USO CON CURL

## 1. Registro y Login

```bash
# Registrarse
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan Pérez",
    "email": "juan.perez@example.com",
    "password": "password123"
  }'

# Login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "juan.perez@example.com",
    "password": "password123"
  }'

# Guardar el token de la respuesta
TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

## 2. Crear Reserva

```bash
curl -X POST http://localhost:8080/api/v1/reservas \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "estudianteId": 1,
    "gradoSolicitado": "Jardín"
  }'
```

## 3. Aprobar Reserva (Admin)

```bash
curl -X PUT http://localhost:8080/api/v1/reservas/1/aprobar \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

## 4. Crear Matrícula

```bash
curl -X POST http://localhost:8080/api/v1/matriculas \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "estudianteId": 1,
    "fecha": "2024-01-15",
    "grado": "Jardín",
    "valorTotal": 1500000.00,
    "contratoFirmado": 1500000.00
  }'
```

## 5. Registrar Pago

```bash
curl -X POST http://localhost:8080/api/v1/pagos \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "matriculaId": 1,
    "fechaPago": "2024-01-15",
    "monto": 500000.00,
    "metodoPago": "Transferencia",
    "referencia": "REF001",
    "comprobante": "comprobante001.pdf"
  }'
```

## 6. Crear Estudiante

```bash
curl -X POST http://localhost:8080/api/v1/estudiantes \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "acudienteId": 1,
    "nombre": "Sofía",
    "apellido": "García",
    "fechaNacimiento": "2019-03-15",
    "tipoDocumento": "TI",
    "numeroDocumento": "1111111111",
    "genero": "F",
    "direccion": "Calle 1 #2-3",
    "telefono": "3001111111"
  }'
```

---

# ⚠️ MANEJO DE ERRORES

## Errores de Validación (400)

```json
{
  "responseCode": 400,
  "responseMessage": "Validation failed",
  "errorList": [
    {
      "field": "email",
      "message": "Please provide a valid email address"
    },
    {
      "field": "nombre",
      "message": "El nombre es requerido"
    }
  ]
}
```

## Error de Autenticación (401)

```json
{
  "responseCode": 401,
  "responseMessage": "Token inválido o expirado"
}
```

## Error de Permisos (403)

```json
{
  "responseCode": 403,
  "responseMessage": "No tiene permisos para realizar esta operación"
}
```

## Recurso No Encontrado (404)

```json
{
  "responseCode": 404,
  "responseMessage": "Reserva no encontrada"
}
```

## Conflicto (409)

```json
{
  "responseCode": 409,
  "responseMessage": "Ya existe un estudiante con el documento 1111111111"
}
```

---

# 📝 NOTAS IMPORTANTES

## Fechas
- Todas las fechas se envían en formato `YYYY-MM-DD`
- Las fechas de respuesta incluyen hora: `2024-11-30T10:15:30`

## Números Decimales
- Los montos se envían como números: `1500000.00`
- Usar punto (.) como separador decimal

## Tokens JWT
- Los tokens expiran en 24 horas (configurable)
- Incluir en header: `Authorization: Bearer <token>`
- Renovar token haciendo login nuevamente

## Estados
- **Reserva**: PENDIENTE, ACEPTADA, RECHAZADA
- **Matrícula**: ACTIVA, CANCELADA
- **Pago**: PENDIENTE, VERIFICADO, RECHAZADO

## Datos de Prueba
Ver archivo `DATOS_SEMILLA.md` para usuarios y datos de prueba.

---

# 🔗 RECURSOS ADICIONALES

- **Patrón Observer**: Ver `PATRON_OBSERVER_DOCUMENTACION.md`
- **Datos de Semilla**: Ver `DATOS_SEMILLA.md`
- **Guía de Implementación**: Ver `IMPLEMENTATION_GUIDE.md`

---

**Última actualización**: Noviembre 2024  
**Versión API**: v1  
**Base URL**: `http://localhost:8080/api/v1`
