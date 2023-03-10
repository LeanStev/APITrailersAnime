package com.sistematrailers.com.trailers.app.controllers;

import com.sistematrailers.com.trailers.app.model.Anime;
import com.sistematrailers.com.trailers.app.repositorios.AnimeRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private AnimeRepositorio animeRepositorio;

    @GetMapping("")
    public ModelAndView verPaginaDeInicio(){
        List<Anime> ultimosAnimes = animeRepositorio.findAll(PageRequest.of(0,4, Sort.by("fechaEstreno").descending())).toList();
        return new ModelAndView("index")
                .addObject("ultimosAnimes",ultimosAnimes);
    }

    @GetMapping("/animes")
    public ModelAndView listarAnimes(@PageableDefault(sort = "fechaEstreno",direction=Sort.Direction.DESC)Pageable pageable){
        Page<Anime> animes = animeRepositorio.findAll(pageable);
        return new ModelAndView("animes")
                .addObject("animes",animes);
    }
}
