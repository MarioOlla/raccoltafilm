package it.prova.raccoltafilm.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.prova.raccoltafilm.model.Film;
import it.prova.raccoltafilm.model.Ruolo;
import it.prova.raccoltafilm.model.StatoUtente;
import it.prova.raccoltafilm.model.Utente;

public class UtenteDAOImpl implements UtenteDAO {

	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Utente> list() throws Exception {
		// dopo la from bisogna specificare il nome dell'oggetto (lettera maiuscola) e
		// non la tabella
		return entityManager.createQuery("from Utente", Utente.class).getResultList();
	}

	@Override
	public Optional<Utente> findOne(Long id) throws Exception {
		Utente result = entityManager.find(Utente.class, id);
		return result != null ? Optional.of(result) : Optional.empty();
	}
	
	@Override
	public Optional<Utente> findOneEager(Long id) throws Exception {
		return entityManager.createQuery("select u from Utente u left join fetch u.ruoli where u.id=:id", Utente.class).setParameter("id", id).getResultStream().findFirst();
	}

	@Override
	public void update(Utente utenteInstance) throws Exception {
		if (utenteInstance == null) {
			throw new Exception("Problema valore in input");
		}
		utenteInstance = entityManager.merge(utenteInstance);
	}

	@Override
	public void insert(Utente utenteInstance) throws Exception {
		if (utenteInstance == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.persist(utenteInstance);
	}

	@Override
	public void delete(Utente utenteInstance) throws Exception {
		if (utenteInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(utenteInstance));
	}

	// questo metodo ci torna utile per capire se possiamo rimuovere un ruolo non
	// essendo collegato ad un utente
	public List<Utente> findAllByRuolo(Ruolo ruoloInput) throws Exception {
		TypedQuery<Utente> query = entityManager.createQuery("select u FROM Utente u join u.ruoli r where r = :ruolo",
				Utente.class);
		query.setParameter("ruolo", ruoloInput);
		return query.getResultList();
	}

	@Override
	public Optional<Utente> findByUsernameAndPassword(String username, String password) throws Exception {
		TypedQuery<Utente> query = entityManager.createQuery(
				"select u FROM Utente u  " + "where u.username = :username and u.password=:password ", Utente.class);
		query.setParameter("username", username);
		query.setParameter("password", password);
		return query.getResultStream().findFirst();
	}

	@Override
	public Optional<Utente> login(String username, String password) throws Exception {
		TypedQuery<Utente> query = entityManager.createQuery(
				"select u FROM Utente u join fetch u.ruoli r "
						+ "where u.username = :username and u.password=:password and u.stato=:statoUtente",
				Utente.class);
		query.setParameter("username", username);
		query.setParameter("password", password);
		query.setParameter("statoUtente", StatoUtente.ATTIVO);
		return query.getResultStream().findFirst();
	}

	@Override
	public List<Utente> findByExample(Utente example) throws Exception {

		Map<String, Object> parameterMap = new HashMap<>();
		List<String> whereClauses = new ArrayList<>();

		StringBuilder queryBuilder = new StringBuilder("from Utente u where u.id=u.id ");

		if (StringUtils.isNotBlank(example.getUsername())) {
			whereClauses.add("u.username like :username");
			parameterMap.put("username", "%"+example.getUsername()+"%");
		}
		if (StringUtils.isNotBlank(example.getNome())) {
			whereClauses.add("u.nome like :nome");
			parameterMap.put("nome","%"+example.getNome()+"%");
		}
		if (StringUtils.isNotBlank(example.getCognome())) {
			whereClauses.add("u.cognome like :cognome");
			parameterMap.put("cognome","%"+example.getCognome()+"%");
		}
		if (example.getDateCreated() != null) {
			whereClauses.add("u.dateCreated >= :dateCreated");
			parameterMap.put("dateCreated", example.getDateCreated() );
		}

		queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Utente> typedQuery = entityManager.createQuery(queryBuilder.toString(), Utente.class);
		
		for (String key : parameterMap.keySet()) {
			typedQuery.setParameter(key, parameterMap.get(key));
		}
		
		return typedQuery.getResultList();
	}
}
