package it.prova.raccoltafilm.web.servlet.utente;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.raccoltafilm.model.Utente;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.service.RuoloService;

@WebServlet("/user/PrepareInsertUtenteServlet")
public class PrepareInsertUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RuoloService ruoloService;

	public PrepareInsertUtenteServlet() {
		super();
		ruoloService = MyServiceFactory.getRuoloServiceInstance();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("tutti_ruoli_attr", ruoloService.listAll());
		} catch (Exception e) {
			request.setAttribute("errorMessage", "Attenzione, non sono riuscito a pescare i ruoli dal DB.");
			request.getRequestDispatcher("home").forward(request, response);
			return;
		}
		request.setAttribute("utente_attr", new Utente());
		request.getRequestDispatcher("/utente/insert.jsp").forward(request, response);
	}

}
