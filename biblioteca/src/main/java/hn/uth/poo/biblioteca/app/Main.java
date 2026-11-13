package hn.uth.poo.biblioteca.app;

import hn.uth.poo.biblioteca.dao.Conexion;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) throws Exception {
        try (Connection c = Conexion.obtener()) {
            System.out.println("Conexion exitosa: " + !c.isClosed());
        }
    }
}