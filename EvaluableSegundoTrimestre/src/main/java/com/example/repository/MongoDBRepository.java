package com.example.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.entities.JuegoMongo;

@Repository
public interface MongoDBRepository extends MongoRepository<JuegoMongo, String>{
	
    @Query("{ 'titulo' : ?0 }")
    public List<JuegoMongo> findByTitulo(String titulo);

    @Query("{ 'titulo' : ?0, 'genero' : ?1 }")
    public List<JuegoMongo> findByTituloYGenero(String titulo, String genero);

}
