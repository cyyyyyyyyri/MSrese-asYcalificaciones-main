package com.example.MSresenasYcalificaciones.controller;

//import com.example.MSresenasYcalificaciones.dto.ProductoDTO;
import com.example.MSresenasYcalificaciones.model.Producto;
import com.example.MSresenasYcalificaciones.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
//import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductoControllerTest {

    @InjectMocks
    private ProductoController productoController;

    @Mock
    private ProductoService productoService;

    private MockMvc mockMvc;
    private Producto producto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
        producto = new Producto(1, "Producto Test", 100.0, "Descripción de producto");
    }

    @Test
    void testCrearProducto() throws Exception {
        when(productoService.guardarProducto(producto)).thenReturn(producto);

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idProducto\": 1, \"nombre\": \"Producto Test\", \"precio\": 100.0, \"descripcion\": \"Descripción de producto\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(1))
                .andExpect(jsonPath("$.nombre").value("Producto Test"))
                .andExpect(jsonPath("$.precio").value(100.0));

        verify(productoService, times(1)).guardarProducto(any(Producto.class));
    }

    @Test
    void testObtenerProductoPorId() throws Exception {
        when(productoService.obtenerProductoPorId(1)).thenReturn(java.util.Optional.of(producto));

        mockMvc.perform(get("/api/productos/{idProducto}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(1))
                .andExpect(jsonPath("$.nombre").value("Producto Test"));

        verify(productoService, times(1)).obtenerProductoPorId(1);
    }

    @Test
    void testObtenerProductoPorIdNoEncontrado() throws Exception {
        when(productoService.obtenerProductoPorId(1)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/productos/{idProducto}", 1))
                .andExpect(status().isNotFound());

        verify(productoService, times(1)).obtenerProductoPorId(1);
    }

    @Test
    void testListarProductos() throws Exception {
        when(productoService.listarProductos()).thenReturn(List.of(producto));

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idProducto").value(1));

        verify(productoService, times(1)).listarProductos();
    }

    @Test
    void testActualizarProducto() throws Exception {
        Producto updatedProducto = new Producto(1, "Producto Actualizado", 150.0, "Nueva descripción");
        when(productoService.actualizarProducto(1, updatedProducto)).thenReturn(updatedProducto);

        mockMvc.perform(put("/api/productos/{idProducto}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idProducto\": 1, \"nombre\": \"Producto Actualizado\", \"precio\": 150.0, \"descripcion\": \"Nueva descripción\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Producto Actualizado"));

        verify(productoService, times(1)).actualizarProducto(1, updatedProducto);
    }

    @Test
    void testEliminarProducto() throws Exception {
        doNothing().when(productoService).eliminarProducto(1);

        mockMvc.perform(delete("/api/productos/{idProducto}", 1))
                .andExpect(status().isNoContent());

        verify(productoService, times(1)).eliminarProducto(1);
    }
}
