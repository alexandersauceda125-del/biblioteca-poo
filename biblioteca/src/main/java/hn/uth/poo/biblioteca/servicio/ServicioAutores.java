package hn.uth.poo.biblioteca.servicio;

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.modelo.Autor;
import java.util.List;

public class ServicioAutores {

    private final AutorDAO autorDAO;
    private final LibroDAO libroDAO;

    // Recibe las interfaces, nunca las implementaciones
    public ServicioAutores(AutorDAO autorDAO, LibroDAO libroDAO) {
        this.autorDAO = autorDAO;
        this.libroDAO = libroDAO;
    }

    public void crear(Autor autor) {
        validar(autor);
        autorDAO.insertar(autor);
    }

    public List<Autor> listar() {
        return autorDAO.listar();
    }

    public Autor buscarPorId(int id) {
        Autor autor = autorDAO.buscarPorId(id);
        if (autor == null) {
            throw new DatoInvalidoException("No existe un autor con id " + id);
        }
        return autor;
    }

    public void actualizar(Autor autor) {
        validar(autor);
        buscarPorId(autor.getId()); // lanza excepcion si no existe
        autorDAO.actualizar(autor);
    }

    public void eliminar(int id) {
        buscarPorId(id); // lanza excepcion si no existe
        if (!libroDAO.listarPorAutor(id).isEmpty()) {
            throw new DatoInvalidoException(
                    "No se puede eliminar el autor " + id + " porque tiene libros registrados");
        }
        autorDAO.eliminar(id);
    }

    private void validar(Autor autor) {
        if (autor.getNombre() == null || autor.getNombre().trim().isEmpty()) {
            throw new DatoInvalidoException("El nombre del autor es obligatorio");
        }
    }
}