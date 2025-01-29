package com.example.lesson16;

public class Comentario {
    private int id;
    private String titulo;
    private String texto;

    // Constructor vacío
    public Comentario() {
    }

    // Constructor con parámetros
    public Comentario(int id, String titulo, String texto) {
        this.id = id;
        this.titulo = titulo;
        this.texto = texto;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    // Método para representar el objeto como String (para Spinner)
    @Override
    public String toString() {
        return titulo;
    }
}

