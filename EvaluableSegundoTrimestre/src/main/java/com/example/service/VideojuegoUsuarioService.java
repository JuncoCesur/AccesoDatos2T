package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.VideojuegoUsuario;
import com.example.repository.VideojuegoUsuarioRepository;

@Service
public class VideojuegoUsuarioService {
    @Autowired
    private VideojuegoUsuarioRepository videojuegoUsuarioRepository;

    public List<VideojuegoUsuario> obtenerTodos() {
        return videojuegoUsuarioRepository.findAll();
    }

    public List<VideojuegoUsuario> obtenerPorId(String _id) {
        return videojuegoUsuarioRepository.findAllById(_id);
    }

    public List<VideojuegoUsuario> obtenerPorNombre(String nombre) {
        return videojuegoUsuarioRepository.findByNombre(nombre);
    }

    public List<VideojuegoUsuario> obtenerPorGenero(String genero) {
        return videojuegoUsuarioRepository.findByGenero(genero);
    }

    public List<VideojuegoUsuario> obtenerPorIdUsuario(Long idUsuario) {
        return videojuegoUsuarioRepository.findByIdUsuario(idUsuario);
    }

    public VideojuegoUsuario guardar(VideojuegoUsuario videojuegoUsuario) {
        return videojuegoUsuarioRepository.save(videojuegoUsuario);
    }

    public void eliminar(String id) {
        videojuegoUsuarioRepository.deleteById(id);
    }
}