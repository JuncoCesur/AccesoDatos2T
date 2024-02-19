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
	@PostMapping ("/juegoAsociado")
		public void juegoAsociado(@RequestParam String email, @RequestParam String titulo, @RequestParam String genero) throws Exception  {
			conexionService.juegoAsociado(email, titulo, genero);
		}
	
	@ResponseBody
	@PostMapping ("/insertarJuego")
		public void insertarJuego(@RequestParam String titulo, @RequestParam String genero) throws SQLException  {
			conexionService.insertarJuego(titulo, genero);
		}
	
	@ResponseBody
	@PostMapping ("/existeUsuarioObject")
		public void existeUsuarioObject(@RequestParam String email) throws SQLException {
			conexionService.existeUsuarioObject(email);
		}
	
	@ResponseBody
	@PostMapping ("/encontrarJuego")
		public void encontrarJuego(@RequestParam String titulo) throws SQLException  {
			conexionService.encontrarJuego(titulo);
		}
	
	@ResponseBody
	@PostMapping ("/mostrar")
		public void mostrar() {
			conexionService.mostrar();
		}
}
