package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.ExistDBRepository;

@Service
public class ExistDBService {
	
	@Autowired
	protected ExistDBRepository repositoryBBDD;

	public void obtenerServicioXPath() throws Exception {
		// Lo mandamos al repositorio
		repositoryBBDD.obtenerServicioXPath();

	}
}
