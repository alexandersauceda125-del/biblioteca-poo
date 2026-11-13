package hn.uth.poo.biblioteca.dao.impl;

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.Conexion;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Autor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AutorDAOImpl implements AutorDAO {

    private static final String SQL_INSERTAR =
            "INSERT INTO autores (nombre, nacionalidad) VALUES (?, ?)";
    private static final String SQL_BUSCAR =
            "SELECT id, nombre, nacionalidad FROM autores WHERE id = ?";
    private static final String SQL_LISTAR =
            "SELECT id, nombre, nacionalidad FROM autores ORDER BY id";
    private static final String SQL_ACTUALIZAR =
            "UPDATE autores SET nombre = ?, nacionalidad = ? WHERE id = ?";
    private static final String SQL_ELIMINAR =
            "DELETE FROM autores WHERE id = ?";

    @Override
    public void insertar(Autor autor) {
        try (Connection c = Conexion.obtener();
             PreparedStatement ps = c.prepareStatement(SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getNacionalidad());
            ps.executeUpdate();
            // El objeto recibe el id generado por la base
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    autor.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DatosException("Error al insertar el autor", e);
        }
    }

    @Override
    public Autor buscarPorId(Integer id) {
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
            throw new DatosException("Error al buscar el autor con id " + id, e);
        }
    }

    @Override
    public List<Autor> listar() {
        List<Autor> autores = new ArrayList<>();
        try (Connection c = Conexion.obtener();
             PreparedStatement ps = c.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                autores.add(mapear(rs));
            }
            return autores;
        } catch (SQLException e) {
            throw new DatosException("Error al listar los autores", e);
        }
    }

    @Override
    public void actualizar(Autor autor) {
        try (Connection c = Conexion.obtener();
             PreparedStatement ps = c.prepareStatement(SQL_ACTUALIZAR)) {
            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getNacionalidad());
            ps.setInt(3, autor.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatosException("Error al actualizar el autor con id " + autor.getId(), e);
        }
    }

    @Override
    public void eliminar(Integer id) {
        try (Connection c = Conexion.obtener();
             PreparedStatement ps = c.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatosException("Error al eliminar el autor con id " + id, e);
        }
    }

    // Convierte la fila actual del ResultSet en un objeto Autor
    private Autor mapear(ResultSet rs) throws SQLException {
        return new Autor(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("nacionalidad"));
    }
}