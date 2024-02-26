package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.ExistDBService;
import com.example.service.JasperService;

@Controller
@RequestMapping("jasper")
public class JasperController {
	
	@Autowired
	protected JasperService jasperService;
	
	/**
	 * Generar informe con el listado de los juegos
	 * 
	 *
	 * @author Patricia
	 * @see JasperService
	 */
	
	@GetMapping("generarInforme")
	@ResponseBody
	public String generarInforme() throws Exception {
		jasperService.generarInforme();
		return "OK";
	}
}
