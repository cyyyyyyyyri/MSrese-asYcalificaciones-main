package com.example.MSresenasYcalificaciones.assembler;

import com.example.MSresenasYcalificaciones.controller.ResenaControllerV2;
import com.example.MSresenasYcalificaciones.dto.ResenaModel;
import com.example.MSresenasYcalificaciones.model.Resena;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ResenaModelAssembler extends RepresentationModelAssemblerSupport<Resena, ResenaModel> {

    public ResenaModelAssembler() {
        super(ResenaControllerV2.class, ResenaModel.class);
    }

    @Override
    public ResenaModel toModel(Resena resena) {
        ResenaModel model = new ResenaModel(
                resena.getId_Resena(),
                resena.getId_Producto(),
                resena.getId_Cliente(),
                resena.getComentario(),
                resena.getCalificacion(),
                resena.getFecha_Resena()
        );

        model.add(linkTo(methodOn(ResenaControllerV2.class).obtenerPorId(resena.getId_Resena())).withSelfRel());
        model.add(linkTo(methodOn(ResenaControllerV2.class).obtenerTodos()).withRel("resenas"));
        model.add(linkTo(methodOn(ResenaControllerV2.class).eliminarResena(resena.getId_Resena())).withRel("eliminar"));
        model.add(linkTo(methodOn(ResenaControllerV2.class).actualizarResena(resena.getId_Resena(), resena)).withRel("actualizar"));

        return model;
    }
}
