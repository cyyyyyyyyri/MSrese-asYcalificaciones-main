package com.example.MSresenasYcalificaciones.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MSresenasYcalificaciones.model.Producto;
import com.example.MSresenasYcalificaciones.repository.ProductoRepository;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
        
    }

    public Optional<Producto> obtenerProductoPorId(int idProducto) {
        return productoRepository.findById(idProducto);
        
    }

    public List<Producto> listarProductos(){
        return productoRepository.findAll();
        
    }

    // Actualizar producto
    public Producto actualizarProducto(int idProducto,Producto datosProducto) {
        Producto producto = productoRepository.findById(idProducto)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setNombre(datosProducto.getNombre());
        producto.setPrecio(datosProducto.getPrecio());
        producto.setDescripcion(datosProducto.getDescripcion());
        return productoRepository.save(producto);

    }

    public void eliminarProducto(int idProducto) {
        if (!productoRepository.existsById(idProducto)) {
            throw new RuntimeException("Producto no encontrado");

        }
        productoRepository.deleteById(idProducto);
    }

}
