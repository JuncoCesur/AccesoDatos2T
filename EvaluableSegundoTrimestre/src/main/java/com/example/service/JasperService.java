package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.JasperRepository;


@Service
public class JasperService {
	
	@Autowired
	protected JasperRepository jasperRepository;
	
	/**
	 * Generamos el informe de la lista de juegos
	 * 
	 *
	 * @author Patricia
	 * @see JasperRepository
	 */

	public void generarInforme() throws Exception {
		jasperRepository.generarInforme();

	}
}
