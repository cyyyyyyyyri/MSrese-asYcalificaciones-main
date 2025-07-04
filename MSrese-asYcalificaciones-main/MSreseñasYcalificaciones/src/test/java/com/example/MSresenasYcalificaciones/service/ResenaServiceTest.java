package com.example.MSresenasYcalificaciones.service;

import com.example.MSresenasYcalificaciones.model.Resena;
import com.example.MSresenasYcalificaciones.repository.ResenaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ResenaServiceTest {

    @InjectMocks
    private ResenaService resenaService;

    @Mock
    private ResenaRepository resenaRepository;

    private Resena resena;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resena = new Resena(1, 1, 1, "Excelente producto", 5.0, "2025-07-03", null);
    }

    @Test
    void testGuardarResena() {
        when(resenaRepository.save(resena)).thenReturn(resena);

        Resena result = resenaService.guardResena(resena);

        assertNotNull(result);
        assertEquals(resena.getId_Resena(), result.getId_Resena());
        verify(resenaRepository, times(1)).save(resena);
    }

    @Test
    void testObtenerResenaPorId() {
        when(resenaRepository.findById(1)).thenReturn(Optional.of(resena));

        Optional<Resena> result = resenaService.obtenerResenaPorId(1);

        assertTrue(result.isPresent());
        assertEquals(resena.getId_Resena(), result.get().getId_Resena());
        verify(resenaRepository, times(1)).findById(1);
    }

    @Test
    void testObtenerResenaPorIdNoEncontrada() {
        when(resenaRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Resena> result = resenaService.obtenerResenaPorId(1);

        assertFalse(result.isPresent());
        verify(resenaRepository, times(1)).findById(1);
    }

    @Test
    void testListarResenas() {
        when(resenaRepository.findAll()).thenReturn(List.of(resena));

        List<Resena> result = resenaService.listarResenas();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(resenaRepository, times(1)).findAll();
    }

    @Test
    void testObtenerResenasPorProducto() {
        when(resenaRepository.findAll()).thenReturn(List.of(resena));

        List<Resena> result = resenaService.obtenerResenasPorProducto(1);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(resenaRepository, times(1)).findAll();
    }

    @Test
    void testObtenerResenasPorProductoNoEncontradas() {
        when(resenaRepository.findAll()).thenReturn(List.of());

        List<Resena> result = resenaService.obtenerResenasPorProducto(1);

        assertTrue(result.isEmpty());
        verify(resenaRepository, times(1)).findAll();
    }

    @Test
    void testActualizarResena() {
        Resena updatedResena = new Resena(1, 1, 1, "Producto increíble", 5.0, "2025-07-03", null);
        when(resenaRepository.findById(1)).thenReturn(Optional.of(resena));
        when(resenaRepository.save(updatedResena)).thenReturn(updatedResena);

        Resena result = resenaService.actualizarResena(1, updatedResena);

        assertNotNull(result);
        assertEquals("Producto increíble", result.getComentario());
        verify(resenaRepository, times(1)).findById(1);
        verify(resenaRepository, times(1)).save(updatedResena);
    }

    @Test
    void testActualizarResenaNoEncontrada() {
        Resena updatedResena = new Resena(1, 1, 1, "Producto increíble", 5.0, "2025-07-03", null);
        when(resenaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> resenaService.actualizarResena(1, updatedResena));

        verify(resenaRepository, times(1)).findById(1);
    }

    @Test
    void testEliminarResena() {
        doNothing().when(resenaRepository).deleteById(1);

        resenaService.eliminarResena(1);

        verify(resenaRepository, times(1)).deleteById(1);
    }

    @Test
    void testEliminarResenaNoEncontrada() {
        doThrow(new RuntimeException("Reseña no encontrada")).when(resenaRepository).deleteById(1);

        assertThrows(RuntimeException.class, () -> resenaService.eliminarResena(1));

        verify(resenaRepository, times(1)).deleteById(1);
    }

        @Test
    void testObtenerResenasPorProductoCuandoNoHayResenas() {
        when(resenaRepository.findAll()).thenReturn(List.of());

        List<Resena> result = resenaService.obtenerResenasPorProducto(2);

        assertTrue(result.isEmpty());
        verify(resenaRepository, times(1)).findAll();
    }
        @Test
    void testEliminarResenaConError() {
        doThrow(new DataIntegrityViolationException("Error al eliminar")).when(resenaRepository).deleteById(1);

        assertThrows(DataIntegrityViolationException.class, () -> resenaService.eliminarResena(1));

        verify(resenaRepository, times(1)).deleteById(1);
    }

}