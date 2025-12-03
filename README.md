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

## Configuracion para correos electronicos

Que provedor usar ->
```
email.provider=mailtrap
```

## Agrega en tu envs
```
email.mailtrap.token=${MAILTRAP_TOKEN}
email.mailtrap.inbox-id=${MAILTRAP_INBOX_ID}
email.mailtrap.from-email=${MAILTRAP_FROM_EMAIL}
email.mailtrap.from-name=${MAILTRAP_FROM_NAME}
```

## en caso de otro distinto:  ejemplo: 

```
email.provider=mailsend
email.mailersend.api-key=${MAILERSEND_API_KEY}
email.mailersend.from-email=${MAILERSEND_FROM_EMAIL}
email.mailersend.from-name=${MAILERSEND_FROM_NAME}
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


## para crear el jar utilizar. 
```
 mvn clean package -DskipTests
```

## Ejecutar jar con variables. 
```
 java "-Dspring.profiles.active=dev" -jar ./target/jar_name.jar.jar
```


