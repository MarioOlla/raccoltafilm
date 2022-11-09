package it.prova.raccoltafilm.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.prova.raccoltafilm.model.Regista;

public class RegistaDAOImpl implements RegistaDAO {

	private EntityManager entityManager;

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Regista> list() throws Exception {
		return entityManager.createQuery("from Regista", Regista.class).getResultList();
	}

	@Override
	public Optional<Regista> findOne(Long id) throws Exception {
		Regista result = entityManager.find(Regista.class, id);
		return result != null? Optional.of(result) : Optional.empty(); 
	}

	@Override
	public void update(Regista o) throws Exception {
		if(o == null) {
			throw new Exception("Problema valore in input.");
		}
		o = entityManager.merge(o);
	}

	@Override
	public void insert(Regista registaInstance) throws Exception {
		if (registaInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(registaInstance);
	}

	@Override
	public void delete(Regista o) throws Exception {
		if(o == null) {
			throw new Exception("problema valore in input.");
		}
		entityManager.remove(entityManager.merge(o));

	}

	@Override
	public List<Regista> findByExample(Regista example) throws Exception {

		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select r from Regista r where r.id = r.id ");

		if (StringUtils.isNotEmpty(example.getNome())) {
			whereClauses.add(" r.nome  like :nome ");
			paramaterMap.put("nome", "%" + example.getNome() + "%");
		}
		if (StringUtils.isNotEmpty(example.getCognome())) {
			whereClauses.add(" r.cognome like :cognome ");
			paramaterMap.put("cognome", "%" + example.getCognome() + "%");
		}
		if (StringUtils.isNotEmpty(example.getNickName())) {
			whereClauses.add(" r.nickName like :nickName ");
			paramaterMap.put("nickName", "%" + example.getNickName() + "%");
		}
		if (example.getSesso() != null) {
			whereClauses.add(" r.sesso =:sesso ");
			paramaterMap.put("sesso", example.getSesso());
		}
		if (example.getDataDiNascita() != null) {
			whereClauses.add("r.dataDiNascita >= :dataDiNascita ");
			paramaterMap.put("dataDiNascita", example.getDataDiNascita());
		}
		
		queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Regista> typedQuery = entityManager.createQuery(queryBuilder.toString(), Regista.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();

	}

	@Override
	public Regista findOneEager(Long id) throws Exception {
		return entityManager.createQuery("from Regista r left join fetch r.films f where r.id=?1", Regista.class).setParameter(1, id).getResultStream().findFirst().orElse(null);
	}

}
			