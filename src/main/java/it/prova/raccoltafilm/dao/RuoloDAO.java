package it.prova.raccoltafilm.dao;

import java.util.List;

import it.prova.raccoltafilm.model.Ruolo;

public interface RuoloDAO extends IBaseDAO<Ruolo> {
	
	public Ruolo findByDescrizioneAndCodice(String descrizione, String codice) throws Exception;

	public List<Ruolo> findRuoliWithIdIn(List<Long> idsRuoliUtente)throws Exception;

}
