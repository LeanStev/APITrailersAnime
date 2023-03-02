package com.sistematrailers.com.trailers.app.service;

import jakarta.annotation.Resource;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;


public interface AlmacenServicio {

    public void iniciarAlmacenDeArchivos();
    public String almacenarArchivo(MultipartFile archivo);

    public Path cargarArchivo(String nombreArchivo);

    public Resource cargarComoRecurso(String nombreArchivo);

    public void eliminarArchivo(String eliminarArchivo);

}
