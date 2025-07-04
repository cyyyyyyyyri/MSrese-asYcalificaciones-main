package com.example.MSresenasYcalificaciones.controller;

import com.example.MSresenasYcalificaciones.dto.ResenaModel;
import com.example.MSresenasYcalificaciones.model.Resena;
import com.example.MSresenasYcalificaciones.service.ResenaService;
import com.example.MSresenasYcalificaciones.assembler.ResenaModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v2/resenas")
public class ResenaControllerV2 {

    private final ResenaService service;
    private final ResenaModelAssembler assembler;

    public ResenaControllerV2(ResenaService service, ResenaModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtiene todas las reseñas")
    @GetMapping
    public ResponseEntity<CollectionModel<ResenaModel>> obtenerTodos() {
        Iterable<Resena> resenas = service.listarResenas();
        List<ResenaModel> modelos = StreamSupport.stream(resenas.spliterator(), false)
                                                 .map(assembler::toModel)
                                                 .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(modelos,
                linkTo(methodOn(ResenaControllerV2.class).obtenerTodos()).withSelfRel()));
    }

    @Operation(summary = "Obtiene una reseña por ID")
    @GetMapping("/{id_Resena}")
    public ResponseEntity<ResenaModel> obtenerPorId(@PathVariable int id_Resena) {
        return service.obtenerResenaPorId(id_Resena)
                .map(resena -> ResponseEntity.ok(assembler.toModel(resena)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crea una nueva reseña")
    @PostMapping
    public ResponseEntity<ResenaModel> crearResena(@RequestBody Resena resena) {
        Resena nueva = service.guardResena(resena);
        return ResponseEntity.ok(assembler.toModel(nueva));
    }

@PutMapping("/{id_Resena}")
public ResponseEntity<Resena> actualizarResena(@PathVariable int id_Resena, @RequestBody Resena resena) {
    try {
        Resena updatedResena = service.actualizarResena(id_Resena, resena);
        return ResponseEntity.ok(updatedResena); // Retorna el objeto actualizado
    } catch (RuntimeException e) {
        return ResponseEntity.notFound().build(); // Si la reseña no se encuentra
    }
}

    @Operation(summary = "Elimina una reseña por ID")
    @DeleteMapping("/{id_Resena}")
    public ResponseEntity<Void> eliminarResena(@PathVariable int id_Resena) {
        try {
            service.eliminarResena(id_Resena);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Si la reseña no se encuentra
        }
    }
@GetMapping("/producto/{id_Producto}")
public ResponseEntity<List<Resena>> obtenerResenasPorProducto(@PathVariable int id_Producto) {
    List<Resena> resenas = service.obtenerResenasPorProducto(id_Producto);
    if (resenas.isEmpty()) {
        return ResponseEntity.notFound().build(); // Si no se encuentran reseñas
    }
    return ResponseEntity.ok(resenas); // Retorna 200 OK si hay reseñas
}
}