package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entities.ExistJuegoUsuarioDTO;
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
	
    @PostMapping("/listado")
    @ResponseBody
    public String listado() {
    	conexionService.listado();
        return "OK";
    }
	
    @PutMapping("/insertar")
    @ResponseBody
    public String insertar(@RequestParam Long idSql, @RequestParam Long idJuego, ExistJuegoUsuarioDTO usuario) throws Exception {
        return conexionService.insertar(idSql, idJuego, usuario);
    }
    
    //Junco
    @PutMapping("/modificar")
    @ResponseBody
    public String modificarNombre(@RequestParam("id") Long id, @RequestParam("nuevoNombre") String nuevoNombre) throws Exception {
        return conexionService.modificarNombre(id, nuevoNombre);
    }


}
