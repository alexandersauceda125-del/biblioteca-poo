package hn.uth.poo.biblioteca.app;

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.dao.impl.AutorDAOImpl;
import hn.uth.poo.biblioteca.dao.impl.LibroDAOImpl;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;

public class Main {

    public static void main(String[] args) {
        AutorDAO autorDao = new AutorDAOImpl();
        LibroDAO libroDao = new LibroDAOImpl();

        Autor a = new Autor("Gabriel Garcia Marquez", "Colombia");
        autorDao.insertar(a);

        Libro l = new Libro("978-0307474728", "Cien anios de soledad", 1967, 350.0, a.getId());
        libroDao.insertar(l);
        System.out.println("Insertado: " + l);

        System.out.println("Buscar: " + libroDao.buscarPorId(l.getId()));
        System.out.println("Por autor: " + libroDao.listarPorAutor(a.getId()));

        l.setPrecio(400.0);
        libroDao.actualizar(l);
        System.out.println("Listar: " + libroDao.listar());

        // Debe fallar: el autor 9999 no existe (llave foranea)
        try {
            libroDao.insertar(new Libro("111", "Libro huerfano", 2000, 10.0, 9999));
        } catch (DatosException e) {
            System.out.println("Rechazado: " + e.getMessage());
        }

        libroDao.eliminar(l.getId());
        autorDao.eliminar(a.getId());
        System.out.println("Despues de eliminar: " + libroDao.buscarPorId(l.getId()));
    }
}