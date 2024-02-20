package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.JasperRepository;


@Service
public class JasperService {
	
	@Autowired
	protected JasperRepository jasperRepository;

	public void generarInforme() throws Exception {
		// Lo mandamos al repositorio
		jasperRepository.generarInforme();

	}
}
