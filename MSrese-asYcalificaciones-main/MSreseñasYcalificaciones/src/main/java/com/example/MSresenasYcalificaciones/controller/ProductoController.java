package com.example.MSresenasYcalificaciones.controller;

import com.example.MSresenasYcalificaciones.dto.ProductoDTO;
import com.example.MSresenasYcalificaciones.model.Producto;
import com.example.MSresenasYcalificaciones.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Convertir Producto a ProductoDTO
    private ProductoDTO convertirAProductoDTO(Producto producto) {
        return new ProductoDTO(producto.getIdProducto(), producto.getNombre(), producto.getPrecio(), producto.getDescripcion());
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody Producto producto) {
        Producto productoGuardado = productoService.guardarProducto(producto);
        ProductoDTO productoDTO = convertirAProductoDTO(productoGuardado);
        return ResponseEntity.ok(productoDTO);
    }

    @GetMapping("/{idProducto}")
    public ResponseEntity<ProductoDTO> obtenerProducto(@PathVariable int idProducto) {
        return productoService.obtenerProductoPorId(idProducto)
                .map(producto -> ResponseEntity.ok(convertirAProductoDTO(producto)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ProductoDTO> listarProductos() {
        return productoService.listarProductos().stream()
                .map(this::convertirAProductoDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/{idProducto}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable int idProducto, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(idProducto, producto);
        ProductoDTO productoDTO = convertirAProductoDTO(productoActualizado);
        return ResponseEntity.ok(productoDTO);
    }

    @DeleteMapping("/{idProducto}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable int idProducto) {
        productoService.eliminarProducto(idProducto);
        return ResponseEntity.noContent().build();
    }
}