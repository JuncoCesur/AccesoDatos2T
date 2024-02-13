package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entities.JuegoMongo;
import com.example.service.MongoDBService;

@Controller
@RequestMapping("/mongoDB")
public class MongoDBController {
    @Autowired
    protected MongoDBService mongoDBService;

    @PostMapping("/listado")
    @ResponseBody
    public List<JuegoMongo> listar(String titulo) {
        return mongoDBService.listar(titulo);
    }

    @PutMapping("/guardar")
    @ResponseBody
    public String guardar(String titulo, String genero, Integer horas) {
        return mongoDBService.guardar(titulo, genero, horas);
    }
}
