package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entities.VideojuegoUsuario;
import com.example.service.VideojuegoUsuarioService;

@Controller
@RequestMapping("/videojuegos-usuarios")
public class VideojuegoUsuarioController {
    @Autowired
    private VideojuegoUsuarioService videojuegoUsuarioService;

    @GetMapping ("/obtenerTodos")
    @ResponseBody
    public List<VideojuegoUsuario> obtenerTodos(@RequestParam String _id) {
    	System.out.println(_id);
    	return videojuegoUsuarioService.obtenerPorId(_id);
    }

    @PostMapping ("/guardar")
    @ResponseBody
    public String guardar(@RequestParam String id, @RequestParam String nombre, @RequestParam String genero, @RequestParam Long idUsuario) {
        System.out.println(id);
    	VideojuegoUsuario videojuegoUsuario = new VideojuegoUsuario(id, nombre, genero, idUsuario);
        videojuegoUsuarioService.guardar(videojuegoUsuario);
        return "Guardado con exito";
    }

    @GetMapping("/eliminar")
    @ResponseBody
    public String eliminar(@RequestParam String id) {
        videojuegoUsuarioService.eliminar(id);
        return "Eliminado con exito";
    }
}