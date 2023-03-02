package com.sistematrailers.com.trailers.app.service;

import com.sistematrailers.com.trailers.app.exceptions.AlmacenException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AlmacenServicioImpl implements AlmacenServicio{

    @Value("${storage.location}")
    private String storageLocation;


    /*Esta anotacion sirve para indicar que este metodo
    se va a ejecutar cada vez que
    haya una nueva instancia de esta clase*/
    @PostConstruct
    @Override
    public void iniciarAlmacenDeArchivos() {
        try {
            Files.createDirectories(Paths.get(storageLocation));
        }catch (IOException exception){
            throw new AlmacenException("Error al iniciarlizar la ubicacion en el almacen de archivos");

        }

    }

    @Override
    public String almacenarArchivo(MultipartFile archivo) {
        return null;
    }

    @Override
    public Path cargarArchivo(String nombreArchivo) {
        return null;
    }

    @Override
    public Resource cargarComoRecurso(String nombreArchivo) {
        return null;
    }

    @Override
    public void eliminarArchivo(String eliminarArchivo) {

    }
}
