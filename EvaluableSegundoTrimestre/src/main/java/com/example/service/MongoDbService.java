package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.DatosJuegosMongo;
import com.example.repository.MongoDbRepository;


@Service
public class MongoDbService {

    @Autowired
    protected MongoDbRepository mongoDbRepository;



    public List<DatosJuegosMongo> listar(String genero) {
        
        List<DatosJuegosMongo> lista = null; 
        if (genero.equals("")) {
        	lista = mongoDbRepository.findAll();
        } else {
        	lista = mongoDbRepository.findByNombre(genero);
        }
        for (DatosJuegosMongo datos : lista) {

            System.out.println(persona.getNombre() + " " + persona.getPrimerApellido());
            if (persona.getDomicilio() != null) {
                System.out.println("Domicilio: " + persona.getDomicilio().getCalle());
            }
        }
        
        return lista;
    }

    public String guardar(String genero, String primerApellido, String direccion, Integer numero) {        
    	Persona persona = new Persona(nombre, primerApellido);
    	persona.setDomicilio(new Domicilio(direccion, numero));
        personasRepository.save(persona);
        return "Guardado";
    }
    
    @Override
    public String guardarDomicilio(String calle, Integer numero) {
        Domicilio domicilio = new Domicilio(calle, numero);
        domicilioRepository.save(domicilio);
        return "Guardado";
    }

    @Override
    public List<Domicilio> listarDomicilio() {
        return domicilioRepository.findAll();
    }
    
    
}