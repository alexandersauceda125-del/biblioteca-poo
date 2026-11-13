# Biblioteca - CRUD con DAO, JDBC y SQLite

**Nombre:** [Alexander Enmanuel Sauceda Palacios]
**Numero de cuenta:** [202620060003]
**Curso:** Programacion Orientada a Objetos - UTH

## Descripcion

Aplicacion de consola en Java que administra los autores de una biblioteca y los libros de cada autor.
Un autor puede tener muchos libros; cada libro pertenece a un solo autor.

Se usa persistencia en SQLite mediante JDBC, interfaces DAO implementadas con `implements`
y una separacion por capas (modelo, dao, servicio y presentacion).

## Estructura del proyecto

El proyecto Maven esta en la carpeta `biblioteca/`.

```
hn.uth.poo.biblioteca.modelo      Autor, Libro
hn.uth.poo.biblioteca.excepciones DatosException, DatoInvalidoException
hn.uth.poo.biblioteca.dao         Conexion, CrudDAO, AutorDAO, LibroDAO
hn.uth.poo.biblioteca.dao.impl    AutorDAOImpl, LibroDAOImpl
hn.uth.poo.biblioteca.servicio    ServicioAutores, ServicioLibros
hn.uth.poo.biblioteca.app         Main
```

El script de la base de datos esta en `biblioteca/biblioteca.sql`.

## Reglas de negocio

- El nombre del autor y el titulo del libro son obligatorios.
- El ISBN no se puede repetir.
- El anio debe estar entre 1450 y el anio actual.
- El precio debe ser mayor o igual a 0.
- Un libro solo se registra si su autor existe.
- No se puede eliminar un autor que tiene libros.

## Requisitos

- JDK 26 (o la version indicada en `maven.compiler.release` del `pom.xml`)
- Maven
- NetBeans (opcional)

## Como ejecutar

### Desde NetBeans
1. Abrir el proyecto con **File > Open Project**, seleccionando la carpeta `biblioteca`.
2. Clic derecho sobre el proyecto > **Clean and Build**.
3. Clic derecho sobre `Main.java` > **Run File**.
4. Usar el menu en la pestana **Output**.

### Desde la terminal
```
cd biblioteca
mvn compile exec:java
```

La base de datos `biblioteca.db` se crea sola en la carpeta del proyecto la primera vez que se ejecuta
(tambien se puede crear manualmente con `biblioteca.sql`).

