package it.prova.raccoltafilm.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import it.prova.raccoltafilm.model.Film;

public class FilmDAOImpl implements FilmDAO {

	private EntityManager entityManager;

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Film> list() throws Exception {
		return entityManager.createQuery("from Film", Film.class).getResultList();
	}

	@Override
	public Optional<Film> findOne(Long id) throws Exception {
		Film result = entityManager.find(Film.class, id);
		return result != null ? Optional.of(result) : Optional.empty();
	}

	@Override
	public void update(Film filmInstance) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(Film filmInstance) throws Exception {
		if (filmInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(filmInstance);
	}

	@Override
	public void delete(Film filmInstance) throws Exception {
		if (filmInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(filmInstance));

	}

	@Override
	public Optional<Film> findOneEager(Long id) throws Exception {
		return entityManager.createQuery("from Film f left join fetch f.regista where f.id=:idFilm", Film.class)
				.setParameter("idFilm", id).getResultList().stream().findFirst();
	}

}
