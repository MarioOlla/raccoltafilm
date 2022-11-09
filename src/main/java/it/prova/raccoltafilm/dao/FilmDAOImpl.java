	package it.prova.raccoltafilm.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.prova.raccoltafilm.exceptions.ElementNotFoundException;
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
		if (filmInstance == null) {
			throw new ElementNotFoundException("Attenzione, il film da aggiornare non esiste");
		}
		filmInstance = entityManager.merge(filmInstance);

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

	@Override
	public List<Film> findByExample(Film example) throws Exception {
		Map<String, Object> parameterMap = new HashMap<>();
		List<String> whereClauses = new ArrayList<>();
		
		StringBuilder queryBuilder = new StringBuilder("select f from Film f left join f.regista r where f.id=f.id ");
		
		if(StringUtils.isNotBlank(example.getTitolo())) {
			whereClauses.add("f.titolo like :titolo ");
			parameterMap.put("titolo", "%"+example.getTitolo()+"%");
		}
		if(StringUtils.isNotBlank(example.getGenere())) {
			whereClauses.add("f.genere like :genere ");
			parameterMap.put("genere", "%"+example.getGenere()+"%");
		}
		if(example.getDataPubblicazione() != null) {
			whereClauses.add("f.dataPublicazione >= :dataPublicazione ");
			parameterMap.put("dataPublicazione", example.getDataPubblicazione());
		}
		if(example.getMinutiDurata() != null && example.getMinutiDurata() < 0) {
			whereClauses.add("f.minutiDurata >= :minutiDurata");
			parameterMap.put("minutiDurata", example.getMinutiDurata());
		}
		if(example.getRegista() != null) {
			whereClauses.add("r.id = : id");
			parameterMap.put("id", example.getRegista().getId());
		}
		
		queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Film> typedQuery = entityManager.createQuery(queryBuilder.toString(), Film.class);
		
		for (String key : parameterMap.keySet()) {
			typedQuery.setParameter(key, parameterMap.get(key));
		}
		
		return typedQuery.getResultList();
	}

}
