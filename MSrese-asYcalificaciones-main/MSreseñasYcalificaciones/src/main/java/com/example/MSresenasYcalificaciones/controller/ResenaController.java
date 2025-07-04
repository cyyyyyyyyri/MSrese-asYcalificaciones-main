package com.example.MSresenasYcalificaciones.controller;

import com.example.MSresenasYcalificaciones.model.Resena;
import com.example.MSresenasYcalificaciones.service.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @PostMapping
    public ResponseEntity<Resena> crearResena(@RequestBody Resena resena) {
        return ResponseEntity.ok(resenaService.guardResena(resena));
    }

    @GetMapping("/{id_Resena}")
    public ResponseEntity<Resena> obtenerResenaPorId(@PathVariable int id_Resena) {
        return resenaService.obtenerResenaPorId(id_Resena)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Iterable<Resena> listarResenas() {
        return resenaService.listarResenas();
    }

@PutMapping("/{id_Resena}")
public ResponseEntity<Resena> actualizarResena(@PathVariable int id_Resena, @RequestBody Resena resena) {
    try {
        Resena updatedResena = resenaService.actualizarResena(id_Resena, resena);
        return ResponseEntity.ok(updatedResena);
    } catch (RuntimeException e) {
        return ResponseEntity.notFound().build(); // Si la reseña no se encuentra
    }
}

    @DeleteMapping("/{id_Resena}")
    public ResponseEntity<Void> eliminarResena(@PathVariable int id_Resena) {
        try {
            resenaService.eliminarResena(id_Resena);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Si la reseña no se encuentra
        }
    }
}