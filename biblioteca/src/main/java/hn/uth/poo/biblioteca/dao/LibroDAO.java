package hn.uth.poo.biblioteca.dao;

import hn.uth.poo.biblioteca.modelo.Libro;
import java.util.List;

public interface LibroDAO extends CrudDAO<Libro, Integer> {

    List<Libro> listarPorAutor(int autorId);
}
