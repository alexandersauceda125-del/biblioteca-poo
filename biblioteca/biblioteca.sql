-- Script de base de datos: biblioteca.db
-- Un autor tiene muchos libros; cada libro pertenece a un solo autor.

PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS autores (
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre       TEXT NOT NULL,
    nacionalidad TEXT
);

CREATE TABLE IF NOT EXISTS libros (
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    isbn     TEXT NOT NULL UNIQUE,
    titulo   TEXT NOT NULL,
    anio     INTEGER,
    precio   REAL NOT NULL,
    autor_id INTEGER NOT NULL REFERENCES autores(id)
);
