package com.sistematrailers.com.trailers.app.repositorios;

import com.sistematrailers.com.trailers.app.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepositorio extends JpaRepository<Anime, Integer> {
}
