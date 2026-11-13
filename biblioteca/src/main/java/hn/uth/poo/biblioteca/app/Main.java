package hn.uth.poo.biblioteca.app;

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.dao.impl.AutorDAOImpl;
import hn.uth.poo.biblioteca.dao.impl.LibroDAOImpl;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;
import hn.uth.poo.biblioteca.servicio.ServicioAutores;
import hn.uth.poo.biblioteca.servicio.ServicioLibros;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner teclado = new Scanner(System.in);
    private static ServicioAutores servicioAutores;
    private static ServicioLibros servicioLibros;

    public static void main(String[] args) {
        // Unico lugar donde se crean las implementaciones y se pasan a los servicios
        AutorDAO autorDao = new AutorDAOImpl();
        LibroDAO libroDao = new LibroDAOImpl();
        servicioAutores = new ServicioAutores(autorDao, libroDao);
        servicioLibros = new ServicioLibros(libroDao, autorDao);

        int opcion;
        do {
            System.out.println();
            System.out.println("===== BIBLIOTECA =====");
            System.out.println("1. Autores");
            System.out.println("2. Libros");
            System.out.println("0. Salir");
            opcion = leerEntero("Opcion: ");
            switch (opcion) {
                case 1:
                    menuAutores();
                    break;
                case 2:
                    menuLibros();
                    break;
                case 0:
                    System.out.println("Hasta luego");
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        } while (opcion != 0);
    }

    // ===================== AUTORES =====================

    private static void menuAutores() {
        int opcion;
        do {
            System.out.println();
            System.out.println("--- AUTORES ---");
            System.out.println("1. Crear");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por id");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            opcion = leerEntero("Opcion: ");
            try {
                switch (opcion) {
                    case 1:
                        crearAutor();
                        break;
                    case 2:
                        listarAutores();
                        break;
                    case 3:
                        buscarAutor();
                        break;
                    case 4:
                        actualizarAutor();
                        break;
                    case 5:
                        eliminarAutor();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            } catch (DatoInvalidoException e) {
                System.out.println("DATO INVALIDO: " + e.getMessage());
            } catch (DatosException e) {
                System.out.println("ERROR DE DATOS: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private static void crearAutor() {
        String nombre = leerTexto("Nombre: ");
        String nacionalidad = leerTexto("Nacionalidad: ");
        Autor autor = new Autor(nombre, nacionalidad);
        servicioAutores.crear(autor);
        System.out.println("Autor creado: " + autor);
    }

    private static void listarAutores() {
        List<Autor> autores = servicioAutores.listar();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados");
            return;
        }
        for (Autor a : autores) {
            System.out.println(a);
        }
    }

    private static void buscarAutor() {
        int id = leerEntero("Id del autor: ");
        System.out.println(servicioAutores.buscarPorId(id));
    }

    private static void actualizarAutor() {
        int id = leerEntero("Id del autor a actualizar: ");
        Autor autor = servicioAutores.buscarPorId(id);
        System.out.println("Actual: " + autor);
        autor.setNombre(leerTexto("Nuevo nombre: "));
        autor.setNacionalidad(leerTexto("Nueva nacionalidad: "));
        servicioAutores.actualizar(autor);
        System.out.println("Autor actualizado: " + autor);
    }

    private static void eliminarAutor() {
        int id = leerEntero("Id del autor a eliminar: ");
        servicioAutores.eliminar(id);
        System.out.println("Autor eliminado");
    }

    // ===================== LIBROS =====================

    private static void menuLibros() {
        int opcion;
        do {
            System.out.println();
            System.out.println("--- LIBROS ---");
            System.out.println("1. Crear");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por id");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("6. Listar libros de un autor");
            System.out.println("0. Volver");
            opcion = leerEntero("Opcion: ");
            try {
                switch (opcion) {
                    case 1:
                        crearLibro();
                        break;
                    case 2:
                        listarLibros();
                        break;
                    case 3:
                        buscarLibro();
                        break;
                    case 4:
                        actualizarLibro();
                        break;
                    case 5:
                        eliminarLibro();
                        break;
                    case 6:
                        listarLibrosDeAutor();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            } catch (DatoInvalidoException e) {
                System.out.println("DATO INVALIDO: " + e.getMessage());
            } catch (DatosException e) {
                System.out.println("ERROR DE DATOS: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private static void crearLibro() {
        String isbn = leerTexto("ISBN: ");
        String titulo = leerTexto("Titulo: ");
        int anio = leerEntero("Anio: ");
        double precio = leerDecimal("Precio: ");
        int autorId = leerEntero("Id del autor: ");
        Libro libro = new Libro(isbn, titulo, anio, precio, autorId);
        servicioLibros.crear(libro);
        System.out.println("Libro creado: " + libro);
    }

    private static void listarLibros() {
        List<Libro> libros = servicioLibros.listar();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados");
            return;
        }
        for (Libro l : libros) {
            System.out.println(l);
        }
    }

    private static void buscarLibro() {
        int id = leerEntero("Id del libro: ");
        System.out.println(servicioLibros.buscarPorId(id));
    }

    private static void actualizarLibro() {
        int id = leerEntero("Id del libro a actualizar: ");
        Libro libro = servicioLibros.buscarPorId(id);
        System.out.println("Actual: " + libro);
        libro.setIsbn(leerTexto("Nuevo ISBN: "));
        libro.setTitulo(leerTexto("Nuevo titulo: "));
        libro.setAnio(leerEntero("Nuevo anio: "));
        libro.setPrecio(leerDecimal("Nuevo precio: "));
        libro.setAutorId(leerEntero("Nuevo id del autor: "));
        servicioLibros.actualizar(libro);
        System.out.println("Libro actualizado: " + libro);
    }

    private static void eliminarLibro() {
        int id = leerEntero("Id del libro a eliminar: ");
        servicioLibros.eliminar(id);
        System.out.println("Libro eliminado");
    }

    private static void listarLibrosDeAutor() {
        int autorId = leerEntero("Id del autor: ");
        List<Libro> libros = servicioLibros.listarPorAutor(autorId);
        if (libros.isEmpty()) {
            System.out.println("Ese autor no tiene libros registrados");
            return;
        }
        for (Libro l : libros) {
            System.out.println(l);
        }
    }

    // ===================== LECTURA DE TECLADO =====================

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine().trim();
    }

    // Repite la pregunta hasta que se escriba un entero valido
    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = teclado.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Debe escribir un numero entero");
            }
        }
    }

    // Repite la pregunta hasta que se escriba un decimal valido
    private static double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = teclado.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(linea);
            } catch (NumberFormatException e) {
                System.out.println("Debe escribir un numero (ejemplo: 150.50)");
            }
        }
    }
}