package com.sistematrailers.com.trailers.app.controllers;

import com.sistematrailers.com.trailers.app.model.Anime;
import com.sistematrailers.com.trailers.app.repositorios.AnimeRepositorio;
import com.sistematrailers.com.trailers.app.repositorios.GeneroRepositorio;
import com.sistematrailers.com.trailers.app.service.AlmacenServicioImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AnimeRepositorio animeRepositorio;

    @Autowired
    private GeneroRepositorio generoRepositorio;

    @Autowired
    private AlmacenServicioImpl servicio;

    @GetMapping("")
    public ModelAndView verPaginaDeInicio(@PageableDefault(sort = "titulo",size = 5)Pageable pageable){
        Page<Anime> animes = animeRepositorio.findAll(pageable);
        return new ModelAndView("index").addObject("animes",animes);
    }



}
