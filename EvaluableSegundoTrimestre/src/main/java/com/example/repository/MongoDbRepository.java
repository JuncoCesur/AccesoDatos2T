package com.example.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


import com.example.entities.DatosJuegosMongo;

public interface MongoDbRepository extends MongoRepository<DatosJuegosMongo, Long> {

    @Query("{ 'nombre' : ?0 }")
    public List<DatosJuegosMongo> findByNombre(String nombre);

    @Query("{ 'nombre' : ?0, 'primer_apellido' : ?1 }")
    public List<DatosJuegosMongo> findByNombreYPrimerApellido(String nombre, String primerApellido);
}