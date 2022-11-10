package it.prova.raccoltafilm.web.servlet.utente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.raccoltafilm.model.Ruolo;
import it.prova.raccoltafilm.model.Utente;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.service.RuoloService;
import it.prova.raccoltafilm.service.UtenteService;
import it.prova.raccoltafilm.utility.UtilityForm;

@WebServlet("/user/ExecuteInsertUtenteServlet")
public class ExecuteInsertUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtenteService utenteService;
	private RuoloService ruoloService;

	public ExecuteInsertUtenteServlet() {
		super();
		utenteService = MyServiceFactory.getUtenteServiceInstance();
		ruoloService = MyServiceFactory.getRuoloServiceInstance();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// importo tutti i parametri che mi servono dalla request
		String usernameParam = request.getParameter("username");
		String nomeParam = request.getParameter("nome");
		String cognomeParam = request.getParameter("cognome");
		String passwordParam = request.getParameter("password");
		String confermaPasswordParam = request.getParameter("confirmPassword");
		String[] idRuoliUtenteParam = request.getParameterValues("ruoli");

		// creo un utente parzialmente completo
		Utente utenteTemp = new Utente(usernameParam, confermaPasswordParam, nomeParam, cognomeParam, new Date());

		// Carico tutti i ruoli da db in caso mi dovessero servire
		List<Ruolo> ruoliSuDB;
		try {
			ruoliSuDB = ruoloService.listAll();
		} catch (Exception e) {
			request.setAttribute("errorMessage", "Attenzione, non sono riuscito a pescare i ruoli dal DB.");
			request.getRequestDispatcher("home").forward(request, response);
			return;
		}

		/*
		 * in questo if gestisco il caso in cui la lista di parametri id che ho ottenuto
		 * sia null o vuota : passo nuovamente alla jsp la lista completa di ruoli, l'utente
		 * parzialmente creato e il messaggio di errore.
		 */

		if (idRuoliUtenteParam == null || idRuoliUtenteParam.length < 1) {
			request.setAttribute("tutti_ruoli_attr", ruoliSuDB);
			request.setAttribute("errorMessage", "Attenzione, selezionare almeno un ruolo.");
			request.setAttribute("utente_attr", utenteTemp);
			request.getRequestDispatcher("/utente/insert.jsp").forward(request, response);
			return;
		}

		// recupero tutti gli id dei ruoli che dovrà avere il mio utente
		List<Long> idRuoliUtente = new ArrayList<>();
		for (String idToBeParsed : idRuoliUtenteParam) {
			if (NumberUtils.isCreatable(idToBeParsed))
				idRuoliUtente.add(Long.parseLong(idToBeParsed));
		}

		// provo a caricare tutti i ruoli dell' utente, se fallisco rimando alla jsp di
		// provenienza con messaggio d' errore
		List<Ruolo> ruoliUtente;
		try {
			ruoliUtente = ruoloService.findGroupByIds(idRuoliUtente);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("tutti_ruoli_attr", ruoliSuDB);
			request.setAttribute("errorMessage", "Attenzione, non sono riuscito ad assegnare i ruoli all'utente.");
			request.setAttribute("utente_attr", utenteTemp);
			request.getRequestDispatcher("/utente/insert.jsp").forward(request, response);
			return;

		}

		// assegno al nuovo utente i suoi ruoli
		utenteTemp.getRuoli().addAll(ruoliUtente);

		// controllo che la password sia quella desiderata
		if (passwordParam.isBlank() || confermaPasswordParam.isBlank() || !passwordParam.equals(confermaPasswordParam)) {
			request.setAttribute("errorMessage", "Attenzione, verificare che la password sia corretta.");
			request.setAttribute("tutti_ruoli_attr", ruoliSuDB);
			request.setAttribute("utente_attr", utenteTemp);
			request.getRequestDispatcher("/utente/insert.jsp").forward(request, response);
			return;
		}
		
		if (usernameParam.isBlank() || nomeParam.isBlank() || cognomeParam.isBlank()) {
			request.setAttribute("errorMessage", "Attenzione, verificare che tutti i campi siano stati compilati.");
			request.setAttribute("tutti_ruoli_attr", ruoliSuDB);
			request.setAttribute("utente_attr", utenteTemp);
			request.getRequestDispatcher("/utente/insert.jsp").forward(request, response);
			return;
		}

		// alla fine provo a inserire l'utente nuovo dopo aver settato la data creazione
		utenteTemp.setDateCreated(new Date());
		try {
			utenteService.inserisciNuovo(utenteTemp);
		} catch (Exception e) {
			request.setAttribute("errorMessage", "Attenzione, non sono riuscito a creare il nuovo utente.");
			request.setAttribute("tutti_ruoli_attr", ruoliSuDB);
			request.setAttribute("utente_attr", utenteTemp);
			request.getRequestDispatcher("/utente/insert.jsp").forward(request, response);
			return;
		}
		response.sendRedirect("./user/PrepareSearchUtenteServlet?operationalResult=SUCCESS");
	}

}
