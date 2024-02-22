package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entities.ExistJuegoUsuarioDTO;
import com.example.service.BBDDService;
import com.example.service.ExistDBService;



@Controller
public class ExistDBController {
	
	@Autowired
	protected ExistDBService conexionService;
	
	/**
	 * Conectamos e interactuamos con objetos de la base de datos de ExistDB
	 * 
	 *
	 * @author Patricia
	 * @see ExistDBService
	 */
	
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
        return conexionService.insertar(idSql, idJuego, usuario, null);
    }
    
	/**
	 * Modificacion de objetos en la base de datos ExistDB
	 *
	 * @author Junco
	 * @see ExistDBService
	 */

    @PutMapping("/modificar")
    @ResponseBody
    public String modificarNombre(@RequestParam("id") Long id, @RequestParam("nuevoNombre") String nuevoNombre) throws Exception {
        return conexionService.modificarNombre(id, nuevoNombre);
    }


}
