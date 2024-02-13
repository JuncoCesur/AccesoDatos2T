package com.example.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;

import com.example.entities.JuegoMongo;

public interface MongoDBRepository extends MongoRepository<JuegoMongo, String>{
	
    @Query("{ 'titulo' : ?0 }")
    public List<JuegoMongo> findByTitulo(String titulo);

    @Query("{ 'titulo' : ?0, 'genero' : ?1 }")
    public List<JuegoMongo> findByTituloYGenero(String titulo, String genero);

}
