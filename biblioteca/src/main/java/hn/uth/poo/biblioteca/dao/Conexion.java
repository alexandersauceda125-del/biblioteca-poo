package hn.uth.poo.biblioteca.dao;

import hn.uth.poo.biblioteca.excepciones.DatosException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Entrega conexiones a la base biblioteca.db (SQLite).
 * Cada conexion nueva activa las llaves foraneas y garantiza que las tablas existan.
 */
public class Conexion {

    private static final String URL = "jdbc:sqlite:biblioteca.db";

    private static final String SQL_AUTORES =
            "CREATE TABLE IF NOT EXISTS autores ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "nombre TEXT NOT NULL, "
            + "nacionalidad TEXT)";

    private static final String SQL_LIBROS =
            "CREATE TABLE IF NOT EXISTS libros ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "isbn TEXT NOT NULL UNIQUE, "
            + "titulo TEXT NOT NULL, "
            + "anio INTEGER, "
            + "precio REAL NOT NULL, "
            + "autor_id INTEGER NOT NULL REFERENCES autores(id))";

    // Clase de utilidad: no se instancia
    private Conexion() {
    }

    /**
     * Abre una conexion nueva. Quien la pida debe cerrarla (try-with-resources).
     */
    public static Connection obtener() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL);
            try (Statement st = conexion.createStatement()) {
                // SQLite trae las llaves foraneas desactivadas: hay que activarlas en CADA conexion
                st.execute("PRAGMA foreign_keys = ON");
                st.execute(SQL_AUTORES);
                st.execute(SQL_LIBROS);
            }
            return conexion;
        } catch (SQLException e) {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException ignorada) {
                    // Ya se esta reportando el error principal
                }
            }
            throw new DatosException("No se pudo abrir la conexion con la base de datos", e);
        }
    }
}
