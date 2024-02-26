package com.example.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.entities.CaratulaJuego;
import com.example.entities.VideojuegoUsuario;
import com.example.service.ExistDBService;
import com.example.service.VideojuegoUsuarioService;

// Controlador para trabajar con MongoDB
@Controller
@RequestMapping("/videojuegos-usuarios")
public class VideojuegoUsuarioController {
	
	@Autowired
	private VideojuegoUsuarioService videojuegoUsuarioService;
	
	/**
	 * Guardamos, eliminamos y obtenemos objetos en MongoDB
	 * 
	 *
	 * @author Junco
	 * @see VideojuegoUsuarioService
	 */

	// Método para obtener todos los usuarios asociados a la id de un juego.
	@GetMapping("/obtenerJuegoPorId")
	@ResponseBody
	public List<VideojuegoUsuario> obtenerTodos(@RequestParam String _id) {
		//Devuelve una lista
		return videojuegoUsuarioService.obtenerPorId(_id);
	}
	
    // Método para obtener usuarios por nombre de juego.
    @GetMapping("/obtenerPorNombre")
    @ResponseBody
    public List<VideojuegoUsuario> obtenerPorNombre(@RequestParam String nombre) {
        return videojuegoUsuarioService.obtenerPorNombre(nombre);
    }

    // Método para obtener usuarios por género de juego.
    @GetMapping("/obtenerPorGenero")
    @ResponseBody
    public List<VideojuegoUsuario> obtenerPorGenero(@RequestParam String genero) {
        return videojuegoUsuarioService.obtenerPorGenero(genero);
    }

    // Método para obtener usuarios por ID de usuario.
    @GetMapping("/obtenerPorIdUsuario")
    @ResponseBody
    public List<VideojuegoUsuario> obtenerPorIdUsuario(@RequestParam Long idUsuario) {
        return videojuegoUsuarioService.obtenerPorIdUsuario(idUsuario);
    }

	// Método para guardar un juego asociado a un usuario. 
	@PostMapping("/guardar")
	@ResponseBody
	public String guardar(@RequestParam String id, @RequestParam String nombre, @RequestParam String genero,
			@RequestParam Long idUsuario) {
		System.out.println(id);
		VideojuegoUsuario videojuegoUsuario = new VideojuegoUsuario(id, nombre, genero, idUsuario);
		videojuegoUsuarioService.guardar(videojuegoUsuario);
		return "Guardado con exito";
	}

	// Método para eliminar juegos por id
	@GetMapping("/eliminar")
	@ResponseBody
	public String eliminar(@RequestParam String id) {
		videojuegoUsuarioService.eliminar(id);
		return "Eliminado con exito";
	}

	/**
	 * Insertamos y mostramos las caratulas de los juegos de la lista
	 * 
	 *
	 * @author Patricia
	 * @see VideojuegoUsuarioService
	 */
	
	
	// Metodo para cargar una caratula y guardarla en MongoDB
	@PostMapping("/cargar")
	@ResponseBody
	public String cargarCaratula(@RequestParam("file") MultipartFile file) {

	    if (!file.isEmpty()) {
	        try {
	        	
	        	// Metemos los bytes en un array de bytes
	            byte[] fileData = file.getBytes();
	            
	            // El nombre del archivo se mete en una variable string
	            String fileName = file.getOriginalFilename();
	            
	            // Tanto el nombre como los bytes se pasan al service
	            videojuegoUsuarioService.agregarCaratula(fileName, fileData);

	            return "Archivo cargado y guardado exitosamente en la base de datos.";

	        } catch (IOException e) {
	            return "Error al procesar el archivo: " + e.getMessage();

	        }
	        
	    } else {
	        return "El archivo está vacío";
	    }
	}
	
	// Metodo para visualizar la caratula
    @GetMapping("/caratula/{id}")
    public ResponseEntity<byte[]> obtenerImagenCaratula(@PathVariable String id) {
    	
    	// Obtenemos el array de bytes segun la id aportada
        byte[] imagenBytes = videojuegoUsuarioService.obtenerDatosBinarios(id);
        
        // Si no es null, dicho array lo convertimos en jpeg
        if (imagenBytes != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imagenBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}