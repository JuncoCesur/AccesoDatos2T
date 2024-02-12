package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.ExistDBService;


@Controller
public class ExistDBController {
	
	@Autowired
	protected ExistDBService conexionService;
	
	@ResponseBody
	@PostMapping ("/conectar")
		public void obtenerServicioXPath() throws Exception  {
			conexionService.obtenerServicioXPath();
		}

}
