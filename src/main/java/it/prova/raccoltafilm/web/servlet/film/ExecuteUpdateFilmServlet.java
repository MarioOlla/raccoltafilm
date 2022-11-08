package it.prova.raccoltafilm.web.servlet.film;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.raccoltafilm.model.Film;
import it.prova.raccoltafilm.service.FilmService;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.utility.UtilityForm;

@WebServlet("/ExecuteUpdateFilmServlet")
public class ExecuteUpdateFilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private FilmService filmService;
    
    public ExecuteUpdateFilmServlet() {
        super();
        filmService = MyServiceFactory.getFilmServiceInstance();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idParam = request.getParameter("idFilm");
		String titoloParam = request.getParameter("titolo");
		String genereParam = request.getParameter("genere");
		String dataPubblicazioneParam = request.getParameter("dataPubblicazione");
		String minutiDurataParam = request.getParameter("minutiDurata");
		String idRegistaParam = request.getParameter("regista.id");
		
		if(idParam == null || !NumberUtils.isParsable(idParam) || Long.parseLong(idParam) < 1) {
			request.setAttribute("errorMessage", "Errore durante la ricerca, id non valido o mancante.");
			response.sendRedirect("ExecuteListFilmServlet?operationResult=NOT_FOUND");
			return;
		}
		
		Film filmInstance = UtilityForm.createFilmFromParams(titoloParam, genereParam, minutiDurataParam, dataPubblicazioneParam, idRegistaParam);
		filmInstance.setId(Long.parseLong(idParam));
		
		if(!UtilityForm.validateFilmBean(filmInstance)) {
			request.setAttribute("filmdaaggiornare", filmInstance);
			request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione.");
			request.getRequestDispatcher("/film/update.jsp").forward(request, response);
			return;
		}
		
		try {
			filmService.aggiorna(filmInstance);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/film/update.jsp").forward(request, response);
			return;
		}
		
		response.sendRedirect("ExecuteListFilmServlet?operationResult=SUCCESS");
		
		
	}
}
