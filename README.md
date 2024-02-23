# Scopesi


## Levantar proyecto en modo dev.

### 1. Cambiar perfiles activos.
_En "web/src/main/resources/application.properties" comentar:_

```
spring.profiles.active=prod-env
```

_y descomentar:_

```
spring.profiles.active=dev
```

### 2. Cambiar rutas folders.
_En el mismo archivo cambiar las rutas de folderImage y file.upload-dir a las rutas que uses localmente._


### 3. Instalar y ejecutar proyecto:
_Luegoo por terminal, en la raiz del proyecto ejecutar los siguientes comando:_

- Instalar dependencias:
```
mvn clean install
```

- Ejecutar proyecto.
```
mvn spring-boot:run
```
