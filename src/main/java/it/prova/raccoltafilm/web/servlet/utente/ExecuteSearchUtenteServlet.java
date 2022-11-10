package it.prova.raccoltafilm.web.servlet.utente;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.raccoltafilm.model.Utente;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.service.UtenteService;
import it.prova.raccoltafilm.utility.UtilityForm;

/**
 * Servlet implementation class ExecuteSearchUtenteServlet
 */
@WebServlet("/user/ExecuteSearchUtenteServlet")
public class ExecuteSearchUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtenteService utenteService;

    public ExecuteSearchUtenteServlet() {
        super();
        utenteService = MyServiceFactory.getUtenteServiceInstance();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usernameParam = request.getParameter("username");
		String nomeParam = request.getParameter("nome");
		String cognomeParam = request.getParameter("cognome");
		String dateCreatedParam = request.getParameter("dateCreated");
		
		Utente example = UtilityForm.createUtenteFromParams(usernameParam,nomeParam, cognomeParam , dateCreatedParam);
		
		try {
			request.setAttribute("utente_list_attr", utenteService.findUtenteByExample(example));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore, chissà che errore è...");
			request.getRequestDispatcher("./utente/search.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("../utente/list.jsp").forward(request, response);
	}
}
