package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
        return conexionService.insertar(idSql, idJuego, usuario, null);
    }
    
    //Junco
//    @PutMapping("/modificar")
//    @ResponseBody
//    public String modificarNombre(@RequestParam("id") Long id, @RequestParam("nuevoNombre") String nuevoNombre) throws Exception {
//        return conexionService.modificarNombre(id, nuevoNombre);
//    }
    
    //Método para insertar de manera directa en ExistsDB
    //El insertar hecho por mi compañera pasaba primero obligatoriamente a ObjetcDB
    @PostMapping("/insertarDirecto")
    @ResponseBody
    public String insertarDirecto(@RequestParam("idSql") Long idSql, @RequestParam("idJuego") Long idJuego) throws Exception {
        ExistJuegoUsuarioDTO usuario = new ExistJuegoUsuarioDTO();
        usuario.setIdSql(idSql);
        usuario.setIdJuego(idJuego);
        return conexionService.insertarDirecto(usuario);
    }
    
    @GetMapping("/calcularJuegosDeCadaUsuario")
    @ResponseBody
    public String calcularJuegosDeCadaUsuario() {
        return conexionService.calcularJuegosDeCadaUsuario();
    }



}
