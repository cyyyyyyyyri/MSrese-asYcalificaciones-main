package com.example.MSresenasYcalificaciones.dto;

import org.springframework.hateoas.RepresentationModel;

public class ResenaModel extends RepresentationModel<ResenaModel> {

    private int id_Resena;
    private int id_Producto;
    private int id_Cliente;
    private String comentario;
    private double calificacion;
    private String fecha_Resena;

    public ResenaModel(int id_Resena, int id_Producto, int id_Cliente, String comentario, double calificacion, String fecha_Resena) {
        this.id_Resena = id_Resena;
        this.id_Producto = id_Producto;
        this.id_Cliente = id_Cliente;
        this.comentario = comentario;
        this.calificacion = calificacion;
        this.fecha_Resena = fecha_Resena;
    }

    // Getters and Setters
    public int getId_Resena() { return id_Resena; }
    public void setId_Resena(int id_Resena) { this.id_Resena = id_Resena; }

    public int getId_Producto() { return id_Producto; }
    public void setId_Producto(int id_Producto) { this.id_Producto = id_Producto; }

    public int getId_Cliente() { return id_Cliente; }
    public void setId_Cliente(int id_Cliente) { this.id_Cliente = id_Cliente; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public double getCalificacion() { return calificacion; }
    public void setCalificacion(double calificacion) { this.calificacion = calificacion; }

    public String getFecha_Resena() { return fecha_Resena; }
    public void setFecha_Resena(String fecha_Resena) { this.fecha_Resena = fecha_Resena; }
}