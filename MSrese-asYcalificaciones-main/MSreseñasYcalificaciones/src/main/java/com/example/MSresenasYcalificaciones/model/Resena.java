package com.example.MSresenasYcalificaciones.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reseña")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_Resena;

    @Column(name = "id_producto", insertable = false, updatable = false)
    private int id_Producto;

    @Column(nullable = false)
    private int id_Cliente;

    @Column(nullable = false, length = 500)
    private String comentario;

    @Column(nullable = false)
    private double calificacion; // valor entre 1 y 5 

    @Column(nullable = false)
    private String fecha_Resena;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", referencedColumnName = "idProducto", insertable = false, updatable = false)
    @JsonBackReference // Aquí se evita la serialización recursiva
    private Producto producto;
}