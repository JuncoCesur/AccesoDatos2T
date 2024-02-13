package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.example.entities.HorasMongo;
import com.example.entities.JuegoMongo;
import com.example.repository.MongoDBRepository;

@Service
public class MongoDBService {

	@Autowired
	protected MongoDBRepository mongoDBRepository;

/*	public List<JuegoMongo> listar(String titulo) {

		List<JuegoMongo> lista = null;

		if (titulo.equals("")) {
			lista = mongoDBRepository.findAll();

		} else {
			lista = mongoDBRepository.findByTitulo(titulo);

		}

		for (JuegoMongo juego : lista) {

			System.out.println(juego.getTitulo() + " " + juego.getGenero());

			if (juego.getHoras() != null) {
				System.out.println("Horas: " + juego.getHoras());
			}
		}

		return lista;
	}

	public String guardar(String titulo, String genero, Integer horas) {

		JuegoMongo juego = new JuegoMongo(titulo, genero);
		juego.setHoras(new HorasMongo(horas));
		mongoDBRepository.save(juego);

		return "Guardado";
	}*/
}
