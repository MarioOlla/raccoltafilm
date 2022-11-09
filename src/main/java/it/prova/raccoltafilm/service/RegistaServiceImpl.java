package it.prova.raccoltafilm.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.raccoltafilm.dao.RegistaDAO;
import it.prova.raccoltafilm.exceptions.ElementNotFoundException;
import it.prova.raccoltafilm.exceptions.RegistaConFilmAssociatiException;
import it.prova.raccoltafilm.model.Regista;
import it.prova.raccoltafilm.web.listener.LocalEntityManagerFactoryListener;

public class RegistaServiceImpl implements RegistaService {

	private RegistaDAO registaDAO;

	@Override
	public void setRegistaDAO(RegistaDAO registaDAO) {
		this.registaDAO = registaDAO;
	}

	@Override
	public List<Regista> listAllElements() throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// uso l'injection per il dao
			registaDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return registaDAO.list();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

	@Override
	public Regista caricaSingoloElemento(Long id) throws Exception {
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			registaDAO.setEntityManager(entityManager);

			return (Regista) registaDAO.findOne(id).get();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

	@Override
	public Regista caricaSingoloElementoConFilms(Long id) throws Exception {
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			registaDAO.setEntityManager(entityManager);
		
			Regista result = registaDAO.findOneEager(id);
			
			if(result == null)
				throw new ElementNotFoundException("Nessun regista trovato con questo id.");
			
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Regista registaInstance) throws Exception {
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			registaDAO.setEntityManager(entityManager);
			if (registaInstance.getId() == null || registaInstance.getId() < 1)
				throw new ElementNotFoundException("Errore durante l'aggiornamento, id non valido o mancante.");
			registaDAO.update(registaInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}

	}

	@Override
	public void inserisciNuovo(Regista registaInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			registaDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			registaDAO.insert(registaInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

	@Override
	public void rimuovi(Long idRegista) throws Exception {
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			
			
			entityManager.getTransaction().begin();
			
			Regista daRimuovere = this.caricaSingoloElementoConFilms(idRegista);

			registaDAO.setEntityManager(entityManager);
			
			if(daRimuovere == null){
				throw new ElementNotFoundException("Errore, nessun elemento con questo id:"+idRegista);
			}
			
			if(!daRimuovere.getFilms().isEmpty()) {
				throw new RegistaConFilmAssociatiException("Errore, questo regista ha ancora film associati.");
			}
			
			registaDAO.delete(daRimuovere);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}

	}

	@Override
	public List<Regista> findByExample(Regista example) throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// uso l'injection per il dao
			registaDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return registaDAO.findByExample(example);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

}
