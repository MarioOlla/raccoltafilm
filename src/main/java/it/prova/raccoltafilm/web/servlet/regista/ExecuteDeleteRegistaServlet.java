package it.prova.raccoltafilm.web.servlet.regista;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.raccoltafilm.exceptions.ElementNotFoundException;
import it.prova.raccoltafilm.exceptions.RegistaConFilmAssociatiException;
import it.prova.raccoltafilm.model.Regista;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.service.RegistaService;

@WebServlet("/ExecuteDeleteRegistaServlet")
public class ExecuteDeleteRegistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RegistaService registaService;

	public ExecuteDeleteRegistaServlet() {
		super();
		registaService = MyServiceFactory.getRegistaServiceInstance();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idRegistapParam = request.getParameter("idRegista");

		if (idRegistapParam == null || !NumberUtils.isParsable(idRegistapParam)) {
			request.setAttribute("errorMessage", "Attenzione, id in input mancante o non valido.");
			request.getRequestDispatcher("/regista/list.jsp").forward(request, response);
			return;
		}
		
		try {
			registaService.rimuovi(Long.parseLong(idRegistapParam));
		}catch (ElementNotFoundException e) {
			request.getRequestDispatcher("ExecuteListRegistaServlet?operationResult=NOT_FOUND").forward(request, response);
			return;
		}catch (RegistaConFilmAssociatiException e) {
			request.getRequestDispatcher("ExecuteListRegistaServlet?operationResult=REGISTA_WITH_FILMS").forward(request, response);
			return;
		}catch (Exception e) {
			request.setAttribute("errorMessage", "Si è verificato un errore, ci scusiamo per il disagios.");
			request.getRequestDispatcher("home").forward(request, response);
			return;
		}
		response.sendRedirect("ExecuteListRegistaServlet?operationResult=SUCCESS");
	}

}
