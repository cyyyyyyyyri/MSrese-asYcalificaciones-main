package com.example.MSresenasYcalificaciones.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MSresenasYcalificaciones.model.Resena;
import com.example.MSresenasYcalificaciones.repository.ResenaRepository;

@Service
public class ResenaService {
    @Autowired
    private ResenaRepository resenaRepository;

    public Resena guardResena(Resena resena){
        return resenaRepository.save(resena);
    
    }

    public Optional<Resena> obtenerResenaPorId(int idResena) {
        return resenaRepository.findById(idResena);
        
    }

    public List<Resena> listarResenas(){
        return resenaRepository.findAll();
        
    }

    public List<Resena> obtenerResenasPorProducto(int idProducto){
        return resenaRepository.findAll().stream()
        .filter(resena -> resena.getId_Producto() == idProducto)
        .toList();
    }

    public Resena actualizarResena(int idResena, Resena datosResena) {
        Resena resena = resenaRepository.findById(idResena).orElseThrow(()-> new RuntimeException("Reseña no encontrada"));
        resena.setComentario(datosResena.getComentario());
        resena.setCalificacion(datosResena.getCalificacion());
        resena.setFecha_Resena(datosResena.getFecha_Resena());
        return resenaRepository.save(resena);

    }

    public void eliminarResena(int idResena) {
        resenaRepository.deleteById(idResena);
    }
    
}
