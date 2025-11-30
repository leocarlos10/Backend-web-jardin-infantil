# 📮 Colección Postman/Insomnia - API Jardín Infantil

## 🚀 Configuración Inicial

### Variables de Entorno

Crear las siguientes variables:

```
base_url = http://localhost:8080/api/v1
token = (se llenará automáticamente después del login)
```

---

## 📁 COLECCIÓN COMPLETA

### 1. AUTH - Autenticación

#### 1.1 Registro
```
POST {{base_url}}/auth/register
Content-Type: application/json

{
  "nombre": "Juan Pérez",
  "email": "juan.perez@example.com",
  "password": "password123"
}
```

#### 1.2 Login
```
POST {{base_url}}/auth/login
Content-Type: application/json

{
  "email": "admin@jardin.test",
  "password": "password123"
}

# Script Post-Response (Postman):
pm.environment.set("token", pm.response.json().token);
```

---

### 2. RESERVAS

#### 2.1 Crear Reserva
```
POST {{base_url}}/reservas
Authorization: Bearer {{token}}
Content-Type: application/json

{
  "estudianteId": 1,
  "gradoSolicitado": "Jardín"
}
```

#### 2.2 Obtener Reserva
```
GET {{base_url}}/reservas/1
Authorization: Bearer {{token}}
```

#### 2.3 Listar Todas (Admin)
```
GET {{base_url}}/reservas
Authorization: Bearer {{token}}
```

#### 2.4 Listar por Estudiante
```
GET {{base_url}}/reservas/estudiante/1
Authorization: Bearer {{token}}
```

#### 2.5 Aprobar Reserva (Admin)
```
PUT {{base_url}}/reservas/1/aprobar
Authorization: Bearer {{token}}
```

#### 2.6 Rechazar Reserva (Admin)
```
PUT {{base_url}}/reservas/1/rechazar
Authorization: Bearer {{token}}
```

---

### 3. MATRÍCULAS

#### 3.1 Crear Matrícula
```
POST {{base_url}}/matriculas
Authorization: Bearer {{token}}
Content-Type: application/json

{
  "estudianteId": 1,
  "fecha": "2024-01-15",
  "grado": "Jardín",
  "valorTotal": 1500000.00,
  "contratoFirmado": 1500000.00
}
```

#### 3.2 Obtener Matrícula
```
GET {{base_url}}/matriculas/1
Authorization: Bearer {{token}}
```

#### 3.3 Listar Todas (Admin)
```
GET {{base_url}}/matriculas
Authorization: Bearer {{token}}
```

#### 3.4 Listar por Estudiante
```
GET {{base_url}}/matriculas/estudiante/1
Authorization: Bearer {{token}}
```

#### 3.5 Actualizar Matrícula
```
PUT {{base_url}}/matriculas/1
Authorization: Bearer {{token}}
Content-Type: application/json

{
  "estudianteId": 1,
  "fecha": "2024-01-15",
  "grado": "Jardín",
  "valorTotal": 1600000.00,
  "contratoFirmado": 1600000.00
}
```

#### 3.6 Cancelar Matrícula (Admin)
```
PUT {{base_url}}/matriculas/1/cancelar
Authorization: Bearer {{token}}
```

---

### 4. PAGOS

#### 4.1 Registrar Pago
```
POST {{base_url}}/pagos
Authorization: Bearer {{token}}
Content-Type: application/json

{
  "matriculaId": 1,
  "fechaPago": "2024-01-15",
  "monto": 500000.00,
  "metodoPago": "Transferencia",
  "referencia": "REF001",
  "comprobante": "comprobante001.pdf"
}
```

#### 4.2 Obtener Pago
```
GET {{base_url}}/pagos/1
Authorization: Bearer {{token}}
```

#### 4.3 Listar por Matrícula
```
GET {{base_url}}/pagos/matricula/1
Authorization: Bearer {{token}}
```

#### 4.4 Listar Todos (Admin)
```
GET {{base_url}}/pagos
Authorization: Bearer {{token}}
```

#### 4.5 Verificar Pago (Admin)
```
PUT {{base_url}}/pagos/1/verificar
Authorization: Bearer {{token}}
```

#### 4.6 Rechazar Pago (Admin)
```
PUT {{base_url}}/pagos/1/rechazar
Authorization: Bearer {{token}}
```

---

### 5. ESTUDIANTES

#### 5.1 Crear Estudiante
```
POST {{base_url}}/estudiantes
Authorization: Bearer {{token}}
Content-Type: application/json

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

#### 5.2 Obtener Estudiante
```
GET {{base_url}}/estudiantes/1
Authorization: Bearer {{token}}
```

#### 5.3 Listar Todos (Admin)
```
GET {{base_url}}/estudiantes
Authorization: Bearer {{token}}
```

#### 5.4 Listar por Acudiente
```
GET {{base_url}}/estudiantes/acudiente/1
Authorization: Bearer {{token}}
```

#### 5.5 Actualizar Estudiante
```
PUT {{base_url}}/estudiantes/1
Authorization: Bearer {{token}}
Content-Type: application/json

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

#### 5.6 Eliminar Estudiante (Admin)
```
DELETE {{base_url}}/estudiantes/1
Authorization: Bearer {{token}}
```

---

## 🔄 FLUJO COMPLETO DE PRUEBA

### Escenario: Proceso de Admisión Completo

```
1. Login como Admin
   POST /auth/login
   
2. Crear Estudiante
   POST /estudiantes
   
3. Crear Reserva
   POST /reservas
   
4. Aprobar Reserva (Admin)
   PUT /reservas/1/aprobar
   
5. Crear Matrícula
   POST /matriculas
   
6. Registrar Pago
   POST /pagos
   
7. Verificar Pago (Admin)
   PUT /pagos/1/verificar
   
8. Consultar Estado
   GET /matriculas/1
   GET /pagos/matricula/1
```

---

## 📊 TESTS AUTOMÁTICOS (Postman)

### Test para Login
```javascript
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response has token", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.token).to.exist;
    pm.environment.set("token", jsonData.token);
});
```

### Test para Crear Reserva
```javascript
pm.test("Status code is 201", function () {
    pm.response.to.have.status(201);
});

pm.test("Reserva creada con estado PENDIENTE", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.data.estadoReserva).to.eql("PENDIENTE");
});
```

### Test para Aprobar Reserva
```javascript
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Reserva aprobada", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.data.estadoReserva).to.eql("ACEPTADA");
});
```

---

## 🎯 DATOS DE PRUEBA

### Usuarios de Semilla
```
Admin:
  email: admin@jardin.test
  password: password123

Acudiente 1:
  email: acudiente1@test.com
  password: password123

Acudiente 2:
  email: acudiente2@test.com
  password: password123
```

### IDs de Referencia
```
Estudiantes: 1, 2, 3
Acudientes: 1, 2
Reservas: 1, 2, 3
Matrículas: 1, 2
Pagos: 1, 2, 3
```

---

## 📥 IMPORTAR EN POSTMAN

### Formato JSON para Importar

Crear archivo `jardin-infantil.postman_collection.json`:

```json
{
  "info": {
    "name": "Jardín Infantil API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "variable": [
    {
      "key": "base_url",
      "value": "http://localhost:8080/api/v1"
    },
    {
      "key": "token",
      "value": ""
    }
  ]
}
```

---

**Tip**: Usa las variables de entorno para cambiar fácilmente entre desarrollo, testing y producción.
