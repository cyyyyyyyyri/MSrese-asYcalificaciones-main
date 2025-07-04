package com.example.MSresenasYcalificaciones.service;

import com.example.MSresenasYcalificaciones.model.Producto;
import com.example.MSresenasYcalificaciones.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductoServiceTest {

    @InjectMocks
    private ProductoService productoService;

    @Mock
    private ProductoRepository productoRepository;

    private Producto producto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        producto = new Producto(1, "Producto Test", 100.0, "Descripción de producto");
    }

    @Test
    void testGuardarProducto() {
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto result = productoService.guardarProducto(producto);

        assertNotNull(result);
        assertEquals(producto.getIdProducto(), result.getIdProducto());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void testObtenerProductoPorId() {
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));

        Optional<Producto> result = productoService.obtenerProductoPorId(1);

        assertTrue(result.isPresent());
        assertEquals(producto.getIdProducto(), result.get().getIdProducto());
        verify(productoRepository, times(1)).findById(1);
    }
    

    @Test
    void testObtenerProductoPorIdNoEncontrado() {
        when(productoRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Producto> result = productoService.obtenerProductoPorId(1);

        assertFalse(result.isPresent());
        verify(productoRepository, times(1)).findById(1);
    }

    @Test
    void testListarProductos() {
        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<Producto> result = productoService.listarProductos();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void testActualizarProducto() {
        Producto updatedProducto = new Producto(1, "Producto Actualizado", 150.0, "Nueva descripción");
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));
        when(productoRepository.save(updatedProducto)).thenReturn(updatedProducto);

        Producto result = productoService.actualizarProducto(1, updatedProducto);

        assertNotNull(result);
        assertEquals("Producto Actualizado", result.getNombre());
        verify(productoRepository, times(1)).findById(1);
        verify(productoRepository, times(1)).save(updatedProducto);
    }

    @Test
    void testActualizarProductoNoEncontrado() {
        Producto updatedProducto = new Producto(1, "Producto Actualizado", 150.0, "Nueva descripción");
        when(productoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productoService.actualizarProducto(1, updatedProducto));

        verify(productoRepository, times(1)).findById(1);
    }

    @Test
    void testEliminarProducto() {
        // Simulamos que el producto existe
        when(productoRepository.existsById(1)).thenReturn(true);
        doNothing().when(productoRepository).deleteById(1);

        productoService.eliminarProducto(1);

        verify(productoRepository, times(1)).deleteById(1);
    }

    @Test
    void testEliminarProductoNoEncontrado() {
        // Simulamos que el producto no existe
        when(productoRepository.existsById(1)).thenReturn(false);

        // Verificamos que se lance una excepción cuando el producto no se encuentra
        assertThrows(RuntimeException.class, () -> productoService.eliminarProducto(1));

        verify(productoRepository, times(0)).deleteById(1); // No se debe llamar a deleteById si el producto no existe
    }

}