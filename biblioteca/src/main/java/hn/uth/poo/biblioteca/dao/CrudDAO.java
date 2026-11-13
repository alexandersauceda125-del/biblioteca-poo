package hn.uth.poo.biblioteca.dao;

import java.util.List;

public interface CrudDAO<T, ID> {

    void insertar(T objeto);

    // Devuelve null si no existe
    T buscarPorId(ID id);

    List<T> listar();

    void actualizar(T objeto);

    void eliminar(ID id);
}