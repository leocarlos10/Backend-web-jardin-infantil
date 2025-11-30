## Configuracion para la base de datos.

Para configurar una base de datos diferente puedes crear un archivo llamado

```
application-dev.properties
```

Agregar las credenciales correspondientes

```
DATABASE_URL=jdbc:mysql://localhost:3306/db_jardin
DB_USER=root
DB_PASS=password
```

Para poder correr la app con esas configuraciones.

```
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
```

O en su defecto. agregar variables en su editor o sistema.

## Migraciones

Este proyecto usa Flyway para gestionar migraciones de base de datos. Las migraciones se encuentran en:

```
src/main/resources/db/migration/
```

Como crear una migracion:

```
V1__Create_usuario_table.sql  ✅
V2__Add_email_column.sql      ✅
V1_1__Fix_constraints.sql     ✅ (versiones con puntos)
V20241121__Initial_schema.sql ✅ (fechas como versión)
```

No esta permitido.:

```
V1__create_user_table.sql (primera letra minúscula)
v1__Create_table.sql      ('v' minúscula)
V1_Create_table.sql       (un solo _ usar __)
V1create_table.sql        (no usar __)
```

Correr las Migraciones con maven.

```
mvn flyway:migrate -Dflyway.url="jdbc:mysql://localhost:3306/db_jardin" -Dflyway.user="db_user" -Dflyway.password="db_password"
```

- Dflyway.user="db_user" nombre de usuario de la base de datos
- Dflyway.password="db_password contrasena de la base de datos.

## ! No modificar un archivo de migracion luego de la migracion.!

En su defecto crear uno nuevo con los nuevos cambios a la base de datos.


## Roles de Usuario

Nuevo camibo en el sistema de rol de un usuario ahora el sistema maneja tres tipos de roles:

- **USUARIO**: Rol asignado por defecto al registrarse. Usuarios normales sin permisos especiales.
- **ACUDIENTE**: Rol para padres o tutores de estudiantes con acceso a funcionalidades específicas.
- **ADMINISTRADOR**: Rol con permisos completos para gestionar el sistema.

### Registro de Usuarios

Al registrarse mediante `/api/v1/auth/register`, los usuarios reciben automáticamente el rol `USUARIO`. Este rol permite acceso básico al sistema sin permisos administrativos ni de acudiente.

Para asignar roles específicos (ACUDIENTE o ADMINISTRADOR), esto debe hacerse posteriormente a través de funcionalidades administrativas o procesos específicos del sistema.

## 🎯 Patrón de Diseño Implementado: Observer

Este proyecto implementa el **Patrón Observer** para gestionar un sistema de notificaciones y eventos desacoplado.

### ¿Qué es el Patrón Observer?

El Patrón Observer permite que múltiples objetos (observadores) sean notificados automáticamente cuando ocurre un evento en el sistema, sin que el código que genera el evento necesite conocer los detalles de los observadores.

### Componentes Implementados

```
pattern/observer/
├── EventManager.java              # Gestor central de eventos (Subject)
├── EventListener.java             # Interfaz para observadores
├── EventType.java                 # 10 eventos del sistema
├── ObserverConfig.java            # Configuración Spring
└── listeners/
    ├── LoggingListener.java       # Auditoría de eventos
    ├── EmailNotificationListener.java  # Notificaciones email
    └── StatisticsListener.java    # Métricas y estadísticas
```

### Servicios Integrados

✅ **ReservaService** - Notifica: RESERVA_CREADA, RESERVA_APROBADA, RESERVA_RECHAZADA  
✅ **MatriculaService** - Notifica: MATRICULA_CREADA, MATRICULA_CANCELADA  
✅ **PagoService** - Notifica: PAGO_REGISTRADO, PAGO_VERIFICADO, PAGO_RECHAZADO  
✅ **EstudianteService** - Notifica: ESTUDIANTE_CREADO, ESTUDIANTE_ACTUALIZADO

### Ejemplo de Uso

```java
// En cualquier servicio
public ReservaResponse aprobarReserva(Long id) {
    // 1. Lógica de negocio
    reserva.setEstadoReserva(EstadoReserva.ACEPTADA);
    reservaRepository.update(reserva);
    
    // 2. Notificar evento - todos los listeners reaccionan automáticamente
    eventManager.notify(EventType.RESERVA_APROBADA.getValue(), reserva);
    
    return mapToResponseSimple(reserva);
}

// Resultado automático:
// → LoggingListener registra en logs
// → EmailListener envía email al acudiente
// → StatisticsListener incrementa contador
```

### Ventajas

✅ **Desacoplamiento**: Los servicios no conocen los detalles de notificación  
✅ **Extensibilidad**: Agregar nuevos listeners sin modificar código existente  
✅ **Mantenibilidad**: Cada listener tiene una única responsabilidad  
✅ **Escalabilidad**: Fácil agregar nuevos canales (SMS, WhatsApp, Webhooks)

### Documentación Completa

📖 **[PATRON_OBSERVER_DOCUMENTACION.md](PATRON_OBSERVER_DOCUMENTACION.md)** - Documentación completa del patrón implementado

## 📚 Documentación Completa

### 📖 Índice General
**[INDICE_DOCUMENTACION.md](INDICE_DOCUMENTACION.md)** - Índice completo de toda la documentación del proyecto

### 🌐 Documentación de la API

📖 **[API_DOCUMENTACION_COMPLETA.md](API_DOCUMENTACION_COMPLETA.md)** - Documentación completa de todos los endpoints  
📮 **[API_EJEMPLOS_POSTMAN.md](API_EJEMPLOS_POSTMAN.md)** - Colección de ejemplos para Postman/Insomnia  
📊 **[API_RESUMEN_VISUAL.txt](API_RESUMEN_VISUAL.txt)** - Resumen visual de la API

### Endpoints Principales

- **Autenticación**: `/api/v1/auth/login`, `/api/v1/auth/register`
- **Reservas**: `/api/v1/reservas` (6 endpoints)
- **Matrículas**: `/api/v1/matriculas` (6 endpoints)
- **Pagos**: `/api/v1/pagos` (6 endpoints)
- **Estudiantes**: `/api/v1/estudiantes` (6 endpoints)

**Total**: 26 endpoints documentados con ejemplos de request/response
