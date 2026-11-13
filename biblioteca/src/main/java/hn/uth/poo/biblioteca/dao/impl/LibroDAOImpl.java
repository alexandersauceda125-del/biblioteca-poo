package hn.uth.poo.biblioteca.dao.impl;

import hn.uth.poo.biblioteca.dao.Conexion;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Libro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    private static final String SQL_INSERTAR =
            "INSERT INTO libros (isbn, titulo, anio, precio, autor_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_BUSCAR =
            "SELECT id, isbn, titulo, anio, precio, autor_id FROM libros WHERE id = ?";
    private static final String SQL_LISTAR =
            "SELECT id, isbn, titulo, anio, precio, autor_id FROM libros ORDER BY id";
    private static final String SQL_LISTAR_POR_AUTOR =
            "SELECT id, isbn, titulo, anio, precio, autor_id FROM libros WHERE autor_id = ? ORDER BY id";
    private static final String SQL_ACTUALIZAR =
            "UPDATE libros SET isbn = ?, titulo = ?, anio = ?, precio = ?, autor_id = ? WHERE id = ?";
    private static final String SQL_ELIMINAR =
            "DELETE FROM libros WHERE id = ?";

    @Override
    public void insertar(Libro libro) {
        try (Connection c = Conexion.obtener();
             PreparedStatement ps = c.prepareStatement(SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, libro.getIsbn());
            ps.setString(2, libro.getTitulo());
            ps.setInt(3, libro.getAnio());
            ps.setDouble(4, libro.getPrecio());
            ps.setInt(5, libro.getAutorId());
            ps.executeUpdate();
            // El objeto recibe el id generado por la base
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    libro.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DatosException("Error al insertar el libro", e);
        }
    }

    @Override
    public Libro buscarPorId(Integer id) {
        try (Connection c = Conexion.obtener();
             PreparedStatement ps = c.prepareStatement(SQL_BUSCAR)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DatosException("Error al buscar el libro con id " + id, e);
        }
    }

    @Override
    public List<Libro> listar() {
        List<Libro> libros = new ArrayList<>();
        try (Connection c = Conexion.obtener();
             PreparedStatement ps = c.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                libros.add(mapear(rs));
            }
            return libros;
        } catch (SQLException e) {
            throw new DatosException("Error al listar los libros", e);
        }
    }

    @Override
    public List<Libro> listarPorAutor(int autorId) {
        List<Libro> libros = new ArrayList<>();
        try (Connection c = Conexion.obtener();
             PreparedStatement ps = c.prepareStatement(SQL_LISTAR_POR_AUTOR)) {
            ps.setInt(1, autorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    libros.add(mapear(rs));
                }
            }
            return libros;
        } catch (SQLException e) {
            throw new DatosException("Error al listar los libros del autor " + autorId, e);
        }
    }

    @Override
    public void actualizar(Libro libro) {
        try (Connection c = Conexion.obtener();
             PreparedStatement ps = c.prepareStatement(SQL_ACTUALIZAR)) {
            ps.setString(1, libro.getIsbn());
            ps.setString(2, libro.getTitulo());
            ps.setInt(3, libro.getAnio());
            ps.setDouble(4, libro.getPrecio());
            ps.setInt(5, libro.getAutorId());
            ps.setInt(6, libro.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatosException("Error al actualizar el libro con id " + libro.getId(), e);
        }
    }

    @Override
    public void eliminar(Integer id) {
        try (Connection c = Conexion.obtener();
             PreparedStatement ps = c.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatosException("Error al eliminar el libro con id " + id, e);
        }
    }

    // Convierte la fila actual del ResultSet en un objeto Libro
    private Libro mapear(ResultSet rs) throws SQLException {
        return new Libro(
                rs.getInt("id"),
                rs.getString("isbn"),
                rs.getString("titulo"),
                rs.getInt("anio"),
                rs.getDouble("precio"),
                rs.getInt("autor_id"));
    }
}