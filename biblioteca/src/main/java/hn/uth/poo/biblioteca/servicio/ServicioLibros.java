package hn.uth.poo.biblioteca.servicio;

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.modelo.Libro;
import java.time.Year;
import java.util.List;

public class ServicioLibros {

    private static final int ANIO_MINIMO = 1450;

    private final LibroDAO libroDAO;
    private final AutorDAO autorDAO;

    // Recibe las interfaces, nunca las implementaciones
    public ServicioLibros(LibroDAO libroDAO, AutorDAO autorDAO) {
        this.libroDAO = libroDAO;
        this.autorDAO = autorDAO;
    }

    public void crear(Libro libro) {
        validar(libro);
        libroDAO.insertar(libro);
    }

    public List<Libro> listar() {
        return libroDAO.listar();
    }

    public Libro buscarPorId(int id) {
        Libro libro = libroDAO.buscarPorId(id);
        if (libro == null) {
            throw new DatoInvalidoException("No existe un libro con id " + id);
        }
        return libro;
    }

    public List<Libro> listarPorAutor(int autorId) {
        if (autorDAO.buscarPorId(autorId) == null) {
            throw new DatoInvalidoException("No existe un autor con id " + autorId);
        }
        return libroDAO.listarPorAutor(autorId);
    }

    public void actualizar(Libro libro) {
        buscarPorId(libro.getId()); // lanza excepcion si no existe
        validar(libro);
        libroDAO.actualizar(libro);
    }

    public void eliminar(int id) {
        buscarPorId(id); // lanza excepcion si no existe
        libroDAO.eliminar(id);
    }

    private void validar(Libro libro) {
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new DatoInvalidoException("El titulo del libro es obligatorio");
        }
        if (libro.getIsbn() == null || libro.getIsbn().trim().isEmpty()) {
            throw new DatoInvalidoException("El ISBN del libro es obligatorio");
        }
        int anioActual = Year.now().getValue();
        if (libro.getAnio() < ANIO_MINIMO || libro.getAnio() > anioActual) {
            throw new DatoInvalidoException(
                    "El anio debe estar entre " + ANIO_MINIMO + " y " + anioActual);
        }
        if (libro.getPrecio() < 0) {
            throw new DatoInvalidoException("El precio no puede ser negativo");
        }
        if (autorDAO.buscarPorId(libro.getAutorId()) == null) {
            throw new DatoInvalidoException(
                    "No existe un autor con id " + libro.getAutorId()
                    + ". Registre primero al autor");
        }
        // ISBN no repetido (al actualizar se ignora el propio libro)
        for (Libro existente : libroDAO.listar()) {
            if (existente.getIsbn().equalsIgnoreCase(libro.getIsbn().trim())
                    && existente.getId() != libro.getId()) {
                throw new DatoInvalidoException(
                        "Ya existe un libro con el ISBN " + libro.getIsbn());
            }
        }
    }
}
