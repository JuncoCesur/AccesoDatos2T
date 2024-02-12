package com.example.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.ObjectDBService;

@Controller
public class ObjectDBController {
	
	@Autowired
	protected ObjectDBService conexionService;
	
	@ResponseBody
	@PostMapping ("/juegosAsociados")
		public void juegosAsociados(@RequestParam String email, @RequestParam String titulo, @RequestParam String genero) throws SQLException  {
			conexionService.juegosAsociados(email, titulo, genero);
		}
	
	@ResponseBody
	@PostMapping ("/mostrar")
		public void mostrar() {
			conexionService.mostrar();
		}
}
