package com.example.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.entities.VideojuegoUsuario;

public interface VideojuegoUsuarioRepository extends MongoRepository<VideojuegoUsuario, String> {

    @Query("{ 'id' : ?0 }")
    List<VideojuegoUsuario> findAllById(String _id);

    @Query("{ 'nombre' : ?0 }")
    List<VideojuegoUsuario> findByNombre(String nombre);

    @Query("{ 'genero' : ?0 }")
    List<VideojuegoUsuario> findByGenero(String genero);

    @Query("{ 'idUsuario' : ?0 }")
    List<VideojuegoUsuario> findByIdUsuario(Long idUsuario);
}