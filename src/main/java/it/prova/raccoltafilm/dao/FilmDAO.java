package it.prova.raccoltafilm.dao;

import java.util.Optional;

import it.prova.raccoltafilm.model.Film;

public interface FilmDAO extends IBaseDAO<Film> {
	public Optional<Film> findOneEager(Long id) throws Exception;
}
