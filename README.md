
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
+ Dflyway.user="db_user"  nombre de usuario de la base de datos
+ Dflyway.password="db_password  contrasena de la base de datos. 

## ! No modificar un archivo de migracion luego de la migracion.!
En su defecto crear uno nuevo con los nuevos cambios a la base de datos.
