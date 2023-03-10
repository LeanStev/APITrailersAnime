package com.sistematrailers.com.trailers.app.controllers;

import com.sistematrailers.com.trailers.app.model.Anime;
import com.sistematrailers.com.trailers.app.model.Genero;
import com.sistematrailers.com.trailers.app.repositorios.AnimeRepositorio;
import com.sistematrailers.com.trailers.app.repositorios.GeneroRepositorio;
import com.sistematrailers.com.trailers.app.service.AlmacenServicioImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

import java.nio.file.Path;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Bean
    public SpringDataDialect springDataDialect() {
        return new SpringDataDialect();
    }
    @Autowired
    private AnimeRepositorio animeRepositorio;

    @Autowired
    private GeneroRepositorio generoRepositorio;

    @Autowired
    private AlmacenServicioImpl servicio;

    @GetMapping("")
    public ModelAndView verPaginaDeInicio(@PageableDefault(sort = "titulo", size = 5) Pageable pageable) {
        Page<Anime> animes = animeRepositorio.findAll(pageable);
        return new ModelAndView("admin/index").addObject("animes", animes);
    }


    @GetMapping("/animes/nuevo")
    public ModelAndView mostrarFormularioDeNuevoAnime() {
        List<Genero> generos = generoRepositorio.findAll(Sort.by("titulo"));
        return new ModelAndView("admin/nuevo-anime")
                .addObject("anime", new Anime())
                .addObject("generos", generos);
    }

    @PostMapping("/animes/nuevo")
    public ModelAndView registrarAnime(@Validated Anime anime, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || anime.getPortada().isEmpty()) {
            if (anime.getPortada().isEmpty()) {
                bindingResult.rejectValue("portada", "MultipartNotEmpty");

            }
            List<Genero> generos = generoRepositorio.findAll(Sort.by("titulo"));
            return new ModelAndView("admin/nuevo-anime")
                    .addObject("anime", anime)
                    .addObject("generos", generos);
        }
        String rutaPortada = servicio.almacenarArchivo(anime.getPortada());
        anime.setRutaPortada(rutaPortada);

        animeRepositorio.save(anime);
        return new ModelAndView("redirect:/admin");
    }

    @GetMapping("/animes/{id}/editar")
    public ModelAndView editarAnime(@PathVariable Integer id) {
        Anime anime = animeRepositorio.getOne(id);

        List<Genero> generos = generoRepositorio.findAll(Sort.by("titulo"));
        return new ModelAndView("admin/editar-anime")
                .addObject("anime", anime)
                .addObject("generos", generos);
    }

    @PostMapping("/animes/{id}/editar")
    public ModelAndView actualizarAnime(@PathVariable Integer id, @Validated Anime anime, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Genero> generos = generoRepositorio.findAll(Sort.by("titulo"));

            return new ModelAndView("admin/editar-anime")
                    .addObject("anime", anime)
                    .addObject("generos", generos);
        }
        Anime animeDB= animeRepositorio.getOne(id);
        animeDB.setTitulo(anime.getTitulo());
        animeDB.setSinopsis(anime.getSinopsis());
        animeDB.setFechaEstreno(anime.getFechaEstreno());
        animeDB.setYoutubeTrailerId(anime.getYoutubeTrailerId());
        animeDB.setGeneros(anime.getGeneros());

        if (!anime.getPortada().isEmpty()){
            servicio.eliminarArchivo(animeDB.getRutaPortada());
            String rutaPortada = servicio.almacenarArchivo(anime.getPortada());
            animeDB.setRutaPortada(rutaPortada);
        }
            animeRepositorio.save(animeDB);
        return new ModelAndView("redirect:/admin");

    }

    @PostMapping("/animes/{id}/eliminar")
    public String eliminarAnime(@PathVariable Integer id){
        Anime anime= animeRepositorio.getOne(id);
        animeRepositorio.delete(anime);
        servicio.eliminarArchivo(anime.getRutaPortada());
        return "redirect:/admin";

    }

}
