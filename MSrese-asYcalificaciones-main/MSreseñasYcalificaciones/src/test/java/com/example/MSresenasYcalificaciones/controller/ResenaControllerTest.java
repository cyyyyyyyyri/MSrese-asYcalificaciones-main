package com.example.MSresenasYcalificaciones.controller;

import com.example.MSresenasYcalificaciones.model.Resena;
import com.example.MSresenasYcalificaciones.service.ResenaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ResenaControllerTest {

    @InjectMocks
    private ResenaController resenaController;

    @Mock
    private ResenaService resenaService;

    private MockMvc mockMvc;
    private Resena resena;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(resenaController).build();
        resena = new Resena(1, 1, 1, "Excelente producto", 5.0, "2025-07-03", null);
    }

    @Test
    void testCrearResena() throws Exception {
        when(resenaService.guardResena(resena)).thenReturn(resena);

        mockMvc.perform(post("/api/resenas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id_Resena\": 1, \"id_Producto\": 1, \"id_Cliente\": 1, \"comentario\": \"Excelente producto\", \"calificacion\": 5.0, \"fecha_Resena\": \"2025-07-03\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comentario").value("Excelente producto"));

        verify(resenaService, times(1)).guardResena(any(Resena.class));
    }

    @Test
    void testObtenerResenaPorId() throws Exception {
        when(resenaService.obtenerResenaPorId(1)).thenReturn(Optional.of(resena));

        mockMvc.perform(get("/api/resenas/{id_Resena}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_Resena").value(1))
                .andExpect(jsonPath("$.comentario").value("Excelente producto"));

        verify(resenaService, times(1)).obtenerResenaPorId(1);
    }

    @Test
    void testObtenerResenaPorIdNoEncontrada() throws Exception {
        when(resenaService.obtenerResenaPorId(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/resenas/{id_Resena}", 1))
                .andExpect(status().isNotFound());

        verify(resenaService, times(1)).obtenerResenaPorId(1);
    }

    @Test
    void testListarResenas() throws Exception {
        when(resenaService.listarResenas()).thenReturn(List.of(resena));

        mockMvc.perform(get("/api/resenas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_Resena").value(1));

        verify(resenaService, times(1)).listarResenas();
    }

@Test
void testObtenerResenasPorProducto() throws Exception {
    when(resenaService.obtenerResenasPorProducto(1)).thenReturn(List.of(resena));

    mockMvc.perform(get("/api/resenas/producto/{id_Producto}", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id_Resena").value(1));

    verify(resenaService, times(1)).obtenerResenasPorProducto(1);
}

@Test
void testActualizarResena() throws Exception {
    Resena updatedResena = new Resena(1, 1, 1, "Producto increíble", 5.0, "2025-07-03", null);
    when(resenaService.actualizarResena(1, updatedResena)).thenReturn(updatedResena);

    mockMvc.perform(put("/api/resenas/{id_Resena}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id_Resena\": 1, \"id_Producto\": 1, \"id_Cliente\": 1, \"comentario\": \"Producto increíble\", \"calificacion\": 5.0, \"fechaResena\": \"2025-07-03\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.comentario").value("Producto increíble"));

    verify(resenaService, times(1)).actualizarResena(1, updatedResena);
}

@Test
void testActualizarResenaNoEncontrada() throws Exception {
    Resena updatedResena = new Resena(1, 1, 1, "Producto increíble", 5.0, "2025-07-03", null);
    when(resenaService.actualizarResena(1, updatedResena)).thenThrow(new RuntimeException("Reseña no encontrada"));

    mockMvc.perform(put("/api/resenas/{id_Resena}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id_Resena\": 1, \"id_Producto\": 1, \"id_Cliente\": 1, \"comentario\": \"Producto increíble\", \"calificacion\": 5.0, \"fechaResena\": \"2025-07-03\"}"))
            .andExpect(status().isNotFound());

    verify(resenaService, times(1)).actualizarResena(1, updatedResena);
}

    @Test
    void testEliminarResena() throws Exception {
        doNothing().when(resenaService).eliminarResena(1);

        mockMvc.perform(delete("/api/resenas/{id_Resena}", 1))
                .andExpect(status().isNoContent());

        verify(resenaService, times(1)).eliminarResena(1);
    }

    @Test
    void testEliminarResenaNoEncontrada() throws Exception {
        doThrow(new RuntimeException("Reseña no encontrada")).when(resenaService).eliminarResena(1);

        mockMvc.perform(delete("/api/resenas/{id_Resena}", 1))
                .andExpect(status().isNotFound());

        verify(resenaService, times(1)).eliminarResena(1);
    }
}